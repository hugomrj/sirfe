
package py.com.consulta;

import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class ConsultaSQL {
    
    


    
    public String consulta001  ( 
            String fecha_desde, String fecha_hasta,
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
    ) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Consulta");
        readerSQL.fileExt = "consulta001.sql";

        sql = readerSQL.get(  
                fecha_desde, fecha_hasta,
                dpto_desde, dpto_hasta,
                consejo_desde, consejo_hasta);              
        
        
        return sql ;                     
        
        
    }
    
    
    public String consulta002  (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
    ) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Consulta");
        readerSQL.fileExt = "consulta002.sql";

        sql = readerSQL.get(  
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta
        );              
        
        return sql ;                     
    }
    
    
    
    
    public String consulta003  (
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
    ) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Consulta");
        readerSQL.fileExt = "consulta003.sql";

        sql = readerSQL.get(  
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta
        );                      
        
        return sql ;                     
    }
    
    
    
    
    public String consulta004  (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
    ) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Consulta");
        readerSQL.fileExt = "consulta004.sql";

        sql = readerSQL.get(  
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta
        );              
        
        return sql ;                     
    }
    
    
    
    
                
    
}




