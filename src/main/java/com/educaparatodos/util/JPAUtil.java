package com.educaparatodos.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utilidad centralizada para obtener el EntityManagerFactory / EntityManager.
 * Se usa un único EMF para toda la aplicación (patrón recomendado en JPA).
 */
public class JPAUtil {

    private static final String UNIDAD_PERSISTENCIA = "educaParaTodosPU";
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
    }

    private JPAUtil() {}

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
