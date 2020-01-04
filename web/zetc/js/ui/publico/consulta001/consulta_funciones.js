

   
function consulta_form_inicio(){    


        ajax.url = html.url.absolute()+'/publico/consulta001/htmf/form.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formcent" ).innerHTML =  ajax.public.html();       
     
    
        //formdet       
        ajax.url = html.url.absolute()+'/publico/consulta001/htmf/resultado.html'; 
        ajax.metodo = "GET";            
        document.getElementById( "formdet" ).innerHTML =  ajax.public.html();  
        
        consulta_interaccion();
        
        consulta_datos ();        
    
}
        
    


function consulta_interaccion (){        
    

    
        var fecha_desde = document.getElementById("fecha_desde");
        fecha_desde.onchange  = function(){                        
            consulta_datos ();            
        }                         
    
        
        var fecha_hasta = document.getElementById("fecha_hasta");
        fecha_hasta.onchange  = function(){                        
            consulta_datos ();            
        }                         
    
    
    var dpto_desde = document.getElementById('dpto_desde');          
    dpto_desde.onblur  = function() {             
        
            dpto_desde.value  =  fmtNum(dpto_desde.value) ;            
            dpto_desde.value  =  NumQP(dpto_desde.value);
            
             var para = dpto_desde.value;
        
            ajax.url = html.url.absolute()+'/api/departamentos/'+para+'' ;
            ajax.metodo = "GET";   
            var_json = ajax.private.json();        
            
            try {
                var oJson = JSON.parse( var_json ) ;     
                document.getElementById('dpto_desde_descripcion').value =  oJson["descripcion"] ;                   
            } catch(e) {
                document.getElementById('dpto_desde_descripcion').value =  "";                   
            }    
            
            consulta_datos();
    };            

    
    
    
        var buscar_dpto_desde = document.getElementById('ico-more-dpto_desde');         
        buscar_dpto_desde.onclick = function(  )
        {              
            
            var ob = new Departamento();                            
            ob.acctionresul = function(id) {    
                dpto_desde.value = id;
                dpto_desde.onblur(); 
            };                    
            modal.ancho = 760;                        
            
            paginacion.pagina = 1;
            busqueda.modal.objeto(ob);           
            tabla.id = "departamento-tabla";
            tabla.oculto = [2]
            tabla.ocultar();                        
        };   
    
    
    
    
    
    
    var dpto_hasta = document.getElementById('dpto_hasta');          
    dpto_hasta.onblur  = function() {             
        
            dpto_hasta.value  =  fmtNum(dpto_hasta.value) ;            
            dpto_hasta.value  =  NumQP(dpto_hasta.value);


             var para = dpto_hasta.value;
        
            ajax.url = html.url.absolute()+'/api/departamentos/'+para+'' ;
            ajax.metodo = "GET";   
            var_json = ajax.private.json();        
            
            try {
                var oJson = JSON.parse( var_json ) ;     
                document.getElementById('dpto_hasta_descripcion').value =  oJson["descripcion"] ;                   
            } catch(e) {
                document.getElementById('dpto_hasta_descripcion').value =  "";                   
            }    
            
        consulta_datos();
    };            
    

    
        var buscar_dpto_hasta = document.getElementById('ico-more-dpto_hasta');         
        buscar_dpto_hasta.onclick = function(  )
        {              
            
            var ob = new Departamento();                            
            ob.acctionresul = function(id) {    
                dpto_hasta.value = id;
                dpto_hasta.onblur(); 
            };                    
            modal.ancho = 760;                        
            
            paginacion.pagina = 1;
            busqueda.modal.objeto(ob);           
            tabla.id = "departamento-tabla";
            tabla.oculto = [2]
            tabla.ocultar();            
            
        };   
    
            
        
        
        
        
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
            
            consulta_datos();
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
            
            ob.recurso = "consejosalud/depto/" + dpto_desde.value +"/"+dpto_hasta.value;
            ob.tablacampos =  ['cod', 'descripcion', 'dpto_descripcion', 'dpto'];
            ob.oculto = [3];
            
            
            paginacion.pagina = 1;
            busqueda.modal.objeto(ob);           
            tabla.id = "consejosalud-tabla";
            
            tabla.oculto = [3];
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
            consulta_datos();
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
            
            ob.recurso = "consejosalud/depto/" + dpto_desde.value +"/"+dpto_hasta.value;
            ob.tablacampos =  ['cod', 'descripcion', 'dpto_descripcion', 'dpto'];
            ob.oculto = [3];
            
            paginacion.pagina = 1;
            busqueda.modal.objeto(ob);           
            tabla.id = "consejosalud-tabla";
            
            tabla.oculto = [3];
            tabla.ocultar();            
        };   
                      
        
        
        

}
    






function consulta_datos (){        
    
    
        var fecha_desde = document.getElementById("fecha_desde");
        var fecha_hasta = document.getElementById("fecha_hasta");
        
        var dpto_desde = document.getElementById("dpto_desde");
        var dpto_hasta = document.getElementById("dpto_hasta");
        
        var consejo_desde = document.getElementById('consejo_desde');    
        var consejo_hasta = document.getElementById('consejo_hasta');    
        
    
    
    

        ajax.url = html.url.absolute()+'/api/consultas/consulta001/'+                
                dateToString ( fecha_desde.value )+'/'+
                dateToString ( fecha_hasta.value )+'/'+
                dpto_desde.value+'/'+
                dpto_hasta.value+'/'+
                consejo_desde.value+'/'+
                consejo_hasta.value;
               
                
            ajax.metodo = "GET";   
            var json = ajax.private.json();           
            
            tabla.json = json ;
            tabla.html = "";               
            //var obj = new RendicionVerificacion();    

            tabla.id =  "consulta-tabla";
            tabla.linea =  "consejo";
            tabla.campos = [ 'dpto',  'dpto_descrip',  'consejo', 'consejo_descrip',  'cant_trasferencia', 'total_depositado'];
            tabla.tbody_id = "consulta-tb";    

            var obj = new Object();
            obj.tablaformat =  ['N', 'C', 'C',  'C',  'N', 'N'];   

            tabla.gene();

            tabla.formato(obj);
            
            
            // totat foot
            var ojson = JSON.parse(tabla.json) ;   
            var general_rendicion = document.getElementById("total_general");   
            
            try {
                general_rendicion.innerHTML = fmtNum( ojson[0]['general_depositado'] );
            }
            catch(error) {
                general_rendicion.innerHTML = fmtNum( "0" );
            }            
            
            general_rendicion.style = "text-align: right";
            
            

            

                
        

}
    

