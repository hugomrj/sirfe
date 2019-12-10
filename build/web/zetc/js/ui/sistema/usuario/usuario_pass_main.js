

window.onload = function( dom ) {

    
    html.url.control();

    if (ajax.state == 200)
    {
        html.menu.mostrar();    
        
        var obj  = new Usuario();    
        obj.dom = 'arti_form';
                

        ajax.url = html.url.absolute() +'/aplicacion/cambiopass/htmf/form.html';    

        ajax.metodo = "GET";            
        document.getElementById( obj.dom ).innerHTML =  ajax.public.html();

        obj.titulosin = "Cambio de contraseña";
        reflex.getTitulosin(obj);


        boton.objeto = ""+obj.tipo;        
        boton.blabels =  ['Cambiar'];
        
        document.getElementById( obj.tipo +'-acciones' ).innerHTML 
                =  boton.get_botton_base();

        usuario_update_pass ( dom  );

    }
        
};



function usuario_update_pass ( dom  )
{
    
    
            var obj  = new Usuario();    
            
            var btn_usuario_cambiar = document.getElementById('btn_usuario_cambiar');
            btn_usuario_cambiar.addEventListener('click',
                function(event) {       



                        if ( obj.form_validar_pass() ){
                            
                            form.name = "form_usuario";
                            var jdata = form.datos.getjson() ;             
                         
                            
                            ajax.metodo = "put";
                            ajax.url = html.url.absolute()+"/api/"+ obj.recurso+"/cambiopass";       
                            
                            var data = ajax.private.json( jdata );  


                            switch (ajax.state) {
                              case 200:
                                    msg.ok.mostrar("password cambiado");                                                                                  
                                    break; 

                              case 204:
                                    msg.error.mostrar( "No se pudo cambiar el password" );          
                                    break;                                 

                              default: 
                                msg.error.mostrar("error desconocido");           
                                
                            } 
  
  
                            
                      }


                },
                false
            );       
    
    
    
    
    
    
}


         




/*
window.onresize = function() {
    
    var nodeList = document.querySelectorAll('.fondo_oscuro');
    for (var i = 0; i < nodeList.length; ++i) {
        document.getElementById(nodeList[i].id).style.height = document.body.scrollHeight;        
    }

};

*/






