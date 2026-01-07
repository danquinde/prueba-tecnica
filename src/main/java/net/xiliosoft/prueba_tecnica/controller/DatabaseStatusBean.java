package net.xiliosoft.prueba_tecnica.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@RequestScoped
public class DatabaseStatusBean {

    @PersistenceContext(unitName = "prueba-tecnica")
    private EntityManager em;

    private String statusMessage;

    @PostConstruct
    public void init() {
        try {
            em.createNativeQuery("SELECT 1 FROM DUAL").getSingleResult();
            statusMessage = "✅ Conexión a la base de datos: EXITOSA";
        } catch (RuntimeException e) {
            statusMessage = "❌ Error de conexión a la base de datos: " + e.getMessage();
        }
    }

    public String getStatusMessage() {
        return statusMessage;
    }
} 