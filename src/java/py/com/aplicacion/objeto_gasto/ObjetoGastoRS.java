/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.objeto_gasto;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class ObjetoGastoRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public ObjetoGastoRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    
    
    public ResultSet  list ( Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new ObjetoGastoSQL().list();
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
    

    
    
   
    public ResultSet  search ( Integer page, String busqueda ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            
            String sql = new ObjetoGastoSQL().search(busqueda);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   

    
    
    
        
    public ResultSet  consulta02 (Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer obj_desde, Integer obj_hasta
            ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();   
            
            
            String sql = new ObjetoGastoSQL().consulta02 ( 
                        resolucion_estado, fecha_desde, fecha_hasta,
                        obj_desde,  obj_hasta
                    );
            
            
            resultset = statement.executeQuery(sql);     
            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
          
    
}
