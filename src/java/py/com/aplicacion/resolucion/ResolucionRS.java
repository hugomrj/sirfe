/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.resolucion;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class ResolucionRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public ResolucionRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   

    
           
    
    public ResultSet  fxActualizarEstado ( Integer resolucion ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new ResolucionSQL().fxActualizarEstado(resolucion);
                 
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
    
           
    
    public ResultSet  fx_resolucion_numero ( Integer resolucion ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new ResolucionSQL().fx_resolucion_numero(resolucion);
                 
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        

    
    
}









