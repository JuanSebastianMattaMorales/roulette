package ruleta.ws;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ruleta.negocio.GestionRuletaNegocio;

/**
 * @author Juan Sebastian Matta Morales
 */
@Path("/endPoint")
public class RouletteRESTFull implements Serializable{

	private static final long serialVersionUID = 7614564158678710546L;
	private Logger log = Logger.getLogger(RouletteRESTFull.class);
	private GestionRuletaNegocio rouletteManagement = new GestionRuletaNegocio();

	@POST
	@Path("/service1")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject newRoulette (String parameter){	
		JsonObjectBuilder respuestaBuilder = Json.createObjectBuilder();
		respuestaBuilder=rouletteManagement.rouletteOpeningDeal();
		
		return respuestaBuilder.build();
	}
		
	@POST
	@Path("/service2")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject rouletteOpening (String parameter){
		JsonObjectBuilder respuestaBuilder = Json.createObjectBuilder();
		respuestaBuilder=rouletteManagement.rouletteOpening(parameter);
		
		return respuestaBuilder.build();
	}
		
	@POST
	@Path("/service3")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonObject betPlay (@HeaderParam("Authorization") String user,String parameter){	
		JsonObjectBuilder respuestaBuilder = Json.createObjectBuilder();
		respuestaBuilder=rouletteManagement.betPlay(user,parameter);
		
		return respuestaBuilder.build();
	}
	
	@POST
	@Path("/service4")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject betClosing (String parameter){	
		JsonObjectBuilder respuestaBuilder = Json.createObjectBuilder();
		respuestaBuilder=rouletteManagement.betClosing(parameter);
		
		return respuestaBuilder.build();
	}
	
	@POST
	@Path("/service5")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject rouletteStatus (){	
		JsonObjectBuilder respuestaBuilder = Json.createObjectBuilder();
		respuestaBuilder=rouletteManagement.rouletteStatus();
		
		return respuestaBuilder.build();
	}
}