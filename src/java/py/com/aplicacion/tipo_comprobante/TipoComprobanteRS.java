/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.tipo_comprobante;

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


public class TipoComprobanteRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public TipoComprobanteRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    

    

   
    
    public ResultSet  all (  ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = "SELECT tipo_comprobante, descripcion\n" +
                            "  FROM aplicacion.tipos_comprobantes\n" +
                            "  order by tipo_comprobante" ;

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
            
    
    
    
}
