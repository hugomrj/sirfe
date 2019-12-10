

   
function reporte01_form_inicio(){    


        ajax.url = html.url.absolute()+'/publico/consulta01/htmf/form.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formcent" ).innerHTML =  ajax.public.html();       
   

        // cargar combo
        reporte01_cargar_combo();
    
    
        //formdet       
        ajax.url = html.url.absolute()+'/publico/consulta01/htmf/resultado.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formdet" ).innerHTML =  ajax.public.html();  
        
        reporte01_interaccion();
        
        reporte01_datos ();        
    
}
        
        




function reporte01_cargar_combo (){        
    
        var estado_resolucion = document.getElementById("estado_resolucion");
        var idedovalue = estado_resolucion.value;
   
      
        ajax.url = html.url.absolute()+'/api/resoluciones/estados/all' ;
        ajax.metodo = "GET";   
        var datajson = ajax.private.json();               

    
        var oJson = JSON.parse( datajson ) ;
        
        for( x=0; x < oJson.length; x++ ) {
            
            var jsonvalue = (oJson[x]['estado'] );            
            
            if (idedovalue != jsonvalue )
            {  
                var opt = document.createElement('option');            
                opt.value = jsonvalue;
                opt.innerHTML = oJson[x]['descripcion'];                        
                estado_resolucion.appendChild(opt);                     
            }
            
        }

}
    
    
    
    


function reporte01_interaccion (){        
    
    
        var estado_resolucion = document.getElementById("estado_resolucion");
        estado_resolucion.onchange  = function(){          
            
            reporte01_datos ();            
        }                         
            

    
        var fecha_desde = document.getElementById("fecha_desde");
        fecha_desde.onchange  = function(){            
            
            reporte01_datos ();            
        }                         
    
        
        var fecha_hasta = document.getElementById("fecha_hasta");
        fecha_hasta.onchange  = function(){            
            
            reporte01_datos ();            
        }                         
    
        
        
        

}
    






function reporte01_datos (){        
    
    
        var estado_resolucion = document.getElementById("estado_resolucion");    
        var fecha_desde = document.getElementById("fecha_desde");
        var fecha_hasta = document.getElementById("fecha_hasta");
    
    
    

        ajax.url = html.url.absolute()+'/api/consejosalud/consulta/consulta01/'+
                estado_resolucion.value+'/'+
                dateToString ( fecha_desde.value )+'/'+
                dateToString ( fecha_hasta.value );
        
        
            ajax.metodo = "GET";   
            var json = ajax.private.json();           

           
            tabla.json = json ;
            tabla.html = "";               
            //var obj = new RendicionVerificacion();    

            tabla.id =  "consulta-tabla";
            tabla.linea =  "cod";
            tabla.campos = ['cod', 'descripcion',  'total_rendicion'];;                    
            tabla.tbody_id = "consulta-tb";    

            var obj = new Object();
            obj.tablaformat =  ['C', 'C', 'N'];   

            tabla.gene();

            tabla.formato(obj);
            
            
            // totat foot
            var ojson = JSON.parse(tabla.json) ;   
            var general_rendicion = document.getElementById("general_rendicion");   
            
            try {
                general_rendicion.innerHTML = fmtNum( ojson[0]['general_rendicion'] );
            }
            catch(error) {
                general_rendicion.innerHTML = fmtNum( "0" );
            }            
            
            general_rendicion.style = "text-align: right";
            
            

            

                
        

}
    

