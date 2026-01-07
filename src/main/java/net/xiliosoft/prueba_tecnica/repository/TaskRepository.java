package net.xiliosoft.prueba_tecnica.repository;

import net.xiliosoft.prueba_tecnica.model.Task;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TaskRepository {

    @PersistenceContext(unitName = "prueba-tecnica")
    private EntityManager em;

    @Transactional
    public void save(Task task) {
        if (task.getId() == null) {
            em.persist(task);
        } else {
            em.merge(task);
        }
    }

    @Transactional
    public void delete(Long id) {
        Task task = findById(id);
        if (task != null) {
            em.remove(task);
        }
    }

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

    public List<Task> findAll() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }
}
