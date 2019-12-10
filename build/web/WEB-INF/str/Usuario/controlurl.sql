    
SELECT  
  usuarios.usuario,  
  usuarios.cuenta,  
  roles.rol,  
  roles_x_selectores.selector,  
  selectores.descripcion,  
  selectores.link 
FROM  
  sistema.usuarios,  
  sistema.usuarios_x_roles,  
  sistema.roles,  
  sistema.roles_x_selectores,  
  sistema.selectores 
WHERE 
  usuarios.usuario = usuarios_x_roles.usuario AND 
  usuarios_x_roles.rol = roles.rol AND 
  roles.rol = roles_x_selectores.rol AND 
  roles_x_selectores.selector = selectores.id 

  and usuarios.usuario = v0
  and link like 'v1'