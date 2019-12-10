
SELECT 
  roles_x_selectores.id, 
  roles.rol, 
  roles.nombre_rol, 
  selectores.superior, 
  selectores.descripcion, 
  selectores.ord 
FROM 
  sistema.roles_x_selectores, 
  sistema.selectores, 
  sistema.roles 
WHERE 
  selectores.id = roles_x_selectores.selector AND 
  roles.rol = roles_x_selectores.rol 
  and  
  (  selectores.descripcion ilike '%v0%' or roles.nombre_rol ilike '%v0%' ) 


