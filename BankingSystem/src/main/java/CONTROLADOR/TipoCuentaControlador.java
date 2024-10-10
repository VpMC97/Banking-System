/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

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
public class TipoCuentaControlador {
            
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    TipoCuentaJpaController TipoCuentaJPA = new TipoCuentaJpaController(emf);  
    Scanner sc = new Scanner(System.in); 
    
    public void CrearTipoCuenta(){

        TipoCuenta tipoCuenta = new TipoCuenta();
        
        System.out.println("--------AGREGAR TIPO DE CUENTA-------\n");
        
        System.out.println("Ingrese ID: ");
        int id = sc.nextInt();
        tipoCuenta.setIdTipoCuenta(id);
        sc.nextLine();
        System.out.println("Ingrese descripción: ");
        String descripcion = sc.nextLine();
        tipoCuenta.setDescripcion(descripcion);
        

        TipoCuentaJPA.Create(tipoCuenta);
        Continuar();
       
    }
    
    public void ModificarTipoCuenta(){
        
        TipoCuenta tipoCuenta = new TipoCuenta();

        System.out.println("--------MODIFICAR TIPO DE CUENTA--------\n");
        
        System.out.println("Ingrese ID de tipo de cuenta a MODIFICAR: ");
        int id = sc.nextInt();
        tipoCuenta.setIdTipoCuenta(id);
        sc.nextLine();
        System.out.println("Ingrese descripción: ");
        String descripcion = sc.nextLine();
        tipoCuenta.setDescripcion(descripcion);
        
        TipoCuentaJPA.Update(tipoCuenta);

        Continuar();
    }
    
    public void BuscarTipoCuenta(){
        
        System.out.println("--------BUSCAR TIPO DE CUENTA--------\n");
        System.out.println("Ingrese ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        boolean bandera = false;
        List <TipoCuenta> tipoCuentas = new ArrayList<>();

        tipoCuentas = TipoCuentaJPA.findTipoCuentaEntities();
        
        for (TipoCuenta tc : tipoCuentas){
            if (tc.getIdTipoCuenta() == id){
                bandera = true;
                System.out.println("ID: " + tc.getIdTipoCuenta());
                System.out.println("Descripción: " + tc.getDescripcion());
                break;
            }
        }
        if (bandera!=true)
            System.out.println("\nTipo de cuenta no existente según ID");
        
        Continuar();
    }
    
    public void EliminarTipoCuenta(){

        System.out.println("--------ELIMINAR TIPO DE CUENTA--------\n");
        
        System.out.println("Ingrese ID de tipo de cuenta a ELIMINAR: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        TipoCuentaJPA.Destroy(id);
        Continuar();
    }
    
    public void ListarTipoCuenta(){
        List <TipoCuenta> tipoCuentas = new ArrayList<>();

        tipoCuentas = TipoCuentaJPA.findTipoCuentaEntities();
        
        System.out.println("--------REPORTE DE TIPO DE CUENTA--------\n");
        
        for (TipoCuenta tc : tipoCuentas){
            System.out.println("ID: " + tc.getIdTipoCuenta());
                System.out.println("Descripción: " + tc.getDescripcion() + "\n");
        }
        Continuar();
    } 
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....\n");
        sc.nextLine();
    }
}
