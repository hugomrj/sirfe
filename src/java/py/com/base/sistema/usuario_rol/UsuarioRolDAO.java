/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario_rol;


import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class UsuarioRolDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public UsuarioRolDAO ( ) throws IOException  {
    }
      
    
    public List<UsuarioRol>  list (Integer page) {
                
        List<UsuarioRol>  lista = null;        
        try {          
            UsuarioRolRS rs = new UsuarioRolRS();      
            
            lista = new Coleccion<UsuarioRol>().resultsetToList(
                    new UsuarioRol(),
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
      

    public List<UsuarioRol>  cabUsuario (Integer usuario, Integer page) {
                
        List<UsuarioRol>  lista = null;        
        try {          
            UsuarioRolRS rs = new UsuarioRolRS();      
            
            lista = new Coleccion<UsuarioRol>().resultsetToList(
                    new UsuarioRol(),
                    rs.cabUsuario(usuario, page)
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
      

    

    public List<UsuarioRol>  cabRol (Integer rol, Integer page) {
                
        List<UsuarioRol>  lista = null;        
        try {          
            UsuarioRolRS rs = new UsuarioRolRS();      
            
            lista = new Coleccion<UsuarioRol>().resultsetToList(
                    new UsuarioRol(),
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
      

    
        
    
    
    
    
    
    public List<UsuarioRol>  search (Integer page, String busqueda) {
                
        List<UsuarioRol>  lista = null;
        
        try {                       
                        
            UsuarioRolRS rs = new UsuarioRolRS();
            
            lista = new Coleccion<UsuarioRol>().resultsetToList(
                    new UsuarioRol(),
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
