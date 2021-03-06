package delegate;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import utils.ItemDto;
import dto.*;
import interfaces.IAdministracionCC;
import interfaces.IAdministracionOV;

public class Delegado {

	private static Delegado instancia;
	private IAdministracionOV manejoDeDatosOV;
	private IAdministracionCC manejoDeDatosCC;

	private Delegado(){
		conexionRemota();
	}

	public static Delegado getInstancia()
	{
		if(instancia == null)
			instancia = new Delegado();
		return instancia;
	}

	private void conexionRemota() 
	{
		try {
			//			manejoDeDatosOV = (IAdministracionOV) Naming.lookup("//192.168.1.118:1099/SistemaRodamientoOV");
			//			manejoDeDatosCC = (IAdministracionCC) Naming.lookup("//192.168.1.118:1099/SistemaRodamientoCC");
			manejoDeDatosOV = (IAdministracionOV) Naming.lookup("//localhost/SistemaRodamientoOV");
			manejoDeDatosCC = (IAdministracionCC) Naming.lookup("//localhost/SistemaRodamientoCC");
			System.out.println("Se conecto correctamente con el servidor");
			System.out.println("========================================");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			System.out.println(e.getMessage());
		}
	}

	//Aca van los metodos


	public CotizacionDto crearCotizacion(List <ItemDto> listaItems,ClienteDto cliente){
		try{
			return manejoDeDatosOV.crearCotizacion(listaItems, cliente);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public List<RodamientoDto> obtenerRodamientos(){
		try {
			return manejoDeDatosOV.obtenerRodamientos();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void aprobarCotizacion(int miCotDto){
		try{
			manejoDeDatosOV.aprobarCotizacion(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}

	public CotizacionDto obtenerCotizacionPorId(int miCotDto){
		try{
			return manejoDeDatosOV.obtenerCotizaciones(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public float cotizarCotizacion(int miCotDto){
		try{
			return manejoDeDatosOV.cotizarCotizacion(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return 0;
	}


	public float obterValorCotizacion(int miCotDto){
		try{
			return manejoDeDatosOV.cotizarCotizacion(miCotDto);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return 0;
	}

	public FacturaDto generarFactura(List <CotizacionDto> cotizaciones, ClienteDto cliente){
		try{
			return manejoDeDatosOV.generarFactura(cotizaciones, cliente);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pasa a ser un proceso Batch.
	 * @param cotizacionesAprobadas
	 * @return
	 */
	@Deprecated
	public SolicitudCompraDto crearSolicitudCompra(List <CotizacionDto> cotizacionesAprobadas){
		try{
			return manejoDeDatosOV.crearSolicitudCompra(cotizacionesAprobadas);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public List <OrdenCompraDto> crearOrdenCompra(List <SolicitudCompraDto> solicitudesPendientes, String formaPago){
		try{
			return manejoDeDatosCC.crearOrdenCompra(solicitudesPendientes, formaPago);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
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

	//public int crearRemito(List <OrdenCompraDto> listaOrdenes, ProveedorDto proveedor){
	public RemitoDto crearRemito(List <OrdenCompraDto> listaOrdenes){
		try{
			//return manejoDeDatosCC.crearRemito(listaOrdenes, proveedor);
			return manejoDeDatosOV.crearRemito(listaOrdenes);
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public ClienteDto obtenerUsuarioLogueado(String usuario, String contrasena){
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

	public List <OrdenCompraDto> obtenerOrdenesCompra(){
		try{
			return manejoDeDatosCC.obtenerOrdenesCompra();
		} catch(RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public void aprobarOrdenesCompra(int nroOrdenCompra){
		try{
			manejoDeDatosCC.aprobarOrdenCompra(nroOrdenCompra);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}


	public void entregarPedidos(RemitoDto remito){
		try{
			manejoDeDatosOV.entregaPedidos(remito);
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}

}
