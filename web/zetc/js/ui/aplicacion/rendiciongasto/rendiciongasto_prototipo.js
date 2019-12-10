


function RendicionGasto(){
    
   this.tipo = "rendiciongasto";   
   this.recurso = "rendicionesgastos/session";   
   this.value = 0;
   this.form_descrip = "descripcion";
   this.json_descrip = ['descripcion'];
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Consejo de salud"
   this.tituloplu = "Consejos de salud"   
      
   
   this.campoid=  'rendicion';
   
   
   this.tablacampos =  [ 'tipo_comprobante.descripcion', 'comprobante_numero', 'objeto.objeto',
        'concepto',  'fecha', 'importe', 'verificacion.estado.descripcion'];
   
   
   
   this.etiquetas =  ['Comprobante tipo', 'Comprobante Nro', 'Objeto del Gasto',
       'Concepto', 'Fecha', 'Importe', 'Estado'];
   
   
      this.tablaformat =  ['C', 'R', 'N', 
          'C', 'D', 'N', 'C' ];  
         
   
   this.tbody_id = "rendicion-tb";
      
   this.botones = [ this.new] ;
   this.botones_form = "rendicionesgastos-acciones";   
      
   this.tabs =  [];
   this.parent = null;
   
      
   
   this.combobox = {tipo_comprobante:{
            "value":"tipo_comprobante",
            "inner":"descripcion"}
   };
      
   
}






RendicionGasto.prototype.new = function( obj, ojson  ) {                

        obj = rendiciongasto_modal_form(obj);        
        
        obj.new_form_data(ojson);
        obj.carga_combos( obj );
        obj.form_ini();
        
        boton.ini(obj);
        document.getElementById( obj.tipo +'-acciones' ).innerHTML 
                =  boton.basicform.get_botton_add();   
                    
                    
        rendiciongasto_new_acciones (obj);               
    
        
        modal.ancho = 800;
        obj.foreign = [ new ObjetoGasto()] ;
        reflex.ui.foreign_more(obj);
    
        document.getElementById('article_rendicionverificacion').style.display = "none";
    
    
    //reflex.ui.foreign_decrip(obj);    
    
};








RendicionGasto.prototype.registro_modal = function( obj, json  ) {                

        obj = rendiciongasto_modal_form(obj);        
        
        form.name = "form_" + obj.tipo;
        form.json = json;
        form.disabled(false);
        form.llenar();                
        form.llenar_cbx(obj);             
        form.ocultar_foreign();
            
        
        
        var ojson = JSON.parse(form.json) ;         
        //var rendiciongasto_consejo = document.getElementById("rendiciongasto_consejo");                
        document.getElementById('rendiciongasto_consejo' ).value
                   =  ojson["consejo"]["cod"] ;                 

        document.getElementById('rendiciongasto_consejo_descripcion' ).value
                   =  ojson["consejo"]["descripcion"] ;                   
               
                
        boton.ini(obj);
        document.getElementById( obj.tipo +'-acciones' ).innerHTML 
                =  boton.basicform.get_botton_mrow();   
                    
        rendiciongasto_modal_reg_acciones (obj);               
    
        modal.ancho = 750;
        obj.foreign = [ new ObjetoGasto()] ;
        
        reflex.ui.foreign_more(obj);
    
    
    
            
        // cargar datos de re verificacion       
        document.getElementById('rendicionverificacion_estado' ).value                   
                   =  ojson["verificacion"]["estado"]["descripcion"] ;    
           
        document.getElementById('rendicionverificacion_comentario' ).value                   
                   =  ojson["verificacion"]["comentario"] ;               
    
    
};










RendicionGasto.prototype.new_form_data = function( ojson  ) {                
        
//        try {

        document.getElementById('rendiciongasto_consejo' ).value
                   =  ojson["consejo"]["cod"] ;   

        document.getElementById('rendiciongasto_consejo_descripcion' ).value
                   =  ojson["consejo"]["descripcion"] ;   
    
        document.getElementById('rendiciongasto_resolucion_numero' ).value
                   =  ojson["resolucion_numero"] ;                                

        document.getElementById('rendiciongasto_transferencia' ).value
                   =  ojson["transferencia"] ;   
        
        
//        }         catch (e) {             msg.error.mostrar("Errores de datos");          }                

    
};













