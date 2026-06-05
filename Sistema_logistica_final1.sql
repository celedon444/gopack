drop database if exists sistema_logistica;
create database sistema_logistica;
use sistema_logistica;

-- =========================================
-- TABLA USUARIOS
-- =========================================
create table usuarios (
    id_usuario int auto_increment primary key,
    cedula varchar(20) unique not null,
    nombre varchar(100),
    username varchar(100) not null unique,
    password varchar(255) not null,
    rol enum('ADMIN', 'USER') not null default 'USER',
    fecha_creacion timestamp default current_timestamp
);

-- =========================================
-- TABLA REPORTES
-- =========================================
create table reportes (
    id_reporte int auto_increment primary key,
    guia_paquete varchar(20) not null,
    motivo varchar(900) not null,
    descripcion varchar(300) not null,
    ruta_evidencia varchar(250),
    fecha_registro timestamp default current_timestamp,
    estado enum('Pendiente', 'En Revisión', 'Resuelto') default 'Pendiente'
);

-- =========================================
-- TABLA AUDITORIA
-- =========================================
create table auditoria_usuarios (
    id_log int auto_increment primary key,
    id_usuario_afectado int,
    username_afectado varchar(100),
    accion varchar(50),
    detalle text,
    fecha_cambio timestamp default current_timestamp
);

-- =========================================
-- TABLA PAQUETES
-- =========================================
create table paquetes (
    id_paquete int auto_increment primary key,
    guia_rastreo varchar(20) unique not null,
    ciudad_origen varchar(100),
    ciudad_destino varchar(100),
    remitente varchar(100) not null,
    destinatario varchar(100) not null,
    direccion_entrega text,
    peso double not null,
    costo_envio double,
    tipo_envio varchar(50),
    estado varchar(50) default 'En Bodega',
    ubicacion_actual varchar(100),
    fecha_registro timestamp default current_timestamp
);

-- =========================================
-- TABLA SOLICITUDES
-- =========================================
create table solicitudes_envio (
    id_solicitud int auto_increment primary key,
    guia varchar(20),
    remitente varchar(100) not null,
    destinatario varchar(100) not null,
    ciudad_origen varchar(100),
    ciudad_destino varchar(100),
    direccion_entrega text,
    peso double not null,
    costo_envio double,
    tipo_envio varchar(50),
    estado varchar(50) default 'Pendiente',
    fecha_registro timestamp default current_timestamp
);

-- =========================================
-- TABLA MOVIMIENTOS
-- =========================================
create table movimientos_paquete (
    id_movimiento int auto_increment primary key,
    guia_rastreo varchar(20),
    estado varchar(50),
    ubicacion varchar(100),
    descripcion varchar(255),
    fecha_movimiento timestamp default current_timestamp,
    foreign key (guia_rastreo)
    references paquetes(guia_rastreo)
);

-- =========================================
-- TABLA PAGOS
-- =========================================
create table pagos (
    id_pago int auto_increment primary key,
    guia_rastreo varchar(20) not null,
    monto double not null,
    metodo_pago varchar(50),
    estado_pago enum('Pendiente', 'Pagado', 'Rechazado')
    default 'Pendiente',
    fecha_pago timestamp default current_timestamp,
    foreign key (guia_rastreo)
    references paquetes(guia_rastreo)
);

-- =========================================
-- PROCEDIMIENTOS ALMACENADOS
-- =========================================

delimiter //

-- drop procedure sp_validar_login //

create procedure sp_validar_login(
    in p_username varchar(100),
    in p_password varchar(255)
)
begin
    select cedula,nombre,rol
    from usuarios
    where binary username = p_username
    and binary password = p_password;
end //

-- drop procedure insertar_reporte //

create procedure insertar_reporte(in r_guia_paquete varchar(50),in r_motivo varchar(900),in r_descripcion varchar(300),in r_ruta_evidencia varchar(250))
begin
    insert into reportes(guia_paquete,motivo,descripcion,ruta_evidencia)values(r_guia_paquete,r_motivo,r_descripcion,r_ruta_evidencia);
