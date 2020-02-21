

function Decreto(){
    
   this.tipo = "decreto";   
   this.recurso = "decretos";   
   this.value = 0;
   
   
   this.dom="";
   this.carpeta=  "/aplicacion";   
        
   
   this.titulosin = "Decreto"
   this.tituloplu = "Decretos"   
      
   this.campoid=  'agno';
   this.tablacampos =  ['agno', 'decreto', 'programa', 'subprograma'];
   this.etiquetas =  ['Año', 'Decreto','Programa', 'Sub programa'];
   
   this.tbody_id = "decreto-tb";
      
   this.botones_lista = [ this.new ] ;
   this.botones_form = "decreto-acciones";   
   
   this.parent = null;
   

}







Decreto.prototype.new = function( obj  ) {                

    reflex.form(obj);
    reflex.acciones.button_add(obj);          
    
        
        document.getElementById("decreto_agno").disabled = false;
    
    
};




Decreto.prototype.form_ini = function(  ) {   
    
}





Decreto.prototype.form_validar = function() {    
    return true;
};