RendicionGasto.prototype.carga_combos = function( obj  ) {    
    
    
        var tipo_comprobante = document.getElementById("tipo_comprobante");
        var idedovalue = tipo_comprobante.value;
   
      
        ajax.url = html.url.absolute()+'/api/tiposcomporbantes/all' ;
        ajax.metodo = "GET";   
        var datajson = ajax.private.json();               
    
        var oJson = JSON.parse( datajson ) ;
        
        for( x=0; x < oJson.length; x++ ) {
            
            var jsonvalue = (oJson[x]['tipo_comprobante'] );            
            
            if (idedovalue != jsonvalue )
            {  
                var opt = document.createElement('option');            
                opt.value = jsonvalue;
                opt.innerHTML = oJson[x]['descripcion'];                        
                tipo_comprobante.appendChild(opt);                     
            }
            
        }

}






RendicionGasto.prototype.form_validar = function() {     
    
    
    
    var rendiciongasto_comprobante_numero = document.getElementById('rendiciongasto_comprobante_numero');            
    if (rendiciongasto_comprobante_numero.value.toString().trim().length == 0)
    {
        msg.error.mostrar("Debe ingresar un valor para Comprobante Nro");   
        rendiciongasto_comprobante_numero.focus();
        rendiciongasto_comprobante_numero.select();     
        return false;
    }       
    
        
        
        
    
    var rendiciongasto_objeto_descripcion = document.getElementById('rendiciongasto_objeto_descripcion');       
    if (rendiciongasto_objeto_descripcion.value.toString().trim().length == 0){
        msg.error.mostrar("Serlecciones objeto de gasto");    
        return false;
    }        
        
        
        
        
    
    var rendiciongasto_fecha = document.getElementById('rendiciongasto_fecha');
    if (rendiciongasto_fecha.value == ""){
        msg.error.mostrar("Error en Fecha ");                    
        rendiciongasto_fecha.focus();
        rendiciongasto_fecha.select();                                       
        return false;        
    }
    
    
    
    

    var rendiciongasto_importe = document.getElementById('rendiciongasto_importe');        
    if (parseInt(NumQP(rendiciongasto_importe.value)) <=  0 )         
    {
        msg.error.mostrar("Debe ingresar un valor para Total Depositado");   
        rendiciongasto_importe.focus();
        rendiciongasto_importe.select();     
        return false;
    }  
    
    
    
    /*
    var rendiciongasto_timbrado_venciomiento = document.getElementById('rendiciongasto_timbrado_venciomiento');
    if (rendiciongasto_timbrado_venciomiento.value == ""){
        msg.error.mostrar("Fecha de timbrado no puede ser vacia");                    
        rendiciongasto_timbrado_venciomiento.focus();
        rendiciongasto_timbrado_venciomiento.select();                                       
        return false;        
    }
    else
    {
        var hoy= new Date();
        var fechaFormulario = new Date(rendiciongasto_timbrado_venciomiento.value);
                
        if (hoy >= fechaFormulario) {        
            
            msg.error.mostrar("Fecha de timbrado menor a la fecha actual");                    
            rendiciongasto_timbrado_venciomiento.focus();
            rendiciongasto_timbrado_venciomiento.select();                                       
            return false;        
            
        }    
        
    }
    */
        
    
    
    
    
    return true;
};






RendicionGasto.prototype.form_ini = function() {    
    
        
        
    var rendiciongasto_importe = document.getElementById('rendiciongasto_importe');          
    rendiciongasto_importe.onblur  = function() {                
        rendiciongasto_importe.value  = fmtNum(rendiciongasto_importe.value);
    };     
    rendiciongasto_importe.onblur();          
    
     
    
    /*
    var rendiciongasto_comprobante_numero = document.getElementById('rendiciongasto_comprobante_numero');          
    rendiciongasto_comprobante_numero.onblur  = function() {                
        rendiciongasto_comprobante_numero.value  = fmtNum(rendiciongasto_comprobante_numero.value);      
    };     
    rendiciongasto_comprobante_numero.onblur();         
    */


    
    var rendiciongasto_objeto_objeto = document.getElementById('rendiciongasto_objeto_objeto');          
    rendiciongasto_objeto_objeto.onblur  = function() {             
        
            rendiciongasto_objeto_objeto.value  
                    =  fmtNum(rendiciongasto_objeto_objeto.value) ;
            
            rendiciongasto_objeto_objeto.value  =  NumQP(rendiciongasto_objeto_objeto.value);
                    
                    
            var para = rendiciongasto_objeto_objeto.value;
        
            ajax.url = html.url.absolute()+'/api/objetosgastos/'+para+'' ;
            ajax.metodo = "GET";   
            var_json = ajax.private.json();        
            
            
            try {
                var oJson = JSON.parse( var_json ) ;     
                document.getElementById('rendiciongasto_objeto_descripcion').value =  oJson["descripcion"] ;                   
            } catch(e) {

                document.getElementById('rendiciongasto_objeto_descripcion').value =  "";                   
            }            
            
            
    };     
    //rendiciongasto_objeto_objeto.onblur();          
    
    
    
        
    
};


