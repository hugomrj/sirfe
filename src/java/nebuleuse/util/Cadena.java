/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.util;


/*
 * @author hugom_000
 */


public  abstract class Cadena {
    
    
    public static Integer contChr(String texto, char buscar){
        
        Integer cantidad = 0;        
        Integer tam = texto.length();
        char letra ;
                
        for (int i = 0; i != tam; i++)
        {
            letra = texto.charAt(i);               
            if ( letra == buscar ) {
                cantidad ++ ;
            }
        }                
        return cantidad;                
    }    
    
    
    
    
    
    
    
}
