/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.rendicion_verificacion;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
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
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;


/**
 * REST Web Service
 * @author hugo
 */
           

           
@Path("verificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class RendicionVerificacionWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    RendicionVerificacion componente = new RendicionVerificacion();       
                         
    public RendicionVerificacionWS() {
    }

    
    
    @GET
    @Path("/veridicador") 
    public Response getVerificador (
        @HeaderParam("token") String strToken
    ) {
        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                Integer usuario =  Integer.parseInt(autorizacion.token.getUser());                
                
                RendicionVerificacionJSON rvjson = new RendicionVerificacionJSON();
                
                String json = rvjson.verificador(usuario).toString();

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
    @Path("/{consejo}/{nro}/{aa}") 
    public Response list ( 
            @HeaderParam("token") String strToken,
            @PathParam ("consejo") Integer consejo, 
            @PathParam ("nro") Integer nro, 
            @PathParam ("aa") String aa,
            @QueryParam("page") Integer page) {
        
            if (page == null) {                
                page = 1;
            }

        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                Integer usuario =  Integer.parseInt(autorizacion.token.getUser());   
                
                RendicionVerificacionDAO dao = new RendicionVerificacionDAO();      
                
                String resolucion = nro.toString() + "/" + aa;
                                
                List<RendicionVerificacion> lista = dao.list(page, usuario, consejo, resolucion);                
                
                
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
                autorizacion.actualizar();                
                
                
                this.componente = (RendicionVerificacion) persistencia.filtrarId(this.componente, id);  
                //this.componente = (RendicionVerificacion) new ObjetoGastoDAO().filtrarId(id);  
                
                
                
                if (this.componente == null){
                    this.status = Response.Status.NO_CONTENT;                           
                }

                
                return Response
                        .status( this.status )
                        .entity(this.componente)
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
      
        
 
    @PUT    
    @Path("/{id}")    
    public Response edit( 
            @HeaderParam("token") String strToken,
            @PathParam ("id") Integer id, 
             String json ) {
           
        try {                    


            if (autorizacion.verificar(strToken))
            {                


                Integer usuario =  Integer.parseInt(autorizacion.token.getUser());                  
                autorizacion.actualizar();                    
                
                Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                                

                
                    RendicionVerificacion req = gsonf.fromJson(json, RendicionVerificacion.class);        
                    req.setVerificacion(id);       
                    
                    
                    RendicionVerificacionJSON j = new RendicionVerificacionJSON();            
                    Integer verireg = j.getVerificadoRendicion(id) ;
                    
                    if (verireg.equals(usuario)){                        
                        req = (RendicionVerificacion) persistencia.update(req);   
                       
                                                
                        // actualiza resoluciones
                        
                        ResolucionDAO dao = new ResolucionDAO();     
                        dao.fxActualizarEstado( req.getRendicion().getTransferencia() );
                        
                        
                        TransferenciaFondoDAO tdao = new TransferenciaFondoDAO();     
                        tdao.fxActualizarEstadoTransferenciaConsejo(req.getRendicion().getTransferencia());
                                        
                        
                    }
                    
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
 
    
    
 

    
    
    

    
}