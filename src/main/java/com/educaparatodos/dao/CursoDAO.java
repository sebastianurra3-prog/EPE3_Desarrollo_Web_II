package com.educaparatodos.dao;

import com.educaparatodos.model.Curso;
import com.educaparatodos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO de Curso: operaciones CRUD + consultas JPQL + operaciones masivas
 * (UPDATE y DELETE) exigidas en la actividad evaluativa EPE3.
 */
public class CursoDAO {

    // ---------- CRUD básico ----------

    public Curso crear(Curso curso) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(curso);
            tx.commit();
            return curso;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Curso buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public List<Curso> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c ORDER BY c.titulo", Curso.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Curso actualizar(Curso curso) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Curso actualizado = em.merge(curso);
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
            Curso curso = em.find(Curso.class, id);
            if (curso != null) {
                em.remove(curso);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- Consultas JPQL (búsqueda) ----------

    /** Busca cursos por tema (ej: "Programación"). */
    public List<Curso> buscarPorTema(String tema) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c WHERE LOWER(c.tema) LIKE LOWER(CONCAT('%', :tema, '%'))", Curso.class);
            query.setParameter("tema", tema);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /** Busca cursos por nivel de dificultad (BASICO, INTERMEDIO, AVANZADO). */
    public List<Curso> buscarPorNivel(String nivel) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c WHERE c.nivelDificultad = :nivel", Curso.class);
            query.setParameter("nivel", nivel);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /** Cursos ordenados por popularidad (los más inscritos primero). */
    public List<Curso> buscarMasPopulares(int limite) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c ORDER BY c.popularidad DESC", Curso.class);
            query.setMaxResults(limite);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /** Búsqueda combinada tema + nivel, usada en la vista de búsqueda de cursos. */
    public List<Curso> buscarPorTemaYNivel(String tema, String nivel) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Curso c WHERE 1=1");
            if (tema != null && !tema.isBlank()) {
                jpql.append(" AND LOWER(c.tema) LIKE LOWER(CONCAT('%', :tema, '%'))");
            }
            if (nivel != null && !nivel.isBlank()) {
                jpql.append(" AND c.nivelDificultad = :nivel");
            }
            jpql.append(" ORDER BY c.popularidad DESC");

            TypedQuery<Curso> query = em.createQuery(jpql.toString(), Curso.class);
            if (tema != null && !tema.isBlank()) query.setParameter("tema", tema);
            if (nivel != null && !nivel.isBlank()) query.setParameter("nivel", nivel);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- Operaciones masivas UPDATE / DELETE (bulk JPQL) ----------

    /**
     * UPDATE masivo: incrementa en 1 la popularidad de todos los cursos
     * creados después de una fecha (ej. campaña de promoción de cursos nuevos).
     */
    public int actualizarPopularidadCursosRecientes(LocalDate desde) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int filas = em.createQuery(
                    "UPDATE Curso c SET c.popularidad = c.popularidad + 1 " +
                    "WHERE c.fechaCreacion >= :desde")
                .setParameter("desde", desde)
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

    /**
     * DELETE masivo: elimina cursos con popularidad por debajo de un umbral
     * y creados antes de cierta fecha (limpieza de cursos poco usados).
     */
    public int eliminarCursosPocoPopulares(int popularidadMinima, LocalDate creadosAntesDe) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int filas = em.createQuery(
                    "DELETE FROM Curso c WHERE c.popularidad < :popMin AND c.fechaCreacion < :antes")
                .setParameter("popMin", popularidadMinima)
                .setParameter("antes", creadosAntesDe)
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
