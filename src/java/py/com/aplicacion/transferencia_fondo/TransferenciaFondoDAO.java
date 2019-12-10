/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.transferencia_fondo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import py.com.aplicacion.rendicion_gasto.RendicionGasto;
import py.com.aplicacion.rendicion_gasto.RendicionGastoDAO;
import py.com.aplicacion.rendicion_gasto.RendicionGastoExt;
 


public class TransferenciaFondoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public TransferenciaFondoDAO ( ) throws IOException  {
    }
      
    
    public List<TransferenciaFondo>  list (Integer page, Integer consejo) {
                
        List<TransferenciaFondo>  lista = null;        
        try {                        
                       
            TransferenciaFondoRS rs = new TransferenciaFondoRS();            
            lista = new Coleccion<TransferenciaFondo>().resultsetToList(
                    new TransferenciaFondo(),
                    rs.list(page, consejo)
            );     
            
            this.total_registros = rs.total_registros  ;            
    
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
      
    
    
    public List<TransferenciaFondo>  list (Integer page) {
                
        List<TransferenciaFondo>  lista = null;        
        try {                        
                       
            TransferenciaFondoRS rs = new TransferenciaFondoRS();            
            lista = new Coleccion<TransferenciaFondo>().resultsetToList(
                    new TransferenciaFondo(),
                    rs.list(page)
            );     
            
            this.total_registros = rs.total_registros  ;            
    
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
      
    

    
    public List<TransferenciaFondo>  search (Integer page, String busqueda, Integer consejo) {
                
        List<TransferenciaFondo>  lista = null;
        
        try {                                   

            TransferenciaFondoRS rs = new TransferenciaFondoRS();
            lista = new Coleccion<TransferenciaFondo>().resultsetToList(
                    new TransferenciaFondo(),
                    rs.search(page, busqueda, consejo)
            );            
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      


    
    
    public List<TransferenciaFondo>  search (Integer page, String busqueda) {
                
        List<TransferenciaFondo>  lista = null;
        
        try {                                   

            TransferenciaFondoRS rs = new TransferenciaFondoRS();
            lista = new Coleccion<TransferenciaFondo>().resultsetToList(
                    new TransferenciaFondo(),
                    rs.search(page, busqueda)
            );            
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }          




    
    
    public TransferenciaFondo  get (Integer transferencia, Integer consejo) {
                
        TransferenciaFondo ret = new TransferenciaFondo();       
        
        try {                       
            
            ret = (TransferenciaFondo) persistencia.filtrarId(ret, transferencia );          
            
                if (ret.getConsejo().getCod() == consejo)
                {
                
                    RendicionGastoDAO rg = new RendicionGastoDAO();
                    List<RendicionGastoExt> detalles = rg.coleccion(transferencia);
                    ret.setRendiciones(detalles);
                    
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();  
                    String json = gson.toJson( ret );                       

                }            

        }         
        catch (Exception ex) {                        
            ret = null;
            throw new Exception(ex);            
        }
        finally
        {
            return ret ;          
        }
    }          
    
        
    
    
    
    
    
       
    
    public TransferenciaFondo filtrarNumeroAgno (String nro_aa, Integer consejo) throws Exception {      

          TransferenciaFondo obj = new TransferenciaFondo();  

          String sql = new TransferenciaFondoSQL().filtrarNumeroAgno(nro_aa, consejo);
          
          obj = (TransferenciaFondo) persistencia.sqlToObject(sql, obj);

          return obj;          

      }
         
    
    
    
    
    
    
    
    
    
    public TransferenciaFondo  getNumeroAgno (String nro_aa, Integer consejo) {
                
        TransferenciaFondo ret = new TransferenciaFondo();       
        
        try { 

            
            
                RendicionGastoDAO rg = new RendicionGastoDAO();
                List<RendicionGastoExt> detalles = rg.coleccionNumeroAgnoExt(nro_aa, consejo);
            
                

                //Integer transferencia = detalles.get(0).getTransferencia();                
                ret = new TransferenciaFondoDAO().filtrarNumeroAgno(nro_aa, consejo);
                
                if (detalles != null){                
                  
                    ret.setRendiciones(detalles);
                }
                    
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();  
                String json = gson.toJson( ret );                       
             

        }         
        catch (Exception ex) {                        
            ret = null;
            throw new Exception(ex);            
        }
        finally
        {
            return ret ;          
        }
    }          
    
    
    
    
              
    public TransferenciaFondo insert (TransferenciaFondo transferenciafondo) 
            throws SQLException, Exception  {      

            TransferenciaFondo obj = new TransferenciaFondo();  
            Persistencia persistencia = new Persistencia();  
          
          //  transferenciafondo.setSaldo_recibido(transferenciafondo.getTotal_depositado());
            transferenciafondo.setTotal_rendicion(0L);
            
            
        try 
        {
            obj = (TransferenciaFondo) persistencia.insert(transferenciafondo);
        } 
        catch (SQLException ex) 
        {
            throw new SQLException(ex);
        }
          
          return obj;          

      }
             
    
    
    

    public List<TransferenciaFondo>  list_resolucion (Integer page)  {
                
        List<TransferenciaFondo>  lista = null;        
        try {                        
             
            
            
            TransferenciaFondoRS rs = new TransferenciaFondoRS();            
            
            
            lista = new Coleccion<TransferenciaFondo>().resultsetToList(
                    new TransferenciaFondo(),
                    rs.list_resolucion(page)
            );
            
            
            
            this.total_registros = rs.total_registros  ;            
            
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
          
    
    
    
    
    public TransferenciaFondo fxActualizarEstadoTransferenciaConsejo (Integer  transferencia) 
            throws IOException, Exception {
                
        
        TransferenciaFondo  transferenciafondo = new TransferenciaFondo();        
        TransferenciaFondoRS rs = new TransferenciaFondoRS();   
        
        ResultSet resultset = rs.fxActualizarEstadoTransferenciaConsejo(transferencia);
        
        resultset.next();
        //System.out.println( resultset.getString("prueba2") );
        resultset.close();        
        
        return transferenciafondo ;          
        
    }      

    
        
    
    
     
    
    
          
        
}
