


SELECT 
  departamentos.dpto,   departamentos.descripcion dpto_descrip,  
  transferencias_fondos.consejo,   consejos_salud.descripcion consejo_descrip,  
  transferencias_fondos.resolucion_numero,  
  (total_depositado + saldo_anterior ) total_depositado  ,   
  transferencias_fondos.total_rendicion, 
  ((total_depositado + saldo_anterior ) - transferencias_fondos.total_rendicion ) diferencia, 
  count(transferencias_fondos.*) cant_trasferencia, 
  general_depositado, general_rendicion  , general_diferencia 
FROM  
  aplicacion.transferencias_fondos,  
  aplicacion.consejos_salud,   aplicacion.departamentos,   
( 

SELECT 
  sum(transferencias_fondos.total_depositado) as general_depositado, 
     sum(transferencias_fondos.total_rendicion) as general_rendicion  , 
      sum(transferencias_fondos.total_depositado) - sum(transferencias_fondos.total_rendicion) general_diferencia 
FROM  
  aplicacion.transferencias_fondos,   aplicacion.consejos_salud,   aplicacion.departamentos  
WHERE  
  consejos_salud.cod = transferencias_fondos.consejo AND  
  departamentos.dpto = consejos_salud.dpto 
  and  deposito_fecha between 'v0' and 'v1'  
  and  departamentos.dpto between v2 and v3  
  and transferencias_fondos.consejo between v4 and v5    
    and tipo_transferencia = v6    

) gen  
  
WHERE  
  consejos_salud.cod = transferencias_fondos.consejo AND 
  departamentos.dpto = consejos_salud.dpto and  

  consejos_salud.cod = transferencias_fondos.consejo AND 
  departamentos.dpto = consejos_salud.dpto 
  and  deposito_fecha between 'v0' and 'v1'  
  and  departamentos.dpto between v2 and v3  
  and transferencias_fondos.consejo between v4 and v5    
    and tipo_transferencia = v6    


    group by  
  departamentos.dpto,   departamentos.descripcion ,  
  transferencias_fondos.consejo,   consejos_salud.descripcion,  
  transferencias_fondos.resolucion_numero,  
  (total_depositado + saldo_anterior ),   
  transferencias_fondos.total_rendicion , 
  ((total_depositado + saldo_anterior ) - transferencias_fondos.total_rendicion ), 
  general_depositado, general_rendicion  , general_diferencia 

order by departamentos.dpto,   transferencias_fondos.consejo, transferencias_fondos.resolucion_numero  

