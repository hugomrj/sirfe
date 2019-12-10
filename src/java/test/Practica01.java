/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.Persistencia;
import nebuleuse.seguridad.Autentificacion;
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.consejosalud.ConsejoSaludDAO;
import py.com.aplicacion.consejosalud.ConsejoSaludSQL;
import py.com.aplicacion.departamento.Departamento;
import py.com.aplicacion.departamento.DepartamentoDAO;
import py.com.aplicacion.rendicion_gasto.RendicionGasto;
import py.com.aplicacion.rendicion_gasto.RendicionGastoDAO;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;
import py.com.base.sistema.usuario.Usuario;
import py.com.base.sistema.usuario.UsuarioDAO;
import py.com.base.sistema.usuario.UsuarioExt;
import py.com.base.sistema.usuario.UsuarioSQL;
import py.com.base.sistema.usuario_rol.UsuarioRol;
import py.com.base.sistema.usuario_rol.UsuarioRolDAO;




/**
 *
 * @author hugo
 */
public class Practica01 {


    
    public static void main(String args[]) {
    
        try {
            
            
            //Usuario usuario = new Usuario();
            Persistencia persistencia = new Persistencia();
               
            
            RendicionGasto r = new RendicionGasto();
            r.setImporte(500L);

           r.setTransferencia(17);
            
          new RendicionGastoDAO().insert(r);
                

            
                
            
        } catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















