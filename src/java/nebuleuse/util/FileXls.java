/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nebuleuse.util;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author hugo
 */
public class FileXls extends FileBin {

    public HSSFRow getFila() {
        return fila;
    }

    public void setFila(HSSFRow fila) {
        this.fila = fila;
    }
    
    
    private HSSFWorkbook libro;
    private HSSFSheet hoja;
    private HSSFRow fila;
    private ArrayList <String> cabecera =  new ArrayList<>();
    private ArrayList <String> campos =  new ArrayList<>();
    
    
        
    public  void gen ( ResultSet resulset ) throws SQLException, Exception {
        
        /*
            fila.createCell(0).setCellValue("priimera celda");
            fila.createCell(1).setCellValue(1.2);
            fila.createCell(2).setCellValue("This is a string");
            fila.createCell(3).setCellValue(true);
        */
       
        
            //ArrayList <String> cabecera = null;
     
            /*
            for (String elemento : elementos) {
            }
            */
            
            
            
            this.newlibro();
            this.newhoja();
            this.writeCabecera(0);
            
            
            this.writeContenido(resulset);
            
        
        // Se salva el libro.
        try {

                String ruta = "";
                ruta = this.getFilePath();

                
                FileOutputStream file = new FileOutputStream( ruta );                
                this.libro.write(file);
                file.close();                
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    public  void newlibro () {        
        this.libro = new HSSFWorkbook();        
    }    
    

    public  void newhoja () {        
        this.hoja = this.libro.createSheet("hoja01");
    }    
    
    
    public  void newfila (Integer indice) {        
        this.setFila(this.hoja.createRow(indice));
    }        
    
    
    
    
    public  void writeCabecera (Integer indice) {        
        
        this.newfila(0);

        int i = 0;
        for (String titulo : cabecera) {        
            this.getFila().createCell(i).setCellValue( titulo );
            i++;
        }
    }            

    
    
    
    public  void writeContenido ( ResultSet resulset ) throws SQLException {        

        Integer nrofila = 1;        

        while (resulset.next()) {            
            //String em = resulset.getString("EM_ID");
            int i = 0;
            
            this.newfila(nrofila);
            
            for (String campo : this.campos) {        
                String em = resulset.getString(campo);                
                this.getFila().createCell(i).setCellValue( em );
                i++;
            }            
            nrofila++;
        }        
                 

    }            

    
    

    public  void gen (Integer indice) {        
        
        this.newfila(0);

        int i = 0;
        for (String titulo : cabecera) {        
            this.getFila().createCell(i).setCellValue( titulo );
            i++;
        }
    }            
    
    
    
    
    
    
    
    
    public ArrayList <String> getCabecera() {
        return cabecera;
    }

    public void setCabecera(ArrayList <String> cabecera) {
        this.cabecera = cabecera;
        
    }

    public ArrayList <String> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList <String> campos) {
        this.campos = campos;
    }
    


    public  void save ( ) {
        
        try {

                String ruta = "";
                ruta = this.getFilePath();

                
                FileOutputStream file = new FileOutputStream( ruta );                
                this.libro.write(file);
                file.close();                
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    

    
}    
    

