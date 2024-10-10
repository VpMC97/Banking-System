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
public class ClienteJpaController implements Serializable {

    // ESTAS CLASES JPA SON LAS QUE SE COMUNICAN CON LA BASE DE DATOS UTILIZANDO TRANSACCIONES
    // LA GENERÉ AUTOMÁTICAMENTE PERO YO MODIFIQUÉ LA MAYORÍA DE MÉTODOS. 
    // ADEMÁS DE CREAR NUEVOS, COMPAREN CON LOS OTROS JPA PARA GUIARSE Y TRABAJAR !!!
    
    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void Create(Cliente cliente){
        if (cliente.getCuentaList() == null) {
            cliente.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : cliente.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getIdCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            cliente.setCuentaList(attachedCuentaList);
            em.persist(cliente);
            for (Cuenta cuentaListCuenta : cliente.getCuentaList()) {
                Cliente oldNitClienteOfCuentaListCuenta = cuentaListCuenta.getNitCliente();
                cuentaListCuenta.setNitCliente(cliente);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldNitClienteOfCuentaListCuenta != null) {
                    oldNitClienteOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldNitClienteOfCuentaListCuenta = em.merge(oldNitClienteOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
            System.out.println("\nCliente agregado correctamente!");
        } catch (Exception ex) {
            if (findCliente(cliente.getNitCliente()) != null) {
                System.out.println("\nCliente ya existente según NIT");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
//
//    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Cliente persistentCliente = em.find(Cliente.class, cliente.getNitCliente());
//            List<Cuenta> cuentaListOld = persistentCliente.getCuentaList();
//            List<Cuenta> cuentaListNew = cliente.getCuentaList();
//            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
//            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
//                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getIdCuenta());
//                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
//            }
//            cuentaListNew = attachedCuentaListNew;
//            cliente.setCuentaList(cuentaListNew);
//            cliente = em.merge(cliente);
//            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
//                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
//                    cuentaListOldCuenta.setNitCliente(null);
//                    cuentaListOldCuenta = em.merge(cuentaListOldCuenta);
//                }
//            }
//            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
//                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
//                    Cliente oldNitClienteOfCuentaListNewCuenta = cuentaListNewCuenta.getNitCliente();
//                    cuentaListNewCuenta.setNitCliente(cliente);
//                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
//                    if (oldNitClienteOfCuentaListNewCuenta != null && !oldNitClienteOfCuentaListNewCuenta.equals(cliente)) {
//                        oldNitClienteOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
//                        oldNitClienteOfCuentaListNewCuenta = em.merge(oldNitClienteOfCuentaListNewCuenta);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                String id = cliente.getNitCliente();
//                if (findCliente(id) == null) {
//                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
    public void Update(Cliente cliente){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getNitCliente());

            if (em.contains(persistentCliente)){
                em.merge(cliente);
                System.out.println("\nCliente modificado correctamente!");
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findCliente(cliente.getNitCliente()) == null ){
                System.out.println("\nCliente con ID " + cliente.getNitCliente() + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
    }
    
    public Cliente ObjetoTipoC(String nit){
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, nit);
            
            if (em.contains(cliente)){
                return cliente;
            }
            em.getTransaction().commit();
        }catch(Exception e){
            if (findCliente(nit) == null ){
                System.out.println("\nCliente con NIT " + nit + " no existente");
                System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
            }
        }finally{
            if (em != null)
                em.close();
        }    
        return null;
    }    
    
    public void Destroy(String id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Cliente cliente = em.getReference(Cliente.class, id);
            cliente.getNitCliente();
            
            List<Cuenta> cuentaList = cliente.getCuentaList();
            for (Cuenta cuentaListCuenta : cuentaList) {
                cuentaListCuenta.setNitCliente(null);
                cuentaListCuenta = em.merge(cuentaListCuenta);
            }
            em.remove(cliente);
            em.getTransaction().commit();
            System.out.println("\nCliente eliminado correctamente");
        } catch (Exception e) {
            System.out.println("\nCliente con NIT " + id + " no existente");
            System.out.println("No fue posible realizar esta acción, pot favor, inténtelo de nuevo");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
}
