

window.onload = function( dom ) {

    
    html.url.control();

    if (ajax.state == 200)
    {
        html.menu.mostrar();    
        
        var obj  = new Usuario();    
        obj.dom = 'arti_form',
                
        //objetoclase.vista.interfase( obj );        
        reflex.interfase.basica(obj);

    }
        
};

         




/*
window.onresize = function() {
    
    var nodeList = document.querySelectorAll('.fondo_oscuro');
    for (var i = 0; i < nodeList.length; ++i) {
        document.getElementById(nodeList[i].id).style.height = document.body.scrollHeight;        
    }

};

*/






