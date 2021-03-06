

SELECT  
  objetos_gastos.objeto ,  
  objetos_gastos.descripcion,  
  sum(rendiciones_gastos.importe) total_rendicion 
  , general_rendicion  
  
FROM 
  aplicacion.consejos_salud,   
  aplicacion.transferencias_fondos,   
  aplicacion.rendiciones_gastos, 
  aplicacion.rendiciones_verificacion,  
  aplicacion.resoluciones, 
  aplicacion.objetos_gastos, 
   ( 
	SELECT  
		sum(rendiciones_gastos.importe) general_rendicion  	   
	FROM 
	  aplicacion.consejos_salud,  
	  aplicacion.transferencias_fondos,  
	  aplicacion.rendiciones_gastos, 
	  aplicacion.rendiciones_verificacion,  
	  aplicacion.resoluciones, 
	  aplicacion.objetos_gastos 
	  
	WHERE 
	  consejos_salud.cod = transferencias_fondos.consejo AND  
	  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND 
	  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion AND 
	  resoluciones.numero = transferencias_fondos.resolucion_numero AND 
	  objetos_gastos.objeto = rendiciones_gastos.objeto 
	  and resoluciones.estado = v0 
	  and rendiciones_gastos.fecha between 'v1' and 'v2' 
	  and rendiciones_gastos.objeto  between v3 and v4  
  ) as gen 
  
WHERE 
  consejos_salud.cod = transferencias_fondos.consejo AND  
  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND  
  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion AND 
  resoluciones.numero = transferencias_fondos.resolucion_numero AND 
  objetos_gastos.objeto = rendiciones_gastos.objeto 
  and resoluciones.estado = v0 
  and rendiciones_gastos.fecha between 'v1' and 'v2' 
  and rendiciones_gastos.objeto  between v3 and v4  
 
GROUP BY objetos_gastos.objeto ,   objetos_gastos.descripcion , general_rendicion 
  
order by objetos_gastos.objeto   


