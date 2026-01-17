-- tablas_basedatos.sql
-- Esquema MySQL alineado al requerimiento con datos de prueba

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
  fecha DATETIME NOT NULL,
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

INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono) VALUES
('Jose Lema', 'M', 30, 'ID-001', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'F', 28, 'ID-002', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'M', 35, 'ID-003', '13 junio y Equinoccial', '098874587');

INSERT INTO cliente (cliente_id, contrasena, estado) VALUES
(1, '1234', 1),
(2, '5678', 1),
(3, '1245', 1);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
('478758', 'Ahorro', 2000, 1, 1),
('225487', 'Corriente', 100, 1, 2),
('495878', 'Ahorros', 0, 1, 3),
('496825', 'Ahorros', 540, 1, 2),
('585545', 'Corriente', 1000, 1, 1);

INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
('2022-02-10 08:00:00', 'Deposito', 600, 700, 2),
('2022-02-08 09:00:00', 'Retiro', -540, 0, 4),
('2022-02-09 10:00:00', 'Retiro', -575, 1425, 1);
