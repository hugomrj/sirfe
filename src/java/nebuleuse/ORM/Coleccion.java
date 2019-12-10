/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.ORM;

import nebuleuse.ORM.postgres.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Coleccion<T> {
    
    
    /*
    public Integer lineas = 12;
    public Integer subLineas = 6;
    */
    
    private List<T>  listaDeObjetos=new ArrayList<T>(); 
    private Conexion conexion = new Conexion();
    private Statement  statement ;
    private ResultSet resultset;        
    private Persistencia persistencia = new Persistencia();
    
       
    /*
    
    public  List<T> all(Object objeto, String strBuscar) throws Exception {
  
        conexion.conectar();            
    
        try
        {            
            statement = conexion.getConexion().createStatement();               
            resultset = statement.executeQuery( persistencia.selectSQL(objeto, strBuscar));                
            listaDeObjetos = resultsetToList(objeto, resultset );
            
            resultset.close();    
                        
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getMessage());    
        }  
                
        conexion.desconectar();                            
        
        return listaDeObjetos;         
    }

    */
       
    
    
    
    
    public  List<T> lista(Object objeto, String strSQL) throws Exception {
  
        conexion.conectar();            
    
        try
        {            
            statement = conexion.getConexion().createStatement();               
            resultset = statement.executeQuery( strSQL );                
            listaDeObjetos = resultsetToList(objeto, resultset );
            
            resultset.close();    
                        
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getMessage());    
        }  
                
        conexion.desconectar();                            
        
        return listaDeObjetos;         
    }

    
        
    
    
    public List<T> resultsetToList(Object objeto, ResultSet resultset ) 
            throws ClassNotFoundException, SQLException, 
            InstantiationException, IllegalAccessException, 
            Exception{
        
            while(resultset.next()) 
            {

                Class claseObjeto = Class.forName(objeto.getClass().getName());             
                T  returnObjecto = (T) Class.forName(claseObjeto.getName()).newInstance();                  
                returnObjecto = (T) persistencia.extraerRegistro(resultset, objeto);             
                listaDeObjetos.add(returnObjecto);
            }
    
        return listaDeObjetos;     
    }
    
    
    
    
}









