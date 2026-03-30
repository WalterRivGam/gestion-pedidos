-- Inserción de zonas de prueba

INSERT INTO zonas (id, soporte_refrigeracion)
VALUES
    ('ZONA1', true),
    ('ZONA2', false),
    ('ZONA3', true),
    ('ZONA4', true),
    ('ZONA5', false),
    ('ZONA6', true),
    ('ZONA7', true),
    ('ZONA8', true),
    ('ZONA9', false),
    ('ZONA10', true);

-- Inserción de clientes de prueba

INSERT INTO clientes (id, activo)
VALUES
    ('CLI-100', true),
    ('CLI-200', true),
    ('CLI-300', true),
    ('CLI-400', true),
    ('CLI-500', true),
    ('CLI-600', true),
    ('CLI-700', true),
    ('CLI-800', false),
    ('CLI-900', false),
    ('CLI-950', false);
