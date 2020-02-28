/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_gasto;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class RendicionGastoRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public RendicionGastoRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    
    public ResultSet  coleccion ( Integer numero ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().coleccion(numero );
            
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }

        
    
    
    
    public ResultSet  coleccionNumeroAgno ( String nro_aa, Integer consejo ) 
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().coleccionNumeroAgno(nro_aa, consejo );

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }

    
    
    
    
    
    public ResultSet  sumaImporte ( Integer id ) 
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = 
                    " SELECT sum(importe) importe\n" +
                    "  FROM aplicacion.rendiciones_gastos\n" +
                    "  where transferencia  = " + id;
            
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }


    
    


       
        
    
    
    public ResultSet  list_resolucion ( Integer page, Integer usuario ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().list_resolucion(usuario);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);            

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
        
        
    
    
    public ResultSet  list_resolucion_consejo ( Integer page, Integer usuario,  Integer consejo ) 
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().list_resolucion_consejo(usuario, consejo);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);            

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
        
    
    
    public ResultSet  isExist (String par_resolucion, Integer par_consejo)  
            
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().isExist(par_resolucion, par_consejo);
            
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }

    

    
    public ResultSet  anexoB09 (String par_resolucion, Integer par_consejo)  
            
            throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new RendicionGastoSQL().anexoB09(par_resolucion, par_consejo);
            
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
    
    
    
    
    
    
}
