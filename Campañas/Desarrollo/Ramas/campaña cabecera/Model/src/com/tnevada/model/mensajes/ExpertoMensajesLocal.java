package com.tnevada.model.mensajes;

import com.tnevada.model.entidades.ListaNegraTelefonos;
import com.tnevada.model.entidades.Menp004m;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.RelacionMensajeCampania;
import com.tnevada.model.entidades.TipificacionesMenDetalle;
import com.tnevada.model.entidades.TipificacionesMensajesCall;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.Local;


@Local
public interface ExpertoMensajesLocal {
    List<Parametros> getParametros()throws MensajesNoResultException;
    List<Menp004m> getMenp004m()throws MensajesNoResultException;
    Future<String> getVistaMensajesDTO(EventMensajes.CallBack c,String url,Date fechaDesde,Date fechaHasta,Long documento,String tipoOrigen,Long clasificacionId,Long campId)throws MensajesNoResultException;
    List<TipificacionesMensajesCall> getTipificaciones()throws MensajesNoResultException;
    String getDatos(Long id,String dni) throws MensajesNoResultException;
    List<String> getGestiones(String user,Long camp,boolean panel)throws MensajesNoResultException;
    List<TipificacionesMenDetalle> getTipificaciones(String[]acciones,Long camp)throws MensajesNoResultException;
    void guardarRelacionMensajeCampania(RelacionMensajeCampania o)throws MensajesException;
    void guardarListaNegra(ListaNegraTelefonos o)throws MensajesException;
    void actualizarRelacionMensajeCampania(RelacionMensajeCampania o) throws MensajesException;
}
