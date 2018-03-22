function customHandler(event, obj) {
    var o = AdfPage.PAGE.findComponentByAbsoluteId(obj);
    if (o == null) {
        o = AdfPage.PAGE.findComponent(obj);
    }
    var actionEvent = new AdfActionEvent(o);
    actionEvent.forceFullSubmit();
    actionEvent.noResponseExpected();
    actionEvent.queue();
}

function clearValue(obj) {
    document.getElementById(obj).value = "";
}

function setValue(obj, val) {
    document.getElementById(obj).value = val;
}

function resetForm() {
    var list = ['pt1:r1:0:it1::content', 'pt1:r1:0:id1::content', 'pt1:r1:0:id2::content'];
    for (var i = 0;i < list.length;i++) {
        clearValue(list[i].toString());
    }
    return true;
}

function isFechaPosterior(idFechaDesde, idFechaHasta) {
    var fechaDesde = document.getElementById(idFechaDesde + '::content').value;
    var fechaHasta = document.getElementById(idFechaHasta + '::content').value;
    if (fechaDesde != null && fechaDesde != '') {
        var enteroFechaDesde = pasarFechaAEntero(fechaDesde)
        var enteroFechaHasta = pasarFechaAEntero(fechaHasta);
        if (enteroFechaDesde != null && enteroFechaHasta != null) {
            if (enteroFechaHasta >= enteroFechaDesde && diasTranscurridos(fechaDesde, fechaHasta) <= 180) {
                return true;
            }
            else {
                mostrarMensajeErrorUnico(idFechaHasta, 'Valor inv\u00E1lido', 'La fecha Hasta debe ser superior a la fecha desde sin superar lso 6 meses.')
                return false;
            }
        }
        else {
            mostrarMensajeErrorUnico(idFechaHasta, 'Valor inv\u00E1lido', 'La fecha Hasta debe ser superior a la fecha desde.')
            return false;
        }
    }
}

function pasarFechaAEntero(fecha) {
    if (fecha != null) {
        var fechaDescompuesta = fecha.split('/');
        return parseInt(fechaDescompuesta[2] + fechaDescompuesta[1] + fechaDescompuesta[0]);
    }
    else {
        return;
    }
}

function diasTranscurridos(f1, f2) {
    var aFecha1 = f1.split('/');
    var aFecha2 = f2.split('/');
    var fFecha1 = Date.UTC(aFecha1[2], aFecha1[1] - 1, aFecha1[0]);
    var fFecha2 = Date.UTC(aFecha2[2], aFecha2[1] - 1, aFecha2[0]);
    var dif = fFecha2 - fFecha1;
    var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
    return dias;
}

function mostrarMensajeErrorUnico(idcomponente, label, mensaje) {
    AdfPage.PAGE.clearAllMessages();
    AdfPage.PAGE.addMessage(idcomponente, new AdfFacesMessage(AdfFacesMessage.TYPE_ERROR, label, mensaje));
    AdfPage.PAGE.showMessages(idcomponente);
}

var alertas = {
    msj : '', clase : '', ele : '', info : function (m) {
        this.msj = m;
        this.clase = 'alert alert-info';
        this.add();
    },
    error : function (m) {
        this.msj = m;
        this.clase = 'alert alert-error';
        this.add();
    },
    warning : function (m) {
        this.msj = m;
        this.clase = 'alert alert-warning';
        this.add();
    },
    add : function () {
        this.init();
        $(this.ele).appendTo('body');
        $(this.ele).fadeOut(6000);
    },
    init : function () {
        this.ele = $('<div/>', 
        {
            'id' : 'alerta', 'class' : this.clase, 'html' : this.msj, 'click' : function () {
                copyToClipboard(this)
            }
        });
    }
};

var copyToClipboard = function (e) {
    var $temp = $('<input>');
    $('body').append($temp);
    $temp.val($(e).text()).select();
    document.execCommand('copy');
    $temp.remove();
}

$('body').keypress(function (event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == 13) {
        $('#pt1:r1:0:b1').children().click();
    }
    //event.stopPropagation();
});