

SELECT id, codstr, cod, descripcion,  
consejos_salud.dpto, rol 
  FROM aplicacion.consejos_salud,  
  ( 
	SELECT dpto 
	  FROM aplicacion.departamentos 
	  where verificador = v0 
	  limit 1 
  ) as d 
  where aplicacion.consejos_salud.dpto = d.dpto 
order by cod





