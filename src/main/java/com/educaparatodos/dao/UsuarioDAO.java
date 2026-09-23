package com.educaparatodos.dao;

import com.educaparatodos.model.Curso;
import com.educaparatodos.model.Usuario;
import com.educaparatodos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class UsuarioDAO {

    // ---------- CRUD básico ----------

    public Usuario crear(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(usuario);
            tx.commit();
            return usuario;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Trae al usuario junto con sus cursos inscritos ya cargados (JOIN FETCH),
     * para poder recorrer usuario.getCursosInscritos() en perfil.jsp sin que
     * falle con LazyInitializationException (el EntityManager ya está cerrado
     * cuando la vista se renderiza).
     */
    public Usuario buscarPorIdConCursos(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.cursosInscritos WHERE u.id = :id",
                Usuario.class);
            query.setParameter("id", id);
            List<Usuario> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    public List<Usuario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u ORDER BY u.nombre", Usuario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario actualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario actualizado = em.merge(usuario);
            tx.commit();
            return actualizado;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- Inscripción de usuario en curso (relación N:M) ----------

    public void inscribirEnCurso(Long usuarioId, Long cursoId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario usuario = em.find(Usuario.class, usuarioId);
            Curso curso = em.find(Curso.class, cursoId);
            if (usuario != null && curso != null) {
                usuario.getCursosInscritos().add(curso);
                curso.setPopularidad(curso.getPopularidad() + 1);
                em.merge(usuario);
                em.merge(curso);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- Consultas JPQL ----------

    public Usuario buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email);
            List<Usuario> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    // ---------- Operaciones masivas UPDATE / DELETE (bulk JPQL) ----------

    /** UPDATE masivo: cambia el rol de todos los usuarios registrados antes de una fecha. */
    public int actualizarRolUsuariosAntiguos(LocalDate antesDe, String nuevoRol) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int filas = em.createQuery(
                    "UPDATE Usuario u SET u.rol = :rol WHERE u.fechaRegistro < :antesDe")
                .setParameter("rol", nuevoRol)
                .setParameter("antesDe", antesDe)
                .executeUpdate();
            tx.commit();
            return filas;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /** DELETE masivo: elimina usuarios registrados antes de una fecha dada (limpieza de cuentas inactivas). */
    public int eliminarUsuariosRegistradosAntesDe(LocalDate fecha) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int filas = em.createQuery(
                    "DELETE FROM Usuario u WHERE u.fechaRegistro < :fecha")
                .setParameter("fecha", fecha)
                .executeUpdate();
            tx.commit();
            return filas;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
