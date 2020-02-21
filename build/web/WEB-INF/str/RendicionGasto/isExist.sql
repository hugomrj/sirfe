


SELECT 
  transferencias_fondos.transferencia
  
FROM 
  aplicacion.transferencias_fondos, 
  aplicacion.rendiciones_gastos, 
  aplicacion.consejos_salud, 
  aplicacion.tipos_comprobantes,
  aplicacion.decretos
  
WHERE 
  transferencias_fondos.transferencia = rendiciones_gastos.transferencia AND
  consejos_salud.cod = rendiciones_gastos.consejo AND
  transferencias_fondos.estado_transferencia = 1 and
  tipos_comprobantes.tipo_comprobante = rendiciones_gastos.tipo_comprobante
  and transferencias_fondos.resolucion_numero like 'v0' 
  and transferencias_fondos.consejo = v1

	and decretos.agno =  extract(year from  deposito_fecha)
  
  order by transferencias_fondos.resolucion_numero, transferencias_fondos.consejo
  limit 1