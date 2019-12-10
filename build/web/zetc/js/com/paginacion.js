/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
var paginacion = 
{  
    pagina:     1,        
    total_registros: 0,
    
    lineas:       16,
    cantidad_paginas:  0,
    
    pagina_inicio : 1,
    pagina_fin : 0, 
    bloques:    12,
    
    html: "",
    
    setTotalRegistros: function() {        
        paginacion.total_registros = sessionStorage.getItem('total_registros')        
    },
       
        
    ini: function() {
 
        paginacion.setTotalRegistros();

        paginacion.cantidad_paginas = paginacion.total_registros / paginacion.lineas;
        paginacion.cantidad_paginas = Math.trunc(paginacion.cantidad_paginas);
        
        var calresto  = paginacion.cantidad_paginas * paginacion.lineas;
        if (calresto != paginacion.total_registros) {
            paginacion.cantidad_paginas = paginacion.cantidad_paginas + 1;
        }
 
 
        if ((paginacion.cantidad_paginas * paginacion.lineas ) < paginacion.total_registros){
            paginacion.cantidad_paginas++;
        }        

        if (paginacion.bloques > paginacion.cantidad_paginas) {
            paginacion.pagina_inicio = 1;
            paginacion.pagina_fin = paginacion.cantidad_paginas;
        }        
        else{
            if (paginacion.bloques == paginacion.cantidad_paginas) {
                paginacion.pagina_inicio = 1;
                paginacion.pagina_fin = paginacion.bloques;
            }    
            else{ 
                if (paginacion.bloques  < paginacion.cantidad_paginas) {

                    if (paginacion.pagina - (paginacion.bloques / 2) < 1) {
                        paginacion.pagina_inicio = 1;
                        paginacion.pagina_fin = paginacion.pagina + paginacion.bloques;
                    }
                    else{
                        paginacion.pagina_inicio = paginacion.pagina - (paginacion.bloques / 2);
                    }

                    if (paginacion.pagina + (paginacion.bloques / 2) > paginacion.cantidad_paginas )                        {
                        paginacion.pagina_inicio = paginacion.cantidad_paginas - paginacion.bloques;
                        paginacion.pagina_fin = paginacion.cantidad_paginas;
                    }
                    else{
                        paginacion.pagina_fin =  paginacion.pagina + (paginacion.bloques / 2);
                    }    
                }
            }    
        }                 
    },
                
        
    gene: function() {       
        
        
        paginacion.ini();
        
        if (paginacion.total_registros == 0){
            return "";
        }
        
        if (paginacion.cantidad_paginas == 1){
            paginacion.pagina = 1;
        }
        
        
        paginacion.html = "<ul data-paginaactual=\"1\" id=\"paginacion\">";
     

        if (paginacion.pagina != paginacion.pagina_inicio){            
            paginacion.html += "<li data-pagina=\"ant\" >" ;
            
            paginacion.html +=      "<a href=\"javascript:void(0);\"id =\"ant\">" ;
            paginacion.html +=          "anterior" ;
            paginacion.html +=      "</a>" ;
            
            paginacion.html += "</li>";
        }
   
        for (i = paginacion.pagina_inicio; i <= paginacion.pagina_fin; i++) {   

            if (parseInt(paginacion.pagina) == i)
            {                
                paginacion.html += "<li class=\"actual\" data-pagina=\"act\">" ;
                paginacion.html += "<a href=\"javascript:void(0);\" > " ;
                paginacion.html += i ;
                paginacion.html += "</a>" ;
                paginacion.html += "</li>" ;
            }
            else
            {                
                paginacion.html += "<li data-pagina= \"pag" + i + "\"  >" ;
                paginacion.html += "<a href=\"javascript:void(0);\" id =pag"+ i +"> " ;
                paginacion.html += i ;
                paginacion.html += "</a>" ;
                paginacion.html += "</li>" ;                
                
            }
        }
   
        if (paginacion.pagina != paginacion.pagina_fin){            
            paginacion.html += "<li data-pagina=\"sig\" >" ;
            
            paginacion.html +=      "<a href=\"javascript:void(0);\"id =\"sig\">" ;
            paginacion.html +=          "siguiente" ;
            paginacion.html +=      "</a>" ;
            
            paginacion.html += "</li>";
        }
        paginacion.html += " </ul>"
        return paginacion.html;
        
    },

    
    
    
    move: function( obj, busca, fn) {     

        var listaUL = document.getElementById( obj.tipo + "_paginacion" );
        var uelLI = listaUL.getElementsByTagName('li');
        
        for (var i=0 ; i < uelLI.length; i++)
        {
            var datapag = uelLI[i].dataset.pagina;     

            if (!(datapag == "act"  || datapag == "det"  ))
            {
                uelLI[i].addEventListener ( 'click',
                    function() {                                      
                        
                        switch (this.dataset.pagina)
                        {
                           case "sig": 
                                   paginacion.pagina = parseInt(paginacion.pagina) +1;
                                   break;                                                                          

                           case "ant":                                     
                                   paginacion.pagina = parseInt(paginacion.pagina) -1;
                                   break;

                           default:  
                                   paginacion.pagina = this.childNodes[0].innerHTML.toString().trim();
                                   break;
                        }                 
                                                                                                
                                                
                        tabla.refresh( obj, paginacion.pagina, busca, fn  ) ;

                        
                    },
                    false
                );                
            }            
        }           
    },
    
      
    
    
    
    
};
 






    