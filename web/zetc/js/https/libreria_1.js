var page = 1;
var subpage = 0;

function ConstructorXMLHttpRequest()
{

    if(window.XMLHttpRequest)
    {
        return new XMLHttpRequest(); 
    }

    else if(window.ActiveXObject)
    {
        var versionesObj = new Array(
        'Msxml2.XMLHTTP.5.0',
        'Msxml2.XMLHTTP.4.0',
        'Msxml2.XMLHTTP.3.0',
        'Msxml2.XMLHTTP',
        'Microsoft.XMLHTTP');

        for (var i = 0; i < versionesObj.length; i++)
        {
            try
            {
                return new ActiveXObject(versionesObj[i]);
            }
            catch (errorControlado) 
            {
            }
        }
    }

        throw new Error("No se pudo crear el objeto XMLHttpRequest");
};


var objetoAjax = null;
    objetoAjax = new ConstructorXMLHttpRequest();




function AjaxPeticion (url, elementoDOM) {

        if(objetoAjax)
        {            
            objetoAjax.open('GET', url, false);             
            objetoAjax.send(null); 
            document.getElementById(elementoDOM).innerHTML = objetoAjax.responseText;
      
        }
}



/*
function AjaxPeticion(url, elementoDOM) 
{
    
    var ajax = new ConstructorXMLHttpRequest();  
    
    ajax.onreadystatechange=function()    
    {
        if (ajax.readyState==4)
        {
            if (ajax.status==200 || window.location.href.indexOf("http")==-1)
            {  
                document.getElementById(elementoDOM).innerHTML = ajax.responseText;                
            }
        else
        {
            alert("An error has occured making the request")
        }
    }
}

ajax.open("GET", url, true);
ajax.send(null);

}
*/




function AjaxPeticionURL(url, parametros) {
        if(objetoAjax)
        {
            objetoAjax.open('POST', url, false);
            //objetoAjax.open('POST', url, true);
            
            objetoAjax.setRequestHeader('Content-Type','application/x-www-form-urlencoded; charset=UTF-8');
            
            objetoAjax.send(parametros);
            
            var retorno = objetoAjax.responseText;
            if (retorno.trim() !== '') {
                return retorno;
            }
            else {
                return null;
            }
        }
}



function AjaxUrl (url) {

    if(objetoAjax)
    {
        objetoAjax.open('GET', url, false);
        //objetoAjax.open('GET', url, true);
        
        //objetoAjax.send(null);
        objetoAjax.send();
        var retorno = objetoAjax.responseText;

        if (retorno.trim() !== '') {
            return retorno;
        }
        else {
            return null;
        }
    }
}



/*
function Ajax (url, parametros) {
        if(objetoAjax)
        {
            objetoAjax.open('POST', url, false);
            objetoAjax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            objetoAjax.send(parametros);
            
            var retorno = objetoAjax.responseText;
            if (retorno.trim() !== '') {
                return retorno;
            }
            else {
                return null;
            }
        }
}
*/





/*
function ajaxSessionObjeto(url) {

        if(objetoAjax)
        {
            objetoAjax.open('GET', url, false); //Abrimos la url, false=forma síncrona
            objetoAjax.send(null); //No le enviamos datos al servidor.
        }

}
*/


function ocultarVentana(elementoDOM)
{
    var ventana = document.getElementById(elementoDOM);
    ventana.style.display = 'none';
}



function mostrarVentana(elementoDOM)
{
      
    document.getElementById(elementoDOM).style.position = 'absolute' ;
    document.getElementById(elementoDOM).style.display = 'block' ;
    document.getElementById(elementoDOM).style.width = '100%' ;


        var altura = document.body.scrollHeight;
        document.getElementById(elementoDOM).style.height = altura;
    
    
}



