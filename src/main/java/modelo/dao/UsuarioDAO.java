package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modelo.entities.Usuario;
import java.util.List;

public class UsuarioDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("usuariosPU");

    // SELECT u FROM Usuario u
    public List<Usuario> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // SELECT * FROM usuarios WHERE correo = ?
    public Usuario findByCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Usuario> resultados = em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class)
                    .setParameter("correo", correo)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    // Validacion de correo para creacion: true si el correo NO existe (disponible)
    public boolean validarCorreo(String correo) {
        return findByCorreo(correo) == null;
    }

    // Validacion de datos para actualizacion: correo no duplicado por otro usuario
    public boolean validarDatos(Usuario usuario) {
        Usuario existente = findByCorreo(usuario.getCorreo());
        return existente == null || existente.getId().equals(usuario.getId());
    }

    // persist
    public Usuario guardar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(usuario);
            tx.commit();
            return usuario;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // merge
    public Usuario actualizar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario actualizado = em.merge(usuario);
            tx.commit();
            return actualizado;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // remove
    public void eliminar(Integer idUsuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario usuario = em.find(Usuario.class, idUsuario);
            if (usuario != null) {
                em.remove(usuario);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorId(Integer idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, idUsuario);
        } finally {
            em.close();
        }
    }
}
