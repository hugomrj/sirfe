


function dateToString ( fecha ){
    
    if (fecha.toString().trim().length == 0 ){        
        return "0"
    }
    else
    {
        var fe = new Date( fecha );    
        fe.setMinutes(fe.getMinutes() + fe.getTimezoneOffset())


        var strfe = fe.toISOString().slice(0,10).toString();
        var re = /-/g;
        strfe = strfe.replace(re, "");

        return strfe;
    }
    //return "00000000";
}




function fmtNum(num) {


    num = num.toString().trim();
    //num = num.replace(/\./g,'');
    num =  num.replace(new RegExp(",","g") ,"");   

    if(!isNaN(num))
    {        
        auxNum = num;        
        // aca hay que controlar si es un numero negativo
        if (auxNum < 0)
        {
            num = num * -1;
        }
        num = Number(num);
        num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1,');
        num = num.split('').reverse().join('').replace(/^[\,]/,'');
        
        if (auxNum < 0)
        {
            num = "-"+num;
        }
        
        return num;
    }
    else{ 

        return 0;
    }

}




function jsonToDate ( fecha ){
    
//    f = f.toISOString().slice(0,10); 
    var d =  new Date( fecha );

    if (Object.prototype.toString.call(d) === "[object Date]") {
      // it is a date
      //alert("// it is a date");
      
      if (isNaN(d.getTime())) {  // d.valueOf() could also work        
        //alert("// date is not valid");
        return null;
      } else {
        // date is valid        
        return d.toISOString().slice(0,10); 
      }
    } else {
      // not a date
      return "";
      
    }

}






function stod ( str ){
    
    str = str.toString().trim();        
    f = str.split(' ');
    
    m = f[1] + "/" + f[0]  + "/" + f[2] ;
    m = m.replace(",", "");
    
    return m;
}






function fDMA ( str ){
    
    var f  = dateToString( str ) ;
    var ret = "";
    
    if (f.length == 8 ){
        ret  = f.substring(8,6)+"/"+f.substring(6,4)+"/"+f.substring(4,0);
    }
    else
    {
        ret = "";
    }
    
    
    return ret ;
}











function NumQP(strnumero) {

    var buscar="," 
    var resultado = 0;
    
    resultado =  strnumero.replace(new RegExp(buscar,"g") ,"");   
    return resultado; 
}







function isNumberKey(evt){
    
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57)){
       return false;
   }
   else{
       return true;
   }
      
}






