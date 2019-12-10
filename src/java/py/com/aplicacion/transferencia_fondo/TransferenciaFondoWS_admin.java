/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.transferencia_fondo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.resolucion.ResolucionDAO;


@Path("transferenciasfondos/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class TransferenciaFondoWS_admin {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    TransferenciaFondo com = new TransferenciaFondo();       
                         
    public TransferenciaFondoWS_admin() {
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
                
                TransferenciaFondoDAO dao = new TransferenciaFondoDAO();                
                
                List<TransferenciaFondo> lista = dao.list(page);   
                
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
                
                q = q.replaceAll(" ", "%");
                autorizacion.actualizar();                                
                
                TransferenciaFondoDAO dao = new TransferenciaFondoDAO();
                
                List<TransferenciaFondo> lista = dao.search(page, q);
                String json = gson.toJson( lista );     
                
                return Response
                        .status(Response.Status.OK)
                        .entity(json)
                        .header("token", autorizacion.encriptar())
                        .header("total_registros", dao.total_registros )
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
                            
                this.com = (TransferenciaFondo) persistencia.filtrarId(this.com, id);                                  
                
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
      
        
    

 
    @POST
    public Response add( 
            @HeaderParam("token") String strToken,
             String json ) {
           
        try {                    
            
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                TransferenciaFondo req = gsonf.fromJson(json, TransferenciaFondo.class);         

                JsonObject jsonobject = new Gson().fromJson(json, JsonObject.class);            
                String var = jsonobject.get("consejosalud").getAsJsonObject().get("consejo").toString()   ;            

                ConsejoSalud consejosalud = new ConsejoSalud();
                consejosalud.setCod( Integer.parseInt(var) );                
                req.setConsejo(consejosalud);

                
                TransferenciaFondo fondo = new TransferenciaFondo();
                //fondo = (TransferenciaFondo) persistencia.insert(req);
                fondo = new TransferenciaFondoDAO().insert(req);

                new ResolucionDAO().fx_resolucion_numero( fondo.getTransferencia() );      
                        
                        
                        
                
                
                
                if (fondo == null){
                    throw new Exception();                
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(fondo)
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
                    .header("token", autorizacion.encriptar())
                    .build();                                        
            }              
        
        
        
    }    
 
    
        
    

         

    @PUT    
    @Path("/{id}")    
    public Response edit (    
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id, 
            String json
    ) {
        
            
        try {                    
       
            
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                TransferenciaFondo req = gsonf.fromJson(json, TransferenciaFondo.class);                   
                
                req.setTransferencia(id);
                
                
                JsonObject jsonobject = new Gson().fromJson(json, JsonObject.class);            
                String var = jsonobject.get("consejosalud").getAsJsonObject().get("consejo").toString()   ;            

                ConsejoSalud consejosalud = new ConsejoSalud();
                consejosalud.setCod( Integer.parseInt(var) );                
                req.setConsejo(consejosalud);

                
                this.com = (TransferenciaFondo) persistencia.update(req);
                
                new ResolucionDAO().fx_resolucion_numero(this.com.getTransferencia() );      
            
                return Response
                        .status(Response.Status.OK)
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
                    .header("token", autorizacion.encriptar())
                    .build();                                        
            }          
        
        //:         
        
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
                filas = persistencia.delete(this.com, id) ;
                
                
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
        
    
    
    
    
    @GET
    @Path("/resoluciones") 
    public Response resoluciones_all (
        @HeaderParam("token") String strToken,
        @QueryParam("page") Integer page ) {
        
            if (page == null) {                
                page = 1;
            }        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                TransferenciaFondoJSON jsonarray = new TransferenciaFondoJSON();
                
                String json = jsonarray.list_resolucion(page).toString();
                
                
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
    


    @GET
    @Path("/resoluciones/search/") 
    public Response resoluciones_all_search (
            @HeaderParam("token") String strToken,
            @QueryParam("page") Integer page,
            @MatrixParam("q") String q
            ) {
        
        
        
            if (page == null) {                
                page = 1;
            }        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                TransferenciaFondoJSON jsonarray = new TransferenciaFondoJSON();
                
                String json = jsonarray.list_resolucion_search(page, q).toString();
                
                
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