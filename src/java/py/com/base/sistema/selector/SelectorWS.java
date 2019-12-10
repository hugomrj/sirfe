/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.base.sistema.selector;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nebuleuse.ORM.Persistencia;
import nebuleuse.seguridad.Autentificacion;

/**
 * REST Web Service
 * @author hugo
 */


@Path("selectores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class SelectorWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();         
    
    Selector objeto = new Selector();    
    
    
                         
    public SelectorWS() {
    }

    
    
    /*
    @GET    
    public Response list ( 
            @HeaderParam("token") String strToken) {
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                SelectorJSON jsonarray = new SelectorJSON();
                
                String json = jsonarray.lista().toString();
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        //.header("total_registros", dao.total_registros )
                        .build();                                       
            }
            else{
                // se puede generar un response de tiempo excedido
                throw new Exception();
                
            }        
        }     
        catch (Exception ex) {
            
            System.out.println(ex.getMessage());
            
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error")
                    .header("token", null)
                    .build();                                        
        }      
    }    
    */


    @GET    
    @Path("/all")
    public Response all ( 
            @HeaderParam("token") String strToken) {
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                SelectorJSON jsonarray = new SelectorJSON();
                
                String json = jsonarray.all().toString();
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        //.header("total_registros", dao.total_registros )
                        .build();                                       
            }
            else{
                // se puede generar un response de tiempo excedido
                throw new Exception();
                
            }        
        }     
        catch (Exception ex) {
            
            System.out.println(ex.getMessage());
            
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
             String json ) {
                     
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                Selector req = gsonf.fromJson(json, Selector.class);                      
                
                this.objeto = (Selector) persistencia.insert(req);
                                             
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
                this.objeto = (Selector) persistencia.filtrarId( new Selector(), id);  
                
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
      
 
    
    

    @PUT    
    @Path("/{id}")    
    public Response edit (            
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id,
            String json) {
        

        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                Selector req = gsonf.fromJson(json, Selector.class);                     
                
                
                req.setSelector(id);
                this.objeto = (Selector) persistencia.update(req);
            
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
        catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())                    
                    .header("token", null)
                    .build();           
        }        
    }    
        
    
    @GET    
    @Path("/menu") 
    public Response cod ( 
            @HeaderParam("token") String strToken) {
        
        try {  
                       
            if (autorizacion.verificar(strToken))
            {  
                SelectorUI ui = new SelectorUI();
                
                return Response
                        .status(Response.Status.OK)
                        .entity(autorizacion.token.getUser())
                        .header("menu", ui.menu( autorizacion.token.getUser()))
                        .header("token", strToken)
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
                    .entity("0")
                    .build();                                      
        }      
    }    
        


    
}