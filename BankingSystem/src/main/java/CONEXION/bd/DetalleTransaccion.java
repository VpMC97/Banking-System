/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION.bd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "DETALLE_TRANSACCION", catalog = "BancoBD", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "DetalleTransaccion.findAll", query = "SELECT d FROM DetalleTransaccion d")})
public class DetalleTransaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle", nullable = false)
    private Integer idDetalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto", precision = 53)
    private Double monto;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo_transaccion")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoTransaccion idTipo;
    @JoinColumn(name = "id_transaccion", referencedColumnName = "id_transaccion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Transaccion idTransaccion;

    public DetalleTransaccion() {
    }

    public DetalleTransaccion(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public TipoTransaccion getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoTransaccion idTipo) {
        this.idTipo = idTipo;
    }

    public Transaccion getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Transaccion idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleTransaccion)) {
            return false;
        }
        DetalleTransaccion other = (DetalleTransaccion) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.bd.DetalleTransaccion[ idDetalle=" + idDetalle + " ]";
    }
    
}
