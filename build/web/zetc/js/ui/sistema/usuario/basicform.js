

function usuario_form ( dom  )
{
    
    ajax.url = html.url.absolute()+'/sistema/usuario/htmf/form.html';       
    ajax.metodo = "GET";
    document.getElementById(  dom ).innerHTML =  ajax.public.html();    


    var usuario_usuario = document.getElementById('usuario_usuario');
    usuario_usuario.value = '0';
    usuario_usuario.disabled = true;

}





function usuario_form_validar( ){
    
    
    var usuario_cuenta = document.getElementById('usuario_cuenta');    
    if (usuario_cuenta .value == "")         
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

}





/*
function usuario_form_id( dom, id ){
    
        usuario_form( dom );

        ajax.url = pathAbs()+'/api/usuarios/'+id;    
        ajax.metodo = "GET";


        form.name = "form_usuario";
        form.json = ajax.private.json();   
        
        form.disabled(false);
        form.llenar();                

        button_reg( dom );    
    
}
*/





