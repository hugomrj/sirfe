/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import nebuleuse.seguridad.Autentificacion;
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.consejosalud.ConsejoSaludDAO;
import py.com.aplicacion.consejosalud.ConsejoSaludSQL;
import py.com.aplicacion.departamento.Departamento;
import py.com.aplicacion.departamento.DepartamentoDAO;
import py.com.aplicacion.objeto_gasto.ObjetoGasto;
import py.com.aplicacion.rendicion_gasto.RendicionGasto;
import py.com.aplicacion.rendicion_gasto.RendicionGastoDAO;
import py.com.aplicacion.rendicion_gasto.RendicionGastoExt;
import py.com.aplicacion.rendicion_gasto.RendicionGastoRS;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacion;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacionDAO;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacionJSON;
import py.com.aplicacion.tipo_transferencia.TipoTransferencia;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoRS;
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
public class Practica03 {


    
    public static void main(String args[]) {
    
        try {
            
            Persistencia persistencia = new Persistencia();   
            Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
    
            
String json = 
        "{\n" +
        "\"rendicion\":0,\n" +
        "\"transferencia\":40,\n" +
        "\"resolucion_numero\":\"456/19\",\n" +
        "\"comprobante_numero\":4545,\n" +
        "\"objetogasto\":{ \"objeto\":112 },\n" +
        "\"fecha\":\"2019-11-04\",\n" +
        "\"importe\":2,\n" +
        "\"observacion\":\n" +
        "\"fgasdgagadga\",\n" +
        "\"ruc_factura\":\"444444-0\",\n" +
        "\"timbrado_venciomiento\": null , \n" +
        "\"tipo_comprobante\" :{  \"tipo_comprobante\": \"1\"} ,\n" +
        "\"concepto\":\"\"\n" +
        "}";            
            

            List<RendicionGastoExt>  lista = null;     
            
            RendicionGastoRS rs = new RendicionGastoRS();            
            
            lista = new Coleccion<RendicionGastoExt>().resultsetToList(
                    new  RendicionGastoExt(), rs.coleccion(40)
            );           
            
            
            
            for (RendicionGastoExt rex : lista) {
                
                System.out.println(   rex.getRendicion() );
                System.out.println(   rex.getComprobante_numero() );
                System.out.println(   rex.getObjeto().getObjeto() );
                System.out.println(   rex.getVerificacion().getComentario());
                
            }
                
            System.out.println(   lista );
            
            
        } 
        catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















