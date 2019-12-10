/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.selector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.sql.ReaderT;




/**
 *
 * @author hugo
 */
public class SelectorSQL {
    
   /* 
public String  Lista ( String buscar)
            throws Exception {
    
            String sql = "";             
            
            String condicionBusqueda = "";
            
            if (!(buscar.equals("")))
            {
                buscar = "%"+buscar+"%";
                buscar = buscar.replaceAll(" ", "%");
            
                condicionBusqueda = " where ( CAST ( repuesto AS text) || descripcion ) ilike '%" + buscar+ "%' ";
                
            }            
                        
            
            sql = " "+
                "  SELECT repuesto, descripcion, precio\n " +
                "  FROM aplicacion.respuestos " 
                + condicionBusqueda  ;

            return sql ;
             
    }              
*/

    
    
public String listaSimple ( )
            throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("Selector");
        readerSQL.fileExt = "select.sql";
        
        sql = readerSQL.get(1, "dos", 3 );
        return sql ;
             
    }              



    
    
public String  ListaRecursiva ( Integer codigo )
            throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("Selector");
        readerSQL.fileExt = "consultamenu.sql";
        
        sql = readerSQL.get( codigo );
        
        return sql ;
             
    }              


    
public String  ListaAll ( )
            throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("Selector");
        readerSQL.fileExt = "menuall.sql";
        
        sql = readerSQL.get( );
        
        return sql ;
             
    }              






    
}




