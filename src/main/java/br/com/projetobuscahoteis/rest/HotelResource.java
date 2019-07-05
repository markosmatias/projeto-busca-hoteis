package br.com.projetobuscahoteis.rest;

import br.com.projetobuscahoteis.dao.DAO;
import br.com.projetobuscahoteis.dto.HotelDTO;
import br.com.projetobuscahoteis.dto.PostDTO;
import br.com.projetobuscahoteis.model.Bairro;
import br.com.projetobuscahoteis.model.Cidade;
import br.com.projetobuscahoteis.model.Hotel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Hibernate;

// Esta classe representa o ENDPOINT REST para a Entidade Hotel.
@Path("/hotel") // A Anotação @Path define o caminho através do ApplicationPath, que foi definido no RestConfig.
public class HotelResource {

    @Inject // Aqui é onde ocorre as injeções de dependências, todas utilizadas pelo @Inject
    private DAO<Hotel> hotelDao;

    @Inject
    private DAO<Bairro> bairroDao;

    @Inject
    private DAO<Cidade> cidadeDao;

    @GET // O @GET define que o método de acesso pelo ENDPOINT será via GET
    @Path(value = "/findbybairro/{id}") // O @Path aqui irá definir também o caminho para o método, seguindo a hierarquia padrão. Ex.: /api/hotel/findbybairro
    public Response findByBairro(@PathParam("id") Long id) {
       /* Note que no @Path nós temos o {id}, esse é responsável por parametrizar a URL, ou seja,
        * ele está indicando que ali será incluído um valor para ser inserido em uma variável.
        * Note que também temos o @PathParam nos paramâmetros do método. Essa anotação está dizendo
        * ao Java que o parametro do método findByBairro é associado ao valor em {id} do caminho da URL.
        */
        Bairro bairro = this.bairroDao.findById(id);
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("bairro", bairro);
        List<HotelDTO> hoteis = new ArrayList<>();
        for (Hotel hotel : this.hotelDao.findByFields(filtros, Arrays.asList("bairro"))) {
            HotelDTO dto = new HotelDTO();
            dto.setId(hotel.getId());
            dto.setNome(hotel.getNome());
            dto.setEstrelas(hotel.getEstrelas());
            dto.setBairro(Hibernate.isInitialized(hotel.getBairro()) ? hotel.getBairro() : null);
            hoteis.add(dto);
        }
        return Response.ok(hoteis, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path(value = "/findbycidade/{id}")
    public Response findByCidade(@PathParam("id") Long id) {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("cidade", this.cidadeDao.findById(id));
        List<Bairro> bairros = this.bairroDao.findByFields(filtros, Arrays.asList("cidade", "hoteis"));
        List<HotelDTO> hoteis = new ArrayList<>();
        for (Bairro bairro : bairros) {
            for (Hotel hotel : bairro.getHoteis()) {
                HotelDTO dto = new HotelDTO();
                dto.setId(hotel.getId());
                dto.setNome(hotel.getNome());
                dto.setEstrelas(hotel.getEstrelas());
                dto.setBairro(bairro);
                hoteis.add(dto);
            }
        }
        return Response.ok(hoteis, MediaType.APPLICATION_JSON).build();
    }

    @POST // @POST define que a URL receberá via POST a requisição no ENDPOINT
    @Consumes(MediaType.APPLICATION_JSON) // @Consumes indica ao Java que esse valor recebido é de um certo tipo. (No caso, diz que será recebido no POST uma string Json.
    @Path(value = "/findbycidprecam") // No Post não é necessário passar um parametro como no GET, pois esse irá vir no Body da requisição, e não na URL.
    public Response findByCidadePrecoCamas(PostDTO post) {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("cidade", post.getCidade());
        filtros.put("precoInicial", post.getPrecoInicial());
        filtros.put("precoFinal", post.getPrecoFinal());
        filtros.put("totalCamas", post.getTotalCamas());
        List<HotelDTO> hoteis = new ArrayList<>();
        for (Hotel hotel : this.hotelDao.findByNamedQuery(Hotel.FIND_BY_CIDADE_PRECO_CAMAS, filtros)) {
            HotelDTO dto = new HotelDTO();
            dto.setId(hotel.getId());
            dto.setNome(hotel.getNome());
            dto.setEstrelas(hotel.getEstrelas());
            dto.setBairro(Hibernate.isInitialized(hotel.getBairro()) ? hotel.getBairro() : null);
            hoteis.add(dto);
        }
        // O Response é utilizado para dar um retorno na requisição que foi feita, aqui será realizado o retorno no formato Json.
        return Response.ok(hoteis, MediaType.APPLICATION_JSON).build();
    }

}
