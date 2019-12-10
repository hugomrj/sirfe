
package py.com.aplicacion.rendicion_verificacion;


import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;


public class RendicionVerificacionSQL {
    
    public String verificador ( Integer usuario )
            throws Exception {
    
        String sql = "";             
        
        ReaderT readerSQL = new ReaderT("RendicionVerificacion");
        readerSQL.fileExt = "verificador.sql";        
        sql = readerSQL.get(usuario);
        
        
        return sql ;             
    }        

    
    
    public String getVerificadoRendicion ( Integer verificacion )
            throws Exception {
    
        String sql = "";             
        
        ReaderT readerSQL = new ReaderT("RendicionVerificacion");
        readerSQL.fileExt = "getVerificadoRendicion.sql";        
        sql = readerSQL.get(verificacion);
        
        return sql ;             
    }        


          

      

    
    
    public String list( Integer usuario, Integer consejo, String resolucion ) throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("RendicionVerificacion");
        readerSQL.fileExt = "list.sql";
        
        sql = readerSQL.get( usuario, consejo, resolucion );              

        
        return sql ;                     
        
        
    }
    
    
    

    
    
}




