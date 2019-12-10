 
var coleccion = 
{  
    
    clase_cab: 'divtabs',
    clase_cue: 'tabsbody',
    dato: [],   
    id: "",
    objetos: [],   
    html: "",
       
    
    ini: function( obj ) {    
        
        coleccion.dato = obj.tabs;    
        coleccion.id = "tabid";

    },    
    
    
    
        
    cabecera: function(  ) {    

        coleccion.html = "";
        
        coleccion.dato.forEach(function (elemento, indice, array) {                               

            coleccion.html += " <li class=\"tabinactivo\" data-id=\""+indice+"\" > ";
            coleccion.html += " <a href=\"javascript:void(0);\" " ;
            coleccion.html += "   id=\"tab_" +elemento+"\"  >"  ;
            coleccion.html +=      elemento;
            coleccion.html += " </a>" ;
            coleccion.html += " </li>";
            
        }); 
        //final
            coleccion.html += " <div id=\"tabfin\"></div>";

        return "<ul  id=\"" +coleccion.id  + "\">" + coleccion.html +"</ul>"  ;
    },    


     
    cuerpo: function( ) {    

        coleccion.html = "";
        //return "<div  class=\""+coleccion.clase_cue+"\"  id=\""+coleccion.clase_cue+"\">" + html +"</div>"  ;
        return "<div  id=\""+coleccion.clase_cue+"\">" + coleccion.html +"</div>"  ;
    
    },    
    
         
    cuerpo_css_clase: function( ) {                     
        
        var ele = document.getElementById( coleccion.clase_cue );        
        ele.className  =  coleccion.clase_cue ;  
        
        var ele = document.getElementById( "tabfin" );        
        ele.className  =  "tabfin" ;  
        
    },    
             
         
     
    gene: function(  ) {    

        coleccion.html =  "";
        coleccion.html =  "<div  class=\""+coleccion.clase_cab+"\"  id=\""+coleccion.clase_cab+"\"  >" 
                + coleccion.cabecera()
                + coleccion.cuerpo() 
                +"</div>" ;

        return coleccion.html;        
    },    
    
    
    
    desmarcar: function(  ) {            
        
            var el = document.getElementById( coleccion.id ).getElementsByTagName("li");                        
            for (var i=0; i<el.length; i++)
            {      
                el[i].className = "tabinactivo";
            }
    },        
    
   
   
   
   
    interaccion: function( ) {    
        
        var el = document.getElementById( coleccion.id ).getElementsByTagName("li");            

        for (var i=0; i<el.length; i++)
        {                
            el[i].onclick = function()
            {                  
                coleccion.cuerpo_css_clase();                                
                coleccion.desmarcar();                
                this.className = "tabactivo";
                
                var valor_id = this.dataset.id;                                             

                coleccion.objetos[valor_id].sublista(  coleccion.objetos[valor_id] );
                
            };                   
        }       
    },        
    
      
    
    
    
    
    
    sublista: function( objeto ) {            
        
    /*
momentoActual = new Date() 
horaImprimible = momentoActual.getHours()  + " : " +  momentoActual.getMinutes()  + " : "
        + momentoActual.getSeconds() + " : " + momentoActual.getMilliseconds()
console.log( " ------ INICIO sublista " + i+"  " + horaImprimible );            
      */
     
     
     
        
        var tabsbody = document.getElementById('tabsbody');                    
        //ajax.url = html.url.absolute() + objetoclase.carpeta +'/'+objeto.tipo + '/htmf/lista.html';   
        ajax.url = html.url.absolute() + objeto.carpeta +'/'+objeto.tipo + '/htmf/lista.html';   
        
        ajax.metodo = "GET";            
        tabsbody.innerHTML =  ajax.public.html();     

        boton.objeto = objeto.tipo;
        var acc = document.getElementById( objeto.botones_lista ); 
        
        acc.innerHTML = boton.basicform.get_botton_att();         

        objeto.funciones = [ objeto.modal_new ] ;
        boton.evento(objeto);


        ajax.url = reflex.getApiRecursoMTM( objeto, 1, null );      
       
//        ajax.url = objetoclase.getApiRecursoMTM( objeto, 1, null );       
        
        ajax.metodo = "GET";        
        tabla.json = ajax.private.json();             
        
        
        tabla.ini(objeto);               
        tabla.gene();        
        
//return;         

        tabla.lista_registro( objeto,  coleccion.sublista_reg );   
        //tabla.lista_registro( objeto.modal );        
                
        tabla.oculto = objeto.tablacamposoculto;        
        tabla.ocultar();                
        
               
    },        
    
             
       
       
    sublista_reg: function( objeto, lineaid ) {            

        modal.form.row (objeto, lineaid);

    },                
    
};
 
 		 