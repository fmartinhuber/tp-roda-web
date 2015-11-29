package delegate;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.naming.CommunicationException;

import utils.ItemDto;
import dto.ClienteDto;
import dto.CotizacionDto;
import dto.RodamientoDto;
import dto.SolicitudCompraDto;
import interfaces.IAdministracionCC;
import interfaces.IAdministracionOV;

public class Delegado {
	
	private static Delegado instancia;
	private IAdministracionOV manejoDeDatosOV;
	private IAdministracionCC manejoDeDatosCC;
	
	private Delegado() throws CommunicationException{
	
		try {
			conexionRemota();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static Delegado getInstancia() throws CommunicationException, MalformedURLException, NotBoundException
	{
		if(instancia == null)
			instancia = new Delegado();
		return instancia;
	}

	private void conexionRemota() throws CommunicationException, MalformedURLException, NotBoundException
	{
		try {
			manejoDeDatosOV = (IAdministracionOV) Naming.lookup("//localhost:1099/SistemaRodamientoOV");
			manejoDeDatosCC = (IAdministracionCC) Naming.lookup("//localhost:1098/SistemaRodamientoCC");
			System.out.println("Se conecto correctamente con el servidor");
			System.out.println("========================================");
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			throw new CommunicationException("No se pudo conectar con el Server");
			
		}
	}
	
	//Aca van los metodos
	

	public int crearCotizacion(List <ItemDto> listaItems,ClienteDto cliente){
		try{
			return manejoDeDatosOV.crearCotizacion(listaItems, cliente);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public void aprobarCotizacion(int miCotDto){
		try{
			manejoDeDatosOV.aprobarYCotizarCotizacion(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	public void generarFactura(List <CotizacionDto> cotizaciones, ClienteDto cliente){
		try{
			manejoDeDatosOV.generarFactura(cotizaciones, cliente);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	public void crearSolicitudCompra(List <CotizacionDto> cotizacionesAprobadas){
		try{
			manejoDeDatosOV.crearSolicitudCompra(cotizacionesAprobadas);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	public void crearOrdenCompra(List <SolicitudCompraDto> solicitudesPendientes){
		try{
			manejoDeDatosOV.crearOrdenCompra(solicitudesPendientes);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	public List <CotizacionDto> obtenerCotizacionesAprobadas(){
		try{
			return manejoDeDatosOV.obtenerCotizacionesAprobadas();
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
