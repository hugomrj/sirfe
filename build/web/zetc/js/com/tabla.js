
 
var tabla = 
{  
    json:  "",
    campos: ['uno', 'dos'],
    etiquetas: ['uno', 'dos', 'tres', 'cuatro','cinco'],
    html: "",
    linea:"",
    tbody_id:"",
    id:"",
    oculto: [],
    
    
    ini: function(obj) {        
        
        tabla.id = obj.tipo+ "-tabla";
        tabla.linea = obj.campoid;
        tabla.tbody_id = obj.tipo+"-tb";
        tabla.campos = obj.tablacampos;
        
        
        tabla.etiquetas = obj.etiquetas;

    },
    
    
    
    
    get_html: function(  strjson  ) {




        if (strjson === undefined) {
           var oJson = JSON.parse(tabla.json) ;        
           
        }
        else {
            var oJson = JSON.parse( strjson ) ;        
            
        }
        

        tabla.html = "";         
        
        for(x=0; x < oJson.length; x++) {     
    
            tabla.html += "<tr data-linea_id=\""+
                    oJson[x][tabla.linea]
                    +"\">";                  


            tabla.campos.forEach(function (elemento, indice, array) {                                

                //tabla.html += "<td>";     


                try { 
                  tabla.html += "<td data-title=\""+tabla.etiquetas[indice]+"\">";                    
                }
                catch (e) {
                    tabla.html += "<td>";     
                }                

                
                //tabla.html += "<td data-title=\""+tabla.etiquetas[indice]+"\">";                    
                var jsonprop;


                if (Array.isArray( elemento )){
                    jsonprop = tabla.campos_array(elemento, oJson[x]);
                }
                else
                {
                    
                    try {
                        
                        eval("jsonprop = oJson[x]."+ elemento + ";");    
                        
                    }
                    catch(error) {                        
                        jsonprop = "";        
                    }
                    
                    
                }

                tabla.html += jsonprop;     
                
                tabla.html += "</td>";

            });    
         
            tabla.html += "</tr>";    
        }


        return tabla.html;
    },
    
    
    campos_array: function(miArray, json) {
        
        var ret = "";
        
        for (var i = 0; i < miArray.length; i+=1) {          
          eval("ret = ret +' '+json."+ miArray[i] + ";");              
        }        
        
        return ret;

    },    
    
    
    
    gene: function() {

        document.getElementById( tabla.tbody_id ).innerHTML = tabla.get_html();  

    },

    
    
    gene_strjson: function(strjson) {
        
        document.getElementById( tabla.tbody_id ).innerHTML = tabla.get_html( strjson );  

    },
    
    

    
    limpiar: function() {     
        
        if (document.getElementById( tabla.tbody_id ))
        {
            tabla.json = '[]';
            tabla.gene();    
        }        
    },
        
        
    lista_registro: function( vartype, fn  ) {
     
            
        var tmptable = document.getElementById( tabla.id ).getElementsByTagName('tbody')[0];
        var rows = tmptable.getElementsByTagName('tr');

        for (var i=0 ; i < rows.length; i++)
        {
            rows[i].onclick = function()
            {  
                
                var linea_id = this.dataset.linea_id;                    
                var expre = typeof vartype;
                
    
                switch(expre) {
                    
                    case 'object':                     
                        //objetoclase.form_id(vartype, linea_id); 
                        fn(vartype, linea_id ); 
                        break;                        
                        
                    /*                    
                    case 'string':              
                        objetoclase.form_id(vartype, linea_id);     
                        break;
                    */ 
                    case 'function':                                  
                        vartype( linea_id );                        
                        break;
                        
                    default:
                        //code block
                }    
                
            };       
        }        
        
    },
    
    
    setObjeto: function( objeto) {
        
        tabla.id = objeto.tipo  + "-tabla";
        tabla.tbody_id =  objeto.tipo +"-tb";       
        tabla.campos = objeto.tablacampos;           
                       
    },
    
    set: {          
        tbodyid: function( objeto  ) {
            tabla.tbody_id = objeto.tipo+ "-tb";
        },
        
        tablaid: function( objeto  ) {
            tabla.id = objeto.tipo+ "-tabla";
        },
    },

    
    refresh: function( obj, page, buscar, fn  ) {

        ajax.url = reflex.getApi( obj, page, buscar );

        ajax.metodo = "GET";        
        tabla.json = ajax.private.json();   
        
        tabla.campos = obj.tablacampos;                     
        tabla.set.tbodyid(obj);            
                
        tabla.gene();       
        tabla.formato(obj);
        tabla.lista_registro(obj, fn);        
    
// ocultar        

            if (!(typeof  obj.oculto == "undefined")){
                tabla.oculto = obj.oculto ;
                tabla.ocultar();                        
            }
            
        
        document.getElementById( obj.tipo + '_paginacion' ).innerHTML = paginacion.gene();      
        paginacion.move(obj, buscar, fn );          

    }, 


    ocultar: function() {

        var tableHe = document.getElementById( tabla.id ).getElementsByTagName('thead')[0];
        var rows = tableHe.getElementsByTagName('tr');
        
        tabla.oculto.forEach(function (ele, indice, array) {                        
            for (var i=0 ; i < rows.length; i++)
            {
                cell = tableHe.rows[i].cells[ele] ;                                                  
                cell.style.display = "none";
            }                           
        });    
        
        
        
        var tableBo = document.getElementById( tabla.id ).getElementsByTagName('tbody')[0];
        var rows = tableBo.getElementsByTagName('tr');
        
        tabla.oculto.forEach(function (ele, indice, array) {                        
            for (var i=0 ; i < rows.length; i++)
            {
                cell = tableBo.rows[i].cells[ele] ;                                                  
                cell.style.display = "none";
            }                           
        });    
        


        


/*
            for (var i=0 ; i < rows.length; i++)
            {
                cell = tmptable.rows[i].cells[0] ;                                                  
                cell.style.display = "none";
            }        
*/
        
    },

    formato: function( obj ) {
        
        try { 
                    
            var table = document.getElementById( tabla.id ).getElementsByTagName('tbody')[0];
            var rows = table.getElementsByTagName('tr');

            for (var i=0 ; i < rows.length; i++)
            {

                for (var j=0 ; j < obj.tablaformat.length; j++)
                {

                    var type= obj.tablaformat[j];
                    switch(type) {

                        case 'C':                                                 
                            break;                        

                        case 'N':                                  
                            
                            cell = table.rows[i].cells[j] ;                                  
                            cell.innerHTML = fmtNum(cell.innerHTML);                        
                            cell.style = "text-align: right";
                            break;

                        case 'D':                                                 
                            
                            cell = table.rows[i].cells[j] ;   
                            //cell.innerHTML = stod (cell.innerHTML) ;                 
                            cell.innerHTML = fDMA (cell.innerHTML) ;                 
                            
                            cell.style = "text-align: right";
                            break;                        
                            
                        case 'R':                                                 
                            
                            cell = table.rows[i].cells[j] ;                               
                            cell.style = "text-align: right";
                            break;                        
                                                        
                            
                        default:
                            //code block
                    }      
                }
            }
                    
        }
        catch (e) {  
        //objetoclase.funciones( obj, fn );                      
        }        
        
        
        

            
    },

    
    
};
 