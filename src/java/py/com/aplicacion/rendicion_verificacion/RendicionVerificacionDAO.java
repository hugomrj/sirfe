/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_verificacion;


import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


public class RendicionVerificacionDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public RendicionVerificacionDAO ( ) throws IOException  {
    }
         
      
    
    
    
    public List<RendicionVerificacion>  list (Integer page, Integer usuario, 
            Integer consejo, String resolucion) {
                
        List<RendicionVerificacion>  lista = null;                
        
        try {                        
        
            
            RendicionVerificacionRS rs = new RendicionVerificacionRS();            
            
            lista = new Coleccion<RendicionVerificacion>().resultsetToList(
                    new RendicionVerificacion(),
                    rs.list( page, usuario, consejo, resolucion )
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
