/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package py.com.reporte;



import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.postgres.Conexion;
import nebuleuse.seguridad.Autentificacion;
import nebuleuse.util.Convercion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author hugo
 */

@WebServlet(name = "AnexoB09_Reporte", 
        urlPatterns = {"/AnexoB09/Reporte/rendicion.pdf"})


public class AnexoB09_Reporte extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
            HttpSession sesion = request.getSession();        
        
        try { 
        
                    String par_resolucion = "";
                    Integer par_consejo = 0;
                    
                    par_resolucion = request.getParameter("resolucion").toString() ;  
                    par_consejo = Integer.parseInt( request.getParameter("consejo").toString() ) ;  
            
                
                Autentificacion autorizacion = new Autentificacion();
                String strToken =  (String) sesion.getAttribute("sesiontoken");
                
                
                
                
                if (autorizacion.verificar(strToken))
                {                      

                    
                    
                    String archivo = "AnexoB09";            
                    String archivo_jrxml = archivo+".jrxml";
                    response.setHeader("Content-disposition","inline; filename="+archivo+".pdf");
                    response.setContentType("application/pdf");

                          //response.setContentType("image/jpeg");            
            //Response.Status status  = Response.Status.OK;

                    


                    /*
                    Long monto_total = 0L;            
                    monto_total  = Long.parseLong(request.getParameter("mtotal")) ;  
                    */

                    Conexion cnx = new Conexion();
                    cnx.conectar();
                    String url =  request.getServletContext().getRealPath("/WEB-INF")+"/jasper/";
                    url = url + archivo_jrxml;

                    // parametros
                    Map<String, Object> parameters = new HashMap<String, Object>();

                    parameters.put("par_resolucion", par_resolucion );            
                    parameters.put("par_consejo", par_consejo );            
                    parameters.put("par_usuario", autorizacion.token.getNombre());            
                    
String report_path = request.getServletContext().getRealPath("/WEB-INF")+"/jasper/";;
report_path = report_path.replace("\\", "/") ;

                    parameters.put("report_path", report_path );    
                    
                    
                    //Convercion conversion = new Convercion();            
                    //parameters.put("par_monto_letras", conversion.numeroaLetras(monto_total));

                    JasperReport report = JasperCompileManager.compileReport(url);            
                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, cnx.getConexion());


                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    byte[] reportePdf = JasperExportManager.exportReportToPdf(jasperPrint);

                    response.setContentLength(reportePdf.length);

                    servletOutputStream.write(reportePdf, 0, reportePdf.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();    

                    
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                }
                else
                {  
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
                
                
//                autorizacion.actualizar();


            
            
            
        }         
        catch (JRException ex) 
        {
            Logger.getLogger(AnexoB09_Reporte.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        finally
        {          
            sesion.invalidate();
        }

            
            
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AnexoB09_Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AnexoB09_Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
