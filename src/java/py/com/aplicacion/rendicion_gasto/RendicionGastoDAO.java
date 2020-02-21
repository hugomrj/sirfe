/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_gasto;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacion;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;
import py.com.aplicacion.verificacion_estado.VerificacionEstado;


public class RendicionGastoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public RendicionGastoDAO ( ) throws IOException  {
    }
      
    
    public List<RendicionGastoExt>  coleccion (Integer numero) {
                
        List<RendicionGastoExt>  lista = null;        
        try {                        
                        
            RendicionGastoRS rs = new RendicionGastoRS();            
            
            lista = new Coleccion<RendicionGastoExt>().resultsetToList(
                    new  RendicionGasto(), rs.coleccion(numero)
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
      
              
    public List<RendicionGastoExt>  coleccionNumeroAgnoExt (String nro_aa, Integer consejo) {
                
        List<RendicionGastoExt>  lista = null;        
        try {                        
                        
            RendicionGastoRS rs = new RendicionGastoRS();            
            
            lista = new Coleccion<RendicionGastoExt>().resultsetToList(
                    new  RendicionGastoExt(), 
                    rs.coleccionNumeroAgno(nro_aa, consejo)
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
      
    
    
    
              
    public RendicionGasto insert (RendicionGasto rendicion) throws SQLException  {      

            RendicionGasto obj = new RendicionGasto();  
            Persistencia persistencia = new Persistencia();  
                     
            
    /*        
            Long suma = new RendicionGastoDAO().sumaImporte(rendicion);
            suma = suma + rendicion.getImporte();
    */
    
            //TransferenciaFondo transferencia = new TransferenciaFondo();
            /*
            transferencia = (TransferenciaFondo) persistencia.filtrarId(
            transferencia,
            rendicion.getTransferencia());
            
            */          
    
        try {
            
            obj = (RendicionGasto) persistencia.insert(rendicion);
            
            obj.getRendicion();
            
            RendicionVerificacion renve = new RendicionVerificacion();
            renve.setVerificacion(0);
            renve.setRendicion(obj);
            
            
            VerificacionEstado ve = new VerificacionEstado();
            ve.setEstado(0);
            renve.setEstado(ve);
            renve.setComentario("");
            
            renve = (RendicionVerificacion) persistencia.insert(renve);
            // insertar 
            
            
            //transferencia.setSaldo_recibido(transferencia.getTotal_depositado() -   suma);
            //transferencia.setTotal_rendicion( suma);
            //transferencia = (TransferenciaFondo) persistencia.update(transferencia);            
            
        } 
        catch (Exception ex) 
        {
            throw new SQLException(ex);
        }
            
            
          return obj;          

      }
    
    
    
    /*    
    public Long sumaImporte (RendicionGasto rendicion) throws Exception {      
                
        Long ret = 0L;
        ResultSet rs = new RendicionGastoRS().sumaImporte(rendicion.getTransferencia());

        if (rs.next()){
            if (rs.getString("importe") != null){
                ret = Long.parseLong(rs.getString("importe"));
            }
        }
        
        return ret;          

      }
*/

    
    

    
    public RendicionGasto filtrarId( Integer id, Integer consejo ) throws Exception {      

          RendicionGasto ret = new RendicionGasto();  

          String sql = 
                    " SELECT rendicion, transferencia, resolucion_numero, tipo_comprobante, \n" +
                    "       comprobante_numero, objeto, concepto, fecha, importe, observacion, \n" +
                    "       consejo, ruc_factura, timbrado_venciomiento  \n" +
                    "  FROM aplicacion.rendiciones_gastos\n" +
                    "  where rendicion = " + id + 
                    "  and consejo =  " + consejo ;
          
          
          ret = (RendicionGasto) persistencia.sqlToObject(sql, ret);

          return ret;          

      }
         

        
    public RendicionGasto filtrarId_Ext( Integer id, Integer consejo ) throws Exception {      

          RendicionGastoExt ret = new RendicionGastoExt();  

          String sql = 
                    " SELECT rendicion, transferencia, resolucion_numero, tipo_comprobante, \n" +
                    "       comprobante_numero, objeto, concepto, fecha, importe, observacion, \n" +
                    "       consejo, ruc_factura, timbrado_venciomiento  \n" +
                    "  FROM aplicacion.rendiciones_gastos\n" +
                    "  where rendicion = " + id + 
                    "  and consejo =  " + consejo ;
          
          
          ret = (RendicionGastoExt) persistencia.sqlToObject(sql, ret);

          return ret;          

      }
         
        
    
    
    
    public RendicionGasto update (RendicionGasto rendicion) throws Exception {      

            RendicionGasto obj = new RendicionGasto();  
            Persistencia persistencia = new Persistencia();  
            
                        
                      
/*            
            Long suma = new RendicionGastoDAO().sumaImporte(rendicion);
            suma = suma + rendicion.getImporte();
*/ 


/*
            TransferenciaFondo transferencia = new TransferenciaFondo();
            transferencia = (TransferenciaFondo) persistencia.filtrarId(
                    transferencia, 
                    rendicion.getTransferencia());
   */                                 

            
            obj = (RendicionGasto) persistencia.update(rendicion);      
            
            /*
            transferencia.setTotal_rendicion( suma);            
            transferencia = (TransferenciaFondo) persistencia.update(transferencia);            
            */
            
          return obj;          

      }
    
    
    
    
    public boolean isExist (String par_resolucion, Integer par_consejo) throws IOException, SQLException, Exception{      

        boolean ret = false;
        
        RendicionGastoRS rs = new RendicionGastoRS();  
        
        ResultSet resultset = rs.isExist(par_resolucion, par_consejo);
        
        if (resultset.next()){
            ret = true;
        }
        
        return ret;          

      }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
