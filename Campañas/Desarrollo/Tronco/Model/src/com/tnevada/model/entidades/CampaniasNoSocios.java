package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAMPANIAS_NO_SOCIOS")
public class CampaniasNoSocios implements Serializable {
    private static final long serialVersionUID = 1523927586597837968L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_no_socios")
    @SequenceGenerator(name = "seq_no_socios", sequenceName = "SEQ_NO_SOCIOS", initialValue = 1, allocationSize=1)  
    @Column(name = "CANS_ID", nullable = false)
    private Long cansId;
    @Column(name = "CANS_APELLIDOS", length = 1000)
    private String cansApellidos;
    @Column(name = "CANS_CONDICION_LABORAL")
    private Integer cansCondicionLaboral;
    @Column(name = "CANS_DOMICILIO", length = 2000)
    private String cansDomicilio;
    @Column(name = "CANS_EMAIL", length = 1000)
    private String cansEmail;
    @Column(name = "CANS_ESTADO_CIVIL")
    private Integer cansEstadoCivil;
    @Temporal(TemporalType.DATE)
    @Column(name = "CANS_FECHA_NACIMIENTO")
    private Date cansFechaNacimiento;
    @Column(name = "CANS_INGRESO_PROMEDIO")
    private Long cansIngresoPromedio;
    @Column(name = "CANS_NOMBRES", length = 1000)
    private String cansNombres;
    @Column(name = "CANS_NRO_DOCUMENTO")
    private Long cansNroDocumento;
    @Column(name = "CANS_NRO_TEL_1")
    private Long cansNroTel1;
    @Column(name = "CANS_NRO_TEL_2")
    private Long cansNroTel2;
    @Column(name = "CANS_NRO_TEL_3")
    private Long cansNroTel3;
    @Column(name = "CANS_NRO_TEL_4")
    private Long cansNroTel4;
    @Column(name = "CANS_PROVINCIA")
    private String cansProvincia;
    @Column(name = "CANS_SEXO", length = 1)
    private String cansSexo;
    @Column(name = "CANS_SUCURSAL")
    private Long cansSucursal;
    @Column(name = "CANS_TIPO_DOCUMENTO")
    private Integer cansTipoDocumento;
    @OneToMany(mappedBy = "campaniasNoSocios", cascade = CascadeType.ALL)
    //@OrderBy("cansmId DESC")        
    //@OrderColumn(name="CANSM_ID")
    private List<CampaniasNoSociosMensajes> campaniasNoSociosMensajesList;
    @OneToOne(mappedBy="campaniasNoSocios",cascade = CascadeType.ALL)
    private CampaniasBase campaniasBase;
    
    /*SELECT o FROM CampaniasNoSocios o, IN(o.campaniasNoSociosMensajesList) l 
    ORDER BY  l.cansmId DESC*/
    
    public CampaniasNoSocios(){
    }


    public CampaniasNoSocios(Long cansId, String cansApellidos, Integer cansCondicionLaboral, String cansDomicilio,
                             String cansEmail, Integer cansEstadoCivil, Date cansFechaNacimiento,
                             Long cansIngresoPromedio, String cansNombres, Long cansNroDocumento, Long cansNroTel1,
                             Long cansNroTel2, Long cansNroTel3, Long cansNroTel4, String cansProvincia,
                             String cansSexo, Long cansSucursal, Integer cansTipoDocumento,
                             List<CampaniasNoSociosMensajes> campaniasNoSociosMensajesList,
                             CampaniasBase campaniasBase) {
        super();
        this.cansId = cansId;
        this.cansApellidos = cansApellidos;
        this.cansCondicionLaboral = cansCondicionLaboral;
        this.cansDomicilio = cansDomicilio;
        this.cansEmail = cansEmail;
        this.cansEstadoCivil = cansEstadoCivil;
        this.cansFechaNacimiento = cansFechaNacimiento;
        this.cansIngresoPromedio = cansIngresoPromedio;
        this.cansNombres = cansNombres;
        this.cansNroDocumento = cansNroDocumento;
        this.cansNroTel1 = cansNroTel1;
        this.cansNroTel2 = cansNroTel2;
        this.cansNroTel3 = cansNroTel3;
        this.cansNroTel4 = cansNroTel4;
        this.cansProvincia = cansProvincia;
        this.cansSexo = cansSexo;
        this.cansSucursal = cansSucursal;
        this.cansTipoDocumento = cansTipoDocumento;
        this.campaniasNoSociosMensajesList = campaniasNoSociosMensajesList;
        this.campaniasBase = campaniasBase;
    }

    public void setCansId(Long cansId) {
        this.cansId = cansId;
    }

    public Long getCansId() {
        return cansId;
    }

    public void setCansApellidos(String cansApellidos) {
        this.cansApellidos = cansApellidos;
    }

    public String getCansApellidos() {
        return cansApellidos;
    }

    public void setCansCondicionLaboral(Integer cansCondicionLaboral) {
        this.cansCondicionLaboral = cansCondicionLaboral;
    }

