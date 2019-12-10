
package py.com.aplicacion.resolucion;



import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;


public class ResolucionSQL {
    

          
    
    public String estado ( String resolucion ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Resolucion");
        readerSQL.fileExt = "estado.sql";
        
        sql = readerSQL.get( resolucion );              
        
        return sql ;                     
        
        
    }
    
    
          
    
    public String estado_transferencia ( Integer consejo, String resolucion ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Resolucion");
        readerSQL.fileExt = "estado_transferencia.sql";
        
        sql = readerSQL.get(consejo, resolucion );              
        
        return sql ;                     
        
        
    }
    
    
        

          
    
    public String fxActualizarEstado ( Integer  rendicion ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Resolucion");
        readerSQL.fileExt = "fxActualizarEstado.sql";
        
        sql = readerSQL.get( rendicion );              
        
        return sql ;                     
        
        
    }
    
        
    

          
    
    public String fx_resolucion_numero ( Integer  rendicion ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Resolucion");
        readerSQL.fileExt = "fx_resolucion_numero.sql";
        
        sql = readerSQL.get( rendicion );              
        
        return sql ;                             
        
    }
    
        
    
    
    

    
    
}




