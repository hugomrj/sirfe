/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario_rol;


import py.com.base.sistema.rol.Rol;
import py.com.base.sistema.usuario.Usuario;


public class UsuarioRol {
    
    private Integer id;
    private Usuario usuario = new Usuario();
    private Rol rol = new Rol();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    

    
}
