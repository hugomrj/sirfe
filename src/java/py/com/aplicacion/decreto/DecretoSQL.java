
package py.com.aplicacion.decreto;


import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class DecretoSQL {
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        sql = SentenciaSQL.select(new Decreto(), busqueda);        
        
        return sql ;             
    }        
    
    
    
    
    

          
    
    
}




