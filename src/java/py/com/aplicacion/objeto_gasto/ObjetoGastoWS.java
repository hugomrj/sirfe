/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.objeto_gasto;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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


@Path("objetosgastos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class ObjetoGastoWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    ObjetoGasto com = new ObjetoGasto();       
                         
    public ObjetoGastoWS() {
    }

    
    
    
    @GET    
    public Response list ( 
            @HeaderParam("token") String strToken,
            @QueryParam("page") Integer page) {
        
            if (page == null) {                
                page = 1;
            }

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                ObjetoGastoDAO dao = new ObjetoGastoDAO();                
                List<ObjetoGasto> lista = dao.list(page);                
                String json = gson.toJson( lista );     
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        .header("total_registros", dao.total_registros )
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
                    .header("token", strToken)
                    .build();                                        
        }      
    }    
    
      
    
    
        
    
    
    @GET       
    //@Path("/search/{opt : (.*)}") 
    @Path("/search/") 
    public Response search ( 
            @HeaderParam("token") String strToken,
            @QueryParam("page") Integer page,              
            @MatrixParam("q") String q
            ) {
        
        
            if (page == null) {                
                page = 1;
            }
            if (q == null){            
                q = "";                
            }
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                ObjetoGastoDAO dao = new ObjetoGastoDAO();
                
                List<ObjetoGasto> lista = dao.search(page, q);
                String json = gson.toJson( lista );     
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        .header("total_registros", dao.total_registros )
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
                    .header("token", strToken)
                    .build();                                        
        }      
    }    
    
    
    
    
    @GET
    @Path("/{id}")
    public Response get(     
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id ) {
                     
        try 
        {                  
            if (autorizacion.verificar(strToken))
            {
                autorizacion.actualizar();                
                
                
               // this.com = (ObjetoGasto) persistencia.filtrarId(this.com, id);  
                this.com = (ObjetoGasto) new ObjetoGastoDAO().filtrarId(id);  
                
                if (this.com == null){
                    this.status = Response.Status.NO_CONTENT;                           
                }

                
                return Response
                        .status( this.status )
                        .entity(this.com)
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
                    .header("token", strToken)
                    .build();                                        
        }        
        
    }    
      
        


    
}