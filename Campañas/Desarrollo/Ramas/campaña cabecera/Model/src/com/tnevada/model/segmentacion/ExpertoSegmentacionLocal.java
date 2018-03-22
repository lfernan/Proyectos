package com.tnevada.model.segmentacion;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasSegmentacion;
import com.tnevada.model.entidades.SucursalDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface ExpertoSegmentacionLocal {

    List<SucursalDTO> getSucursalesDTO() throws SegmentacionNoResultException;

    String getDatos(List sucursales, String pp, Campanias campania, String resumen, String edad) throws SegmentacionNoResultException;

    void segmentar(int cantidad, String usuario, List sucursales, String pp, Campanias campania, String url, String edades, String resumen) throws SegmentacionException;

    void reciclarCampania(Campanias campania) throws SegmentacionException;

    List<CampaniasSegmentacion> getSegmentacion(Campanias c, Date fdesde,
                                                Date fhasta) throws SegmentacionNoResultException;

    void getSegmentacionBase(Long segmento, String url) throws SegmentacionNoResultException;
}
