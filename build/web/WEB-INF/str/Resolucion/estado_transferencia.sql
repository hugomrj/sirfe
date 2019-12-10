

SELECT  
  transferencias_fondos.transferencia as id,  
  transferencias_fondos.resolucion_numero as numero,  
  transferencias_fondos.estado_transferencia as estado,  
  resoluciones_estados.descripcion 
FROM  
  aplicacion.transferencias_fondos,  
  aplicacion.resoluciones_estados 
WHERE  
  transferencias_fondos.estado_transferencia = resoluciones_estados.estado 
  and  transferencias_fondos.resolucion_numero like 'v1' 
  and consejo = v0 


