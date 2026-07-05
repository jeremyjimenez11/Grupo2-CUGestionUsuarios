package modelo.servicios;

import modelo.dao.UsuarioDAO;
import modelo.entities.Usuario;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    /**
     * @return null si todo OK; mensaje de errorValidacion en caso contrario.
     */
    public String crearUsuario(Usuario usuario) {
        if (camposVacios(usuario)) {
            return "Todos los campos son obligatorios.";
        }
        if (!usuarioDAO.validarCorreo(usuario.getCorreo())) {
            return "El correo ingresado ya esta registrado.";
        }
        usuarioDAO.guardar(usuario);
        return null;
    }

    /**
     * @return null si todo OK; mensaje de errorValidacion en caso contrario.
     */
    public String actualizarUsuario(Usuario usuario) {
        if (camposVacios(usuario)) {
            return "Todos los campos son obligatorios.";
        }
        if (!usuarioDAO.validarDatos(usuario)) {
            return "El correo ingresado ya pertenece a otro usuario.";
        }
        usuarioDAO.actualizar(usuario);
        return null;
    }

    /**
     * @return true si se elimino (respuesta = confirmar); false si se cancelo.
     */
    public boolean eliminarUsuario(Integer idUsuario, String respuesta) {
        if ("confirmar".equalsIgnoreCase(respuesta)) {
            usuarioDAO.eliminar(idUsuario);
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuario(Integer idUsuario) {
        return usuarioDAO.buscarPorId(idUsuario);
    }

    private boolean camposVacios(Usuario u) {
        return u.getNombre() == null || u.getNombre().trim().isEmpty()
                || u.getCorreo() == null || u.getCorreo().trim().isEmpty()
                || u.getClave() == null || u.getClave().trim().isEmpty()
                || u.getRol() == null || u.getRol().trim().isEmpty();
    }
}
