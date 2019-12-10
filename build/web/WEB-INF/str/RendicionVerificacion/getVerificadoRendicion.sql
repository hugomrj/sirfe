SELECT  
  rendiciones_verificacion.verificacion,  
  departamentos.verificador 
FROM  
  aplicacion.rendiciones_verificacion,  
  aplicacion.rendiciones_gastos,  
  aplicacion.consejos_salud,  
  aplicacion.departamentos 
WHERE  
  rendiciones_gastos.rendicion = rendiciones_verificacion.rendicion AND 
  consejos_salud.cod = rendiciones_gastos.consejo AND 
  departamentos.dpto = consejos_salud.dpto 
  and rendiciones_verificacion.verificacion = v0 



