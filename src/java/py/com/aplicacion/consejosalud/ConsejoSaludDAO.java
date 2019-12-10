/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.consejosalud;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


public class ConsejoSaludDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ConsejoSaludDAO ( ) throws IOException  {
    }
      
    
    public List<ConsejoSalud>  list (Integer page) {
                
        List<ConsejoSalud>  lista = null;        
        try {                        
                        
            ConsejoSaludRS rs = new ConsejoSaludRS();            
            lista = new Coleccion<ConsejoSalud>().resultsetToList(
                    new ConsejoSalud(),
                    rs.list(page)
            );     
            
                                  
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      

    
    
    public List<ConsejoSalud>  listVerificador (Integer page, Integer usuario) {
                
        List<ConsejoSalud>  lista = null;        
        try {                        
                        
            ConsejoSaludRS rs = new ConsejoSaludRS();            
            lista = new Coleccion<ConsejoSalud>().resultsetToList(
                    new ConsejoSalud(),
                    rs.listVerificador(page, usuario)
            );     
            
                                  
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
    
    
/*
    public List<ConsejoSalud>  all () {
                
        List<ConsejoSalud>  lista = null;        
        try {                        
                        
            ConsejoSaludRS rs = new ConsejoSaludRS();            
            lista = new Coleccion<ConsejoSalud>().resultsetToList(
                    new ConsejoSalud(),
                    rs.all()
            );                        
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }      
    */  

    
    
    public List<ConsejoSalud>  search (Integer page, String busqueda) {
                
        List<ConsejoSalud>  lista = null;
        
        try {                       
                        
            ConsejoSaludRS rs = new ConsejoSaludRS();
            lista = new Coleccion<ConsejoSalud>().resultsetToList(
                    new ConsejoSalud(),
                    rs.search(page, busqueda)
            );            
            
            this.total_registros = rs.total_registros  ;            
        }         
        catch (Exception ex) {                        
            throw new Exception(ex);
        }
        finally
        {
            return lista ;          
        }
    }          
    

    
    public Integer  usuarioConsejo (Integer usuario ) {
                
        Integer ret = 0;
        
        try {                       
            
            
            ResultSet rs = new ConsejoSaludRS().usuarioConsejo(usuario);
            
            if (rs.next()) 
            {                
                ret = Integer.parseInt(rs.getString("consejo"));
            }            
            
        }         
        catch (Exception ex) {                        
            throw new Exception(ex);
        }
        finally
        {
            return ret ;          
        }
    }          
        
    
    
    
        
}
