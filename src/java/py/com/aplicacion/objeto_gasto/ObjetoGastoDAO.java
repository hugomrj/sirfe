/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.objeto_gasto;


import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


public class ObjetoGastoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ObjetoGastoDAO ( ) throws IOException  {
    }
      
    
    public List<ObjetoGasto>  list (Integer page) {
                
        List<ObjetoGasto>  lista = null;        
        try {                        
                        
            ObjetoGastoRS rs = new ObjetoGastoRS();            
            lista = new Coleccion<ObjetoGasto>().resultsetToList(
                    new ObjetoGasto(),
                    rs.list(page)
            );     
            
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
      

    
    public List<ObjetoGasto>  search (Integer page, String busqueda) {
                
        List<ObjetoGasto>  lista = null;
        
        try {                       
                        
            ObjetoGastoRS rs = new ObjetoGastoRS();
            lista = new Coleccion<ObjetoGasto>().resultsetToList(
                    new ObjetoGasto(),
                    rs.search(page, busqueda)
            );            
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }          
    

    
    public ObjetoGasto filtrarId( Integer id ) throws Exception {      

          ObjetoGasto ret = new ObjetoGasto();  

          String sql = 
                    " SELECT objeto, descripcion, imputa\n" +
                    "  FROM aplicacion.objetos_gastos\n" +
                    "  where objeto = " + id + 
                    "  and imputa like 'S' " ;
          ret = (ObjetoGasto) persistencia.sqlToObject(sql, ret);

          return ret;          

      }
         
    
    
        
}
