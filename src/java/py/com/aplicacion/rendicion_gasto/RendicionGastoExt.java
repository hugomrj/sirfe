/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_gasto;


import java.io.IOException;
import nebuleuse.ORM.Persistencia;
import py.com.aplicacion.consejosalud.ConsejoSaludDAO;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacion;


/**
 *
 * @author hugo
 */

public class RendicionGastoExt extends RendicionGasto{
    
    
    private RendicionVerificacion verificacion ;

    
    
    public void extender() throws IOException, Exception {
        
        Persistencia persistencia = new Persistencia();
        RendicionVerificacion v = new  RendicionVerificacion() ;
        
        String strSQL =    " SELECT verificacion, rendicion, estado, comentario\n" +
                                "  FROM aplicacion.rendiciones_verificacion\n" +
                                "  where rendicion =  " + this.getRendicion() ;
        
        v = (RendicionVerificacion) persistencia.sqlToObject(strSQL, new RendicionVerificacion() );        
        this.verificacion = v;
        
    }

    public RendicionVerificacion getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(RendicionVerificacion verificacion) {
        this.verificacion = verificacion;
    }



        
     
            
}
