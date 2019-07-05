package br.com.projetobuscahoteis.rest;

import br.com.projetobuscahoteis.dao.DAO;
import br.com.projetobuscahoteis.dto.ReservaDTO;
import br.com.projetobuscahoteis.model.Quarto;
import br.com.projetobuscahoteis.model.Reserva;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;

@Path("/reserva")
public class ReservaResource {
    
    @Inject
    private DAO<Reserva> reservaDao;
    
    @POST // Devido a ser uma inserção no banco, o padrão é utilizar o POST
    @Consumes(MediaType.APPLICATION_JSON) // O Consumes indica para o Java que esse método irá receber um JSON, e que ele deve converter para objeto.
    @Path("/reservarquarto") //  Caminho do para acessar o método
    public Response reservarQuarto(ReservaDTO dto) {
        dto.setId(null);
        Map<String, Object> datas = new HashMap<>();
        datas.put("dtIni", dto.getDataInicio());
        datas.put("dtFim", dto.getDataFim());
        Reserva r = this.reservaDao.findByNamedQuery(Reserva.FIND_BY_DATAS, datas).stream().findFirst().orElse(null);
        if (r != null) {
            List<Quarto> quartos = r.getQuartos().stream()
                .filter(res -> dto.getQuartos().stream().map(Quarto::getId).anyMatch(dId -> dId.equals(res.getId())))
                .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(quartos)) {
                StringBuilder resposta = new StringBuilder();
                resposta.append("Ja existe reserva nessas datas para os quartos selecionados: Ids = [");
                for (Quarto q : quartos) {
                    resposta.append(", ").append(q.getId());
                }
                resposta.append("]");
                return Response.ok(resposta.toString().replaceFirst("\\[,", "["), MediaType.TEXT_HTML).build();
            }
        }
        Reserva reserva = new Reserva();
        reserva.setDataInicio(dto.getDataInicio());
        reserva.setDataFim(dto.getDataFim());
        reserva.setCliente(dto.getCliente());
        reserva.setQuartos(dto.getQuartos());
        this.reservaDao.save(reserva);
        return Response.ok("Reserva realizada com sucesso!", MediaType.TEXT_HTML).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterarreserva")
    public Response alterarReserva(ReservaDTO dto) {
        Reserva reserva = this.reservaDao.findById(dto.getId());
        Hibernate.initialize(reserva.getCliente());
        if (dto.getId().equals(reserva.getCliente().getId())) {
            reserva.setDataInicio(dto.getDataInicio());
            reserva.setDataFim(dto.getDataFim());
            reserva.setQuartos(dto.getQuartos());
            this.reservaDao.atualizar(reserva);
            return Response.ok("Reserva atualizada com sucesso!", MediaType.TEXT_XML).build();
        } else {
            return Response.ok("É necessário o ID da Reserva para realizar a alteração!", MediaType.TEXT_XML).build();
        }
    }
    
    @GET // Como se trata apenas de uma exclusão por id, pode-se utilizar o GET para realizar a requisição.
    @Path("/excluirreserva/{id}")
    public Response excluirReserva(@PathParam("id") Long id) {
        if (id != null) {
            this.reservaDao.removeById(id);
            return Response.ok("Reserva foi excluída com sucesso.", MediaType.TEXT_XML).build();
        } else {
            return Response.ok("É necessário o ID da Reserva para realizar a exclusão!", MediaType.TEXT_XML).build();
        }
    }
    
}
