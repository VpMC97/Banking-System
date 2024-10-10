/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;


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
public class SucursalJpaController implements Serializable {

    public SucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void Create(Sucursal sucursal) {
        if (sucursal.getTransaccionList() == null) {
            sucursal.setTransaccionList(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
            for (Transaccion transaccionListTransaccionToAttach : sucursal.getTransaccionList()) {
                transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getIdTransaccion());
                attachedTransaccionList.add(transaccionListTransaccionToAttach);
            }
            sucursal.setTransaccionList(attachedTransaccionList);
            em.persist(sucursal);
            for (Transaccion transaccionListTransaccion : sucursal.getTransaccionList()) {
                Sucursal oldIdSucursalOfTransaccionListTransaccion = transaccionListTransaccion.getIdSucursal();
                transaccionListTransaccion.setIdSucursal(sucursal);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
                if (oldIdSucursalOfTransaccionListTransaccion != null) {
                    oldIdSucursalOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
                    oldIdSucursalOfTransaccionListTransaccion = em.merge(oldIdSucursalOfTransaccionListTransaccion);
                }
            }
            em.getTransaction().commit();
            System.out.println("\nSucursal agregada correctamente!");
        } catch (Exception ex) {
            if (findSucursal(sucursal.getIdSucursal()) != null) {
                System.out.println("\nSucursal ya existente según ID");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

//    public void Edit(Sucursal sucursal) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getIdSucursal());
//            List<Transaccion> transaccionListOld = persistentSucursal.getTransaccionList();
//            List<Transaccion> transaccionListNew = sucursal.getTransaccionList();
//            List<Transaccion> attachedTransaccionListNew = new ArrayList<Transaccion>();
//            for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
//                transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getIdTransaccion());
//                attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
//            }
//            transaccionListNew = attachedTransaccionListNew;
//            sucursal.setTransaccionList(transaccionListNew);
//            sucursal = em.merge(sucursal);
//            for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
//                if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
//                    transaccionListOldTransaccion.setIdSucursal(null);
//                    transaccionListOldTransaccion = em.merge(transaccionListOldTransaccion);
//                }
//            }
//            for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
//                if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
//                    Sucursal oldIdSucursalOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getIdSucursal();
//                    transaccionListNewTransaccion.setIdSucursal(sucursal);
//                    transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
//                    if (oldIdSucursalOfTransaccionListNewTransaccion != null && !oldIdSucursalOfTransaccionListNewTransaccion.equals(sucursal)) {
//                        oldIdSucursalOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
//                        oldIdSucursalOfTransaccionListNewTransaccion = em.merge(oldIdSucursalOfTransaccionListNewTransaccion);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = sucursal.getIdSucursal();
//                if (findSucursal(id) == null) {
//                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//    
    public void Update(Sucursal sucursal){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getIdSucursal());

            if (em.contains(persistentSucursal)){
                em.merge(sucursal);
                System.out.println("\nSucursal modificada correctamente!");
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findSucursal(sucursal.getIdSucursal()) == null ){
                System.out.println("\nSucursal con ID " + sucursal.getIdSucursal() + " no existente");
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
            Sucursal sucursal = em.getReference(Sucursal.class, id);
            sucursal.getIdSucursal();

            List<Transaccion> transaccionList = sucursal.getTransaccionList();
            for (Transaccion transaccionListTransaccion : transaccionList) {
                transaccionListTransaccion.setIdSucursal(null);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
            System.out.println("\nSucursal eliminada correctamente");
        } catch (Exception e) {
            System.out.println("\nSucursal con ID " + id + " no existente");
            System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Sucursal ObjetoTipoS(int id){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal = em.find(Sucursal.class, id);
            
            if (em.contains(sucursal)){
                return sucursal;
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findSucursal(id) == null ){
                System.out.println("\nSucursal con ID " + id + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
        return null;
    }            
    
    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
}
