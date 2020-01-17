/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package nebuleuse.ORM;


import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.ORM.xml.Serializacion;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import nebuleuse.ORM.sql.SentenciaSQL;

import nebuleuse.util.Datetime;
import py.com.base.sistema.usuario.Usuario;

/**
 * @author hugo
 */

public class Persistencia {
    
    
    public Object insert ( Object objeto ) throws SQLException,  Exception  {
    
        String strSQL = "";
        String strInsert = "", strColums = "", strValues = "", strRETURNING = "";
        Object valorPropieded = null;        


        Serializacion serializacion = new Serializacion();        
        serializacion.generar(objeto);        
        serializacion.getElementos() ;

        strInsert = "insert into "  + serializacion.getElementos().get(0).getTabla();
        strColums = " ";
        strValues = " values ";
        strRETURNING = " RETURNING " + serializacion.getElementos().get(1).getTabla();
        
        Nexo elemento = new Nexo();
        String nombreMetodo = "";
        Class classDato = null;

        Object objetoReturn;


        for ( int i = 2; i < serializacion.getElementos().size() ; i++ ) 
        {      

            elemento = serializacion.getElementos().get(i);          
            elemento.getObjeto().toString();

            if (!elemento.isInsert()){                        
                continue;            
            }            
            
            strColums = strColums + ((i == 2) ? " ( " : " , " ) + elemento.getTabla().toString(); 
            strValues = strValues +  ((i == 2) ? "  ( " : " , " ) ;
            
            nombreMetodo = "get" + elemento.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
                    + elemento.getObjeto().toString().substring(1).toLowerCase() ;
                        
            classDato = objeto.getClass().getMethod(nombreMetodo).getReturnType();
            valorPropieded =  objeto.getClass().getMethod(nombreMetodo).invoke(objeto);     
                        
            if ( valorPropieded == null ) 
            {
                strValues = strValues + "null";
            }
            else 
            {  
                
                if (classDato == Integer.class) 
                {               
                    strValues = strValues +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                }
                else if (classDato == Long.class) 
                {                
                    strValues = strValues +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                }
                else if (classDato == Float.class) 
                {                
                    strValues = strValues +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                }
                else if (classDato == String.class) 
                {                    
                    if (objeto.getClass().getSimpleName().equals("Usuario") && nombreMetodo.equals("getClave"))
                    {
                        strValues = strValues + " " +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString() + " ";                        
                    }
                    else
                    {
                        strValues = strValues + " '" +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString().replaceAll("'","''") + "' ";
                    }                                
                }            
                else if (classDato == Date.class) 
                { 
                    strValues = strValues + " '" +  Datetime.toSQLDate(objeto.getClass().getMethod(nombreMetodo).invoke(objeto)) + "' ";                
                }
                
                else 
                {              
                    
                    for ( Atributo atr : elemento.getAtributo() ) 
                    {
                        
                        if (classDato.getSimpleName().equals(atr.getValor()))
                        {
                            
                            
                            Object objetoRelacionado = new Object();                                                     
                            objetoRelacionado = objeto.getClass().getMethod(nombreMetodo).invoke(objeto);
                            
                            
                            if ( this.getValorClavePrimaria(objetoRelacionado)  == null){
                                strValues = strValues + "0";      
                            }
                            else
                            {
                                
                                Object clavePri =  this.getValorClavePrimaria(objetoRelacionado);
                                //strValues = strValues + this.getValorClavePrimaria(objetoRelacionado).toString();       

                                if (clavePri.getClass() == Integer.class){
                                    strValues = strValues + clavePri.toString();                             
                                }
                                if (clavePri.getClass() == String.class){
                                    strValues = strValues + " '" + clavePri.toString() + "' " ;                             
                                }                                            
                                
                            }
                        }
                    }                    
                }
            }
        }

        
        strColums = strColums +" ) " ;
        strValues = strValues +" ) " ;

        
        strSQL = strInsert + strColums + strValues + strRETURNING;
        
     
        Integer intID = 0;  
        try
        {            
            intID = this.ejecutarSQL(strSQL, serializacion.getElementos().get(1).getTabla() );
        }
        catch (SQLException ex)
        {                         
            throw new SQLException(ex);
        }          
        
        
        if ( intID != 0 )
        {            
            Class claseObjeto = Class.forName(objeto.getClass().getName());                     
            objetoReturn = claseObjeto.newInstance();  
            objetoReturn = this.filtrarId(objetoReturn, intID);
        }
        else
        {
            objetoReturn = null;        
        }        
        return objetoReturn;
        
    }
    
    

    
    
    
    public Object update ( Object objeto) throws SQLException,  Exception {
        
        Object objetoReturn;        
       
        String strSQL = "";
        String strUpdate = "", strSet = "", strWhere = "", strRETURNING = "";
        Object valorPropieded = null;        
                
        Serializacion serializacion = new Serializacion();        
        serializacion.generar(objeto);        
        serializacion.getElementos() ;
        
        strUpdate = "update  " + serializacion.getElementos().get(0).getTabla();
        strSet = " set ";
        strWhere = " where " + serializacion.getElementos().get(1).getTabla() ;        
        strRETURNING = " RETURNING " + serializacion.getElementos().get(1).getTabla();

        Nexo elemento = new Nexo();
        String nombreMetodo = "";
        Class classDato = null;      
    
        Integer contadorElementos = 0;
        
        
        
        for ( int i = 1; i < serializacion.getElementos().size() ; i++ ) {  
            
            elemento = serializacion.getElementos().get(i);     

            nombreMetodo = "get" + elemento.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
                    + elemento.getObjeto().toString().substring(1).toLowerCase() ;
                        
            classDato = objeto.getClass().getMethod(nombreMetodo).getReturnType();
            valorPropieded =  objeto.getClass().getMethod(nombreMetodo).invoke(objeto);  

            
            // si es Id de tabla
            if ( i == 1 ) 
            {
                strWhere = strWhere  + " =  " +
                        objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();          
            }
            else
            {      
                
                contadorElementos ++;                
                
                if ( valorPropieded == null ) 
                {

                    for ( Atributo atr : serializacion.getElementos().get(i).getAtributo() ) 
                    {
                        //updatenull
                        //System.out.println(atr.getNombre());
                        //System.out.println(atr.getValor());                            
                    }
                                                            
                    if ( contadorElementos == 1){
                        strSet = strSet + " " + serializacion.getElementos().get(i).getTabla().toString()  + " = null "   ;                        
                    }
                    else{                    
                        strSet = strSet + ", " + serializacion.getElementos().get(i).getTabla().toString()  + " = null "   ;                        
                    }
                    
                    //continue;
                }
                
                else
                {
                                        
                    if ( contadorElementos == 1){
                        strSet = strSet + " " + elemento.getTabla().toString() + " = "   ;                        
                    }
                    else{                    
                        strSet = strSet + ", " + elemento.getTabla().toString() + " = "   ;                        
                    }
                           
                                       
                    
                    
                    if (classDato == Integer.class) 
                    {
                        if ( valorPropieded == null ) 
                        {
                            strSet = strSet + "null";
                        }
                        else 
                        {  
                            strSet = strSet +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                        }
                    }
                    else if (classDato == Long.class) 
                    {
                        if ( valorPropieded == null ) 
                        {
                            strSet = strSet + "null";
                        }
                        else 
                        {                          
                            strSet = strSet +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                        }
                    }
                    else if (classDato == String.class) 
                    {
                        if (objeto.getClass().getSimpleName().equals("Usuario") && nombreMetodo.equals("getClave"))
                        {   
                            if ( valorPropieded == null ) 
                            {
                                strSet = strSet + "null";
                            }
                            else 
                            {                          
                                strSet = strSet + " " +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString() + " ";                        
                            }
                        }
                        else
                        {
                            if ( valorPropieded == null ) 
                            {
                                strSet = strSet + "null";
                            }
                            else 
                            {                          
                                strSet = strSet + " '" +  objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString().replaceAll("'","''") + "' ";
                            }
                        }
                    }            
                    else if (classDato == Date.class) 
                    { 
                        strSet = strSet + " '" +  Datetime.toSQLDate(objeto.getClass().getMethod(nombreMetodo).invoke(objeto)) + "' ";                
                    }
                    else
                    { 
                        
                        if ( valorPropieded == null ) 
                        {

                            strSet = strSet + "null";
                        }
                        else 
                        {                     
                            
                            for ( Atributo atr : serializacion.getElementos().get(i).getAtributo() ) 
                            {
                            

                                
                                if (classDato.getSimpleName().equals(atr.getValor()))
                                {
                                    Object objetoRelacionado = new Object();                                                     
                                    objetoRelacionado = objeto.getClass().getMethod(nombreMetodo).invoke(objeto);
                                    

                                    if ( this.getValorClavePrimaria(objetoRelacionado)  == null){
                                        strSet = strSet + "0";                             
                                    }
                                    else
                                    {
                                        
                                        Object clavePri =  this.getValorClavePrimaria(objetoRelacionado);
                                                                                                                
                                        if (clavePri.getClass() == Integer.class){
                                            strSet = strSet + clavePri.toString();                             
                                        }
                                        if (clavePri.getClass() == String.class){
                                            strSet = strSet + " '" + clavePri.toString() + "' " ;                             
                                        }                                            
                                        
                                        
                                    }
                                }
                            }  
                        }                    
                    }
                 }   
            }      
        }
        
        strSQL = strUpdate + strSet + strWhere + strRETURNING;
       
        Integer intID = 0;
        
        try
        {            
            intID = this.ejecutarSQL(strSQL, serializacion.getElementos().get(1).getTabla() );
        }
        catch (SQLException ex)
        {                         
            throw new SQLException(ex);
        }          
        
        
        if ( intID != 0 )
        {            
            Class claseObjeto = Class.forName(objeto.getClass().getName());                     
            objetoReturn = claseObjeto.newInstance();  
            objetoReturn = this.filtrarId(objetoReturn, intID);
        }
        else        {
            objetoReturn = null;        
        }
        
        return objetoReturn;
        
    }
    
    

    
    
