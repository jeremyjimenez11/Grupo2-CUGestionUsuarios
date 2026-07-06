<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actualizar Usuario</title>
</head>
<body>
    <h1>Actualizar Usuario</h1>

    <c:if test="${not empty mensajeError}">
        <p style="color:red;">${mensajeError}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/UsuarioController" method="post">
        <input type="hidden" name="accion" value="actualizar"/>
        <input type="hidden" name="id" value="${usuario.id}"/>

        <p>
            <label>Nombre:</label>
            <input type="text" name="nombre" value="${usuario.nombre}"/>
        </p>
        <p>
            <label>Correo:</label>
            <input type="email" name="correo" value="${usuario.correo}"/>
        </p>
        <p>
            <label>Clave:</label>
            <input type="password" name="clave" value="${usuario.clave}"/>
        </p>
        <p>
            <label>Rol:</label>
            <select name="rol">
                <option value="admin" ${usuario.rol == 'admin' ? 'selected' : ''}>Admin</option>
                <option value="empleado" ${usuario.rol == 'empleado' ? 'selected' : ''}>Empleado</option>
         	</select>

        </p>

        <button type="submit">Actualizar</button>
        <a href="${pageContext.request.contextPath}/UsuarioController?accion=ingresar">Cancelar</a>
    </form>
</body>
</html>
