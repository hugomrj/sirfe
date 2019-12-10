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




/**
 *
 * @author hugo
 */
public class Practica02 {


    
    public static void main(String args[]) {
    
        try {
            
            
            //Usuario usuario = new Usuario();
            Persistencia persistencia = new Persistencia();
               
            
            
            
            //String json = "\"consejo\":100,\"transferencia_numero\":\"44/44\",\"comprobante_numero\":5000,\"objetogasto\":{ \"objeto\":\"132\" },\"fecha\":\"2019-09-12\",\"importe\":4000,\"observacion\":\"\", \"tipo_comprobante\" :{  \"tipo_comprobante\": \"1\"} ";
            //String json = "{\"consejosalud\":{ \"consejo\":100 },\"transferencia_numero\":\"44/44\",\"comprobante_numero\":444,\"objetogasto\":{ \"objeto\":\"100\" },\"fecha\":\"2019-09-19\",\"importe\":45454,\"observacion\":\"\", \"tipo_comprobante\":{  \"tipo_comprobante\": \"1\"} }";
           
            //String json = "{\"consejosalud\":{ \"consejo\":100 },\"transferencia_numero\":\"5/01\",\"comprobante_numero\":444,\"objetogasto\":{ \"objeto\":\"100\" },\"fecha\":\"2019-09-06\",\"importe\":4454,\"observacion\":\"sfffafsd\", \"tipo_comprobante\" :{  \"tipo_comprobante\": \"1\"} }";
            //String json = "{\"consejosalud\":{ \"consejo\":100 },\"transferencia_numero\":\"5/01\",\"comprobante_numero\":444,\"objeto\":{ \"objeto\":\"100\" },\"fecha\":\"2019-09-06\",\"importe\":4454,\"observacion\":\"sfffafsd\", \"tipo_comprobante\" :{  \"tipo_comprobante\": \"1\"} }";
            
            
//            String json = "{\"transferencia_numero\":\"44/44\",\"comprobante_numero\":444,\"objeto\":{ \"objeto\":\"100\" },\"fecha\":\"2019-09-06\",\"importe\":45545,\"observacion\":\"\", \"tipo_comprobante\" :{  \"tipo_comprobante\": \"1\"} }";

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
                
                TransferenciaFondoJSON jsonarray = new TransferenciaFondoJSON();
                
                String json = jsonarray.list_resolucion(1).toString();

                
                
            System.out.println( json);
            
                
            
        } catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















