<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmar Eliminacion</title>
</head>
<body>
    <h1>Confirmar Eliminacion</h1>

    <p>Esta seguro de que desea eliminar el usuario con ID ${idUsuario}?</p>

    <form action="${pageContext.request.contextPath}/UsuarioController" method="post">
        <input type="hidden" name="accion" value="eliminar"/>
        <input type="hidden" name="id" value="${idUsuario}"/>

        <button type="submit" name="respuesta" value="confirmar">Confirmar</button>
        <button type="submit" name="respuesta" value="cancelar">Cancelar</button>
    </form>
</body>
</html>
