

/*

function usuario_lista ( dom  )
{
         
        paginacion.pagina = 1;    
        //usuario_lista_pagina( dom, paginacion.pagina );       
    
        tabla.linea = "usuario";
        tabla.campos = ['usuario', 'cuenta'];        
        tabla.tbody_id = "usuarios-tb";
        
        
        objetoclase.nombre = "usuario";
        objetoclase.recurso = "usuarios";
        objetoclase.lista(dom, paginacion.pagina);


        var btn_nuevo_usuario = document.getElementById('btn_nuevo_usuario');
        btn_nuevo_usuario.addEventListener('click',
            function(event) {      

                usuario_form( dom );
                button_add( dom );

            },
            false
        ); 

    //supratag.tablapaginacion("usuario", paginacion.pagina);

}

*/


/*
function usuario_lista_pagina( dom, page )
{
    
    
    ajax.url = pathAbs()+'/sistema/usuario/htmf/lista.html';            
    ajax.metodo = "GET";    
    document.getElementById(  dom ).innerHTML =  ajax.public.html();      
    
    
    ajax.url = pathAbs()+'/api/usuarios?page=' + page;    
    ajax.metodo = "GET";
        
        
        
    
    tabla.json = ajax.private.json();    
    tabla.linea = "usuario";
    tabla.campos = ['usuario', 'cuenta'];        
    tabla.tbody_id = "usuarios-tb";
    tabla.gene();
   
   
    document.getElementById( 'div_paginacion' ).innerHTML = paginacion.gene();        
    paginacion.autofn( usuario_lista_pagina, dom );
    usuario_lista_registro('usuarios-tabla', dom);    
        
}
*/




/*
function usuario_lista_registro( tablaid, dom )
{

    var table = document.getElementById( tablaid ).getElementsByTagName('tbody')[0];
    var rows = table.getElementsByTagName('tr');

    for (var i=0 ; i < rows.length; i++)
    {
        rows[i].onclick = function()
        {
                var linea_id = this.dataset.linea_id;                                   
                usuario_form_id(dom, linea_id);                
        };       
    }


};
*/
