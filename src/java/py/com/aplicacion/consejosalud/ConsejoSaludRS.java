/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.consejosalud;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class ConsejoSaludRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public ConsejoSaludRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    public ResultSet  list ( Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new ConsejoSaludSQL().list();            
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            
            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    

    
    public ResultSet  listVerificador ( Integer page, Integer usuario ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new ConsejoSaludSQL().listVerificador(usuario);            
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            
            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    

    
   
    public ResultSet  search ( Integer page, String busqueda ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsejoSaludSQL().search(busqueda);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
    
   
    public ResultSet  usuarioConsejo ( Integer usuario ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsejoSaludSQL().usuarioConsejo(usuario);
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
    

    public ResultSet  consulta01 (Integer resolucion_estado,
            String fecha_desde, String fecha_hasta ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsejoSaludSQL().consulta01( resolucion_estado, fecha_desde, fecha_hasta );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
    

    public ResultSet  consulta04 (
            String fecha_desde, String fecha_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsejoSaludSQL().consulta04(  fecha_desde, fecha_hasta,
                    consejo_desde, consejo_hasta
                    );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                      
    

    public ResultSet  consulta05 (  Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsejoSaludSQL().consulta05(  resolucion_estado, fecha_desde, fecha_hasta,
                    consejo_desde, consejo_hasta
                    );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                      
    
    
    
    
}
