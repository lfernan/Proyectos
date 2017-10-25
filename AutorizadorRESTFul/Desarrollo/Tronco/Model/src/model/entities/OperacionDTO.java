package model.entities;

public class OperacionDTO {
    private Long codAutorizacion,codOperacion,codError;    
    private String entidad,fecha,observacion;
    private Float monto;    
    
    public OperacionDTO() {
        super();
    }

    public void setCodError(Long codError) {
        this.codError = codError;
    }

    public Long getCodError() {
        return codError;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setCodAutorizacion(Long codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public Long getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodOperacion(Long codOperacion) {
        this.codOperacion = codOperacion;
    }

    public Long getCodOperacion() {
        return codOperacion;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getMonto() {
        return monto;
    }
}
