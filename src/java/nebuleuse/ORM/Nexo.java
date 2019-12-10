
package nebuleuse.ORM;

import java.util.ArrayList;

/*
 estructura de datos que contiene datos de relacion directa entre la tabla y la clase de la tabla
 * el indice 0 (cero) se utiliza para el nombre de la tabla y la clase
 * el indice 1 (uno) se utiliza para el nombre de la clave primaria y la propiedad de la clase
*/

public class Nexo {   
    
    private String tabla;
    private String objeto;
    
    private ArrayList <Atributo> atributo = new ArrayList <Atributo>()  {
            { 
                add(new Atributo());  
            }
        };
        
    
    public Nexo() {           
        this.tabla = null;
        this.objeto = null;    
    }
    
    public Nexo(String tablaElemento, String claseElemento ) {
        this.tabla = tablaElemento;
        this.objeto = claseElemento;
    }
       
     public String nombreMetodoGET() {
                  
         return "get" + this.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
        + this.getObjeto().toString().substring(1).toLowerCase() ;
    }
    
     public String nombreMetodoSET() {
                  
         return "set" + this.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
        + this.getObjeto().toString().substring(1).toLowerCase() ;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public ArrayList<Atributo> getAtributo() {
        return atributo;
    }

    public void setAtributo(ArrayList<Atributo> atributo) {
        this.atributo = atributo;
    }

    public void addAtributo(Atributo a) {                
        Atributo nvoAtributo = new Atributo();
        nvoAtributo = a;        
        this.atributo.add(nvoAtributo);    
    }
        
    public boolean isInsert (){
        boolean retorno = true;    
        for (Atributo a : atributo) {            
            if (a.getNombre() == "insertnot" ){
                retorno = false;
            }
        }
        return retorno;
    }

    
    public boolean isSelect (){
        boolean retorno = true;    
        for (Atributo a : atributo) {            
            if (a.getNombre() == "selectnot" ){
                retorno = false;
            }
        }
        return retorno;
    }
          
    
}







