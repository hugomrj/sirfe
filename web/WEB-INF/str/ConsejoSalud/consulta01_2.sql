

SELECT 
  consejos_salud.cod, 
  consejos_salud.descripcion,   
  sum(rendiciones_gastos.importe) total_rendicion, 
  general_rendicion 
FROM 
  aplicacion.consejos_salud,  
  aplicacion.transferencias_fondos,  
  aplicacion.rendiciones_gastos, 
  aplicacion.rendiciones_verificacion,  
  aplicacion.resoluciones , 

			  ( 
			SELECT 
			  sum(rendiciones_gastos.importe) general_rendicion  
			FROM  
			  aplicacion.consejos_salud,  
			  aplicacion.transferencias_fondos,  
			  aplicacion.rendiciones_gastos, 
			  aplicacion.rendiciones_verificacion,  
			  aplicacion.resoluciones 
			WHERE 
			  consejos_salud.cod = transferencias_fondos.consejo AND 
			  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND 
			  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion AND 
			  resoluciones.numero = transferencias_fondos.resolucion_numero 
			  and resoluciones.estado = v0 
			  and rendiciones_gastos.fecha between 'v1' and 'v2' 

			  ) as gen 

  
WHERE  
  consejos_salud.cod = transferencias_fondos.consejo AND  
  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND  
  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion AND 
  resoluciones.numero = transferencias_fondos.resolucion_numero 
  and resoluciones.estado = v0 
  and rendiciones_gastos.fecha between 'v1' and 'v2'  
 
GROUP BY consejos_salud.cod,   consejos_salud.descripcion, general_rendicion  



 
order by cod 
