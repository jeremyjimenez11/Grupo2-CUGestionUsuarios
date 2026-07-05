<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion de Usuarios</title>
</head>
<body>
    <h1>Gestion de Usuarios</h1>

    <p>
        <a href="${pageContext.request.contextPath}/UsuarioController?accion=new">
            Crear nuevo usuario
        </a>
    </p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${listaUsuarios}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.nombre}</td>
                    <td>${u.correo}</td>
                    <td>${u.rol}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/UsuarioController?accion=edit&id=${u.id}">
                            Actualizar
                        </a>
                        &nbsp;|&nbsp;
                        <a href="${pageContext.request.contextPath}/UsuarioController?accion=delete&id=${u.id}">
                            Eliminar
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty listaUsuarios}">
                <tr>
                    <td colspan="5">No hay usuarios registrados.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>
