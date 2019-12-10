

function UsuarioRol(){
        
    this.tipo = "usuariorol";   
    this.recurso = "usuariorol";   
    this.value = 0;

   this.carpeta=  "/sistema";   


    this.campoid=  'id';
    this.tablacampos = ['id', 'usuario.usuario', 'usuario.cuenta',  'rol.rol', 'rol.nombre' ];    
    this.tablacamposoculto = [];    
    
    this.formcampooculto = [];     
        
    this.titulosin = ""
    this.tituloplu = ""  
    
    this.botones_lista = "acciones-lista";
    this.botones_form = "usuariorol-acciones";    
    
        
    this.funciones = null;  
    this.venactiva = null;

    this.cabecera= "";    
    this.foreign = [ new Usuario(), new Rol() ] ;
    
    this.break = false;    
};




UsuarioRol.prototype.sublista = function( obj ) {                
  
   if ( obj.break  == false )
   {
        tabla.setObjeto(obj);
        coleccion.sublista( obj );     
   }

    
};





UsuarioRol.prototype.modal_new = function(  obj  ) {    


    modal.form.new( obj );  
    

    obj.foreign = [ new Usuario(), new Rol() ] ;
    reflex.ui.foreign_more(obj);
    reflex.ui.foreign_plus(obj);
    reflex.ui.foreign_decrip(obj);

    modal.form.pasarcab(obj);
    modal.form.ocultarcab(obj);
    
    var caid = document.getElementById( "usuariorol_id");
    caid.value = 0;    
    caid.disabled=true;
    
    
    
};






UsuarioRol.prototype.form_validar = function( objeto ) {    

    var usuariorol_id = document.getElementById('usuariorol_id');    
    if (usuariorol_id.value == "")         
    {
        msg.error.mostrar("error campo vacio");                   
        return false;
    }        
    
    var usuariorol_usuusu = document.getElementById('usuariorol_usuario_usuario');    
    if (usuariorol_usuusu.value == "")         
    {
        msg.error.mostrar("error campo usuario");                   
        return false;
    }  
    
    return true;
}











