<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orden de compra</title>
</head>
<body>


	<script type="text/javascript">
	
	var listaOrdenCompra = [];
	function agregar(){
		listaOrdenCompra.push({
			"codigo": document.getElementById('codigo').value
		});
		alert("Se agrego una solicitud");
	}
	
	function enviar(){
		var a = JSON.stringify(listaOrdenCompra);
		document.getElementById("listaOrdenCompra").value = a;
		document.getElementById("metodo").value = "generarRemito";
		alert("Se creo la Orden de compra");
	}
</script>

	<form action="RemitoServlet" method="POST">
		<input type="hidden" name="listaOrdenCompra" id="listaOrdenCompra" value="">
		<input type="hidden" name="metodo" id="metodo" value="">
		<h1>
			<a href="index.html" class="menu">OV</a>
		</h1>
		<table>
			<tr>
				<td colspan="2">Orden de compra</td>
			</tr>
			<tr>
				<td>Nro orden compra:</td>
				<td><input type="TEXT" name="codigo" id="codigo" value="1"></td>
				<td>CUIT Proveedor:</td>
				<td><input type="TEXT" id="proveedor" name="proveedor" value="1"></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="button" value="Agregar" onClick="agregar();"></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="Aceptar" onClick="enviar();"></td>
				<td align="center"><input type="reset" value="Cancelar"></td>
			</tr>
		</table>
	</form>
</body>
</html>