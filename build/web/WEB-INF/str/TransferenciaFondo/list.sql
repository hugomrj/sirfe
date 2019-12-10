
        
SELECT transferencia, resolucion_numero, saldo_anterior, origen_ingreso, recibo_numero, 
       comprobante_numero, deposito_fecha, total_depositado, consejo, total_rendicion  

  FROM aplicacion.transferencias_fondos 
  where consejo = v0  
  order by deposito_fecha  


