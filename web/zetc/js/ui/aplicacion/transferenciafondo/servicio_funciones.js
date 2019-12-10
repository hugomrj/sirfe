


   
function servicio_id ( id ){     
    
        ajax.metodo = "GET";   
        ajax.url = html.url.absolute()+'/api/servicios/'+id ;    
        ajax.metodo = "GET";   
        var servicio_json = ajax.private.json();   
        
        return servicio_json;        
}
   






function facturaventadetalle_servicio_descripcion(id){        
    
    
        var  servicio_json = servicio_id( id );         
        
        ajax.metodo = "GET";   
        var servicio_json = ajax.private.json();       

        var facturaventadetalle_servicio = document.getElementById('facturaventadetalle_servicio');        
        var facturaventadetalle_porcentaje = document.getElementById('facturaventadetalle_porcentaje');        
        var facturaventadetalle_impuesto = document.getElementById('facturaventadetalle_impuesto');        
        var facturaventadetalle_precio = document.getElementById('facturaventadetalle_precio');      
        
        
        var servicio_descripcion = document.getElementById('servicio_descripcion');      
        var facturaventadetalle_descripcion = document.getElementById('facturaventadetalle_descripcion');   
    
        if (ajax.state == 200){
            
            var oJson = JSON.parse( servicio_json ) ;
            
            facturaventadetalle_servicio.value = oJson['servicio'];
            facturaventadetalle_porcentaje.value = oJson['impuesto']['porcentaje'];
            facturaventadetalle_impuesto.value = oJson['impuesto']['impuesto'];
            facturaventadetalle_precio.value = oJson['precio'];
            
            servicio_descripcion.innerHTML  = oJson['descripcion'];
            facturaventadetalle_descripcion.value = oJson['descripcion'];
                  
        }
        else
        {
            
            facturaventadetalle_servicio.value = "0";            
            facturaventadetalle_porcentaje.value = "";
            facturaventadetalle_impuesto.value = "0";
            facturaventadetalle_precio.value = "0";
            servicio_descripcion.innerHTML  = "";
            facturaventadetalle_descripcion.value = "";

        }      
      
        facturaventadetalle_precio.onblur(); 
        
}

