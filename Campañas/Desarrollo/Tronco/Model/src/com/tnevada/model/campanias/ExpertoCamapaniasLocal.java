package com.tnevada.model.campanias;

import com.tnevada.model.entidades.BaseFiltradaDTO;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;
import com.tnevada.model.entidades.ConsultaDTO;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.TipoCampanias;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Local;

@Local
public interface ExpertoCamapaniasLocal {
    void create(Campanias entity);

    void edit(Campanias entity);

    void remove(Campanias entity)throws Exception;

    Campanias find(Object id);

    List<Campanias> findAll();

    int count();
    
    List<Campanias> find(String p0,Long p1,Date p2, Date p3, String p4, boolean todas, String producto)throws CampaniasNoResultException;
    
    List<TipoCampanias> getTipoCampanias()throws CampaniasNoResultException;
    
    Long getLegajoPorNombreDeUsuario(String usuario)throws CampaniasNoResultException;
    
    void cargarBaseClientes(HashSet<Long> documentos,Campanias campania, List<BaseFiltradaDTO> preFiltro)throws CampaniasException;
    
    List<BaseFiltradaDTO> filtrarBaseClientes(HashSet<Long> documentos,Campanias campania,ConsultaDTO consulta,String path)throws CampaniasNoResultException;
    
    List<Parametros> getRenglones()throws CampaniasNoResultException;
    
    String getFechaCartera(Long periodo)throws CampaniasNoResultException;
    
    List<ConsultaDTO> getProductos(Long tipo)throws CampaniasNoResultException;    
    
    void eliminarCampaniaDependencias(Campanias c)throws CampaniasException;
    
    void cargarBaseClientes(HashSet<CampaniasBase> cns) throws CampaniasException;
    
    String getLegajoPorNombreDeUsuario(Long legajo) throws CampaniasNoResultException;
    
    boolean esSoscios(Long documento)throws CampaniasNoResultException;
}
