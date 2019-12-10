

function Sucursal(){
    
   this.tipo = "sucursal";   
   this.recurso = "sucursales";   
   this.value = 0;
   this.form_descrip = "sucursal_descripcion";
   this.json_descrip = "nombre";
   
   
   this.dom="";
   this.carpeta=  "/sistema";   
      
   
   
   this.titulosin = "Sucursal"
   this.tituloplu = "Sucursales"   
      
   this.campoid=  'sucursal';
   this.tablacampos =  ['sucursal', 'nombre'];
   this.etiquetas =  ['Sucursal', 'Descripcion'];                                  
   
   
   this.tbody_id = "sucursal-tb";
      
   this.botones_lista = [ this.new ] ;
   this.botones_form = "sucursal-acciones";   
   
    this.tabs =  ['roles' ];
   this.parent = null;
   
}





Sucursal.prototype.new = function( obj  ) {                

    reflex.form(obj);
    reflex.acciones.button_add(obj);          
    
    var sucursal_sucursal = document.getElementById( "sucursal_sucursal" );
    sucursal_sucursal.disabled = false; 
    sucursal_sucursal.focus();
    sucursal_sucursal.select(); 
    
};






Sucursal.prototype.form_validar = function() {    
    return true;
};





Sucursal.prototype.tabuladores = function() {  
           
        var obj = new Sucursal();
        coleccion.ini(obj);
        
        document.getElementById( "sucursal-tabuladores" ).innerHTML =  coleccion.gene();    
    
        //var rol = new Rol();        
        obj.value = document.getElementById('sucursal_sucursal').value;          

        
        var rolsuc = new RolSucursal();         
        rolsuc.cabecera = obj;
        rolsuc.tablacamposoculto = [0,3,4];    
        rolsuc.dom = obj.dom;    
                
        coleccion.objetos = [ rolsuc];                        
        coleccion.interaccion();     
        
};



