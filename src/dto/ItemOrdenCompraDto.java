package dto;

import java.io.Serializable;



public class ItemOrdenCompraDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private RodamientoDto rodamiento;
	private float monto;
	private int cantidad;
	
	public ItemOrdenCompraDto(RodamientoDto rodamiento, float monto,
			int cantidad) {
		this.rodamiento = rodamiento;
		this.monto = monto;
		this.cantidad = cantidad;
	}
	
	public ItemOrdenCompraDto() {

	}

	public RodamientoDto getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(RodamientoDto rodamiento) {
		this.rodamiento = rodamiento;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
