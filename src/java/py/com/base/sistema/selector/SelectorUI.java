/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.selector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.xml.Global;


/**
 * @author hugo
 */
public class SelectorUI {
    

    public String menu ( String strCOdigo ){

        
        String strHtml = "";     

        try {
            
            Integer cod = Integer.parseInt( strCOdigo );
            
            String htmlCierre = "</li></ul>";
            
            
            ResultSet res = new SelectorRS().ListaRecursiva(cod);
            Integer nivel = 0;
            Integer nivelAnterior = -1;
            
            while(res.next())
            {
                nivel = Integer.parseInt(res.getString(4)) ;
                
                if (nivel > nivelAnterior){
                    strHtml += "<ul><li>";
                }
                else
                {
                    if (nivel < nivelAnterior){
                        Integer diferencia = nivelAnterior -nivel;
                        
                        for (int i = 0; i < diferencia ; i++ ){
                            strHtml += "</li></ul>" ;
                        }
                    }
                    strHtml += "</li><li>";
                }
                
                strHtml +=" <a href='";
                if ((res.getString(6) != null) && !(res.getString(6).equals("")) ) {
                    
                    
                    String auxstr = res.getString(6).toString().trim();
                    auxstr = auxstr.substring(0, 1);
                    
                    if (auxstr.equals("/")){

                        String raiz = new Global().getValue("raiz");
                        
                        strHtml +=  raiz +  res.getString(6).toString().trim();

    
                    }
                    else{
                        
                        strHtml +=   "" +  res.getString(6).toString().trim() +"' " +" target='_blank";
                        
                    }
                    
                }
                
                else{
                    strHtml +="javascript:void(0);";
                }
                strHtml +="'>";
                
                
                strHtml +=   "<div>" + res.getString(3).toString().trim() + "</div>";
                strHtml +=" </a>";
                
                nivelAnterior =  nivel;
            }
            
            if (strHtml != ""){
                for (int i = 0; i <= nivel ; i++ ){
                    strHtml += htmlCierre ;
                }
            }
            //response.getWriter().println(strHtml);
            
            
            
            
        } 
        catch (Exception ex) {
            
            System.out.println(ex.getMessage());
        
        }
        finally{
        
            return strHtml ;
        }

            
            
            
            
            
        }              
    
    
}
