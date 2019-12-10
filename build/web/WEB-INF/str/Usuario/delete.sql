WITH filas AS (
    
    DELETE FROM sistema.usuarios
    WHERE usuario = v0
    RETURNING usuario

)
SELECT count(*) resultado FROM filas; 