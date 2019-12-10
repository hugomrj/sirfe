

   
function consulta05_form_inicio(){    


        ajax.url = html.url.absolute()+'/publico/consulta05/htmf/form.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formcent" ).innerHTML =  ajax.public.html();       
   
           // cargar combo
        reporte_cargar_combo();
    
        //formdet       
        ajax.url = html.url.absolute()+'/publico/consulta05/htmf/resultado.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formdet" ).innerHTML =  ajax.public.html();  
        
        consulta05_interaccion();
        
        consulta05_datos ();        
    
}
        
    


function consulta05_interaccion (){        
    
    
        var estado_resolucion = document.getElementById("estado_resolucion");
        estado_resolucion.onchange  = function(){          
            
            consulta05_datos ();            
        }                         
            

    


    
        var fecha_desde = document.getElementById("fecha_desde");
        fecha_desde.onchange  = function(){            
            
            consulta05_datos ();            
        }                         
    
        
        var fecha_hasta = document.getElementById("fecha_hasta");
        fecha_hasta.onchange  = function(){            
            
            consulta05_datos ();            
        }                         
    
        
        
    var consejo_desde = document.getElementById('consejo_desde');          
    consejo_desde.onblur  = function() {             
        
            consejo_desde.value  =  fmtNum(consejo_desde.value) ;            
            consejo_desde.value  =  NumQP(consejo_desde.value);
                    
            var para = consejo_desde.value;
        
            ajax.url = html.url.absolute()+'/api/consejosalud/'+para+'' ;
            ajax.metodo = "GET";   
            var_json = ajax.private.json();        
            
            try {
                var oJson = JSON.parse( var_json ) ;     
                document.getElementById('consejo_desde_descripcion').value =  oJson["descripcion"] ;                   
            } catch(e) {
                document.getElementById('consejo_desde_descripcion').value =  "";                   
            }    
            
            consulta05_datos();
    };            
        
        
        
        var buscar_consejo_desde = document.getElementById('ico-more-consejo_desde');         
        buscar_consejo_desde.onclick = function(  )
        {              
            var ob = new ConsejoSalud();                            
            ob.acctionresul = function(id) {    
                consejo_desde.value = id;
                consejo_desde.onblur(); 
            };                    
            modal.ancho = 760;                        
            busqueda.modal.objeto(ob);           
            tabla.id = "consejosalud-tabla";
            tabla.oculto = [3]
            tabla.ocultar();            
        };   
                
        
        
        
        
    var consejo_hasta = document.getElementById('consejo_hasta');          
    consejo_hasta.onblur  = function() {             
        
            consejo_hasta.value  =  fmtNum(consejo_hasta.value) ;            
            consejo_hasta.value  =  NumQP(consejo_hasta.value);
                    
            var para = consejo_hasta.value;
        
            ajax.url = html.url.absolute()+'/api/consejosalud/'+para+'' ;
            ajax.metodo = "GET";   
            var_json = ajax.private.json();        
            
            try {
                var oJson = JSON.parse( var_json ) ;     
                document.getElementById('consejo_hasta_descripcion').value =  oJson["descripcion"] ;                   
            } catch(e) {
                document.getElementById('consejo_hasta_descripcion').value =  "";                   
            }         
            consulta05_datos();
    };            
        
        
        
        var buscar_consejo_hasta = document.getElementById('ico-more-consejo_hasta');         
        buscar_consejo_hasta.onclick = function(  )
        {              
            var ob = new ConsejoSalud();                            
            ob.acctionresul = function(id) {    
                consejo_hasta.value = id;
                consejo_hasta.onblur(); 
            };                    
            modal.ancho = 760;                        
            busqueda.modal.objeto(ob);       
            tabla.id = "consejosalud-tabla";
            tabla.oculto = [3]
            tabla.ocultar();
            
        };   
                
                
        
        
        

}
    






function consulta05_datos (){        
    
        var estado_resolucion = document.getElementById("estado_resolucion");    
    
        var fecha_desde = document.getElementById("fecha_desde");
        var fecha_hasta = document.getElementById("fecha_hasta");
        var consejo_desde = document.getElementById('consejo_desde');    
        var consejo_hasta = document.getElementById('consejo_hasta');    
        
    
    
    

        ajax.url = html.url.absolute()+'/api/consejosalud/consulta/consulta05/'+                
                estado_resolucion.value+'/'+
                dateToString ( fecha_desde.value )+'/'+
                dateToString ( fecha_hasta.value )+'/'+
                consejo_desde.value+'/'+
                consejo_hasta.value;
               
                
            ajax.metodo = "GET";   
            var json = ajax.private.json();           

            
            tabla.json = json ;
            tabla.html = "";               
            //var obj = new RendicionVerificacion();    

            tabla.id =  "consulta-tabla";
            tabla.linea =  "consejo";
            tabla.campos = ['consejo', 'descripcion',  'sum_depositado'];;                    
            tabla.tbody_id = "consulta-tb";    

            var obj = new Object();
            obj.tablaformat =  ['C', 'C', 'N'];   

            tabla.gene();

            tabla.formato(obj);
            
            
            // totat foot
            var ojson = JSON.parse(tabla.json) ;   
            var general_rendicion = document.getElementById("general_rendicion");   
            
            try {
                general_rendicion.innerHTML = fmtNum( ojson[0]['general_depositado'] );
            }
            catch(error) {
                general_rendicion.innerHTML = fmtNum( "0" );
            }            
            
            general_rendicion.style = "text-align: right";
            
            

            

                
        

}
    






function reporte_cargar_combo (){        
    
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
    
    
    
    