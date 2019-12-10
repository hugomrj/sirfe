

function ObjetoGasto(){
    
   this.tipo = "objetogasto";   
   this.recurso = "objetosgastos";   
   this.value = 0;
   this.form_descrip = "rendiciongasto_objeto_descripcion";
   this.json_descrip = "descripcion";
   this.objjson = "objeto";
    
    
    
   this.dom="";   
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Objeto de gasto"
   this.tituloplu = "Objetos de gastos"
       
   
   this.campoid=  'objeto';
   this.tablacampos =   ['objeto', 'descripcion'];      
   this.etiquetas =   ['Objeto', 'Descripcion'];   
   
   this.tbody_id = "objetogasto-tb";   
    
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "objetogasto-acciones";   
   
    //this.modalform_fn = [ this.modal_add, this.modal_cerrar ] ;      
    //this.modalbusqueda_fn = [ this.modal_cerrar  ] ;

   this.parent = null;
   
};




ObjetoGasto.prototype.lista_new = function( obj  ) {    

    reflex.form(obj);
    reflex.acciones.button_add(obj);    
        
    //objetoclase.button_add( obj );                 
    
};




ObjetoGasto.prototype.tabuladores = function( obj ) {  
            
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




ObjetoGasto.prototype.form_validar = function() {    


    return true;

};



