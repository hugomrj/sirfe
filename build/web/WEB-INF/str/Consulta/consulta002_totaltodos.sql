	SELECT
	  sum(importe) total_depositado_todos 
	FROM  
	  aplicacion.transferencias_fondos,   aplicacion.consejos_salud,   aplicacion.departamentos,  
	  aplicacion.rendiciones_gastos,   aplicacion.objetos_gastos 
	  WHERE 
	  consejos_salud.cod = transferencias_fondos.consejo 
                AND  departamentos.dpto = consejos_salud.dpto 
                AND rendiciones_gastos.transferencia = transferencias_fondos.transferencia 
                AND  rendiciones_gastos.objeto = objetos_gastos.objeto 

