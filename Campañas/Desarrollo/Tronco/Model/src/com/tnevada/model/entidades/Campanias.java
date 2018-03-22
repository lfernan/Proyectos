package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "CAMPANIAS")
public class Campanias implements Serializable {
    private static final long serialVersionUID = -8185885432197600877L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_campania")
    @SequenceGenerator(name = "seq_campania", sequenceName = "SEQ_CAMPANIA", initialValue = 1, allocationSize=1)    
    @Column(name = "CAMP_ID", nullable = false)
    private Long campId;
    @Column(name = "CAMP_CANTIDAD_CLIENTES")
    private Long campCantidadClientes;
    @Column(name = "CAMP_CARTERA", length = 100)
    private String campCartera;
    @Column(name = "CAMP_CATEGORIA", length = 1)
    private String campCategoria;
    @Column(name = "CAMP_DESCRIPCION", length = 1000)
    private String campDescripcion;
    @Column(name = "CAMP_ENTIDAD_DESTINO")
    private Long campEntidadDestino;
    @Temporal(TemporalType.DATE)
    @Column(name = "CAMP_FECHA_ALTA")
    private Date campFechaAlta;
    @Temporal(TemporalType.DATE)
    @Column(name = "CAMP_FECHA_VIGENCIA_DESDE")
    private Date campFechaVigenciaDesde;
    @Temporal(TemporalType.DATE)
    @Column(name = "CAMP_FECHA_VIGENCIA_HASTA")
    private Date campFechaVigenciaHasta;    
    @Column(name = "CAMP_LEGAJO_USUARIO")
    private Integer campLegajoUsuario;
    @Column(name = "CAMP_OBJETIVO_CONS_ID", length = 40)
    private String campObjetivoConsId;
    @Column(name = "CAMP_PERIODO")
    private Long campPeriodo;
    @Column(name = "CAMP_SECTOR_SOLICITANTE")
    private Long campSectorSolicitante;
    @Column(name = "CAMP_SUBSECTOR_SOLICITANTE")
    private Long campSubsectorSolicitante;
    @ManyToOne
    @JoinColumn(name = "CAMP_TIPOCAMP_ID", nullable = false)
    private TipoCampanias tipoCampanias;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campania")
    private List<CampaniasBase> campaniaBase;
    @Transient
    private String tipoCampaniasDescripcion;
    
    public Campanias(){
    }
    
    public Campanias(Long campId, Long campCantidadClientes, String campCartera, String campCategoria,
                     String campDescripcion, Long campEntidadDestino, Date campFechaAlta, Date campFechaVigenciaDesde,
                     Date campFechaVigenciaHasta, Integer campLegajoUsuario, String campObjetivoConsId,
                     Long campPeriodo, Long campSectorSolicitante, Long campSubsectorSolicitante,
                     TipoCampanias tipoCampanias, List<CampaniasBase> campaniaBase, String tipoCampaniasDescripcion) {
        super();
        this.campId = campId;
        this.campCantidadClientes = campCantidadClientes;
        this.campCartera = campCartera;
        this.campCategoria = campCategoria;
        this.campDescripcion = campDescripcion;
        this.campEntidadDestino = campEntidadDestino;
        this.campFechaAlta = campFechaAlta;
        this.campFechaVigenciaDesde = campFechaVigenciaDesde;
        this.campFechaVigenciaHasta = campFechaVigenciaHasta;
        this.campLegajoUsuario = campLegajoUsuario;
        this.campObjetivoConsId = campObjetivoConsId;
        this.campPeriodo = campPeriodo;
        this.campSectorSolicitante = campSectorSolicitante;
        this.campSubsectorSolicitante = campSubsectorSolicitante;
        this.tipoCampanias = tipoCampanias;
        this.campaniaBase = campaniaBase;
        this.tipoCampaniasDescripcion = tipoCampaniasDescripcion;
    }

    public void setCampId(Long campId) {
        this.campId = campId;
    }

    public Long getCampId() {
        return campId;
    }

    public void setCampCantidadClientes(Long campCantidadClientes) {
        this.campCantidadClientes = campCantidadClientes;
    }

    public Long getCampCantidadClientes() {
        return campCantidadClientes;
    }

    public void setCampCartera(String campCartera) {
        this.campCartera = campCartera;
    }

    public String getCampCartera() {
        return campCartera;
    }

    public void setCampCategoria(String campCategoria) {
        this.campCategoria = campCategoria;
    }

    public String getCampCategoria() {
        return campCategoria;
    }

    public void setCampDescripcion(String campDescripcion) {
        this.campDescripcion = campDescripcion;
    }

    public String getCampDescripcion() {
        return campDescripcion;
    }

    public void setCampEntidadDestino(Long campEntidadDestino) {
        this.campEntidadDestino = campEntidadDestino;
    }

    public Long getCampEntidadDestino() {
        return campEntidadDestino;
    }

    public void setCampFechaAlta(Date campFechaAlta) {
        this.campFechaAlta = campFechaAlta;
    }

    public Date getCampFechaAlta() {
        return campFechaAlta;
    }

    public void setCampFechaVigenciaDesde(Date campFechaVigenciaDesde) {
        this.campFechaVigenciaDesde = campFechaVigenciaDesde;
    }

    public Date getCampFechaVigenciaDesde() {
        return campFechaVigenciaDesde;
    }

    public void setCampFechaVigenciaHasta(Date campFechaVigenciaHasta) {
        this.campFechaVigenciaHasta = campFechaVigenciaHasta;
    }

    public Date getCampFechaVigenciaHasta() {
        return campFechaVigenciaHasta;
    }

    public void setCampLegajoUsuario(Integer campLegajoUsuario) {
        this.campLegajoUsuario = campLegajoUsuario;
    }

    public Integer getCampLegajoUsuario() {
        return campLegajoUsuario;
    }

    public void setCampObjetivoConsId(String campObjetivoConsId) {
        this.campObjetivoConsId = campObjetivoConsId;
    }

    public String getCampObjetivoConsId() {
        return campObjetivoConsId;
    }

    public void setCampPeriodo(Long campPeriodo) {
        this.campPeriodo = campPeriodo;
    }

    public Long getCampPeriodo() {
        return campPeriodo;
    }

    public void setCampSectorSolicitante(Long campSectorSolicitante) {
        this.campSectorSolicitante = campSectorSolicitante;
    }

    public Long getCampSectorSolicitante() {
        return campSectorSolicitante;
    }

    public void setCampSubsectorSolicitante(Long campSubsectorSolicitante) {
        this.campSubsectorSolicitante = campSubsectorSolicitante;
    }

    public Long getCampSubsectorSolicitante() {
        return campSubsectorSolicitante;
    }

    public void setTipoCampanias(TipoCampanias tipoCampanias) {
        this.tipoCampanias = tipoCampanias;
    }

    public TipoCampanias getTipoCampanias() {
        return tipoCampanias;
    }

    public void setCampaniaBase(List<CampaniasBase> campaniaBase) {
        this.campaniaBase = campaniaBase;
    }

    public List<CampaniasBase> getCampaniaBase() {
        return campaniaBase;
    }

    public void setTipoCampaniasDescripcion(String tipoCampaniasDescripcion) {
        this.tipoCampaniasDescripcion = tipoCampaniasDescripcion;
    }

    public String getTipoCampaniasDescripcion() {
        return tipoCampaniasDescripcion;
    }
}
