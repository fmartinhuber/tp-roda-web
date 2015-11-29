package servlet;

import interfaces.IAdministracionOV;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.CommunicationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.ItemDto;
import delegate.Delegado;
import dto.ClienteDto;
import dto.CotizacionDto;
import dto.RodamientoDto;

/**
 * Servlet implementation class RodamientoServlet
 */
@WebServlet("/CotizacionServlet")
public class CotizacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CotizacionServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("metodo:" + request.getMethod());
		System.out.println("metodo:" + request.getParameter("metodo"));
		if(request.getParameter("metodo").equals("generarCotizacion")){
			generarCotizaciones(request,response);
		}else if(request.getParameter("metodo").equals("aprobarCotizacion")){
			aprobarCotizaciones(request,response);
		}else if(request.getParameter("metodo").equals("buscarCotizaciones")){
			buscarCotizaciones(request,response);
		}else{
			try {
				throw new Exception("No se encontro el metodo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void generarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try {
			ClienteDto cliente = new ClienteDto();
			cliente.setCUIT(request.getParameter("cuit"));
			cliente.setRazonSocial(request.getParameter("razonSocial"));
			
			List <ItemDto> listaItems = new ArrayList<ItemDto>();
	
			JSONArray jObj = new JSONArray(request.getParameter("listaRodamiento")); // this parses the json
			
			
			for (int i = 0; i < jObj.length(); i++) {
				JSONObject objeto = jObj.getJSONObject(i);
				String codigo = objeto.getString("codigo");
				String marca = objeto.getString("marca");
				String pais = objeto.getString("pais");
				String caracteristica = objeto.getString("caracteristica");
				String cantidad = objeto.getString("cantidad");
				ItemDto item = new ItemDto();
				
				RodamientoDto rodamiento = new RodamientoDto();
				rodamiento.setCodigo(codigo);
				rodamiento.setMarca(marca);
				rodamiento.setOrigen(pais);
				rodamiento.setCaracteristica(caracteristica);
				
				item.setRodamiento(rodamiento);
				item.setCantidad(Integer.valueOf(cantidad));
				listaItems.add(item);
			}
			
			try {
				int nroCotizacion = Delegado.getInstancia().crearCotizacion(listaItems, cliente);
				request.setAttribute("nroCotizacion", nroCotizacion);
			} catch (CommunicationException | NotBoundException e) {
				e.printStackTrace();
			}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cotizacion.jsp");
			dispatcher.forward(request,response);
		
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
	}
	
	protected void buscarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//Buscar cotizaciones aprobadas.
//		ClienteNegocio clienteNegocio = new ClienteNegocio();
//		clienteNegocio.setCUIT(request.getParameter("cuit"));
//		
//		List <CotizacionNegocio> cotizaciones = AdministracionOV.getInstancia().obtenerCotizacionesDeCiente(clienteNegocio);
//		
//		String [] arrayCotizaciones = new String [cotizaciones.size()];
//		
//		for (int i = 0; i < arrayCotizaciones.length; i++) {
//			arrayCotizaciones[i] = String.valueOf(cotizaciones.get(i).getIdCotizacion());
//		}
//		request.setAttribute("arrayCotizaciones", arrayCotizaciones);
//		
//		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/aprobarCotizacion.jsp");
//		dispatcher.forward(request,response);
//		
		
	
	}
	
	protected void aprobarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Buscar cotizaciones aprobadas.
		ClienteDto clienteNegocio = new ClienteDto();
		clienteNegocio.setCUIT(request.getParameter("cuit"));
		
		CotizacionDto cotizacion = new CotizacionDto();
		
		//cotizacion.setIdCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
		//cotizacion.setNumeroCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
		System.out.println("cotizacionSeleccionada: "+request.getParameter("cotizacionSeleccionada"));
		try {
			Delegado.getInstancia().aprobarCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
		} catch (CommunicationException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//AdministracionOV.getInstancia().aprobarYCotizarCotizacion(cotizacion);	
	
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/aprobarCotizacion.jsp");
		dispatcher.forward(request,response);
	}

}
