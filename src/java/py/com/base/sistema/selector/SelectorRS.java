/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.selector;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.sql.BasicSQL;
import nebuleuse.ORM.xml.Global;
//import nebuleuse.ORM.ListMap;

/**
 *
 * @author hugom_000
 */


public class SelectorRS  {
        
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;          
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
        public Integer totalRegistros = 0;
        
    
    public SelectorRS ( ) throws IOException, SQLException  {
        conexion.conectar();  
        statement = conexion.getConexion().createStatement();              
    }
   
    
    
    public ResultSet  ListaRecursiva ( Integer codigo ) throws Exception {
            
        
            String sql = new SelectorSQL().ListaRecursiva( codigo );
            resultset = statement.executeQuery(sql);
            
            conexion.desconectar();    
            return resultset;
                 
    }
    


    
    public ResultSet  ListaAll (  ) throws Exception {
            
        
            String sql = new SelectorSQL().ListaAll();
            resultset = statement.executeQuery(sql);

            conexion.desconectar();    
            return resultset;
                 
    }
        
    
    
    
    /*
    
    public ResultSet  Lista ( String buscar, Integer page )
            throws Exception {

            page = (page==0) ? 1 : page;
        
            page = (lineas * page) - lineas ;        
            String limite_offset = "  limit " + lineas + " "+ 
                    " offset " + page  ;
            
            statement = conexion.getConexion().createStatement();      
            
            String sql = new SelectorSQL().listaSimple();
            
            //this.totalRegistros =  BasicSQL.cont_registros(conexion, sql);
                
            //sql = sql + limite_offset ;
            resultset = statement.executeQuery(sql);     

            conexion.desconectar();                
            return resultset;                 
    }
    */
    
    
    
    
    
}
