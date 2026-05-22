CREATE DATABASE IF NOT EXISTS sistema_logistica;
USE sistema_logistica;

-- 1. TABLAS
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100),
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reportes (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    guia_paquete VARCHAR(20) NOT NULL,
    motivo VARCHAR(900) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    ruta_evidencia VARCHAR(250),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Pendiente', 'En Revisión', 'Resuelto') DEFAULT 'Pendiente'
);

CREATE TABLE IF NOT EXISTS auditoria_usuarios (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_afectado INT,
    username_afectado VARCHAR(100),
    accion VARCHAR(50),
    detalle TEXT,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS paquetes (
    id_paquete INT AUTO_INCREMENT PRIMARY KEY,
    guia_rastreo VARCHAR(20) UNIQUE NOT NULL,
    ciudad_origen VARCHAR(100),
    ciudad_destino VARCHAR(100),
    remitente VARCHAR(100) NOT NULL,
    destinatario VARCHAR(100) NOT NULL,
    direccion_entrega TEXT,
    peso DOUBLE NOT NULL,
    costo_envio DOUBLE,
    tipo_envio VARCHAR(50),
    estado VARCHAR(50) DEFAULT 'En Bodega',
    ubicacion_actual VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS solicitudes_envio (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    guia VARCHAR(20),
    remitente VARCHAR(100) NOT NULL,
    destinatario VARCHAR(100) NOT NULL,
    ciudad_origen VARCHAR(100),
    ciudad_destino VARCHAR(100),
    direccion_entrega TEXT,
    peso DOUBLE NOT NULL,
    costo_envio DOUBLE,
    tipo_envio VARCHAR(50),
    estado VARCHAR(50) DEFAULT 'Pendiente',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movimientos_paquete (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    guia_rastreo VARCHAR(20),
    estado VARCHAR(50),
    ubicacion VARCHAR(100),
    descripcion VARCHAR(255),
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (guia_rastreo) REFERENCES paquetes(guia_rastreo)
);

CREATE TABLE IF NOT EXISTS pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    guia_rastreo VARCHAR(20) NOT NULL,
    monto DOUBLE NOT NULL,
    metodo_pago VARCHAR(50),
    estado_pago ENUM('Pendiente', 'Pagado', 'Rechazado') DEFAULT 'Pendiente',
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (guia_rastreo) REFERENCES paquetes(guia_rastreo)
);

-- 2. PROCEDIMIENTOS Y TRIGGERS
DELIMITER //

-- Procedimiento corregido para el Login
DROP PROCEDURE IF EXISTS sp_validar_login //
CREATE PROCEDURE sp_validar_login(IN p_username VARCHAR(100), IN p_password VARCHAR(255))
BEGIN
    -- Ahora devuelve los 3 campos que tu Java espera
    SELECT cedula, nombre, rol 
    FROM usuarios 
    WHERE BINARY username = p_username 
    AND BINARY password = p_password;
END //

DROP PROCEDURE IF EXISTS insertar_reporte //
CREATE PROCEDURE insertar_reporte(IN r_guia_paquete VARCHAR(50), IN r_motivo VARCHAR(900), IN r_descripcion VARCHAR(300), IN r_ruta_evidencia VARCHAR(250))
BEGIN
    INSERT INTO reportes (guia_paquete, motivo, descripcion, ruta_evidencia) VALUES (r_guia_paquete, r_motivo, r_descripcion, r_ruta_evidencia);
END //

DROP PROCEDURE IF EXISTS sp_insertar_usuario //
CREATE PROCEDURE sp_insertar_usuario(IN u_cedula VARCHAR(20), IN u_nombre VARCHAR(100), IN u_username VARCHAR(100), IN u_password VARCHAR(255))
BEGIN
    INSERT INTO usuarios(cedula, nombre, username, password, rol) VALUES (u_cedula, u_nombre, u_username, u_password, 'USER');
END //

DROP PROCEDURE IF EXISTS sp_registrar_paquete //
CREATE PROCEDURE sp_registrar_paquete(IN p_guia VARCHAR(20), IN p_ciudad_origen VARCHAR(100), IN p_ciudad_destino VARCHAR(100), IN p_remitente VARCHAR(100), IN p_destinatario VARCHAR(100), IN p_direccion TEXT, IN p_peso DOUBLE, IN p_fecha_form VARCHAR(50), IN p_tipo VARCHAR(50), IN p_estado VARCHAR(50), IN p_ubicacion_actual VARCHAR(100))
BEGIN
    INSERT INTO paquetes(guia_rastreo, ciudad_origen, ciudad_destino, remitente, destinatario, direccion_entrega, peso, fecha_registro, tipo_envio, estado, ubicacion_actual)
    VALUES (p_guia, p_ciudad_origen, p_ciudad_destino, p_remitente, p_destinatario, p_direccion, p_peso, p_fecha_form, p_tipo, p_estado, p_ubicacion_actual);
END //

DROP TRIGGER IF EXISTS tr_auditoria_usuarios //
CREATE TRIGGER tr_auditoria_usuarios
AFTER UPDATE ON usuarios FOR EACH ROW
BEGIN
    IF OLD.rol <> NEW.rol OR OLD.username <> NEW.username THEN
        INSERT INTO auditoria_usuarios (id_usuario_afectado, username_afectado, accion, detalle)
        VALUES (NEW.id_usuario, NEW.username, 'MODIFICACION', CONCAT('Cambio de: ', OLD.username, ' a: ', NEW.username));
    END IF;
END //

DELIMITER ;

-- 3. INSERTS
INSERT IGNORE INTO usuarios (cedula, nombre, username, password, rol) 
VALUES ('12345678', 'Camilo Administrador', '12345678', '123', 'ADMIN');