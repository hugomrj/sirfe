/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import nebuleuse.util.FileXls;
import py.com.aplicacion.rendicion_gasto.RendicionGastoRS;




/**
 *
 * @author hugo
 */
public class Practica01 {


    
    public static void main(String args[]) {
    
        try {
            
                    String par_resolucion = "61/19";
                    Integer par_consejo = 402;
                    
                    

                FileXls filexls = new FileXls();
            
                
                filexls.path = "C:\\Server\\apache-tomcat-8.0.22\\webapps\\sirfe\\WEB-INF\\";
                        
                //filexls.iniciar(request);
                filexls.folder = "/files";                
                
        filexls.name = "/base"+ par_consejo.toString().trim() +".xls";
                
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
            
            
            
            
            resulset.next();
            
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("Decreto " 
                        + resulset.getString("decreto")   + " / "  +  resulset.getString("agno")  );
                row++;            
                
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("ANEXO B-09 - PLANILLA DE EJECUCION DE INGRESOS Y GASTOS");
            row++;            
                
            
            filexls.newfila(row);                                    
            filexls.getFila().createCell(0).setCellValue("OBJETO DEL GASTO - 834 OTRAS TRASNF. AL SECT. PUBL. Y ORG. REGIONALES");
            row++;            
            row++;

            

            
            
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
                filexls.getFila().createCell(0).setCellValue("(3) PROGRAMA:");
                filexls.getFila().createCell(1).setCellValue( resulset.getString("programa"));
                row++;            
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("(4) SUBPROGRAMA:");
                filexls.getFila().createCell(1).setCellValue( resulset.getString("subprograma"));
                row++;            
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("(5) PROYECTO: ");
                filexls.getFila().createCell(1).setCellValue("00");
                row++;                            
                row++;            
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("A) INGRESOS");
                row++;            
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("Saldo Anterior (6)");
                filexls.getFila().createCell(1).setCellValue("Origen de Ingreso (7)");
                filexls.getFila().createCell(2).setCellValue("Concepto (8)");
                filexls.getFila().createCell(3).setCellValue("Recibo o Factura N° (9)");
                filexls.getFila().createCell(4).setCellValue("Comprobante Nro (10)");
                filexls.getFila().createCell(5).setCellValue("Fecha de deposito (11)");
                filexls.getFila().createCell(6).setCellValue("Total Deposito G. (12)");
                row++;         
                
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue(resulset.getString("saldo_anterior"));
                filexls.getFila().createCell(1).setCellValue(resulset.getString("origen_ingreso"));
                filexls.getFila().createCell(2).setCellValue("Transferencias de la Tesoreria General");
                filexls.getFila().createCell(3).setCellValue(resulset.getString("recibo_numero"));
                filexls.getFila().createCell(4).setCellValue(resulset.getString("comprobante_numero"));
                filexls.getFila().createCell(5).setCellValue(resulset.getString("deposito_fecha"));
                filexls.getFila().createCell(6).setCellValue(Integer.parseInt(resulset.getString("total_depositado")));                
                row++;         
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(4).setCellValue("TOTAL DEPOSITADO (13)");                
                filexls.getFila().createCell(6).setCellValue(Integer.parseInt(resulset.getString("total_depositado")));                
                row++;         

                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(4).setCellValue("SALDO ACUMULADO (14) TOTAL DEL PERIODO + ACUMULADO ");                
                
                Integer total = Integer.parseInt(resulset.getString("total_depositado")) +
                        Integer.parseInt(resulset.getString("saldo_anterior"));
                
                filexls.getFila().createCell(6).setCellValue(total);                
                row++;                            
                row++;            
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("B) EGRESOS");
                row++;              
                
                
                
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("Tipo de Comprobante (15)");
                filexls.getFila().createCell(1).setCellValue("Comprobante Nro (16)");
                filexls.getFila().createCell(2).setCellValue("Objeto del Gasto (17)");
                filexls.getFila().createCell(3).setCellValue("Concepto (18)");
                filexls.getFila().createCell(4).setCellValue("Fecha (19)");
                filexls.getFila().createCell(5).setCellValue("Importe en Gs (20)");
                filexls.getFila().createCell(6).setCellValue("Observaciones (21)");
                row++;                        


                Integer sumimporte = 0;
                Integer totalrendir = 0;
                
                totalrendir = Integer.parseInt(resulset.getString("total_rendir"));
                
                
                do {

                    filexls.newfila(row);                                    
                    filexls.getFila().createCell(0).setCellValue(resulset.getString("comprobante_descripcion"));
                    filexls.getFila().createCell(1).setCellValue(resulset.getString("rendicion_comprobante_numero"));
                    filexls.getFila().createCell(2).setCellValue(resulset.getString("objeto"));
                    filexls.getFila().createCell(3).setCellValue(resulset.getString("concepto"));
                    filexls.getFila().createCell(4).setCellValue(resulset.getString("rendicion_fecha"));
                    filexls.getFila().createCell(5).setCellValue(Integer.parseInt(resulset.getString("importe")));
                    
                    sumimporte = sumimporte + Integer.parseInt(resulset.getString("importe"));
                    
                    filexls.getFila().createCell(6).setCellValue(resulset.getString("rendicion_observacion"));
                    row++;                    

                } while (resulset.next());                    
                
                
                
                filexls.newfila(row);                      
                filexls.getFila().createCell(3).setCellValue("TOTAL DE GASTOS (22) ");      
                filexls.getFila().createCell(5).setCellValue(sumimporte);   
                row++;                    
                
                
                filexls.newfila(row);                      
                filexls.getFila().createCell(3).setCellValue("SALDO A RENDIR (23) ");      
                filexls.getFila().createCell(5).setCellValue(totalrendir - sumimporte );   
                
                
                
                // firmas del consejo
                
                row++;                    
                row++;                    
                row++;                    
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("C) FIRMA DE LOS RESPONSABLES DEL CONSEJO (24)");
                row++;                  
                row++;   
                row++;   
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(1).setCellValue("FIRMA DEL PRESIDENTE O");
                filexls.getFila().createCell(3).setCellValue("FIRMA DEL TESORERO O ADMINISTRADOR");
                filexls.getFila().createCell(6).setCellValue("CONTADOR");
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(1).setCellValue("TITULAR");                
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(1).setCellValue("Firma, sello o aclaración");
                filexls.getFila().createCell(3).setCellValue("Firma, sello o aclaración");
                filexls.getFila().createCell(6).setCellValue("Firma, sello o aclaración");
                row++;   
                
                
                row++;  
                row++;  
                filexls.newfila(row);                                    
                filexls.getFila().createCell(0).setCellValue("CONSTANCIA DE PRESENTACION DE PLANILLA AL M.S.P.B.S (25)");
                row++;                                  
                row++;                                  
                
                
                filexls.newfila(row);     
                filexls.getFila().createCell(1).setCellValue("FECHA DE RECEPCION:");
                filexls.getFila().createCell(3).setCellValue("JEFE UAF");
                filexls.getFila().createCell(6).setCellValue("HORA:");
                row++;   
                row++;   
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(2).setCellValue("NOTA: LA PRESENTE PLANILLA TIENE CARACTE DE DECLARACION JURADA");                
                row++;   
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(0).setCellValue("La presentación del formulario Anexo B-09 - \"Planilla de Ejecución y Gastos\", al Ministerio de Salud Pública y Bienestar Social, contituirá un documento legal a los efectos de los");                
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(0).setCellValue("registros presupuestarios y contables en los sistemas vigentes de contabilidad. No constituye un examen de la rendición de cuentas presentada. El examen de cuentas será");                
                row++;   
                
                filexls.newfila(row);     
                filexls.getFila().createCell(0).setCellValue("realizado posteriormente de acuerdo a las Normas de Auditoria Generalmente Aceptadas y disposiciones");                
                //row++;   
                
                
            
                //filexls.writeContenido(resultset);                
                filexls.save();
                filexls.newFileStream();
                
                
                
                filexls.close();
                
                            
            
            
            
            
            
            
            
            
            
            
            
        } catch (Exception ex) {
            System.out.println( ex.getCause() );
        }
        
        
    }

        
        

}
















