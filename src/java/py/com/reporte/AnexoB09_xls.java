/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.reporte;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nebuleuse.seguridad.Autentificacion;
import nebuleuse.util.FileBin;
import nebuleuse.util.FileXls;
import py.com.aplicacion.rendicion_gasto.RendicionGastoRS;
//import py.com.base.aplicacion.relacionlaboral.RelacionLaboralRS;
//import py.com.base.aplicacion.sociodescuento.SocioDescuentoRS;

/**
 *
 * @author hugo
 */
@WebServlet(name = "AnexoB09_xls", 
        urlPatterns = {"/AnexoB09/Reporte/rendicion.xls"})
public class AnexoB09_xls extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, Exception
 {
        //response.setContentType("text/html;charset=UTF-8");
        
     
     
        
            String strToken = "";
        
            strToken = request.getHeader("token");
            Autentificacion autorizacion = new Autentificacion();
            
            if (autorizacion.verificar(strToken))
            {  
            
                
                
                    String par_resolucion = "";
                    Integer par_consejo = 0;
                    
                    par_resolucion = request.getParameter("resolucion").toString() ;  
                    par_consejo = Integer.parseInt( request.getParameter("consejo").toString() ) ;  
                    
                    

                FileXls filexls = new FileXls();
                
                filexls.iniciar(request);
                filexls.folder = "/files";                
                filexls.name = "/base.xls";                
        
                ArrayList<String> textofijo = new ArrayList<String>();
                Integer row = 0;
                

        
                
                
                
                ArrayList<String> campos = new ArrayList<String>();
                campos.add("socio");
                campos.add("cedula");                
                campos.add("nombre_apellido");                
                campos.add("servicio_descripcion");                
                campos.add("monto_descuento");                
                campos.add("mes");                
                campos.add("agno");                
                campos.add("fecha");                
                filexls.setCampos(campos);                
                
                
                
                RendicionGastoRS rs = new RendicionGastoRS();     
                ResultSet resulset = rs.anexoB09(par_resolucion, par_consejo);
                
                
            //filexls.gen(resultset);                       
                
                
                filexls.newlibro();
                filexls.newhoja();
                
                
                        
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("PRESIDENCIA DE LA REPUBLICA");
            row++;            
                
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("Ministerio de Hacienda");
            row++;            
                
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("Decreto 9999 / 2019");
            row++;            
                
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("ANEXO B-09 - PLANILLA DE EJECUCION DE INGRESOS Y GASTOS");
            row++;            
                
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("OBJETO DEL GASTO - 834 OTRAS TRASNF. AL SECT. PUBL. Y ORG. REGIONALES");
            row++;            

            
            resulset.next();
            
            
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("(1) ENTIDAD:");
                filexls.getFila().createCell(1).setCellValue(  
                        resulset.getString("descripcion") + " - Resol. DGDS N° " + resulset.getString("resolucion_numero")  );
                row++;            
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("(2) TIPO DE PRESUPUESTO:");
                filexls.getFila().createCell(1).setCellValue("1 Programas Centrales");
                row++;            
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("(2) TIPO DE PRESUPUESTO:");
                filexls.getFila().createCell(1).setCellValue( resulset.getString("programa"));
                row++;            
                
            
            
/*                
do {
     statement(s)
} while (expression);                
   */             
                
                
            
                //filexls.writeContenido(resultset);                
                filexls.save();
                filexls.newFileStream();
                
                
                ServletContext context = getServletContext();
                

                response.setHeader("token", autorizacion.encriptar());
                
                filexls.getServeltFile(request, response, context);
                
                filexls.close();
                
                
                
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
            Logger.getLogger(AnexoB09_xls.class.getName()).log(Level.SEVERE, null, ex);
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
