/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.consulta;

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


@Path("consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class ConsultaWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
                        
    public ConsultaWS() {
    }

    
    
    
    
    @GET        
    @Path("/consulta001/{fechadesde}/{fechahasta}/{dptodesde}/{dptohasta}/{consejodesde}/{consejohasta}")    
    public Response consulta001 ( 

            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("dptodesde") Integer dptodesde,
            @PathParam ("dptohasta") Integer dptohasta,            
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
                    
                    JsonArray jsonarray = new ConsultaJSON().consulta001(fechadesde, fechahasta, 
                            dptodesde, dptohasta,
                            consejodesde, consejohasta);
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
    @Path("/consulta002/{estadores}/{fechadesde}/{fechahasta}/{dptodesde}/{dptohasta}"
            + "/{consejodesde}/{consejohasta}/{objdesde}/{objhasta}"   )    
    public Response consulta002 (
            
            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("dptodesde") Integer dptodesde,
            @PathParam ("dptohasta") Integer dptohasta,            
            @PathParam ("consejodesde") Integer consejodesde,
            @PathParam ("consejohasta") Integer consejohasta,            
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
                    
                    JsonArray jsonarray = new ConsultaJSON().consulta002( estadores,
                            fechadesde, fechahasta, 
                            dptodesde, dptohasta,
                            consejodesde, consejohasta,
                            objdesde, objhasta
                            );
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
    @Path("/consulta003/{fechadesde}/{fechahasta}/{dptodesde}/{dptohasta}"
            + "/{consejodesde}/{consejohasta}"   )    
    public Response consulta003 (
            
            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("dptodesde") Integer dptodesde,
            @PathParam ("dptohasta") Integer dptohasta,            
            @PathParam ("consejodesde") Integer consejodesde,
            @PathParam ("consejohasta") Integer consejohasta,            
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
                    
                    JsonArray jsonarray = new ConsultaJSON().consulta003( 
                            fechadesde, fechahasta, 
                            dptodesde, dptohasta,
                            consejodesde, consejohasta                    
                            );
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
    @Path("/consulta004/{estadores}/{fechadesde}/{fechahasta}/{dptodesde}/{dptohasta}"
            + "/{consejodesde}/{consejohasta}/{objdesde}/{objhasta}"   )    
    public Response consulta004 (
            
            @PathParam ("estadores") Integer estadores,
            @PathParam ("fechadesde") String fechadesde,
            @PathParam ("fechahasta") String fechahasta,
            @PathParam ("dptodesde") Integer dptodesde,
            @PathParam ("dptohasta") Integer dptohasta,            
            @PathParam ("consejodesde") Integer consejodesde,
            @PathParam ("consejohasta") Integer consejohasta,            
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
                    
                    JsonArray jsonarray = new ConsultaJSON().consulta004( estadores,
                            fechadesde, fechahasta, 
                            dptodesde, dptohasta,
                            consejodesde, consejohasta,
                            objdesde, objhasta
                            );
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
    
    
    
  