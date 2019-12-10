/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nebuleuse.ORM.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import nebuleuse.ORM.Atributo;
import nebuleuse.ORM.Nexo;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;


public final  class Serializacion {    

        
        private String ruta =  this.getClass().getResource("/").getPath().replaceAll("classes", "xml");

 
        private ArrayList <Nexo> elementos = new ArrayList <Nexo>()  {
            { 
                add(new Nexo());  
                add(new Nexo());
            }
        };
              
        public Serializacion() {            
            
        }
        
        public Serializacion( Object objeto)  {
            this.generar(objeto) ;        
        }

        public void generar( Object objeto)  {
            
            try 
            {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                
                String xmlfile = objeto.getClass().getSimpleName();
                String ext = xmlfile.substring(xmlfile.length()-3, xmlfile.length());
                
                if (ext.equals("Ext") ){
                    xmlfile = xmlfile.substring(0, xmlfile.length() - 3);;
                }
                       
                File fileXML = new File(this.ruta + xmlfile + ".xml");
                
                if (fileXML.exists())             
                {        
                    Document doc = dBuilder.parse(fileXML);      
                    doc.normalizeDocument();
                    
                    // Obtenemos la etiqueta raiz  
                    Element elementRaiz = doc.getDocumentElement();  
                    this.recorrerNodo(elementRaiz, objeto);                               
                
                }
                else
                {                    
                    System.out.println((this.ruta+objeto.getClass().getSimpleName()+".xml"));
                    System.out.println("No existe archivo xml");
                    
                }
            }

            
            catch (SAXException ex) 
            {
                Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
            }            catch (IOException ex) {
                Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
            }            catch (ParserConfigurationException ex) {
                Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  

        
    public void recorrerNodo (Node n, Object objeto)  {
                       
        NodeList hijos = n.getChildNodes();
        Nexo elemento = new Nexo();
        boolean isElemento = false;
            
        for ( int i = 0; i < hijos.getLength(); i++ )
        {
            Node nodo = hijos.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) 
            { 
                             
                NamedNodeMap attributesList = nodo.getAttributes();
                ArrayList <Atributo> listaAtributos = new ArrayList<Atributo>();      
                
                for (int j = 0; j < attributesList.getLength(); j++) 
                {
    
                    // se carga el nombre de la tabla y el indice y las demas columnas                                                 
                    if (nodo.getNodeName().equals("table"))
                    {                        
                        if (attributesList.item(j).getNodeName() .equals("name"))
                        {                                                       
                            this.getElementos().get(0).setTabla(attributesList.item(j).getNodeValue());
                            this.getElementos().get(0).setObjeto(objeto.getClass().getSimpleName());                                
                        }
                    }                                
                    else if ((nodo.getNodeName().equals("id"))) 
                    {            
                        
                        if (attributesList.item(j).getNodeName() .equals("name"))
                        {
                            // se copia el nombre del campo y la propiedad iguales
                            this.getElementos().get(1).setTabla(attributesList.item(j).getNodeValue());                                                                
                            this.getElementos().get(1).setObjeto(attributesList.item(j).getNodeValue());                                                                
                        }
                        else if (attributesList.item(j).getNodeName() .equals("property"))
                        {   
                            // se copia la propiedad en el caso que exista                                                             
                            this.getElementos().get(1).setObjeto(attributesList.item(j).getNodeValue());                                
                        }
                        
                    }                        
                    // recorre las columnas            
                    else if ((nodo.getNodeName().equals("column"))) 
                    {       

                        if (attributesList.item(j).getNodeName().equals("name"))
                        {       
                            // se copia el nombre del campo y la propiedad iguales
                            elemento.setTabla(attributesList.item(j).getNodeValue());
                            elemento.setObjeto(attributesList.item(j).getNodeValue());
                            isElemento = true;                            
                        }// fin if name
                        
                        
                        else if (attributesList.item(j).getNodeName() .equals("property"))
                        {
                            // se copia la propiedad en el caso que exista                                                            
                            elemento.setObjeto(attributesList.item(j).getNodeValue());
                            isElemento = true;
                        }

                        else if (attributesList.item(j).getNodeName() .equals("foreign"))
                        {
                            // se copia la propiedad en el caso que exista                                                             
                            //elemento.setObjeto(attributesList.item(j).getNodeValue());

                           listaAtributos.add(new Atributo("foreing", attributesList.item(j).getNodeValue()));
                           isElemento = true;             
                        }
                        
                        else if (attributesList.item(j).getNodeName() .equals("updatenull"))
                        {
                            // se copia la propiedad en el caso que exista                                                             
                            //elemento.setObjeto(attributesList.item(j).getNodeValue());

                           listaAtributos.add(new Atributo("updatenull", attributesList.item(j).getNodeValue()));
                           isElemento = true;             
                        }
                        
                        else if (attributesList.item(j).getNodeName() .equals("insertnot"))
                        {
                           listaAtributos.add(new Atributo("insertnot", attributesList.item(j).getNodeValue()));
                           isElemento = true;             
                        }
                        
                        else if (attributesList.item(j).getNodeName() .equals("selectnot"))
                        {
                           listaAtributos.add(new Atributo("selectnot", attributesList.item(j).getNodeValue()));
                           isElemento = true;             
                        }
                        
                        
                    }      
                    else 
                    {                                             
                        System.out.println(" No se encontro etiqueta xml  "+nodo.getNodeName() );    
                    }  
                }

                
                // agreaga elemento en la lista de elementos
                if ((isElemento == true))
                {
                    Nexo nvoEnlace = new Nexo(elemento.getTabla(), elemento.getObjeto() );
                    nvoEnlace.setAtributo(listaAtributos);
                  
                    this.getElementos().add(nvoEnlace);
         
                }                    
                
                // llamada a recursividad
                 if ( nodo.hasChildNodes() == true)
                {
                    recorrerNodo(nodo, objeto);
                }                            
            }
        }  
    }
        
   
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public ArrayList <Nexo> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList <Nexo> elementos) {
        this.elementos = elementos;
    }
   
}
    
        
   


