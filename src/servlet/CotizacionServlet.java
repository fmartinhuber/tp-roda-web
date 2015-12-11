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
import dto.ItemCotizacionDto;
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
		}else if(request.getParameter("metodo").equals("buscarCotizacion")){
			buscarCotizacion(request,response);
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
			
			
			List <ItemDto> listaItems = new ArrayList<ItemDto>();
	
			JSONArray jObj = new JSONArray(request.getParameter("listaRodamiento")); // this parses the json
			System.out.println("listaRodamiento:" +request.getParameter("listaRodamiento"));
			
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
				
				ClienteDto cliente = Delegado.getInstancia().obtenerUsuarioLogueado();
				
				CotizacionDto cotizacionDto = Delegado.getInstancia().crearCotizacion(listaItems, cliente);
				float total = Delegado.getInstancia().cotizarCotizacion(cotizacionDto.getNumeroCotizacion());
				
				response.getWriter().print("<p> Se creo la Cotizacion numero :  <b><u>" + cotizacionDto.getNumeroCotizacion() + "</u></b></p>");
				response.getWriter().print("<p> Estado :  " + cotizacionDto.getEstado() + "</p>");
				response.getWriter().print("<p> Cliente :  " + cotizacionDto.getCliente().getRazonSocial() + "</p>");
				response.getWriter().print("<p> Fecha Creacion :  " + cotizacionDto.getFechaCreacion() + "</p>");
				
				for (int i = 0; i < cotizacionDto.getItems().size(); i++) {
					ItemCotizacionDto item = cotizacionDto.getItems().get(i);
					response.getWriter().print("<p> RODAMIENTO </p> ");
					response.getWriter().print("<p> Carateristica :  " + item.getRodamiento().getCaracteristica() + " Codigo: " + item.getRodamiento().getCodigo() +  "</p>");
					response.getWriter().print("<p> Valor :  " + item.getRodamiento().getMonto() + " Cantidad: " + item.getCant() +  "</p>");
					//total+=item.getRodamiento().getMonto()*item.getCant();
				}
				response.getWriter().print("<p> TOTAL: " + total +  "</p>");
				response.getWriter().print("<p> <a href=\"/tp-roda-web/index.html\">Regresar Menu</a></p>");
			} catch (CommunicationException | NotBoundException e) {
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
	}
	
	protected void buscarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<CotizacionDto> cotizaciones;
		try {
			cotizaciones = Delegado.getInstancia().obtenerCotizaciones();
			
			for (int i = 0; i < cotizaciones.size(); i++) {
				CotizacionDto cotizacionDto = cotizaciones.get(i);
				response.getWriter().print("<table>");
				response.getWriter().print("<th>Numero</th><th>Estado</th><th>Cliente</th>");
				response.getWriter().print("<tr><td>" +cotizacionDto.getNumeroCotizacion() + "</td><td>"+ cotizacionDto.getEstado() + "</td><td>"+ cotizacionDto.getCliente().getRazonSocial()+"</td></tr>");
				response.getWriter().print("</table>");
				response.getWriter().print("<table>");
				for(int j=0; j<cotizacionDto.getItems().size();j++){
					ItemCotizacionDto item = cotizacionDto.getItems().get(j);
					response.getWriter().print("<th>Rodamiento</th><th>Cantidad</th>");
					response.getWriter().print("<tr><td>" +item.getRodamiento().getCodigo()+ "</td><td>"+ item.getCant() + "</td></tr>");
				}
				response.getWriter().print("</table>");
			}
			response.getWriter().print("<p> <a href=\"/tp-roda-web/index.html\">Regresar Menu</a></p>");
		} catch (CommunicationException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	
	protected void obtenerCotizacionesParaAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Buscar cotizaciones aprobadas.
		ClienteDto clienteNegocio = new ClienteDto();
		clienteNegocio.setCUIT(request.getParameter("cuit"));
		
		
		//cotizacion.setIdCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
		//cotizacion.setNumeroCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
		System.out.println("cotizacionSeleccionada: "+request.getParameter("cotizacionSeleccionada"));
		try {
			Delegado.getInstancia().aprobarCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
			//----response.getWriter().print("<p> Se aprobo la cotizacion por un valor de:  <b>$" + valor + "</b></p>");
		} catch (CommunicationException | NotBoundException e) {
			e.printStackTrace();
		}
		//AdministracionOV.getInstancia().aprobarYCotizarCotizacion(cotizacion);	
	
	}
	
	protected void aprobarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Buscar cotizaciones aprobadas.
		ClienteDto clienteNegocio = new ClienteDto();
		clienteNegocio.setCUIT(request.getParameter("cuit"));
		System.out.println("cotizacionSeleccionada: "+request.getParameter("cotizacionSeleccionada"));
		try {
			Delegado.getInstancia().aprobarCotizacion(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
			response.getWriter().print("<p>La cotizacion ha sido aprobada</p>");
			response.getWriter().print("<a href=\"/tp-roda-web/index.html\">Regresar Menu</a>");
		} catch (CommunicationException | NotBoundException e) {
			e.printStackTrace();
		}
		//AdministracionOV.getInstancia().aprobarYCotizarCotizacion(cotizacion);	
	
	}
	
	public void buscarCotizacion (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			CotizacionDto cotizacionDto = Delegado.getInstancia().obtenerCotizacionPorId(Integer.valueOf(request.getParameter("cotizacionSeleccionada")));
			response.getWriter().print("<p> Estado :  " + cotizacionDto.getEstado() + "</p>");
			response.getWriter().print("<p> Cliente :  " + cotizacionDto.getCliente().getRazonSocial() + "</p>");
			response.getWriter().print("<p> Fecha Creacion :  " + cotizacionDto.getFechaCreacion() + "</p>");
			float total = 0;
			for (int i = 0; i < cotizacionDto.getItems().size(); i++) {
				ItemCotizacionDto item = cotizacionDto.getItems().get(i);
				response.getWriter().print("<p> RODAMIENTO </p> ");
				response.getWriter().print("<p> Carateristica :  " + item.getRodamiento().getCaracteristica() + " Codigo: " + item.getRodamiento().getCodigo() +  "</p>");
				response.getWriter().print("<p> Valor :  " + item.getRodamiento().getMonto() + " Cantidad: " + item.getCant() +  "</p>");
				total+=item.getRodamiento().getMonto()*item.getCant();
			}
			response.getWriter().print("<p> TOTAL: " + total +  "</p>");
			response.getWriter().print("<form action=\"CotizacionServlet\" method=\"POST\">");
			response.getWriter().print("<input type=\"hidden\" name=\"metodo\" id=\"metodo\" value=\"aprobarCotizacion\">");
			response.getWriter().print("<input type=\"hidden\" name=\"cotizacionSeleccionada\" value=\"" + cotizacionDto.getNumeroCotizacion() + "\">");
			response.getWriter().print("<p>Desea aprobar la cotizacion?</p><input type=\"submit\" value=\"Aceptar\" onClick=\"enviar();\">");
			response.getWriter().print("</form>");
			response.getWriter().print("<p> <a href=\"/tp-roda-web/index.html\">Regresar Menu</a></p>");
		} catch (CommunicationException | NotBoundException e) {
			e.printStackTrace();
		}
		//AdministracionOV.getInstancia().aprobarYCotizarCotizacion(cotizacion);
		
	}

}
