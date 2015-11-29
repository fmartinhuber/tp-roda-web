package dto;

import java.io.Serializable;
import java.util.*;

import utils.ItemDto;



public class BultoDto  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int numeroBulto;
	private List <ItemDto> rodamientos;
	private ClienteDto cliente;
	private RemitoDto remito;
	
	public BultoDto() {
		
	}

	public BultoDto(int numeroBulto, List<ItemDto> rodamientos,
			ClienteDto cliente, RemitoDto remito) {
		super();
		this.numeroBulto = numeroBulto;
		this.rodamientos = rodamientos;
		this.cliente = cliente;
		this.remito = remito;
	}

	public int getNumeroBulto() {
		return numeroBulto;
	}

	public void setNumeroBulto(int numeroBulto) {
		this.numeroBulto = numeroBulto;
	}

	public List<ItemDto> getRodamientos() {
		return rodamientos;
	}

	public void setRodamientos(List<ItemDto> rodamientos) {
		this.rodamientos = rodamientos;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public RemitoDto getRemito() {
		return remito;
	}

	public void setRemito(RemitoDto remito) {
		this.remito = remito;
	}

}
