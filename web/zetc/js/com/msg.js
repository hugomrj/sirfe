
var msg = 
{    
    texto: "", 
    timeOutPeriod: 4000,
    divContenedor: "idalertas",
    
    generarUID: function() {        
        return "msg-" + Math.floor(Math.random() * 999999);
    },
    
  
    error : {        
        
        mostrar: function(mensaje) {
            
            var UID = msg.generarUID();
            msg.crear(UID, mensaje ).setAttribute("class", "alerta alerta-error "); ;        
            msg.eliminar(UID);
            return;            
        }
    },
      
    ok : {        
        
        mostrar: function(mensaje) {
            
            var UID = msg.generarUID();
            msg.crear(UID, mensaje ).setAttribute("class", "alerta alerta-ok "); ;        
            msg.eliminar(UID);
            return;            
        }
    },
    
    
    eliminar:  function(id) {
        
        var strCmd = "document.getElementById('"+this.divContenedor+"').removeChild(document.getElementById('"+id+"'));";
                
        setTimeout(strCmd, this.timeOutPeriod);
        return;        
    },
                
        
    crear:  function(uid, mensaje) {

        this.conten();
        var midiv = document.createElement("div");
        midiv.setAttribute("id", uid);        
        midiv.innerHTML = mensaje ;
        document.getElementById(this.divContenedor).appendChild(midiv);

        return midiv;        
    },
    
    
    conten:  function() {
        
        if ( (document.getElementById(this.divContenedor) === null)){

            var div = document.createElement("div");
            div.setAttribute("id", this.divContenedor);
            //div.innerHTML = "<div></div>";
            document.body.appendChild(div);
        }               
    }
    
    
};


