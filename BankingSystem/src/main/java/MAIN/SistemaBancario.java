/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package MAIN;

import CONTROLADOR.*;
import java.util.Scanner;

/**
 *
 * @author Isa
 */
public class SistemaBancario {

    private static int opcion = 0;
    private static int opcion2 = 0;
    private static final Scanner sc = new Scanner(System.in);
    
    // MAIN, ACÁ ESTAN EL MENÚ Y SUBMENÚ. ACCEDEMOS A LOS MÉTODOS DE CADA CLASE UTILIZANDO 
    // LAS CLASES CONTROLADORES (TIENEN QUE CREARLAS, EJ. ClienteControlador) 
    
    private static final ClienteControlador c = new ClienteControlador();
    private static final EmpleadoControlador e = new EmpleadoControlador();
    private static final SucursalControlador s = new SucursalControlador();
    private static final TransaccionControlador t = new TransaccionControlador();
    private static final TipoCuentaControlador tc = new TipoCuentaControlador();
    private static final TipoTransaccionControlador tt = new TipoTransaccionControlador();
    private static final CuentaControlador cu = new CuentaControlador();
    
    public static void main(String[] args) {   
        do{
            Menu();
        }while (opcion!=8);
    }
    
    private static void Menu(){
        
        System.out.println("=====================================");
        System.out.println("|           SISTEMA BANCARIO        |");
        System.out.println("=====================================");
        System.out.println("| MENÚ:                             |");
        System.out.println("|        1. Cliente                 |");
        System.out.println("|        2. Cuenta                  |");
        System.out.println("|        3. Empleado                |");
        System.out.println("|        4. Sucursal                |");
        System.out.println("|        5. Transacción             |");
        System.out.println("|        6. Tipo de Transacción     |");
        System.out.println("|        7. Tipo de Cuenta          |");
        System.out.println("|        8. Salir                   |");
        System.out.println("=====================================");
        
        System.out.println("Ingrese número de módulo: ");
        opcion = sc.nextInt();
        
        do{
            switch(opcion){
                case 1:
                    Submenu("CLIENTE");
                break;
                case 2:
                    Submenu("CUENTA");
                break;    
                case 3:
                    Submenu("EMPLEADO");
                break; 
                case 4:
                    Submenu("SUCURSAL");
                break;     
                case 5:
                    Submenu("TRANSACCIÓN");
                break;  
                case 6:
                    Submenu("TIPO DE TRANSACCIÓN");
                break;  
                case 7:
                    Submenu("TIPO DE CUENTA");
                break;  
                case 8:
                    System.out.println("Fin del programa.");
                break;
                default:
                    System.out.println("Opción inválida");
                break;
            }
        }while(opcion!=8);
        
    }
    
    private static void Submenu(String modulo){
        
        System.out.println("=====================================");
        System.out.println("|           " + modulo + "           |");
        System.out.println("=====================================");
        System.out.println("| SUBMENÚ:                          |");
        System.out.println("|        1. Crear                   |");
        System.out.println("|        2. Modificar               |");
        System.out.println("|        3. Buscar                  |");
        System.out.println("|        4. Eliminar                |");
        System.out.println("|        5. Reporte                 |");
        System.out.println("|        6. Menú principal          |");
        System.out.println("=====================================");
        
        System.out.println("Ingrese opción");
        opcion2 = sc.nextInt();
        
        switch(opcion2){
                case 1:
                    if (opcion == 1) 
                        c.CrearCliente();
                    else if (opcion == 2)
                        cu.CrearCuenta();
                    else if (opcion == 3)
                        e.CrearEmpleado();
                    else if (opcion == 4)
                        s.CrearSucursal();
                    else if (opcion == 5) 
                        t.CrearTransaccion();
                    else if (opcion == 6)
                        tt.CrearTipoTransaccion();
                    else if (opcion == 7)
                        tc.CrearTipoCuenta();
                break;
                case 2:
                    if (opcion == 1)
                        c.ModificarCliente();
                    else if (opcion == 2)
                        cu.ModificarCuenta();
                    else if (opcion == 3)
                        e.ModificarEmpleado();
                    else if (opcion == 4)
                        s.ModificarSucursal();
                    else if (opcion == 5) 
                        System.out.println("No es posible modificar una transacción");
                    else if (opcion == 6)
                        tt.ModificarTipoTransaccion();
                    else if (opcion == 7)
                        tc.ModificarTipoCuenta();
                break;
                case 3:
                    if (opcion == 1)
                        c.BuscarCliente();
                    else if (opcion == 2)
                        cu.BuscarCuenta();
                    else if (opcion == 3)
                        e.BuscarEmpleado();
                    else if (opcion == 4)
                        s.BuscarSucursal();
                    else if (opcion == 5) 
                        t.BuscarTransacción();
                    else if (opcion == 6)
                        tt.BuscarTipoTransaccion();
                    else if (opcion == 7)
                        tc.BuscarTipoCuenta();
                break;
                case 4:
                    if (opcion == 1)
                        c.EliminarCliente();
                    else if (opcion == 2)
                        cu.EliminarCuenta();
                    else if (opcion == 3)
                        e.EliminarEmpleado();
                    else if (opcion == 4)
                        s.EliminarSucursal();
                    else if (opcion == 5) 
                        t.EliminarTransaccion();
                    else if (opcion == 6)
                        tt.EliminarTipoTransaccion();
                    else if (opcion == 7)
                        tc.EliminarTipoCuenta();
                break;
                case 5:
                    if (opcion == 1)
                        c.ListarClientes();
                    else if (opcion == 2)
                        cu.ListarCuentas();
                    else if (opcion == 3)
                        e.ListarEmpleados();
                    else if (opcion == 4)
                        s.ListarSucursales();
                    else if (opcion == 5) 
                        t.ListarTransacciones();
                    else if (opcion == 6)
                        tt.ListarTipoTransacciones();
                    else if (opcion == 7)
                        tc.ListarTipoCuenta();
                break;
                case 6:
                    Menu();
                break;
                default:
                    System.out.println("Opción inválida");
                break;
            }
    }
 
}
