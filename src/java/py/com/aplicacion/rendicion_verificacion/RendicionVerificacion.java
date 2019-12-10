/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_verificacion;

import py.com.aplicacion.rendicion_gasto.RendicionGasto;
import py.com.aplicacion.verificacion_estado.VerificacionEstado;


/**
 *
 * @author hugo
 */
public class RendicionVerificacion {
    
    private Integer verificacion;
    private RendicionGasto rendicion;
    
    private VerificacionEstado estado;
    
    private String comentario;

    public Integer getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(Integer verificacion) {
        this.verificacion = verificacion;
    }

    public RendicionGasto getRendicion() {
        return rendicion;
    }

    public void setRendicion(RendicionGasto rendicion) {
        this.rendicion = rendicion;
    }

    public VerificacionEstado getEstado() {
        return estado;
    }

    public void setEstado(VerificacionEstado estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
    
    
}



