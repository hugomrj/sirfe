

window.onload = function() {


    localStorage.clear();    
    document.body.style.height = "100%" ;  
    
    
    var btn_login = document.getElementById('btn_login');
    btn_login.addEventListener('click',
        function(event) {           
            
           ajax.url = html.url.absolute()+"/api/usuarios/login";           
           ajax.metodo = "POST";           
           
           form.name = "form_login";

          ajax.private.json( form.datos.getjson() );   
        

            if (ajax.state == 200){
               
                html.menu.ini();
                window.location = html.url.absolute()+"/sistema/inicio/main.html";  

            }
            else{
                msg.error.mostrar("error de acceso");           
            }           
           
           
        },
        false
    );        
        



    
    var acceso_publico = document.getElementById('acceso_publico');
    acceso_publico.addEventListener('click',
        function(event) {           
            
           ajax.url = html.url.absolute()+"/api/usuarios/login";           
           ajax.metodo = "POST";           
           
           form.name = "form_login";

          ajax.private.json( "{\"cuenta\":\"publico\",\"clave\":\"publico\"}" );   

            if (ajax.state == 200){
               
                html.menu.ini();
                window.location = html.url.absolute()+"/sistema/inicio/main.html";                 
            }
            else{
                msg.error.mostrar("error de acceso");           
            }           
           
           
        },
        false
    );        
        




           
           
/*
var xhr = new XMLHttpRequest();
xhr.open("GET", "https://localhost/Municipalidad/api/usuarios/1", true);
xhr.onreadystatechange = function (e) {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {      
      alert(xhr.responseText);
    } else {
      console.error(xhr.statusText);
    }
  }
};
xhr.onerror = function (e) {  
  alert("error");
};         
xhr.send(null);          
*/




};




/*
window.onresize = function() {
    
    var nodeList = document.querySelectorAll('.fondo_oscuro');
    for (var i = 0; i < nodeList.length; ++i) {
        document.getElementById(nodeList[i].id).style.height = document.body.scrollHeight;        
    }

};

*/




