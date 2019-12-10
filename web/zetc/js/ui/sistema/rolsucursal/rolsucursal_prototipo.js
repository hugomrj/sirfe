

function RolSucursal(){
        
    this.tipo = "rolsucursal";   
    this.recurso = "rolsucursal";   
    this.value = 0;

   this.carpeta=  "/sistema";   


    this.campoid=  'id';
    this.tablacampos = ['id', 'rol.rol', 'rol.nombre',  'sucursal.sucursal', 'sucursal.nombre' ];    
    this.tablacamposoculto = [];    
    
    this.formcampooculto = [];     
        
    this.titulosin = ""
    this.tituloplu = ""  
    
    this.botones_lista = "acciones-lista";
    this.botones_form = "rolsucursal-acciones";    
    
        
    this.funciones = null;  
    this.venactiva = null;

    this.cabecera= "";    
    this.foreign = [ new Rol(), new Sucursal() ] ;
    
    this.break = false;    
};




RolSucursal.prototype.sublista = function( obj ) {                

   if ( obj.break  == false )
   {
        tabla.setObjeto(obj);
        coleccion.sublista( obj );     
   }

    
};





RolSucursal.prototype.modal_new = function(  obj  ) {    


    modal.form.new( obj );  

    obj.foreign = [ new Rol(), new Sucursal() ] ;
    reflex.ui.foreign_more(obj);
    reflex.ui.foreign_plus(obj);
    reflex.ui.foreign_decrip(obj);

    modal.form.pasarcab(obj);
    modal.form.ocultarcab(obj);
    
    var caid = document.getElementById( "rolsucursal_id");
    caid.value = 0;    
    caid.disabled=true;
    
};






RolSucursal.prototype.form_validar = function( objeto ) {    

    var rolsucursal_sucursal_sucursal = document.getElementById('rolsucursal_sucursal_sucursal');    
    if (rolsucursal_sucursal_sucursal.value == "")         
    {
        msg.error.mostrar("error campo vacio");                   
        return false;
    }        
    
    /*
    var usuariosucursal_usuusu = document.getElementById('usuariosucursal_usuario_usuario');    
    if (usuariosucursal_usuusu.value == "")         
    {
        msg.error.mostrar("error campo usuario");                   
        return false;
    }  
    */
    return true;
}











