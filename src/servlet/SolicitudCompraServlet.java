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

import utils.ItemDto;
import delegate.Delegado;
import dto.CotizacionDto;
import dto.RodamientoDto;


/**
 * Servlet implementation class solicitudCompraServlet
 */
@WebServlet("/SolicitudCompraServlet")
public class SolicitudCompraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolicitudCompraServlet() {
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
		if(request.getParameter("metodo").equals("solicitudCompra")){
			solicitudCompra(request,response);
	   }
	}

	private void solicitudCompra(HttpServletRequest request, HttpServletResponse response) {
		
		 
		try {
			List <CotizacionDto> listaCotizaciones = Delegado.getInstancia().obtenerCotizacionesAprobadas();
			Delegado.getInstancia().crearSolicitudCompra(listaCotizaciones);

		}  catch (CommunicationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

}
