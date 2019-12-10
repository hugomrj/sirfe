
var modal = 
{    
    
    ancho: 500,   
    
    
    
    dimensionar : function( fo )
   {
       //var a =  document.body.offsetWidth;
       var a =  document.body.clientWidth;
        
       if ( a < modal.ancho ){
           modal.ancho = a;
           modal.ancho = modal.ancho -14;
           
           fo.style.marginTop = window.scrollY+"px";
           
       }

        fo.style.width = modal.ancho+'px';
        
              
        
   },
    
    
    
    redim : function( dom )
   {
       //var a =  document.body.offsetWidth;
       var a =  document.body.clientWidth;
       var b = dom.offsetWidth;
       
       if ( a < b ){           
           //dom.style.width = (a - 14) + "px";
           dom.style.width = (a - 30) + "px";
           dom.style.marginTop = window.scrollY+"px";
       }
        
   },
        
    
        
    ventana_cerrar : function(indice)
   {
        do 
        {
            var ventanaID = "vnt"+indice;
            var capaOscura = document.getElementById(ventanaID);

            if ( !(capaOscura === null)){
                var padre = capaOscura.parentNode;
                padre.removeChild(capaOscura);    
            }
            var capaOscura = document.getElementById(ventanaID);
        }
        while ( !(capaOscura === null));

   },

   
    capaoscura : function  (indice)
    {        
        var ventanaID = "vnt"+indice;          
        modal.ventana_cerrar(ventanaID);               

        var body = document.getElementsByTagName("body").item(0);
        var fondo = document.createElement("div");
        fondo.id = ventanaID;
        fondo.className = "capaoscura";

        body.appendChild(fondo);   
        
        return fondo;        
    },
   
   
   
    capaoclara : function  (indice, conten)
    {
        
        var clara = document.createElement("div");
        clara.id = "mdl"+indice;        
        clara.className = "capaclara";
        /*clara.width = modal.ancho + "px";*/
        conten.appendChild(clara);
        
        return clara;        
    },
      
   
   
    ventana: {            
        
        mostrar: function( indice ) {                                  

            var ba = modal.capaoscura(indice);            
            var fo = modal.capaoclara(indice, ba);
            
            fo.innerHTML = ajax.public.html();    
            fo.style.width = modal.ancho+'px';
            
            var hei  = document.body.scrollHeight.toString();  
            ba.style.height = hei+"px";                 
            
            modal.dimensionar( fo );
            
            return ba;
        },
        
        
        html: function( indice, html ) {                                  

            var ba = modal.capaoscura(indice);            
            var fo = modal.capaoclara(indice, ba);
            
            fo.innerHTML = html;    
            fo.style.width = modal.ancho+'px';
            
            var hei  = document.body.scrollHeight.toString();  
            
            ba.style.height = hei+"px";                 
            
            return ba;
        },
        
        
        cerrar : function (dom){
            
            var coscura = dom;
            if ( !(coscura === null)){
                var padre = coscura.parentNode;
                padre.removeChild(coscura);    
            }
            
        },
    },

    
    
    form: {         
            
        
        new: function( objeto  ) {
            
            ajax.url = html.url.absolute() + objeto.carpeta  +'/'+objeto.tipo + '/htmf/form.html';                
            ajax.metodo = "GET";  
            modal.ancho = 600;
        
            objeto.venactiva = modal.ventana.mostrar( objeto.tipo + "add");                  
            var dom = objeto.venactiva.firstChild.id;     

            reflex.setTitulosObjeto(objeto)
            reflex.getTitulosin(dom, "h5");                

            var bo = document.getElementById( objeto.botones_form );                       
            
            boton.objeto = objeto.tipo;
            bo.innerHTML = boton.basicform.get_botton_add() ;
  

            objeto.funciones = [  
                 [
                     boton.accion.modal_add ,  
                     boton.accion.sublista
                ],
                 [
                     boton.accion.modal_cerrar ,  
                     boton.accion.sublista
                ]
            ];  


            boton.evento( objeto );      
            
            return objeto.venactiva;
            
        }, 
        
        
        
        plus: function( objeto  ) {
                
            ajax.url = html.url.absolute() + objeto.carpeta  +'/'+objeto.tipo + '/htmf/form.html';                
            ajax.metodo = "GET";  
            modal.ancho = 600;
        
            objeto.venactiva = modal.ventana.mostrar( objeto.tipo + "add");                  
            var dom = objeto.venactiva.firstChild.id;     


                reflex.setTitulosObjeto(objeto)
                reflex.getTitulosin(dom, "h5");
                

            var bo = document.getElementById( objeto.botones_form );                       
            
            boton.objeto = objeto.tipo;
            bo.innerHTML = boton.basicform.get_botton_add() ;
  
            objeto.funciones = [  
                boton.accion.modal_add,
                boton.accion.modal_cerrar
            ];  





                    if( typeof  objeto.acctionresul  ==  "function" ) {                          
                        objeto.acctionresul();                
                    }
                    else
                    {                
                        boton.evento( objeto );  
                    }





            
            var donid = document.getElementById( objeto.tipo + "_" + objeto.campoid );  
            donid.value = 0;
            donid.disabled=true;

            
            if(typeof objeto.form_ini === 'function') {
              objeto.form_ini();
            }                        

            
            return objeto.venactiva;
            
            
        }, 
        
        
        
        
        
        
        row: function( obj, lineaid  ) {
            
            obj.value = lineaid;
            
            reflex.ini( obj );    
            ajax.url = html.url.absolute() + obj.carpeta  +'/'+obj.tipo + '/htmf/form.html';                
        
            ajax.metodo = "GET";  
            modal.ancho = 600;        
        
            obj.venactiva = modal.ventana.mostrar( obj.tipo + "reg");                  
            var dom = obj.venactiva.firstChild.id;     

            reflex.setTitulosObjeto(obj);                        
            reflex.getTituloplu(dom, "h5");            
            reflex.ui.foreign_ocultar(obj);

            modal.form.pasarcab(obj);            
            modal.form.ocultarcab(obj);            
            

            var bo = document.getElementById( obj.botones_form );                     
            boton.objeto = obj.tipo;              
            bo.innerHTML = boton.basicform.get_botton_mrow() ;
            

            ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+lineaid;    
            ajax.metodo = "GET";

            form.name = "form_" + obj.tipo;
            form.json = ajax.private.json();   

            form.disabled(false);
            form.llenar();        		



            obj.funciones =   [
                
                [ 
                    boton.accion.modal_cerrar,
                    boton.accion.modal_edi
                ]
                ,                 
                [ 
                    boton.accion.modal_del,
                    boton.accion.modal_cerrar,
                    boton.accion.sublista,
                    boton.accion.cabecera 
                ]
                , 
                [ 
                    boton.accion.modal_cerrar,
                    boton.accion.sublista,
                    boton.accion.cabecera 
                ]
            
            ];
            boton.evento( obj );      
    
            return obj.venactiva;
            
        }, 
                
        
        
        
        add: function( objeto ) {          
            
            if ( objeto.form_validar())
            {
                ajax.metodo = "post";
                ajax.url = html.url.absolute()+"/api/"+ objeto.recurso;                         

                form.name = "form_"+objeto.tipo;                
                reflex.data = ajax.private.json( form.datos.getjson() );     
                

                if (ajax.state == 200){
                         
                    modal.form.retorno(objeto);               
                    modal.ventana.cerrar(objeto.venactiva);    
                    msg.ok.mostrar("registro agregado");

                }
                else{
                    
                    if (ajax.state == 502  ||  ajax.state == 500  ){                         
                        msg.error.mostrar(ajax.req.responseText);
                        objeto.break = true;
                    }
                    else{
                        msg.error.mostrar("error de acceso");           
                    }                                        
                }                
            }
            else
            {
                objeto.break = true;
            }
        },   
        
        
        update: function( objeto ) {          
            
            if ( objeto.form_validar())
            {
                ajax.metodo = "put";
                ajax.url = html.url.absolute()+"/api/"+ objeto.recurso+"/"+objeto.value;                      

                form.name = "form_"+objeto.tipo;                
                reflex.data = ajax.private.json( form.datos.getjson() );    
                
                if (ajax.state == 200){
                    
                    msg.ok.mostrar("registro actualizado");        
                    modal.ventana.cerrar(objeto.venactiva);      
                    
                }
                else{
                    if (ajax.state == 502 ||  ajax.state == 500  ){                         
                        
                        msg.error.mostrar(ajax.req.responseText);
                        objeto.break = true;
                        
                    }
                    else{
                        msg.error.mostrar("error de acceso");           
                    }                                        
                }                
            }
            else
            {
                objeto.break = true;
            }
        },   
        
        
        
        
        
        
        del: function( obj ) {          
            
            var id = document.getElementById( obj.tipo + '_'  + obj.campoid).value;

            ajax.metodo = "delete";            
            ajax.url = html.url.absolute()+"/api/" + reflex.recurso + "/"+id;           
            ajax.private.json( null );

            if (ajax.state == 200){
                msg.ok.mostrar("registro eliminado");      
            }
            else{
                msg.error.mostrar("error");           
            }                    

        },   
        
                        
        
        edi: function( obj ) {         
            
            ajax.url = html.url.absolute() + obj.carpeta  +'/'+obj.tipo + '/htmf/form.html';                
            ajax.metodo = "GET";  
            modal.ancho = 600;
        
            obj.venactiva = modal.ventana.mostrar( obj.tipo + "edit");                  
            var dom = obj.venactiva.firstChild.id;     

            reflex.setTitulosObjeto(obj);                        
            reflex.getTituloplu(dom, "h5");            
            //modal.form.pasarcab(objeto);      
            
            modal.form.pasarcab(obj);            
            modal.form.ocultarcab(obj);                        

            //obj.foreign = [ new Usuario(), new Rol() ] ;
            
            reflex.ui.foreign_more(obj);
            reflex.ui.foreign_plus(obj);
            reflex.ui.foreign_decrip(obj);       
            
            var caid = document.getElementById( obj.tipo + "_" + obj.campoid );
            caid.disabled=true;              
            
                        
            var bo = document.getElementById( obj.botones_form );                       
            
            boton.objeto = obj.tipo;
            bo.innerHTML = boton.basicform.get_botton_edit() ;
 
            ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+obj.value;    
            ajax.metodo = "GET";

            form.name = "form_" + obj.tipo;
            form.json = ajax.private.json();   
            form.llenar();   


            obj.funciones = [  
                 [
                     boton.accion.modal_update ,  
                     //boton.accion.modal_cerrar ,  
                     boton.accion.sublista
                ],
                 [
                     boton.accion.modal_cerrar ,  
                     boton.accion.sublista
                ]
            ];  
            boton.evento( obj );      
            
            return obj.venactiva;            
            
        },           
        
        
        retorno: function( objeto ) {
            if (typeof(objeto.parent) === "undefined") {                             
        //        objeto.sublista(objeto);
            }
            else{         
                
                
                var rid = objeto.parent.tipo + "_" + objeto.tipo + "_" + objeto.tipo;
                var ret = document.getElementById( rid );                       
                ret.value = reflex.getJSONdataID( objeto.tipo ) ;
                
                
                
            }
        },            
        


        pasarcab: function( obj ) {
            
            var camid = obj.tipo + "_" + obj.cabecera.tipo  + "_" + obj.cabecera.tipo;
            var cam = document.getElementById( camid);
            cam.value = obj.cabecera.value;                        
        },           


        ocultarcab: function( obj ) {            
            //f-line-rol
            var fline = document.getElementById( "f-line-" + obj.cabecera.tipo );
            fline.style.display = "none";            
        },           
                
        
        
        
    },



    accion: {        
    
        
        add: function( objeto ) {          
            
            if ( objeto.form_validar())
            {
                ajax.metodo = "post";
                ajax.url = html.url.absolute()+"/api/"+ objeto.recurso;                         

                form.name = "form_"+objeto.tipo;                
                reflex.data = ajax.private.json( form.datos.getjson() );   
                
                
                if (ajax.state == 200){

                    modal.ventana.cerrar(objeto.venactiva);    
                    msg.ok.mostrar("registro agregado");        
                    return reflex.getJSONdataID( objeto.campoid )   ;

                }
                else{                    
                    if (ajax.state == 500) {                                             
                        msg.error.mostrar(ajax.req.responseText);                        
                    }
                    else{
                        msg.error.mostrar("error de acceso");                                   
                    }                                        
                }                 
                
                
                
                
                
            }
        },   
        
              
            
    },

  
};



