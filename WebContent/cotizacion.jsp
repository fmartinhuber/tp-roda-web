<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.RodamientoDto" %>
<%@ page import="delegate.Delegado" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cotizacion</title>
</head>

	<script type="text/javascript">

var listItems = [];


function validar(campo){
	var codigo = document.getElementById(campo).value;
	var regex = /[0-9]/;
	if(!regex.test(codigo)){
		alert("Debe ingresar codigo numerico");
		document.getElementById(campo).value = "";
	}
	
}

function agregar(){
	listItems.push({
		"codigo": document.getElementById('codigo').value,
		"cantidad": document.getElementById('cantidad').value,
		"caracteristica": document.getElementById('caracteristica').value,
		"pais": document.getElementById('pais').value,
		"marca": document.getElementById('marca').value
	});
	alert("Se agrego un elemento");
}

function enviar(){
	var a = JSON.stringify(listItems);
	document.getElementById("listaRodamiento").value = a;
	document.getElementById("metodo").value = "generarCotizacion";
}

function cotizacionCreada (numero){
	if(numero != null) {
		alert("Se creo la cotizacion:" + numero);
	}
	
	
}

</script>

	<form action="CotizacionServlet" method="POST">
		<input type="hidden" name="listaRodamiento" id="listaRodamiento" value="">
		<input type="hidden" name="metodo" id="metodo" value="">
		<h1>
			<a href="index.html" class="menu">OV</a>
		</h1>
		<table>
			<!--  <tr>
				<td>Razon Social:</td>
				<td><input type="TEXT" name="razonSocial" value="Agro Negocios Lujar"></td>
			</tr>
			<tr>
				<td>CUIT:</td>
				<td><input type="TEXT" name="cuit" value="30-22222222-3"></td>
			</tr>-->
			<tr>
				<td colspan="2" align="center">Rodamiento</td>
			</tr>
			<tr>
				<td>Codigo:</td>
				<td width="200"><select name="listaCombo" id="listaCombo" >
				<% List <RodamientoDto> listaRodamiento = Delegado.getInstancia().obtenerRodamientos();
					for(int i = 0;i<listaRodamiento.size();i++){
						RodamientoDto roda = listaRodamiento.get(i);
				%>
					<option value="<%= roda.getCodigo() %>"><%= roda.getCaracteristica() %></option>
				<% 
					}
				%>
				</select></td>
				<!--  <td><input type="TEXT" id="codigo" value="20210" onChange="validar(codigo)"></td>-->
			</tr>
			<tr>
				<td>Cantidad:</td>
				<td><input type="TEXT" id="cantidad" value="1" onChange="validar(cantidad)"></td>
			</tr>
			<tr>
				<td>caracteristica:</td>
				<td><input type="TEXT" id="caracteristica" value="1"></td>
			</tr>
			<tr>
				<td>Pais:</td>
				<td><input type="TEXT" id="pais" value="Suecia"></td>
			</tr>
			<tr>
				<td>Marca:</td>
				<td><input type="TEXT" id="marca" value="SKF"></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="button"
					value="Agregar" onClick="agregar();"></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="Aceptar" onClick="enviar();"></td>
				<td align="center"><input type="reset" value="Cancelar"></td>
			</tr>
		</table>
	</form>
</body>
</html>