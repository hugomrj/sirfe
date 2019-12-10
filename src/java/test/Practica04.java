/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.List;
import nebuleuse.ORM.Persistencia;
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.consejosalud.ConsejoSaludJSON;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacion;
import py.com.aplicacion.rendicion_verificacion.RendicionVerificacionDAO;
import py.com.aplicacion.resolucion.Resolucion;
import py.com.aplicacion.resolucion.ResolucionDAO;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondo;
import py.com.aplicacion.transferencia_fondo.TransferenciaFondoDAO;




/**
 *
 * @author hugo
 */
public class Practica04 {


    
    public static void main(String args[]) {
    
        try {
            
            
//            [{"dpto":1,"descripcion":"Concepción ","verificador":{"usuario":1,"cuenta":"root","token_iat":"1571162559856"}},{"dpto":2,"descripcion":"San Pedro ","verificador":{"usuario":1,"cuenta":"root","token_iat":"1571162559856"}}]

            ConsejoSalud com = new ConsejoSalud();   
            Persistencia persistencia = new Persistencia();   

    Gson gson = new Gson();
            

                String resolucion = "61/19";
                
                ResolucionDAO dao = new ResolucionDAO();  
                
                Resolucion componente = new Resolucion();       
                
                
                componente = dao.estado_transferencia(403, resolucion);
                
                String json = gson.toJson( componente );       
    
    System.out.println( json );
                
              //String json = gson.toJson( json );    
                                
                
   
            
            
            
        } 
        catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















