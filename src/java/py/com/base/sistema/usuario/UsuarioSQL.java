/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario;

//import py.com.base.sistema.selector.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;




/**
 *
 * @author hugo
 */
public class UsuarioSQL {
    
    public String login(String cuenta, String pass)
            throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "login.sql";
        
        sql = readerSQL.get( cuenta, pass );
        return sql ;
             
    }              
    
    
    
    public String login(String cuenta, String pass, Integer succ)
            throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "loginSucursal.sql";
        
        sql = readerSQL.get( cuenta, pass, succ );
        return sql ;
             
    }              
    
    
        
    
    
    
    
    
    
    
    public String list ( )
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "select.sql";
        
        sql = readerSQL.get( 1, "dos", 3 );
        return sql ;             
    }        
    
    
    
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        //sql = SentenciaSQL.select( new Usuario(), busqueda );        

        busqueda = busqueda.replace(" ", "%") ;

        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "search.sql";

        sql = readerSQL.get( busqueda );                    
        
        return sql ;             
    }        
        
       
    
    
    public String insert (String cuenta, String pass)
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "insert.sql";
        
        sql = readerSQL.get( cuenta, pass );
        return sql ;             
    }        
        
    
    
    
    public String update (String cuenta, String pass, Integer codigo)
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "update.sql";
        
        sql = readerSQL.get( cuenta, pass, codigo );
        return sql ;             
    }        
    

    
        
    public String delete (Integer codigo)
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "delete.sql";
        
        sql = readerSQL.get( codigo );
        return sql ;             
    }        
            
  
        
    public String controlurl (Integer usu, String path)
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "controlurl.sql";
        
        sql = readerSQL.get( usu, path );
        return sql ;             
    }        
            

    
    
    public String cambiarPass (String usu, String anterior, String nuevo)
            throws Exception {
    
        String sql = "";                         
        ReaderT readerSQL = new ReaderT("Usuario");
        readerSQL.fileExt = "cambiar_pass.sql";
        
        sql = readerSQL.get( usu, anterior,  nuevo);
        return sql ;             
    }        
            
    
    
    
    
}




