-- tablas_basedatos.sql
-- Esquema MySQL alineado al requerimiento

CREATE TABLE persona (
  persona_id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(120) NOT NULL,
  genero VARCHAR(20) NOT NULL,
  edad INT NOT NULL,
  identificacion VARCHAR(30) NOT NULL,
  direccion VARCHAR(200) NOT NULL,
  telefono VARCHAR(30) NOT NULL,
  PRIMARY KEY (persona_id),
  UNIQUE KEY uk_persona_identificacion (identificacion)
) ENGINE=InnoDB;

CREATE TABLE cliente (
  cliente_id BIGINT NOT NULL,
  contrasena VARCHAR(120) NOT NULL,
  estado TINYINT(1) NOT NULL,
  PRIMARY KEY (cliente_id),
  CONSTRAINT fk_cliente_persona
    FOREIGN KEY (cliente_id) REFERENCES persona(persona_id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE cuenta (
  cuenta_id BIGINT NOT NULL AUTO_INCREMENT,
  numero_cuenta VARCHAR(30) NOT NULL,
  tipo_cuenta VARCHAR(30) NOT NULL,
  saldo_inicial DECIMAL(18,2) NOT NULL,
  estado TINYINT(1) NOT NULL,
  cliente_id BIGINT NOT NULL,
  PRIMARY KEY (cuenta_id),
  UNIQUE KEY uk_cuenta_numero (numero_cuenta),
  KEY idx_cuenta_cliente (cliente_id),
  CONSTRAINT fk_cuenta_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE movimiento (
  movimiento_id BIGINT NOT NULL AUTO_INCREMENT,
  fecha DATE NOT NULL,
  tipo_movimiento VARCHAR(30) NOT NULL,
  valor DECIMAL(18,2) NOT NULL,
  saldo DECIMAL(18,2) NOT NULL,
  cuenta_id BIGINT NOT NULL,
  PRIMARY KEY (movimiento_id),
  KEY idx_movimiento_cuenta (cuenta_id),
  KEY idx_movimiento_fecha (fecha),
  CONSTRAINT fk_movimiento_cuenta
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(cuenta_id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;
