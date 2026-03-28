CREATE TABLE clientes (
  id VARCHAR(255) PRIMARY KEY,
  activo BOOLEAN NOT NULL
);

CREATE TABLE zonas (
  id VARCHAR(255) PRIMARY KEY,
  soporte_refrigeracion BOOLEAN NOT NULL
);

CREATE TABLE pedidos (
  id UUID PRIMARY KEY,
  numero_pedido VARCHAR(255) UNIQUE NOT NULL,
  cliente_id VARCHAR(255) NOT NULL,
  zona_id VARCHAR(255) NOT NULL,
  fecha_entrega DATE NOT NULL,
  estado VARCHAR(50) CHECK (estado IN ('PENDIENTE', 'CONFIRMADO', 'ENTREGADO')) NOT NULL,
  requiere_refrigeracion BOOLEAN NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pedidos_estado_fecha ON pedidos(estado, fecha_entrega);

CREATE TABLE cargas_idempotencia (
  id UUID PRIMARY KEY,
  idempotency_key VARCHAR(255) NOT NULL,
  archivo_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uk_cargas_idempotencia UNIQUE (idempotency_key, archivo_hash)
);