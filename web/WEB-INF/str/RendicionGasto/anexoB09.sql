

SELECT 
  transferencias_fondos.transferencia, 
  transferencias_fondos.resolucion_numero, 
  transferencias_fondos.saldo_anterior, 
  transferencias_fondos.origen_ingreso, 
  transferencias_fondos.recibo_numero, 
  transferencias_fondos.comprobante_numero, 
  transferencias_fondos.deposito_fecha, 
  transferencias_fondos.total_depositado, 
  transferencias_fondos.consejo, 
  transferencias_fondos.total_rendicion, 
  rendiciones_gastos.rendicion, 
  rendiciones_gastos.resolucion_numero, 
  rendiciones_gastos.tipo_comprobante, 
  rendiciones_gastos.comprobante_numero as rendicion_comprobante_numero, 
  rendiciones_gastos.objeto, 
	TRIM(rendiciones_gastos.concepto) concepto, 
  rendiciones_gastos.fecha as rendicion_fecha, 
  rendiciones_gastos.importe, 
  rendiciones_gastos.observacion as rendicion_observacion, 
  consejos_salud.cod, 
  consejos_salud.descripcion, 
  tipos_comprobantes.descripcion as comprobante_descripcion, 
  (saldo_anterior + total_depositado) as total_rendir, 
	decretos.agno, decretos.decreto,  programa, subprograma 
  
  
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



