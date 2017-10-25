package view.dto;

public class Respuesta {
    static Respuesta status;
    private Long codigoAutorizacion;
    private Integer codigoError;
    private String observacion;

    public Respuesta() {
    }

    public Respuesta(Long codigoAutorizacion, Integer codigoError, String observacion) {
        super();
        this.codigoAutorizacion = codigoAutorizacion;
        this.codigoError = codigoError;
        this.observacion = observacion;
    }

    /**
     * @return the codigoAutorizacion
     */
    public Long getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    /**
     * @param codigoAutorizacion the codigoAutorizacion to set
     */
    public void setCodigoAutorizacion(Long codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    /**
     * @return the codigoError
     */
    public Integer getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    }
