      

var var_json = "";
        
   
   
function transferencia_form_inicio(f){    

    
        var dom = 'transferencia_cabecera';       
        transferencia_form_html ( dom );
        transferencia_form_accion (  ) ;

                
        
        var dom = 'transferencia_detalle';                
        rendiciongasto_lista_html ( dom ) ;
        rendiciongasto_lista_botones( );        

}
        
        



function transferencia_form_html ( dom ) {
           
        ajax.url = html.url.absolute()+'/aplicacion/transferenciafondo/htmf/form_cabecera.html'; 
        ajax.metodo = "GET";            
        document.getElementById( dom ).innerHTML =  ajax.public.html();           

        consejosalud_cabeceraformulario();
}   




function transferencia_form_accion (  ) {
           
        
        var buscar_transferencia = document.getElementById('ico-more-transferencia');         
        buscar_transferencia.onclick = function(  )
        {              
            var ob = new TransferenciaFondo();                            
            ob.acctionresul = cabecera_sessiondataid;
                                   
            modal.ancho = 760;                        
            busqueda.modal.objeto(ob);            
            
        };   
        

    
        
        var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');
        transferencia_resolucion_numero.onblur =function(){
            
                vista_tabla_rediciones ( );
        }

        
}   



function vista_tabla_rediciones ( ) {
    
    
        var transferencia_resolucion_numero = document.getElementById('transferencia_resolucion_numero');
        var para = transferencia_resolucion_numero.value ;
        var regex = /^[\d]+[/][\d]{2}$/;       

        if ( !(regex.test( transferencia_resolucion_numero.value.toString().trim())))   
        {
            para = "00/00";
        }       

        ajax.url = html.url.absolute()+'/api/rendicionesgastos/session/transferencia/'+para+'' ;
        ajax.metodo = "GET";   
        var_json = ajax.private.json();        


        var oJson = JSON.parse( var_json ) ;             

        
        try {            
            
            document.getElementById('transferencia_transferencia').value =  oJson["transferencia"] ;                        

            document.getElementById('total_rendicion').value =  fmtNum(oJson["total_rendicion"] );
            
            
            
            var saldo_rendir = 0;
            saldo_rendir = ( Number(oJson["saldo_anterior"]) + Number(oJson["total_depositado"]) ) 
                        -  (Number(oJson["total_rendicion"] ));
                
            document.getElementById('saldo_rendir').value =  fmtNum(saldo_rendir);

            globaltransferencia =  oJson["transferencia"] ;     
              
        }
        catch (e) {

            document.getElementById('transferencia_transferencia').value =  "0";                                
            document.getElementById('total_rendicion').value =  "0";                                             
            document.getElementById('saldo_rendir').value =  "0";
            globaltransferencia = "0";
            
        }                        
        rendicion_tabla_gene("rendicion-tb", var_json);   
        
        
        tabla.id = "rendicion-tabla";        
        
        var obj = new RendicionGasto();                 
        tabla.lista_registro(obj, RendicionGasto_registroModal );  
        
    
    
}





function RendicionGasto_registroModal ( obj, linea ){

        ajax.url = html.url.absolute()+'/api/'+obj.recurso+'/'+linea ;
        ajax.metodo = "GET";   
        var var_json = ajax.private.json();               
        
        
        var oJson = JSON.parse( var_json ) ;    
        
        obj.registro_modal(obj, var_json);
        
        obj.form_ini();

};




















function rendiciongasto_lista_html ( dom ) {           
    
    ajax.url = html.url.absolute()+'/aplicacion/rendiciongasto/htmf/lista.html'; 
    ajax.metodo = "GET";            
    document.getElementById( dom ).innerHTML =  ajax.public.html();      
   
   // aca llamar botones de accion de llista
    
}   
   




