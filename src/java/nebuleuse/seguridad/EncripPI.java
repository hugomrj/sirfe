/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.seguridad;



/**
 *
 * @author hugo
 */


public class EncripPI {    
             
    private Integer cantidadLugares = 3;
            
    public String encriptar(String texto) {
    
        String ret ="";
        String binario = todoBin(texto);    
        
        String bloque = "";    
        //String invertido = "";
        
        Integer largo = binario.length();
        Integer sumaLargoBloque = 0;

        while ( largo > 0 ) {
            
            sumaLargoBloque =  getSumarCadena( binario.substring(0, cantidadLugares)) ;     
    
            Integer largoBloque = sumaLargoBloque + cantidadLugares;            
            bloque = binario.substring(0, largoBloque);
            
             ret += encriptarBloque(bloque);
             
            binario = binario.substring(largoBloque);            
            largo = binario.length();              
            
        }                
        
        return ret;
    }
    
    
    
    
    public String desencriptar(String texto){
    
        try{

            String ret ="";
            Integer largo = texto.length();
            String bloque = ""; 
            Integer sumaLargoBloque = 0;
            Integer largoBloque = 0;

            while ( largo > 0 ) {

                sumaLargoBloque =  getSumarCadena( texto.substring(0, cantidadLugares)) ;                        
                largoBloque = sumaLargoBloque + cantidadLugares;                        
                bloque = texto.substring(cantidadLugares, largoBloque);

                 bloque = bloqueBinario(bloque);      
                 ret +=  desencriptarBloque(bloque);

                texto = texto.substring(largoBloque);            
                largo = texto.length();              
            }                
            return ret;

        }
        catch (Exception ex){
            return "";
        }
        
    }
    
    
    
    
    public String desencriptarBloque(String texto){
        
        String ret ="";
        char c =(char)Integer.parseInt(texto,2); 
        ret = Character.toString(c);
        
        return ret;        
    }
     
        
    
    public String bloqueBinario(String texto){
    
        String ret ="";
        Integer largo = texto.length();        
                
      for (int i=0; i<texto.length(); i++)
        {            
            char x = texto.charAt(i);    
            if (esPI((int)x) == "p"){
                ret = ret + "0";
            }
            if (esPI((int)x) == "i"){
                ret = ret + "1";
            }            
        }    
        return ret;
    }
    
        
    
    
        
    public String encriptarBloque(String texto){
    
        String ret ="";
        String prefijo ="";
        Integer largo = texto.length();                        
        prefijo =  texto.substring(0, cantidadLugares);       
        
      for (int i=cantidadLugares; i<texto.length(); i++)
        {            
            char x = texto.charAt(i);    
            if (esPI((int)x) == "p"){
                ret = ret + randonPar();
            }
            if (esPI((int)x) == "i"){
                ret = ret + randonImpar();
            }            
        }    
        return prefijo+ret;
    }
    
    
    
    
    public Integer randonPar(){        
        Integer i = randInt();        
        if ( esPI(i) == "i" ){            
            i --;
        }        
        return i;
    }
   

    public Integer randonImpar(){        
        Integer i = randInt();        
        if ( esPI(i) == "p" ){            
            i ++;
        }        
        return i;
    }
    
    
    
    
    public String randonBin(String texto){        
        String ret ="";
        
        for (int i=0; i<texto.length(); i++)
        {            
            char x = texto.charAt(i);                        
            ret = ret + charBin(x).trim();
        }        
        return ret;        
    }

    

    public Integer randInt() {
    
        int intAletorio = 0 ;    
            intAletorio = (int) (Math.random()*9) ;

        return intAletorio;
    }



    public String todoBin(String texto){
        
        String ret ="";

        for (int i=0; i<texto.length(); i++)
        {            
            char x = texto.charAt(i);                        
            ret = ret + charBin(x).trim();
        }
        return ret;
        
    }
    
    
    
    public String charBin(char x){
        
        String retorno ="";
        Integer largo = 0;
        largo = Integer.toBinaryString((int) x).length();
        
        String prefijo= "";        
        prefijo = getSumador(cantidadLugares, largo);

        retorno = prefijo.trim() + Integer.toBinaryString((int) x).toString().trim();        
        return retorno;
    }
    

    
    public String pasarBinario(String texto){
             
            texto = texto.replaceAll("0", "0");
            texto = texto.replaceAll("2", "0");
            texto = texto.replaceAll("6", "0");
            texto = texto.replaceAll("4", "0");
            texto = texto.replaceAll("8", "0");
            
            texto = texto.replaceAll("1", "1");
            texto = texto.replaceAll("3", "1");
            texto = texto.replaceAll("5", "1");
            texto = texto.replaceAll("7", "1");
            texto = texto.replaceAll("9", "1");
        
        return texto;        
    }

    
    
    public String invertirBinario(String texto){
        
        texto = texto.replaceAll("0", "2");
        texto = texto.replaceAll("1", "0");
        texto = texto.replaceAll("2", "1");
        
        return texto;        
    }
          
  
    
   public String esPI(Integer numero){    
        if (numero%2==0){
            return "p";
        }
        else{
            return "i";
        }
    }

    
   
   public String getSumador(Integer lugares, Integer resultante){
       
       Integer sum = 0;       
       String num = "";
       Integer aux = 0;
       
       for (int i = 1; i <= (lugares -1); i++) {
           num +=  randInt().toString().trim();
       }

       sum = getSumarCadena(num);       
       
       if (sum < resultante){
           aux = resultante - sum;
           num += aux.toString();
       }
       if (sum == resultante){
            num += "0";
       }

       if (sum > resultante){           
           
           num += resultante.toString();
           aux = getSumarCadena(num);
           
           if (aux > resultante ){               

                Integer resul = Integer.parseInt(num);
                resul = resul - (aux - resultante);
                num = resul.toString();
           }
           
           if (aux < resultante ){                              
                Integer resul = Integer.parseInt(num);                
                resul = resul + (resultante - aux);
                num = resul.toString();
           }
          
           if (num.length() <  cantidadLugares){                                                  
               while (num.length() <  cantidadLugares) {                   
                   num +=  "0";
               }
           }

       }            
       return num;                      
   }
   
   
   
   public Integer getSumarCadena(String ran ){
   
       int valor = 0;
       Integer sum = 0; 
       
       for (int i = 0; i < ran.length(); i++) {
           valor = Character.getNumericValue(ran.charAt(i));  
           sum =  sum + valor;
           
           if (sum >= 10 ){
               sum = Integer.parseInt(sum.toString().trim().substring(0, 1)) 
                       +Integer.parseInt(sum.toString().trim().substring(1, 2)) ;            
           }
       }       
       return sum;
   }
   
   
    
}

