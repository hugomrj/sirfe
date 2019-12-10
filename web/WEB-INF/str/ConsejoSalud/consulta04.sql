

SELECT 
  transferencias_fondos.consejo, 
  consejos_salud.descripcion, 
  sum(transferencias_fondos.total_depositado) sum_depositado 
  , general_depositado 
  
FROM 
  aplicacion.transferencias_fondos, 
  aplicacion.consejos_salud, ( 

	SELECT 
	  sum(transferencias_fondos.total_depositado) general_depositado 
	  
	FROM 
	  aplicacion.transferencias_fondos,  
	  aplicacion.consejos_salud 
	WHERE 
	  consejos_salud.cod = transferencias_fondos.consejo and  
	  deposito_fecha between 'v0'  and  'v1' and  
	  transferencias_fondos.consejo between v2  and  v3   
  ) as gen 
WHERE 
  consejos_salud.cod = transferencias_fondos.consejo and 
  deposito_fecha between 'v0'  and  'v1' and 
  transferencias_fondos.consejo between v2  and  v3   
  
group by   transferencias_fondos.consejo,   consejos_salud.descripcion,  general_depositado  
order by transferencias_fondos.consejo  
