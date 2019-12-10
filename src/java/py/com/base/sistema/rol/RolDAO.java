/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.rol;

import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class RolDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public RolDAO ( ) throws IOException  {
    }
      
    
    public List<Rol>  list (Integer page) {
                
        List<Rol>  lista = null;        
        try {                        
                        
            RolRS rolRs = new RolRS();            
            lista = new Coleccion<Rol>().resultsetToList(
                    new Rol(),
                    rolRs.list(page)
            );                        
            this.total_registros = rolRs.total_registros  ;
            
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
      

    
    
    public List<Rol>  search (Integer page, String busqueda) {
                
        List<Rol>  lista = null;
        
        try {                       
                        
            RolRS rolRs = new RolRS();
            lista = new Coleccion<Rol>().resultsetToList(
                    new Rol(),
                    rolRs.search(page, busqueda)
            );            
            
            this.total_registros = rolRs.total_registros  ;            
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
