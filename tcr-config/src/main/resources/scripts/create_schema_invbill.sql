CREATE SCHEMA INVBILL;

SET SCHEMA INVBILL;

CREATE TABLE `app_config` (
  `id_app_config` varchar(50) NOT NULL,
  `valor` varchar(200) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_app_config`)
);

CREATE TABLE `app_menu` (
  `id_app_menu` varchar(50) NOT NULL,
  `valor` varchar(200) NOT NULL,
  `salida` varchar(50) DEFAULT NULL,
  `icono` varchar(50) DEFAULT NULL,
  `id_menu_padre` varchar(50) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `orden` varchar(8) NOT NULL,
  PRIMARY KEY (`id_app_menu`),
  CONSTRAINT `app_menu_parent_fk` FOREIGN KEY (`id_menu_padre`) REFERENCES `app_menu` (`id_app_menu`)
);

CREATE TABLE `categoria_producto` (
  `id_categoria` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `categoria` varchar(50) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_categoria`)
);

CREATE TABLE `cliente_app` (
  `id_cliente_app` int NOT NULL,
  `razon_social` varchar(200) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cod_verificacion` smallint DEFAULT NULL,
  PRIMARY KEY (`id_cliente_app`)
);

CREATE TABLE `factura_cabecera` (
  `id_factura` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `valor_neto` double NOT NULL,
  `valor_iva` double NOT NULL,
  `valor_total` int NOT NULL,
  `usuario_creacion` varchar(20) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_cliente_app` int DEFAULT NULL,
  PRIMARY KEY (`id_factura`),
  CONSTRAINT `factura_cliente_fk` FOREIGN KEY (`id_cliente_app`) REFERENCES `cliente_app` (`id_cliente_app`)
);

CREATE TABLE `devolucion_cabecera` (
  `id_factura` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `valor_neto` double NOT NULL,
  `valor_iva` double NOT NULL,
  `valor_total` int NOT NULL,
  `usuario_creacion` varchar(20) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_factura`),
  CONSTRAINT `facturaCabecera_devolucion_fk` FOREIGN KEY (`id_factura`) REFERENCES `factura_cabecera` (`id_factura`)
);

CREATE TABLE `login_app` (
  `id_login_app` varchar(20) NOT NULL,
  `ultimo_login` timestamp NULL DEFAULT NULL,
  `id_session` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_login_app`)
);

CREATE TABLE `proveedor_app` (
  `id_proveedor_app` int NOT NULL,
  `cod_verificacion` smallint NOT NULL,
  `razon_social` varchar(200) NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `telefono` int DEFAULT NULL,
  `telefono_ext` int DEFAULT NULL,
  `celular` bigint DEFAULT NULL,
  `estado` char(1) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_proveedor_app`)
);

CREATE TABLE `compra_cabecera` (
  `id_proveedor_app` int NOT NULL,
  `id_factura_compra` varchar(20) NOT NULL,
  `valor_neto` double NOT NULL,
  `valor_iva` double NOT NULL,
  `valor_total` int NOT NULL,
  `usuario_creacion` varchar(20) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_factura_compra`,`id_proveedor_app`),
  CONSTRAINT `compra_proveedor_fk` FOREIGN KEY (`id_proveedor_app`) REFERENCES `proveedor_app` (`id_proveedor_app`)
);

CREATE TABLE `rol_app` (
  `id_rol_app` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `rol` varchar(45) NOT NULL,
  `estado` char(1) NOT NULL,
  PRIMARY KEY (`id_rol_app`)
);

CREATE TABLE `tipo_movimiento_stock` (
  `id_movimiento_stock` int NOT NULL,
  `tipo_movimiento` varchar(45) NOT NULL,
  `estado` char(1) NOT NULL,
  PRIMARY KEY (`id_movimiento_stock`)
);

CREATE TABLE `tipo_periodo` (
  `id_periodo` int NOT NULL,
  `periodo` varchar(45) NOT NULL,
  `numero_meses` int NOT NULL,
  `estado` char(1) NOT NULL,
  PRIMARY KEY (`id_periodo`)
);

