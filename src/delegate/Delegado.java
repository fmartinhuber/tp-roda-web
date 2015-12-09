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
import dto.OrdenCompraDto;
import dto.ProveedorDto;
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
			manejoDeDatosCC = (IAdministracionCC) Naming.lookup("//localhost:1099/SistemaRodamientoCC");
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
			manejoDeDatosOV.aprobarCotizacion(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	//TODO hay algo para esto??
//	public float obterValorCotizacion(int miCotDto){
//		try{
//			return manejoDeDatosOV.aprobarCotizacion(miCotDto);
//		} catch(RemoteException e){
//			e.printStackTrace();
//		}
//		return 0;
//	}
	
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
	
	public int crearOrdenCompra(List <SolicitudCompraDto> solicitudesPendientes, String formaPago){
		try{
			return manejoDeDatosCC.crearOrdenCompra(solicitudesPendientes, formaPago);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public List <CotizacionDto> obtenerCotizacionesAprobadas(){
		try{
			return manejoDeDatosOV.obtenerCotizacionesAprobadas();
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List <CotizacionDto> obtenerCotizaciones(){
		try{
			return manejoDeDatosOV.obtenerCotizaciones();
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int crearRemito(List <OrdenCompraDto> listaOrdenes, ProveedorDto proveedor){
		try{
			return manejoDeDatosCC.crearRemito(listaOrdenes, proveedor);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public ClienteDto obtenerUsuario(String usuario, String contrasena){
		try{
			return manejoDeDatosOV.obtenerUsuario(usuario, contrasena);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ClienteDto obtenerUsuarioLogueado(){
		try{
			return manejoDeDatosOV.obtenerUsuarioLogueado();
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
