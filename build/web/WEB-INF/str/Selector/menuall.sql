
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