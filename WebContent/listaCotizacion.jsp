<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cotizaciones</title>
</head>
<body>

	<script type="text/javascript">
		
	function enviar(){
		document.getElementById('metodo').value = "buscarCotizaciones";
		
	}
		
	</script>
	<form action="CotizacionServlet" method="POST">
		<input type="hidden" name="listaRodamiento" id="listaRodamiento" value="" />
		<input type="hidden" name="metodo" id="metodo" value="">
		<h1>
			<a href="index.html" class="menu">OV</a>
		</h1>
		<input type="submit" value="Aceptar" onClick="enviar();">
	</form>
</body>
</html>