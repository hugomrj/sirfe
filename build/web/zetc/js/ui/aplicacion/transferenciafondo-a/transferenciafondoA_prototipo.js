
function TransferenciaFondoA(){
    
   this.tipo = "transferenciafondo";   
   this.alias = "transferenciafondo-a";   
   
   this.recurso = "transferenciasfondos/admin";   
   this.value = 0;
   this.form_descrip = "descripcion";
   this.json_descrip =[];   
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Transferencias de Fondos de Equidad"
   this.tituloplu = "Transferencias de Fondos de Equidad"         
   
   this.campoid=  'transferencia';
   this.tablacampos =  [ 'consejo.cod', 'consejo.descripcion', 'resolucion_numero',  'saldo_anterior',  'recibo_numero',  
                                'comprobante_numero', 'deposito_fecha',  
                                 'total_depositado' ];
                            
   this.etiquetas =  ['Consejo',  'Descripcion',  'Resolucion Nro',  'Saldo Anterior',   'Recibo Numero',
                            'Comprobante Nro',  'Fecha Deposito',
                             'Total Depositado'];
   
   this.tablaformat =  ['R' , 'C', 'C', 'N', 'N',
                            'N', 'D', 'N' ];   
   
   
   this.tbody_id = "transferenciafondo-tb";
      
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "transferenciafondo-acciones";   
      
   this.tabs =  [];
   this.parent = null;
   
   
   
   this.combobox = {tipo_transferencia:{
            "value":"tipo_transferencia",
            "inner":"descripcion"}
   };
         
   
   
}



TransferenciaFondoA.prototype.lista_new = function( obj  ) {                
    
    
    
    reflex.form_new(obj);
    reflex.acciones.button_add(obj);       
    
        /*
    var fondoequidad_origen_ingreso = document.getElementById('transferenciafondo_origen_ingreso');    
    fondoequidad_origen_ingreso.value = 151;
    fondoequidad_origen_ingreso.disabled = true;
    
    modal.ancho = 750;
    obj.foreign = [ new ConsejoSalud()] ;
    reflex.ui.foreign_more(obj);
        
    
    

    var transferenciafondo_resolucion_numero = document.getElementById('transferenciafondo_resolucion_numero'); 
    transferenciafondo_resolucion_numero.focus();
    transferenciafondo_resolucion_numero.select();   
    */
};









TransferenciaFondoA.prototype.form_validar = function() {    

    var ret = true;    
    
    var transferenciafondo_consejo_descripcion = document.getElementById('transferenciafondo_consejo_descripcion');       
    if (transferenciafondo_consejo_descripcion.value.toString().trim().length == 0){
        msg.error.mostrar("Serlecciones consejo de salud");    
        ret =  false;
    }        
        
    
    if (ret == true){
        var obj = new TransferenciaFondo();    
        ret = obj.form_validar();        
    }

    
    return ret;
            
};







TransferenciaFondoA.prototype.form_ini = function() {        
    
    var obj = new TransferenciaFondo();    
    obj.form_ini_basic();
    

    
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
    
    
    
    var fondoequidad_origen_ingreso = document.getElementById('transferenciafondo_origen_ingreso');    
    fondoequidad_origen_ingreso.value = 151;
    fondoequidad_origen_ingreso.disabled = true;
    
    modal.ancho = 750;
    obj.foreign = [ new ConsejoSalud()] ;
    reflex.ui.foreign_more(obj);
    
        

};









TransferenciaFondoA.prototype.post_form_id = function( obj, id ) { 
    
    
        ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+id;    
        ajax.metodo = "GET";

        form.name = "form_" + obj.tipo;
        form.json = ajax.private.json();   
        

        var ojson = JSON.parse(form.json) ; 
   
        var transferenciafondo_consejo_cod = document.getElementById('transferenciafondo_consejo_cod');     
        transferenciafondo_consejo_cod.value = ojson['consejo']['cod']; 
           
        var transferenciafondo_consejo_descripcion = document.getElementById('transferenciafondo_consejo_descripcion');     
        transferenciafondo_consejo_descripcion.value = ojson['consejo']['descripcion'];            
        
};












TransferenciaFondoA.prototype.preedit = function() {    

    var obj = new TransferenciaFondo();    
    obj.preedit();       
    
    document.getElementById('transferenciafondo_consejo_descripcion').disabled = true;  
    
    
  
};



TransferenciaFondoA.prototype.carga_combos = function( obj  ) {    
    
    
        var tipo_transferencia = document.getElementById("tipo_transferencia");
        var idedovalue = tipo_transferencia.value;
   
      
        ajax.url = html.url.absolute()+'/api/tipostransferencias/all' ;
        ajax.metodo = "GET";   
        var datajson = ajax.private.json();               
    
        var oJson = JSON.parse( datajson ) ;
        
        for( x=0; x < oJson.length; x++ ) {
            
            var jsonvalue = (oJson[x]['tipo_transferencia'] );            
            
            if (idedovalue != jsonvalue )
            {  
                var opt = document.createElement('option');            
                opt.value = jsonvalue;
                opt.innerHTML = oJson[x]['descripcion'];                        
                tipo_transferencia.appendChild(opt);                     
            }
            
        }



}

