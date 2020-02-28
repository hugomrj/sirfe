/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hugo
 */
public class FileBin {
    
     public String name;       
     public String path;
     public String folder;
     
     private File file;
     private FileInputStream inStream;
     private OutputStream outStream;
     
     
    public void iniciar( HttpServletRequest request )   {
        this.path = request.getServletContext().getRealPath("/WEB-INF");        
    }     
    
    
    public String getFilePath(){    
        return this.path + this.folder + this.name;
    }
    
    
    public void newFileStream(  ) throws FileNotFoundException   {        
        
        this.file = new File( this.getFilePath()  );        
        this.inStream = new FileInputStream(this.getFile());                        
        
    }         

    

    
    public void getServeltFile( HttpServletRequest request,
            HttpServletResponse response,
            ServletContext context            
            ) throws IOException {        
        

                // gets MIME type of the file
                //String mimeType = context.getMimeType(filePath);
                String mimeType = context.getMimeType( this.getFilePath() );
                if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);

                // modifies response
                response.setContentType(mimeType);
                //response.setContentLength((int) downloadFile.length());
                response.setContentLength((int) this.getFile().length()  );

                // forces download
                String headerKey = "Content-Disposition";
                //String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                String headerValue = String.format("attachment; filename=\"%s\"",
                        this.getFile().getName());
                
                
                response.setHeader(headerKey, headerValue);                
        
                                
                
                
                              
            
                
                

                // obtains response's output stream
                //OutputStream outStream = response.getOutputStream();
                //this.outStream = response.getOutputStream();
                this.outStream = new DataOutputStream(response.getOutputStream());
                
                
                
                
                
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                //while ((bytesRead = inStream.read(buffer)) != -1) {
                while ((bytesRead = this.getInStream().read(buffer)) != -1) {
                    this.outStream.write(buffer, 0, bytesRead);
                }

                this.outStream.flush();
                      
                
                
    }         


    
    
    public void close(  ) throws IOException    {      
        this.getInStream().close();
        this.outStream.close();      
    }         

    
    
    
    
    public File getFile() {
        return file;
    }

    public FileInputStream getInStream() {
        return inStream;
    }
    
    
    
    
    
    
}
