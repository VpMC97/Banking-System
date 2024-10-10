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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void Create(Empleado empleado) {
        if (empleado.getTransaccionList() == null) {
            empleado.setTransaccionList(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
            for (Transaccion transaccionListTransaccionToAttach : empleado.getTransaccionList()) {
                transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getIdTransaccion());
                attachedTransaccionList.add(transaccionListTransaccionToAttach);
            }
            empleado.setTransaccionList(attachedTransaccionList);
            em.persist(empleado);
            for (Transaccion transaccionListTransaccion : empleado.getTransaccionList()) {
                Empleado oldIdEmpleadoOfTransaccionListTransaccion = transaccionListTransaccion.getIdEmpleado();
                transaccionListTransaccion.setIdEmpleado(empleado);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
                if (oldIdEmpleadoOfTransaccionListTransaccion != null) {
                    oldIdEmpleadoOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
                    oldIdEmpleadoOfTransaccionListTransaccion = em.merge(oldIdEmpleadoOfTransaccionListTransaccion);
                }
            }
            em.getTransaction().commit();
            System.out.println("\nEmpleado agregado correctamente!");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

//    public void Edit(Empleado empleado) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
//            List<Transaccion> transaccionListOld = persistentEmpleado.getTransaccionList();
//            List<Transaccion> transaccionListNew = empleado.getTransaccionList();
//            List<Transaccion> attachedTransaccionListNew = new ArrayList<Transaccion>();
//            for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
//                transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getIdTransaccion());
//                attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
//            }
//            transaccionListNew = attachedTransaccionListNew;
//            empleado.setTransaccionList(transaccionListNew);
//            empleado = em.merge(empleado);
//            for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
//                if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
//                    transaccionListOldTransaccion.setIdEmpleado(null);
//                    transaccionListOldTransaccion = em.merge(transaccionListOldTransaccion);
//                }
//            }
//            for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
//                if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
//                    Empleado oldIdEmpleadoOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getIdEmpleado();
//                    transaccionListNewTransaccion.setIdEmpleado(empleado);
//                    transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
//                    if (oldIdEmpleadoOfTransaccionListNewTransaccion != null && !oldIdEmpleadoOfTransaccionListNewTransaccion.equals(empleado)) {
//                        oldIdEmpleadoOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
//                        oldIdEmpleadoOfTransaccionListNewTransaccion = em.merge(oldIdEmpleadoOfTransaccionListNewTransaccion);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = empleado.getIdEmpleado();
//                if (findEmpleado(id) == null) {
//                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    
    public void Update(Empleado empleado){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpelado = em.find(Empleado.class, empleado.getIdEmpleado());

            if (em.contains(persistentEmpelado)){
                em.merge(empleado);
                System.out.println("\nEmpleado modificado correctamente!");
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findEmpleado(empleado.getIdEmpleado()) == null ){
                System.out.println("\nEmpleado con ID " + empleado.getIdEmpleado() + " no existente");
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
            Empleado empleado = em.getReference(Empleado.class, id);
            empleado.getIdEmpleado();
            
            List<Transaccion> transaccionList = empleado.getTransaccionList();
            for (Transaccion transaccionListTransaccion : transaccionList) {
                transaccionListTransaccion.setIdEmpleado(null);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
            }
            em.remove(empleado);
            em.getTransaction().commit();
            System.out.println("\nEmpleado eliminado correctamente");
        } catch (Exception e) {
            System.out.println("\nEmpleado con ID " + id + " no existente");
            System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Empleado ObjetoTipoE(int id){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = em.find(Empleado.class, id);
            
            if (em.contains(empleado)){
                return empleado;
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findEmpleado(id) == null ){
                System.out.println("\nEmpleado con ID " + id + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
        return null;
    }        
    
    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
