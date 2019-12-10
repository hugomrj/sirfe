

function Verificador(){
    
   this.tipo = "usuario";   
   this.recurso = "usuarios";   
   this.value = 0;
   this.form_descrip = "usuario_descripcion";
   this.json_descrip = "cuenta";
   
   this.objjson = "verificador";
       
   this.dom="";   
   this.carpeta=  "/sistema";   
   
   this.titulosin = "Usuario"
   this.tituloplu = "Usuarios"
       
   //this.tablalinea=  'usuario';
   this.campoid=  'usuario';
   this.tablacampos =   ['usuario', 'cuenta'];      
   this.etiquetas =   ['Usuario', 'Cuenta'];   


   this.botones_form = "usuario-acciones";   

   this.parent = null;
   
};



