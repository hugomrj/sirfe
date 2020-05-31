/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.consulta;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.RegistroMap;


public class ConsultaJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ConsultaJSON ( ) throws IOException  {
    }
      

    public JsonArray  consulta001 (
            
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer tipo
            
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta001(fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta, 
                    tipo
                    );                
                       
            
            
            
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            this.total_registros = rs.total_registros  ;   
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
              

    public JsonArray  consulta002 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta,
            Integer tipo
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta002 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta,
                    tipo
                    );                
                       
            
            Integer i = 0;
            Long gen_depo = 0L;
            Long linea_depo = 0L;
            String strPorce = "";            
            
                    
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                
//System.out.println(resulset);
                                
            //porcen =  Float.parseFloat(linea_depo / gen_depo);
            
                        gen_depo = Long.parseLong(map.get("general_depositado"));                
                        linea_depo = Long.parseLong(map.get("total_depositado"));     

            
                        double porcentaje = (double) linea_depo / gen_depo;                
                        BigDecimal bd = new BigDecimal((porcentaje) * 100);            
                        bd = bd.setScale(2, RoundingMode.HALF_UP);            
                        map.put("cal_porcen", String.valueOf( bd )  );

                        if (i  == 0 ){
                            strPorce = String.valueOf( bd )  ;
                        }            
                        else
                        {                            
                            double doble = Double.parseDouble(strPorce);
                            double doblebd = Double.parseDouble(String.valueOf( bd ) );

                            BigDecimal bd2 = new BigDecimal((doble + doblebd) );     
                            bd2 = bd2.setScale(2, RoundingMode.HALF_UP);            
                            //bd2 = bd2 * 100;
                            strPorce  =  String.valueOf(  bd2 );
                        }

                        map.put("cal_acum", strPorce );            

                        i++;
            
                
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            this.total_registros = rs.total_registros  ;   
            
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
    

    public JsonArray  consulta002_totaltodos () {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta002_totaltodos ();                
                    
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            this.total_registros = rs.total_registros  ;   
            
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
    
              

    public JsonArray  consulta003 (            
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer tipo
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta003 (
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    tipo
                    );                
                       
            
                    
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            this.total_registros = rs.total_registros  ;   
            
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
    
              

    public JsonArray  consulta004 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta,
            Integer tipo
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta004 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta,
                    tipo
                    );                
                       
            
            Integer i = 0;
            Long gen_depo = 0L;
            Long linea_depo = 0L;
            String strPorce = "";

            
            
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                
                        gen_depo = Long.parseLong(map.get("general_depositado"));                
                        linea_depo = Long.parseLong(map.get("total_depositado"));                
                
                        //porcen =  Float.parseFloat(linea_depo / gen_depo);

                        double porcentaje = (double) linea_depo / gen_depo;                
                        BigDecimal bd = new BigDecimal((porcentaje) * 100);            
                        bd = bd.setScale(2, RoundingMode.HALF_UP);            
                        map.put("cal_porcen", String.valueOf( bd )  );

                        if (i  == 0 ){
                            strPorce = String.valueOf( bd )  ;
                        }            
                        else
                        {                            
                            double doble = Double.parseDouble(strPorce);
                            double doblebd = Double.parseDouble(String.valueOf( bd ) );

                            BigDecimal bd2 = new BigDecimal((doble + doblebd) );     
                            bd2 = bd2.setScale(2, RoundingMode.HALF_UP);            
                            //bd2 = bd2 * 100;
                            strPorce  =  String.valueOf(  bd2 );
                        }

                        map.put("cal_acum", strPorce );            

                            JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                            jsonarray.add( element );

                            i++;
            }                    
            this.total_registros = rs.total_registros  ;   
            
//System.out.println(i);
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
        
    
    
    
    
        
}
