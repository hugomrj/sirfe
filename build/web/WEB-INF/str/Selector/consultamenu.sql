
select * from ( 
with recursive menu(id, superior, descripcion, nivel, codigo, link ) as ( 

  SELECT id, superior, descripcion, 0 as ni, concat(ord) as codigo, 
  link, ord 
  FROM sistema.selectores 
  where superior = 0 
		
union all 

  SELECT selectores.id,  selectores.superior, selectores.descripcion, nivel+1,  
  concat( codigo , '.', selectores.ord) codigo, selectores.link, selectores.ord 
  FROM sistema.selectores, menu 
  where selectores.superior = menu.id 
  
)  

SELECT id as selector, superior, descripcion,nivel, codigo, link, ord 
FROM menu  
order by codigo 
) as mc , 
( 
with recursive menu(id, superior ) as ( 


  SELECT id, superior 
  FROM sistema.selectores, 
  ( 
		SELECT 
		  roles_x_selectores.selector 
		FROM  
		  sistema.usuarios_x_roles,  
		  sistema.roles_x_selectores 
		WHERE  
		  usuarios_x_roles.rol = roles_x_selectores.rol 
		  and  usuarios_x_roles.usuario = v0  
		group by roles_x_selectores.selector 
  ) as mam 
  where id = mam.selector 
		
union all 

  SELECT selectores.id,  selectores.superior 
  FROM sistema.selectores, menu 
  where selectores.id = menu.superior  
) 
SELECT id as selector, superior 
FROM menu  
group by selector, superior 
 ) as mu 
where mc.selector = mu.selector and mc.superior = mu.superior 
order by codigo 



