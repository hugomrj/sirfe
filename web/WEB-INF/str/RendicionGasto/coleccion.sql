
SELECT rendicion, transferencia, resolucion_numero, tipo_comprobante,  
       comprobante_numero, objeto, concepto, fecha, importe, observacion, 
    ruc_factura,  timbrado_venciomiento  

  FROM aplicacion.rendiciones_gastos 
  where transferencia = v0  
  order by fecha  

