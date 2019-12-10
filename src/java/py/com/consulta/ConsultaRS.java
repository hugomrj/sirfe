/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.consulta;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class ConsultaRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public ConsultaRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   



    public ResultSet  consulta001 (
            String fecha_desde, String fecha_hasta,
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsultaSQL().consulta001(  fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta
                    );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                      
    
   
    public ResultSet  consulta002 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsultaSQL().consulta002 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta                   
            );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                      
    
   
    public ResultSet  consulta003 (            
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsultaSQL().consulta003 (                    
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta    
            );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                      
    
    
   
    public ResultSet  consulta004 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new ConsultaSQL().consulta004 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta                   
            );
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
                 
    
    
    
    
    
}
