<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitud Orden Compra</title>
</head>
<body>

<script type="text/javascript">

	function generar(){
		document.getElementById('metodo').value = "solicitudCompra";
		alert('Solicitudes de compras generadas.');
	}

</script>

	<form action="SolicitudCompraServlet" method="POST">
		<input type="hidden" name="metodo" id="metodo" value="">
		<h1>
			<a href="index.html" class="menu">OV</a>
		</h1>
		<table>
			<tr>
				<td colspan="2">Generar solicitudes orden compra</td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="Generar" onClick="generar()"></td>
				<td align="center"><input type="reset" value="Cancelar"></td>
			</tr>
		</table>
	</form>

</body>
</html>