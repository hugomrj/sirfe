/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.consejosalud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nebuleuse.ORM.Persistencia;
import nebuleuse.seguridad.Autentificacion;


@Path("consejosalud/consulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class ConsejoSaludWS_query {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    ConsejoSalud com = new ConsejoSalud();       
                         
    public ConsejoSaludWS_query() {
    }

    
    
    
    @GET    
    @Path("/consulta01/{estadores}/{fechadesde}/{fechahasta}")    
    public Response consulta01 ( 
            
            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            
            @HeaderParam("token") String strToken
            ) {
        

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                String json = "[]";
                
                
                if (!( fechadesde.trim().equals("0") || fechahasta.trim().equals("0")) )
                {
                    
                    JsonArray jsonarray = new ConsejoSaludJSON().consulta01(estadores, fechadesde, fechahasta);                
                    json = jsonarray.toString();                
                }
                
                
                
                return Response
                        .status(Response.Status.OK)
                        .entity( json )
                        .header("token", autorizacion.encriptar())                        
                        .build();                       
            }
            else{
                
                return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header("token", null)
                    .build();     
                
            }        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", autorizacion.encriptar())
                    .build();                                        
        }      
    }    
    
      
    
        
    

    
    
    @GET        
    @Path("/consulta04/{fechadesde}/{fechahasta}/{consejodesde}/{consejohasta}")    
    public Response consulta04 ( 

            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("consejodesde") Integer consejodesde,
            @PathParam ("consejohasta") Integer consejohasta,            
            
            
            @HeaderParam("token") String strToken
            ) {
        

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                String json = "[]";
                
                
                if (!( fechadesde.trim().equals("0") || fechahasta.trim().equals("0")) )
                {
                    
                    JsonArray jsonarray = new ConsejoSaludJSON().consulta04(fechadesde, fechahasta, consejodesde, consejohasta);
                    json = jsonarray.toString();                
                }
                
                
                
                return Response
                        .status(Response.Status.OK)
                        .entity( json )
                        .header("token", autorizacion.encriptar())                        
                        .build();                       
            }
            else{
                
                return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header("token", null)
                    .build();     
                
            }        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", autorizacion.encriptar())
                    .build();                                        
        }      
    }    
    
      
    

    

    
    
    @GET        
    @Path("/consulta05/{estadores}/{fechadesde}/{fechahasta}/{consejodesde}/{consejohasta}")    
    public Response consulta05 ( 

            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("consejodesde") Integer consejodesde,
            @PathParam ("consejohasta") Integer consejohasta,            
            
            @HeaderParam("token") String strToken
            ) {
        

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                String json = "[]";
                                
                if (!( fechadesde.trim().equals("0") || fechahasta.trim().equals("0")) )
                {
                    
                    JsonArray jsonarray = new ConsejoSaludJSON().consulta05(estadores, 
                            fechadesde, fechahasta, consejodesde, consejohasta);
                    json = jsonarray.toString();                
                }
                
                
                
                return Response
                        .status(Response.Status.OK)
                        .entity( json )
                        .header("token", autorizacion.encriptar())                        
                        .build();                       
            }
            else{
                
                return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header("token", null)
                    .build();     
                
            }        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", autorizacion.encriptar())
                    .build();                                        
        }      
    }    
    
      
    
    
}
    
    
    
  