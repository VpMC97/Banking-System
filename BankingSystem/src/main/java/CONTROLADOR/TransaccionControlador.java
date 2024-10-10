/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.Cuenta;
import CONEXION.bd.CuentaJpaController;
import CONEXION.bd.Empleado;
import CONEXION.bd.EmpleadoJpaController;
import CONEXION.bd.Sucursal;
import CONEXION.bd.SucursalJpaController;
import CONEXION.bd.TipoTransaccion;
import CONEXION.bd.TipoTransaccionJpaController;
import CONEXION.bd.Transaccion;
import CONEXION.bd.TransaccionJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class TransaccionControlador {
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    TransaccionJpaController TransaccionJPA = new TransaccionJpaController(emf); 
    CuentaJpaController cuJPA = new CuentaJpaController(emf);
    EmpleadoJpaController eJPA = new EmpleadoJpaController(emf);
    SucursalJpaController sJPA = new SucursalJpaController(emf);
    TipoTransaccionControlador ttc = new TipoTransaccionControlador();
    DetalleControlador dc = new DetalleControlador();
    TipoTransaccionJpaController ttJPA = new TipoTransaccionJpaController(emf);
            
    Scanner sc = new Scanner(System.in); 
    
    public void CrearTransaccion(){

        Transaccion transaccion = new Transaccion();
        
        System.out.println("--------NUEVA TRANSACCIÓN--------\n");
        
        int id = Contador();
        
        System.out.println("Transacción ID " + id);
        transaccion.setIdTransaccion(id);
        
        Date fecha = new Date();
        DateFormat dateF=new SimpleDateFormat("dd/MM/yy");
        transaccion.setFecha(fecha);
        System.out.println(dateF.format(transaccion.getFecha()));
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date hora = new Date();
        System.out.println(dateFormat.format(hora));
        transaccion.setHora(hora);
        
        System.out.println("Ingrese ID de cuenta: ");
        int idCuenta = sc.nextInt();
        sc.nextLine();
        Cuenta c = cuJPA.ObjetoTipoC(idCuenta);
        if (c != null)
            transaccion.setIdCuenta(c);
        
        System.out.println("Ingrese ID de empleado: ");
        int idEmpleado = sc.nextInt();
        sc.nextLine();
        Empleado e = eJPA.ObjetoTipoE(idEmpleado);
        if (e != null)
            transaccion.setIdEmpleado(e);
        
        System.out.println("Ingrese ID de sucursal: ");
        int idSucursal = sc.nextInt();
        sc.nextLine();
        Sucursal s = sJPA.ObjetoTipoS(idSucursal);
        if (s != null)
            transaccion.setIdSucursal(s);   
        
        if (transaccion.getIdEmpleado() != null && transaccion.getIdSucursal() != null && transaccion.getIdCuenta() != null){
            System.out.println("Tipo de Transaccion a realizar: ");
            ttc.ReporteTipoTransacciones();
            int tipo = sc.nextInt();
            sc.nextLine();
            TipoTransaccion tt = ttJPA.ObjetoTipoT(tipo);
            if (tt != null){
                TransaccionJPA.create(transaccion);
                int idtrans = Contador() - 1;
                transaccion = TransaccionJPA.findTransaccion(idtrans);
                dc.CrearDetalle(transaccion, tt);
            }
            else{
                System.out.println("TIPO DE TRANSACCIÓN NO EXISTENTE");
                System.out.println("NO SE PUDO REALIZAR LA TRANSACCIÓN");
            }
        } 
        else{
            System.out.println("\ncampos empleado, sucursal y cuenta necesarios para realizar esta acción");
        }
        Continuar();
    }
    
//    public void ModificarCuenta(){
//        
//        Cuenta cuenta = new Cuenta();
//        System.out.println("--------ACTUALIZAR CUENTA--------\n");
//        
//        System.out.println("Ingrese ID de cuenta a modificar: ");
//        int id = sc.nextInt();
//        sc.nextLine();
//        cuenta.setIdCuenta(id);
//        
//        System.out.println("Ingrese saldo: ");
//        Double saldo = sc.nextDouble();
//        sc.nextLine();
//        cuenta.setSaldo(saldo);
//        System.out.println("Ingrese margen: ");
//        Double margen = sc.nextDouble();
//        sc.nextLine();
//        cuenta.setMargen(margen);
//        
//        System.out.println("Ingrese ID tipo de cuenta: ");
//        int idTipo = sc.nextInt();
//        sc.nextLine();
//        TipoCuenta tc = tcJPA.ObjetoTipoC(idTipo);
//        if (tc != null)
//            cuenta.setIdTipoCuenta(tc);
//
//        System.out.println("Ingrese NIT de cliente: ");
//        String nit = sc.nextLine();
//        Cliente c = cJPA.ObjetoTipoC(nit);
//        if (c != null)
//            cuenta.setNitCliente(c);
//        
//        if (cuenta.getIdTipoCuenta() != null && cuenta.getNitCliente()!= null){
//            CuentaJPA.Edit(cuenta);
//        } 
//        else{
//            System.out.println("\nID y NIT necesarios para realizar esta acción");
//        }
//       
//        Continuar();
//    }
    
    public void BuscarTransacción(){
        
        System.out.println("--------BUSCAR TRANSACCIÓN--------\n");
        System.out.println("Ingrese ID de transacción: ");
        int ID = sc.nextInt();
        sc.nextLine();
                
        boolean bandera = false;
        List <Transaccion> transacciones = new ArrayList<>();

        transacciones = TransaccionJPA.findTransaccionEntities();
        
        for (Transaccion t : transacciones){
            if (t.getIdTransaccion() == ID){
                DateFormat dateF=new SimpleDateFormat("dd/MM/yy");
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                
                bandera = true;
                System.out.println("\nID: " + t.getIdTransaccion());
                System.out.println("Fecha: " + dateF.format(t.getFecha()));
                System.out.println("Hora: " + dateFormat.format(t.getHora()));
                System.out.println("ID empleado: " + t.getIdEmpleado().getIdEmpleado());
                System.out.println("Empelado: " +  t.getIdEmpleado().getNombre());
                System.out.println("ID cuenta: " + t.getIdCuenta().getIdCuenta());
                System.out.println("Nombre de cliente: " + t.getIdCuenta().getNitCliente().getNombre());
                System.out.println("ID Sucursal: " + t.getIdSucursal().getIdSucursal() + "'\n");
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Transacción no existente según ID");
        
        Continuar();
    }
    
    public void EliminarTransaccion(){

        System.out.println("--------ELIMINAR TRANSACCIÓN--------\n");
        
        System.out.println("Ingrese ID de la transacción a ELIMINAR: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        try {
            dc.EliminarDetalle(ID);
            TransaccionJPA.destroy(ID);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TransaccionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Continuar();
    }
    
    public void ListarTransacciones(){
        List <Transaccion> transacciones = new ArrayList<>();

        transacciones = TransaccionJPA.findTransaccionEntities();
        
        System.out.println("--------REPORTE DE TRANSACCIONES--------\n");
        
        for (Transaccion t : transacciones){
            DateFormat dateF=new SimpleDateFormat("dd/MM/yy");
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            
            System.out.println("\nID: " + t.getIdTransaccion());
            System.out.println("Fecha: " + dateF.format(t.getFecha()));
            System.out.println("Hora: " + dateFormat.format(t.getHora()));
            System.out.println("ID empleado: " + t.getIdEmpleado().getIdEmpleado());
            System.out.println("Empelado: " +  t.getIdEmpleado().getNombre());
            System.out.println("ID cuenta: " + t.getIdCuenta().getIdCuenta());
            System.out.println("Nombre de cliente: " + t.getIdCuenta().getNitCliente().getNombre());
            System.out.println("ID Sucursal: " + t.getIdSucursal().getIdSucursal() + "'\n");
        }
        Continuar();
    } 
    
    public int Contador(){
        List <Transaccion> transacciones = new ArrayList<>();

        transacciones = TransaccionJPA.findTransaccionEntities();
        
        int contador = 0;
        
        for (Transaccion t : transacciones){
            contador = t.getIdTransaccion();
        }
        return contador + 1;
    }    
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....");
        sc.nextLine();
    }
}
