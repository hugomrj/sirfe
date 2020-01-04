

SELECT  
  consejos_salud.cod,   
  consejos_salud.descripcion,  
  departamentos.dpto,  
  departamentos.descripcion  dpto_descripcion  
FROM  
  aplicacion.consejos_salud,  
  aplicacion.departamentos 
WHERE  
  consejos_salud.dpto = departamentos.dpto 
  and departamentos.dpto between  v0  and  v1  
  order by consejos_salud.cod   

