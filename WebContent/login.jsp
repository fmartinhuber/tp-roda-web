<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="LoginServlet" method="POST">
		<table>
			<tr>
				<td>Usuario:</td>
				<td><input type="TEXT" name="razonSocial" value="Agro Negocios Lujar"></td>
			</tr>
			<tr>
				<td>Contraseña:</td>
				<td><input type="password" name="password" value="Agro Negocios Lujar"></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="Aceptar" ></td>
				<td align="center"><input type="reset" value="Cancelar"></td>
			</tr>
		</table>
	</form>
</body>
</html>