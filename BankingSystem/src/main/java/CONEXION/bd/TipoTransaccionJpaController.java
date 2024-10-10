/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;

import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class TipoTransaccionJpaController implements Serializable {

    public TipoTransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void Create(TipoTransaccion tipoTransaccion) {
        if (tipoTransaccion.getDetalleTransaccionList() == null) {
            tipoTransaccion.setDetalleTransaccionList(new ArrayList<DetalleTransaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleTransaccion> attachedDetalleTransaccionList = new ArrayList<DetalleTransaccion>();
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccionToAttach : tipoTransaccion.getDetalleTransaccionList()) {
                detalleTransaccionListDetalleTransaccionToAttach = em.getReference(detalleTransaccionListDetalleTransaccionToAttach.getClass(), detalleTransaccionListDetalleTransaccionToAttach.getIdDetalle());
                attachedDetalleTransaccionList.add(detalleTransaccionListDetalleTransaccionToAttach);
            }
            tipoTransaccion.setDetalleTransaccionList(attachedDetalleTransaccionList);
            em.persist(tipoTransaccion);
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccion : tipoTransaccion.getDetalleTransaccionList()) {
                TipoTransaccion oldIdTipoOfDetalleTransaccionListDetalleTransaccion = detalleTransaccionListDetalleTransaccion.getIdTipo();
                detalleTransaccionListDetalleTransaccion.setIdTipo(tipoTransaccion);
                detalleTransaccionListDetalleTransaccion = em.merge(detalleTransaccionListDetalleTransaccion);
                if (oldIdTipoOfDetalleTransaccionListDetalleTransaccion != null) {
                    oldIdTipoOfDetalleTransaccionListDetalleTransaccion.getDetalleTransaccionList().remove(detalleTransaccionListDetalleTransaccion);
                    oldIdTipoOfDetalleTransaccionListDetalleTransaccion = em.merge(oldIdTipoOfDetalleTransaccionListDetalleTransaccion);
                }
            }
            em.getTransaction().commit();
            System.out.println("\nTipo de transacción agregado corretamente");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void Edit(TipoTransaccion tipoTransaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransaccion persistentTipoTransaccion = em.find(TipoTransaccion.class, tipoTransaccion.getIdTipoTransaccion());
            List<DetalleTransaccion> detalleTransaccionListOld = persistentTipoTransaccion.getDetalleTransaccionList();
            List<DetalleTransaccion> detalleTransaccionListNew = tipoTransaccion.getDetalleTransaccionList();
            List<DetalleTransaccion> attachedDetalleTransaccionListNew = new ArrayList<DetalleTransaccion>();
            for (DetalleTransaccion detalleTransaccionListNewDetalleTransaccionToAttach : detalleTransaccionListNew) {
                detalleTransaccionListNewDetalleTransaccionToAttach = em.getReference(detalleTransaccionListNewDetalleTransaccionToAttach.getClass(), detalleTransaccionListNewDetalleTransaccionToAttach.getIdDetalle());
                attachedDetalleTransaccionListNew.add(detalleTransaccionListNewDetalleTransaccionToAttach);
            }
            detalleTransaccionListNew = attachedDetalleTransaccionListNew;
            tipoTransaccion.setDetalleTransaccionList(detalleTransaccionListNew);
            tipoTransaccion = em.merge(tipoTransaccion);
            for (DetalleTransaccion detalleTransaccionListOldDetalleTransaccion : detalleTransaccionListOld) {
                if (!detalleTransaccionListNew.contains(detalleTransaccionListOldDetalleTransaccion)) {
                    detalleTransaccionListOldDetalleTransaccion.setIdTipo(null);
                    detalleTransaccionListOldDetalleTransaccion = em.merge(detalleTransaccionListOldDetalleTransaccion);
                }
            }
            for (DetalleTransaccion detalleTransaccionListNewDetalleTransaccion : detalleTransaccionListNew) {
                if (!detalleTransaccionListOld.contains(detalleTransaccionListNewDetalleTransaccion)) {
                    TipoTransaccion oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion = detalleTransaccionListNewDetalleTransaccion.getIdTipo();
                    detalleTransaccionListNewDetalleTransaccion.setIdTipo(tipoTransaccion);
                    detalleTransaccionListNewDetalleTransaccion = em.merge(detalleTransaccionListNewDetalleTransaccion);
                    if (oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion != null && !oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion.equals(tipoTransaccion)) {
                        oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion.getDetalleTransaccionList().remove(detalleTransaccionListNewDetalleTransaccion);
                        oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion = em.merge(oldIdTipoOfDetalleTransaccionListNewDetalleTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
            System.out.println("\nTipo de transaccion editado correctamente");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTransaccion.getIdTipoTransaccion();
                if (findTipoTransaccion(id) == null) {
                    System.out.println("\nTipo de Transacción con ID " + tipoTransaccion.getIdTipoTransaccion() + " no existente");
                    System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
                    throw new NonexistentEntityException("The tipoTransaccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void Update(TipoTransaccion tipoTransaccion){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransaccion persistentTipoTransaccion = em.find(TipoTransaccion.class, tipoTransaccion.getIdTipoTransaccion());

            if (em.contains(persistentTipoTransaccion)){
                em.merge(tipoTransaccion);
                System.out.println("\nTipo de transaccion editada correctamente!");
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findTipoTransaccion(tipoTransaccion.getIdTipoTransaccion()) == null ){
                System.out.println("\nTipo de transacción con ID " + tipoTransaccion.getIdTipoTransaccion() + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
    }    
    
    public void Destroy(Integer id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            TipoTransaccion tipoTransaccion = em.getReference(TipoTransaccion.class, id);
            tipoTransaccion.getIdTipoTransaccion();
            
            List<DetalleTransaccion> detalleTransaccionList = tipoTransaccion.getDetalleTransaccionList();
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccion : detalleTransaccionList) {
                detalleTransaccionListDetalleTransaccion.setIdTipo(null);
                detalleTransaccionListDetalleTransaccion = em.merge(detalleTransaccionListDetalleTransaccion);
            }
            em.remove(tipoTransaccion);
            em.getTransaction().commit();
            System.out.println("\nTipo de trsnsaccion eliminado corrstamente");
        } catch (Exception e) {
            System.out.println("\nTipo de transacción con ID " + id + " no existente");
            System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public TipoTransaccion ObjetoTipoT(Integer id){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransaccion tipoTransaccion = em.find(TipoTransaccion.class, id);
            
            if (em.contains(tipoTransaccion)){
                return tipoTransaccion;
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findTipoTransaccion(id) == null ){
                System.out.println("\nTipo de transaccion con id " + id + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
        return null;
    }

    public List<TipoTransaccion> findTipoTransaccionEntities() {
        return findTipoTransaccionEntities(true, -1, -1);
    }

    public List<TipoTransaccion> findTipoTransaccionEntities(int maxResults, int firstResult) {
        return findTipoTransaccionEntities(false, maxResults, firstResult);
    }

    private List<TipoTransaccion> findTipoTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTransaccion.class));
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

    public TipoTransaccion findTipoTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTransaccion> rt = cq.from(TipoTransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
