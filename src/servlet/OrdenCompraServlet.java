package servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.CommunicationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import delegate.Delegado;
import dto.CotizacionDto;
import dto.ItemOrdenCompraDto;
import dto.OrdenCompraDto;
import dto.SolicitudCompraDto;

/**
 * Servlet implementation class OrdenCompraServlet
 */
@WebServlet("/OrdenCompraServlet")
public class OrdenCompraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdenCompraServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("metodo:" + request.getMethod());
		System.out.println("metodo:" + request.getParameter("metodo"));
		if(request.getParameter("metodo").equals("generarOrdenCompra")){
			generarOrdenCompra(request,response);
		}else if(request.getParameter("metodo").equals("buscarOrdenesCompra")){
			obtenerOrdenesCompra(request,response);
		}else if(request.getParameter("metodo").equals("aprobarOrdenCotizacion")){
			aprobarOrdenCompra(request,response);
		}
	}


	private void aprobarOrdenCompra(HttpServletRequest request,	HttpServletResponse response) {
		try {
			Delegado.getInstancia().aprobarCotizacion(Integer.valueOf(request.getParameter("ordenSeleccionada")));
			
			response.getWriter().print("<p> Se aprobó la orden de compra </p>");
			response.getWriter().print("<p> <a href=\"/tp-roda-web/indexCC.html\">Regresar Menu</a></p>");
		} catch (NumberFormatException | CommunicationException  | NotBoundException | IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void obtenerOrdenesCompra(HttpServletRequest request, HttpServletResponse response) {
		try {
			List <OrdenCompraDto> listaOrdenes = Delegado.getInstancia().obtenerOrdenesCompra();
			response.getWriter().print("<h1> ORDENES DE COMPRA </h1>");
			for (OrdenCompraDto ordenCompraDto : listaOrdenes) {
				response.getWriter().print("<p> Orden: " +ordenCompraDto.getNumeroOrdenCompra() +" </p>");
				response.getWriter().print("<p> Estado: " +ordenCompraDto.getEstado() +" </p>");
				response.getWriter().print("<p> Forma Pago: " +ordenCompraDto.getFormaPago() +" </p>");
				response.getWriter().print("<p> Proveedor: " +ordenCompraDto.getProveedor().getNombre() +" </p>");
				for (ItemOrdenCompraDto itemOrdenCompraDto : ordenCompraDto.getItems()) {
					response.getWriter().print("<p> ITEM  </p>");
					response.getWriter().print("<p> Nro Item: " + itemOrdenCompraDto.getNroItemOrdenCompra() +" </p>");
					response.getWriter().print("<p> Cantidad: " + itemOrdenCompraDto.getCantidad() +" </p>");
					response.getWriter().print("<p> Monto: " + itemOrdenCompraDto.getMonto() +" </p>");
					response.getWriter().print("<p> Rodamiento: " + itemOrdenCompraDto.getRodamiento().getCaracteristica() +" </p>");
				}
				response.getWriter().print("<p> Total: " +ordenCompraDto.getTotal() +" </p>");
			}
			response.getWriter().print("<p> <a href=\"/tp-roda-web/index.html\">Regresar Menu</a></p>");
		} catch (CommunicationException  | NotBoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void generarOrdenCompra(HttpServletRequest request, HttpServletResponse response) {
		
		List <SolicitudCompraDto> solicitudesPendientes = new ArrayList <SolicitudCompraDto> (); 
		JSONArray jObj;
		try {
			jObj = new JSONArray(request.getParameter("listaOrdenCompra"));
			for (int i = 0; i < jObj.length(); i++) {
				JSONObject objeto = jObj.getJSONObject(i);
				String codigo = objeto.getString("codigo");
				
				SolicitudCompraDto solicitudCompra = new SolicitudCompraDto();
				solicitudCompra.setNumeroSolicitudCompra(Integer.valueOf(codigo));
				
				solicitudesPendientes.add(solicitudCompra);
			}
			String forma = request.getParameter("formaPago");
			List <OrdenCompraDto> ordenes = Delegado.getInstancia().crearOrdenCompra(solicitudesPendientes,forma);
			for (OrdenCompraDto ordenCompraDto : ordenes) {
				response.getWriter().print("<p> Orden de Compra: " + ordenCompraDto.getNumeroOrdenCompra() + "</p>");
				response.getWriter().print("<p> Estado: " + ordenCompraDto.getEstado() + "</p>");
				response.getWriter().print("<p> Proveedor: " + ordenCompraDto.getProveedor() + "</p>");
				response.getWriter().print("<p> SOLICITUDES </p>");
				for (int i = 0; i < ordenCompraDto.getSolicitudesCompra().size(); i++) {
					SolicitudCompraDto solicitud = ordenCompraDto.getSolicitudesCompra().get(i);
					response.getWriter().print("<p> Solicitud: " + solicitud.getNumeroSolicitudCompra() + "</p>");
					response.getWriter().print("<p> Estado: " + ordenCompraDto.getEstado() + "</p>");
				}
				for (int i = 0; i < ordenCompraDto.getListaCotizaciones().size(); i++) {
					CotizacionDto cotizacion = ordenCompraDto.getListaCotizaciones().get(i);
					response.getWriter().print("<p> Cotizacion: " + cotizacion.getNumeroCotizacion() + "</p>");
				}
			}
			
//			response.getWriter().print("<form action=\"OrdenCompraServlet\" method=\"POST\">");
//			response.getWriter().print("<input type=\"hidden\" name=\"metodo\" id=\"metodo\" value=\"aprobarOrdenCotizacion\">");
//			response.getWriter().print("<input type=\"hidden\" name=\"ordenSeleccionada\" value=\"" + orden.getNumeroOrdenCompra() + "\">");
//			response.getWriter().print("<p>Desea aprobar la cotizacion?</p><input type=\"submit\" value=\"Aceptar\" onClick=\"enviar();\">");
//			response.getWriter().print("</form>");
			response.getWriter().print("<p> <a href=\"/tp-roda-web/indexCC.html\">Regresar Menu</a></p>");
		} catch (JSONException e) {
			e.printStackTrace();
		}  catch (CommunicationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
