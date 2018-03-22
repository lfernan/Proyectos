package com.tnevada.model.entidades;

import java.io.Serializable;


public class SucursalDTO implements Serializable {
    @SuppressWarnings("compatibility:2949178407656461937")
    private static final long serialVersionUID = 1L;
    private Long id;    
    private String nombre;
    
    public SucursalDTO() {        
    }
    
    public SucursalDTO(Long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
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
}
