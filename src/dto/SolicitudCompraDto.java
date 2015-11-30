package dto;

import java.io.Serializable;
import java.util.List;

public class SolicitudCompraDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2561420590604026077L;
	private int numeroSolicitudCompra;
	private String estado;
	private List <CotizacionDto> listaCotizaciones;
	
	public SolicitudCompraDto(int numeroSolicitudCompra, String estado, List<CotizacionDto> listaCotizaciones) {
		this.numeroSolicitudCompra = numeroSolicitudCompra;
		this.estado = estado;
		this.listaCotizaciones = listaCotizaciones;
	}

	public SolicitudCompraDto() {
	}

	public int getNumeroSolicitudCompra() {
		return numeroSolicitudCompra;
	}

	public void setNumeroSolicitudCompra(int numeroSolicitudCompra) {
		this.numeroSolicitudCompra = numeroSolicitudCompra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<CotizacionDto> getListaCotizaciones() {
		return listaCotizaciones;
	}

	public void setListaCotizaciones(List<CotizacionDto> listaCotizaciones) {
		this.listaCotizaciones = listaCotizaciones;
	}
	
}