CREATE TABLE `tipo_unidad_medida` (
  `id_unidad_medida` int NOT NULL,
  `tipo_unidad` varchar(45) NOT NULL,
  `unidad` varchar(45) NOT NULL,
  `estado` char(1) NOT NULL,
  PRIMARY KEY (`id_unidad_medida`)
);

CREATE TABLE `producto` (
  `id_producto` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `id_categoria` int NOT NULL,
  `producto` varchar(50) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_unidad_medida` int NOT NULL DEFAULT '1',
  `medida` double DEFAULT NULL,
  PRIMARY KEY (`id_producto`),
  CONSTRAINT `producto_categoria_fk` FOREIGN KEY (`id_categoria`) REFERENCES `categoria_producto` (`id_categoria`),
  CONSTRAINT `producto_medida_fk` FOREIGN KEY (`id_unidad_medida`) REFERENCES `tipo_unidad_medida` (`id_unidad_medida`)
);

CREATE TABLE `compra_detalle` (
  `id_proveedor_app` int NOT NULL,
  `id_factura_compra` varchar(20) NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_compra` double NOT NULL,
  `valor_iva` double NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_proveedor_app`,`id_factura_compra`,`id_producto`),
  CONSTRAINT `compra_cabecera_fk` FOREIGN KEY (`id_proveedor_app`, `id_factura_compra`) REFERENCES `compra_cabecera` (`id_proveedor_app`, `id_factura_compra`),
  CONSTRAINT `compra_producto_fk` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
);

CREATE TABLE `devolucion_detalle` (
  `id_factura` int NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_venta` double NOT NULL,
  `valor_iva` double NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_factura`,`id_producto`),
  CONSTRAINT `devolucion_cabecera_fk` FOREIGN KEY (`id_factura`) REFERENCES `devolucion_cabecera` (`id_factura`),
  CONSTRAINT `devolucion_producto_fk` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
);

CREATE TABLE `factura_detalle` (
  `id_factura` int NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_venta` double NOT NULL,
  `valor_iva` double NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_factura`,`id_producto`),
  CONSTRAINT `factura_cabecera_fk` FOREIGN KEY (`id_factura`) REFERENCES `factura_cabecera` (`id_factura`),
  CONSTRAINT `factura_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
);

CREATE TABLE `stock_producto` (
  `id_stock_producto` int NOT NULL GENERATED ALWAYS AS IDENTITY,
  `id_producto` int NOT NULL,
  `stock` int DEFAULT '0',
  `precio_compra` int DEFAULT '0',
  `precio_venta` int DEFAULT '0',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_stock_producto`),
  CONSTRAINT `stock_producto_fk` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
);

CREATE TABLE `usuario_app` (
  `id_usuario_app` varchar(20) NOT NULL,
  `id_rol_app` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `password` varchar(45) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario_app`),
  CONSTRAINT `usuario_rol_fk` FOREIGN KEY (`id_rol_app`) REFERENCES `rol_app` (`id_rol_app`)
);

INSERT INTO `app_config` VALUES ('IMPRESORA_PREDETERMINADA','Epson TM-T20II','Configuracion de Impresora Predeterminada','2017-10-21 18:21:09'),('IVA','19','Valor del impuesto a la venta de bienes de consumo','2017-10-21 18:21:09'),('TOPE_STOCK','3','Configuracion de alerta para tope minimo de stock','2018-07-27 19:33:20');

INSERT INTO `tipo_periodo` VALUES (1,'ANUAL',12,'A'),(2,'SEMESTRAL',6,'A'),(3,'TRIMESTRAL',3,'A'),(4,'MENSUAL',1,'A');

INSERT INTO `tipo_unidad_medida` VALUES (1,'UNIDAD','UNIDAD','A'),(2,'MASA','KILOGRAMO','A'),(3,'VOLUMEN','LITRO','A'),(4,'LONGITUD','METRO','A');

INSERT INTO `rol_app` VALUES (1,'ADMINISTRADOR','A');

INSERT INTO `usuario_app` VALUES ('ADMIN',1,'KELLY MOSQUERA','4Zas7qfa+pk=','A','2017-02-08 03:23:54');

