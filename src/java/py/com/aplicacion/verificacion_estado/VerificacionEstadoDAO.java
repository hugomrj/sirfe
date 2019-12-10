/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.verificacion_estado;


import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class VerificacionEstadoDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public VerificacionEstadoDAO ( ) throws IOException  {
    }
      


    
    
    
    

    public List<VerificacionEstado>  all () {
                
        List<VerificacionEstado>  lista = null;        
        try {                        
                        
            VerificacionEstadoRS rs = new VerificacionEstadoRS();            
            lista = new Coleccion<VerificacionEstado>().resultsetToList(
                    new VerificacionEstado(),
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
