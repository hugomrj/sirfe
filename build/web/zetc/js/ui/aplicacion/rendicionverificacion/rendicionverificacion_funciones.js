      

var var_json = "";
paginacion.pagina = 1;        


   
function verificacion_form_inicio(f){    
    
        var dom = 'verificacion_cabecera';              
        verificacion_form_html ( dom );
        
        

        var dom = 'verificacion_detalle';                
        verificacion_lista ( dom );


}
        
        


function verificacion_form_html(dom){
    
    
        ajax.url = html.url.absolute()+'/aplicacion/rendicionverificacion/htmf/form_cabecera.html'; 
        ajax.metodo = "GET";            
        document.getElementById( dom ).innerHTML =  ajax.public.html();           
        

        ajax.url = html.url.absolute()+'/api/verificaciones/veridicador/' ;        
        ajax.metodo = "GET";   
        var json = ajax.private.json();   


    
        var verificador_cuenta = document.getElementById('verificador_cuenta');        
        
        if (ajax.state == 200){            
            var oJson = JSON.parse( json ) ;            
            verificador_cuenta.value = oJson[0]['cuenta'];            
            document.getElementById('departamento_descripcion').value  = oJson[0]['descripcion']; ;                 
                  
        }


        
        var ico_more_consejosalud = document.getElementById('ico-more-consejosalud');         
        ico_more_consejosalud.onclick = function(  )
        {              
                       
            
            var ob = new ConsejoSalud();     
            ob.recurso =   'consejosalud/verificador';
            
            ob.acctionresul =  function (id) { 
                document.getElementById('verificacion_consejo_cod').value = id;
                document.getElementById( 'verificacion_consejo_cod' ).onblur();     
                verificacion_tabla_gene ( );  
            }
            
            busqueda.modal.objeto( ob );
            
            ob.tablacamposoculto = [2,3];    
            tabla.oculto = ob.tablacamposoculto;        
            tabla.ocultar();     

        };   
        



    
        var verificacion_consejo_cod = document.getElementById('verificacion_consejo_cod');          
        verificacion_consejo_cod.onblur  = function() {             
            
                verificacion_consejo_cod.value  = fmtNum(verificacion_consejo_cod.value);

                var para = verificacion_consejo_cod.value ;        
                ajax.url = html.url.absolute()+'/api/consejosalud/'+para+'/verificador' ;
                ajax.metodo = "GET";   
                var_json = ajax.private.json();        


                try {
                    var oJson = JSON.parse( var_json ) ;     
                    document.getElementById('verificacion_consejo_descripcion').value =  oJson["descripcion"] ;                   
                } catch(e) {

                    document.getElementById('verificacion_consejo_descripcion').value =  "";                   
                }       
                
                verificacion_tabla_gene (  );        

        };     
        verificacion_consejo_cod.onblur();    

    

    
    
      
     
        
        var buscar_transferencia = document.getElementById('ico-more-resolucion');         
        buscar_transferencia.onclick = function(  )
        {              
            
            var consejo = 0;
            consejo = verificacion_consejo_cod.value;
            
            
            modal.ancho = 500;                        
            
            var ins = {  
                    tipo: 'transferenciafondo',        
                    url:  html.url.absolute() 
                        +  '/aplicacion/transferenciafondo-a/htmf/lista_resolucion.html',
                    venactiva: '',
                    titulosin: 'singular',
                    tituloplu: 'Buscar numero de resolucion',
                    recurso:  'rendicionesgastos/verificacion/resoluciones/'+consejo,
                    api: '',
                    tablacampos :  [ 'resolucion_numero'],
                    campoid: 'resolucion_numero',
                    carpeta: '/aplicacion',
                    
                    acctionresul: function (id) { 
                         document.getElementById('verificacion_resolucion_numero').value = id;
                         verificacion_tabla_gene ( );  
                    
                    },                    
                };
            //busqueda.modal.custom(ins);           
            busqueda.modal.objeto( ins );            
        };   
        
             
        var verificacion_resolucion_numero = document.getElementById('verificacion_resolucion_numero');
        verificacion_resolucion_numero.onchange  = function(){            
            verificacion_tabla_gene (  );            
        }                         
                 
                 
                 
            
    
}