end //

-- drop procedure sp_insertar_usuario //

create procedure sp_insertar_usuario(in u_cedula varchar(20),in u_nombre varchar(100),in u_username varchar(100),in u_password varchar(255))
begin
    insert into usuarios(cedula,nombre,username,password,rol) values (u_cedula,u_nombre,u_username,u_password,'USER');
end //

-- drop procedure sp_registrar_paquete //

create procedure sp_registrar_paquete(
    in p_guia varchar(20),
    in p_ciudad_origen varchar(100),
    in p_ciudad_destino varchar(100),
    in p_remitente varchar(100),
    in p_destinatario varchar(100),
    in p_direccion text,
    in p_peso double,
    in p_fecha_form varchar(50),
    in p_tipo varchar(50),
    in p_estado varchar(50),
    in p_ubicacion_actual varchar(100)
)
begin
    insert into paquetes
    (guia_rastreo,ciudad_origen,ciudad_destino,remitente,destinatario,direccion_entrega,peso,fecha_registro,tipo_envio,estado,ubicacion_actual)
    values(p_guia,p_ciudad_origen,p_ciudad_destino,p_remitente,p_destinatario,p_direccion,p_peso,p_fecha_form,p_tipo,p_estado,p_ubicacion_actual);
end //

-- =========================================
-- FUNCION
-- =========================================

-- drop function fn_calcular_costo //

create function fn_calcular_costo(p_peso double)
returns double
deterministic
begin

    declare costo double;

    if p_peso <= 5 then
        set costo = 25000;
    elseif p_peso <= 10 then
        set costo = 30000;
    else
        set costo = 20000;
    end if;

    return costo;

end //

-- =========================================
-- TRIGGER ORIGINAL
-- =========================================

-- drop trigger tr_auditoria_usuarios //

create trigger tr_auditoria_usuarios
after update on usuarios
for each row
begin

    if old.rol <> new.rol
       or old.username <> new.username then

        insert into auditoria_usuarios(id_usuario_afectado,username_afectado,accion,detalle)
        values(new.id_usuario,new.username,'MODIFICACION',concat('Cambio de: ',old.username,' a: ',new.username)
        );

    end if;

end //

-- =========================================
-- TRIGGER NUEVO
-- =========================================

-- drop trigger tr_movimiento_inicial //

create trigger tr_movimiento_inicial
after insert on paquetes
for each row
begin

    insert into movimientos_paquete(guia_rastreo,estado,ubicacion,descripcion)values(new.guia_rastreo,new.estado,new.ubicacion_actual,'Registro inicial del paquete');

end //

delimiter ;

-- =========================================
-- VISTAS
-- =========================================

drop view if exists vista_rastreo_paquetes;

create view vista_rastreo_paquetes AS
select
    guia_rastreo,
    remitente,
    destinatario,
    ciudad_origen,
    ciudad_destino,
    estado,
    ubicacion_actual
from paquetes;

drop view if exists vista_pagos;

create view vista_pagos AS
select
    p.guia_rastreo,
    p.remitente,
    p.destinatario,
    pa.monto,
    pa.metodo_pago,
    pa.estado_pago
from paquetes p
inner join pagos pa
on p.guia_rastreo = pa.guia_rastreo;

-- =========================================
-- DATOS DE PRUEBA
-- =========================================

insert into usuarios(cedula,nombre,username,password,rol)values('12345678','Camilo','12345678','123','ADMIN');
insert into usuarios(cedula,nombre,username,password,rol)values('98765432','Juan Perez','juan','123','USER');

insert into paquetes(guia_rastreo,ciudad_origen,ciudad_destino,remitente,destinatario,direccion_entrega,peso,costo_envio,tipo_envio,estado,ubicacion_actual)
values('PK001','Santa Marta','Bogota','Carlos Perez','Juan Gomez','Calle 10 #15-20',5,fn_calcular_costo(5),'Documentos','En Bodega','Santa Marta');

