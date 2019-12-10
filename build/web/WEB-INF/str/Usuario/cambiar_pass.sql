

UPDATE sistema.usuarios 
   SET clave = md5('v2') 
 WHERE usuario   
     =  (SELECT usuario 
	FROM sistema.usuarios  
	WHERE usuario = v0   
	and clave like md5('v1'))  

RETURNING usuario        







