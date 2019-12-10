

function Usuario(){
    
   this.tipo = "usuario";   
   this.recurso = "usuarios";   
   this.value = 0;
   this.form_descrip = "usuario_descripcion";
   this.json_descrip = "cuenta";
    
   this.dom="";   
   this.carpeta=  "/sistema";   
   
   this.titulosin = "Usuario"
   this.tituloplu = "Usuarios"
       
   //this.tablalinea=  'usuario';
   this.campoid=  'usuario';
   this.tablacampos =   ['usuario', 'cuenta'];      
   this.etiquetas =   ['Usuario', 'Cuenta'];   
   
   this.tbody_id = "usuario-tb";   
    
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "usuario-acciones";   
   
    //this.modalform_fn = [ this.modal_add, this.modal_cerrar ] ;      
    //this.modalbusqueda_fn = [ this.modal_cerrar  ] ;
      
   this.tabs =  ['rol'];
   this.parent = null;
   
};




Usuario.prototype.lista_new = function( obj  ) {    

    reflex.form(obj);
    reflex.acciones.button_add(obj);    
    
    
    //objetoclase.button_add( obj );         
        
    
};




Usuario.prototype.tabuladores = function( obj ) {  
            
            /*
        coleccion.dato = this.tabs;    
        coleccion.id = "tabid";
        */
      
       
        //var obj = new Usuario();
        coleccion.ini(obj);        
        
        document.getElementById( "usuario-tabuladores" ).innerHTML =  coleccion.gene();    
                                
        var usurol = new UsuarioRol();         
        var usu = new Usuario();
        usu.dom = obj.dom;
        
        usu.value = document.getElementById('usuario_usuario').value;          
        usurol.cabecera = usu;
        usurol.tablacamposoculto = [0,1,2];    
        usurol.dom = obj.dom;
   
    
    
    /*
        var ususuc = new UsuarioSucursal();         
        var suc = new Sucursal();
        suc.dom = obj.dom;    
    
        suc.value = document.getElementById('usuario_usuario').value;          
        ususuc.cabecera = usu;
        ususuc.tablacamposoculto = [0,1,2];    
        ususuc.dom = obj.dom;    
    */
    
    
        coleccion.objetos = [ usurol, null ];                        
        coleccion.interaccion();        
};
    
    
    
    
    
/*
Usuario.prototype.modal_add = function( objeto ) {            
    modal.form.add(objeto);
};


Usuario.prototype.modal_cerrar = function( objeto ) {        
    modal.ventana.cerrar(objeto.venactiva);        
};
*/




Usuario.prototype.form_validar = function() {    

    var usuario_cuenta = document.getElementById('usuario_cuenta');    
    if (usuario_cuenta.value == "")         
    {
        msg.error.mostrar("error campo vacio");           
        usuario_cuenta.focus();
        usuario_cuenta.select();        
        return false;
    }    
    
    var usuario_clave = document.getElementById('usuario_clave');    
    if (usuario_clave .value == "")         
    {
        msg.error.mostrar("error campo vacio");           
        usuario_clave.focus();
        usuario_clave.select();        
        return false;
    }    
    return true;

};




Usuario.prototype.form_validar_pass = function() {    

    var usuario_new = document.getElementById('usuario_new');    
    var usuario_confi = document.getElementById('usuario_confi');    

    if (usuario_new.value.toString().trim().length == 0){    
        msg.error.mostrar("La nueva contraseña no puede estar vacia");                   
        return false;        
    }
   
   
        
    if (usuario_new.value.toString().trim() != usuario_confi.value.toString().trim() ){        
        msg.error.mostrar("Las contraseñas no son iguales");                   
        return false;        
    }
    
    
    return true;

};





Usuario.prototype.sublista = function( obj ) {                
  
        tabla.setObjeto(obj);
        coleccion.sublista( obj );     
  
    
    // aca posiblemente se ponga el reg
};


