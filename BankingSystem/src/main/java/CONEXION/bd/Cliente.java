/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "CLIENTE", catalog = "BancoBD", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {

    //ESTAS CLASES SON LAS QUE TIENEN LOS ATRIBUTOS Y MÉTODOS GET & SET,
    //GUIARSE PARA OBTEMER LOS DATOS EN CONSOLA
    //NO MODIFICAR NADA DE ACÁ!!!
    
    private static final long serialVersionUID = 1L;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "direccion", length = 100)
    private String direccion;
    @Column(name = "correo", length = 50)
    private String correo;
    @Column(name = "dpi", length = 20)
    private String dpi;
    @Column(name = "telefono", length = 20)
    private String telefono;
    @Id
    @Basic(optional = false)
    @Column(name = "nit_cliente", nullable = false, length = 20)
    private String nitCliente;
    @OneToMany(mappedBy = "nitCliente", fetch = FetchType.LAZY)
    private List<Cuenta> cuentaList;

    public Cliente() {
    }

    public Cliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nitCliente != null ? nitCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.nitCliente == null && other.nitCliente != null) || (this.nitCliente != null && !this.nitCliente.equals(other.nitCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.bd.Cliente[ nitCliente=" + nitCliente + " ]";
    }
    
}
