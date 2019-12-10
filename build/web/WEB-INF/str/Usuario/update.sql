    
UPDATE sistema.usuarios
   SET 
    cuenta= 'v0', 
    clave=   md5('v1')
 WHERE usuario = v2
 RETURNING usuario