    public Integer getCansCondicionLaboral() {
        return cansCondicionLaboral;
    }

    public void setCansDomicilio(String cansDomicilio) {
        this.cansDomicilio = cansDomicilio;
    }

    public String getCansDomicilio() {
        return cansDomicilio;
    }

    public void setCansEmail(String cansEmail) {
        this.cansEmail = cansEmail;
    }

    public String getCansEmail() {
        return cansEmail;
    }

    public void setCansEstadoCivil(Integer cansEstadoCivil) {
        this.cansEstadoCivil = cansEstadoCivil;
    }

    public Integer getCansEstadoCivil() {
        return cansEstadoCivil;
    }

    public void setCansFechaNacimiento(Date cansFechaNacimiento) {
        this.cansFechaNacimiento = cansFechaNacimiento;
    }

    public Date getCansFechaNacimiento() {
        return cansFechaNacimiento;
    }

    public void setCansIngresoPromedio(Long cansIngresoPromedio) {
        this.cansIngresoPromedio = cansIngresoPromedio;
    }

    public Long getCansIngresoPromedio() {
        return cansIngresoPromedio;
    }

    public void setCansNombres(String cansNombres) {
        this.cansNombres = cansNombres;
    }

    public String getCansNombres() {
        return cansNombres;
    }

    public void setCansNroDocumento(Long cansNroDocumento) {
        this.cansNroDocumento = cansNroDocumento;
    }

    public Long getCansNroDocumento() {
        return cansNroDocumento;
    }

    public void setCansNroTel1(Long cansNroTel1) {
        this.cansNroTel1 = cansNroTel1;
    }

    public Long getCansNroTel1() {
        return cansNroTel1;
    }

    public void setCansNroTel2(Long cansNroTel2) {
        this.cansNroTel2 = cansNroTel2;
    }

    public Long getCansNroTel2() {
        return cansNroTel2;
    }

    public void setCansNroTel3(Long cansNroTel3) {
        this.cansNroTel3 = cansNroTel3;
    }

    public Long getCansNroTel3() {
        return cansNroTel3;
    }

    public void setCansNroTel4(Long cansNroTel4) {
        this.cansNroTel4 = cansNroTel4;
    }

    public Long getCansNroTel4() {
        return cansNroTel4;
    }

    public void setCansProvincia(String cansProvincia) {
        this.cansProvincia = cansProvincia;
    }

    public String getCansProvincia() {
        return cansProvincia;
    }

    public void setCansSexo(String cansSexo) {
        this.cansSexo = cansSexo;
    }

    public String getCansSexo() {
        return cansSexo;
    }

    public void setCansSucursal(Long cansSucursal) {
        this.cansSucursal = cansSucursal;
    }

    public Long getCansSucursal() {
        return cansSucursal;
    }

    public void setCansTipoDocumento(Integer cansTipoDocumento) {
        this.cansTipoDocumento = cansTipoDocumento;
    }

    public Integer getCansTipoDocumento() {
        return cansTipoDocumento;
    }

    public void setCampaniasNoSociosMensajesList(List<CampaniasNoSociosMensajes> campaniasNoSociosMensajesList) {
        this.campaniasNoSociosMensajesList = campaniasNoSociosMensajesList;
    }

    public List<CampaniasNoSociosMensajes> getCampaniasNoSociosMensajesList() {
        return campaniasNoSociosMensajesList;
    }

    public void setCampaniasBase(CampaniasBase campaniasBase) {
        this.campaniasBase = campaniasBase;
    }

    public CampaniasBase getCampaniasBase() {
        return campaniasBase;
    }
    
    public CampaniasNoSociosMensajes addCampaniasNoSociosMensajes(CampaniasNoSociosMensajes campaniasNoSociosMensajes) {
        if(getCampaniasNoSociosMensajesList()==null){
            setCampaniasNoSociosMensajesList(new ArrayList<CampaniasNoSociosMensajes>());
        }
        getCampaniasNoSociosMensajesList().add(campaniasNoSociosMensajes);
        campaniasNoSociosMensajes.setCampaniasNoSocios(this);
        return campaniasNoSociosMensajes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CampaniasNoSocios)) {
            return false;
        }
        final CampaniasNoSocios other = (CampaniasNoSocios)object;
        if (!(cansApellidos == null ? other.cansApellidos == null : cansApellidos.equals(other.cansApellidos))) {
            return false;
        }
        if (!(cansNombres == null ? other.cansNombres == null : cansNombres.equals(other.cansNombres))) {
            return false;
        }
        if (!(cansNroTel1 == null ? other.cansNroTel1 == null : cansNroTel1.equals(other.cansNroTel1))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((cansApellidos == null) ? 0 : cansApellidos.hashCode());
        result = PRIME * result + ((cansNombres == null) ? 0 : cansNombres.hashCode());
        result = PRIME * result + ((cansNroTel1 == null) ? 0 : cansNroTel1.hashCode());
        return result;
    }
}
