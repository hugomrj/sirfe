    
SELECT 
  usuarios_x_roles.id, 
  usuarios_x_roles.usuario, 
  usuarios_x_roles.rol, 
  usuarios.cuenta, 
  roles.nombre_rol 
FROM  
  sistema.usuarios_x_roles,  
  sistema.usuarios,  
  sistema.roles 
WHERE  
  usuarios.usuario = usuarios_x_roles.usuario AND 
  roles.rol = usuarios_x_roles.rol 
  and  
  (  usuarios.cuenta ilike '%v0%' or roles.nombre_rol ilike '%v0%' ) 

