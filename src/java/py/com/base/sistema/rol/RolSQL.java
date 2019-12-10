
package py.com.base.sistema.rol;

import nebuleuse.ORM.sql.SentenciaSQL;

public class RolSQL {
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        sql = SentenciaSQL.select(new Rol(), busqueda);        
        
        return sql ;             
    }        
    
}




