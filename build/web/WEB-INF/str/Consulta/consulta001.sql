
SELECT departamentos.dpto, departamentos.descripcion dpto_descrip, consejo, consejos_salud.descripcion consejo_descrip,  
count(*) as cant_trasferencia, sum(total_depositado) total_depositado, general_depositado 
  FROM aplicacion.transferencias_fondos,  aplicacion.consejos_salud, aplicacion.departamentos, 
( 
SELECT sum(total_depositado) general_depositado 
  FROM aplicacion.transferencias_fondos,  aplicacion.consejos_salud, aplicacion.departamentos 
  where deposito_fecha between 'v0' and 'v1'  
  and  departamentos.dpto between v2 and v3 
  and consejo between v4 and v5      
  and transferencias_fondos.consejo = consejos_salud.cod 
  and departamentos.dpto =  consejos_salud.dpto 
) as gen 
  where deposito_fecha between 'v0' and 'v1'  
  and  departamentos.dpto between v2 and v3 
  and consejo between v4 and v5      
  and transferencias_fondos.consejo = consejos_salud.cod 
  and departamentos.dpto =  consejos_salud.dpto 
  group by departamentos.dpto, departamentos.descripcion, consejo, consejos_salud.descripcion, general_depositado  
  order by departamentos.dpto,  consejo 


