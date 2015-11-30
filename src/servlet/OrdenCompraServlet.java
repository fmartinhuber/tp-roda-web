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
			int ordenCompra = Delegado.getInstancia().crearOrdenCompra(solicitudesPendientes);
			response.getWriter().print("<p> Se generaron las solicitudes de compra: " +ordenCompra+ "</p>");
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
