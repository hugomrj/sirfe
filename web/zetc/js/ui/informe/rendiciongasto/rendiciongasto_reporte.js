

   
function reporte_form_inicio(dom){    


        ajax.url = html.url.absolute()+'/reporte/rendiciongasto/htmf/form.html'; 
        ajax.metodo = "GET";            
        document.getElementById( dom ).innerHTML =  ajax.public.html();       
      
     
        
        var buscar_transferencia = document.getElementById('ico-more-transferencia');         
        buscar_transferencia.onclick = function(  )
        {              
            
            modal.ancho = 500;                        

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
        



        var transferenciafondo_consejo_cod = document.getElementById('transferenciafondo_consejo_cod');          
        transferenciafondo_consejo_cod.onblur  = function() {             


                transferenciafondo_consejo_cod.value  
                        =  fmtNum(transferenciafondo_consejo_cod.value) ;

                transferenciafondo_consejo_cod.value  =  NumQP(transferenciafondo_consejo_cod.value);

                var para = transferenciafondo_consejo_cod.value;

                ajax.url = html.url.absolute()+'/api/consejosalud/'+para+'' ;
                ajax.metodo = "GET";   
                var_json = ajax.private.json();        


                try {
                    var oJson = JSON.parse( var_json ) ;     
                    document.getElementById('transferenciafondo_consejo_descripcion').value =  oJson["descripcion"] ;                   
                } catch(e) {
                    console.log(e); 
                    document.getElementById('transferenciafondo_consejo_descripcion').value =  "";                   
                }            

        };     



    
    
        
        
        var reporte_acciones = document.getElementById( "reporte-acciones" );
        
        boton.blabels = ['Imprimir'];          
        reporte_acciones.innerHTML = boton.get_botton_base();
        
        var btn__imprimir = document.getElementById('btn__imprimir');
        btn__imprimir.addEventListener('click',
            function(event) {        
                
                
                    
                    var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');            
                
                        
                    ajax.url = html.url.absolute()+"/AnexoB09/Reporte/rendicion.pdf"
                        +"?resolucion="+transferencia_resolucion_numero.value
                        +"&consejo="+transferenciafondo_consejo_cod.value;
                    ajax.private.jasper();                         
            },
            false
        );    
            
        
        
        


}
        
        
