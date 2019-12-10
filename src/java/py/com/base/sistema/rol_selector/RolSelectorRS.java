/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.base.sistema.rol_selector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


/*
 * @author hugom_000
 */


public class RolSelectorRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public RolSelectorRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    public ResultSet  list ( Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = SentenciaSQL.select(new RolSelector());            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
    
    


    public ResultSet  cabRol ( Integer rol, Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RolSelectorSQL().cabRol(rol);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
              
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
                    
            return resultset;                 
            
    }
        
         
    


    public ResultSet  cabSelector ( Integer sel, Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RolSelectorSQL().cabSelector(sel);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
              
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
                    
            return resultset;                 
            
    }
        
         
                
            
   
    public ResultSet  search ( Integer page, String busqueda ) throws Exception {

            statement = conexion.getConexion().createStatement();    
            String sql = new RolSelectorSQL().search(busqueda);

            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
    
    
}
