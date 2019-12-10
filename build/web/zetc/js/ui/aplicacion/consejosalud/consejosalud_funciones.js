
function consejosalud_cabeceraformulario(){        



        ajax.url = html.url.absolute()+'/api/consejosalud/session/' ;
        
        ajax.metodo = "GET";   
        var servicio_json = ajax.private.json();   


        var globlal_consejo = document.getElementById('globlal_consejo');        
        var globlal_consejo_descripcion = document.getElementById('globlal_consejo_descripcion');        
        
    
        if (ajax.state == 200){
            
            var oJson = JSON.parse( servicio_json ) ;
            
            globlal_consejo.value = oJson['cod'];
            globlal_consejo_descripcion.value = oJson['descripcion'];
                  
        }
        else
        {
            
            globlal_consejo.value = "0";            
            globlal_consejo_descripcion.value = "";
            //facturaventadetalle_porcentaje.value = "";

        }      
      
        
}





