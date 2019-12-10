
package py.com.base.sistema.rol_selector;

import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class RolSelectorSQL {
    
    

    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";            
        busqueda = busqueda.replace(" ", "%") ;
        ReaderT readerSQL = new ReaderT("RolSelector");
        readerSQL.fileExt = "search.sql";
        sql = readerSQL.get( busqueda );  
        
        
        return sql ;             
    }        
        
    
    

    public String cabRol ( Integer rol )
            throws Exception {
    
        String sql = "";            
        
        ReaderT readerSQL = new ReaderT("RolSelector");
        readerSQL.fileExt = "cabRol.sql";
        sql = readerSQL.get( rol );            
        
        return sql ;             
    }        

    

    public String cabSelector ( Integer sel )
            throws Exception {
    
        String sql = "";            
        
        ReaderT readerSQL = new ReaderT("RolSelector");
        readerSQL.fileExt = "cabSelector.sql";
        sql = readerSQL.get( sel );            
        
        return sql ;             
    }        
    
    
    
}