function verificacion_lista ( dom ){
    
    ajax.url = html.url.absolute()+'/aplicacion/rendicionverificacion/htmf/lista.html'; 
    
    ajax.metodo = "GET";            
    document.getElementById( dom ).innerHTML =  ajax.public.html();          
    
    
}






   function verificacion_tabla_gene (  ){
       

        try {
            
            
            var consejo = 0;            
            var para = verificacion_resolucion_numero.value ;
            
            consejo = verificacion_consejo_cod.value ;

            var regex = /^[\d]+[/][\d]{2}$/;       
            
            if ( !(regex.test( verificacion_resolucion_numero.value.toString().trim())))   
            {
                para = "00/00";
            }               
            
                ajax.url = html.url.absolute()+'/api/verificaciones/'+consejo+'/'+para+"?page="+paginacion.pagina;                
                ajax.metodo = "GET";   
                json = ajax.private.json();       
                
                //var oJson = JSON.parse( json ) ;    
                tabla.json = json ;


                tabla.html = "";               
                var obj = new RendicionVerificacion();    

                tabla.ini(obj);
                tabla.linea =  obj.campoid;
                tabla.campos = obj.tablacampos;                    
                tabla.tbody_id = obj.tbody_id;    
                
                

               tabla.gene();
               tabla.formato(obj);
               tabla.lista_registro( function (id) {edicion_verificacion ( id );} );
               
               
               
               // paginacion
                document.getElementById( obj.tipo + "_paginacion" ).innerHTML 
                = paginacion.gene();    
               
               //var buscar = busqueda.getTextoBusqueda();
               var buscar = "";
        
                verificacion_paginacion_move(obj, buscar, function (id) {edicion_verificacion ( id );} )
                //paginacion.move(obj, buscar, function (id) {edicion_verificacion ( id );}  );    
               
               
                // mostrar estado
                                                                                          
                ajax.url = html.url.absolute()+'/api/resoluciones/estado/transferencia/'+consejo+'/'+para;
                ajax.metodo = "GET";   
                json = ajax.private.json();       
     
                
                if (ajax.state == 200){            
                    var oJson = JSON.parse( json ) ;                                                                               
                    document.getElementById('resolucion_estado_descripcion').value  = oJson['descripcion']; ;                 
                }
                else                {
                    document.getElementById('resolucion_estado_descripcion').value  = ""; ;                 
                }
                





               
                
   
        }
        catch(error) {
                //document.getElementById( dom ).innerHTML = "" ;                        
                //console.log(error)
        }
       
   }



