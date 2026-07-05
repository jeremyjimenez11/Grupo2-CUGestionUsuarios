package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entities.Usuario;
import modelo.servicios.UsuarioService;
import java.io.IOException;
import java.util.List;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ruteador(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ruteador(request, response);
    }

    private void ruteador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "new":             mostrarFormularioCrear(request, response);      break;
            case "crear":           crear(request, response);                       break;
            case "edit":           	mostrarFormularioActualizar(request, response); break;
            case "actualizar":      actualizar(request, response);                  break;
            case "delete":  		mostrarConfirmacion(request, response);         break;
            case "eliminar":        eliminar(request, response);                    break;
            case "listar":
            default:                listar(request, response);                      break;
        }
    }

    // Solicita abrir el modulo -> listarUsuarios() -> JSPGestionUsuarios
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.getRequestDispatcher("/jsp/JSPGestionUsuarios.jsp").forward(request, response);
    }

    // Solicita crear -> JSPCrearUsuario
    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/JSPCrearUsuario.jsp").forward(request, response);
    }

    // Envia datos del nuevo usuario -> crearUsuario(Usuario)
    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = leerUsuarioDesdeRequest(request, false);

        String mensajeError = usuarioService.crearUsuario(usuario);

        if (mensajeError != null) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/jsp/JSPCrearUsuario.jsp").forward(request, response);
        } else {
            listar(request, response);
        }
    }

    // Solicita actualizar -> JSPActualizarUsuario con datosUsuario
    private void mostrarFormularioActualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Usuario datosUsuario = usuarioService.obtenerUsuario(id);
        request.setAttribute("usuario", datosUsuario);
        request.getRequestDispatcher("/jsp/JSPActualizarUsuario.jsp").forward(request, response);
        actualizar(request, response); // Llamada a actualizar para procesar la actualización
    }

    // Envia cambios -> actualizarUsuario(Usuario)
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = leerUsuarioDesdeRequest(request, true);

        String mensajeError = usuarioService.actualizarUsuario(usuario);

        if (mensajeError != null) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/jsp/JSPActualizarUsuario.jsp").forward(request, response);
        } else {
            listar(request, response);
        }
    }

    // Solicita eliminar -> JSPConfirmarEliminacion con idUsuario
    private void mostrarConfirmacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idUsuario = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("idUsuario", idUsuario);
        request.getRequestDispatcher("/jsp/JSPConfirmarEliminacion.jsp").forward(request, response);
    }

    // Confirma o cancela -> eliminarUsuario(idUsuario, respuesta)
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idUsuario = Integer.valueOf(request.getParameter("id"));
        String respuesta = request.getParameter("respuesta");

        usuarioService.eliminarUsuario(idUsuario, respuesta);

        // Tanto si confirma como si cancela, se regresa a la lista actualizada
        listar(request, response);
    }

    private Usuario leerUsuarioDesdeRequest(HttpServletRequest request, boolean conId) {
        Usuario usuario = new Usuario();
        if (conId) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                usuario.setId(Integer.valueOf(id));
            }
        }
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setCorreo(request.getParameter("correo"));
        usuario.setClave(request.getParameter("clave"));
        usuario.setRol(request.getParameter("rol"));
        return usuario;
    }
}
