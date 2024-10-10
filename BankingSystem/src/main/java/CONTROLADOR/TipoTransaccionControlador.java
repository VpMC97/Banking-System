/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.bd.TipoTransaccion;
import CONEXION.bd.TipoTransaccionJpaController;
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
public class TipoTransaccionControlador {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaBancario_jar_1.0-SNAPSHOTPU");
    TipoTransaccionJpaController TipoTransaccionJPA = new TipoTransaccionJpaController(emf);  
    Scanner sc = new Scanner(System.in); 
    
    public void CrearTipoTransaccion(){

        TipoTransaccion tipoTransaccion = new TipoTransaccion();
        
        System.out.println("--------AGREGAR TIPO DE TRANSACCIÓM--------\n");
        
        int id = Contador();
        
        System.out.println("ID: " + id);
        tipoTransaccion.setIdTipoTransaccion(id);
        System.out.println("Ingrese descripción: ");
        String descripcion = sc.nextLine();
        tipoTransaccion.setDescripcion(descripcion);
        
        TipoTransaccionJPA.Create(tipoTransaccion);
        Continuar();
    }
    
    public void ModificarTipoTransaccion(){
        
        TipoTransaccion tipoTransaccion = new TipoTransaccion();
        
        System.out.println("--------MODIFICAR TIPO DE TRANSACCIÓN--------\n");
        
        System.out.println("Ingrese ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        tipoTransaccion.setIdTipoTransaccion(id);
        System.out.println("Ingrese descripción: ");
        String descripcion = sc.nextLine();
        tipoTransaccion.setDescripcion(descripcion);
        

        try {
            TipoTransaccionJPA.Update(tipoTransaccion);
        } catch (Exception ex) {
            Logger.getLogger(TipoTransaccionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Continuar();
    }
    
    public void BuscarTipoTransaccion(){
        
        System.out.println("--------BUSCAR TIPO DE TRANSACCION--------\n");
        System.out.println("Ingrese ID de tipó de transacción: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        boolean bandera = false;
        List <TipoTransaccion> tipoTransacciones = new ArrayList<>();

        tipoTransacciones = TipoTransaccionJPA.findTipoTransaccionEntities();
        
        for (TipoTransaccion tt : tipoTransacciones){
            if (tt.getIdTipoTransaccion() == ID){
                bandera = true;
                System.out.println("ID: " + tt.getIdTipoTransaccion());
                System.out.println("Nombre: " + tt.getDescripcion());
                break;
            }
        }
        if (bandera!=true)
            System.out.println("Tipo de Transacción no existente según ID");
            
        Continuar();
    }
    
    public void EliminarTipoTransaccion(){

        System.out.println("--------ELIMINAR TIPO DE TRANSACCIÓN--------\n");
        
        System.out.println("Ingrese ID del tipo de transacción a ELIMINAR: ");
        int ID = sc.nextInt();
        sc.nextLine();
        
        TipoTransaccionJPA.Destroy(ID);
        Continuar();
    }
    
    public void ListarTipoTransacciones(){
        List <TipoTransaccion> tipoTransacciones = new ArrayList<>();

        tipoTransacciones = TipoTransaccionJPA.findTipoTransaccionEntities();
        
        System.out.println("--------REPORTE DE TIPO DE TRANSACCIONES--------\n");
        
        for (TipoTransaccion tt : tipoTransacciones){
            System.out.println("ID: " + tt.getIdTipoTransaccion());
            System.out.println("Nombre: " + tt.getDescripcion() + "\n");
        }
        Continuar();
    } 
    
    public void ReporteTipoTransacciones(){
        List <TipoTransaccion> tipoTransacciones = new ArrayList<>();

        tipoTransacciones = TipoTransaccionJPA.findTipoTransaccionEntities();

        for (TipoTransaccion tt : tipoTransacciones){
            System.out.println("ID. " + tt.getIdTipoTransaccion() + "   Nombre: " + tt.getDescripcion());
        }
    } 
    
    public int Contador(){
        List <TipoTransaccion> tipodeTransacciones = new ArrayList<>();

        tipodeTransacciones = TipoTransaccionJPA.findTipoTransaccionEntities();
        
        int contador = 0;
        
        for (TipoTransaccion tt : tipodeTransacciones){
            contador = tt.getIdTipoTransaccion();
        }
        return contador + 1;
    }
    
    public void Continuar(){
        System.out.println("\nPresione enter para continuar ....");
        sc.nextLine();
    }
}
