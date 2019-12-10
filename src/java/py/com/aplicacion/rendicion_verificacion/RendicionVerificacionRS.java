/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_verificacion;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class RendicionVerificacionRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public RendicionVerificacionRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
           
    
    public ResultSet  verificador ( Integer usuario ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new RendicionVerificacionSQL().verificador(usuario);
                 
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
               
    
    public ResultSet  getVerificadoRendicion ( Integer verificacion ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new RendicionVerificacionSQL().getVerificadoRendicion(verificacion);
                 
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
    
    
    
    
    
    
    
    
    public ResultSet  list ( Integer page, Integer usuario, Integer consejo, String resolucion ) 
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionVerificacionSQL().list(usuario, consejo, resolucion);                        
           
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);       
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
    
    

    
    
}









