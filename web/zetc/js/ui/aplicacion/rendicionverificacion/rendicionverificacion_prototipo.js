
function RendicionVerificacion(){
   
    
   this.tipo = "rendicionverificacion";   
   
   
   this.recurso = "verificaciones";   
   this.value = 0;
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Verificacion de Rendicion"
   this.tituloplu = "Verificacion de Rendicion"   
      
   
   this.campoid=  'verificacion';
   
   
   this.tablacampos =  [ 'rendicion.tipo_comprobante.descripcion', 'rendicion.comprobante_numero', 
       'rendicion.objeto.objeto',  'rendicion.concepto',  
       'rendicion.fecha', 'rendicion.importe', 'rendicion.observacion', 'rendicion.ruc_factura',
       'comentario', 'estado.descripcion'
   ];
   
   
   
   this.etiquetas =  ['Comprobante tipo', 'Comprobante Nro', 'Objeto del Gasto',
       'Concepto', 'Fecha', 'Importe', 'Observacion', 'Estado'];
   
   
      this.tablaformat =  ['C', 'R', 'N', 
          'C', 'D', 'N', 'C', 'C' ];  
         
   
   this.tbody_id = "rendicionverificacion-tb";
      
   this.botones = [ this.new] ;
   this.botones_form = "rendicionverificacion-acciones";   
      
   this.tabs =  [];
   this.parent = null;
   
      
   
   this.combobox = {tipo_comprobante:{
            "value":"tipo_comprobante",
            "inner":"descripcion"}
   };
      
   
}






RendicionVerificacion.prototype.new = function( obj, ojson  ) {                

        obj = rendiciongasto_modal_form(obj);        
        
        obj.new_form_data(ojson);
        obj.carga_combos( obj );
        obj.form_ini();
        
        boton.ini(obj);
        document.getElementById( obj.tipo +'-acciones' ).innerHTML 
                =  boton.basicform.get_botton_add();   
                    
        rendiciongasto_new_acciones (obj);               
    
        
        modal.ancho = 750;
        obj.foreign = [ new ObjetoGasto()] ;
        reflex.ui.foreign_more(obj);
    
    
    
    //reflex.ui.foreign_decrip(obj);    
    
};








RendicionVerificacion.prototype.registro_modal = function( obj, json  ) {                

        obj = rendiciongasto_modal_form(obj);        
        
        form.name = "form_" + obj.tipo;
        
        form.json = json;
        
        form.disabled(false);
        form.llenar();                
        form.llenar_cbx(obj);             
        form.ocultar_foreign();
            
        obj.form_ini();
        
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
    
        modal.ancho = 800;
        obj.foreign = [ new ObjetoGasto()] ;
        
        reflex.ui.foreign_more(obj);
        
        
        
        
    
    
};










RendicionVerificacion.prototype.new_form_data = function( ojson  ) {                
        
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






RendicionVerificacion.prototype.form_validar = function() {     
    
    
    /*
    var rendiciongasto_comprobante_numero = document.getElementById('rendiciongasto_comprobante_numero');        
    if (parseInt(NumQP(rendiciongasto_comprobante_numero.value)) <=  0 )         
    {
        msg.error.mostrar("Debe ingresar un valor para Comprobante Nro");   
        rendiciongasto_comprobante_numero.focus();
        rendiciongasto_comprobante_numero.select();     
        return false;
    }       
   */     
        
        
        
    
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
    
    return true;
};






RendicionVerificacion.prototype.form_ini = function() {        
        
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
                console.log(e); 
                document.getElementById('rendiciongasto_objeto_descripcion').value =  "";                   
            }            
            
            
    };     
    //rendiciongasto_objeto_objeto.onblur();          
    
    
    
        
    
};


