/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.base.sistema.rol_selector;

import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/*
 * @author hugom_000
 */

public class RolSelectorDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public RolSelectorDAO ( ) throws IOException  {    
        
    }
      
    
    
    
    
    public List<RolSelector>  list (Integer page) {
                
        List<RolSelector>  lista = null;        
        try {          
            RolSelectorRS rs = new RolSelectorRS();      
            
            lista = new Coleccion<RolSelector>().resultsetToList(
                    new RolSelector(),
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
      
    

    

    public List<RolSelector>  cabRol (Integer rol, Integer page) {
                
        List<RolSelector>  lista = null;        
        try {          
            RolSelectorRS rs = new RolSelectorRS();      
            
            lista = new Coleccion<RolSelector>().resultsetToList(
                    new RolSelector(),
                    rs.cabRol(rol, page)
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
      

    
    

    public List<RolSelector>  cabSelector (Integer selector, Integer page) {
                
        List<RolSelector>  lista = null;        
        try {          
            RolSelectorRS rs = new RolSelectorRS();      
            
            lista = new Coleccion<RolSelector>().resultsetToList(
                    new RolSelector(),
                    rs.cabSelector(selector, page)
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
      

            
    
    
    
    
    
    public List<RolSelector>  search (Integer page, String busqueda) {
                
        List<RolSelector>  lista = null;
        
        try {                       
                        
            RolSelectorRS rs = new RolSelectorRS();
            
            lista = new Coleccion<RolSelector>().resultsetToList(
                    new RolSelector(),
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
