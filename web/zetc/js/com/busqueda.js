

  
var busqueda = 
{  
    elemid: "bar_busqueda",
    html: "",
    panel: "panel_central",
    idtexto: "busquedatexto",
    id_x: "ico-x",
    idarticulo: "idarticulobusqueda",
    idabajo: "idcompuesta",
    idico_searchbar: "icon-search_bar",
    idcerrar: "icon-up_bar",

    fn_compuesta: null,    
    botonlimpiar: "hidden",
    botoncompuesta: false,
    
    fn_lista_paginacion: null,    
    
    
    
    get_bar_icon: function() {
 
        busqueda.html = "";
        
        busqueda.html += " <div class=\"bar-icon-search\"> ";
        busqueda.html += "  <span class=\"icon-search-mate\"  id=\""+busqueda.idico_searchbar+"\"></span>";
        busqueda.html += " </div>";

        busqueda.html += " <div class=\"bar-icon-search\"> ";
        busqueda.html += "  <span class=\"icon-chevron-up\"  id=\""+busqueda.idcerrar+"\" ></span>";
        busqueda.html += " </div>";
        
        return busqueda.html;              
        
    },
    
    
    
    gene: function( obj ) {        

        var bar = document.getElementById( busqueda.elemid );
        bar.innerHTML = busqueda.get_bar_icon();
        bar.style.display = "flex";       
            
        busqueda.mostrar_icono_busqueda( true, obj );              
        busqueda.bar_accion( obj );    

    },
    
    vista: {                    
        cuadro: function( objeto  ) {
            
            var suf = "";
            if (!(objeto === undefined)) {    
                suf = objeto.tipo;
            }
            
            var html = "";           
            
            html +=                   
            "<div class=\"icono\"> " +                
            "<span class=\"icon-search\" id=\"ico1"+suf+"\"></span>" +
            "</div> " ;                

            html +=         
            "<div class=\"texto\"> " +             
            "<input     autocomplete=\"off\"     placeholder=\"Buscar..\"   id=\""+busqueda.idtexto+suf+ "\"   type=\"text\" />" +
            "</div> " ;

            html +=     
            "<div class=\"icono\"  id=\"icono-x\"    > " +                
            "<span class=\"icon-x\" id=\""+busqueda.id_x+suf+"\"></span>" +
            "</div> " ;                

            html +=     
            "<div class=\"icono\" > " +                
            "<span class=\"icon-chevron-down\" id=\""+busqueda.idabajo+suf+"\"></span>" +
            "</div> " ;    
    

            return  html;
            
        },
        
        contenedor: function(  ) {
        
            var articulo = document.createElement("article");
            articulo.setAttribute("id", busqueda.idarticulo );
            articulo.setAttribute("class", "conten_busqueda" );
            
            return  articulo;            
        },
                
        
        
        
        
        
    },
    
    
    bar_accion: function(obj ) {
        
                
        var search_acction = document.getElementById( busqueda.idico_searchbar );
        search_acction.addEventListener('click',
            function(event) {      
                                       
                if ( !document.getElementById( busqueda.idarticulo ))
                {
                                     
                   var articulo = busqueda.vista.contenedor();
                   
                    var primer = document.getElementById( busqueda.panel ).firstChild;
                    document.getElementById( busqueda.panel  ).insertBefore(articulo,  primer)   ;                

                    var busquedadiv = document.createElement("div");
                    busquedadiv.setAttribute("class", "busqueda_central");        
                    //busquedadiv.setAttribute("id", "algunid");
                    
                    busquedadiv.innerHTML= busqueda.vista.cuadro();
                    
                    articulo.appendChild(busquedadiv);         
                    busqueda.limpiar_mostrar();
                    busqueda.compuesta_mostrar();
                  
                    busqueda.mostrar_icono_busqueda( false, obj );  
         
                    busqueda.texto_evento( obj );
                    busqueda.x_evento();
                    busqueda.compuesta_evento();
                    
                }
                
            },
            false
        ); 
    },
            
            
            
    limpiar_mostrar: function() {                    
        document.getElementById( busqueda.id_x ).style.visibility 
                = busqueda.botonlimpiar;           
    },

            
    compuesta_mostrar: function() {                    
        
        if (busqueda.botoncompuesta == false) {            
            document.getElementById( busqueda.idabajo ).style.visibility = "hidden";        
            
            var divsup = document.getElementById(busqueda.idabajo).parentNode;
            divsup.style.display = "none";
        }
        
        if (busqueda.botoncompuesta == true) {            
            document.getElementById( busqueda.idabajo ).style.visibility = "visible";        
            
            var divsup = document.getElementById(busqueda.idabajo).parentNode;
            divsup.style.display = "block";
        }        
        

    },
                
    
    
    
            
    texto_evento: function( obj ) {        
        
        var texto = document.getElementById( busqueda.idtexto );        
        
        //texto.addEventListener('change',
        texto.addEventListener('keypress',
            function(event) {               




//strvalue =  texto.value.replace(new RegExp("/","g") ," ");




                var strlen = 0;
                
                if ( event.keyCode != 13 ){
                    strlen = (this.value.length + 1);                               
                }
                else{
                    // funcion busqueda              
                    
                    if ( typeof busqueda.fn_lista_paginacion == 'function' ){                        
                        busqueda.fn_lista_paginacion(obj, 1);    
                        
                    }
                    else{                        
                        reflex.lista_paginacion(obj, 1);
                    }
                    
                    /*
                    try {
                        objetoclase.lista(obj, 1);                             
                    } catch (e) {
                        
                    }               
                    */
                    
                }
                
                if (strlen != 0){                                        
                    document.getElementById( busqueda.id_x ).style.visibility = "visible";                    
                }
            },
            false
        )   
    },
        
        
        
        
    x_evento: function() {                
        var busquedax = document.getElementById( busqueda.id_x );        
        busquedax.addEventListener('click',
            function(event) {               
                
                var texto = document.getElementById( busqueda.idtexto );            
                if (texto.length != 0){                
                    texto.value = "";                    
                    document.getElementById( busqueda.id_x ).style.visibility = "hidden";     
                }                
            },
            false
        )   
    },
                
                
                
                
                
    compuesta_evento: function() {                
        var busquedacom = document.getElementById( busqueda.idabajo );        
        busquedacom.addEventListener('click',
            function(event) {               
                
                if ( typeof busqueda.fn_compuesta == 'function' ){
                    busqueda.fn_compuesta();    
                }
                
            },
            false
        )   
    },        
    
    
                
    cerrar_evento: function( obj ) {   

        
        var cerrrarcom = document.getElementById( busqueda.idcerrar );        
        cerrrarcom.addEventListener('click',
            function(event) {               
                
                busqueda.cerrar_busqueda_texto(obj);
                
                
                    try {
                        objetoclase.lista(obj, 1);                             
                    } catch (e) {
                        reflex.lista_paginacion(obj, 1);
                    }                 
                
                
 
            },
            false
        )   
    },        
    
                          
    cerrar_busqueda_texto: function(obj) {                
        
        if ( document.getElementById( busqueda.idarticulo))
        {
            var artbusqueda = document.getElementById( busqueda.idarticulo);                    
            padre = artbusqueda.parentNode;
            padre.removeChild(artbusqueda);
            busqueda.mostrar_icono_busqueda( true, obj );  
        }      
    },        
    
        
        
    
                
    mostrar_icono_busqueda: function( bool, obj ) {   

        if (bool == true){
            
            var arriba = document.getElementById( busqueda.idcerrar ); 
            arriba.style.visibility = "hidden";        
            arriba.parentNode.style.display = "none";                    
            
            var busbar = document.getElementById( busqueda.idico_searchbar ); 
            busbar.style.visibility = "visible";        
            busbar.parentNode.style.display = "block";         
            
        }

        if (bool == false){
            var arriba = document.getElementById( busqueda.idcerrar ); 
            arriba.style.visibility = "visible";        
            arriba.parentNode.style.display = "block";         
            
            busqueda.cerrar_evento(obj);
            
            var buscar = document.getElementById( busqueda.idico_searchbar ); 
            buscar.style.visibility = "hidden";        
            buscar.parentNode.style.display = "none";                                
            
        }        
        

        if (bool == null){
            
            busqueda.cerrar_busqueda_texto();
            
            var arriba = document.getElementById( busqueda.idcerrar ); 
            arriba.style.visibility = "hidden";        
            arriba.parentNode.style.display = "none";                    
            
            var busbar = document.getElementById( busqueda.idico_searchbar ); 
            busbar.style.visibility = "hidden";        
            busbar.parentNode.style.display = "none";     
            
        }           
    },        
    
                        
    
    modal: {                    
        objeto: function( objeto ) {


            if (!(objeto.url === undefined)) {                                    
                ajax.url = objeto.url;                                
            }
            else {                
                ajax.url = html.url.absolute() + objeto.carpeta +'/'+ 
                    objeto.tipo + '/htmf/lista.html';                                
            }
                           

            ajax.metodo = "GET";    
                                    
            objeto.venactiva = modal.ventana.html( 
                                                objeto.tipo + "f", 
                                                ajax.public.html()
                                                );    

            var dom = objeto.venactiva.firstChild.id;       
            
            modal.redim(document.getElementById(dom));            
            
            busqueda.modal.cuadrobusqueda(objeto, dom);
            
                reflex.setTitulosObjeto(objeto)
                reflex.getTituloplu(dom, "h5");
            
            busqueda.modal.textoevento(objeto, busqueda.modal.acctionresul );            
            busqueda.modal.tablabusqueda(objeto, 1, "");
            
            
            boton.objeto = ""+objeto.tipo;
            document.getElementById( objeto.tipo +'_acciones_lista' ).innerHTML 
                    =  boton.basicform.get_botton_cancel();
            
            
            //objeto.funciones =  objeto.modalbusqueda_fn;
            objeto.funciones =   [ boton.accion.modal_cerrar ];
            
            boton.evento( objeto ); 
            
            busqueda.modal.icono_x(objeto, "none");
            busqueda.modal.icono_c(objeto, "none");
            
        }, 
        

        
        
        custom: function( ins ) {



/*
            ajax.url = html.url.absolute() + objeto.carpeta +'/'+ 
                    objeto.tipo + '/htmf/lista.html';    
*/
            ajax.metodo = "GET";    
                                    
                                    /*
            objeto.venactiva = modal.ventana.html( 
                                                objeto.tipo + "f", 
                                                ajax.public.html()
                                                );    
*/

            ajax.url = ins.url;
            ins.venactiva = modal.ventana.html( 
                                                    ins.tipo+"fc", 
                                                    ajax.public.html()
                                                    );    

                                        

            //var dom = objeto.venactiva.firstChild.id;       
            var dom = ins.venactiva.firstChild.id;       
            
            modal.redim(document.getElementById(dom));          
            
            busqueda.modal.cuadrobusqueda(ins, dom);
            
                reflex.setTitulosObjeto(ins)
                reflex.getTituloplu(dom, "h5");
            
            
            
            busqueda.modal.textoevento(ins, busqueda.modal.acctionresul );  
            
            busqueda.modal.tablabusqueda(ins, 1, "");
            
            
            boton.objeto = ""+ins.tipo;
            document.getElementById( ins.tipo +'_acciones_lista' ).innerHTML 
                    =  boton.basicform.get_botton_cancel();
            
            
            //objeto.funciones =  objeto.modalbusqueda_fn;
            ins.funciones =   [ boton.accion.modal_cerrar ];
            
            boton.evento( ins ); 
            
            busqueda.modal.icono_x(ins, "none");
            busqueda.modal.icono_c(ins, "none");
            
            
            
            
        }, 
        
        
        
        
        
        
        cuadrobusqueda: function( objeto, dom  ) {
            
             var articulo = busqueda.vista.contenedor( );

             var primer = document.getElementById( dom ).firstChild;
             document.getElementById( dom  ).insertBefore(articulo,  primer)   ;                

             var busquedadiv = document.createElement("div");
             busquedadiv.setAttribute("class", "busqueda_central");        
             //busquedadiv.setAttribute("id", "algunid");

             busquedadiv.innerHTML= busqueda.vista.cuadro( objeto );
             articulo.appendChild(busquedadiv);       
                
        }, 
                
                
                
                
                
     
        tablabusqueda: function( obj, page, buscar  ) {

            if (buscar === ""){
                buscar = null;
            }
                   
            try {
                reflex.ini(obj);            
                throw "myException" // genera una excepción
            }
            catch (e) {
                // instrucciones para manejar cualquier excepción generada
                reflex.ini(obj);
            }            
                        
          
            try {                
                ajax.url = reflex.getApi(obj, page, buscar);                
                throw "myException" // genera una excepción
            }
            catch (e) {
                // instrucciones para manejar cualquier excepción generada
//                ajax.url =  reflex.getApi(obj, page, buscar);
            }            

            
            ajax.metodo = "GET";        
            tabla.json = ajax.private.json();   
            
            tabla.ini(obj);
            tabla.gene();              
            tabla.formato(obj);
            
                                                       
            tabla.lista_registro(obj, busqueda.modal.acctionresul  );            
        
            document.getElementById( obj.tipo + '_paginacion' ).innerHTML = paginacion.gene();      
          
            paginacion.move(obj, buscar, busqueda.modal.acctionresul  );          
                            
        }, 
                       

        textoevento: function(obj, fn) {        

            var idtexto  = busqueda.idtexto + obj.tipo;
            var texto = document.getElementById( idtexto );        
                        
            // cambiar los caracteress especiales
                
            texto.onkeypress = function(event) {                
                if (event.keyCode == 13) {                 
                    //strvalue =  texto.value.replace(new RegExp("/","g") ," ");
                    
                    strvalue = busqueda.escCaracteres(texto.value);

                    busqueda.modal.tablabusqueda(obj, 1, strvalue );                    
                    paginacion.move(obj, strvalue , fn ); 
                };                
            };                       
        },        
        
 

        acctionresul: function( obj, id  ) {

            if( typeof  obj.acctionresul  ==  "function" ) {                
                obj.acctionresul(id);                
            }
            else
            {                
                
                var ifo = form.get.foreign( "form_" + obj.parent.tipo , obj );         
                
                document.getElementById( ifo ).value = id;             
                document.getElementById( ifo ).onblur();                                
            }

            
            modal.ventana.cerrar( obj.venactiva );     
            
        },

            
        icono_x: function( obj, op ) {                    
            var d = document.getElementById("ico-x"+obj.tipo).parentNode;
            d.style.display = op;      
        },        

        
        icono_c: function( obj,  op ) {                    
            var d = document.getElementById("idcompuesta" +obj.tipo ).parentNode;
            d.style.display = op;      
        },                
                      
    },        
        
    

    getTextoBusqueda: function( ) {               
        
        if (document.getElementById( busqueda.idtexto ))
        {
            var ele = document.getElementById( busqueda.idtexto );
            

            strvalue =  this.escCaracteres(ele.value);
            
            return  strvalue ;
        }
        else{
            return null;
        }        
    },
    
    
    

    escCaracteres: function( strvalue ) {               
        
        strvalue =  strvalue.replace(new RegExp("/","g") ," ");

        return strvalue;
            
    },
    
    
        
    
    /*
    main: {                    
        texto: function( ) {            
            var r = null;            
            if (document.getElementById(busqueda.idtexto)){
                var t = document.getElementById(busqueda.idtexto);
                
//alert("idtexto  " + document.getElementById(busqueda.idtexto))                ;

                r = t.value;
            }
            return r;
        }
    },    
    */
    
    
    
    
    
    
    
};
 
 
