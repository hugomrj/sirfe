/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.tipo_transferencia;


import java.io.IOException;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class TipoTransferenciaDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public TipoTransferenciaDAO ( ) throws IOException  {
    }
      


    
    
    
    

    public List<TipoTransferencia>  all () {
                
        List<TipoTransferencia>  lista = null;        
        try {                        
                        
            TipoTransferenciaRS rs = new TipoTransferenciaRS();            
            lista = new Coleccion<TipoTransferencia>().resultsetToList(
                    new TipoTransferencia(),
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
