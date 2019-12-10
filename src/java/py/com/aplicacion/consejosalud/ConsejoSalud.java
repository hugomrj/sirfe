/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

package py.com.aplicacion.consejosalud;
import py.com.aplicacion.departamento.Departamento;
import py.com.base.sistema.rol.Rol;


public class ConsejoSalud {
    
    private Integer id;
    private Integer cod;    
    private String descripcion;
    private Departamento dpto;
    private Rol rol;
    



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Departamento getDpto() {
        return dpto;
    }

    public void setDpto(Departamento dpto) {
        this.dpto = dpto;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
}

