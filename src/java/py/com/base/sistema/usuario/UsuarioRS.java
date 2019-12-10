/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.xml.Global;
//import nebuleuse.ORM.ListMap;

/**
 *
 * @author hugom_000
 */


public class UsuarioRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer total_registros = 0;
        
    
    public UsuarioRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    public ResultSet  list ( Integer page ) throws Exception {

            statement = conexion.getConexion().createStatement();      
            
            String sql = new UsuarioSQL().list();
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);
            
            sql = sql + BasicSQL.limite_offset(page, lineas);

            resultset = statement.executeQuery(sql);     

            conexion.desconectar();                
            return resultset;                 
            
    }
    
    
    
    public ResultSet  search ( Integer page, String busqueda ) throws Exception {
        
            statement = conexion.getConexion().createStatement();      
            
            String sql = new UsuarioSQL().search(busqueda);
            
            this.total_registros =  BasicSQL.cont_registros(conexion, sql);            
            sql = sql + BasicSQL.limite_offset(page, lineas);
            resultset = statement.executeQuery(sql);     

            conexion.desconectar();                
            return resultset;                 

    }
    
    
    public ResultSet  controlpath ( Integer usu, String path ) throws Exception {
        
            //Integer retornar = 0;
        
            statement = conexion.getConexion().createStatement();                  
            String sql = new UsuarioSQL().controlurl(usu, path);            
            resultset = statement.executeQuery(sql);     
            
            /*
            if (resultset.next()){ 
                retornar = 1;
            }            
            */
            
            conexion.desconectar();                
            return resultset;                 

    }    
    
    
    
}


    
    
/*

        String sql = "";                                 
        
        busqueda = busqueda.replace(" ", "%") ;

        ReaderSQL readerSQL = new ReaderSQL("UsuarioRol");
        readerSQL.fileSQL = "search.sql";

        sql = readerSQL.getSQL( busqueda );            
        
        return sql ;             
        


*/