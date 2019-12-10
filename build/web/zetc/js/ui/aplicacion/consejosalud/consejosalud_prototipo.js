

function ConsejoSalud(){
    
   this.tipo = "consejosalud";   
   
   this.objjson = "consejo";   
   
   this.recurso = "consejosalud";   
   this.value = 0;
   this.form_descrip = "descripcion";
   this.json_descrip = ['descripcion'];
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
   
   this.titulosin = "Consejo de salud"
   this.tituloplu = "Consejos de salud"   
      
   
   this.campoid=  'cod';
   this.tablacampos =  ['cod', 'descripcion', 'dpto.descripcion', 'rol.nombre'];
   this.etiquetas =  ['Cod', 'Descripcion', 'Depto', 'Rol'];
   
   this.tbody_id = "consejosalud-tb";
      
   this.botones_lista = [ this.lista_new] ;
   this.botones_form = "consejosalud-acciones";   
      
   this.tabs =  [];
   this.parent = null;
   
   
    this.combobox = {"dpto":{
         "value":"dpto",
         "inner":"descripcion"}};
         
      
   
}



ConsejoSalud.prototype.lista_new = function( obj  ) {                

    reflex.form_new( obj ); 
    reflex.acciones.button_add(obj);        
    
    document.getElementById('consejosalud_cod').disabled =  false;
    
};



ConsejoSalud.prototype.form_ini = function(  ) {   
    
    
        var consejosalud_rol_rol = document.getElementById('consejosalud_rol_rol');
        consejosalud_rol_rol.onblur =function(){
        
                consejosalud_rol_rol.value  = fmtNum(consejosalud_rol_rol.value);               
                
                var rol_descripcion = document.getElementById('rol_descripcion');
                
                var doc = new Rol();           
                var id =  NumQP(fmtNum(consejosalud_rol_rol.value));
                var  json = doc.linea(id);        
                
                if (ajax.state == 200)
                {
                    var oJson = JSON.parse( json ) ;
                    rol_descripcion.innerHTML = oJson['nombre'] ;
                }
                else
                {
                    consejosalud_rol_rol.value = "0";
                    rol_descripcion.innerHTML = ""
                }              
        }
        consejosalud_rol_rol.onblur();                




        var ico_more_rol = document.getElementById('ico-more-rol');  
        ico_more_rol.onclick = function() {

                var rol = new Rol();                
                rol.acctionresul = function(id) {    
                    consejosalud_rol_rol.value = id; 
                    consejosalud_rol_rol.onblur(); 
                };                                                   
                busqueda.modal.objeto( rol );
        }    
        //ico_more_doctor.parentNode.style.display = "flex";   




}




ConsejoSalud.prototype.form_validar = function() {    
    return true;
};





ConsejoSalud.prototype.carga_combos = function( obj  ) {                
    
   
   
        var consejosalud_dpto = document.getElementById("consejosalud_dpto");
        var idedovalue = consejosalud_dpto.value;
   
        ajax.url = html.url.absolute()+'/api/departamentos/all' ;
        ajax.metodo = "GET";   
        var datajson = ajax.private.json();               
        
        var oJson = JSON.parse( datajson ) ;
        
        for( x=0; x < oJson.length; x++ ) {
            
            var jsonvalue = (oJson[x]['dpto'] );            
            
            if (idedovalue != jsonvalue )
            {  
                var opt = document.createElement('option');            
                opt.value = jsonvalue;
                opt.innerHTML = oJson[x]['descripcion'];                        
                consejosalud_dpto.appendChild(opt);                     
            }
            
        }

            

};

