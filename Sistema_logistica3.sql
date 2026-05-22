CREATE DATABASE sistema_logistica;
USE sistema_logistica;

-- Estructura para manejar el acceso y roles.
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Longitud para seguridad/hash
    rol ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reportes (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    guia_paquete VARCHAR(20) NOT NULL,
    motivo VARCHAR(900) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    ruta_evidencia VARCHAR(250),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Pendiente', 'En Revisión', 'Resuelto') DEFAULT 'Pendiente'
);

-- 2. TABLA DE AUDITORÍA
-- Registra automáticamente cambios de roles o datos sensibles en usuarios.
CREATE TABLE auditoria_usuarios (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_afectado INT,
    username_afectado VARCHAR(100),
    accion VARCHAR(50),
    detalle TEXT,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABLA DE PAQUETES (Ajustada a tu Interfaz GoPack)
CREATE TABLE paquetes (
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

CREATE TABLE solicitudes_envio (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    guia VARCHAR(5),
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


CREATE TABLE movimientos_paquete (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    guia_rastreo VARCHAR(50),
    estado VARCHAR(50),
    ubicacion VARCHAR(100),
    descripcion VARCHAR(255),
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (guia_rastreo)
    REFERENCES paquetes(guia_rastreo)
);

-- =============================================
-- TRIGGERS
-- =============================================
DELIMITER //

CREATE PROCEDURE insertar_reporte(
    IN r_guia_paquete VARCHAR(50),
    IN r_motivo VARCHAR(900),
    IN r_descripcion VARCHAR(300),
    IN r_ruta_evidencia VARCHAR(250)
)
BEGIN
    INSERT INTO reportes (
        guia_paquete,
        motivo,
        descripcion,
        ruta_evidencia
    )
    VALUES (
        r_guia_paquete,
        r_motivo,
        r_descripcion,
        r_ruta_evidencia
    );
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_obtener_reportes()
BEGIN
    SELECT 
        id_reporte,
        guia_paquete,
        motivo,
        descripcion,
        ruta_evidencia,
        fecha_registro,
        estado
    FROM reportes
    ORDER BY id_reporte DESC;
END //

DELIMITER ;

DELIMITER //
-- Trigger para auditar cambios en la tabla de usuarios
CREATE TRIGGER tr_auditoria_usuarios
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
    IF OLD.rol <> NEW.rol OR OLD.username <> NEW.username THEN
        INSERT INTO auditoria_usuarios (id_usuario_afectado, username_afectado, accion, detalle)
        VALUES (NEW.id_usuario, NEW.username, 'MODIFICACION', 
                CONCAT('Cambio de: ', OLD.username, ' (', OLD.rol, ') a: ', NEW.username, ' (', NEW.rol, ')'));
    END IF;
END //
DELIMITER ;

-- =============================================
-- PROCEDIMIENTOS ALMACENADOS
-- =============================================

DELIMITER //

-- login seguro
CREATE PROCEDURE sp_validar_login(
    IN p_username VARCHAR(100),
    IN p_password VARCHAR(255)
)
BEGIN
    SELECT id_usuario, username, rol 
    FROM usuarios 
        WHERE BINARY username = p_username 
       AND BINARY password = p_password;
END //

-- registro de usuario (rol USER por defecto)
CREATE PROCEDURE sp_insertar_usuario(
    IN u_username VARCHAR(100),
    IN u_password VARCHAR(255)
)
BEGIN
    INSERT INTO usuarios(username, password, rol)
    VALUES (u_username, u_password, 'USER');
END //
delimiter ;

delimiter //
-- 3. REGISTRO DE PAQUETE (Mapeado exactamente a tu VtnRegistroPaquetes)

CREATE PROCEDURE sp_registrar_paquete(
    IN p_guia VARCHAR(20),
    IN p_ciudad_origen VARCHAR(100),
    IN p_ciudad_destino VARCHAR(100),
    IN p_remitente VARCHAR(100),
    IN p_destinatario VARCHAR(100),
    IN p_direccion TEXT,
    IN p_peso DOUBLE,
    IN p_fecha_form VARCHAR(50),
    IN p_tipo VARCHAR(50),
    IN p_estado VARCHAR(50),
    IN p_ubicacion_actual VARCHAR(100)
)
BEGIN

    INSERT INTO paquetes(
        guia_rastreo,
        ciudad_origen,
        ciudad_destino,
        remitente,
        destinatario,
        direccion_entrega,
        peso,
        fecha_registro,
        tipo_envio,
        estado,
        ubicacion_actual
    )
    VALUES (
        p_guia,
        p_ciudad_origen,
        p_ciudad_destino,
        p_remitente,
        p_destinatario,
        p_direccion,
        p_peso,
        p_fecha_form,
        p_tipo,
        p_estado,
        p_ubicacion_actual
    );
    
END //

-- consultas Generales
CREATE PROCEDURE sp_obtener_paquetes()
BEGIN
    SELECT * FROM paquetes ORDER BY id_paquete DESC;
END //

DELIMITER ;

-- cuentas de administrador
INSERT INTO usuarios (username, password, rol) VALUES ('Camilo11', '123', 'ADMIN');
INSERT INTO usuarios (username, password, rol) VALUES ('Camilo12', '456', 'ADMIN');

select * from reportes;
select * from usuarios;
select * from paquetes;
SELECT * FROM movimientos_paquete;
select * from solicitudes_envio;


