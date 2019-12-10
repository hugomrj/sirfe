/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nebuleuse.ORM.postgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.xml.Basedatos;


public class Conexion {
    
    static String driver = "org.postgresql.Driver";    
    static String server = "";    
    static String database = "";    
    public String user = "";    
    public String puerto = "";    
    public String password = "";    
    public String url = "";  
    public Connection conn = null;
    
    
public  void Conexion() {
   
}     


public  void inicializar() {
            
        try 
        {
            
            Basedatos basedatos = new Basedatos();
            basedatos.setFile("basedatos.xml");
            basedatos.setPropiedades();
            
            Conexion.server = basedatos.getValue("server");
            this.database = basedatos.getValue("database");
            this.user = basedatos.getValue("user");
            this.password = basedatos.getValue("password");
            this.puerto = basedatos.getValue("puerto");
               
            this.url = "jdbc:postgresql://"+this.server+":"+this.puerto+"/"+this.database;  
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
}     



public  void conectar() {
    
    this.inicializar();
    
    try {      
        
        Class.forName(Conexion.driver).newInstance();        
        conn = DriverManager.getConnection(url,user,password);
        

        /*        
        if (conn != null)  {
            System.out.println("Conexión a base de datos "+url+" ... Ok");
        }
        */
        
    }
    catch(SQLException ex) {      
        System.out.println("Hubo un problema al intentar conectarse con la base de datos "+url);
        System.out.println(ex.getMessage());
        
        
    }
    catch(Exception ex) {
        System.out.println(ex);
    }
}

    public void desconectar(){
        
        
        try {
            if ( this.conn.isClosed() == false ){
                this.conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public Connection getConexion() {
        return conn;
    }
    
    
    public String getServer() {      
        return Conexion.server;      
    }
    
   
}
