function VentanaModal(indice, form, ancho)
{

    var ventanaID = "ventana"+indice;  
    VentanaModalCerrar(ventanaID) ;
    
    
    var body = document.getElementsByTagName("body").item(0);
    var fondo = document.createElement("div");
    fondo.id = ventanaID;
    fondo.className = "fondo_oscuro";
    body.appendChild(fondo);      
    

    var capaOscura = document.getElementById(ventanaID);
    var capaClara = document.createElement("div");
    capaClara.id = "modal"+indice;
    capaClara.className = "fondo_claro";
    capaOscura.appendChild(capaClara);


    AjaxPeticion(form , capaClara.id );      
    
    
    mostrarVentana(capaOscura.id);
    mostrarVentana(capaClara.id);                   
    
    dimensionarVentana(capaClara.id, ancho);       

}






function VentanaModalBusqueda(indice, form, objeto, dom, tabla, ancho)
{
    VentanaModal(indice, form, ancho) ;    
    BusquedaRelacionada(indice, objeto, dom, tabla);
    dimensionarVentana("modal"+indice, ancho);      
}



function ModalBusquedaSimple (indice, form, objeto, dom, tabla, ancho)
{
    VentanaModal(indice, form, ancho) ;    
    BusquedaSimple(indice, objeto, dom, tabla);
    dimensionarVentana("modal"+indice, ancho);      
}





function VentanaModalCerrar(indice)
{
    
        do 
        {
            var ventanaID = "ventana"+indice;
            var capaOscura = document.getElementById(ventanaID);

            if ( !(capaOscura === null)){
                var padre = capaOscura.parentNode;
                padre.removeChild(capaOscura);    
            }

            var capaOscura = document.getElementById(ventanaID);

        }
        while ( !(capaOscura === null));
    
}




function asignarValor(elementoDOM, valor )
{
    document.getElementById(elementoDOM).value = valor  ;
}







function dimensionarVentana(elementoDOM, ancho)
{

    var ventana = document.getElementById(elementoDOM);
    ventana.style.setProperty('height', 'auto');   
    
    
    //var win_ancho = window.innerWidth; // ancho
    //var win_alto = window.innerHeight; // alto


    
    ventana.style.setProperty('position', 'relative');           
    
    ventana.clientHeight;
    ventana.scrollHeight;
    ventana.offsetHeight;
    
   
    ventana.clientWidth;
    ventana.scrollWidth;
    //var  v_offsetWidth =   ventana.offsetWidth;
    var  v_offsetWidth =   document.body.offsetWidth;


//alert(document.body.offsetWidth);


    if ( (ancho + 40) <  document.body.offsetWidth ) 
    {
        ventana.style.setProperty('padding', '1rem');
        ventana.style.setProperty('width', ancho );
        ventana.style.setProperty('left', ((v_offsetWidth - (ancho + 20)) / 2) );        
    }
    else
    {
        


        ventana.style.setProperty('padding', '0rem'); 
        ventana.style.setProperty('padding-bottom', '1rem'); 
        ventana.style.setProperty('width', ventana.clientWidth -2 );               
    }
        
    if ((ventana.clientHeight) < window.innerHeight )
    {  
        var he = (window.innerHeight - (ventana.clientHeight)) / 2;
        ventana.style.setProperty('top', (he) - (he/4) );        
    }
        
        
    var y = document.body.scrollTop;
    ventana.style.setProperty('margin-top', y);


    //if (ventanaAlto ){}


}





function controlSession(){
    
    
}


   


