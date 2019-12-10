

window.onload = function( dom ) {
    
    html.url.control();
    
    if (ajax.state == 200)
    {
        
        html.menu.mostrar();    
        var obj = new ConsejoSalud();    
        obj.dom = 'arti_form';        
        reflex.interfase.basica(obj);
    }
    
};
   
   
   
   
   
window.onresize = function() {
    document.body.style.minheight  = "100%" ;  
    var nodeList = document.querySelectorAll('.capaoscura');
    for (var i = 0; i < nodeList.length; ++i) {
        document.getElementById(nodeList[i].id).style.height = document.body.scrollHeight.toString() + "px";                
    }
};


   
