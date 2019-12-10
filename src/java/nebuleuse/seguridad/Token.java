/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.seguridad;




public class Token {
    
    private String user;
    private String nombre;
    private String iat;
    private String exp;
    private String firma;
    

    
    public void firmar(){    
        //this.firma = Hash.sha1(user.trim() + nombre.trim() + iat.trim() + exp.trim());
        this.setFirma(hashShaFirma());        
    }
        
    
    public String hashShaFirma(){    
        return Hash.sha1(getUser().trim() + getNombre().trim() + getIat().trim() + getExp().trim());
    }
    
    
    public Boolean Isfirma(){            
        
        //
        Boolean retornar = false;
        
        try {
            if (this.getFirma() != null){
                String control = Hash.sha1(getUser().trim() + getNombre().trim() + getIat().trim() + getExp().trim());  

                if (this.getFirma().trim().equals( control.trim() )){
                    retornar = true;
                }
                else {
                    retornar =  false;
                }                    
            }
        }
        catch (Exception ex){
            return false;
        }
        finally{
            return retornar;
        }
        
    }    
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
    

    
    
}
