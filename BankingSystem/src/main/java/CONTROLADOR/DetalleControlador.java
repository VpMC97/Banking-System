/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.Cuenta;
import CONEXION.bd.CuentaJpaController;
import CONEXION.bd.DetalleTransaccion;
import CONEXION.bd.DetalleTransaccionJpaController;
import CONEXION.bd.Transaccion;
import CONEXION.bd.TipoTransaccion;
import CONEXION.bd.TransaccionJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isa
 */
public class DetalleControlador {
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    DetalleTransaccionJpaController DetalleJPA = new DetalleTransaccionJpaController(emf);  
    CuentaJpaController cuJPA = new CuentaJpaController(emf);
    TransaccionJpaController tJPA = new TransaccionJpaController(emf);
            
    Scanner sc = new Scanner(System.in); 
    
    public void CrearDetalle(Transaccion t, TipoTransaccion tt){

        DetalleTransaccion detalle = new DetalleTransaccion();
        
        int id = Contador();
        detalle.setIdDetalle(id);
        detalle.setIdTipo(tt);
        detalle.setIdTransaccion(t);

        String name = tt.getDescripcion();
        int idTipo = tt.getIdTipoTransaccion();
        
        System.out.println("Ingrese monto para realizar " + name + ": ");
        Double monto = sc.nextDouble();
        sc.nextLine();
        detalle.setMonto(monto);
        Double NewSaldo;
        Cuenta cuenta = t.getIdCuenta();
        
        DetalleJPA.create(detalle);
        
        switch(idTipo){
            case 1:
                NewSaldo = t.getIdCuenta().getSaldo() - monto;
                if (NewSaldo >= 0){
                    ActualizarSaldo(NewSaldo, cuenta);
                    System.out.println("\nTransaccion tipo Retiro realizada exitósamente");
                }
                else{
                    System.out.println("\nCuenta con fondos insuficientes para realizar esta acción");
                    rollback(id, t);
                }
            break;
            case 2:
                NewSaldo = t.getIdCuenta().getSaldo() + monto;
                ActualizarSaldo(NewSaldo, cuenta);
                System.out.println("\nTransaccion tipo Depósito realizada exitósamente");
            break;
            case 4:
                System.out.println("Ingrese ID de cuenta a tranferir: ");
                int idT = sc.nextInt();
                sc.nextLine();
                Cuenta c = cuJPA.ObjetoTipoC(idT);
                if (c != null){
                    NewSaldo = t.getIdCuenta().getSaldo() - monto;
                    if (NewSaldo >= 0){
                        ActualizarSaldo(NewSaldo, cuenta);
                        Double saldo = c.getSaldo() + monto;
                        ActualizarSaldo(saldo, c);
                    }
                    else{
                        System.out.println("\nCuenta con fondos insuficientes para realizar esta acción");
                        rollback(id, t);
                    } 
                }
                else{
                    System.out.println("\nCuenta a transferir no existente, inténtelo de nuevo");
                    rollback(id, t);
                }
            break;
            default:
                System.out.println("Tipo de Transacción inválida");
                rollback(id, t);
            break;
        }
    }
    
    public void rollback(int idDetalle, Transaccion t){
        try {
            DetalleJPA.destroy(idDetalle);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            tJPA.destroy(t.getIdTransaccion());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void EliminarDetalle(int idTransaccion){
        List <DetalleTransaccion> detalles = new ArrayList<>();

        detalles = DetalleJPA.findDetalleTransaccionEntities();
        
        for (DetalleTransaccion dt : detalles){
            if (dt.getIdTransaccion().getIdTransaccion() == idTransaccion){
                try {
                    DetalleJPA.destroy(dt.getIdDetalle());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(DetalleControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void ActualizarSaldo(Double newSaldo, Cuenta cuenta){
        cuenta.setSaldo(newSaldo);
        cuJPA.Edit(cuenta);
    }
    
    public int Contador(){
        List <DetalleTransaccion> detalles = new ArrayList<>();

        detalles = DetalleJPA.findDetalleTransaccionEntities();
        
        int contador = 0;
        
        for (DetalleTransaccion d : detalles){
            contador = d.getIdDetalle();
        }
        return contador + 1;
    }    
}
