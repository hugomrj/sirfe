
function Selector(){
    
   this.tipo = "selector";   
   this.recurso = "selectores";   
   this.value = 0;
   this.form_descrip = "selector_descripcion_out";
   this.json_descrip = "descripcion";
   
   this.dom="";
   
   this.titulosin = ""
   this.tituloplu = ""   
   
   this.campoid=  '';
   this.tablacampos =  [];
   this.tbody_id = "";
      
   this.botones_lista = [] ;
   this.botones_form = "";   
      
   this.tabs =  ['roles'];
   this.parent = null;
   
   
   
}


Selector.prototype.form_validar = function() {    
    return true;
};





Selector.prototype.tabuladores = function() {  
           
        var selector = new Selector();
        coleccion.ini(selector);        
        document.getElementById( "selectores-tabuladores" ).innerHTML =  coleccion.gene();    
        
        var rolsel = new RolSelector(); 
        //var sel = new Selector();
        
        selector.value = document.getElementById('selector_selector').value;   

        rolsel.cabecera = selector;       

        //usurol.tablacamposoculto = [0,3, 4];    
        
        coleccion.objetos = [ rolsel ];                        
        coleccion.interaccion();      
    
      
};





