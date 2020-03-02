

   
function reporte_form_inicio(dom){    


        ajax.url = html.url.absolute()+'/reporte/rendiciongasto/htmf/form.html'; 
        ajax.metodo = "GET";            
        document.getElementById( dom ).innerHTML =  ajax.public.html();       
      
      
        // obtener numero de consejo        
        consejosalud_cabeceraformulario();
        
        var ico_consejo = document.getElementById('ico_consejo');
        
        if (globlal_consejo.value  == '0'){
            

                var ico_more_consejosalud = document.getElementById('ico-more-consejosalud');         
                ico_more_consejosalud.onclick = function(  )
                {              
                    var ob = new ConsejoSalud();     
                    //ob.recurso =   'consejosalud/verificador';
                    ob.recurso =   'consejosalud';

                    ob.acctionresul =  function (id) { 
                        
                        document.getElementById('globlal_consejo').value = id;
                        document.getElementById('globlal_consejo').onblur();
                        
                        //verificacion_tabla_gene ( );  
                    }

                    ob.oculto = [2,3];    

                    modal.ancho = 700;   
                    paginacion.pagina = 1;            
                    busqueda.modal.objeto( ob );

                    //ob.tablacamposoculto = [2,3];    
                    tabla.id = "consejosalud-tabla";            
                    tabla.oculto  = ob.oculto ; 

                    tabla.ocultar();     
                };   



                var verificacion_consejo_cod = document.getElementById('globlal_consejo');          
                verificacion_consejo_cod.onblur  = function() {             


                        verificacion_consejo_cod.value  = fmtNum(verificacion_consejo_cod.value);
                        verificacion_consejo_cod.value  = NumQP(verificacion_consejo_cod.value);

                        var para = verificacion_consejo_cod.value ;        
                        ajax.url = html.url.absolute()+'/api/consejosalud/'+para ;
                        ajax.metodo = "GET";   
                        var_json = ajax.private.json();        


                        try {
                            var oJson = JSON.parse( var_json ) ;     
                            document.getElementById('globlal_consejo_descripcion').value =  oJson["descripcion"] ;                   
                        } catch(e) {
                            document.getElementById('globlal_consejo_descripcion').value =  "";                   
                        }       
                };     
            
        }
        
        
        else{
            ico_consejo.style.display = "none";              
            globlal_consejo.disabled = "true";
        }
        
        
        
     
        
        var buscar_transferencia = document.getElementById('ico-more-transferencia');         
        buscar_transferencia.onclick = function(  )
        {              
            
            modal.ancho = 500;                        
            paginacion.pagina = 1;
            
            
            var ins = {  
                    tipo: 'transferenciafondo',        
                    url:  html.url.absolute() 
                        +  '/aplicacion/transferenciafondo-a/htmf/lista_resolucion.html',
                    venactiva: '',
                    titulosin: 'singular',
                    tituloplu: 'Buscar numero de resolucion',
                    recurso:  'transferenciasfondos/admin/resoluciones',
                    api: '',
                    tablacampos :  [ 'resolucion_numero'],
                    campoid: 'resolucion_numero',
                    carpeta: '/aplicacion',
                    
                    acctionresul: function (id) { 
                         document.getElementById('transferencia_resolucion_numero').value = id;
                    
                    },
                    
                };
            
            
            //busqueda.modal.custom(ins);           
            
            busqueda.modal.objeto( ins );
            
        };   
        

         
        
        
        var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');
        transferencia_resolucion_numero.onblur =function(){
            
    //            vista_tabla_rediciones ( );
        }        
        

    
        
        
        var reporte_acciones = document.getElementById( "reporte-acciones" );
        
        boton.blabels = ['Imprimir' , 'a Excel'];          
        reporte_acciones.innerHTML = boton.get_botton_base();
        
        var btn__imprimir = document.getElementById('btn__imprimir');
        btn__imprimir.addEventListener('click',
            function(event) {        
                

                
                
                
                
                
                    
                    var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');            
                
                    ajax.url = html.url.absolute()+"/AnexoB09/Reporte/existe"
                        +"?resolucion="+transferencia_resolucion_numero.value
                        +"&consejo="+document.getElementById('globlal_consejo').value;                
                
                    var ret = ajax.public.json();
                    

                
                if (ret.toString().trim() == 'false' ){                    
                    msg.error.mostrar("No existe datos para el reporte");
                } 
                else
                {
                    
                    ajax.url = html.url.absolute()+"/AnexoB09/Reporte/rendicion.pdf"
                        +"?resolucion="+transferencia_resolucion_numero.value
                        +"&consejo="+document.getElementById('globlal_consejo').value;
                    ajax.private.jasper();     
                    
                }
                    
                    
                    
            },
            false
        );    
            
        
        
        
        
        var btn__a_excel = document.getElementById('btn__a_excel');
        btn__a_excel.addEventListener('click',
            function(event) {        
                
                
                    
                    var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');            

                
                
                    ajax.url = html.url.absolute()+"/AnexoB09/Reporte/existe"
                        +"?resolucion="+transferencia_resolucion_numero.value
                        +"&consejo="+document.getElementById('globlal_consejo').value;                
                
                    var ret = ajax.public.json();
                
                
                if (ret.toString().trim() == 'false' ){                    
                    msg.error.mostrar("No existe datos para el reporte");
                } 
                else
                {
                    
                    
                    ajax.url = html.url.absolute()+"/AnexoB09/Reporte/rendicion.xls"
                        +"?resolucion="+transferencia_resolucion_numero.value
                        +"&consejo="+document.getElementById('globlal_consejo').value;    
                    
                    
                    
                    
                    ajax.req.open("POST", ajax.url, true);
                    ajax.req.responseType = 'blob';
                    
                    ajax.req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    
                    ajax.req.onload = function (e) {
                        if (ajax.req.readyState === 4 && ajax.req.status === 200) {  
                            var contenidoEnBlob = ajax.req.response;
                            var link = document.createElement('a');
                            link.href = (window.URL || window.webkitURL).createObjectURL(contenidoEnBlob);
                    
                            
                            //link.download = "archivo_"+aa.value+"_"+mm.value+".xls";
                            link.download = "anexoB09_"+transferencia_resolucion_numero.value+"_"
                                    +document.getElementById('globlal_consejo').value+".xls";
                            
                            var clicEvent = new MouseEvent('click', {
                                'view': window,
                                'bubbles': true,
                                'cancelable': true
                            });
                            link.dispatchEvent(clicEvent);

                            ajax.headers.getResponse();                    
                            
                        }
                        else 
                        {
                            if (ajax.req.status === 401){
                                    ajax.state = ajax.req.status;                   
                                    html.url.redirect(ajax.state);
                            }
                            else{
                                alert(" No es posible acceder al archivo");
                            }
                            
                        }
                    };                    
                    
                    ajax.headers.setRequest();  
                    
                    ajax.req.send();
                    
                }
            },
            false
        );    
            
                
        


}
        
        
