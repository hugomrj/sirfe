/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.tipo_transferencia;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


/**
 *
 * @author hugom_000
 */


public class TipoTransferenciaRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public TipoTransferenciaRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    

    

   
    
    public ResultSet  all (  ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = "  SELECT tipo_transferencia, descripcion\n " +
                            "  FROM aplicacion.tipos_transferencias " ;

            
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
            
    
    
    
}
