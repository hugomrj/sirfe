/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.usuario;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;


/**
 *
 * @author hugom_000
 */

public class UsuarioDAO  {
        
    /*
        Conexion conexion = new Conexion();
        Statement  statement ;
        ResultSet resultset;  
        Lista lista ;
        Integer lineas = Integer.parseInt(new Global().getValue("lineasLista"));
    */ 
        
    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    private Gson gsonf = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();                
    
        
    
    public UsuarioDAO ( ) throws IOException  {

    }
       
       
    
    public Usuario existeUsuario(String cuenta, String pass) throws Exception {      

          Usuario objUsuario = new Usuario();  

          String sql = 
                    " SELECT usuario, cuenta, clave "
                    + " FROM sistema.usuarios "
                    + " WHERE cuenta like '"+cuenta+"'  " 
                    + "and clave like md5('"+pass+"')" ;  
                
          objUsuario = (Usuario) persistencia.sqlToObject(sql, objUsuario);

          return objUsuario;          

      }
     
    
    
      public Usuario login(String cuenta, String pass) throws Exception {      

          Usuario usuario = new Usuario();                      
          String sql = (new UsuarioSQL().login(cuenta, pass)); 

          usuario = (Usuario) persistencia.sqlToObject(sql, usuario);          
          return usuario;          
      }
                 
      
    
    
      public Usuario login(String cuenta, String pass, Integer succ) throws Exception {      

          Usuario usuario = new Usuario();                      
          //String sql = persistencia.selectSQL(contador, usuario ) ; 
          String sql = (new UsuarioSQL().login(cuenta, pass, succ)); 
          
          usuario = (Usuario) persistencia.sqlToObject(sql, usuario);          
          return usuario;          
      }
                 
      
      
            
      
      
      
      
      public void set_tokenDB (String usuario, String tokkenIat) 
              throws SQLException{
          
        String sql =  "   UPDATE sistema.usuarios\n" +
                         "   SET token_iat='"+tokkenIat+"'\n" +
                         "   WHERE usuario = " + usuario; 

        persistencia.ejecutarSQL(sql);
      }
        
      
      
      
      
      
      public String get_tokenDB (Integer codUser ) 
              throws SQLException, Exception{
          
        String sql = "	SELECT usuario, cuenta, clave, token_iat\n" +
                        "	  FROM sistema.usuarios\n" +
                        "	  where usuario = " + codUser; 
        
        Usuario usuario = new Usuario();                      
        usuario = (Usuario) persistencia.sqlToObject(sql, usuario); 
        
        return usuario.getToken_iat();
      }
        
      
      
      
      
    public Usuario insert (String cuenta, String pass ) 
              throws SQLException, Exception{
        
            String sql = new UsuarioSQL().insert(cuenta, pass);
      
            Integer cod  =  0;
            cod = persistencia.ejecutarSQL ( sql, "usuario") ;

            Usuario usuario = new Usuario();                      
            usuario = (Usuario) persistencia.filtrarId(usuario, cod) ; 

            return usuario;
    }
        
      
      
      
      
    public Usuario update (Usuario u, Integer codigo ) 
              throws SQLException, Exception{
        
            String sql = new UsuarioSQL().update(
                                                            u.getCuenta(),
                                                            u.getClave(),
                                                            codigo );

            
            Integer cod  =  0;
            cod = persistencia.ejecutarSQL ( sql, "usuario") ;

            Usuario usuario = new Usuario();                      
            usuario = (Usuario) persistencia.filtrarId(usuario, cod) ; 

            return usuario;
    }
        
      
      
      
    public Integer delete (Integer codigo ) {
                
        Integer filas = 0;

        try {
            
            String sql = new UsuarioSQL().delete(codigo);

            filas = persistencia.ejecutarSQL ( sql, "resultado");
            
        } 
        
        catch (SQLException ex) {
            throw new SQLException(ex);
        } 
        catch (Exception ex) {
            throw new Exception(ex);        
        }
        finally{
            return filas;
        }

    }
        
      
          
    
    
    
      
      
    
    public List<Usuario>  list (Integer page) {
                
        List<Usuario>  lista = null;
        
        try {                        
                        
            UsuarioRS usuarioRs = new UsuarioRS();
            
            lista = new Coleccion<Usuario>().resultsetToList(
                    new Usuario(),
                    usuarioRs.list(page)
            );            
            
            this.total_registros = usuarioRs.total_registros  ;
            
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
      

    
    
    public List<Usuario>  search (Integer page, String busqueda) {
                
        List<Usuario>  lista = null;
        
        try {                        
                        
            UsuarioRS usuarioRs = new UsuarioRS();
            
            
            lista = new Coleccion<Usuario>().resultsetToList(
                    new Usuario(),
                    usuarioRs.search(page, busqueda)
            );            
            
            this.total_registros = usuarioRs.total_registros  ;
            
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
      
     
    
 
    public boolean IsControlPath (Integer usu, String path) 
            throws SQLException, IOException, Exception        {
        
        UsuarioRS usuarioRs = new UsuarioRS();

        
        if (usuarioRs.controlpath(usu, path).next()){ 
            return true;
        }            
        else{
            return false;
        }
        
    }      
        
       
      public Usuario setJson (String json) throws Exception {  
          
            Usuario o = new Usuario();     
            o = gsonf.fromJson(json, Usuario.class);      
            
          return o;          
      }
              
    
      
       
      
      
      
    
    public Integer cambiarPass(String usuario, String anterior, String nuevo) {      

        try {
            
            //Usuario objUsuario = new Usuario();
            
            String sql =
                    new UsuarioSQL().cambiarPass(usuario, anterior, nuevo);
            
            Integer i = persistencia.ejecutarSQL ( sql, "usuario" ) ;
                      
            return i;
            
            
        } 
        catch (Exception ex) 
        {
            return 0;
        }

      }
      
    
    
      
    
        
}
