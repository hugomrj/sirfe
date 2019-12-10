 
 
var form =  
{  
    name: "",
    json:  "",
    campos: [],
    
        
    llenar: function(  ) {
        
        var oJson = JSON.parse(form.json) ; 
        var campos = document.getElementById( form.name ).getElementsByTagName("input");    
        
        
        for(var i=0; i< campos.length; i++) {            
            var c = campos[i];                      

            if (typeof(c.dataset.foreign) === "undefined") {

                if (c.type == "text"  ||  c.type == "hidden"  )
                {                
                    if (c.className == "num")
                    {
                        c.value  = oJson[c.name]; 
                    }
                    else
                    {  
                        //alert(oJson[campos[i].name] );
                        c.value  = oJson[c.name]; 

                        if (c.value === 'undefined') {
                            c.value  = ""; 
                        }  
                    }                
                }
                else
                {
                    if (c.type == "date"){                        
                        c.value  = jsonToDate( oJson[c.name] );   
                    }
                    else{
                        if (c.type == "password"){
                              c.value  = oJson[c.name];                     
                        }                        
                    }
                    
                }            
            }
            else
            {                
                var objev = eval( "new "+c.dataset.foreign+"()");          
    
                
                if (objev.objjson == undefined){
                    var f = c.dataset.foreign;
                }
                else{
                    var f = objev.objjson;
                }
                
                f = f.toLowerCase();
                
                try {

                        c.value = oJson[f][objev.campoid] ;     
                        vardes = oJson[f][objev.json_descrip] ;

                        var foo = document.getElementById( objev.form_descrip ).nodeName;
                        
                        switch (foo) {
                          case "INPUT":
                            document.getElementById( objev.form_descrip ).value =  vardes
                            break;
                          case "OUTPUT":                             
                            document.getElementById( objev.form_descrip ).innerHTML =  vardes
                            break;
                          default:
                            console.log('default switch');
                        }                        
                
                    //console.log(document.getElementById( objev.form_descrip ).nodeName)           
                    //document.getElementById( objev.form_descrip ).innerHTML                                 =  vardes


                }
                catch(error) {
                        //console.error(error);
                        c.value = "0" ;              
                      //  document.getElementById( objev.form_descrip ).innerHTML = "";

                }                                                  
            }            
        }
        this.llenar_textarea(form.json);
        
        
    },
    
    
    
    
    
    
    
    llenar_cbx: function( obj ) {
        
        var oJson = JSON.parse(form.json) ;      

        var campos = document.getElementById( form.name ).getElementsByTagName("select");    
        
        for(var i=0; i< campos.length; i++) {   
            
            var c = campos[i];              
            
            var opt = document.createElement('option');            
            
            opt.value = oJson[c.name][obj.combobox[c.name]['value']];
            
            opt.innerHTML = oJson[c.name][obj.combobox[c.name]['inner']];            
            
            document.getElementById(   campos[i].id ).appendChild(opt);           
            
        }
    },
        
    
    
    
    llenar_textarea: function( json  ) {
        
        var oJson = JSON.parse(json) ;      

        var campos = document.getElementById( form.name ).getElementsByTagName("textarea");    
        
        for(var i=0; i< campos.length; i++) {               
            var c = campos[i];         
                //c.value = "cargado;"
                 c.value  = oJson[c.name]; 
        }
    },
        
    
        
    
    
    
    
    
    relaciondescrip: function( id, obj ) {
        
        reflex.ini(obj);
        ajax.url = html.url.absolute()+'/api/' + obj.recurso + '/'+id;    
        
        ajax.metodo = "GET";

        form.json = ajax.private.json();  
        var camdes =  document.getElementById(obj.form_descrip);   
        
        
        if (ajax.state == 200){            
            var oJson = JSON.parse(form.json) ;    

            if (Array.isArray(obj.json_descrip))
            {
                camdes.innerHTML = "";
                
                obj.json_descrip.forEach(function(element) {
                  //console.log(element);
                  camdes.innerHTML = camdes.innerHTML + oJson[element] + " ";                   
                });                                
                
            }
            else
            {
                camdes.innerHTML = oJson[obj.json_descrip];                   
            }
                        
        }
        else{
            camdes.innerHTML = "";                                
        }


    },
        
    
    
    
    
    
    
    
    
    disabled: function( bool ) {
        
        var fcampos = document.getElementById( form.name ).getElementsByTagName("input");    
        
        var esIgual = false;    
        for(var i=0; i< fcampos.length; i++) 
        {            
            form.campos.forEach(function (elemento, indice, array) {    
                if (elemento === fcampos[i].id ){                                        
                    
                    esIgual = true;
                    
                }
            });                
            
            if (!( esIgual )){
                document.getElementById(fcampos[i].id).disabled =  !bool;
            }else{
                document.getElementById(fcampos[i].id).disabled = bool;                
            }
            esIgual = false;            
        }
        



        var fcampos = document.getElementById( form.name ).getElementsByTagName("select");            
        var esIgual = false;
    
        for(var i=0; i< fcampos.length; i++) 
        {            
            form.campos.forEach(function (elemento, indice, array) {    
                if (elemento === fcampos[i].id ){                                        
                    esIgual = true;
                }
            });                
            
            if (!( esIgual )){
                document.getElementById(fcampos[i].id).disabled =  !bool;
            }else{
                document.getElementById(fcampos[i].id).disabled = bool;                
            }
            esIgual = false;            
        }
        

        

        var fcampos = document.getElementById( form.name ).getElementsByTagName("textarea");            
        var esIgual = false;
    
        for(var i=0; i< fcampos.length; i++) 
        {            
            form.campos.forEach(function (elemento, indice, array) {    
                if (elemento === fcampos[i].id ){                                        
                    esIgual = true;
                }
            });                
            
            if (!( esIgual )){
                document.getElementById(fcampos[i].id).disabled =  !bool;
            }else{
                document.getElementById(fcampos[i].id).disabled = bool;                
            }
            esIgual = false;            
        }
        
        
        form.campos = [];
        
    },
    
    
       
    

    
    
    ocultar_foreign: function(  ) {
                
        var iconclass = document.getElementById( form.name ).getElementsByClassName("icono");        
    
        for(var i=0; i< iconclass.length; i++) {                          
            iconclass[i].style.display = "none";   
            iconclass[i].parentNode.parentNode.style.width = "10px";   
            //iconclass[i].parentNode.style.width = "10px";   
        }            
        
    },
    
    
    
    
    
    
    
    mostrar_foreign: function(  ) {
                
        var iconclass = document.getElementById( form.name ).getElementsByClassName("icono");        
        for(var i=0; i< iconclass.length; i++) {              
            iconclass[i].style.display = "flex";   
            iconclass[i].parentNode.parentNode.style.width = "auto";   
            //iconclass[i].parentNode.style.width = "auto";   
        }            
    },    
    
    
    
    
    
    datos: {   
    
        getjson: function( ) {    

            var campos = document.getElementById( form.name ).getElementsByTagName("input");    
            var str = "";        

            for (var i=0; i< campos.length; i++) {
                
                var ele = campos[i];    
                
                // si existe nojson pasar al siguiente                
                
                if (ele.type == "hidden")
                {                                                
                    if (typeof ele.dataset.pertenece === "undefined" ){     
                        str =  str  + "";
                    }  
                    else
                    {
                        if (str  != ""){
                            str =  str  + ",";
                        }                                                
                        //console.log(form.datos.elemetiq(campos[i]));
                        str =   str + form.datos.elemetiq(campos[i]) ;                           
                    }
                }
                
                else
                {
                    
                    
                    if (typeof ele.dataset.vista === "undefined" ){     
                        
                        if (str  != ""){
                            str =  str  + ",";
                        }                                                
                        //console.log(form.datos.elemetiq(campos[i]));
                        str =   str + form.datos.elemetiq(campos[i]) ;                    

                    }  
                
                }
                
            }
            
            
            var str2 = "";  
            var combos = document.getElementById( form.name ).getElementsByTagName("select");   
            for (var y=0; y< combos.length; y++) {                

                    if (str2  != ""){
                        str2 =  str2  + ",";
                    }                                                            
                    str2 =  str2 + form.datos.elemetcombo(combos[y]) ;                      
            }

            if (str2  != ""){
                if (str  != ""){
                    str =  str  + "," + str2;
                }
                else{
                    str =  str2;
                }
            }


            // textarea
            var str3 = "";  
            var areas = document.getElementById( form.name ).getElementsByTagName("textarea");   
            for (var x=0; x< areas.length; x++) {                

                    if (str3  != ""){
                        str3 =  str3  + ",";
                    }                                                            
                    str3 =  str3 + form.datos.elemenTextArea(areas[x]);                      
            }

            if (str3  != ""){
                if (str  != ""){
                    str =  str  + "," + str3;
                }
                else{
                    str =  str3;
                }
            }


            return "{" +str+ "}"  ;            
        },   
        
        
        elemen: function( ele ) {    
            
            
            var str = "";        
            
            str =  str  + "\"" +ele.getAttribute('name')+ "\"" ;
            str =   str + ":";            
            
            if (ele.type == "text"  ||  ele.type == "hidden"  )
            {  
                if (ele.className == "num")
                {
                    //str = str + ele.value  ;    
                    str = str + NumQP(ele.value)  ;    
                }
                else
                {
                    str = str + "\"" +ele.value+ "\"" ;
                }                
            }
            else
            {
                if (ele.type == "password"){
                    str =   str + "\"" + ele.value+ "\"" ;
                }
                if (ele.type == "date"){
                
                
                    if (ele.value !=  "")
                    {  
                        str =   str + "\"" + ele.value+ "\"" ;
                    }
                    else
                    {
                        str =   str + " null " ;
                    }

                    //str =   str + "\"" + ele.value+ "\"" ;
                }                                
            }
            
            return str ;            
        },
            
            
        
        elemenTextArea: function( ele ) {    
            
            var str = "";        
            
            str =  str  + "\"" +ele.getAttribute('name')+ "\"" ;
            str =   str + ":";            
            
            str = str + "\"" +ele.value+ "\"" ;
            
            return str ;            
        },
                        
            
            
            
        elemetiq: function( ele ) {                
            var str = "";              
            
            if (typeof ele.dataset.foreign === "undefined" ){             
                //str =   str + form.datos.elemen(ele).toString().toLocaleLowerCase();
                str =   str + form.datos.elemen(ele).toString();

            }
            else
            {
                

                var e = ele.dataset.foreign.toString().toLocaleLowerCase();
                
                if (e == ele.getAttribute('name') ){
                    
                    //str =  str  + "\"" +e+ "\"" ;                
                    str =  str  + "\"" +ele.getAttribute('name')+ "\"" ;                
                    
                }
                else{                    
                    str =  str  + "\"" +e+ "\"" ;                
                    //str =  str  + "\"" +ele.getAttribute('name')+ "\"" ;                
                }
                                  
                
                
                //str =  str  + "\"" +ele.dataset.foreign+ "\"" ;                
                str =   str + ":";                  
                str =  str + "{ "+  form.datos.elemen(ele) + " }" ;
            }           
            return str ;            
        },
        
        
        elemetcombo: function( ele ) {                
            var str = "";              
            
            if (typeof ele.dataset.foreign === "undefined" ){         
                str = str + " \"" +ele.name+ "\" " ;            
            }
            
            else{                
                var e = ele.dataset.foreign.toString().toLocaleLowerCase();
                str = str + " \"" + e + "\"" ;            
            } 
            
            str =   str + ":";            
            str =  str + "{ ";  
            str =  str + " \"" +ele.name+ "\": " ;
                if (ele.className == "num")
                {
                    str = str + NumQP(ele.value)  ;    
                }
                else
                {
                    str = str + "\"" +ele.value+ "\"" ;
                }             
            str =  str + "} ";
            return str ;            
        }, 
        
    },         
        
        
        
    get: {   
    
        foreign: function ( f, obj ) {                   
        
            var campos = document.getElementById( f ).getElementsByTagName("input");    
            var idinput = 0;    

            for(var i=0; i< campos.length; i++) {                

                var f = campos[i].dataset.foreign;
                
                if ( f != undefined ) {                
                    if (obj.tipo == f.toString().toLocaleLowerCase()){    
                        idinput = campos[i].id;                
                    }                    
                }                                
            }            
            return idinput;        
        },

    },
        
    
};
 