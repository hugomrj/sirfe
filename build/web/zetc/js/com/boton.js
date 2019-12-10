 
var boton = 
{  
    class_c: "boton",
    class_a: "botonA",
    objeto: "",
    blabels: [],   
    funciones: null,
    
    ini: function( obj ) {
        boton.objeto = obj.tipo;        
        boton.class_a = "botonA";
    }  ,                                
    
    
    get_botton_html: function( etiqueta, indice ) {                                  
        
            strhtml = "";            
            strhtml += " <div class=\""+boton.class_c+"\"> ";
            strhtml += "    <a id=\"btn_"+boton.objeto+"_"+etiqueta.toString().toLowerCase() +"\"" ;
            strhtml += "        data-id=\""+indice +"\" ";
            strhtml += "        class=\""+boton.class_a +"\" ";
            strhtml += "        href=\"javascript:void(0);\">";    
            strhtml += "     "+ etiqueta;    
            strhtml += "    </a>";        
            strhtml += "   </div>";        
            
            return strhtml;
    },
    
    
    get_botton_base: function(  ) {                  
        strhtml = "";            
        boton.blabels.forEach(function (elemento, indice, array) {                                                        
            strhtml += boton.get_botton_html(elemento, indice);
        });                        
        strhtml =  " <div class=\"botones\">" + strhtml + "</div>";
        return strhtml;
    },    

    
    basicform: {    
        
        b_add: ['Guardar','Cancelar'],        
        b_reg: ['Nuevo','Modificar', 'Eliminar', 'Lista'],        
        b_edit: ['Editar','Cancelar'],        
        b_del: ['Eliminar','Cancelar'],              
        b_new: ['Nuevo'],      
        b_att: ['Agregar'],      
        b_cancel: ['Cancel'],     
        b_mrow: ['Modificar','Eliminar','Salir'],     
        b_newback: ['Nuevo','Atras'],     
        b_reg3: ['Nuevo','Eliminar', 'Lista'],  
        b_okcan: ['Aceptar','Cancelar'],        
        b_reg2nl: ['Nuevo', 'Lista'],  
        
        
        get_botton: function( eti ) {                  
            boton.blabels = eval("boton.basicform."+eti);
            return boton.get_botton_base();
        },                
        
        get_botton_add: function(  ) {                        
            boton.blabels = boton.basicform.b_add;
            return boton.get_botton_base();
        },        
        
        get_botton_reg: function(  ) {                        
            boton.blabels = boton.basicform.b_reg;
            return boton.get_botton_base();
        },     

        get_botton_edit: function(  ) {                        
            boton.blabels = boton.basicform.b_edit;
            return boton.get_botton_base();
        },            
        
        get_botton_del: function(  ) {                        
            boton.blabels = boton.basicform.b_del;
            return boton.get_botton_base();
        },     
        get_botton_new: function(  ) {                        
            boton.blabels = boton.basicform.b_new;
            return boton.get_botton_base();
        },          
        get_botton_att: function(  ) {                        
            boton.blabels = boton.basicform.b_att;
            return boton.get_botton_base();
        },          

        get_botton_cancel: function(  ) {                        
            boton.blabels = boton.basicform.b_cancel;
            return boton.get_botton_base();
        },                  
        get_botton_mrow: function(  ) {                        
            boton.blabels = boton.basicform.b_mrow;
            return boton.get_botton_base();
        },             
        
        get_botton_newback: function(  ) {                        
            boton.blabels = boton.basicform.b_newback;
            return boton.get_botton_base();
        },                     

        get_botton_okcan: function(  ) {                        
            boton.blabels = boton.basicform.b_okcan;
            return boton.get_botton_base();
        },           

        get_botton_reg2nl: function(  ) {                        
            boton.blabels = boton.basicform.b_reg2nl;
            return boton.get_botton_base();
        },            
        

    },
   
        
    evento: function( obj ) {
        
        /*
        alert( typeof  obj.funciones[0]);
        alert( obj.funciones[0]);
        */
 
 
 
        for (var i=0; i < boton.blabels.length; i++)
        {  
            
            var insta = document.getElementById(
                    ('btn_'+boton.objeto+'_'+boton.blabels[i]).toString().toLowerCase());            

            insta.onclick = function()
            {                                  
               
                var valor_id = this.dataset.id;      
                var fn = obj.funciones[valor_id];
                
                reflex.funciones( obj, fn );    
                
                /*
                try { 
                    reflex.funciones( obj, fn );    
                }
                catch (e) {  
                    objetoclase.funciones( obj, fn );           
                }                
                */
     
            };       
        } 

    },
               
               
        
        
    accion: {
                
        modal_cerrar: function( obj ) {
            obj.break = false;
            modal.ventana.cerrar(obj.venactiva);                
        },
        
        modal_add: function( obj ) {
            modal.form.add(obj);     
        },        
        
        modal_del: function( obj ) {
           modal.form.del(obj);     
        },        

        modal_edi: function( obj ) {
            modal.form.edi(obj);     
        },             
        
        modal_update: function( obj ) {
            modal.form.update(obj);     
        },                     
        
        sublista: function( obj ) {            
            obj.sublista(obj);
        },                        
                        
        cabecera: function( obj ) {                     
            //objetoclase.button_reg( obj.cabecera );
            reflex.acciones.button_reg(obj.cabecera);
            
        },             
        
        
    } ,       
        
        
    
    
    
};
 
 
