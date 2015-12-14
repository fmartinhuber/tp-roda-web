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
import dto.ClienteDto;
import dto.CotizacionDto;
import dto.FacturaDto;
import dto.ItemFacturaDto;
import dto.RodamientoDto;

/**
 * Servlet implementation class FacturaServlet
 */
@WebServlet("/FacturaServlet")
public class FacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacturaServlet() {
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
		if(request.getParameter("metodo").equals("crearFactura")){
			crearFactura(request,response);
	   }

	}
	
	private void crearFactura(HttpServletRequest request, HttpServletResponse response){
		
		List <CotizacionDto> listaCotizaciones = new ArrayList<CotizacionDto>();
		
		JSONArray jObj;
		try {
			jObj = new JSONArray(request.getParameter("listaCotizacion"));
			
			for (int i = 0; i < jObj.length(); i++) {
				JSONObject objeto = jObj.getJSONObject(i);
				String codigo = objeto.getString("codigo");
				CotizacionDto cotizacionDto = new CotizacionDto();
				cotizacionDto.setNumeroCotizacion(Integer.valueOf(codigo));
				listaCotizaciones.add(cotizacionDto);
				
			}

			ClienteDto cliente = Delegado.getInstancia().obtenerUsuarioLogueado();
			FacturaDto factura = Delegado.getInstancia().generarFactura(listaCotizaciones, cliente);

			response.getWriter().print("<p> Factura Nro: " + factura.getNumeroFactura() + "</p>");
			response.getWriter().print("<p> Estado: " + factura.getEstado() + "</p>");
			//response.getWriter().print("<p> Cliente: " + factura.getCliente() + "</p>");
			response.getWriter().print("<p> Fecha: " + factura.getFecha()+ "</p>");
			for (int i = 0; i < factura.getItems().size(); i++) {
				ItemFacturaDto item = factura.getItems().get(i);
				response.getWriter().print("<p> Item </p>");
				response.getWriter().print("<p> Rodamiento: " + item.getRodamiento().getCaracteristica() + "Codigo: " + item.getRodamiento().getCaracteristica() + "Cantidad: " + item.getCantidad() + "</p>");
			}
			response.getWriter().print("<p> Total: " + factura.getTotal()+ "</p>");
			response.getWriter().print("<p> <a href=\"/tp-roda-web/index.html\">Regresar Menu</a></p>");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (CommunicationException | NotBoundException | IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
