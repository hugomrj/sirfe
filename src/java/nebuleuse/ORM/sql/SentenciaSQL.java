/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package nebuleuse.ORM.sql;

import nebuleuse.ORM.*;

/**
 *
 * @author hugo
 */
public class SentenciaSQL {
            
  
    public static String select (Object objeto, String strBuscar)
            throws Exception  {       

        
        String strSelect =  SentenciaSQL.selectSQL(  objeto ) ;            
        String strFrom = " from " ;       
        String strWhere = "" ;       
      
        nebuleuse.ORM.xml.Serializacion serializacion = new nebuleuse.ORM.xml.Serializacion(objeto);                                
        serializacion.getElementos() ;        
        Nexo elemento = new Nexo();
  
        strFrom = strFrom + serializacion.getElementos().get(0).getTabla() ;       
        
        if (strBuscar != null) 
        {
            strBuscar = strBuscar.replace(" ", "%") ;
        
            strWhere = strWhere  + " (  ";        
            strWhere = strWhere  + " cast("+serializacion.getElementos().get(1).getTabla()
                                                   + " as text) ilike '%"+strBuscar+"%'";       
        }
        

        for (int i = 2; i < serializacion.getElementos().size() ; i++) 
        {
            elemento = serializacion.getElementos().get(i);

            if (!(strBuscar == null)) 
            {
                strWhere = strWhere  + " or cast("+ elemento.getTabla().toString()
                                                    + " as text) ilike '%" + strBuscar + "%'";         
            }

        }
        
        if (!strWhere.equals("")){
            strWhere = strWhere  + " )  ";
        }
              
        /*
        if (objetoRel != null) 
        {
            for (int i = 2; i < serializacion.getElementos().size() ; i++) 
            {   
                elemento = serializacion.getElementos().get(i);

                if (elemento.getTabla().equals(objetoRel.getClass().getSimpleName().toLowerCase()) ) 
                {
                    if (strBuscar != null)
                    {
                        strWhere = strWhere  + " and "  ;
                    }                    
                    strWhere = strWhere  + "  ("  +   elemento.getTabla() + " = ";                        
                    strWhere = strWhere  + Persistencia.getValorClavePrimaria(objetoRel) + ")";    
                    
                    break;   
                }
                              
            }
        }        
        */
        
        if (strWhere != ""){
            strWhere =" WHERE "+strWhere;
        }
        
        return strSelect + " " + strFrom +" "+strWhere ;   
      
    }    

    
    
      
    public static String selectSQL (Object objeto)  {       
          
        String strSelect = " select " ;             
        nebuleuse.ORM.xml.Serializacion serializacion = new nebuleuse.ORM.xml.Serializacion(objeto);                
        serializacion.getElementos() ;
        
        Nexo elemento = new Nexo();
        strSelect = strSelect + serializacion.getElementos().get(1).getTabla() ; 
               
        for (int i = 2; i < serializacion.getElementos().size() ; i++) 
        {  
            elemento = serializacion.getElementos().get(i);    
            if (!elemento.isSelect()){                                
                continue;            
            }    
            strSelect = strSelect + " , "+ elemento.getTabla().toString() ;       
        }
      return strSelect  ;                 
    }    

    
    
    
    public static String select (Object objeto){
        
        String strSelect = " select " ;      
        String strFrom = " from " ;
        
        nebuleuse.ORM.xml.Serializacion serializacion = new nebuleuse.ORM.xml.Serializacion(objeto);                
        serializacion.getElementos() ;
    
        strFrom = strFrom + serializacion.getElementos().get(0).getTabla() ;  
        
        Nexo elemento = new Nexo();
        strSelect = strSelect + serializacion.getElementos().get(1).getTabla() ; 
               
        for (int i = 2; i < serializacion.getElementos().size() ; i++) 
        {  
            elemento = serializacion.getElementos().get(i);    
            if (!elemento.isSelect()){                                
                continue;            
            }    
            strSelect = strSelect + " , "+ elemento.getTabla().toString() ;       
        }

      return strSelect  + strFrom;          
    
    }

    
    
    
    public static String delete (Object objeto, Integer id){
        
        String strDelete = " delete " ;      
        String strFrom = " from " ;
        String strWhere = " where " ;    
        String strReturning  = " returning " ;    
                
        nebuleuse.ORM.xml.Serializacion serializacion = new nebuleuse.ORM.xml.Serializacion(objeto);                
        serializacion.getElementos() ;    
        strFrom = strFrom + serializacion.getElementos().get(0).getTabla() ;  
        
        
        strWhere = strWhere + serializacion.getElementos().get(1).getTabla() ;
        strWhere = strWhere + "  =  "  + id;
        
        strReturning  =   strReturning + serializacion.getElementos().get(1).getTabla() ; 
        
        String sql = strDelete  + strFrom + strWhere + strReturning;          
        
        sql = "   WITH filas AS ( " 
                + sql + 
                " )   SELECT count(*) res FROM filas" ;
        
        return sql;
    
    }

        
    
    
        
    
    
    
}