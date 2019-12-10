
window.onload = function( dom ) {
    
    
    html.url.control();

    if (ajax.state == 200)
    {
        html.menu.mostrar();    
        
        var dom = 'arti_form';
        selector_lista_all ( dom );
    }
        
    
};



function selector_lista_all ( dom ){
   
    
    tabla.linea = "selector";    
    tabla.campos = ['selector', 'descripcion', 'nivel', 'superior', 'codigo', 'ord'];        
    tabla.tbody_id = "selectores-tb";    
           

    ajax.url = html.url.absolute()+'/sistema' +'/'+'selector' + '/htmf/lista.html'; 
    ajax.metodo = "GET";            
    document.getElementById( dom ).innerHTML =  ajax.public.html();         

    ajax.url = html.url.absolute()+'/api/selectores/all' ;
    ajax.metodo = "GET";   
    
    tabla.json = ajax.private.json();  

    tabla.gene();
    tabla.id =  "selectores-tabla"  ;    
    tabla.lista_registro(  selector_registro  );
    
    selector_lista_formato (dom);
    
    
    var btn_nuevo = document.getElementById( "btn_nuevo" );
    btn_nuevo.addEventListener('click',
        function(event) {      
                        
            ajax.url = html.url.absolute()+'/sistema/selector/htmf/form.html'; 
            ajax.metodo = "GET";  
            modal.ancho = 600;
            var obj = modal.ventana.mostrar("ind");                        
            selector_modal_add( obj  );
            
        },
        false
    );     
    
    
    
    
}







function selector_modal_add ( obj ){
    
    
    document.getElementById( "botones_borrar" ).style.display = 'none';
        
    
    var selector_superior = document.getElementById( "selector_superior" );
    selector_superior.value = 0;
    //selector_superior.disabled=true;    
    
    var selector_id = document.getElementById( "selector_selector" );
    selector_id.value = 0;
    //selector_id.disabled=true;
    
    var selector_ord = document.getElementById( "selector_ord" );
    selector_ord.value = 0;
        
    var btn_selmod_nuevo = document.getElementById( "btn_selmod_aceptar" );
    btn_selmod_nuevo.addEventListener('click',
        function(event) {     
            
                        
            //    if ( main_form_validar() )
                if ( true )
                {
                    ajax.metodo = "post";                    
                    ajax.url = html.url.absolute()+"/api/selectores";
                    
                    
                    form.name = "form_selector";
                    
                    var jsondata = ajax.private.json( form.datos.getjson() );  

                
                    if (ajax.state == 200) {
                        
                        
                        btn_selmod_cancelar.click();
                        selector_lista_all ( 'arti_form' );
                        msg.ok.mostrar("registro agregado");           
                        //objetoclase.form_id( dom, getJSONdataID(jsondata, objetoclase.nombre )  ) ;
                        
                        
                        /*
                        if ( typeof objetoclase.fn_add_guardar == 'function' ){
                            objetoclase.fn_add_guardar();    
                        }     
                        */
                        
                        
                    }
                    else{
                        msg.error.mostrar("error de acceso");           
                    }                
                }
                
        }
    );    
    
    
    
    var btn_selmod_cancelar = document.getElementById( "btn_selmod_cancelar" );
    btn_selmod_cancelar.addEventListener('click',
        function(event) {     
            modal.ventana.cerrar(obj);
        }
    );    
    
    
};


function selector_modal_editdelete ( obj, linea ){
    
    ajax.url = html.url.absolute()+'/api/selectores/'+linea;    
    ajax.metodo = "GET";

    form.name = "form_selector" ;
    form.json = ajax.private.json();   

    form.campos = ['selector_id'];
    form.disabled(true);

    form.llenar();     


    var btn_selmod_aceptar = document.getElementById( "btn_selmod_aceptar" );
    btn_selmod_aceptar.innerHTML = "Editar";
    btn_selmod_aceptar.addEventListener('click',
        function(event) {     
                
            //if ( main_form_validar() )
            if ( true )
            {
                var id = document.getElementById('selector_selector').value;
                ajax.metodo = "put";

                ajax.url = html.url.absolute()+"/api/selectores/"+id;                  
                
                form.name = "form_selector";

                var jsondata = ajax.private.json( form.datos.getjson() );                  
                

                if (ajax.state == 200){

                    btn_selmod_cancelar.click();
                    selector_lista_all ( 'arti_form' );                        
                    msg.ok.mostrar("registro editado");                                   

                }
                else{
                    msg.error.mostrar("error de acceso");           
                } 
            }
        }
    ); 


    var btn_selmod_borrarr = document.getElementById( "btn_selmod_borrarr" );
    btn_selmod_borrarr.addEventListener('click',
        function(event) {     
                
            var id = document.getElementById('selector_selector').value;

            ajax.metodo = "delete";            
            ajax.url = html.url.absolute()+"/api/selectores/"+id;        
            
            ajax.private.json( null );  

            if (ajax.state == 200){

                btn_selmod_cancelar.click();
                selector_lista_all ( 'arti_form' );                        
                msg.ok.mostrar("registro editado");                   
            }
            else{
                msg.error.mostrar("error");           
            }                 
                
        }
    );    




    var btn_selmod_cancelar = document.getElementById( "btn_selmod_cancelar" );
    btn_selmod_cancelar.addEventListener('click',
        function(event) {     
                modal.ventana.cerrar(obj);
        }
    );    
    

};





function selector_registro ( linea ){
    
        ajax.url = html.url.absolute()+'/sistema/selector/htmf/form.html'; 
        ajax.metodo = "GET";       

        modal.ancho = 600;
        var obj = modal.ventana.mostrar("ide");   
        selector_modal_editdelete( obj, linea  );    
        
        // tabuladores
        var obj = new Selector();
        obj.tabuladores();

};




function selector_lista_formato ( tabla ){

    var table = document.getElementById( tabla ).getElementsByTagName('tbody')[0];
    var rows = table.getElementsByTagName('tr');

    for (var i=0 ; i < rows.length; i++)
    {
        cell = table.rows[i].cells[1] ;                                  
        //cell.innerHTML = fmtNum(cell.innerHTML);  
        cell.innerHTML = '&nbsp'.repeat(table.rows[i].cells[2].innerHTML * 5) + cell.innerHTML;            
    }



};







function main_lista ( dom  ){
};


function main_form_validar( ){
    return true;
};


function main_form_edit( ){    
};


function busquedalista( ){    
};




                





window.onresize = function() {
    document.body.style.minheight  = "100%" ;  
    var nodeList = document.querySelectorAll('.capaoscura');
    for (var i = 0; i < nodeList.length; ++i) {
        document.getElementById(nodeList[i].id).style.height = document.body.scrollHeight.toString() + "px";                
    }
};