function rendiciongasto_lista_botones (  ) {
    
    var rendicion_acciones_lista = document.getElementById( "rendicion_acciones_lista" );
   
        var obj = new RendicionGasto();
        boton.ini(obj);    
        rendicion_acciones_lista.innerHTML = boton.basicform.get_botton_att();


    // genera accion de botones de subtabla
    
        var btn_rendiciongasto_agregar = document.getElementById('btn_rendiciongasto_agregar');
        btn_rendiciongasto_agregar.addEventListener('click',
            function(event) {    
                
                // solo si existe cabecera
                
                var transferencia_transferencia = document.getElementById('transferencia_transferencia');
                var json = transferencia_sessiondataid_json(transferencia_transferencia.value);

                if (globaltransferencia == 0) {
                    
                    msg.error.mostrar("Selecciona una transferencia");            
                    
                }
                else
                {                                      
                    
                    var oJson = JSON.parse( json ) ;  
                    var tranferencia_cod  =  oJson["transferencia"] ;                   
                    
                    var obj = new RendicionGasto();                
                    obj.new(obj, oJson);                    
                    
                    
                }    
                //selector_modal_add( obj  );
                
                
                
            },
            false
        );    
      
    
    
}   
   


   
   
   
   
   function rendicion_tabla_gene ( dom, json ){
        

        try {
                var oJson = JSON.parse( json ) ;    

                var oJson = oJson.rendiciones;
                var myJSON = JSON.stringify(oJson);        

                tabla.html = "";               
                var obj = new RendicionGasto();    

                tabla.ini(obj);
                tabla.linea =  obj.campoid;
                tabla.campos = obj.tablacampos;                    
                tabla.tbody_id = obj.tbody_id;    
                tabla.gene_strjson(myJSON);  
                document.getElementById( dom ).innerHTML = tabla.html ;  
                

                var obj = new RendicionGasto();    
                tabla.ini(obj);
                tabla.id = "rendicion-tabla";
                tabla.formato(obj);                
                
                
        }
        catch(error) {
                document.getElementById( dom ).innerHTML = "" ;                        
        }
       
   }



   
   
   
   
   
   

   
function consulta_boton_funciones(){    
    
        
        var btn_consulta_nuevo = document.getElementById('btn_consulta_nuevo');
        btn_consulta_nuevo.addEventListener('click',
            function(event) {    
                
                
                var obj = new Consulta();

                ajax.url = html.url.absolute()+'/aplicacion/consulta/htmf/form.html'; 
                ajax.metodo = "GET";  
                modal.ancho = 740;
                obj.venactiva = modal.ventana.mostrar("cons");                        
                //selector_modal_add( obj  );
                
                
                cargarDoctoresJson( document.getElementById('doctores') );
                forasteroPaciente();
                obj.form_ini();
                
                // guardarConsulta                
                guardarConsulta_modal(obj);
                
            },
            false
        );    

   
    
        var btn_consulta_atras = document.getElementById('btn_consulta_atras');
        btn_consulta_atras.addEventListener('click',
            function(event) {                    
                var agenda_fecha = document.getElementById( "agenda_fecha" );
                filtrar_fecha_agenda(agenda_fecha.value);
            },
            false
        );    
    

}
   
   
   



/**************/

function cabecera_sessiondataid(id){ 
        
            var json =  transferencia_sessiondataid_json(id);
        
       
            var oJson = JSON.parse( json ) ; 
            
            document.getElementById('transferencia_resolucion_numero').value =  oJson["resolucion_numero"] ;            
            document.getElementById('transferencia_resolucion_numero').onblur();
                 
};
        
   
   
function transferencia_sessiondataid_json(id){ 
    
    
        if (id == null || id == ""){
            id = 0;
        }

        ajax.url = html.url.absolute()+'/api/transferenciasfondos/session/'+id ;
        
   
        ajax.metodo = "GET";   
        json = ajax.private.json();             




        return json;

};
    
   
   
   
   
   
   
   


