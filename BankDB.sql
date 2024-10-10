create database BancoBD;

use BancoBD;

create table CLIENTE(
nombre varchar(50),
direccion varchar(100),
correo varchar(50),
dpi varchar(20),
telefono varchar (20),
nit_cliente varchar(20) PRIMARY KEY
)

create table TIPO_CUENTA(
id_tipo_cuenta int PRIMARY KEY, 
descripcion varchar(50)
)

create table CUENTA(
id_cuenta int IDENTITY (1,1) PRIMARY KEY,
id_tipo_cuenta int,
saldo float,
margen float,
nit_cliente varchar(20)
constraint fk_nit_cliente foreign key (nit_cliente) references CLIENTE(nit_cliente),
constraint fk_id_tipo_cuenta foreign key (id_tipo_cuenta) references TIPO_CUENTA(id_tipo_cuenta)
)
  
create table TIPO_TRANSACCION(
id_tipo_transaccion int IDENTITY (1,1) PRIMARY KEY,
descripcion varchar (50)
)
  
create table EMPLEADO(
id_empleado int IDENTITY(1,1) PRIMARY KEY,
nombre varchar(50),
puesto varchar(50),
correo varchar (50),
telefono varchar (20)
)

create table SUCURSAL(
id_sucursal int IDENTITY (1,1) PRIMARY KEY,
nombre varchar (50),
direccion varchar (100),
telefono varchar (20),
horario varchar (50)
)

create table TRANSACCION (
id_transaccion int IDENTITY (1,1) PRIMARY KEY,
fecha date,
hora time,
id_empleado int,
id_cuenta int,
id_sucursal int
constraint fk_id_empleado foreign key (id_empleado) references EMPLEADO(id_empleado),
constraint fk_cuenta foreign key (id_cuenta) references CUENTA(id_cuenta),
constraint fk_id_sucursal foreign key (id_sucursal) references SUCURSAL(id_sucursal)
)

create table DETALLE_TRANSACCION(
id_detalle int IDENTITY (1,1) PRIMARY KEY,
id_transaccion int,
id_tipo int,
monto float,
constraint fk_id_transaccion foreign key (id_transaccion) references TRANSACCION(id_transaccion),
constraint fk_id_tipo foreign key (id_tipo) references TIPO_TRANSACCION(id_tipo_transaccion)
)