function edicion_verificacion ( id ){
    
    //alert("desded edcion " + id);
       
    
        ajax.url = html.url.absolute()+'/aplicacion/rendicionverificacion/htmf/form.html'; 
        ajax.metodo = "GET";       

        modal.ancho = 800;
        var obj = modal.ventana.mostrar("renve");   
        //selector_modal_editdelete( obj, linea  );    
        


        ajax.url = html.url.absolute()+'/api/verificaciones/'+id ;        
        ajax.metodo = "GET";   
        var json = ajax.private.json();         



        if (ajax.state == 200){            
            var oJson = JSON.parse( json ) ;            
            
            
            document.getElementById('rendiciongasto_consejo').value =  oJson['rendicion']['consejo']['cod'];                   
            document.getElementById('rendiciongasto_consejo_descripcion').value =  oJson['rendicion']['consejo']['descripcion'];                               
            
            document.getElementById('rendiciongasto_resolucion_numero').value =  oJson['rendicion']['resolucion_numero'];
            
            document.getElementById('rendiciongasto_tipo_comprobante').value =  oJson['rendicion']['tipo_comprobante']['descripcion'];
            document.getElementById('rendiciongasto_comprobante_numero').value =  (oJson['rendicion']['comprobante_numero']);
            
            
            document.getElementById('rendiciongasto_objeto_objeto').value =  oJson['rendicion']['objeto']['objeto'];
            document.getElementById('rendiciongasto_objeto_descripcion').innerHTML =  oJson['rendicion']['objeto']['descripcion'];
            
            
            document.getElementById('rendiciongasto_concepto').value =  oJson['rendicion']['concepto'].toString().trim();
            
            
            document.getElementById('rendiciongasto_fecha').value =   jsonToDate(  oJson['rendicion']['fecha']);
            document.getElementById('rendiciongasto_importe').value =  fmtNum(oJson['rendicion']['importe']);
            
            
            
            document.getElementById('rendiciongasto_observacion').value =  oJson['rendicion']['observacion'];
            
            document.getElementById('rendiciongasto_ruc_factura').value =  oJson['rendicion']['ruc_factura'];
            document.getElementById('rendiciongasto_timbrado_venciomiento').value =    jsonToDate(  oJson['rendicion']['timbrado_venciomiento'] );  
            


            // form verificaicon

                document.getElementById('rendicionverificacion_verificacion').value  = oJson['verificacion'];
                document.getElementById('rendicionverificacion_rendicion').value  = oJson['rendicion']['rendicion'];

                if ((oJson['comentario']) === undefined || (oJson['comentario']) === null) {
                     document.getElementById('rendicionverificacion_comentario').value =  "";
                }            
                else{
                     document.getElementById('rendicionverificacion_comentario').value =  oJson['comentario'];
                }



                

                var opt = document.createElement('option');            
                opt.value = oJson['estado']['estado'];                   
                opt.innerHTML = oJson['estado']['descripcion'];                   
                document.getElementById(   "rendicionverificacion_estado" ).appendChild(opt);           

                var rendicionverificacion_estado = document.getElementById("rendicionverificacion_estado");
                var idedovalue = rendicionverificacion_estado.value;

                ajax.url = html.url.absolute()+'/api/verificacionestado/all' ;
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
                        rendicionverificacion_estado.appendChild(opt);                     
                    }
                }    

                    
        }


        //rendiciongasto-acciones
        document.getElementById('rendiciongasto-acciones').innerHTML = boton.basicform.get_botton_edit();


        
        var btn_cancelar = document.getElementById('btn_transferenciafondo_cancelar');         
        btn_cancelar.onclick = function(  )
        {  
            modal.ventana_cerrar("renve");   
        };  





    
        var btn_editar = document.getElementById('btn_transferenciafondo_editar');
        btn_editar.onclick = function(  )
        {  
            
                form.name = "form_rendicionverificacion";
                var jdata = form.datos.getjson() ;      
                

                    var id = document.getElementById('rendicionverificacion_verificacion').value;
                    ajax.metodo = "put";
                    ajax.url = html.url.absolute()+"/api/verificaciones/"+id;               
                    var data = ajax.private.json( jdata );  
                    

                    switch (ajax.state) {

                      case 200:

                            msg.ok.mostrar("actualizado");          
                            verificacion_tabla_gene (  );
                            modal.ventana_cerrar("renve");   

                            break; 
                            
                            
                      case 401:
                            window.location = html.url.absolute();                 
                            break;                                 
                            

                      case 500:

                            msg.error.mostrar( data );          
                            break;                                 


                      case 502:

                            msg.error.mostrar( data );          
                            break; 

                      default: 
                        msg.error.mostrar("error desconocido");           
                    }                        


                        
            
            
        };  


             
    
}







function verificacion_paginacion_move ( obj, buscar,  fn  ){
    
    
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
                                            
                        verificacion_tabla_gene (  );            
                        
                    },
                    false
                );                
            }            
        }      
    
    
    
}




    

    