function alerta_error(mensaje){


    // hay que cerrar div en caso que exista
    do 
    {
        var ventanaID = "err1";
        var div_mensaje = document.getElementById(ventanaID);

        if ( !(div_mensaje === null)){
            document.body.removeChild(div_mensaje); 
        }
        var div_mensaje = document.getElementById(ventanaID);

    }
    while ( !(div_mensaje === null));





     if (mensaje != null)
    {
        if (mensaje.toString().trim() == "NoSession")
        {
            window.location = "../";    
        }
        else
        {
            var indice = 1;
            var id = "err";
            id = (id + indice.toString().trim());

            if (document.getElementById(id)){
                return;
            }

            var midiv = document.createElement("div");
            midiv.setAttribute("id", id);

            midiv.setAttribute("class", "notificacion_error");

            midiv.innerHTML = "<div class='contenedorInterior'><div class='left'>"+mensaje+"</div>"
                    +"</div>";

            document.body.appendChild(midiv);

            erasediv = document.getElementById(id);
            // var strCmd = "document.body.removeChild(erasediv);";
            var strCmd = "if (document.getElementById('"+id+"')){ document.body.removeChild(erasediv);}";
            var timeOutPeriod = 4000;


            if ( !(document.getElementById(id) === null)){
                var hideTimer = setTimeout(strCmd, timeOutPeriod);
            }

            indice = Number(indice) + 1;
        }
    }
    mensaje = null;
}


function alerta_info(mensaje){

    if (mensaje != null)
    {
            var indice = 1;
            var id = "info";
            id = (id + indice.toString().trim());


            if (document.getElementById(id)){
                return;
            }

            var midiv = document.createElement("div");
            midiv.setAttribute("id", id);

            midiv.setAttribute("class", "notificacion_info");

            midiv.innerHTML = "<div class='contenedorInterior'><div class='left'>"+mensaje+"</div>"
                    +"</div>";

            document.body.appendChild(midiv);

            erasediv = document.getElementById(id);
           // var strCmd = "document.body.removeChild(erasediv);";
            var strCmd = "if (document.getElementById('"+id+"')){ document.body.removeChild(erasediv);}";
            var timeOutPeriod = 4000;
            var hideTimer = setTimeout(strCmd, timeOutPeriod);

            indice = Number(indice) + 1;

    }

}





var Mensaje = {

    self: this,
    tipo: '',
    contenido: ''

};


function MensajesOnload()
{

    Mensaje.tipo = AjaxUrl('../MensajeTipo');
    Mensaje.contenido = AjaxUrl('../MensajeAction');
    AjaxUrl('../MensajeRemove');
    
    if (Mensaje.tipo != null)
    {
        if (Mensaje.tipo.trim()  === 'error')
        {
            alerta_error(Mensaje.contenido);
        }

        if (Mensaje.tipo.trim()  === 'info')
        {
            alerta_info(Mensaje.contenido);
        }
    }
    
    
}




function MensajeErrorCheck()
{
    Mensaje.tipo = AjaxUrl('../MensajeTipo');
    Mensaje.contenido = AjaxUrl('../MensajeAction');
    
    if (Mensaje.tipo.trim()  === 'error') {
        return false;
    }
    return true;
}





