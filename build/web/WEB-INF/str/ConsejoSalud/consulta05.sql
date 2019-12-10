SELECT 
    transferencias_fondos.consejo, 
  consejos_salud.descripcion, 
  sum(transferencias_fondos.total_depositado) sum_depositado , 
    general_rendicion 
  
FROM 
  aplicacion.consejos_salud, 
  aplicacion.transferencias_fondos, 
  aplicacion.rendiciones_gastos, 
  aplicacion.resoluciones, 
			  (  
			SELECT 
			  sum(rendiciones_gastos.importe) general_rendicion  
			FROM 
			  aplicacion.consejos_salud, 
			  aplicacion.transferencias_fondos, 
			  aplicacion.rendiciones_gastos, 
			  aplicacion.resoluciones 
			WHERE 
			  transferencias_fondos.consejo = consejos_salud.cod AND 
			  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND 
			  resoluciones.numero = transferencias_fondos.resolucion_numero 
			    and resoluciones.estado = v0 
			    and deposito_fecha between 'v1'  and  'v2' and 
			  transferencias_fondos.consejo between v3  and  v4   
			  
			  ) as gen 

WHERE 
  transferencias_fondos.consejo = consejos_salud.cod AND 
  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND 
  resoluciones.numero = transferencias_fondos.resolucion_numero 
    and resoluciones.estado = v0 
    and deposito_fecha between 'v1'  and  'v2' and 
  transferencias_fondos.consejo between v3  and  v4   
  
  group by   transferencias_fondos.consejo,   consejos_salud.descripcion ,   general_rendicion  
order by transferencias_fondos.consejo  



