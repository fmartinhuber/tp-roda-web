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
import dto.ClienteDto;
import dto.OrdenCompraDto;
import dto.ProveedorDto;
import dto.RemitoDto;
import dto.RodamientoDto;

/**
 * Servlet implementation class remitoServlet
 */
@WebServlet("/RemitoServlet")
public class RemitoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemitoServlet() {
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
		if(request.getParameter("metodo").equals("generarRemito")){
			generarRemito(request,response);
		}else{
			try {
				throw new Exception("No se encontro el metodo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void generarRemito(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProveedorDto proveedor = new ProveedorDto();
			proveedor.setCUIT(request.getParameter("proveedor"));
			
			List <OrdenCompraDto> listaItems = new ArrayList<OrdenCompraDto>();
	
			JSONArray jObj = new JSONArray(request.getParameter("listaOrdenCompra")); // this parses the json
			
			for (int i = 0; i < jObj.length(); i++) {
				JSONObject objeto = jObj.getJSONObject(i);
				String codigo = objeto.getString("codigo");
				
				OrdenCompraDto orden = new OrdenCompraDto();
				orden.setNumeroOrdenCompra(Integer.valueOf(codigo));
				listaItems.add(orden);
			}
			
			try {
				int nroOrden = Delegado.getInstancia().crearRemito(listaItems, proveedor);
				response.getWriter().print("<p> Se creo el Remito numero :  <b>" + nroOrden + "</b></p>");
			} catch (CommunicationException | NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
