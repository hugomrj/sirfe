/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario;

import java.io.IOException;
import py.com.aplicacion.consejosalud.ConsejoSaludDAO;


/**
 *
 * @author hugo
 */

public class UsuarioExt extends Usuario{
    
    private Integer consejo ;

    
    
    public void extender() throws IOException {
        
        this.consejo =  new ConsejoSaludDAO().usuarioConsejo(this.getUsuario()) ;
        
    }

    public Integer getConsejo() {
        return consejo;
    }

    public void setConsejo(Integer consejo) {
        this.consejo = consejo;
    }

        
     
            
}
