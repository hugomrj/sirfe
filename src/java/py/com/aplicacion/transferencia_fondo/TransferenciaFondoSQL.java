
package py.com.aplicacion.transferencia_fondo;


import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;


public class TransferenciaFondoSQL {
    
    
    public String search ( String busqueda, Integer consejo )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("TransferenciaFondo");
        reader.fileExt = "search.sql";
        
        sql = reader.get( busqueda, consejo );    
        
        return sql ;             
    }        
        
    
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("TransferenciaFondo");
        reader.fileExt = "search_admin.sql";
        
        sql = reader.get( busqueda );    
        
        return sql ;             
    }        
        
    
        
    
    
    
    
    public String filtrarNumeroAgno ( String nro_aa, Integer consejo )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("TransferenciaFondo");
        reader.fileExt = "filtrarNumeroAgno.sql";
        sql = reader.get( nro_aa, consejo );          
    
        return sql ;             
    }        

    
    
    
    public String lista_admin() throws IOException {
        
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("TransferenciaFondo");
        reader.fileExt = "list_admin.sql";
        
        sql = reader.get( );          
    
        return sql ;                     
    }
        
        
    
          
    
    public String fxActualizarEstadoTransferenciaConsejo ( Integer  transferencia ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("TransferenciaFondo");
        readerSQL.fileExt = "fxActualizarEstadoTransferenciaConsejo.sql";
        
        sql = readerSQL.get( transferencia );              
        
        return sql ;                     
        
        
    }
    
           
    
    
}