    public Object delete ( Object objeto )  throws SQLException, Exception {

        boolean bool = false;
                    
        String strSQL = "";
        String strDelete = "", strWhere = "";
        Serializacion serializacion = new Serializacion();
        serializacion.generar(objeto);        
        serializacion.getElementos() ;
        strDelete = "delete from  " + serializacion.getElementos().get(0).getTabla();
        strWhere = " where " + serializacion.getElementos().get(1).getTabla() ;
        
        Nexo elemento = new Nexo();
        String nombreMetodo = "";
        // obtiene valor del id
        elemento = serializacion.getElementos().get(1);
        nombreMetodo = "get" + elemento.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
                + elemento.getObjeto().toString().substring(1).toLowerCase() ;
        strWhere = strWhere  + " =  " +
                objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
                
        try 
        {            
                        
            strSQL = strDelete + strWhere;

            bool  = this.ejecutarSQL(strSQL);

            Integer valorInt;
            valorInt = Integer.parseInt( objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString());
                            
        } 
        catch (SQLException ex) 
        {

            throw new SQLException(ex);
        } 
            
        if (bool)
        { 
            return null;
        }
        else
        {
            return objeto;                
        }            
        
    }    
    

    
    public Integer delete ( Object objeto, Integer id ) 
            throws SQLException  {
        
        Integer res = 0;
        String strSQL = "";
        
        strSQL = SentenciaSQL.delete(objeto, id);   
         
        res = this.ejecutarSQL(strSQL, "res");
        
        return res;
        
    }        
    
    
    
    
    
