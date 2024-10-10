/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isa
 */
public class ClienteControlador {
    

    // ESTA CLASE CONTROLADOR YO LA CREÉ DESDE CERO, DEBEN CREAR UNA PARA CADA MODULO
    // EN LOS CONTROLADORES ES DONDE SE PIDEN LOS DATOS A VALIDAR PARA MANDARLOS A LOS MÉTODOS DE LAS JPA
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    ClienteJpaController ClienteJPA = new ClienteJpaController(emf);  
    Scanner sc = new Scanner(System.in); 
    
    public void CrearCliente(){

        Cliente cliente = new Cliente();
        
        System.out.println("--------CREAR CLIENTE--------\n");
        
        System.out.println("Ingrese NIT del cliente: ");
        String nit = sc.nextLine();
        cliente.setNitCliente(nit);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        cliente.setNombre(nombre);
        System.out.println("Ingrese DPI: ");
        String dpi = sc.nextLine();
        cliente.setDpi(dpi);
        System.out.println("Ingrese dirección: ");
        String direccion = sc.nextLine();
        cliente.setDireccion(direccion);
        System.out.println("Ingrese correo: ");
        String correo = sc.nextLine();
        cliente.setCorreo(correo);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        cliente.setTelefono(telefono);
        
        ClienteJPA.Create(cliente);
        Continuar();
    }
    
    public void ModificarCliente(){
        
        Cliente cliente = new Cliente();

        System.out.println("--------MODIFICAR CLIENTE--------\n");
        
        System.out.println("Ingrese NIT del cliente a MODIFICAR: ");
        String nit = sc.nextLine();
        cliente.setNitCliente(nit);
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        cliente.setNombre(nombre);
        System.out.println("Ingrese DPI: ");
        String dpi = sc.nextLine();
        cliente.setDpi(dpi);
        System.out.println("Ingrese dirección: ");
        String direccion = sc.nextLine();
        cliente.setDireccion(direccion);
        System.out.println("Ingrese correo: ");
        String correo = sc.nextLine();
        cliente.setCorreo(correo);
        System.out.println("Ingrese teléfono: ");
        String telefono = sc.nextLine();
        cliente.setTelefono(telefono);
        
        ClienteJPA.Update(cliente);
        Continuar();
    }
    
    public void BuscarCliente(){
        
        System.out.println("--------BUSCAR CLIENTE--------\n");
        System.out.println("Ingrese NIT del cliente: ");
        String nit = sc.nextLine();
        
        List <Cliente> clientes = new ArrayList<>();
        boolean bandera = false;
        clientes = ClienteJPA.findClienteEntities();
        
        for (Cliente c : clientes){
            if (c.getNitCliente().equals(nit)){
                bandera = true;
                System.out.println("NIT: " + c.getNitCliente());
                System.out.println("Nombre: " + c.getNombre());
                System.out.println("Dirección: " + c.getDireccion());
                System.out.println("Correo: " + c.getCorreo());
                System.out.println("DPI: " + c.getDpi());
                System.out.println("Teléfono: " + c.getTelefono());
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Cliente no existente según NIT");
        
        Continuar();
    }
    
    public void EliminarCliente(){

        System.out.println("--------ELIMINAR CLIENTE--------\n");
        
        System.out.println("Ingrese NIT del cliente a ELIMINAR: ");
        String nit = sc.nextLine();
        
        ClienteJPA.Destroy(nit);
        Continuar();
    }
    
    public void ListarClientes(){
        List <Cliente> clientes = new ArrayList<>();

        clientes = ClienteJPA.findClienteEntities();
        
        System.out.println("--------REPORTE DE CLIENTES--------\n");
        
        for (Cliente c : clientes){
            System.out.println("NIT: " + c.getNitCliente());
            System.out.println("Nombre: " + c.getNombre());
            System.out.println("Dirección: " + c.getDireccion());
            System.out.println("Correo: " + c.getCorreo());
            System.out.println("DPI: " + c.getDpi());
            System.out.println("Teléfono: " + c.getTelefono() + "\n");
        }
        Continuar();
    } 
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....\n");
        sc.nextLine();
    }
}
