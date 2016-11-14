<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h3>Alterar tarefa</h3>
	
	<form action="alteraTarefa" method="post">
	
		<input type="hidden" name="id" value="${tarefa.id}" /> Descrição:<br />
		<textarea name="descricao" cols="100" rows="5">${tarefa.descricao}</textarea>
		<br /> Finalizado <input type="checkbox" name="finalizado"
			value="true" ${tarefa.finalizado? 'checked' : '' } /> <br /> Data de finalização: <br />  
			

			
<input type="text" name="dataFinalizacao" value="<fmt:parseDate value="${tarefa.dataFinalizacao}" pattern="yyyy-MM-dd" />"/>
	
			 <br /> 
			<input type="submit" value="Alterar" />
	</form>
</body>
</html>
