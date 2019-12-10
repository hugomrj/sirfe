
var html = 
{      
    sitio:  "",
    
    menu: {  
        
        user: 0,
        div: "menubar",

        ini: function(  ) {

            ajax.url = html.url.absolute()+"/api/selectores/menu";           
            ajax.metodo = "GET";     
            html.menu.user = ajax.private.json(null);
            localStorage.setItem('menu', ajax.req.getResponseHeader("menu") );     
        }  ,                                

        mostrar: function(  ) {            
            document.getElementById( html.menu.div ).innerHTML 
                    =  localStorage.getItem('menu');                    
            //redirect(ajax.state);            
            //alert(  window.location.pathname );            
        }  ,                                
    },
    
    url:{
        
        control: function() {
                        
            var comp = window.location.pathname;            
            var carp = html.url.absolute();
            var path =   comp.replace(carp, "");         
            
            ajax.url = html.url.absolute()+"/api/usuarios/controlurl"; 
            ajax.metodo = "GET";     
            
            ajax.req =  new XMLHttpRequest();
            ajax.json = null ;                      
            ajax.req.open( ajax.metodo.toUpperCase(),   ajax.url,  false );              
            ajax.req.setRequestHeader('Content-Type', 'application/json');   
            
            ajax.headers.setRequest();
            ajax.req.setRequestHeader("path", path );
            
            ajax.req.send( ajax.json );                        
            ajax.headers.getResponse();
            
            ajax.state = ajax.req.status;              
            
            html.url.redirect(ajax.state)
            
        } ,     
        
        
        absolute: function() 
        {
            var pathname = window.location.pathname;
            pathname = pathname.substring(1,pathname.length);
            pathname = pathname.substring(0,pathname.indexOf("/"));
            return "/"+pathname ;            
        },        
                
                
        redirect: function(val) 
        {
            switch(val) {
                case 200:
                    break;
                case 500:
                    window.location = html.url.absolute();                 
                    break;            
                default:
                    window.location = html.url.absolute();                 
            }

        },                  
                
        
    },
    
    

/*
    util: {  
        
        pathAbs: function() 
        {
            var pathname = window.location.pathname;
            pathname = pathname.substring(1,pathname.length);
            pathname = pathname.substring(0,pathname.indexOf("/"));
            return "/"+pathname ;            
        },        
        

        redirect: function(val) 
        {
            switch(val) {
                case 200:
                    break;
                case 500:
                    window.location = pathAbs();                 
                    break;            
                default:
                    window.location = pathAbs();                 
            }

        },        

        

    },
*/




};





