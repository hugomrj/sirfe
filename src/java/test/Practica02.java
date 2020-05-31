/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.sql.ResultSet;
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
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoJSON;
import py.com.base.sistema.selector.SelectorJSON;
import py.com.base.sistema.usuario.Usuario;
import py.com.base.sistema.usuario.UsuarioDAO;
import py.com.base.sistema.usuario.UsuarioExt;
import py.com.base.sistema.usuario.UsuarioSQL;
import py.com.base.sistema.usuario_rol.UsuarioRol;
import py.com.base.sistema.usuario_rol.UsuarioRolDAO;
import py.com.consulta.ConsultaJSON;
import py.com.consulta.ConsultaRS;




/**
 *
 * @author hugo
 */
public class Practica02 {


    
    public static void main(String args[]) {
    
        try {
            
            
            //Usuario usuario = new Usuario();
            Persistencia persistencia = new Persistencia();
               
            
                String json = "";

                
                    JsonArray jsonarray = new ConsultaJSON()
                            .consulta001("20100101", "20201010", 
                            0, 999, 
                            0, 999, 
                            0);
                    
                    
                    
                    
                            ConsultaRS rs = new ConsultaRS();            
                            ResultSet resulset = rs.consulta001("20100101", "20201010", 
                            0, 999, 
                            0, 999, 
                            0);
                    
                            
                            
                            
                            while (resulset.next()) {                                  
                                System.out.println(resulset.getString("dpto"));
                            }                    
        
                            
                            
                    json = jsonarray.toString();
                    System.out.println( json);
            
                
            
        } catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















