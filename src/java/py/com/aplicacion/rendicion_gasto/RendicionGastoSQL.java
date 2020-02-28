
package py.com.aplicacion.rendicion_gasto;



import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;


public class RendicionGastoSQL {
    
    
    
    public String coleccion ( Integer numero )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "coleccion.sql";
        sql = reader.get( numero );          
    
        return sql ;             
    }        
        
    
    
    
    
    
    
    public String coleccionNumeroAgno ( String nro_aa, Integer consejo )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "coleccionNumeroAgno.sql";
        sql = reader.get( nro_aa, consejo );          
    
        return sql ;             
    }        
        
    
    
    
    
    public String list_resolucion ( Integer usuario )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "list_resolucion.sql";
        sql = reader.get( usuario );          
    
        return sql ;             
    }        
    
        
    
    
    public String list_resolucion_consejo ( Integer usuario, Integer consejo )
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "list_resolucion_consejo.sql";
        sql = reader.get( usuario, consejo );          
    
        return sql ;             
    }        
        

    
    public String isExist (String par_resolucion, Integer par_consejo)  
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "isExist.sql";
        sql = reader.get( par_resolucion, par_consejo );          
    
        return sql ;             
    }        
            
    

    
    public String anexoB09 (String par_resolucion, Integer par_consejo)  
            throws Exception {
    
        String sql = "";                                 
        
        ReaderT reader = new ReaderT("RendicionGasto");
        reader.fileExt = "anexoB09.sql";
        sql = reader.get( par_resolucion, par_consejo );          
    
        return sql ;             
    }        
            
    
    
    
}




