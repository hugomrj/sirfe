/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.consulta;


import com.google.gson.JsonArray;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nebuleuse.seguridad.Autentificacion;
import nebuleuse.util.FileXls;
import py.com.aplicacion.rendicion_gasto.RendicionGastoRS;
//import py.com.base.aplicacion.relacionlaboral.RelacionLaboralRS;
//import py.com.base.aplicacion.sociodescuento.SocioDescuentoRS;

/**
 *
 * @author hugo
 */

@WebServlet(name = "ConsultaXLS_001", 
        urlPatterns = {"/consulta/publico/001.xls"})

public class ConsultaXLS_001 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, Exception
 {
        //response.setContentType("text/html;charset=UTF-8");
        
     
     
        
            String strToken = "";
        
            strToken = request.getHeader("token");
            Autentificacion autorizacion = new Autentificacion();
            
            if (autorizacion.verificar(strToken))
            {  
            
                String fechadesde = "";
                fechadesde = request.getParameter("fecha_desde").toString() ;  


                String fechahasta = "";                    
                fechahasta = request.getParameter("fecha_hasta").toString() ;  
                    
                Integer dpto_desde = 0;                    
                dpto_desde = Integer.parseInt( request.getParameter("dpto_desde").toString()) ;  

                Integer dpto_hasta = 0;                    
                dpto_hasta = Integer.parseInt( request.getParameter("dpto_hasta").toString()) ;  
                    
                Integer consejo_desde = 0;                    
                consejo_desde = Integer.parseInt( request.getParameter("consejo_desde").toString()) ;  

                Integer consejo_hasta = 0;                    
                consejo_hasta = Integer.parseInt( request.getParameter("consejo_hasta").toString()) ;  
                    
                Integer tipo_transferencia = 0;                    
                tipo_transferencia = Integer.parseInt( request.getParameter("tipo_transferencia").toString()) ;  

                    
                    
                    
                    //par_resolucion = "61/19";
                    //par_consejo = 402;
                    
                    


                if (!( fechadesde.trim().equals("0") || fechahasta.trim().equals("0")) )
                {
                    
                    FileXls filexls = new FileXls();

                    filexls.iniciar(request);
                    filexls.folder = "/files";                
                    filexls.name = "/base"+ ".xls";                

                    ArrayList<String> textofijo = new ArrayList<String>();
                    Integer row = 0;

                    ArrayList<String> cabecera = new ArrayList<String>();
                    cabecera.add("dpto");
                    cabecera.add("dpto descripcion");                
                    cabecera.add("codigo");                
                    cabecera.add("consejo salud");                
                    cabecera.add("cant. transferencias");                
                    cabecera.add("importe");                
                    filexls.setCabecera(cabecera);


                    ArrayList<String> campos = new ArrayList<String>();
                    campos.add("dpto");
                    campos.add("dpto_descrip");
                    campos.add("consejo");
                    campos.add("consejo_descrip");
                    campos.add("cant_trasferencia");
                    campos.add("total_depositado");
                    filexls.setCampos(campos);                


                    /*
                    RendicionGastoRS rs = new RendicionGastoRS();     
                    ResultSet resulset = rs.anexoB09(par_resolucion, par_consejo);
                    */

                    ConsultaRS rs = new ConsultaRS();            
                    ResultSet resulset = rs.consulta001(fechadesde, fechahasta, 
                        dpto_desde, dpto_hasta, 
                        consejo_desde, consejo_hasta, 
                        tipo_transferencia);




                    filexls.gen(resulset);                       

                    //filexls.writeContenido(resultset);                
                    filexls.save();
                    filexls.newFileStream();

                    ServletContext context = getServletContext();                
                    response.setHeader("token", autorizacion.encriptar());                
                    filexls.getServeltFile(request, response, context);                
                    filexls.close();

                }                
            }
            else{   
                //System.out.println("no autorizado");                
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            
            
            
            
                
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ConsultaXLS_001.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
