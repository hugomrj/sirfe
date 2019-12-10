
var objetoclase = 
{      
    recurso:  "",
    carpeta:  "/sistema",     
    titulosin:     "",
    tituloplu:     "",
    data:"",
    
    idbusqueda: "",    
    
    fn_edit: null,
    fn_lista:null,        
    fn_lista_nuevo:null,
        
    fn_reg_modificar:null,    
    fn_reg_agregar: null,    
    fn_reg_eliminar:null,
    
    
    fn_add_guardar:null,
    fn_add_cancelar:null,   
       
    fn_edit_editar:null,
    fn_edit_cancelar:null,
        
    fn_del_eliminar:null,
    fn_del_cancelar:null,
    
       
    ini: function(obj ) {        
        objetoclase.recurso = obj.recurso;        
        objetoclase.data = "";        
    } ,      
  
        
    
    
    lista: function(obj,  page ) {
        
        ajax.url = html.url.absolute() + obj.carpeta +'/'+ obj.tipo + '/htmf/lista.html';    

        ajax.metodo = "GET";            
        document.getElementById( obj.dom ).innerHTML =  ajax.public.html();         

        objetoclase.getTituloplu(obj);
                                                                                                
        var bu = "";
        bu = busqueda.getTextoBusqueda();
                                                
        ajax.url = objetoclase.getApi( obj, page, bu );

        ajax.metodo = "GET";        
        tabla.json = ajax.private.json();    


        tabla.ini(obj);
        tabla.gene();        
        tabla.set.tablaid(obj);                
        
        
        tabla.lista_registro(obj, objetoclase.form_id ); 
        document.getElementById( obj.tipo + "_paginacion" ).innerHTML 
                = paginacion.gene();     


        paginacion.move(obj, "", objetoclase.form_id );
                
        boton.objeto = ""+obj.tipo;
        document.getElementById( obj.tipo +'_acciones_lista' ).innerHTML 
                =  boton.basicform.get_botton_new();


        obj.funciones =  obj.botones_lista;
        boton.evento( obj ); 

    },        
    
    
    

    setTitulosObjeto: function( obj ) {         
        
        var expre = typeof obj;      
        if (expre ===  'object'){            
            objetoclase.titulosin = obj.titulosin;
            objetoclase.tituloplu = obj.tituloplu;
        }                
      
    },
    

    getTitulosin: function( obj, h ) {     
                
       if (h === undefined) {    h = "h4";}        
        objetoclase.setTitulosObjeto(obj);
        
        if (objetoclase.titulo != ""){            
    
            var primer = document.getElementById( obj.dom ).firstChild;
            var htitulo = document.createElement(h);
            htitulo.innerHTML = objetoclase.titulosin;
            document.getElementById( obj.dom ).insertBefore(htitulo,  primer) ;                      
        }        
    },
    



    getTituloplu: function( para, h  ) {  
                
        if ( typeof  para  == 'object' ){                        
            var d  = para.dom;
        }
        if ( typeof  para  == 'string' ){
            var d = para;
        }        
        

        if (h === undefined) {    h = "h4";}        
        objetoclase.setTitulosObjeto(para);
        
        if (objetoclase.titulo != ""){            
    
            var primer = document.getElementById( d ).firstChild;
            var htitulo = document.createElement(h);
            htitulo.innerHTML = objetoclase.tituloplu;
            document.getElementById( d  ).insertBefore(htitulo,  primer);                    

        }        
    },
    




    getBusqueda: function( ) {               
        
        if (document.getElementById( objetoclase.idbusqueda ))
        {
            var ele = document.getElementById( objetoclase.idbusqueda );
            return  ele.value;
        }
        else{
            return null;
        }        
    },




    getApi: function( obj, page, busqueda ) {        
    
        if ( page === undefined) { page = 1;}       
        
        if ( busqueda === undefined) {             
            var textobusqueda = busqueda.getTextoBusqueda();
        } 
        else {
            var textobusqueda = busqueda;
        }        
        
        if (textobusqueda == null){
            return html.url.absolute()+'/api/' + obj.recurso + '?page=' + page;   
        }
        else{
            return html.url.absolute()+'/api/' + obj.recurso + '/search/' 
                + ';q=' + textobusqueda + '?page=' + page;  ;    
        }        
    },        



    getApiRecursoMTM: function( obj, page ) {        

        var recurso = obj.recurso 
                +"/" + obj.cabecera.tipo + "/" +obj.cabecera.value +"/"   
        return objetoclase.getApiRecurso(recurso, page, null );
        
    },        





    getApiRecurso: function( recurso, page, busqueda ) {        
        
        if (page == null){ page = 1;}          
        
        if (busqueda == null){
            return html.url.absolute()+'/api/' + recurso + '?page=' + page;   
        }
        else{
            return html.url.absolute()+'/api/' + recurso+ '/search/' 
                + ';q=' + textobusqueda + '?page=' + page;  ;    
        }        
    },        


       
    


    form_id : function ( obj, id ){    

        objetoclase.ini( obj );
        objetoclase.form( obj );
        
        ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+id;    
        ajax.metodo = "GET";

        form.name = "form_" + obj.tipo;
        form.json = ajax.private.json();   
        
        form.disabled(false);
        form.llenar();                

        objetoclase.button_reg(obj );                
           
        var expre = typeof obj.tabuladores;        
        if (expre ===  'function'){   
            obj.tabuladores( obj );
        }
  
    },


    

    form : function ( obj ) {    

        ajax.url = html.url.absolute()+'/'+ obj.carpeta + '/' + obj.tipo + '/htmf/form.html';  

        ajax.metodo = "GET";
        //obj.dom = "arti_form";

        document.getElementById(  obj.dom ).innerHTML =  ajax.public.html();    
        //document.getElementById(  obj.botones_form ).innerHTML =  ajax.public.html();    

        objetoclase.getTitulosin( obj ) ;


        var campoid = document.getElementById(  obj.tipo + '_' + obj.campoid  );
        campoid.value = '0';
        campoid.disabled = true;
        
        if(typeof obj.form_ini === 'function') {
          obj.form_ini();
        }           
        

    },
    



    button_add: function( obj ) 
    {
    
        boton.objeto = "" + obj.tipo;        
        
        boton.ini(obj);
        document.getElementById( obj.tipo +'-acciones' ).innerHTML 
                    =  boton.basicform.get_botton_add();        
        
        var btn_objeto_guardar = document.getElementById('btn_'+ obj.tipo +'_guardar');
        btn_objeto_guardar.addEventListener('click',
            function(event) {               


                if ( obj.form_validar())
                {
                    ajax.metodo = "post";
                    ajax.url = html.url.absolute()+"/api/"+ objetoclase.recurso;               
                    
                    form.name = "form_"+obj.tipo;
                    
                    var jsondata = ajax.private.json( form.datos.getjson() );  

                    if (ajax.state == 200){

                        msg.ok.mostrar("registro agregado");          
                         //alert( objetoclase.getJSONdataID( obj.tipo ) ) ;
                        //objetoclase.form_id( obj, 16 ) ;
                        
                        objetoclase.data  = jsondata;
                        objetoclase.form_id( obj, objetoclase.getJSONdataID( obj.campoid ) ) ;
                        
                        if ( typeof objetoclase.fn_add_guardar == 'function' ){
                            objetoclase.fn_add_guardar();    
                        }    
                    }
                    else{
                        msg.error.mostrar("error de acceso");           
                    }                
                }
                
            },
            false
        )



        var btn_objeto_cancelar = document.getElementById('btn_'+ obj.tipo +'_cancelar');
        btn_objeto_cancelar.addEventListener('click',
            function(event) {     

                if ( typeof objetoclase.fn_add_cancelar == 'function' ){
                    objetoclase.fn_add_cancelar();    
                }                                

                objetoclase.lista(obj, 1);
            },
            false
        );    

    },

  
  
  
    button_reg : function( obj )
    {
        
        boton.ini(obj);
        document.getElementById(  obj.tipo + '-acciones' ).innerHTML 
                =  boton.basicform.get_botton_reg();
               
        
        var btn_objeto_nuevo = document.getElementById('btn_' + obj.tipo + '_nuevo');
        btn_objeto_nuevo.addEventListener('click',
            function(event) {       

                if ( typeof objetoclase.fn_reg_agregar == 'function' ){
                    objetoclase.fn_reg_agregar();    
                }                
                objetoclase.form(obj);
                objetoclase.button_add(obj);                           
            },
            false
        );    




        var btn_objeto_modificar = document.getElementById('btn_' + obj.tipo + '_modificar');
        btn_objeto_modificar.addEventListener('click',
            function(event) {       
                                         
                
                if ( typeof objetoclase.fn_reg_modificar == 'function' ){
                    objetoclase.fn_reg_modificar();    
                }                
                                
                form.name = "form_" + obj.tipo ;
                form.campos =  [  obj.tipo + '_'  + obj.campoid ];                 
                form.disabled(true);
      
      
                objetoclase.button_edit(obj);
                objetoclase.tabs.oculta(obj);
                
                if (objetoclase.fn_edit != null){
                    objetoclase.fn_edit();
                }
                
            },
            false
        );    



        var btn_objeto_eliminar = document.getElementById('btn_' + obj.tipo + '_eliminar');
        btn_objeto_eliminar.addEventListener('click',
            function(event) {       
                
                if ( typeof objetoclase.fn_reg_eliminar == 'function' ){
                    objetoclase.fn_reg_eliminar();    
                }                
                objetoclase.button_del(obj);
                objetoclase.tabs.oculta(obj);
            },
            false
        );    




        var btn_objeto_lista = document.getElementById('btn_' + obj.tipo + '_lista');
        btn_objeto_lista.addEventListener('click',
            function(event) { 
                
                objetoclase.lista(obj, 1);
            },
            false
        );    
    },




    button_edit: function( obj ){
        
        boton.ini(obj);
        document.getElementById(  obj.tipo + '-acciones' ).innerHTML 
                =  boton.basicform.get_botton_edit();
        

        var btn_objeto_editar = document.getElementById('btn_' + obj.tipo + '_editar');
        btn_objeto_editar.addEventListener('click',
            function(event) {               
                
                if ( obj.form_validar())
                {  
          
                    var id = document.getElementById( obj.tipo + '_'  + obj.campoid ).value;

                    ajax.metodo = "put";      
                    ajax.url = html.url.absolute()+"/api/" + objetoclase.recurso + "/"+id;                    
                    
                    form.name = "form_"+obj.tipo;
                    var jsondata = ajax.private.json( form.datos.getjson() );  

                    if (ajax.state == 200){
                    
                        //objetoclase.form_id( obj, getJSONdataID(jsondata, obj.tipo ) );                        
                        objetoclase.data  = jsondata;
                        
                        objetoclase.form_id( obj, objetoclase.getJSONdataID( obj.campoid ) ) ;

                        msg.ok.mostrar("registro editado");        
                        
                        if ( typeof objetoclase.fn_edit_editar == 'function' ){
                            objetoclase.fn_edit_editar();    
                        }      
                        
                        
                    }
                    else
                    {
                        msg.error.mostrar("error de acceso");           
                    } 
                }
            },
            false
        );    




        var btn_objeto_cancelar = document.getElementById('btn_'+ obj.tipo +'_cancelar');
        btn_objeto_cancelar.addEventListener('click',
            function(event) {            
                                            
                if ( typeof objetoclase.fn_edit_cancelar == 'function' ){
                    objetoclase.fn_edit_cancelar();    
                }                   

                var id = document.getElementById(obj.tipo + '_'  + obj.campoid).value;                
              
                
                
//alert(id);


                objetoclase.form_id(obj, id);

            },
            false
        );    
    },
    
    
    
    
    button_del: function( obj ){
  
        reflex.ini(obj);
        boton.ini(obj);
        document.getElementById(  obj.tipo + '-acciones' ).innerHTML 
                =   boton.basicform.get_botton_del();
        

        var btn_objeto_eliminar = document.getElementById('btn_' +  obj.tipo + '_eliminar');
        btn_objeto_eliminar.addEventListener('click',
            function(event) {               

                    var id = document.getElementById( obj.tipo + '_'  + obj.tipo).value;
                    ajax.metodo = "delete";            
                    ajax.url = html.url.absolute()+"/api/" + objetoclase.recurso + "/"+id;           
                    ajax.private.json( null );  




                   // error al borrar usuariorol vuelve a quedarse en usuariorol y no pasa a usuario




                    if (ajax.state == 200)
                    {
                        
                        //objetoclase.fn_lista( obj, 1 );
                        objetoclase.lista( obj, 1 );
                        msg.ok.mostrar("registro eliminado");      
                        
                        if ( typeof objetoclase.fn_del_eliminar == 'function' ){
                            objetoclase.fn_del_eliminar();    
                        }                           
                    }
                    else
                    {
                        
                        if (ajax.state == 401)
                        {                        
                            window.location = html.url.absolute();
                        }
                        else
                        {
                            msg.error.mostrar("error");           
                        } 
                    }
                    
            },
            false
        );    


        var btn_objeto_cancelar = document.getElementById('btn_'+obj.tipo+'_cancelar');
        btn_objeto_cancelar.addEventListener('click',
            function(event) {              
                
                if ( typeof objetoclase.fn_del_cancelar == 'function' ){
                    objetoclase.fn_del_cancelar();    
                }                   
               objetoclase.button_reg(obj);
            },
            false
        );    

    },


    
    fn: {    
        asignar: function( ) {                                  
             
            objetoclase.fn_lista_nuevo = objetoclase.fn.ocultar_botones_bar;
            
            objetoclase.fn_reg_agregar = objetoclase.fn.ocultar_botones_bar;
            objetoclase.fn_reg_modificar = objetoclase.fn.ocultar_botones_bar;        
            objetoclase.fn_reg_eliminar = objetoclase.fn.ocultar_botones_bar;
            

            objetoclase.fn_add_guardar = objetoclase.fn.mostrar_botones_bar;
            objetoclase.fn_add_cancelar = objetoclase.fn.mostrar_botones_bar;
            
            objetoclase.fn_edit_editar = objetoclase.fn.mostrar_botones_bar;
            objetoclase.fn_edit_cancelar = objetoclase.fn.mostrar_botones_bar;
                        
            objetoclase.fn_del_eliminar = objetoclase.fn.mostrar_botones_bar;
            objetoclase.fn_del_cancelar = objetoclase.fn.mostrar_botones_bar;
                
        },
        
        ocultar_botones_bar: function (  ){      
            busqueda.mostrar_icono_busqueda( null );  
        },
        mostrar_botones_bar : function (  ){      
            busqueda.mostrar_icono_busqueda( true );  
        },
    },
    
    
    vista: {   

        interfase: function( obj ) {                
     
            objetoclase.fn.asignar();
            busqueda.fn_consulta = objetoclase.vista.lista;
            busqueda.gene( obj ); 
            
            paginacion.pagina = 1;    
              
            tabla.linea =  obj.campoid;
            tabla.campos = obj.tablacampos;                    
            tabla.tbody_id = obj.tbody_id;        

            objetoclase.nombre = obj.tipo;                    
            objetoclase.recurso =  obj.recurso;                    
            
            objetoclase.idbusqueda = busqueda.idtexto;
            boton.objeto = objetoclase.nombre;    
            
            objetoclase.vista.lista( obj );
            
        },
   
        lista: function( obj ) {                
            objetoclase.lista( obj, paginacion.pagina);     
        },   
   
    },


    tabs: {           
        oculta: function(  obj ) {        
            var ot = document.getElementById( obj.tipo + '-tabuladores' );
            ot.innerHTML = "";
        },
    },

    
    ui: {          
        
        foreign_more: function( obj ) {        
            
            for (var i=0; i < obj.foreign.length; i++)
            {
                var tid =  "ico-more-"+ obj.foreign[i].tipo ;
                
                var bton = document.getElementById(tid);                                
                bton.onclick = function(  )
                {  
                    var valor_ind = this.dataset.ind;                              
                    var ob = obj.foreign[valor_ind];     
                    ob.parent = obj;                
                    busqueda.modal.objeto(ob);
                };                   
            }                
        },
        
        
        
        foreign_plus: function( obj ) {        
            
            for (var i=0; i < obj.foreign.length; i++)
            {
                var tid =  "ico-plus-"+ obj.foreign[i].tipo ;
                
                var bton = document.getElementById(tid);                                
                bton.onclick = function(  )
                {  
                    var valor_ind = this.dataset.ind;                              
                    var seg = obj.foreign[valor_ind];     
                    seg.parent = obj;                

                    modal.form.plus(seg);
                };                   
            }                
        },        
        
        
        foreign_decrip: function(  obj ) {        
   
            
            for (var i=0; i < obj.foreign.length; i++)
            {  
                
                var tid =  obj.tipo + "_"+ obj.foreign[i].tipo + "_"+ obj.foreign[i].tipo ;                                
                //var objfo = obj.foreign[i];
                
                var domid = document.getElementById(tid);                     
                
                domid.onblur = function()
                {  
                        if (this.value.toString().trim() == "" ){
                            this.value = 0;                        
                        }                
                        
                        if (isNaN(this.value)) {                            
                          this.value = 0;
                        }              
                        
                        var objev = eval( "new "+this.dataset.foreign+"()");     
                        form.relaciondescrip(this.value, objev);                        
                    
                }
                
                
            }                
        },     
        
        
        foreign_ocultar: function( obj  ) {        
            
            var campos = document.getElementById( "form_" + obj.tipo ).querySelectorAll(".icono") ;    
            
            for (var i=0; i< campos.length; i++) {
                campos[i].style.display = "none";
                campos[i].parentNode.className = "bl-1";                
            }             
            
        },     
                
        
    },    
    
    
    
    getJSONdataID: function(  campo ) { 
        var oJson = JSON.parse(objetoclase.data) ;               
        return  oJson[campo];              
    },
        
    
    
    funciones: function(  obj,  fn ) {    
 
        console.log(fn);
 
        if ( fn.length == 1){            
            
momentoActual = new Date() 
horaImprimible = momentoActual.getHours()  + " : " +  momentoActual.getMinutes()  + " : "
        + momentoActual.getSeconds() + " : " + momentoActual.getMilliseconds()
console.log( " ----- INICIO fn      "  + horaImprimible );             
            
            fn(obj);
            
momentoActual = new Date() 
horaImprimible = momentoActual.getHours()  + " : " +  momentoActual.getMinutes()  + " : "
        + momentoActual.getSeconds() + " : " + momentoActual.getMilliseconds()
console.log( " ----- FIn fn     "  + horaImprimible );                         
            
        }
        else
        {            
            for (var i = 0; i < fn.length; i++) {      
                
                
momentoActual = new Date() 
horaImprimible = momentoActual.getHours()  + " : " +  momentoActual.getMinutes()  + " : "
        + momentoActual.getSeconds() + " : " + momentoActual.getMilliseconds()
console.log( " ----- INICIO fn " + i+"  " + horaImprimible );                 
                
                fn[i](obj);                        
                
momentoActual = new Date() 
horaImprimible = momentoActual.getHours()  + " : " +  momentoActual.getMinutes()  + " : "
        + momentoActual.getSeconds() + " : " + momentoActual.getMilliseconds()
console.log( " ----- FIN fn " + i+"  " + horaImprimible );                 


            }
        }
        
    },
        
        
    
    
} ;  