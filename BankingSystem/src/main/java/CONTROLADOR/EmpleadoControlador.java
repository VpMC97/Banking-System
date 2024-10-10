/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.Empleado;
import CONEXION.bd.EmpleadoJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isa
 */
public class EmpleadoControlador {
            
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    EmpleadoJpaController EmpleadoJPA = new EmpleadoJpaController(emf);  
    Scanner sc = new Scanner(System.in); 
    
    public void CrearEmpleado(){

        Empleado empleado = new Empleado();
        
        System.out.println("--------CREAR EMPLEADO--------\n");
        
        int id = Contador();
        
        System.out.println("Empleado ID " + id);
        empleado.setIdEmpleado(id);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        empleado.setNombre(nombre);
        System.out.println("Ingrese puesto: ");
        String puesto = sc.nextLine();
        empleado.setPuesto(puesto);
        System.out.println("Ingrese correo: ");
        String correo = sc.nextLine();
        empleado.setCorreo(correo);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        empleado.setTelefono(telefono);
        
        EmpleadoJPA.Create(empleado);
        Continuar();

    }
    
    public void ModificarEmpleado(){
        
        Empleado empleado = new Empleado();
        

        System.out.println("--------MODIFICAR EMPLEADO--------\n");
        
        System.out.println("Ingrese ID de empleado a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();
        empleado.setIdEmpleado(id);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        empleado.setNombre(nombre);
        System.out.println("Ingrese puesto: ");
        String puesto = sc.nextLine();
        empleado.setPuesto(puesto);
        System.out.println("Ingrese correo: ");
        String correo = sc.nextLine();
        empleado.setCorreo(correo);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        empleado.setTelefono(telefono);
        
        EmpleadoJPA.Update(empleado);
        Continuar();
    }
    
    public void BuscarEmpleado(){
        
        System.out.println("--------BUSCAR EMPLEADO--------\n");
        System.out.println("Ingrese ID del empleado: ");
        int ID = sc.nextInt();
        sc.nextLine();
                
        boolean bandera = false;
        List <Empleado> empleados = new ArrayList<>();

        empleados = EmpleadoJPA.findEmpleadoEntities();
        
        for (Empleado e : empleados){
            if (e.getIdEmpleado() == ID){
                bandera = true;
                System.out.println("ID: " + e.getIdEmpleado());
                System.out.println("Nombre: " + e.getNombre());
                System.out.println("Puesto: " + e.getPuesto());
                System.out.println("Correo: " + e.getCorreo());
                System.out.println("Teléfono: " + e.getTelefono());
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Empleado no existente según ID");
        
        Continuar();
    }
    
    public void EliminarEmpleado(){

        System.out.println("--------ELIMINAR EMPLEADO--------\n");
        
        System.out.println("Ingrese ID de empelado a ELIMINAR: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        EmpleadoJPA.Destroy(ID);
        Continuar();
    }
    
    public void ListarEmpleados(){
        List <Empleado> empleados = new ArrayList<>();

        empleados = EmpleadoJPA.findEmpleadoEntities();
        
        System.out.println("--------REPORTE DE EMPLEADOS--------\n");
        
        for (Empleado e : empleados){
            System.out.println("ID: " + e.getIdEmpleado());
            System.out.println("Nombre: " + e.getNombre());
            System.out.println("Puesto: " + e.getPuesto());
            System.out.println("Correo: " + e.getCorreo());
            System.out.println("Teléfono: " + e.getTelefono() + "\n");
        }
        Continuar();
    } 
    
    public int Contador(){
        List <Empleado> empleados = new ArrayList<>();

        empleados = EmpleadoJPA.findEmpleadoEntities();
        
        int contador = 0;
        
        for (Empleado e : empleados){
            contador = e.getIdEmpleado();
        }
        return contador + 1;
    }    
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....");
        sc.nextLine();
    }
}
