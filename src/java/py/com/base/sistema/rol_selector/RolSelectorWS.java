/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.base.sistema.rol_selector;

import com.google.gson.Gson;
import java.sql.SQLException;
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





@Path("rolselector")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class RolSelectorWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    
    RolSelector objeto = new RolSelector();       
    
    
                         
    public RolSelectorWS() {
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
                
                RolSelectorDAO dao = new RolSelectorDAO();                
                List<RolSelector> lista = dao.list(page);                
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
    
      
    
    
    
    ///api/usuarios/search/;q=fafaasf?page=1
    @GET           
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
                
                RolSelectorDAO dao = new RolSelectorDAO();
                
                List<RolSelector> lista = dao.search(page, q);
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
    @Path("/rol/{ro}/")
    public Response cabRol ( 
            @HeaderParam("token") String strToken,
            @QueryParam("page") Integer page,   
            @PathParam ("ro") Integer ro
            ) {
        
        
            if (page == null) {                
                page = 1;
            }

        try {                    
           
            if (autorizacion.verificar(strToken))            
            {                
                autorizacion.actualizar();                                
                
                RolSelectorDAO dao = new RolSelectorDAO();                
                List<RolSelector> lista = dao.cabRol(ro, page);
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
    @Path("/selector/{se}/")
    public Response cabSelector ( 
            @HeaderParam("token") String strToken,
            @QueryParam("page") Integer page,   
            @PathParam ("se") Integer se
            ) {
        
        
            if (page == null) {                
                page = 1;
            }

        try {                    
           
            if (autorizacion.verificar(strToken))            
            {                
                autorizacion.actualizar();                                
                
                RolSelectorDAO dao = new RolSelectorDAO();                
                List<RolSelector> lista = dao.cabSelector(se, page);
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
                this.objeto = (RolSelector) persistencia.filtrarId(this.objeto, id);  
                
                return Response
                        .status(Response.Status.OK)
                        .entity(this.objeto)
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
            RolSelector req ) {
                     
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                this.objeto = (RolSelector) persistencia.insert(req);
                                             
                if (this.objeto == null){
                    throw new Exception();                
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(this.objeto)
                        .header("token", autorizacion.encriptar())
                        .build();       
            }
            else
            {                
                throw new Exception();                
            }
        }     
        
        
        catch (SQLException ex) {     

            return Response
                    .status(Response.Status.BAD_GATEWAY)
                    .entity(ex.getMessage())
                    .header("token", autorizacion.encriptar())
                    .build();       
            
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
            RolSelector req,
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id) {
        
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                req.setId(id);
                this.objeto = (RolSelector) persistencia.update(req);
            
                return Response
                        .status(Response.Status.OK)
                        .entity(this.objeto)
                        .header("token", autorizacion.encriptar())
                        .build();       
            }
            else{    
                throw new Exception();                
            }
        
        }
        
        catch (SQLException ex) {     
            return Response
                    .status(Response.Status.BAD_GATEWAY)
                    .entity(ex.getMessage())
                    .header("token", autorizacion.encriptar())
                    .build();    
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
                filas = persistencia.delete(this.objeto, id) ;
                
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
                throw new Exception();                
            }        
        }     
        
        
        catch (SQLException ex) {     

            return Response
                    .status(Response.Status.BAD_GATEWAY)
                    .entity(ex.getMessage())
                    .header("token", autorizacion.encriptar())
                    .build();       
            
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