/*
function sub_tabla_tab(tabla , tab, vinculo_coleccion, prefijo,
        cabecera, funcion, largo, ancho ){


    var vinculo_registro = vinculo_coleccion + '/Registro.do?id=';
    var vinculo_listar =  vinculo_coleccion + "/Listar.do";



    var sub_tabla = function(tabla , tab, vinculo_registro, largo, ancho ){

        //tabla tab rol
        var tabla_coleccion = document.getElementById( tabla );
        var rows = tabla_coleccion.getElementsByTagName('tr');

        for (var i=0 ; i < rows.length; i++)
        {
            rows[i].addEventListener ( 'click',
                function() {

                    AjaxUrl('../SetTab?val='+tab);
                    registroid = this.getElementsByTagName('id')[0].innerHTML;

                    mostrarVentana('capa_oscura');
                    mostrarVentana('capa_clara');

                    dimensionarVentana('capa_clara', largo, ancho);
                    AjaxPeticion( vinculo_registro + registroid , 'capa_clara' );

                    setTimeout(funcion, 0);

                },
                false
            );
        }
    }


    var botones_paginacion_tab = function(prefijo, cabecera, vinculo_listar){


        var vinculo = vinculo_listar + "?jsp="+prefijo+cabecera;

        var htmlElemento = "tab_body";

        var paginacion =  document.getElementById( prefijo+'pagination');
        var page = paginacion.dataset.page;

        var lis = paginacion.getElementsByTagName("li");
        for (var i=0; i<lis.length; i++)
        {

            if ( (( lis[i].dataset.orden ) == 'sig')  ||  (( lis[i].dataset.orden ) == 'ant') )
            {

                if ( (( lis[i].dataset.orden ) == 'sig') )
                {
                    if ( document.getElementById( prefijo+'pag_sig') )
                    {
                        document.getElementById( prefijo+'pag_sig').addEventListener('click',
                            function()
                            {
                                
                                AjaxPeticion(vinculo+'&page='+((parseInt(page)+1)), htmlElemento);
                                botones_paginacion_tab(prefijo, cabecera);

                                sub_tabla_tab(tabla , tab, vinculo_coleccion, prefijo,
                                        cabecera, funcion, largo, ancho );

                            },
                            false
                        );
                    }
                }

                if ( (( lis[i].dataset.orden ) == 'ant') )
                {
                    if ( document.getElementById( prefijo+'pag_ant') )
                    {
                        document.getElementById( prefijo+'pag_ant').addEventListener('click',
                            function()
                            {
                                AjaxPeticion(vinculo+'&page='+((parseInt(page)-1)), htmlElemento);
                                botones_paginacion_tab(prefijo, cabecera);

                                sub_tabla_tab(tabla , tab, vinculo_coleccion, prefijo,
                                        cabecera, funcion, largo, ancho );

                            },
                            false
                        );
                    }

                }

            }
            else
            {

                lis[i].addEventListener ( 'click',
                    function() {

                        AjaxPeticion(vinculo+'&page='
                            +(this.getElementsByTagName('a')[0].innerHTML.trim()),
                            htmlElemento);

                        sub_tabla_tab(tabla , tab, vinculo_coleccion, prefijo,
                                cabecera, funcion, largo, ancho );

                    },
                    false
                );
            }
        }
    }

    sub_tabla (tabla , tab, vinculo_registro, largo, ancho);
    botones_paginacion_tab(prefijo, cabecera, vinculo_listar);

}
// fin
*/



function BusquedaRelacionada (  ventana, objeto, dom, tabla )
{
        objeto = objeto.toString().trim();    
        var fxpar = 'BusquedaRelacionada(" '+ventana+' ", " '+objeto+ ' ", "' +dom+ '", "'+ tabla+ '" )';        
            
        var buscar = document.getElementById('buscar');
        buscar.addEventListener('keyup',
            function(event) {
                if(event.keyCode == 13)
                {
                    subpage = 1;
                    sfbusqueda_lista();
                    sfseleccionar_registro();
                }
            },
            false
        );    
    
    
    
        var cerrar_busqueda = document.getElementById('cerrar_busqueda');
        cerrar_busqueda.addEventListener('click',
            function()
            {
                VentanaModalCerrar(ventana);
            },
            false
        );    
    
        
        var sfbusqueda_lista = function( )
        {        
            
            AjaxPeticion(  getRutaAbsoluta()+'/'+objeto+'/Coleccion/Lista?buscar='
                +document.getElementById('buscar').value  
                +"&page="+subpage
                ,'idconsulta');                         
                
            var totalregistros = document.getElementById( tabla ).dataset.totalregistros;  
            
            AjaxPeticion( getRutaAbsoluta()+'/Paginacion?page='+subpage+"&totalregistros="+totalregistros
                +""
                ,'div_paginacion');     
     
            paginacionajax ( fxpar,"subpage");            
            
        }        
        
        
        
    var sfseleccionar_registro = function(  )
    {
        
        var tabla_qry = document.getElementById( tabla );               
        var rows = tabla_qry.getElementsByTagName('tr');

        for (var i=0 ; i < rows.length; i++)
        {
            rows[i].addEventListener ( 'click',
                function() {
                    
                    linea_id = this.dataset.linea_id;    
                    asignarValor(  dom , linea_id );
                
                    document.getElementById( dom ).onblur();  
                    cerrar_busqueda.click();
                 
                },
                false
            );

        }

    };
        
    sfbusqueda_lista();
    sfseleccionar_registro();
        
}




