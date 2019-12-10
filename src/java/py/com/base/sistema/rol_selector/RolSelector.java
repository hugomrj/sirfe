/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.rol_selector;

import py.com.base.sistema.rol.Rol;
import py.com.base.sistema.selector.Selector;


public class RolSelector {
    
    private Integer id;
    private Rol rol = new Rol();
    private Selector selector = new Selector();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    
}
