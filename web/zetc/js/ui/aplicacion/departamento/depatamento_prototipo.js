

function Departamento(){
    
   this.tipo = "departamento";   
   this.recurso = "departamentos";   
   this.value = 0;
   this.form_descrip = "descripcion";
   this.json_descrip = "descripcion";
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
        
   
   this.titulosin = "Departamento"
   this.tituloplu = "Departamentos"   
      
   this.campoid=  'dpto';
   this.tablacampos =  ['dpto', 'descripcion', 'verificador.cuenta'];
   this.etiquetas =  ['Departamento', 'Descripcion', 'Verificador'];
   
   this.tbody_id = "departamento-tb";
      
   this.botones_lista = [ this.new ] ;
   this.botones_form = "departamento-acciones";   
   
   this.parent = null;
   

}







Departamento.prototype.new = function( obj  ) {                

    reflex.form(obj);
    reflex.acciones.button_add(obj);          
    
    
    var departamento_dpto = document.getElementById('departamento_dpto');
    departamento_dpto.disabled=false;
    
    
};




Departamento.prototype.form_ini = function(  ) {   
    
    
        var departamento_usuario_usuario = document.getElementById('departamento_usuario_usuario');
        departamento_usuario_usuario.onblur =function(){
        
                
                departamento_usuario_usuario.value  = fmtNum(departamento_usuario_usuario.value);               
                
                var usuario_descripcion = document.getElementById('usuario_descripcion');
                
                var usu = new Usuario(); 
                usu.objjson = "usuario";
                
                var id =  NumQP(fmtNum(departamento_usuario_usuario.value));
                

            ajax.metodo = "GET";   
            ajax.url = html.url.absolute()+'/api/'+usu.recurso+'/'+id ;    
            ajax.metodo = "GET";   
            var json = ajax.private.json();   

              
                
                
                if (ajax.state == 200)
                {
                    var oJson = JSON.parse( json ) ;
                    usuario_descripcion.innerHTML = oJson['cuenta'] ;
                }
                else
                {
                    departamento_usuario_usuario.value = "0";
                    usuario_descripcion.innerHTML = ""
                }                             
           
        }
        departamento_usuario_usuario.onblur();                




        var ico_more_usuario = document.getElementById('ico-more-usuario');  
        ico_more_usuario.onclick = function() {

                var veri = new Usuario();                
                veri.acctionresul = function(id) {    
                    departamento_usuario_usuario.value = id; 
                    departamento_usuario_usuario.onblur(); 
                };                                                   
                busqueda.modal.objeto( veri );
        }    
        //ico_more_doctor.parentNode.style.display = "flex";   


}







Departamento.prototype.form_validar = function() {    
    return true;
};








Departamento.prototype.post_form_id = function( obj, id ) { 
    
    
    
    
        ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+id;    
        ajax.metodo = "GET";

        form.name = "form_" + obj.tipo;
        form.json = ajax.private.json();   
        
        var ojson = JSON.parse(form.json) ; 



try {
  
        var departamento_usuario_usuario = document.getElementById('departamento_usuario_usuario');     
        departamento_usuario_usuario.value = ojson['verificador']['usuario']; 

        var usuario_descripcion = document.getElementById('usuario_descripcion');     
        usuario_descripcion.value = ojson['verificador']['cuenta'];   
  
}
catch(error) {
  //console.error(error);
}



    
    

    
};




