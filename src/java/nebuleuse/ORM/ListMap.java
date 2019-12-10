/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.ORM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.xml.Global;

/**
 *
 * @author hugom_000
 */
public class ListMap {

    public Integer lineas ;
    public Integer l = Integer.parseInt(new Global().getValue("lineasLista"));
        
    public ListMap() throws IOException {
        
        Global global = new Global();                
        this.lineas = Integer.parseInt(global.getValue("lineasLista"));
        
    }
    
    
        
    
    
        
    
    public List<Map <String, Object>> resultsetToList(ResultSet rs) throws SQLException{
    
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        
        List<Map <String, Object>> rows = new ArrayList<Map <String, Object>>();
        
        while (rs.next()){
        
            Map<String, Object> row = new HashMap<String, Object>(columns);
            
            for (int i=1 ; i <= columns ; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            
        rows.add(row);
        
        }
        
        return rows;
    
    
    }
    
    
    
    
    
    
    
}
