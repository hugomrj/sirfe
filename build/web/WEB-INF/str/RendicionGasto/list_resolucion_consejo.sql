


SELECT  
 resolucion_numero 
  
FROM    
  aplicacion.rendiciones_gastos,   
  aplicacion.departamentos,   
  aplicacion.consejos_salud  
  

WHERE   
  rendiciones_gastos.consejo = consejos_salud.cod AND  
  consejos_salud.dpto = departamentos.dpto  
	and verificador = v0  
	and consejo = v1   
  
group by resolucion_numero  



  