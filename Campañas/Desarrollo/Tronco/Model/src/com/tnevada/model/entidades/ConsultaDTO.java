package com.tnevada.model.entidades;


public class ConsultaDTO {
    private String id,nombre,query;
    private boolean check;
    
    public ConsultaDTO() {
        super();
    }
    
    public ConsultaDTO(String id,String nombre,String query){
        this.id = id;
        this.nombre = nombre;
        this.query = query;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }
}