    public boolean ejecutarSQL( String strSQL ) 
            throws SQLException {
        
        boolean bool = false;        
        Conexion conexion = new Conexion();
        conexion.conectar();            
        
        try
        {
            Statement  stmt = conexion.getConexion().createStatement();    
            stmt.execute( strSQL );            
            bool = true;
        }
        catch (SQLException SqlEx)
        {            
            // ("Se genera el error de origen");            
            bool = false;
            conexion.desconectar();
            throw new SQLException(SqlEx);
        }                    

            conexion.desconectar();   
            return bool;

        
    }    
    
    
       
    
    
    public Integer ejecutarSQL ( String strSQL, String strID ) 
            throws SQLException {
        
        Integer intRetornar = 0;        
        Conexion conexion = new Conexion();
        conexion.conectar();  
        ResultSet resultset ;
        
        try
        {            
            Statement  stmt = conexion.getConexion().createStatement();    
            resultset  = stmt.executeQuery( strSQL );     
            
            if(resultset.next()) 
            {    
                intRetornar = Integer.parseInt(resultset.getString(strID));
                resultset.close();
            }                                                
        }
        catch (SQLException ex)
        {
            conexion.desconectar();
            throw new SQLException(ex);
        }    
                  
        conexion.desconectar();        
        return intRetornar;
    }    

    
    public Object filtrarIdpol ( Object objeto, Object codigoID ) throws Exception {
             
        Object returnObjecto = null ;
        Conexion conexion = new Conexion();
        conexion.conectar();            
        Statement  statement ;
        ResultSet resultset;


        try
        {            
            statement = conexion.getConexion().createStatement();                           
            resultset = statement.executeQuery( selectSQLpol(objeto, codigoID) );    

            
            
            if(resultset.next()) 
            {    
                returnObjecto = this.extraerRegistro(resultset, objeto);
            }
            
            resultset.close();
            conexion.desconectar(); 
                        
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getMessage());
        }    
                
