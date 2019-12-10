/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.seguridad;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import py.com.base.sistema.usuario.UsuarioDAO;

/**
 *
 * @author hugo
 */

public class Autentificacion {
    
    public TokenExt token = new TokenExt();
    public Long valides = 900000L;
    
    
    public TokenExt newTokken( String user, String nombre){
        
        token.setUser(user);
        token.setNombre(nombre);
        
        Long actual = System.currentTimeMillis();
        Long expiracion = actual + valides;
        
        token.setIat(actual.toString());
        token.setExp(expiracion.toString());
        
        token.firmar();        
        return token;
    }
        
    
    public Token actualizar( ) throws IOException, SQLException{        
        
        Long actual = System.currentTimeMillis();
        Long expiracion = actual + valides;
        
        token.setIat(actual.toString());
        token.setExp(expiracion.toString());        
        token.firmar();        
        
        new UsuarioDAO().set_tokenDB(
                this.token.getUser(), 
                this.token.getIat());        
        
        
        return token;
    }
        
    
        
    
    

    
    public Boolean IsExpirado (){
                
        Boolean retornar = true;        
        
        try {   
            if ( Long.parseLong(token.getExp()) > System.currentTimeMillis() ){
                retornar = false;
            }    
        }        
        catch (Exception ex) {
            retornar = true;
        }        
        finally{
            return retornar;
        }
        
        
    }
    
    
    
    
    public void setTokken( String jsondato ){
        
        try {
            
            Gson gson = new Gson();     
            token= gson.fromJson(jsondato, TokenExt.class);
        }        
        catch (Exception ex) {
            token = null;
        }        
        
    }

    
    
    
    public Boolean verificar( String dato ){
                
        Boolean retornar = false;        
        try {
            
            String json = this.desencriptar(dato);
            this.setTokken(json);
            
            retornar = (this.token.Isfirma());
            
            retornar = (!(this.IsExpirado()));
            
            //retornar = (this.IsKeyDB( Integer.parseInt(this.token.getUser())));
            
        }        
        catch (Exception ex) {
            retornar = false;
        }        
        finally{
            return retornar;
        }
        
        
    }

    
    
    
    
    public Boolean IsKeyDB( Integer codUser ){
                
        Boolean retornar = false;    
        
        try {
            
            String keyDB = new UsuarioDAO().get_tokenDB(codUser);
        
            if(this.token.getIat().equals(keyDB)  ){
                retornar = true;
            }            
                        
        }        
        catch (Exception ex) {
            retornar = false;
        }        
        finally{
            return retornar;
        }
        
        
    }
    
    
    
    public String toJson(){
        Gson gson = new Gson();                
        String json = gson.toJson( token );
        return json;
    }
    
    public String encriptar(){
        
        EncripPI encrip = new EncripPI();
        return encrip.encriptar(this.toJson());
        
    }
    
    public String desencriptar(String data){
        
        EncripPI encrip = new EncripPI();
        return encrip.desencriptar(data);
        
    }    
    
    
}
