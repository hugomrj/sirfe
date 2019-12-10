SELECT 
  consejos_salud.cod, 
  consejos_salud.descripcion, 
  transferencias_fondos.total_depositado, 
  transferencias_fondos.total_rendicion, 
  rendiciones_gastos.fecha, 
  rendiciones_verificacion.estado verificacion_estado, 
  resoluciones.estado resolucion_estado
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

  order by cod
