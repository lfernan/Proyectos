package model.session;

import java.util.List;

import javax.ejb.Local;

import model.entities.OperacionDTO;
import model.entities.Plan;

@Local
public interface ExpertoGatewayLocal {
    boolean validar(Long ocr, String nombre, Integer cvc, Integer expiracion, Integer documento);

    boolean login(String entidad, String serial);

    OperacionDTO verificarOperacion(Long operacion, Double monto);

    void log(Long comercio, int estado, Long operacion, Long ocr, Integer moneda, Double monto, Integer plan,
             Long codigoAutorizacion, Long codigoError);

    List<Plan> planes(Long comercio, Double monto, Integer documento);
    
    boolean validarToken(String token);
    
    String crearToken();
    
    void boorarToken(String token);
}
