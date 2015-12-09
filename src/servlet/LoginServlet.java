package servlet;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.naming.CommunicationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import delegate.Delegado;
import dto.ClienteDto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		try {
			
			String razonSocial = request.getParameter("razonSocial");
			String password = request.getParameter("password");
			
			ClienteDto cliente = Delegado.getInstancia().obtenerUsuario(razonSocial, password);
			
			if(cliente == null){
				response.getWriter().print("Usuario o Contraseña erronea");
			}else{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.html");
		        requestDispatcher.forward(request, response);
			}
			
		} catch (CommunicationException | NotBoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
