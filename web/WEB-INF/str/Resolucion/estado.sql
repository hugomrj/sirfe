


SELECT  
  resoluciones.id,  
  resoluciones.numero,  
  resoluciones.estado,  
  resoluciones_estados.descripcion 
FROM  
  aplicacion.resoluciones,  
  aplicacion.resoluciones_estados 
WHERE  
  resoluciones.estado = resoluciones_estados.estado 
  and  resoluciones.numero like 'v0' 
