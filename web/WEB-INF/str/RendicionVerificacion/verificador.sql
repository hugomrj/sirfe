 

SELECT  
  departamentos.dpto,  
  departamentos.descripcion,  
  departamentos.verificador,  
  usuarios.cuenta 
FROM  
  aplicacion.departamentos,  
  sistema.usuarios 
WHERE  
  usuarios.usuario = departamentos.verificador 
  and usuarios.usuario = v0  
limit 1 



  
