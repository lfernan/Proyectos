var $placeholder = $('<div id="placeholder" class="free-wall" style="width:90%; height:400px; margin: 0 auto;"></div>');
var indice = 0;

var panel = function (o) {
    var temp = "<div class='cell' style='width:{width}px; height: {height}px; background-color: {color}'>{texto}<div class='cant'><span>{cantidad}</span></div></div>";
    var w = 400, h = 200, html = '';
    for (var i = 0;i < o.length;i++) {
        var s = o[i].split('@');
        html += temp.replace(/\{height\}/g, h)
        .replace(/\{width\}/g, w)
        .replace("{color}", randomColor())
        .replace("{texto}", s[0])
        .replace("{cantidad}", s[1]);
    }
    indice = 0;
    $placeholder.remove();
    $placeholder.appendTo('body');
    $placeholder.html(html);

    var wall = new Freewall($placeholder);
    wall.reset( {
        selector : '.cell', 
        animate : false, 
        cellW : 300, 
        cellH : 200, 
        onResize : function () {
            wall.refresh();
        }
    });
    wall.fitWidth();
    $(window).trigger("resize");
}

var pie = function(o){
    var data = [],series = Math.floor(Math.random() * 6) + 3;

    for (var j = 0; j < o.length; j++) {
        var it = o[j].split('@');
        data[j] = {
            label: it[0],
            data: it[1]
	}
    }
    
    $placeholder.remove();
    $placeholder.appendTo('body');

    $placeholder.unbind();

    $.plot($placeholder, data, {
        series: {
            pie: {
                show: true,
                radius: 1,
                label: {
                    show: true,
                    radius: 2/3,
                    formatter: labelFormatter,
                    threshold: 0
                }
            }
        },
        legend: {
            show: true
        }
    });
}

var bar = function(data){
    $placeholder = $('<div id="placeholder2" style="width:70%; height:400px"></div>');
    $placeholder.appendTo('body');;
    $placeholder.unbind();
    var plot = $.plot($placeholder, data, {
        bars: { show: true, barWidth: 0.5, fill: 0.9 },
        xaxis: { ticks: [], autoscaleMargin: 0.02 },
        yaxis: { min: 0, max: 2 }
    });

    var o = plot.pointOffset({ x: 2, y: -1.2});

    $placeholder.append("<div style='position:absolute;left:" + (o.left + 4) + "px;top:" + o.top + "px;color:#666;font-size:smaller'>Warming up</div>");

    o = plot.pointOffset({ x: 8, y: -1.2});
    $placeholder.append("<div style='position:absolute;left:" + (o.left + 4) + "px;top:" + o.top + "px;color:#666;font-size:smaller'>Actual measurements</div>");

    var ctx = plot.getCanvas().getContext("2d");
    ctx.beginPath();
    o.left += 4;
    ctx.moveTo(o.left, o.top);
    ctx.lineTo(o.left, o.top - 10);
    ctx.lineTo(o.left + 10, o.top - 5);
    ctx.lineTo(o.left, o.top);
    ctx.fillStyle = "#000";
    ctx.fill();
}

var randomColor = function () {
    var colores = ['#641E16', '#1B2631', '#626567', '#6E2C00', '#784212', '#7E5109', '#7D6608', '#186A3B', '#145A32', '#0B5345', '#0E6251', '#1B4F72', '#154360', '#4A235A', '#512E5F', '#78281F'];
    //return colores[Math.floor(Math.random() * colores.length)];
    if(indice == colores.length || indice > colores.length){
        indice = 0;
    }
    c = colores[indice];
    indice += 6;    
    return c;
}

function labelFormatter(label, series) {
    return "<div style='font-size:10pt; font-weight: bold; text-align:center; padding:2px; color:white;'>" + Math.round(series.percent*100)/100 + "%</div>";
} 

function removePlaceholder(){
    $placeholder.remove();
}

