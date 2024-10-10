/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;

import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
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
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccion transaccion) {
        if (transaccion.getDetalleTransaccionList() == null) {
            transaccion.setDetalleTransaccionList(new ArrayList<DetalleTransaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta idCuenta = transaccion.getIdCuenta();
            if (idCuenta != null) {
                idCuenta = em.getReference(idCuenta.getClass(), idCuenta.getIdCuenta());
                transaccion.setIdCuenta(idCuenta);
            }
            Empleado idEmpleado = transaccion.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                transaccion.setIdEmpleado(idEmpleado);
            }
            Sucursal idSucursal = transaccion.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getIdSucursal());
                transaccion.setIdSucursal(idSucursal);
            }
            List<DetalleTransaccion> attachedDetalleTransaccionList = new ArrayList<DetalleTransaccion>();
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccionToAttach : transaccion.getDetalleTransaccionList()) {
                detalleTransaccionListDetalleTransaccionToAttach = em.getReference(detalleTransaccionListDetalleTransaccionToAttach.getClass(), detalleTransaccionListDetalleTransaccionToAttach.getIdDetalle());
                attachedDetalleTransaccionList.add(detalleTransaccionListDetalleTransaccionToAttach);
            }
            transaccion.setDetalleTransaccionList(attachedDetalleTransaccionList);
            em.persist(transaccion);
            if (idCuenta != null) {
                idCuenta.getTransaccionList().add(transaccion);
                idCuenta = em.merge(idCuenta);
            }
            if (idEmpleado != null) {
                idEmpleado.getTransaccionList().add(transaccion);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idSucursal != null) {
                idSucursal.getTransaccionList().add(transaccion);
                idSucursal = em.merge(idSucursal);
            }
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccion : transaccion.getDetalleTransaccionList()) {
                Transaccion oldIdTransaccionOfDetalleTransaccionListDetalleTransaccion = detalleTransaccionListDetalleTransaccion.getIdTransaccion();
                detalleTransaccionListDetalleTransaccion.setIdTransaccion(transaccion);
                detalleTransaccionListDetalleTransaccion = em.merge(detalleTransaccionListDetalleTransaccion);
                if (oldIdTransaccionOfDetalleTransaccionListDetalleTransaccion != null) {
                    oldIdTransaccionOfDetalleTransaccionListDetalleTransaccion.getDetalleTransaccionList().remove(detalleTransaccionListDetalleTransaccion);
                    oldIdTransaccionOfDetalleTransaccionListDetalleTransaccion = em.merge(oldIdTransaccionOfDetalleTransaccionListDetalleTransaccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getIdTransaccion());
            Cuenta idCuentaOld = persistentTransaccion.getIdCuenta();
            Cuenta idCuentaNew = transaccion.getIdCuenta();
            Empleado idEmpleadoOld = persistentTransaccion.getIdEmpleado();
            Empleado idEmpleadoNew = transaccion.getIdEmpleado();
            Sucursal idSucursalOld = persistentTransaccion.getIdSucursal();
            Sucursal idSucursalNew = transaccion.getIdSucursal();
            List<DetalleTransaccion> detalleTransaccionListOld = persistentTransaccion.getDetalleTransaccionList();
            List<DetalleTransaccion> detalleTransaccionListNew = transaccion.getDetalleTransaccionList();
            if (idCuentaNew != null) {
                idCuentaNew = em.getReference(idCuentaNew.getClass(), idCuentaNew.getIdCuenta());
                transaccion.setIdCuenta(idCuentaNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                transaccion.setIdEmpleado(idEmpleadoNew);
            }
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getIdSucursal());
                transaccion.setIdSucursal(idSucursalNew);
            }
            List<DetalleTransaccion> attachedDetalleTransaccionListNew = new ArrayList<DetalleTransaccion>();
            for (DetalleTransaccion detalleTransaccionListNewDetalleTransaccionToAttach : detalleTransaccionListNew) {
                detalleTransaccionListNewDetalleTransaccionToAttach = em.getReference(detalleTransaccionListNewDetalleTransaccionToAttach.getClass(), detalleTransaccionListNewDetalleTransaccionToAttach.getIdDetalle());
                attachedDetalleTransaccionListNew.add(detalleTransaccionListNewDetalleTransaccionToAttach);
            }
            detalleTransaccionListNew = attachedDetalleTransaccionListNew;
            transaccion.setDetalleTransaccionList(detalleTransaccionListNew);
            transaccion = em.merge(transaccion);
            if (idCuentaOld != null && !idCuentaOld.equals(idCuentaNew)) {
                idCuentaOld.getTransaccionList().remove(transaccion);
                idCuentaOld = em.merge(idCuentaOld);
            }
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                idCuentaNew.getTransaccionList().add(transaccion);
                idCuentaNew = em.merge(idCuentaNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getTransaccionList().remove(transaccion);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getTransaccionList().add(transaccion);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getTransaccionList().remove(transaccion);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getTransaccionList().add(transaccion);
                idSucursalNew = em.merge(idSucursalNew);
            }
            for (DetalleTransaccion detalleTransaccionListOldDetalleTransaccion : detalleTransaccionListOld) {
                if (!detalleTransaccionListNew.contains(detalleTransaccionListOldDetalleTransaccion)) {
                    detalleTransaccionListOldDetalleTransaccion.setIdTransaccion(null);
                    detalleTransaccionListOldDetalleTransaccion = em.merge(detalleTransaccionListOldDetalleTransaccion);
                }
            }
            for (DetalleTransaccion detalleTransaccionListNewDetalleTransaccion : detalleTransaccionListNew) {
                if (!detalleTransaccionListOld.contains(detalleTransaccionListNewDetalleTransaccion)) {
                    Transaccion oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion = detalleTransaccionListNewDetalleTransaccion.getIdTransaccion();
                    detalleTransaccionListNewDetalleTransaccion.setIdTransaccion(transaccion);
                    detalleTransaccionListNewDetalleTransaccion = em.merge(detalleTransaccionListNewDetalleTransaccion);
                    if (oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion != null && !oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion.equals(transaccion)) {
                        oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion.getDetalleTransaccionList().remove(detalleTransaccionListNewDetalleTransaccion);
                        oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion = em.merge(oldIdTransaccionOfDetalleTransaccionListNewDetalleTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getIdTransaccion();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
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
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getIdTransaccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            Cuenta idCuenta = transaccion.getIdCuenta();
            if (idCuenta != null) {
                idCuenta.getTransaccionList().remove(transaccion);
                idCuenta = em.merge(idCuenta);
            }
            Empleado idEmpleado = transaccion.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getTransaccionList().remove(transaccion);
                idEmpleado = em.merge(idEmpleado);
            }
            Sucursal idSucursal = transaccion.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getTransaccionList().remove(transaccion);
                idSucursal = em.merge(idSucursal);
            }
            List<DetalleTransaccion> detalleTransaccionList = transaccion.getDetalleTransaccionList();
            for (DetalleTransaccion detalleTransaccionListDetalleTransaccion : detalleTransaccionList) {
                detalleTransaccionListDetalleTransaccion.setIdTransaccion(null);
                detalleTransaccionListDetalleTransaccion = em.merge(detalleTransaccionListDetalleTransaccion);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
            System.out.println("\nTransacción eliminada correctamente");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
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

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
