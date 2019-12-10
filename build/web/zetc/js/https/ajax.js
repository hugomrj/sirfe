

var ajax = 
{  
    
    req :  new XMLHttpRequest(),    
    json: "",
    url: "",
    state: 0,    
    dataType: 'json',
    metodo: '',
    
    local:{
         token : ""
    },
    
    headers: {    
        
        setRequest: function( ) {                                  
                ajax.req.setRequestHeader("token", localStorage.getItem('token'));
        },
        
        getResponse: function( ) {    
            
            ajax.local.token = ajax.req.getResponseHeader("token") ;            
            localStorage.setItem('token', ajax.local.token);     
            
            sessionStorage.setItem('total_registros',  ajax.req.getResponseHeader("total_registros"));
                        
            
            /*
            if ( ajax.req.getResponseHeader("succ") == "null"){
                localStorage.setItem('succ',  "--");
            }
            else{
                localStorage.setItem('succ',  ajax.req.getResponseHeader("succ"));
            }
            */
           
           
            
        }
    },


  
    private:{    

        json: function( data ) {                                           
            
            ajax.req =  new XMLHttpRequest();
            ajax.json = data ;                      
            ajax.req.open( ajax.metodo.toUpperCase(),   ajax.url,  false );              
            ajax.req.setRequestHeader('Content-Type', 'application/json');   
            
            ajax.headers.setRequest();
            ajax.req.send( ajax.json );                        
            ajax.headers.getResponse();
            
            ajax.state = ajax.req.status;      
            return  ajax.req.responseText;
        },
                
        
        ws: function() {


            ajax.req =  new XMLHttpRequest(); 
            ajax.req.open( ajax.metodo.toUpperCase(), ajax.url, false);              
            ajax.req.setRequestHeader('Content-Type', 'text/html;charset=UTF-8');
        
            ajax.headers.setRequest();              
            ajax.req.send();                    
            ajax.headers.getResponse();
        
            ajax.state = ajax.req.status;                    
            return ajax.req.responseText;      
            
            
        },


        
        
        jasper: function() {         
            
            ajax.req =  new XMLHttpRequest();             
            var url =  html.url.absolute()+"/TokenReport";                        
            ajax.req.open( "POST", url, false);              
            ajax.req.setRequestHeader('Content-Type', 'application/html;charset=UTF-8');
        
            ajax.headers.setRequest();              
            ajax.req.send();                    
            ajax.headers.getResponse();
        
            ajax.state = ajax.req.status;          
            if (ajax.state == 202){                
                window.open( ajax.url , '_blank');
            }
            //return ajax.req.responseText;               
        
        },                
                
        
        
        
    },
    
    public:{    
        
        html: function() {

            ajax.req =  new XMLHttpRequest(); 
            ajax.req.open( ajax.metodo.toUpperCase(), ajax.url, false);              
            ajax.req.setRequestHeader('Content-Type', 'text/html;charset=UTF-8');
        
            ajax.req.send();  
            
            ajax.state = ajax.req.status;                    
            return ajax.req.responseText;
        },
        
        
        json: function() {

            ajax.req =  new XMLHttpRequest(); 
            ajax.req.open(ajax.metodo.toUpperCase(), ajax.url, false);              
            ajax.req.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
                  
            ajax.req.send();                            
        
            ajax.state = ajax.req.status;                    
            return ajax.req.responseText;
        },        
        
    },
    
    
}






    function downloadFile(url, success) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        //xhr.open('POST', url, true);
        
        xhr.contentType = 'application/pdf',
                
        xhr.responseType = "blob";
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                if (success) success(xhr.response);
            }
        };
        xhr.send(null);
    }           
        

                