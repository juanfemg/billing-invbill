package co.com.juan.invbill.model;
// Generated 22-feb-2017 23:43:04 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * StockProducto generated by hbm2java
 */
public class StockProducto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idStockProducto;
	private Producto producto;
	private Integer stock;
	private Integer precioCompra;
	private Integer precioVenta;
	private Date fechaCreacion;

	public StockProducto() {
	}

	public StockProducto(Integer idStockProducto, Producto producto,
			Integer stock) {
		this.idStockProducto = idStockProducto;
		this.producto = producto;
		this.stock = stock;
	}

	public StockProducto(Integer idStockProducto, Producto producto,
			Integer stock, Integer precioCompra, Integer precioVenta,
			Date fechaCreacion) {
		this.idStockProducto = idStockProducto;
		this.producto = producto;
		this.stock = stock;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdStockProducto() {
		return this.idStockProducto;
	}

	public void setIdStockProducto(Integer idStockProducto) {
		this.idStockProducto = idStockProducto;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getPrecioCompra() {
		return this.precioCompra;
	}

	public void setPrecioCompra(Integer precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Integer getPrecioVenta() {
		return this.precioVenta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