function rendiciongasto_modal_form(obj){ 
        
        
        var obj = new RendicionGasto();    
        ajax.url = html.url.absolute()+'/aplicacion/rendiciongasto/htmf/form.html'; 
        
        ajax.metodo = "GET";  
        modal.ancho = 800;
        obj.venactiva = modal.ventana.mostrar("redgasm");         
        
        return obj;
        

};
        
        
function rendiciongasto_new_acciones(obj){ 
    
    
        var btn_rendiciongasto_guardar = document.getElementById('btn_rendiciongasto_guardar');
        btn_rendiciongasto_guardar.addEventListener('click',
            function(event) {               
                
                
                    if ( obj.form_validar() )
                    {
                        
                        form.name = "form_rendiciongasto";
                        var jdata = form.datos.getjson() ;                        
                    
       
                    
                        ajax.metodo = "post";
                        ajax.url = html.url.absolute()+"/api/"+ obj.recurso;               
                    
                        var data = ajax.private.json( jdata );  
                        
                        
                        switch (ajax.state) {

                          case 200:

                                msg.ok.mostrar("registro agregado");          
                                
                                vista_tabla_rediciones ( ) ;
                                modal.ventana.cerrar(obj.venactiva);
//                                reflex.form_id( obj, reflex.getJSONdataID( obj.campoid ) ) ;                                                                                        

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
                    }

            },
            false
        );        
    
    
                        
    
        var btn_rendiciongasto_cancelar = document.getElementById('btn_rendiciongasto_cancelar');
        btn_rendiciongasto_cancelar.addEventListener('click',
            function(event) {                
                
                modal.ventana.cerrar(obj.venactiva);
                
            },
            false
        );        
        
    
};        
   





  


function rendiciongasto_modal_reg_acciones(obj){ 
    
    
        var btn_rendiciongasto_modificar = document.getElementById('btn_rendiciongasto_modificar');
        btn_rendiciongasto_modificar.addEventListener('click',
            function(event) {               
                
                    form.disabled(true);                
                    reflex.datos.combo(obj);                    
                    form.mostrar_foreign();
                    
                    obj.form_ini();
                    
                    document.getElementById('rendiciongasto_consejo').disabled =  true;
                    document.getElementById('rendiciongasto_consejo_descripcion').disabled =  true;
                    document.getElementById('rendiciongasto_resolucion_numero').disabled =  true;                    
                    
                    rendiciongasto_modal_editar_acciones(obj);
            },
            false
        );        
    
    
    
    
    
    
    
        var btn_rendiciongasto_eliminar = document.getElementById('btn_rendiciongasto_eliminar');
        btn_rendiciongasto_eliminar.addEventListener('click',
            function(event) {                               
                
                //alert("codigo borrar");
                rendiciongasto_modal_borrar(obj) ;
                
                
            },
            false
        );        
    
    
    
    
    
    
    
                        
    
        var btn_rendiciongasto_salir = document.getElementById('btn_rendiciongasto_salir');
        btn_rendiciongasto_salir.addEventListener('click',
            function(event) {      
                modal.ventana.cerrar(obj.venactiva);
            },
            false
        );        
        
    
};        
   


   
   
   
   

function rendiciongasto_modal_editar_acciones(obj){ 
        

        var rendiciongasto_acciones = document.getElementById( "rendiciongasto-acciones" );   
        
        boton.ini(obj);    
        rendiciongasto_acciones.innerHTML = boton.basicform.get_botton_edit();      
        
    
    
    
    
        var btn_rendiciongasto_editar = document.getElementById('btn_rendiciongasto_editar');
        btn_rendiciongasto_editar.addEventListener('click',
            function(event) {         
                                
                
                    if ( obj.form_validar() )
                    {
                        
                        form.name = "form_rendiciongasto";
                        var jdata = form.datos.getjson() ;                        


                        ajax.metodo = "put";
                        ajax.url = html.url.absolute()+"/api/"+ obj.recurso;               

                        var data = ajax.private.json( jdata );                          
                        
                        switch (ajax.state) {

                          case 200:

                                msg.ok.mostrar("registro editado");                                          
                                vista_tabla_rediciones ( ) ;
                                modal.ventana.cerrar(obj.venactiva);
                                
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
                    }                
                
            },
            false
        );        
                

        
        
    
        var btn_rendiciongasto_cancelar = document.getElementById('btn_rendiciongasto_cancelar');
        btn_rendiciongasto_cancelar.addEventListener('click',
            function(event) {                    
                modal.ventana.cerrar(obj.venactiva);                                
            },
            false
        );        
                
        

};   
   
      
   
      
   
   
   
   

function rendiciongasto_modal_borrar(obj) { 

            
            var rendiciongasto_rendicion = document.getElementById( "rendiciongasto_rendicion" );   
    
            ajax.metodo = "DELETE";
            ajax.url = html.url.absolute()+"/api/"+ obj.recurso+"/"+rendiciongasto_rendicion.value;               

            var data = ajax.private.json( );  

            switch (ajax.state) {

              case 200:

                    msg.ok.mostrar("registro eliminado");                                          
                    vista_tabla_rediciones ( ) ;
                    modal.ventana.cerrar(obj.venactiva);

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



   
   
   

 
                
   
   

      
   