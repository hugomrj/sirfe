/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package py.com.aplicacion.departamento;

import py.com.base.sistema.usuario.Usuario;




public class Departamento {
    
    private Integer dpto;
    private String descripcion;    
    private Usuario verificador;
    

    public Integer getDpto() {
        return dpto;
    }

    public void setDpto(Integer dpto) {
        this.dpto = dpto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getVerificador() {
        return verificador;
    }

    public void setVerificador(Usuario verificador) {
        this.verificador = verificador;
    }


}


