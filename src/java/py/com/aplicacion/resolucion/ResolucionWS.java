/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.aplicacion.resolucion;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
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
           

           
@Path("resoluciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)



public class ResolucionWS {

    @Context
    private UriInfo context;    
    private Persistencia persistencia = new Persistencia();   
    private Autentificacion autorizacion = new Autentificacion();
    private Gson gson = new Gson();          
    private Response.Status status  = Response.Status.OK;
    
    Resolucion componente = new Resolucion();       
                         
    public ResolucionWS() {
    }

    
    
    @GET
    @Path("/estado/{nro}/{aa}") 
    public Response estadores (
                @PathParam ("nro") Integer nro, 
                @PathParam ("aa") String aa,
                @HeaderParam("token") String strToken
        ) {
        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                                
                String resolucion = nro.toString() + "/" + aa;
                
                ResolucionDAO dao = new ResolucionDAO();                
                
                componente = dao.estado(resolucion);
                
                String json = gson.toJson( componente );   
                
           
                if (this.componente == null){
                    status = status.NO_CONTENT;
                }
                                
                
                
                
                return Response
                        .status( status )
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
    @Path("/estado/transferencia/{consejo}/{nro}/{aa}") 
    public Response estadotrans (
                @PathParam ("consejo") Integer consejo, 
                @PathParam ("nro") Integer nro, 
                @PathParam ("aa") String aa,
                @HeaderParam("token") String strToken
        ) {
        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                                
                String resolucion = nro.toString() + "/" + aa;
                
                ResolucionDAO dao = new ResolucionDAO();                
                
                componente = dao.estado_transferencia(consejo, resolucion);
                
                String json = gson.toJson( componente );   
                
           
                if (this.componente == null){
                    status = status.NO_CONTENT;
                }
                                
                
                
                
                return Response
                        .status( status )
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
    @Path("/estados/all") 
    public Response all (
        @HeaderParam("token") String strToken
    ) {
        
        
        try {                    
           
            if (autorizacion.verificar(strToken))
            {                
                autorizacion.actualizar();                                
                
                
                /*   
                
                ResolucionDAO dao = new ResolucionDAO();                
                List<Resolucion> lista = dao.all();                
                String json = gson.toJson( lista );   
                
                */
                
                
                String json = new ResolucionJSON().resolucion_estados();   
                
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


