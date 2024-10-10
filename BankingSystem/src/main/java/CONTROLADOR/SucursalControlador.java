/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.Sucursal;
import CONEXION.bd.SucursalJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isa
 */
public class SucursalControlador {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    SucursalJpaController SucursalJPA = new SucursalJpaController(emf);  
    Scanner sc = new Scanner(System.in); 
    
    public void CrearSucursal(){

        Sucursal sucursal = new Sucursal();
        
        System.out.println("--------AGREGAR SUCURSAL--------\n");
        
        int id = Contador();
        
        System.out.println("ID: " + id);
        sucursal.setIdSucursal(id);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        sucursal.setNombre(nombre);
        System.out.println("Ingrese dirección: ");
        String direccion = sc.nextLine();
        sucursal.setDireccion(direccion);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        sucursal.setTelefono(telefono);
        System.out.println("Ingrese horario: ");
        String horario = sc.nextLine();
        sucursal.setHorario(horario);
        
        SucursalJPA.Create(sucursal);
        Continuar();
    }
    
    public void ModificarSucursal(){
        
        Sucursal sucursal = new Sucursal();
        
         System.out.println("--------MODIFICAR SUCURSAL--------\n");
        
        System.out.println("Ingrese ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        sucursal.setIdSucursal(id);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        sucursal.setNombre(nombre);
        System.out.println("Ingrese dirección: ");
        String direccion = sc.nextLine();
        sucursal.setDireccion(direccion);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        sucursal.setTelefono(telefono);
        System.out.println("Ingrese horario: ");
        String horario = sc.nextLine();
        sucursal.setHorario(horario);

        SucursalJPA.Update(sucursal);
        Continuar();
    }
    
    public void BuscarSucursal(){
        
        System.out.println("--------BUSCAR SUCURSAL--------\n");
        System.out.println("Ingrese ID de sucursal: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        boolean bandera = false;
        List <Sucursal> sucursales = new ArrayList<>();

        sucursales = SucursalJPA.findSucursalEntities();
        
        for (Sucursal s : sucursales){
            if (s.getIdSucursal() == ID){
                bandera = true;
                System.out.println("ID: " + s.getIdSucursal());
                System.out.println("Nombre: " + s.getNombre());
                System.out.println("Dirección: " + s.getDireccion());
                System.out.println("Teléfono: " + s.getTelefono());
                System.out.println("Horario: " + s.getHorario());
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Sucursal no existente según ID");
            
        Continuar();
    }
    
    public void EliminarSucursal(){

        System.out.println("--------ELIMINAR SUCURSAL--------\n");
        
        System.out.println("Ingrese ID de sucursal a ELIMINAR: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        SucursalJPA.Destroy(ID);
        Continuar();
    }
    
    public void ListarSucursales(){
        List <Sucursal> sucursales = new ArrayList<>();

        sucursales = SucursalJPA.findSucursalEntities();
        
        System.out.println("--------REPORTE DE SUCURSALES--------\n");
        
        for (Sucursal s : sucursales){
            System.out.println("ID: " + s.getIdSucursal());
            System.out.println("Nombre: " + s.getNombre());
            System.out.println("Dirección: " + s.getDireccion());
            System.out.println("Teléfono: " + s.getTelefono());
            System.out.println("Horario: " + s.getHorario() + "\n");
        }
        Continuar();
    } 
    
    public int Contador(){
        List <Sucursal> sucursales = new ArrayList<>();

        sucursales = SucursalJPA.findSucursalEntities();
        
        int contador = 0;
        
        for (Sucursal s : sucursales){
            contador = s.getIdSucursal();
        }
        return contador + 1;
    }
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....");
        sc.nextLine();
    }
}
