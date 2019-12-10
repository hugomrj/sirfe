    

SELECT usuario, cuenta, clave, token_iat 
  FROM sistema.usuarios 
  WHERE 
    ( cast(cuenta as text) ilike '%v0%' )  
    
