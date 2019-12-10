
package py.com.aplicacion.consejosalud;

import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class ConsejoSaludSQL {
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        sql = SentenciaSQL.select(new ConsejoSalud(), busqueda);        
        
        return sql ;             
    }        
    
    
    
    
    public String usuarioConsejo ( Integer usuario )
            throws Exception {
    
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "usuarioConsejo.sql";

        sql = readerSQL.get( usuario );              
        
        return sql ;             
    }            

    
    
    public String list( ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "list.sql";

        sql = readerSQL.get(  );              
        
        return sql ;                     
        
        
    }
    


    
    public String listVerificador(  Integer usuario ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "listVerificador.sql";

        sql = readerSQL.get( usuario );              
        
        return sql ;                     
        
        
    }
    
    
    public String consulta01  ( Integer resolucion_estado,
            String fecha_desde, String fecha_hasta    ) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "consulta01.sql";

        sql = readerSQL.get(  resolucion_estado, fecha_desde, fecha_hasta  );              
        
        return sql ;                     
        
        
    }
    
    
    public String consulta04  ( 
            String fecha_desde, String fecha_hasta,
            Integer consejo_desde, Integer consejo_hasta) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "consulta04.sql";

        sql = readerSQL.get(  fecha_desde, fecha_hasta,
                consejo_desde, consejo_hasta);              
        
        
        return sql ;                     
        
        
    }
    
    
    public String consulta05  ( 
            Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer consejo_desde, Integer consejo_hasta) 
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ConsejoSalud");
        readerSQL.fileExt = "consulta05.sql";

        sql = readerSQL.get(  resolucion_estado, fecha_desde, fecha_hasta,
                consejo_desde, consejo_hasta);              
        
        
        return sql ;                     
        
        
    }
    
                
    
}




