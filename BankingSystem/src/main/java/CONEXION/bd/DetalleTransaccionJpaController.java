/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;

import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Isa
 */
public class DetalleTransaccionJpaController implements Serializable {

    public DetalleTransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleTransaccion detalleTransaccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransaccion idTipo = detalleTransaccion.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getIdTipoTransaccion());
                detalleTransaccion.setIdTipo(idTipo);
            }
            Transaccion idTransaccion = detalleTransaccion.getIdTransaccion();
            if (idTransaccion != null) {
                idTransaccion = em.getReference(idTransaccion.getClass(), idTransaccion.getIdTransaccion());
                detalleTransaccion.setIdTransaccion(idTransaccion);
            }
            em.persist(detalleTransaccion);
            if (idTipo != null) {
                idTipo.getDetalleTransaccionList().add(detalleTransaccion);
                idTipo = em.merge(idTipo);
            }
            if (idTransaccion != null) {
                idTransaccion.getDetalleTransaccionList().add(detalleTransaccion);
                idTransaccion = em.merge(idTransaccion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleTransaccion detalleTransaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleTransaccion persistentDetalleTransaccion = em.find(DetalleTransaccion.class, detalleTransaccion.getIdDetalle());
            TipoTransaccion idTipoOld = persistentDetalleTransaccion.getIdTipo();
            TipoTransaccion idTipoNew = detalleTransaccion.getIdTipo();
            Transaccion idTransaccionOld = persistentDetalleTransaccion.getIdTransaccion();
            Transaccion idTransaccionNew = detalleTransaccion.getIdTransaccion();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getIdTipoTransaccion());
                detalleTransaccion.setIdTipo(idTipoNew);
            }
            if (idTransaccionNew != null) {
                idTransaccionNew = em.getReference(idTransaccionNew.getClass(), idTransaccionNew.getIdTransaccion());
                detalleTransaccion.setIdTransaccion(idTransaccionNew);
            }
            detalleTransaccion = em.merge(detalleTransaccion);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getDetalleTransaccionList().remove(detalleTransaccion);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getDetalleTransaccionList().add(detalleTransaccion);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idTransaccionOld != null && !idTransaccionOld.equals(idTransaccionNew)) {
                idTransaccionOld.getDetalleTransaccionList().remove(detalleTransaccion);
                idTransaccionOld = em.merge(idTransaccionOld);
            }
            if (idTransaccionNew != null && !idTransaccionNew.equals(idTransaccionOld)) {
                idTransaccionNew.getDetalleTransaccionList().add(detalleTransaccion);
                idTransaccionNew = em.merge(idTransaccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleTransaccion.getIdDetalle();
                if (findDetalleTransaccion(id) == null) {
                    throw new NonexistentEntityException("The detalleTransaccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleTransaccion detalleTransaccion;
            try {
                detalleTransaccion = em.getReference(DetalleTransaccion.class, id);
                detalleTransaccion.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleTransaccion with id " + id + " no longer exists.", enfe);
            }
            TipoTransaccion idTipo = detalleTransaccion.getIdTipo();
            if (idTipo != null) {
                idTipo.getDetalleTransaccionList().remove(detalleTransaccion);
                idTipo = em.merge(idTipo);
            }
            Transaccion idTransaccion = detalleTransaccion.getIdTransaccion();
            if (idTransaccion != null) {
                idTransaccion.getDetalleTransaccionList().remove(detalleTransaccion);
                idTransaccion = em.merge(idTransaccion);
            }
            em.remove(detalleTransaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleTransaccion> findDetalleTransaccionEntities() {
        return findDetalleTransaccionEntities(true, -1, -1);
    }

    public List<DetalleTransaccion> findDetalleTransaccionEntities(int maxResults, int firstResult) {
        return findDetalleTransaccionEntities(false, maxResults, firstResult);
    }

    private List<DetalleTransaccion> findDetalleTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleTransaccion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetalleTransaccion findDetalleTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleTransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleTransaccion> rt = cq.from(DetalleTransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
