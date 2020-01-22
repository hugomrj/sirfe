
SELECT   
  rendiciones_gastos.rendicion,  
  rendiciones_gastos.transferencia,  
  rendiciones_gastos.resolucion_numero,  
  rendiciones_gastos.tipo_comprobante,  
  rendiciones_gastos.comprobante_numero,  
  rendiciones_gastos.objeto,  
  rendiciones_gastos.concepto,  
  rendiciones_gastos.fecha,  
  rendiciones_gastos.importe,  
  rendiciones_gastos.observacion, 
rendiciones_gastos.ruc_factura, 
rendiciones_gastos.timbrado_venciomiento ,
rendiciones_gastos.timbrado_iniciovigencia    

  
FROM  
  aplicacion.rendiciones_gastos,  
  aplicacion.transferencias_fondos 
WHERE  
  rendiciones_gastos.transferencia = transferencias_fondos.transferencia 
  and transferencias_fondos.resolucion_numero like 'v0' 
  and transferencias_fondos.consejo = v1 
order by rendiciones_gastos.fecha 