        conexion.desconectar();               
        return returnObjecto;
        
    }        
    
    
    
    public Object filtrarId ( Object objeto, String codigoID ) throws Exception     
    {
        return this.filtrarIdpol(objeto, codigoID);        
    }    
        
    
    
    
    
    public Object filtrarId ( Object objeto, Integer codigoID ) throws Exception 
    {        
        return this.filtrarIdpol(objeto, codigoID);        
    }    
    
    
    
    
      
    public String selectSQL (Object objeto, Integer codigoID)  
    {       
      return this.selectSQLpol(objeto, codigoID) ;                 
    }    
    

    
    
    public String selectSQL (Object objeto, String codigoID)  
    {       
            return this.selectSQLpol(objeto, codigoID) ;                 
    }    



    public String selectSQLpol (Object objeto, Object codigoID)  {       
          
        String strSelect = " select " ;       
        String strFrom = " from " ;       
        String strWhere = " where " ;       
      
        Serializacion serializacion = new Serializacion(objeto);                
        serializacion.getElementos() ;
        
        Nexo elemento = new Nexo();
        strSelect = strSelect + serializacion.getElementos().get(1).getTabla() ;       
        strFrom = strFrom + serializacion.getElementos().get(0).getTabla() ;       
        strWhere = strWhere  + serializacion.getElementos().get(1).getTabla();       
               
        for (int i = 2; i < serializacion.getElementos().size() ; i++) 
        {              
            elemento = serializacion.getElementos().get(i);                        
            
            if (!elemento.isSelect()){                                
                continue;            
            }                
            strSelect = strSelect + " , "+ elemento.getTabla().toString() ;       
        }
        
        
        if (codigoID.getClass().equals( Integer.class )){
            strWhere = strWhere +  " = " + codigoID.toString();                 
        }else{
            if (codigoID.getClass().equals( String.class )){
                strWhere = strWhere +  " like '" + codigoID.toString() +"' ";    
            }
        }

      
        
        String sql =  strSelect + " " + strFrom +" "+strWhere ;         
        
      return sql ;         
      
    }    

    
    
    
    /*
   public String selectSQL (Object objeto, Object objetoRelacionado, String strBuscar)
            throws Exception  {       
                               
      return this.basicSQL(objeto, objetoRelacionado, strBuscar, "select") ;   
      
    }    
      */
   
   
   
   
    /*
   public String countSQL (Object objeto, Object objetoRelacionado, String strBuscar)
            throws Exception  {       
                        
      return this.basicSQL(objeto, objetoRelacionado, strBuscar, "count") ;   
      
    }    
  
   
  

     public String countSQL(Object objeto, String strBuscar) 
            throws Exception  {       
                        
      return this.basicSQL(objeto, null, strBuscar, "count");   
     
    }    
  */
   
   
   
        
    public Object extraerRegistro ( HttpServletRequest request, Object objeto) throws Exception {
    
            Object instanciaObjeto = null;
            HashMap registro = new HashMap();
            RegistroMap registoMap = new RegistroMap();
            registro = registoMap.convertirHashMap(request, objeto); 
            instanciaObjeto = extraerObjeto(registro,objeto);    
            return instanciaObjeto;  
    }
    
    
    
    
    
    
    
    public Object extraerRegistro ( ResultSet resultset, Object objeto) throws Exception {
        
        
        Object instanciaObjeto = null;
        HashMap registro = new HashMap();
        RegistroMap registoMap = new RegistroMap();      
        registro = registoMap.convertirHashMap(resultset);           

        instanciaObjeto = extraerObjeto(registro,objeto);    

        return instanciaObjeto;          
        
    }

    
    
    


    public Object extraerRegistro ( Map<String, Object> map, Object objeto) throws Exception {
        
        Object instanciaObjeto = null;
        HashMap registro = new HashMap();
        RegistroMap registoMap = new RegistroMap();
        registro = registoMap.convertirHashMap(map);           
        instanciaObjeto = extraerObjeto(registro,objeto);    
        return instanciaObjeto;          
        
    }

        
    
    
    
    
    
    
    public Object extraerObjeto ( HashMap parametro_registro, Object objeto) 
     {
         
        try
        {
            
            HashMap registro  = new HashMap();
            registro  = parametro_registro;

            Object instanciaObjeto = null;
            
            Serializacion serializacion = new Serializacion(objeto);                
            //serializacion.getElementos() ;
            Nexo elemento = new Nexo();
            Class classDato = null;
            Method metodoSet = null;
            String valorCampo = null;
            
            Class claseObjeto = Class.forName(objeto.getClass().getName());
            instanciaObjeto = claseObjeto.newInstance();
            
            for (int i = 1; i < serializacion.getElementos().size() ; i++)
            {
                elemento = serializacion.getElementos().get(i); 
                //elemento.getObjeto();
                //elemento.getTabla();
                serializacion.getElementos().get(i).getObjeto();


                

                valorCampo  = (String) registro.get(elemento.getTabla());
                classDato = instanciaObjeto.getClass().getMethod(elemento.nombreMetodoGET()).getReturnType();
                metodoSet = instanciaObjeto.getClass().getMethod(elemento.nombreMetodoSET(), classDato );       



                
                
                if (  registro.get(elemento.getTabla()) == null )
                {
                    
                    Boolean selectnot = false;
                    for (int g = 0; g < elemento.getAtributo().size() ; g++)
                    {
                        if (elemento.getAtributo().get(g).getNombre().equals("selectnot")){
                                selectnot = true;
                                break;
                        }                    
                    }

                    if (selectnot == false){
                        metodoSet.invoke(instanciaObjeto, (Object) null);  
                    }
                }
                else
                {                                        
                    
                    valorCampo  = (String) registro.get(elemento.getTabla());
                    classDato = instanciaObjeto.getClass().getMethod(elemento.nombreMetodoGET()).getReturnType();
                    metodoSet = instanciaObjeto.getClass().getMethod(elemento.nombreMetodoSET(), classDato );                                                    

                    
                    if (classDato == Integer.class)
                    {
                        valorCampo = valorCampo.replace(",", "");
                        valorCampo = valorCampo.replace(".", "");
                        metodoSet.invoke(instanciaObjeto, Integer.parseInt(valorCampo));                    
                    }
                    else if (classDato == Long.class) 
                    {
                        valorCampo = valorCampo.replace(",", "");
                        valorCampo = valorCampo.replace(".", "");
                        metodoSet.invoke(instanciaObjeto, Long.parseLong(valorCampo));
                    }

                    
                    else if (classDato == Float.class) 
                    {
                        valorCampo = valorCampo.replace(",", "");
                        metodoSet.invoke(instanciaObjeto, Float.parseFloat(valorCampo));
                        
                    }                    

                    else if (classDato == String.class) 
                    {
                       
                        // posiblemente se tenga que poner una opcion para no traer el valor del pass encriptado
                        metodoSet.invoke(instanciaObjeto, (valorCampo));  
                        
                    }
                    else if (classDato == Date.class) 
                    {
                        // aca posiblente se le tenga que poner un formateardo de fechas
                        metodoSet.invoke(instanciaObjeto, Datetime.castDate(valorCampo));
                        
                    }
                    else
                    {

                        Persistencia persistencia = new Persistencia();                    
                        Object instanciaAuxiliarObjeto;                        
                        instanciaAuxiliarObjeto = classDato.newInstance();

                        if (valorCampo.trim().matches("[0-9]*")) 
                        {                        
                            instanciaAuxiliarObjeto = persistencia.filtrarId( instanciaAuxiliarObjeto, Integer.parseInt(valorCampo));
                        } 
                        else 
                        {                            
                            instanciaAuxiliarObjeto = persistencia.filtrarId( instanciaAuxiliarObjeto, (valorCampo) );
                        }

                        //instanciaAuxiliarObjeto = persistencia.filtrarId( instanciaAuxiliarObjeto, Integer.parseInt(valorCampo));
                        metodoSet.invoke( instanciaObjeto, instanciaAuxiliarObjeto );
                        
                        
                    }               
                }
            }
                       
            
                String clasenom = instanciaObjeto.getClass().getSimpleName();
                String ext = clasenom.substring(clasenom.length()-3, clasenom.length());
                
                
                if (ext.equals("Ext") ){         
                    instanciaObjeto.getClass().getMethod("extender").invoke(instanciaObjeto);
                }            
                
            return instanciaObjeto;    
            
            
        } 
        catch (Exception ex) {
            
            //Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());

            
            return null;            
        } 
        
        
    }    
    
    
    
    
    public Object sqlToObject ( String strSQL, Object objeto) throws Exception {
            
        Object instanciaObjeto = null;

        Conexion conexion = new Conexion();
        conexion.conectar();

        Statement stmt =conexion.getConexion().createStatement();      
        ResultSet resultset;
        resultset  = stmt.executeQuery(strSQL);
        resultset.next();
        
        if (resultset.getRow() != 0) 
        {
            HashMap registro = new HashMap();
            RegistroMap registroMap = new RegistroMap();
            registro = registroMap.convertirHashMap(resultset);              
            instanciaObjeto = extraerObjeto(registro,objeto);                            
        }

        return instanciaObjeto;    
    
    }
    

    public static Object getValorClavePrimaria ( Object objeto) throws Exception {
        
        Object objetoReturn = 0;        
                  
        Serializacion serializacion = new Serializacion();        
        serializacion.generar(objeto);        
        serializacion.getElementos() ;
        
        Nexo elemento = new Nexo();
        String nombreMetodo = "";
        
        int i = 1;
            elemento = serializacion.getElementos().get(i);            
                         
            nombreMetodo = "get" + elemento.getObjeto().toString().substring(0,1).toLowerCase().toUpperCase() 
                    + elemento.getObjeto().toString().substring(1).toLowerCase() ;
                        
        // si es Id de tabla
        Class tipoclase = objeto.getClass().getMethod(nombreMetodo).invoke(objeto).getClass();     
        
        
        if (tipoclase == Integer.class){
            objetoReturn = (Integer) objeto.getClass().getMethod(nombreMetodo).invoke(objeto);
        }
        if (tipoclase == String.class){
            objetoReturn = objeto.getClass().getMethod(nombreMetodo).invoke(objeto).toString();
        }        
            
            
        return objetoReturn;
        
    }
        
       
        
}





