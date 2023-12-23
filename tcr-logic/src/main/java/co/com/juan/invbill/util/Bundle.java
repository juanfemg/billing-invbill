package co.com.juan.invbill.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Bundle {

    public static final String ID_DIALOG_MESSAGES = "menMod";
    public static String MSG_ERROR_CONSULTA_CATEGORIAS = "MSG_ERROR_CONSULTA_CATEGORIAS";
    public static String ERROR_CONSULTA_CATEGORIAS = "=== Getting categories: An error has occurred getting categories";
    public static String MSG_CATEGORIA_ACTUALIZADA = "MSG_CATEGORIA_ACTUALIZADA";
    public static String MSG_ERROR_ACTUALIZACION_CATEGORIA = "MSG_ERROR_ACTUALIZACION_CATEGORIA";
    public static String ERROR_ACTUALIZACION_CATEGORIA = "=== Update a category: An error has occurred updating the category with id: {}. Error: {}";
    public static String MSG_ERROR_CONSULTA_PROVEEDORES = "MSG_ERROR_CONSULTA_PROVEEDORES";
    public static String ERROR_CONSULTA_PROVEEDORES = "=== Getting suppliers: An error has occurred getting suppliers";
    public static String MSG_PROVEEDOR_ACTUALIZADO = "MSG_PROVEEDOR_ACTUALIZADO";
    public static String MSG_ERROR_ACTUALIZACION_PROVEEDOR = "MSG_ERROR_ACTUALIZACION_PROVEEDOR";
    public static String ERROR_ACTUALIZACION_PROVEEDOR = "=== Update a supplier: An error has occurred updating the supplier with id: {}. Error: {}";
    public static String MSG_ERROR_CONSULTA_PRODUCTOS = "MSG_ERROR_CONSULTA_PRODUCTOS";
    public static String ERROR_CONSULTA_PRODUCTOS = "=== Getting products: An error has occurred getting products";
    public static String MSG_MODIFICACION_CATEGORIAS_INACTIVAS = "MSG_MODIFICACION_CATEGORIAS_INACTIVAS";
    public static String MSG_MODIFICACION_UNIDADES_MEDIDA_INACTIVAS = "MSG_MODIFICACION_UNIDADES_MEDIDA_INACTIVAS";
    public static String MSG_ERROR_CONSULTA_UNIDADES_MEDIDA = "MSG_ERROR_CONSULTA_UNIDADES_MEDIDA";
    public static String ERROR_CONSULTA_UNIDADES_MEDIDA = "=== Getting measuring units: An error has occurred getting measuring units";
    public static String MSG_PRODUCTO_ACTUALIZADO = "MSG_PRODUCTO_ACTUALIZADO";
    public static String MSG_ERROR_ACTUALIZACION_PRODUCTO = "MSG_ERROR_ACTUALIZACION_PRODUCTO";
    public static String ERROR_ACTUALIZACION_PRODUCTO = "=== Update a product: An error has occurred updating the product with id: {}. Error: {}";
    public static String MSG_USUARIO_ACTUALIZADO = "MSG_USUARIO_ACTUALIZADO";
    public static String MSG_ERROR_ACTUALIZACION_USUARIO = "MSG_ERROR_ACTUALIZACION_USUARIO";
    public static String ERROR_ACTUALIZACION_USUARIO = "=== Update a user: An error has occurred updating the user with id: {}. Error: {}";
    public static String MSG_ERROR_CONSULTA_USUARIO = "MSG_ERROR_CONSULTA_USUARIO";
    public static String ERROR_CONSULTA_USUARIO = "=== Fetching a user: An error has occurred fetching the user with id: {}. Error: {}";
    public static String MSG_DATA_NO_FOUND = "MSG_DATA_NO_FOUND";
    public static String ERROR_CREACION_LOGIN = "=== Save a login: An error has occurred saving the login with id: {}. Error: {}";
    public static String ERROR_ACTUALIZACION_LOGIN = "=== Update login: An error has occurred updating the login with id: {}. Error: {}";
    public static String ERROR_CONSULTA_PARAMETROS = "=== Getting configuration params: An error has occurred getting configuration params";
    public static String MSG_USER_NO_FOUND = "MSG_USER_NO_FOUND";
    public static String ERROR_CREDENCIALES_INCORRECTAS = "=== Fetching a user: An error has occurred fetching the user with id: {} or credentials are wrong";
    public static String ERROR_CONSULTA_MENUES = "=== Getting menus: An error has occurred getting menus";
    public static String MSG_ERROR_CONSULTA_IMPRESORAS = "MSG_ERROR_CONSULTA_IMPRESORAS";
    public static String ERROR_CONSULTA_IMPRESORAS = "=== Getting printers: An error has occurred getting printers";
    public static String MSG_CONFIGURACION_IMPRESORA_ACTUALIZADA = "MSG_CONFIGURACION_IMPRESORA_ACTUALIZADA";
    public static String MSG_ERROR_ACTUALIZACION_IMPRESORA = "MSG_ERROR_ACTUALIZACION_IMPRESORA";
    public static String MSG_CONFIGURACION_IVA_ACTUALIZADA = "MSG_CONFIGURACION_IVA_ACTUALIZADA";
    public static String MSG_ERROR_ACTUALIZACION_IVA = "MSG_ERROR_ACTUALIZACION_IVA";
    public static String MSG_CONFIGURACION_TOPE_STOCK_ACTUALIZADA = "MSG_CONFIGURACION_TOPE_STOCK_ACTUALIZADA";
    public static String MSG_ERROR_ACTUALIZACION_TOPE_STOCK = "MSG_ERROR_ACTUALIZACION_TOPE_STOCK";
    public static String ERROR_ACTUALIZACION_CONFIGURACION = "=== Update configuration: An error has occurred updating the configuration with id {}. Error: {}";
    public static String MSG_USUARIO_CREADO = "MSG_USUARIO_CREADO";
    public static String MSG_ERROR_CREACION_USUARIO = "MSG_ERROR_CREACION_USUARIO";
    public static String ERROR_CREACION_USUARIO = "=== Save user: An error has occurred creating the user with id {}. Error: {}";
    public static String MSG_CATEGORIA_CREADA = "MSG_CATEGORIA_CREADA";
    public static String MSG_ERROR_CREACION_CATEGORIA = "MSG_ERROR_CREACION_CATEGORIA";
    public static String ERROR_CREACION_CATEGORIA = "=== Save category: An error has occurred creating the category with name {}. Error: {}";
    public static String MSG_CATEGORIAS_INACTIVAS = "MSG_CATEGORIAS_INACTIVAS";
    public static String MSG_UNIDADES_MEDIDA_INACTIVAS = "MSG_UNIDADES_MEDIDA_INACTIVAS";
    public static String MSG_ERROR_CREACION_PROVEEDOR = "MSG_ERROR_CREACION_PROVEEDOR";
    public static String MSG_PRODUCTO_CREADO = "MSG_PRODUCTO_CREADO";
    public static String MSG_ERROR_CREACION_PRODUCTO = "MSG_ERROR_CREACION_PRODUCTO";
    public static String ERROR_CREACION_PRODUCTO = "=== Save product: An error has occurred creating the product with name {}. Error: {}";
    public static String MSG_PROVEEDOR_CREADO = "MSG_PROVEEDOR_CREADO";
    public static String ERROR_CREACION_PROVEEDOR = "=== Save supplier: An error has occurred creating the supplier with name {}. Error: {}";
    public static String MSG_ERROR_STOCK_PRODUCTOS = "MSG_ERROR_STOCK_PRODUCTOS";
    public static String ERROR_STOCK_PRODUCTOS = "=== Getting products stock: An error has occurred getting products stock";
    public static String MSG_ERROR_HISTORICO_COMPRA = "MSG_ERROR_HISTORICO_COMPRA";
    public static String ERROR_HISTORICO_COMPRA = "=== Getting sales history: An error has occurred getting sales history";
    public static String MSG_STOCK_PRODUCTO_ACTUALIZADO = "MSG_STOCK_PRODUCTO_ACTUALIZADO";
    public static String MSG_ERROR_ACTUALIZACION_STOCK_PRODUCTO = "MSG_ERROR_ACTUALIZACION_STOCK_PRODUCTO";
    public static String ERROR_ACTUALIZACION_STOCK_PRODUCTO = "=== Update product stock: An error has occurred updating the product stock with id {}. Error: {}";
    private FacesMessage message;

    protected void addInfoMessage(String summary) {
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    protected void addErrorMessage(String summary, String... clientId) {
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(clientId == null ? null : ID_DIALOG_MESSAGES, message);
    }

    protected void addWarnMessage(String summary) {
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
