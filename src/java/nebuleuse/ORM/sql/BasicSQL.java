/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package nebuleuse.ORM.sql;

import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hugo
 */
public class BasicSQL {
        
    
    
    public static Integer cont_registros(Conexion conexion, String sql) 
            throws SQLException{
    
         Integer totalRegistros = 0;

        String sqlCount = " select count(*) as rows from ( "
                + sql 
                + " ) as C ";                       
        
        ResultSet resultset = conexion.getConexion().createStatement().executeQuery(sqlCount);        

        if (resultset.next()){
            totalRegistros =  Integer.parseInt(resultset.getString(1));
        }            
        return totalRegistros;    
            
    }
        

    public static String limite_offset(Integer page, Integer lineas) {
    
            page = (page==0) ? 1 : page;
        
            page = (lineas * page) - lineas ;     
            
            String sqlend = "  limit " + lineas + " "+ 
                    " offset " + page  ;         
            
        return sqlend;    
            
    }

    
    
    
}