INSERT INTO `app_menu` VALUES ('menuInicio','Inicio',NULL,NULL,NULL,'2018-01-06 21:57:39','1'),('menuItemPrincipal','Principal','dashboard','fa fa-home','menuInicio','2018-01-06 22:50:33','1.1'),('menuItemConfiguracion','Configuración','goConfiguracion','fa fa-cogs','menuInicio','2018-01-06 22:50:33','1.2'),('menuAdministracion','Administración',NULL,NULL,NULL,'2018-01-06 22:53:47','2'),('subMenuUsuario','Usuario',NULL,NULL,'menuAdministracion','2018-01-06 22:53:47','2.1'),('menuItemCrearUsuario','Crear Usuario','goCrearUsuario','fa fa-user','subMenuUsuario','2018-01-06 22:53:47','2.1.1'),('menuItemConsultarUsuario','Consultar Usuario','goConsultarUsuario','fa fa-search','subMenuUsuario','2018-01-06 22:53:47','2.1.2'),('subMenuCategoria','Categoría',NULL,NULL,'menuAdministracion','2018-01-06 22:53:47','2.2'),('menuItemCrearCategoria','Crear Categoría','goCrearCategoria','fa fa-cubes','subMenuCategoria','2018-01-06 22:53:47','2.2.1'),('menuItemConsultarCategoria','Consultar Categoría','goConsultarCategoria','fa fa-search','subMenuCategoria','2018-01-06 22:53:47','2.2.2'),('subMenuProducto','Producto',NULL,NULL,'menuAdministracion','2018-01-06 22:53:47','2.3'),('menuItemCrearProducto','Crear Producto','goCrearProducto','fa fa-magnet','subMenuProducto','2018-01-06 22:53:47','2.3.1'),('menuItemConsultarProducto','Consultar Producto','goConsultarProducto','fa fa-search','subMenuProducto','2018-01-06 22:53:47','2.3.2'),('subMenuProveedor','Proveedor',NULL,NULL,'menuAdministracion','2018-01-06 22:53:47','2.4'),('menuItemCrearProveddor','Crear Proveedor','goCrearProveedor','fa fa-users','subMenuProveedor','2018-01-06 22:53:47','2.4.1'),('menuItemConsultarProveedor','Consultar Proveedor','goConsultarProveedor','fa fa-search','subMenuProveedor','2018-01-06 22:53:47','2.4.2'),('menuInventario','Inventario',NULL,NULL,NULL,'2018-01-06 22:53:47','3'),('menuItemGestionProductos','Gestión de Productos','goGestionProductos','fa fa-tasks','menuInventario','2018-01-06 22:53:47','3.1'),('menuVentas','Ventas',NULL,NULL,NULL,'2018-01-06 22:53:47','4'),('menuItemCrearFactura','Crear Factura','goCrearFactura','fa fa-file-text-o','menuVentas','2018-01-06 22:53:47','4.1'),('menuItemConsultarFactura','Consultar Factura','goConsultarFactura','fa fa-search','menuVentas','2018-01-06 22:53:47','4.2'),('menuItemRegistrarDevolucion','Registrar Devolución','goRegistrarDevolucion','fa fa-reply-all','menuVentas','2018-01-06 22:53:47','4.3'),('menuItemConsultarDevolucion','Consultar Devolución','goConsultarDevolucion','fa fa-search','menuVentas','2018-01-06 22:53:47','4.4'),('menuCompras','Compras',NULL,NULL,NULL,'2018-01-06 22:53:47','5'),('menuItemRegistrarCompra','Registrar Compra','goRegistrarCompra','fa fa-book','menuCompras','2018-01-06 22:53:47','5.1'),('menuItemConsultarCompra','Consultar Compra','goConsultarCompra','fa fa-search','menuCompras','2018-01-06 22:53:47','5.2'),('menuReportes','Reportes',NULL,NULL,NULL,'2018-11-10 05:00:00','6'),('menuItemProductos','Productos','goReporteProductos','fa fa-file-text-o','menuReportes','2018-11-10 05:00:00','6.1'),('menuItemVentas','Ventas','goReporteVentas','fa fa-file-text-o','menuReportes','2019-07-10 05:00:00','6.2');