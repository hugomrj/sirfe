/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.rendicion_gasto;


import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nebuleuse.ORM.Persistencia;
import nebuleuse.seguridad.Autentificacion;



/**
 * REST Web Service
 * @author hugo
 */


@Path("rendicionesgastos/verificacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class RendicionGastoWS_verificador {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    RendicionGasto componente = new RendicionGasto();       
                         
    public RendicionGastoWS_verificador() {
    }

    
  
    
    
    
    @GET
    @Path("/resoluciones/{consejo}") 
    public Response resoluciones_all (
        @HeaderParam("token") String strToken,
        @PathParam ("consejo") Integer consejo, 
        @QueryParam("page") Integer page ) {
        
            if (page == null) {                
                page = 1;
            }        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                RendicionGastoJSON jsonarray = new RendicionGastoJSON();
                
                Integer usuario =  Integer.parseInt(autorizacion.token.getUser());   
                
                String json = jsonarray.list_resolucion_consejo(page, usuario, consejo).toString();
                
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())                        
                        .build();                       
                
            }
            else
            {                
                return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header("token", null)
                    .build();    
            }        
        }   
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .header("token", null)
                    .build();                                        
        }          

        
    }    
    


        

    
}