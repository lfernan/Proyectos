package com.tnevada.model.entidades;

import com.tnevada.model.util.Util;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAMPANIAS_SEGMENTACION")
public class CampaniasSegmentacion implements Serializable {
    private static final long serialVersionUID = -4610255145329009530L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_segmentacion")
    @SequenceGenerator(name = "seq_segmentacion", sequenceName = "SEQ_SEGMENTACION", initialValue = 1, allocationSize=1)
    @Column(name = "CASE_ID", nullable = false)
    private Long id;
    @Column(name = "CASE_CAMP_ID", nullable = false)
    private Long campId;
    @Temporal(TemporalType.DATE)
    @Column(name = "CASE_FECHA")
    private Date fecha;    
    @Column(name = "CASE_NOMBRE_SEGMENTO", length = 1000)
    private String nombre;
    @Column(name = "CASE_USUARIO")
    private Long usuario;
    @Column(name = "CASE_BARRIDO")
    private Long barrido;
    @Column(name = "CASE_FILTROS")
    private String filtros;

    public CampaniasSegmentacion() {
    }
    
    public CampaniasSegmentacion(Long campId, Date fecha, Long id, String nombre, Long usuario, Long barrido,
                                 String filtros) {
        super();
        this.campId = campId;
        this.fecha = fecha;
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.barrido = barrido;
        this.filtros = filtros;
    }


    public void setCampId(Long campId) {
        this.campId = campId;
    }

    public Long getCampId() {
        return campId;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setBarrido(Long barrido) {
        this.barrido = barrido;
    }

    public Long getBarrido() {
        return barrido;
    }

    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }

    public String getFiltros() {
        return filtros;
    }
    
    public List<String> getFiltrosFormateados(){
        List<String> datos = new ArrayList<String>();
        Map map = Util.jsonParse(getFiltros());
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            datos.add(key + "&nbsp;<b>" + value + "</b>");                
        }
        return datos;
    }
}
