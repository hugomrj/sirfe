
package py.com.base.sistema.usuario_rol;


import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class UsuarioRolSQL {
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";            
        busqueda = busqueda.replace(" ", "%") ;
        ReaderT readerSQL = new ReaderT("UsuarioRol");
        readerSQL.fileExt = "search.sql";
        sql = readerSQL.get( busqueda );            
        
        return sql ;             
    }        
    

    public String cabUsuario ( Integer usuario )
            throws Exception {
    
        String sql = "";            
        //busqueda = busqueda.replace(" ", "%") ;
        ReaderT readerSQL = new ReaderT("UsuarioRol");
        readerSQL.fileExt = "cabUsuario.sql";
        sql = readerSQL.get( usuario );            
        
        return sql ;             
    }        


    public String cabRol ( Integer rol )
            throws Exception {
    
        String sql = "";            
        
        ReaderT readerSQL = new ReaderT("UsuarioRol");
        readerSQL.fileExt = "cabRol.sql";
        sql = readerSQL.get(rol );            
        
        return sql ;             
    }        


    
    
}




