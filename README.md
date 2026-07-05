# Gestion de Usuarios (Servlet + JSP + JPA/EclipseLink)

Proyecto Maven que implementa el CU de Gestion de Usuarios segun el DSD:
listar, crear, actualizar y eliminar, con validaciones y confirmacion.

## Stack
- Jakarta EE 10 (namespaces `jakarta.*`)
- Servidor: Apache Tomcat 10.1+ (obligatorio por `jakarta.*`)
- JDK 17
- JPA con EclipseLink 4.x
- MySQL 8

> Si usas Tomcat 9 (`javax.*`) NO funcionara sin cambiar los namespaces. Avisa si es tu caso.

## Estructura
```
gestion-usuarios/
├── pom.xml
├── database.sql
└── src/main/
    ├── java/
    │   ├── Servlets/UsuarioController.java
    │   └── modelo/
    │       ├── entities/Usuario.java
    │       ├── dao/UsuarioDAO.java
    │       └── servicios/UsuarioService.java
    ├── resources/META-INF/persistence.xml
    └── webapp/
        ├── index.jsp
        ├── WEB-INF/web.xml
        └── jsp/
            ├── JSPGestionUsuarios.jsp
            ├── JSPCrearUsuario.jsp
            ├── JSPActualizarUsuario.jsp
            └── JSPConfirmarEliminacion.jsp
```

## Pasos para ejecutar

1. Crear la base de datos (o solo la BD y dejar que EclipseLink cree la tabla):
   ```
   mysql -u root -p < database.sql
   ```

2. Ajustar usuario/clave/URL de MySQL en:
   `src/main/resources/META-INF/persistence.xml`

3. Compilar y empaquetar:
   ```
   mvn clean package
   ```
   Esto genera `target/gestion-usuarios.war`.

4. Desplegar el WAR en Tomcat 10.1+
   (copiar a `TOMCAT/webapps/` o desplegar desde el IDE).

5. Abrir en el navegador:
   ```
   http://localhost:8080/gestion-usuarios/
   ```
   Redirige automaticamente a la lista de usuarios.

## Notas
- El context path `/gestion-usuarios` viene de `<finalName>` en el pom.xml.
- En el codigo nunca se escribe esa ruta: se usa `${pageContext.request.contextPath}`.
