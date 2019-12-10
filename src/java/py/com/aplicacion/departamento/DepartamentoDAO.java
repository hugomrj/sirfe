/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.departamento;

import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class DepartamentoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public DepartamentoDAO ( ) throws IOException  {
    }
      
    
    public List<Departamento>  list (Integer page) {
                
        List<Departamento>  lista = null;        
        try {                        
                        
            DepartamentoRS rs = new DepartamentoRS();        
            
            lista = new Coleccion<Departamento>().resultsetToList(
                    new Departamento(),
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
      

    
    
    public List<Departamento>  search (Integer page, String busqueda) {
                
        List<Departamento>  lista = null;
        
        try {                       
                        
            DepartamentoRS rs = new DepartamentoRS();
            lista = new Coleccion<Departamento>().resultsetToList(
                    new Departamento(),
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
    
    
    
    

    public List<Departamento>  all () {
                
        List<Departamento>  lista = null;        
        try {                        
                        
            DepartamentoRS rs = new DepartamentoRS();            
            lista = new Coleccion<Departamento>().resultsetToList(
                    new Departamento(),
                    rs.all()
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
          
    
    
    
          
        
}
