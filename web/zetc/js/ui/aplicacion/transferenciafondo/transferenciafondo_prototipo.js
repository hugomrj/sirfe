
function TransferenciaFondo(){
    
   this.tipo = "transferenciafondo";   
   this.recurso = "transferenciasfondos/session";   
   this.value = 0;
   this.form_descrip = "descripcion";
   this.json_descrip =[];   
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Transferencias de Fondos de Equidad"
   this.tituloplu = "Transferencias de Fondos de Equidad"         
   
   this.campoid=  'transferencia';
   this.tablacampos =  [ 'resolucion_numero', 'saldo_anterior',  'recibo_numero',  
                                'comprobante_numero', 'deposito_fecha',  
                                 'total_depositado' ];
                            
   this.etiquetas =  [ 'Resolucion Nro', 'Salto Anterior',   'Recibo Numero',
                            'Comprobante Nro',  'Fecha Deposito',
                             'Total Depositado'];
   
   this.tablaformat =  ['C', 'N', 'N',
                            'N', 'D', 'N' ];   
   
   
   this.tbody_id = "transferenciafondo-tb";
      
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "transferenciafondo-acciones";   
      
   this.tabs =  [];
   this.parent = null;
   
   
}



TransferenciaFondo.prototype.lista_new = function( obj  ) {                
    
    reflex.form_new(obj);
    reflex.acciones.button_add(obj);       
    
    
    var fondoequidad_origen_ingreso = document.getElementById('transferenciafondo_origen_ingreso');    
    fondoequidad_origen_ingreso.value = 151;
    fondoequidad_origen_ingreso.disabled = true;
    
    
    var transferenciafondo_resolucion_numero = document.getElementById('transferenciafondo_resolucion_numero'); 
    transferenciafondo_resolucion_numero.focus();
    transferenciafondo_resolucion_numero.select();   
    
};







TransferenciaFondo.prototype.form_validar = function() {    

    
    var transferenciafondo_resolucion_numero = document.getElementById('transferenciafondo_resolucion_numero');            
    var regex = /^[\d]+[/][\d]{2}$/;       
    if ( !(regex.test( transferenciafondo_resolucion_numero.value.toString().trim())))   
    {
        msg.error.mostrar("Error para Resolucion Nro, formato valido 000000/00");   
        transferenciafondo_resolucion_numero.focus();
        transferenciafondo_resolucion_numero.select();     
        return false;
    }       
  
    

    var transferenciafondo_recibo_numero = document.getElementById('transferenciafondo_recibo_numero');        
    if (parseInt(NumQP(transferenciafondo_recibo_numero.value)) <=  0 )         
    {
        msg.error.mostrar("Debe ingresar un valor para Recibo Nro");   
        transferenciafondo_recibo_numero.focus();
        transferenciafondo_recibo_numero.select();     
        return false;
    }       
        
    var transferenciafondo_comprobante_numero = document.getElementById('transferenciafondo_comprobante_numero');        
    if (parseInt(NumQP(transferenciafondo_comprobante_numero.value)) <=  0 )         
    {
        msg.error.mostrar("Debe ingresar un valor para Comprobante Nro");   
        transferenciafondo_comprobante_numero.focus();
        transferenciafondo_comprobante_numero.select();     
        return false;
    }       
        
        
    
    var transferenciafondo_deposito_fecha = document.getElementById('transferenciafondo_deposito_fecha');
    if (transferenciafondo_deposito_fecha.value == ""){
        msg.error.mostrar("Error en Fecha ");                    
        transferenciafondo_deposito_fecha.focus();
        transferenciafondo_deposito_fecha.select();                                       
        return false;        
    }
    

    var transferenciafondo_total_depositado = document.getElementById('transferenciafondo_total_depositado');        
    if (parseInt(NumQP(transferenciafondo_total_depositado.value)) <=  0 )         
    {
        msg.error.mostrar("Debe ingresar un valor para Total Depositado");   
        transferenciafondo_total_depositado.focus();
        transferenciafondo_total_depositado.select();     
        return false;
    }       
        


    return true;
    
    
    
};






TransferenciaFondo.prototype.form_ini = function() {        
    
    
    consejosalud_cabeceraformulario();
    
    var obj = new TransferenciaFondo();    
    obj.form_ini_basic();
    
    
};





TransferenciaFondo.prototype.form_ini_basic = function() {        
    
    
    
    var origen_ingreso_descripcion = document.getElementById('origen_ingreso_descripcion');    
    origen_ingreso_descripcion.value = "Transferencias a la Tesoreria Central";
    origen_ingreso_descripcion.disabled = true;        
        

    
    var transferenciafondo_saldo_anterior = document.getElementById('transferenciafondo_saldo_anterior');          
    transferenciafondo_saldo_anterior.onblur  = function() {                
        transferenciafondo_saldo_anterior.value  = fmtNum(transferenciafondo_saldo_anterior.value);      
    };     
    transferenciafondo_saldo_anterior.onblur();     
    
    
    
    
    var transferenciafondo_recibo_numero = document.getElementById('transferenciafondo_recibo_numero');          
    transferenciafondo_recibo_numero.onblur  = function() {                
        transferenciafondo_recibo_numero.value  = fmtNum(transferenciafondo_recibo_numero.value);      
    };     
    transferenciafondo_recibo_numero.onblur();     
    
    
    
    var transferenciafondo_comprobante_numero = document.getElementById('transferenciafondo_comprobante_numero');          
    transferenciafondo_comprobante_numero.onblur  = function() {                
        transferenciafondo_comprobante_numero.value  = fmtNum(transferenciafondo_comprobante_numero.value);      
    };     
    transferenciafondo_comprobante_numero.onblur();     
    
    
        
    
    var transferenciafondo_total_depositado = document.getElementById('transferenciafondo_total_depositado');          
    transferenciafondo_total_depositado.onblur  = function() {                
        transferenciafondo_total_depositado.value  = fmtNum(transferenciafondo_total_depositado.value);      
    };     
    transferenciafondo_total_depositado.onblur();     
    
    
    
    
};









TransferenciaFondo.prototype.preedit = function() {    
        
        
    var fondoequidad_origen_ingreso = document.getElementById('transferenciafondo_origen_ingreso');    
    fondoequidad_origen_ingreso.value = 151;
    fondoequidad_origen_ingreso.disabled = true;        

        
    
    var origen_ingreso_descripcion = document.getElementById('origen_ingreso_descripcion');    
    origen_ingreso_descripcion.disabled = true;      
    
        
        
    var transferenciafondo_total_depositado = document.getElementById('transferenciafondo_total_depositado');            
    transferenciafondo_total_depositado.disabled = true;                

    
    
    if ( document.getElementById( "globlal_consejo" )) {
      document.getElementById('globlal_consejo').disabled = true;        
    }
    
    
    
    if ( document.getElementById( "globlal_consejo_descripcion" )) {
      document.getElementById('globlal_consejo_descripcion').disabled = true;        
    }
    
        

};






TransferenciaFondo.prototype.carga_combos = function( obj  ) {       
    
    
    // no carga combo solo bloquea input
    
    var fondoequidad_origen_ingreso = document.getElementById('transferenciafondo_origen_ingreso');    
    fondoequidad_origen_ingreso.value = 151;
    fondoequidad_origen_ingreso.disabled = true;      
            

};

