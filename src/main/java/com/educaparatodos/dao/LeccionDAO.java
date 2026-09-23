package com.educaparatodos.dao;

import com.educaparatodos.model.Leccion;
import com.educaparatodos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LeccionDAO {

    public Leccion crear(Leccion leccion) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(leccion);
            tx.commit();
            return leccion;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Leccion buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Leccion.class, id);
        } finally {
            em.close();
        }
    }

    /** Todas las lecciones de un curso, ordenadas. Usada en la página de detalle de curso. */
    public List<Leccion> listarPorCurso(Long cursoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Leccion> query = em.createQuery(
                "SELECT l FROM Leccion l WHERE l.curso.id = :cursoId ORDER BY l.orden", Leccion.class);
            query.setParameter("cursoId", cursoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Leccion actualizar(Leccion leccion) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Leccion actualizada = em.merge(leccion);
            tx.commit();
            return actualizada;
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
            Leccion leccion = em.find(Leccion.class, id);
            if (leccion != null) {
                em.remove(leccion);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
