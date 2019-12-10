        
SELECT  
  usuarios.usuario,  
  consejos_salud.cod as consejo 
FROM  
  sistema.usuarios,  
  sistema.usuarios_x_roles,  
  aplicacion.consejos_salud 
WHERE  
  usuarios.usuario = usuarios_x_roles.usuario AND 
  usuarios_x_roles.rol = consejos_salud.rol 
  and usuarios.usuario = v0 

