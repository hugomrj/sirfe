
        
SELECT transferencia, resolucion_numero, saldo_anterior, origen_ingreso, recibo_numero,  
       comprobante_numero, deposito_fecha, total_depositado, consejo, total_rendicion 
 
  FROM aplicacion.transferencias_fondos  
  where  
	consejo = v1  
	and (
	cast(resolucion_numero as text) ilike '%v0%'  
	or cast(recibo_numero as text) ilike '%v0%'  
	or cast(comprobante_numero as text) ilike '%v0%'  
	or cast(deposito_fecha as text) ilike '%v0%'  

	) 
  order by deposito_fecha   




