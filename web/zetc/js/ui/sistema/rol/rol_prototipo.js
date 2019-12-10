

function Rol(){
    
   this.tipo = "rol";   
   this.recurso = "roles";   
   this.value = 0;
   this.form_descrip = "rol_descripcion";
   this.json_descrip = "nombre";
   
   this.dom="";
   this.carpeta=  "/sistema";   
      
   
   
   this.titulosin = "Rol"
   this.tituloplu = "Roles"   
      
   //this.tablalinea=  'rol';
   this.campoid=  'rol';
   this.tablacampos =  ['rol', 'nombre'];
   
    this.etiquetas =  ['Rol', 'Descripcion'];                                  
   
   this.tbody_id = "rol-tb";
      
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "rol-acciones";   
      
   //this.tabs =  ['usuario', 'sucursal' ];
   this.tabs =  ['usuario'];
   this.parent = null;
   
}



Rol.prototype.lista_new = function( obj  ) {                

    reflex.form(obj);
    reflex.acciones.button_add(obj);          
    
};



Rol.prototype.form_validar = function() {    
    return true;
};



Rol.prototype.tabuladores = function() {  
           
        var obj = new Rol();
        coleccion.ini(obj);
        
        document.getElementById( "rol-tabuladores" ).innerHTML =  coleccion.gene();    
    
        var usurol = new UsuarioRol(); 
        var rol = new Rol();
        
        rol.value = document.getElementById('rol_rol').value;          
        usurol.cabecera = rol;                
        usurol.tablacamposoculto = [0,3, 4];    
        
        
        var rolsuc = new RolSucursal();         
        rolsuc.cabecera = rol;
        rolsuc.tablacamposoculto = [0,1,2];    
        rolsuc.dom = obj.dom;    
    
            
        
        coleccion.objetos = [ usurol,  rolsuc];                        
        coleccion.interaccion();     
        
};







Rol.prototype.linea = function(id) {    
    
        ajax.metodo = "GET";   
        ajax.url = html.url.absolute()+'/api/'+this.recurso+'/'+id ;    
        ajax.metodo = "GET";   
        var json = ajax.private.json();   
        
        return json;          
    
    
};

