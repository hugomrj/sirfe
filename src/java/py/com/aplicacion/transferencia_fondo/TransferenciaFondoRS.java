/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.transferencia_fondo;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;
import nebuleuse.ORM.xml.Global;


public class TransferenciaFondoRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public TransferenciaFondoRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    
    
    
    
    public ResultSet  list ( Integer page, Integer consejo ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            
            String sql = "";
            
            ReaderT readerSQL = new ReaderT("TransferenciaFondo");
            readerSQL.fileExt = "list.sql";
            
            sql = readerSQL.get( consejo );            
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
     
    
    public ResultSet  list ( Integer page) throws Exception {

            statement = conexion.getConexion().createStatement();      
                      
            
            String sql = new TransferenciaFondoSQL().lista_admin();
          
            
                        
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;              
            
    }
    
    
        
    
   
    public ResultSet  search ( Integer page, String busqueda, Integer consejo ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new TransferenciaFondoSQL().search(busqueda, consejo);

            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            
            resultset = statement.executeQuery(sql);     
            

            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
       
    
    
   
    public ResultSet  search ( Integer page, String busqueda ) 
            throws Exception {

        
            statement = conexion.getConexion().createStatement();                  
            String sql = new TransferenciaFondoSQL().search(busqueda);

            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            
            resultset = statement.executeQuery(sql);     
            

            
            conexion.desconectar();                
            return resultset;                 
            
    }
   
       
        
    
    
    public ResultSet  list_resolucion ( Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = 
                    "  SELECT resolucion_numero\n" +
                    "  FROM aplicacion.transferencias_fondos\n" +
                    "  group by resolucion_numero " ;
            
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);            

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
        
    
    
    
    public ResultSet  list_resolucion_search ( Integer page, String busqueda ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            
            busqueda = busqueda.replace(" ", "%");
            
            String sql = 
                    "  SELECT resolucion_numero\n" +
                    "  FROM aplicacion.transferencias_fondos\n" +
                    "  WHERE resolucion_numero like '%"+busqueda+"%'" +
                    "  group by resolucion_numero " ;
           

            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);            

            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
            
    
    
    
    
           
    
    public ResultSet  fxActualizarEstadoTransferenciaConsejo ( Integer trasferencia ) throws Exception {

            statement = conexion.getConexion().createStatement();                  
            String sql = new TransferenciaFondoSQL().fxActualizarEstadoTransferenciaConsejo(trasferencia);
                 
            resultset = statement.executeQuery(sql);     
            conexion.desconectar();                
            return resultset;                 
            
    }
    
            
    
    

    
    
}
