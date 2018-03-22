CREATE OR REPLACE PACKAGE pkg_campanias_call
AS
   /******************************************************************************
   NAME:       pkg_segmentacion
   PURPOSE:   Devolver la cantidad de gestiones y generar nuevos segmentos
   Ver        Date        Author              Description
   ---------  ----------  ---------------     ------------------------------------
   1.0        02/06/2016  Leandro Fernandez           Devolver la cantidad de gestiones y generar nuevos segmentos
   1.1        01/07/2016  Leandro Fernandez           Se agrega el parametro p_tipo a los metodos para procesar diferentes tipos de campañas
   ******************************************************************************/
      
  FUNCTION f_base (p_sucursales    IN VARCHAR2,
                    p_planpago   IN VARCHAR2,
                    p_campania   IN PLS_INTEGER,
                    p_tipo       IN PLS_INTEGER,
                    p_edad       IN VARCHAR2,
                    p_resumen    IN VARCHAR2,
                  p_cantidad          IN  NUMBER)
    RETURN VARCHAR2;

  PROCEDURE p_base (p_sucursales   IN     VARCHAR2,
                     p_planpago     IN     VARCHAR2,
                     p_campania     IN     PLS_INTEGER,
                     p_tipo         IN     PLS_INTEGER,
                     p_edad         IN     VARCHAR2,
                     p_resumen      IN     VARCHAR2,
                     p_datos        OUT NOCOPY VARCHAR2);

  PROCEDURE p_reciclar_campania (p_campania IN PLS_INTEGER, p_tipo IN PLS_INTEGER);

  PROCEDURE p_segmentar (p_cantidad           IN      PLS_INTEGER,
                          p_planpago          IN     VARCHAR2,
                          p_campania          IN     PLS_INTEGER,
                          p_legajo            IN     PLS_INTEGER,
                          p_campania_nombre   IN     VARCHAR2,
                          p_sucursales        IN     VARCHAR2,
                          p_tipo              IN     PLS_INTEGER,
                          p_edad              IN     VARCHAR2,
                          p_resumen           IN     VARCHAR2,
                          p_id_segmento       OUT NUMBER);

  PROCEDURE p_filtrar_base_tk (p_producto     IN     VARCHAR2,
                                con_producto  OUT NOCOPY SYS_REFCURSOR,
                                en_camp       OUT NOCOPY SYS_REFCURSOR,
                                habilitados   OUT NOCOPY SYS_REFCURSOR);
                                
  PROCEDURE p_fecha_renglon (p_periodo IN PLS_INTEGER, p_fecha OUT VARCHAR2);
  
END pkg_campanias_call;
/