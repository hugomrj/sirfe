
SELECT 
  usuarios.usuario,   usuarios.cuenta,   usuarios.clave 
FROM  
  sistema.usuarios,  
  sistema.roles_x_sucursales, 
  sistema.roles, 
  sistema.usuarios_x_roles 
WHERE 
  usuarios.usuario = usuarios_x_roles.usuario AND 
  roles.rol = roles_x_sucursales.rol AND 
  usuarios_x_roles.rol = roles.rol 
  and 
    cuenta like 'v0'    
    and clave like md5('v1')  
    and sucursal = v2 


  