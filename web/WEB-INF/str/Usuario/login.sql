    

    SELECT usuario, cuenta, clave 
    FROM sistema.usuarios 
    WHERE cuenta like 'v0'  
    and clave like md5('v1')  