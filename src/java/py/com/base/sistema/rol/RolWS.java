/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.base.sistema.rol;

import com.google.gson.Gson;
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


/**
 * REST Web Service
 * @author hugo
 */


@Path("roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class RolWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    Rol rol = new Rol();       
                         
    public RolWS() {
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
                
                RolDAO dao = new RolDAO();                
                List<Rol> lista = dao.list(page);                
                String json = gson.toJson( lista );     
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        .header("total_registros", dao.total_registros )
                        .build();                       
            }
            else{
                // se puede generar un response de tiempo excedido
                throw new Exception();
            }        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
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
                
                RolDAO dao = new RolDAO();
                
                List<Rol> lista = dao.search(page, q);
                String json = gson.toJson( lista );     
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        .header("total_registros", dao.total_registros )
                        .build();                       
            }
            else{
                // se puede generar un response de tiempo excedido
                throw new Exception();
            }        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
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
                this.rol = (Rol) persistencia.filtrarId(rol, id);  
                
                if (this.rol == null){
                    this.status = Response.Status.NO_CONTENT;                           
                }

                
                return Response
                        .status( this.status )
                        .entity(this.rol)
                        .header("token", autorizacion.encriptar())
                        .build();       
            }
            else{
                // se puede generar un response de tiempo excedido
                throw new Exception();
            }
        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
                    .build();                                        
        }        
        
    }    
      
        
    

 
    @POST
    public Response add( 
            @HeaderParam("token") String strToken,
            Rol req ) {
                     
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                this.rol = (Rol) persistencia.insert(req);
                                             
                if (this.rol == null){
                    throw new Exception();                
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(this.rol)
                        .header("token", autorizacion.encriptar())
                        .build();       
            }
            else
            {                
                throw new Exception();                
            }
        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
                    .build();                                        
        }        

    }    
 
    
        
    

         

    @PUT    
    @Path("/{id}")    
    public Response edit (
            Rol req,
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id) {
        
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                req.setRol(id);
                this.rol = (Rol) persistencia.update(req);
            
                return Response
                        .status(Response.Status.OK)
                        .entity(this.rol)
                        .header("token", autorizacion.encriptar())
                        .build();       
            }
            else{    
                throw new Exception();                
            }
        
        }     
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
                    .build();                    
    
        }        
    }    
    
    
    

    

    @DELETE  
    @Path("/{id}")    
    public Response delete (            
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id) {
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
            
                Integer filas = 0;
                filas = persistencia.delete(this.rol, id) ;
                
                
                if (filas != 0){
                    
                    return Response
                            .status(Response.Status.OK)
                            .entity(null)
                            .header("token", autorizacion.encriptar())
                            .build();                       
                }
                else{                    
                    
                    return Response
                            .status(Response.Status.NO_CONTENT)
                            .entity(null)
                            .header("token", autorizacion.encriptar())
                            .build();          
                    
                    
                }
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
                    .header("token", autorizacion.encriptar())
                    .build();           
        }  
        
    }    
        
    
    
    

    
}