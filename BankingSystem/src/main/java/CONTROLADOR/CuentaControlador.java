/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.Cliente;
import CONEXION.bd.ClienteJpaController;
import CONEXION.bd.Cuenta;
import CONEXION.bd.CuentaJpaController;
import CONEXION.bd.TipoCuenta;
import CONEXION.bd.TipoCuentaJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isa
 */
public class CuentaControlador {
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    CuentaJpaController CuentaJPA = new CuentaJpaController(emf);  
    TipoCuentaJpaController tcJPA = new TipoCuentaJpaController(emf);
    ClienteJpaController cJPA = new ClienteJpaController(emf);
            
    Scanner sc = new Scanner(System.in); 
    
    public void CrearCuenta(){

        Cuenta cuenta = new Cuenta();
        
        System.out.println("--------CREAR CUENTA--------\n");
        
        int id = Contador();
        
        System.out.println("Cuenta ID " + id);
        cuenta.setIdCuenta(id);
        System.out.println("Ingrese saldo: ");
        Double saldo = sc.nextDouble();
        sc.nextLine();
        cuenta.setSaldo(saldo);
        System.out.println("Ingrese margen: ");
        Double margen = sc.nextDouble();
        sc.nextLine();
        cuenta.setMargen(margen);
        
        System.out.println("Ingrese ID tipo de cuenta: ");
        int idTipo = sc.nextInt();
        sc.nextLine();
        TipoCuenta tc = tcJPA.ObjetoTipoC(idTipo);
        if (tc != null)
            cuenta.setIdTipoCuenta(tc);

        System.out.println("Ingrese NIT de cliente: ");
        String nit = sc.nextLine();
        Cliente c = cJPA.ObjetoTipoC(nit);
        if (c != null)
            cuenta.setNitCliente(c);
        
        if (cuenta.getIdTipoCuenta() != null && cuenta.getNitCliente()!= null){
            CuentaJPA.Create(cuenta);
        } 
        else{
            System.out.println("\nID y NIT necesarios para realizar esta acción");
        }
       
        Continuar();

    }
    
    public void ModificarCuenta(){
        
        Cuenta cuenta = new Cuenta();
        System.out.println("--------ACTUALIZAR CUENTA--------\n");
        
        System.out.println("Ingrese ID de cuenta a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();
        cuenta.setIdCuenta(id);
        
        System.out.println("Ingrese saldo: ");
        Double saldo = sc.nextDouble();
        sc.nextLine();
        cuenta.setSaldo(saldo);
        System.out.println("Ingrese margen: ");
        Double margen = sc.nextDouble();
        sc.nextLine();
        cuenta.setMargen(margen);
        
        System.out.println("Ingrese ID tipo de cuenta: ");
        int idTipo = sc.nextInt();
        sc.nextLine();
        TipoCuenta tc = tcJPA.ObjetoTipoC(idTipo);
        if (tc != null)
            cuenta.setIdTipoCuenta(tc);

        System.out.println("Ingrese NIT de cliente: ");
        String nit = sc.nextLine();
        Cliente c = cJPA.ObjetoTipoC(nit);
        if (c != null)
            cuenta.setNitCliente(c);
        
        if (cuenta.getIdTipoCuenta() != null && cuenta.getNitCliente()!= null){
            CuentaJPA.Update(cuenta);
        } 
        else{
            System.out.println("\nID y NIT necesarios para realizar esta acción");
        }
       
        Continuar();
    }
    
    public void BuscarCuenta(){
        
        System.out.println("--------BUSCAR CUENTA--------\n");
        System.out.println("Ingrese ID de cuenta: ");
        int ID = sc.nextInt();
        sc.nextLine();
                
        boolean bandera = false;
        List <Cuenta> cuentas = new ArrayList<>();

        cuentas = CuentaJPA.findCuentaEntities();
        
        for (Cuenta c : cuentas){
            if (c.getIdCuenta() == ID){
                bandera = true;
                System.out.println("\nID: " + c.getIdCuenta());
                System.out.println("Saldo: " + c.getSaldo());
                System.out.println("Margen: " + c.getMargen());
                System.out.println("Tipo de Cuenta: " + c.getIdTipoCuenta().getDescripcion());
                System.out.println("NIT de cliente: " + c.getNitCliente().getNitCliente());
                System.out.println("Nombre del cliente: " + c.getNitCliente().getNombre() + "\n");
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Cuenta no existente según ID");
        
        Continuar();
    }
    
    public void EliminarCuenta(){

        System.out.println("--------ELIMINAR CUENTA--------\n");
        
        System.out.println("Ingrese ID de la cuenta a ELIMINAR: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        CuentaJPA.Destroy(ID);
        Continuar();
    }
    
    public void ListarCuentas(){
        List <Cuenta> cuentas = new ArrayList<>();

        cuentas = CuentaJPA.findCuentaEntities();
        
        System.out.println("--------REPORTE DE CUENTAS--------\n");
        
        for (Cuenta c : cuentas){
            System.out.println("ID: " + c.getIdCuenta());
            System.out.println("Saldo: " + c.getSaldo());
            System.out.println("Margen: " + c.getMargen());
            System.out.println("Tipo de Cuenta: " + c.getIdTipoCuenta().getDescripcion());
            System.out.println("NIT de cliente: " + c.getNitCliente().getNitCliente());
            System.out.println("Nombre del cliente: " + c.getNitCliente().getNombre() + "\n");
        }
        Continuar();
    } 
    
    public int Contador(){
        List <Cuenta> cuentas = new ArrayList<>();

        cuentas = CuentaJPA.findCuentaEntities();
        
        int contador = 0;
        
        for (Cuenta c : cuentas){
            contador = c.getIdCuenta();
        }
        return contador + 1;
    }    
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....");
        sc.nextLine();
    }
}
