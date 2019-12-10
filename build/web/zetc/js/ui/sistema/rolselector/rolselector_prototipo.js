

function RolSelector(){
        
    this.tipo = "rolselector";   
    this.recurso = "rolselector";   
    this.value = 0;
    
   this.carpeta=  "/sistema";       
    
    this.campoid=  'id';
    this.tablacampos = ['id', 'rol.rol', 'rol.nombre', 'selector.selector', 'selector.descripcion', 'selector.ord' ];        
    this.tablacamposoculto = [];    
    
    this.formcampooculto = [];     
        
    this.titulosin = "";
    this.tituloplu = "" ; 
    
    this.botones_lista = "acciones-lista";
    this.botones_form = "rolselector-acciones";    
        
    this.funciones = null;  
    this.venactiva = null;

    this.cabecera= "";    
    this.foreign = [ new Rol(), new Selector() ] ;

    this.break = false;
};




RolSelector.prototype.sublista = function( obj ) {      
 
   if ( obj.break  == false )
   {
        tabla.setObjeto(obj);
        coleccion.sublista( obj );     
   }
   
};





RolSelector.prototype.modal_new = function(  obj  ) {    


    modal.form.new( obj );  

    obj.foreign = [ new Selector() ] ;
    reflex.ui.foreign_plus(obj);
    
    obj.foreign = [ new Rol(), new Selector() ] ;
    reflex.ui.foreign_more(obj);
    reflex.ui.foreign_decrip(obj);


    modal.form.pasarcab(obj);
    modal.form.ocultarcab(obj);
    
    var caid = document.getElementById( "rolselector_id");
    caid.value = 0;    
    caid.disabled=true;
  
 
 
};







RolSelector.prototype.form_validar = function( obj ) {    
    
    return true;
}











