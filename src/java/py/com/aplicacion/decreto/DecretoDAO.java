/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.decreto;


import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class DecretoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public DecretoDAO ( ) throws IOException  {
    }
      
    
    
    
    public List<Decreto>  list (Integer page) {
                
        List<Decreto>  lista = null;        
        try {                        
                        
            DecretoRS rs = new DecretoRS();        
            
            lista = new Coleccion<Decreto>().resultsetToList(
                    new Decreto(),
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
      

    
    
    
    
    public List<Decreto>  search (Integer page, String busqueda) {
                
        List<Decreto>  lista = null;
        
        try {                       
                        
            DecretoRS rs = new DecretoRS();
            lista = new Coleccion<Decreto>().resultsetToList(
                    new Decreto(),
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
    
    

    
          
        
}
