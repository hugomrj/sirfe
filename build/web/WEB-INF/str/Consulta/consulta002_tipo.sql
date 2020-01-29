


	SELECT 
	  objetos_gastos.objeto,   objetos_gastos.descripcion objeto_descrip,   departamentos.dpto,   departamentos.descripcion dpto_descrip, 
	  transferencias_fondos.consejo,   consejos_salud.descripcion consejo_descrip,  
	  sum(importe) total_depositado, general_depositado 
	FROM  
	  aplicacion.transferencias_fondos,   aplicacion.consejos_salud,   aplicacion.departamentos,  
	  aplicacion.rendiciones_gastos,   aplicacion.objetos_gastos, ( 

			SELECT sum(importe) general_depositado  from(
			SELECT rendiciones_gastos.importe
			FROM  
			  aplicacion.transferencias_fondos,   aplicacion.consejos_salud,   aplicacion.departamentos,  
			  aplicacion.rendiciones_gastos,   aplicacion.objetos_gastos 
			WHERE  
			  consejos_salud.cod = transferencias_fondos.consejo AND  departamentos.dpto = consejos_salud.dpto AND 
			  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND  rendiciones_gastos.objeto = objetos_gastos.objeto 
                                            
                                            and tipo_transferencia = v0  
                                            and  deposito_fecha between 'v1' and 'v2'   
                                            and  departamentos.dpto between v3 and v4     
                                            and transferencias_fondos.consejo between v5  and v6   
                                            and objetos_gastos.objeto between v7 and v8   
                                                and tipo_transferencia = v9     

			) as sg


	  ) as gen  
	WHERE 
	  consejos_salud.cod = transferencias_fondos.consejo AND  departamentos.dpto = consejos_salud.dpto AND 
	  rendiciones_gastos.transferencia = transferencias_fondos.transferencia AND  rendiciones_gastos.objeto = objetos_gastos.objeto  

                and tipo_transferencia = v0  
                and  deposito_fecha between 'v1' and 'v2'   
                and  departamentos.dpto between v3 and v4     
                and transferencias_fondos.consejo between v5  and v6   
                and objetos_gastos.objeto between v7 and v8   
                    and tipo_transferencia = v9     

	group by  objetos_gastos.objeto,   objetos_gastos.descripcion,   departamentos.dpto,   departamentos.descripcion, 
	  transferencias_fondos.consejo,   consejos_salud.descripcion, general_depositado  
	
order by     objeto, dpto, consejo 


