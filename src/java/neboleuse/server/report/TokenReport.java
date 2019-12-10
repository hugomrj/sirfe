/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neboleuse.server.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nebuleuse.seguridad.Autentificacion;

/**
 *
 * @author hugo
 */
@WebServlet(name = "TokenReport", 
        urlPatterns = {"/TokenReport"}
)

public class TokenReport extends HttpServlet {



    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("application/html;charset=UTF-8");
                
        
        String strToken = null;
        
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            //String value = request.getHeader(key);            
            if (key.equals("token")){
                strToken = request.getHeader(key);
            }
        }
        
        //String token = request.getHeaders("token").toString();        
        if (strToken == null ){            
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else{
            
            try {                                

                Autentificacion autorizacion = new Autentificacion();
                if (autorizacion.verificar(strToken))
                {  
                    
                    autorizacion.actualizar();
                    
                    strToken = autorizacion.encriptar();
                    response.addHeader("token", strToken );
                    

                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("sesiontoken", strToken );
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);

                }
                else
                {  
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }

                
            } 
            catch (SQLException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }            
            
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
