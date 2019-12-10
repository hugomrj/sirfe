/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.rendicion_gasto;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.sql.SQLException;
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
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.objeto_gasto.ObjetoGasto;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;



/**
 * REST Web Service
 * @author hugo
 */


@Path("rendicionesgastos/session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class RendicionGastoWS_session {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    RendicionGasto componente = new RendicionGasto();       
                         
    public RendicionGastoWS_session() {
    }

    
  

         
    /*
    
    @GET
    @Path("/{id}/rendiciones")
    public Response get(     
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id ) {
                 
        try 
        {                  
                        
            if (autorizacion.verificar(strToken))          
            {               
                                                
                Integer consejo = autorizacion.token.getConsejo();
                autorizacion.actualizar();                
                
                               
                TransferenciaFondo tranferencia = new TransferenciaFondo();               
                tranferencia = new TransferenciaFondoDAO().get(id, consejo);
                
                
                String json = gson.toJson( tranferencia );                       

                    return Response
                            .status( Response.Status.OK )
                            .entity(json)
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
                    .entity(ex.getMessage())
                    .header("token", autorizacion.encriptar())
                    .build();                                        
        }        
        
    }    
      
    

    */
    
    @GET
    @Path("/transferencia/{nro}/{aa}")
    public Response getNumeroAgno(     
            @HeaderParam("token") String strToken,
            @PathParam ("nro") Integer nro, 
            @PathParam ("aa") String aa ) {
        try 
        {                  
            
            
            if (autorizacion.verificar(strToken))          
            {               
                                                
                Integer consejo = autorizacion.token.getConsejo();
                autorizacion.actualizar();                                
                
                TransferenciaFondo tranferencia = new TransferenciaFondo();      
                
                String resolucion = nro.toString() + "/" + aa;
                
                tranferencia = new TransferenciaFondoDAO().getNumeroAgno( resolucion, consejo );
                                
                String json = gson.toJson( tranferencia );                       

                    return Response
                            .status( Response.Status.OK )
                            .entity(json)
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
                    .entity(ex.getMessage())
                    .header("token", autorizacion.encriptar())
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
                Integer consejo = autorizacion.token.getConsejo();
                ConsejoSalud consejosalud = new ConsejoSalud();
                consejosalud.setCod(consejo);
                
                autorizacion.actualizar();    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                
                RendicionGasto req = gsonf.fromJson(json, RendicionGasto.class);        
                req.setConsejo(consejosalud);
                
                
                JsonObject jsonobject = new Gson().fromJson(json, JsonObject.class);                     
                String varO = jsonobject.get("objetogasto").getAsJsonObject().get("objeto").toString();                      
                ObjetoGasto objeto = new ObjetoGasto();
                objeto.setObjeto( Integer.parseInt(varO) );
                req.setObjeto(objeto);
                                
                //req = (RendicionGasto) persistencia.insert(req);
                req = (RendicionGasto) new RendicionGastoDAO().insert(req);
                                
                if (req == null){
                    throw new Exception();                
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(req)
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
                    .entity("Error de servidor")
                    .header("token", autorizacion.encriptar())
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
                
                Integer consejo = autorizacion.token.getConsejo();
                ConsejoSalud consejosalud = new ConsejoSalud();
                consejosalud.setCod(consejo);
                
                autorizacion.actualizar();                
               
                //this.componente = new RendicionGastoDAO().filtrarId(id, consejo);
                
                
                RendicionGastoExt comExt = new RendicionGastoExt();     
                comExt = (RendicionGastoExt) new RendicionGastoDAO().filtrarId_Ext(id, consejo);
                
                
                
                if (this.componente == null){
                    this.status = Response.Status.NO_CONTENT;                           
                }

                
                return Response
                        .status( this.status )
                        .entity(comExt)
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
      
        
    
 
    @PUT    
    public Response edit( 
            @HeaderParam("token") String strToken,
             String json ) {
           
        try {                    


            if (autorizacion.verificar(strToken))
            {                
                Integer consejo = autorizacion.token.getConsejo();
                ConsejoSalud consejosalud = new ConsejoSalud();
                consejosalud.setCod(consejo);
                
                autorizacion.actualizar();    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                
                RendicionGasto req = gsonf.fromJson(json, RendicionGasto.class);        
                req.setConsejo(consejosalud);
                                
                
                JsonObject jsonobject = new Gson().fromJson(json, JsonObject.class);                     
                String varO = jsonobject.get("objetogasto").getAsJsonObject().get("objeto").toString();                      
                ObjetoGasto objeto = new ObjetoGasto();
                objeto.setObjeto( Integer.parseInt(varO) );
                req.setObjeto(objeto);                             
                
                
                //req = (RendicionGasto) persistencia.insert(req);
                req = (RendicionGasto) new RendicionGastoDAO().update( req );


                
                if (req == null){
                    throw new Exception();                
                }
                
                return Response
                        .status(Response.Status.OK)
                        .entity(req)
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
                    .entity("Error de servidor")
                    .header("token", autorizacion.encriptar())
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
                Integer consejo = autorizacion.token.getConsejo();
                ConsejoSalud consejosalud = new ConsejoSalud();
                autorizacion.actualizar();    
            
                Integer filas = 0;
                filas = persistencia.delete(this.componente, id) ;                
                
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