function BusquedaSimple (  ventana, objeto, dom, tabla )
{
        objeto = objeto.toString().trim();    
    
        var cerrar_busqueda = document.getElementById('cerrar_busqueda');
        cerrar_busqueda.addEventListener('click',
            function()
            {
                VentanaModalCerrar(ventana);
            },
            false
        );    
    
        
        var sfbusqueda_lista = function( )
        {                    
            AjaxPeticion(  getRutaAbsoluta()+'/'+objeto+'/Coleccion/Lista','idconsulta');                                     
        }        
        
        
        
    var sfseleccionar_registro = function(  )
    {
        
        var tabla_qry = document.getElementById( tabla );               
        var rows = tabla_qry.getElementsByTagName('tr');

        for (var i=0 ; i < rows.length; i++)
        {
            rows[i].addEventListener ( 'click',
                function() {
                    
                    linea_id = this.dataset.linea_id;    
                    asignarValor(  dom , linea_id );
                
                    document.getElementById( dom ).onblur();  
                    cerrar_busqueda.click();
                 
                },
                false
            );

        }

    };
        
    sfbusqueda_lista();
    sfseleccionar_registro();
        
}






/*

    function paginacionajax ( fn )
    {
      
        var listaUL = document.getElementById( "pagination" );
        var uelLI = listaUL.getElementsByTagName('li');
        
        var paginaActual = listaUL.dataset.paginaactual;
                
        for (var i=0 ; i < uelLI.length; i++)
        {
            datapag = uelLI[i].dataset.pagina;     

            if (!(datapag == "act"  || datapag == "det"  ))
            {

                uelLI[i].addEventListener ( 'click',
                    function() {                                      
                        
                            switch (this.dataset.pagina)
                            {
                               case "sig": 
                                   page = parseInt(paginaActual) +1;
                                   break;

                               case "ant": 
                                   page = parseInt(paginaActual) - 1;
                                    break;

                               default:  
                                    page = this.childNodes[0].innerHTML.toString().trim();
                            }                            
                            
                            eval( fn );
                            
                        },
                        false
                    );                
                
            }            
        }           
            
    }
    
    */




    
    function paginacionajax ( fn, relpage )
    {
      
        if (relpage === undefined || relpage === null) {
             relpage = "page";
        }
            
        var listaUL = document.getElementById( "pagination" );
        var uelLI = listaUL.getElementsByTagName('li');
        
        var paginaActual = listaUL.dataset.paginaactual;
                             
                
        for (var i=0 ; i < uelLI.length; i++)
        {
            datapag = uelLI[i].dataset.pagina;     

            if (!(datapag == "act"  || datapag == "det"  ))
            {

                uelLI[i].addEventListener ( 'click',
                    function() {                                      
                        
                            switch (this.dataset.pagina)
                            {
                               case "sig": 
                                   
                                   if (relpage == "subpage"){
                                        subpage = parseInt(paginaActual) +1;
                                        break;
                                   }
                                   else{
                                       page = parseInt(paginaActual) +1;
                                       break;
                                   }
                                       

                               case "ant":                                     
                                   if (relpage == "subpage"){
                                        subpage = parseInt(paginaActual) -1;
                                        break;
                                   }
                                   else{
                                       page = parseInt(paginaActual) -1;
                                       break;
                                   }
                                     
                               default:  
                                   if (relpage == "subpage"){
                                        subpage = this.childNodes[0].innerHTML.toString().trim();
                                        break;
                                   }
                                   else{
                                       page = this.childNodes[0].innerHTML.toString().trim();
                                       break;
                                   }                                    
                                    
                            }                            
                            
                            eval( fn );
                            
                        },
                        false
                    );                
                
            }            
        }           
            
    }
    
    
    
    
    

    
    

