

SELECT objeto, descripcion, imputa 
  FROM aplicacion.objetos_gastos 
  where imputa like 'S' 
  and descripcion ilike '%v0%' 
  order by objeto 


