/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.base.sistema.usuario;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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


@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class UsuarioWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    
    Usuario usuario = new Usuario();       
                         
    public UsuarioWS() {
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
                
                UsuarioDAO dao = new UsuarioDAO();
                
                List<Usuario> lista = dao.list(page);                
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
                
                UsuarioDAO dao = new UsuarioDAO();
                
                List<Usuario> lista = dao.search(page, q);
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
    @Path("/{usu}")
    public Response getUsuario(     
            @HeaderParam("token") String strToken,
            @PathParam ("usu") Integer usu ) {
                     
        try 
        {                  
            if (autorizacion.verificar(strToken))
            {
                autorizacion.actualizar();                
                this.usuario = (Usuario) persistencia.filtrarId(usuario, usu);  
        
                if (this.usuario == null){
                    this.status = Response.Status.NO_CONTENT;                           
                }else
                {
                    this.usuario.setClave("secret");
                }
                
                return Response
                        .status( this.status )
                        .entity(this.usuario)
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
    public Response addUsuario( 
            @HeaderParam("token") String strToken,
            Usuario usuarioReq ) {
                     
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                this.usuario = new UsuarioDAO().insert(
                                                            usuarioReq.getCuenta(), 
                                                            usuarioReq.getClave());
                             
                if (this.usuario == null){
                    System.out.println("usuario vacio");
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(this.usuario)
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
    @Path("/{usu}")    
    public Response editUsuario (
            Usuario usuarioReq,
            @HeaderParam("token") String strToken,
            @PathParam ("usu") Integer usu) {
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
            
                this.usuario = new UsuarioDAO().update(
                                                                usuarioReq, 
                                                                usu);
                
                return Response
                        .status(Response.Status.OK)
                        .entity(this.usuario)
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
                    .header("token", null)
                    .build();                    
    
        }        
    }    
    
    
    
    
    
    
    
    @DELETE  
    @Path("/{id}")    
    public Response deleteUsuario (            
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id) {
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
            
                Integer filas = 0;
                //filas = new UsuarioDAO().delete(usu);
                
                filas = persistencia.delete(usuario, id) ;
                
                
                
                
                
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
        

    
    
    
    
    @POST
    @Path("/login")
    public  Response login ( String json ) {
        
                    
        try {

                Usuario usu = new UsuarioDAO().setJson(json);

                usu  = new UsuarioDAO().login(
                            usu.getCuenta(), 
                            usu.getClave()
                );
    
            
            if (usu == null)
            {                
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization Required")
                        .header("token", null)
                        .build();                                        
            }
            else
            {

                
                UsuarioExt usuarioext = new UsuarioExt();          
                usuarioext.setUsuario(usu.getUsuario());
                usuarioext.extender();
                
                usu.setClave("secret");                
                autorizacion.newTokken(usu.getUsuario().toString(), usu.getCuenta().trim()) ;
                
                autorizacion.token.setConsejo(usuarioext.getConsejo());

                
                new UsuarioDAO().set_tokenDB(
                        usu.getUsuario().toString(), 
                        autorizacion.token.getIat()
                );
                
                
                return Response
                        .status(Response.Status.OK)
                        .entity(null)
                        .header("token", autorizacion.encriptar())
                        .build();                        
            }                      
        } 
        
        catch (Exception ex) {
            
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();                                        
        }
    }
       


    
    
    
    
    
    
    
    @GET    
    @Path("/controlurl") 
    public Response cod ( 
            @HeaderParam("token") String strToken,
            @HeaderParam("path") String strPath
            ) {
        
        try {  
                       
            if (autorizacion.verificar(strToken))
            {                  
                UsuarioDAO dao = new UsuarioDAO();
                Integer usu = Integer.parseInt( autorizacion.token.getUser() );
                
               //String succ = autorizacion.token.getSucursal().getSucursal().toString() + " " + 
                 //      autorizacion.token.getSucursal().getNombre() ;
      
               
                if ( dao.IsControlPath(usu, strPath ) )
                {
                    return Response
                            .status(Response.Status.OK)           
                            .header("token", strToken)
                            //.header("succ", succ)
                            .build();                     
                }
                else
                {                
                    return Response
                            .status(Response.Status.UNAUTHORIZED)
                            .build();                            
                } 
            }
            else
            {  
                throw new Exception();
            }        
        }     
        catch (Exception ex) {
                return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("0")
                    .build();                                      
        }      
    }    
        
    
    
    
      
     
    @PUT    
    @Path("/cambiopass")    
    public Response cambiopass (            
            @HeaderParam("token") String strToken,
            String json
            ) {
            
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    

            JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
            
            String old = convertedObject.get("old").getAsString() ;
            String jnew = convertedObject.get("new").getAsString() ;
            String usu = autorizacion.token.getUser();
            
                Integer i = new UsuarioDAO().cambiarPass(usu, old, jnew);
            
                
                if (i == 0)
                {
                    
                    return Response
                            .status(Response.Status.NO_CONTENT)
                            .entity(null)
                            .header("token", autorizacion.encriptar())
                            .build();                                            
                }
                else
                {   
                    return Response
                            .status(Response.Status.OK)
                            .entity(this.usuario)
                            .header("token", autorizacion.encriptar())
                            .build();                           

                }            

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
                    //.entity("Error")
                    .entity(ex.getMessage())
                    .header("token", strToken)
                    .build();                    
    
        }        
    }    
    
    
    
    
        
    

    
    
    
}