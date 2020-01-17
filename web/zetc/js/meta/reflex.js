
var reflex = 
{      
    
    recurso:  "",
    data:"",
    idbusqueda: "",               
       
    titulosin:     "",
    tituloplu:     "",       
       
       
       
    ini: function(obj ) {        
        reflex.recurso = obj.recurso;        
        reflex.data = "";        
    } ,      
  
               
    
    
    lista_paginacion: function(obj,  page ) {
        
                
            if (!(obj.alias === undefined)) 
            {    
                ajax.url = html.url.absolute() + obj.carpeta +'/'+ obj.alias + '/htmf/lista.html';    
            }              
            else
            {
                ajax.url = html.url.absolute() + obj.carpeta +'/'+ obj.tipo + '/htmf/lista.html';    
            }
        
                

        ajax.metodo = "GET";            
        document.getElementById( obj.dom ).innerHTML =  ajax.public.html();
        reflex.getTituloplu(obj);
        
                                                         
        var bu = "";
        bu = busqueda.getTextoBusqueda();                                                
        ajax.url = reflex.getApi( obj, page, bu );

        
        ajax.metodo = "GET";        
        tabla.json = ajax.private.json();    


        tabla.ini(obj);
        tabla.gene();   
        tabla.formato(obj);
        
        
        tabla.set.tablaid(obj);     
        tabla.lista_registro(obj, reflex.form_id ); 
        
        
        document.getElementById( obj.tipo + "_paginacion" ).innerHTML 
                = paginacion.gene();     
        
        
       var buscar = busqueda.getTextoBusqueda();
        
        paginacion.move(obj, buscar, reflex.form_id );        
        //paginacion.move(obj, "", reflex.form_id );        


        boton.objeto = ""+obj.tipo;
        document.getElementById( obj.tipo +'_acciones_lista' ).innerHTML 
                =  boton.basicform.get_botton_new();


        obj.funciones =  obj.botones_lista;

        boton.evento( obj ); 

    },        
    
           
       
    
    interfase: {   

        basica: function( obj ) {                
     
            //objetoclase.fn.asignar();
            //busqueda.fn_consulta = reflex.vista.lista;
            busqueda.gene( obj ); 
            
            paginacion.pagina = 1;    
              
            tabla.linea =  obj.campoid;
            tabla.campos = obj.tablacampos;                    
            tabla.tbody_id = obj.tbody_id;        

            //reflex.nombre = obj.tipo;                    
            //objetoclase.recurso =  obj.recurso;                    
            
            reflex.idbusqueda = busqueda.idtexto;
            boton.objeto = obj.nombre;    
            
            //objetoclase.vista.lista( obj );
            reflex.lista_paginacion(obj, paginacion.pagina);
            
        },
   
   
    },        
    



    setTitulosObjeto: function( obj ) {         
        
        var expre = typeof obj;      
        if (expre ===  'object'){            
            reflex.titulosin = obj.titulosin;
            reflex.tituloplu = obj.tituloplu;
        }  
        
    },
        
    
    

    getTitulosin: function( para, h ) {     
        
                
        if ( typeof  para  == 'object' ){                        
            var d  = para.dom;
        }
        if ( typeof  para  == 'string' ){
            var d = para;
        }                
                
        
        if (h === undefined) {  h = "h4";}                
        reflex.setTitulosObjeto(para);
        
        if (reflex.titulo != ""){            
    
            var primer = document.getElementById( d ).firstChild;
            var htitulo = document.createElement(h);
            htitulo.innerHTML = reflex.titulosin;
            document.getElementById( d  ).insertBefore(htitulo,  primer);                    

        }                       
                
     
    },
            

    getTituloplu: function( para, h  ) {  
                
        if ( typeof  para  == 'object' ){                        
            var d  = para.dom;
        }
        if ( typeof  para  == 'string' ){
            var d = para;
        }                
        
        if (h === undefined) {  h = "h4";}                
        reflex.setTitulosObjeto(para);
        
        if (reflex.titulo != ""){            
    
            var primer = document.getElementById( d ).firstChild;
            var htitulo = document.createElement(h);
            htitulo.innerHTML = reflex.tituloplu;
            document.getElementById( d  ).insertBefore(htitulo,  primer);                    

        }        
    },
    
    
    
    


    getApi: function( obj, page, busqueda ) {        
    
    
/*arreglar tiene que salir null en texto cuando no hay nada*/    


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





    form_id : function ( obj, id ){    


        reflex.ini( obj );        
        reflex.form( obj );
        
        ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+id;    
        ajax.metodo = "GET";

        form.name = "form_" + obj.tipo;
        form.json = ajax.private.json();   
        

        form.disabled(false);
        form.llenar();                
        form.llenar_cbx(obj);                
        
                
        if(typeof obj.form_ini === 'function') {
          obj.form_ini();
        }                                   
        
        //objetoclase.button_reg(obj );                
        reflex.acciones.button_reg(obj);

        
        var expre = typeof obj.tabuladores;        
        if (expre ===  'function'){   
            obj.tabuladores( obj );
        }  
        
        
        
        var expre = typeof obj.post_form_id;        
        if (expre ===  'function'){   
            obj.post_form_id( obj, id );
        }  
        
        
        
    },




    form_new : function ( obj ) {    

        reflex.ini( obj );        
        reflex.form( obj );

        reflex.datos.combo(obj);
        
        var campoid = document.getElementById(  obj.tipo + '_' + obj.campoid  );
        campoid.value = '0';
        campoid.disabled = true;
        
    },
    



    

    form : function ( obj ) {    

        //ajax.url = html.url.absolute()+'/'+ obj.carpeta + '/' + obj.tipo + '/htmf/form.html';  

        if (!(obj.alias === undefined)) 
        {    
            ajax.url = html.url.absolute() + obj.carpeta +'/'+ obj.alias + '/htmf/form.html';    
        }              
        else
        {
            ajax.url = html.url.absolute() + obj.carpeta +'/'+ obj.tipo + '/htmf/form.html';    
        }

                

        ajax.metodo = "GET";        

        document.getElementById(  obj.dom ).innerHTML =  ajax.public.html();            
        reflex.getTitulosin( obj ) ;


        var campoid = document.getElementById(  obj.tipo + '_' + obj.campoid  );
        campoid.value = '0';
        campoid.disabled = true;
        
        
        
        if(typeof obj.form_ini === 'function') {
          obj.form_ini();
        }           
        
    },
    
    
    getJSONdataID: function(  campo ) { 
        var oJson = JSON.parse(reflex.data) ;               
        return  oJson[campo];              
    },
        
        
    
    

    acciones: {   

        button_reg : function( obj ){


            // falta bloquear cuadros de busqueda;            
            form.name = "form_" + obj.tipo;
            
            form.ocultar_foreign();

            boton.ini(obj);

            if (typeof  obj.acciones == "undefined"){                
                var strhtml =  boton.basicform.get_botton_reg();                
            }
            else
            {
                if (typeof  obj.acciones.botones == "undefined"){
                    var strhtml =   boton.basicform.get_botton_reg();
                }
                else{ 
                    var strhtml =   boton.basicform.get_botton(obj.acciones.botones);                    
                }
            }
            document.getElementById(  obj.tipo + '-acciones' ).innerHTML = strhtml;
                        



            if ( document.getElementById('btn_' + obj.tipo + '_nuevo')) {                
                
                    var btn_objeto_nuevo = document.getElementById('btn_' + obj.tipo + '_nuevo');
                    btn_objeto_nuevo.addEventListener('click',
                        function(event) {       
                            
                            /*  
                            reflex.form_new(obj);                                   
                            reflex.acciones.button_add(obj);                           
                            */
                            
                            if ( typeof obj.new == 'function' ){
                                  obj.new( obj );
                            }
                            else{
                                obj.lista_new( obj );
                            }
                           
                        },
                        false
                    );   
            }




            if ( document.getElementById('btn_' + obj.tipo + '_modificar')){                
            
                    var btn_objeto_modificar = document.getElementById('btn_' + obj.tipo + '_modificar');
                    btn_objeto_modificar.addEventListener('click',
                        function(event) {       



                            form.name = "form_" + obj.tipo ;
                            form.campos =  [  obj.tipo + '_'  + obj.campoid ];                 
                            form.disabled(true);

                            reflex.datos.combo(obj);
                            form.mostrar_foreign();

                            //objetoclase.button_edit(obj);
                            reflex.acciones.button_edit(obj);
                            reflex.tabs.oculta(obj);
                            
                            
                            reflex.control.preedit(obj);
               
                            
                        },
                        false
                    );    
            
            }




            if ( document.getElementById('btn_' + obj.tipo + '_eliminar')){          

                    var btn_objeto_eliminar = document.getElementById('btn_' + obj.tipo + '_eliminar');
                    btn_objeto_eliminar.addEventListener('click',
                        function(event) {       
             
                            reflex.acciones.button_del(obj);
                            reflex.tabs.oculta(obj);
                        },
                        false
                    );    

            }


            if ( document.getElementById('btn_' + obj.tipo + '_lista')){          

                    var btn_objeto_lista = document.getElementById('btn_' + obj.tipo + '_lista');
                    btn_objeto_lista.addEventListener('click',
                        function(event) { 
                            reflex.lista_paginacion(obj, 1);
                        },
                        false
                    );    
            }
    
        },
        
        
        
        

        button_add: function( obj ) {

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
                        ajax.url = html.url.absolute()+"/api/"+ obj.recurso;               


                        form.name = "form_"+obj.tipo;

                        var data = ajax.private.json( form.datos.getjson() );  
                        

                        switch (ajax.state) {
                            
                          case 200:
                              
                                msg.ok.mostrar("registro agregado");          
                                reflex.data  = data;
                                reflex.form_id( obj, reflex.getJSONdataID( obj.campoid ) ) ;                                                                                        
                                break; 
                            
                            
                          case 401:
                              
                                window.location = html.url.absolute();         
                                break;                             
                            

                          case 500:
                              
                                msg.error.mostrar( data );          
                                break; 
                                
                                
                          case 502:                              
                                msg.error.mostrar( data );          
                                break;                                 
                                

                          default: 
                            msg.error.mostrar("error de acceso");           
                        }                        

               
                    }

                },
                false
            )



            var btn_objeto_cancelar = document.getElementById('btn_'+ obj.tipo +'_cancelar');
            btn_objeto_cancelar.addEventListener('click',
                function(event) {     

                    reflex.lista_paginacion(obj, 1);
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

                    if ( obj.form_validar()){  

                        var id = document.getElementById( obj.tipo + '_'  + obj.campoid ).value;

                        ajax.metodo = "put";      
                        ajax.url = html.url.absolute()+"/api/" + obj.recurso + "/"+id;                    

                        form.name = "form_"+obj.tipo;

                        var data = ajax.private.json( form.datos.getjson() );  
           

                        switch (ajax.state) {
                            
                          case 200:
                              
                                msg.ok.mostrar("registro editado");          
                                reflex.data  = data;
                                reflex.form_id( obj, reflex.getJSONdataID( obj.campoid ) ) ;                                                                                        
                                break; 
                            
                            
                          case 401:
                              
                                window.location = html.url.absolute();         
                                break;                             
                            

                          case 500:
                              
                                msg.error.mostrar( data );          
                                break; 
                                
                          case 502:                              
                                msg.error.mostrar( data );          
                                break;                                                                 

                          default: 
                                msg.error.mostrar("error de acceso");           
                                
                        }                        


                    }
                },
                false
            );    




            var btn_objeto_cancelar = document.getElementById('btn_'+ obj.tipo +'_cancelar');
            btn_objeto_cancelar.addEventListener('click',
                function(event) {            

                    var id = document.getElementById(obj.tipo + '_'  + obj.campoid).value;                    
                    reflex.form_id(obj, id);

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


                        var id = document.getElementById( obj.tipo + '_'  + obj.campoid ).value;
                        ajax.metodo = "delete";            
                        ajax.url = html.url.absolute()+"/api/" + obj.recurso + "/"+id;           
                        var data = ajax.private.json( null );  


                        switch (ajax.state) {

                            case 200:

                                  msg.ok.mostrar("registro eliminado");          
                                  reflex.lista_paginacion(obj, 1);
                                  break; 


                            case 401:

                                  window.location = html.url.absolute();         
                                  break;                             


                            case 500:

                                  msg.error.mostrar( data );          
                                  break; 

                            default: 
                              msg.error.mostrar("error de acceso");           
                        }                        
                },
                false
            );    


            var btn_objeto_cancelar = document.getElementById('btn_'+obj.tipo+'_cancelar');
            btn_objeto_cancelar.addEventListener('click',
                function(event) {              
                   reflex.acciones.button_reg(obj);
                },
                false
            );    

        },
        
        
        
   
    },  


    
    
    
    funciones: function(  obj,  fn ) {    
 
        //console.log(fn);
 
        if ( fn.length == 1){            
    
            /*
ma = new Date() 
horaImprimible = ma.getHours()  + " : " +  ma.getMinutes()  + " : "
        + ma.getSeconds() + " : " + ma.getMilliseconds()
console.log( " ----- INICIO fn      "  + horaImprimible );             
            */
 
            fn(obj);
           
/*            
ma = new Date() 
horaImprimible = ma.getHours()  + " : " +  ma.getMinutes()  + " : "
        + ma.getSeconds() + " : " + ma.getMilliseconds()
console.log( " ----- FIn fn     "  + horaImprimible );                         
   */         
        }
        else
        {            
            for (var i = 0; i < fn.length; i++) {                      
/*                
ma = new Date() 
horaImprimible = ma.getHours()  + " : " +  ma.getMinutes()  + " : "
        + ma.getSeconds() + " : " + ma.getMilliseconds()
console.log( " ----- INICIO fn " + i+"  " + horaImprimible );                 
   */             
                fn[i](obj);                        
      /*          
ma = new Date() 
horaImprimible = ma.getHours()  + " : " +  ma.getMinutes()  + " : "
        + ma.getSeconds() + " : " + ma.getMilliseconds()
console.log( " ----- FIN fn " + i+"  " + horaImprimible );                 
*/

            }
        }
        
    },
            
    
    tabs: {           
        oculta: function(  obj ) {        
            var ot = document.getElementById( obj.tipo + '-tabuladores' );
            ot.innerHTML = "";
        },
    },
            
    
    
    datos: {           
        combo: function(  obj ) {        
            if (!(obj.carga_combos === undefined)) {    
                obj.carga_combos(obj);
            }            
        },        
    },
           
       
    
        
    control: {           
        preedit: function(  obj ) {        
            if (!(obj.preedit === undefined)) {    
                obj.preedit(obj);
            }            
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
    
    
    
    


    getApiRecursoMTM: function( obj, page ) {        

        var recurso = obj.recurso +"/" + obj.cabecera.tipo + "/" +obj.cabecera.value +"/"   
        return reflex.getApiRecurso(recurso, page, null );
        
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


           
    
    
    
} ;  