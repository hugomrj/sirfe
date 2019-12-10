/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.objeto_gasto;


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


@Path("objetosgastos/consulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class ObjetoGastoWS_query {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    ObjetoGasto com = new ObjetoGasto();       
                         
    public ObjetoGastoWS_query() {
    }

    
    
    
    @GET    
    @Path("/consulta02/{estadores}/{fechadesde}/{fechahasta}/{objdesde}/{objhasta}")    
    public Response consulta01 ( 
            
            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("objdesde") Integer objdesde,
            @PathParam ("objhasta") Integer objhasta,
            
            @HeaderParam("token") String strToken
            ) {
        

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                String json = "[]";
                
                
                if (!( fechadesde.trim().equals("0") || fechahasta.trim().equals("0")) )
                {  
                    
                    JsonArray jsonarray = new ObjetoGastoJSON().consulta02(estadores, fechadesde, fechahasta, 
                            objdesde, objhasta);
                    
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
    
    
    
  