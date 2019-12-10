

SELECT   
    rendiciones_verificacion.* 
    
FROM  
  aplicacion.rendiciones_verificacion,  
  aplicacion.rendiciones_gastos,  
  aplicacion.consejos_salud,  
  aplicacion.departamentos 
WHERE  
  rendiciones_gastos.rendicion = rendiciones_verificacion.rendicion AND 
  rendiciones_gastos.consejo = consejos_salud.cod AND 
  departamentos.dpto = consejos_salud.dpto 
  and verificador = v0  
  and consejo =  v1 
  and resolucion_numero like 'v2' 




