<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">

	var listItems = [];

	function agregar(){
		listItems.push({
			"codigo": document.getElementById('codigo').value
		});
		alert("Se agrego un elemento");
	}
	
	function enviar(){
		var a = JSON.stringify(listItems);
		document.getElementById("listaCotizacion").value = a;
		document.getElementById("metodo").value = "crearFactura";
	}
</script>
<body>
	<form action="FacturaServlet" method="POST">
		<input type="hidden" name="listaCotizacion" id="listaCotizacion" value="">
		<input type="hidden" name="metodo" id="metodo" value="">
		<h1>
			<a href="index.html" class="menu">OV</a>
		</h1>
		<table>
			<!--  <tr>
				<td>CUIT:</td>
				<td><input type="TEXT" name="cuit" value="30-22222222-3">
			</tr>
			<tr>
				<td>Razon Social:</td>
				<td><input type="TEXT" name="razonSocial" value="Maquinaria Industrial SA">
			</tr> -->
			<tr>
				<td colspan="2">Factura</td>
			</tr>
			<tr>
				<td>Codigo:</td>
				<td><input type="TEXT" id="codigo" value="2"></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="button" value="Agregar" onClick="agregar();"></td>
			</tr>
			<!--  <tr>
				<td>Sub total:</td>
				<td><input type="TEXT" id="subTotal" value="1"></td>
			</tr>
			<tr>
				<td>Total:</td>
				<td><input type="TEXT" id="total" value="1"></td>
			</tr>-->
			<tr>
				<td align="center"><input type="submit" value="Aceptar" onClick="enviar();"></td>
				<td align="center"><input type="reset" value="Cancelar"></td>
			</tr>
		</table>
	</form>
</body>
</html>