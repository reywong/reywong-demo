// $(function () {
    function tagUI_linechart(id, json, datasetFill) {
        var datas = json.datas;
        var labels = json.labels;
        if (datas != null && labels != null) {
            var colors = ["220,220,220", "26,179,148", "26,179,148", "151,187,205"];
            var chart_Data = new Object();
            chart_Data.labels = labels;
            var datasets = new Array();
            for (var i = 0; i < datas.length; i++) {
                datasets[i] = new Object();
                datasets[i].label = datas[i].label;
                datasets[i].data = datas[i].datas;
                datasets[i].fillColor = "rgba(" + colors[i % colors.length] + ",0.5)";
                datasets[i].strokeColor = "rgba(" + colors[i % colors.length] + ",1)";
                datasets[i].pointColor = "rgba(" + colors[i % colors.length] + ",1)";
                datasets[i].pointStrokeColor = "#fff";
                datasets[i].pointHighlightFill = "#fff";
                datasets[i].pointHighlightStroke = "rgba(" + colors[i % colors.length] + ",1)";
            }
            chart_Data.datasets = datasets;
            if (datasetFill == null) {
                datasetFill = false;
            } else {
                datasetFill = true;
            }
            var chart_Options = {
                scaleShowGridLines: true,
                scaleGridLineColor: "rgba(0,0,0,.05)",
                scaleGridLineWidth: 1,
                bezierCurve: true,
                bezierCurveTension: 0.4,
                pointDot: true,
                pointDotRadius: 4,
                pointDotStrokeWidth: 1,
                pointHitDetectionRadius: 20,
                datasetStroke: true,
                datasetStrokeWidth: 2,
                datasetFill: datasetFill,
                responsive: true
            };
            var ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx).Line(chart_Data, chart_Options);
        }
    }


    function tagUI_barchart(id, json) {
        var datas = json.datas;
        var labels = json.labels;
        if (datas != null && labels != null) {
            var colors = ["220,220,220", "26,179,148", "26,179,148", "151,187,205"];
            var chart_Data = new Object();
            chart_Data.labels = labels;
            var datasets = new Array();
            for (var i = 0; i < datas.length; i++) {
                datasets[i] = new Object();
                datasets[i].label = datas[i].label;
                datasets[i].data = datas[i].datas;
                datasets[i].fillColor = "rgba(" + colors[i % colors.length] + ",0.5)";
                datasets[i].strokeColor = "rgba(" + colors[i % colors.length] + ",0.8)";
                datasets[i].highlightFill = "rgba(" + colors[i % colors.length] + ",0.75)";
                datasets[i].highlightStroke = "rgba(" + colors[i % colors.length] + ",1)";
            }
            chart_Data.datasets = datasets;

            var chart_Options = {
                scaleBeginAtZero: true,
                scaleShowGridLines: true,
                scaleGridLineColor: "rgba(0,0,0,.05)",
                scaleGridLineWidth: 1,
                barShowStroke: true,
                barStrokeWidth: 2,
                barValueSpacing: 5,
                barDatasetSpacing: 1,
                responsive: true
            };
            var ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx).Bar(chart_Data, chart_Options);
        }
    }

    function tagUI_polarchart(id, json) {
        var datas = json.datas;
        if (datas != null) {
            var colors = ["#a3e1d4", "#dedede", "#b5b8cf"];
            var chart_Data = new Array();
            for (var i = 0; i < datas.length; i++) {
                chart_Data[i] = new Object();
                chart_Data[i].value = datas[i].value;
                chart_Data[i].color = colors[i % colors.length];
                chart_Data[i].highlight = "#1ab394";
                chart_Data[i].label = datas[i].label;
            }
            var chart_Options = {
                scaleShowLabelBackdrop: true,
                scaleBackdropColor: "rgba(255,255,255,0.75)",
                scaleBeginAtZero: true,
                scaleBackdropPaddingY: 1,
                scaleBackdropPaddingX: 1,
                scaleShowLine: true,
                segmentShowStroke: true,
                segmentStrokeColor: "#fff",
                segmentStrokeWidth: 2,
                animationSteps: 100,
                animationEasing: "easeOutBounce",
                animateRotate: true,
                animateScale: false,
                responsive: true
            };
            var ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx).PolarArea(chart_Data, chart_Options);
        }
    }


    function tagUI_piechart(id, json, percentageInnerCutout) {
        var datas = json.datas;
        if (datas != null) {
            var colors = ["#a3e1d4", "#dedede", "#b5b8cf"];
            var chart_Data = new Array();
            for (var i = 0; i < datas.length; i++) {
                chart_Data[i] = new Object();
                chart_Data[i].value = datas[i].value;
                chart_Data[i].color = colors[i % colors.length];
                chart_Data[i].highlight = "#1ab394";
                chart_Data[i].label = datas[i].label;
            }

            if (percentageInnerCutout == null) {
                percentageInnerCutout = 0;
            }
            var chart_Options = {
                segmentShowStroke: true,
                segmentStrokeColor: "#fff",
                segmentStrokeWidth: 2,
                percentageInnerCutout: percentageInnerCutout, // This is 0 for Pie charts
                animationSteps: 100,
                animationEasing: "easeOutBounce",
                animateRotate: true,
                animateScale: false,
                responsive: true
            };
            var ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx).Pie(chart_Data, chart_Options);
        }
    }

    function tagUI_radarchart(id, json) {
        var datas = json.datas;
        var labels = json.labels;
        if (datas != null && labels != null) {
            var colors = ["220,220,220", "26,179,148", "26,179,148", "151,187,205"];
            var chart_Data = new Object();
            chart_Data.labels = labels;
            var datasets = new Array();
            for (var i = 0; i < datas.length; i++) {
                datasets[i] = new Object();
                datasets[i].label = datas[i].label;
                datasets[i].data = datas[i].datas;
                datasets[i].fillColor = "rgba(" + colors[i % colors.length] + ",0.2)";
                datasets[i].strokeColor = "rgba(" + colors[i % colors.length] + ",1)";
                datasets[i].pointColor = "rgba(" + colors[i % colors.length] + ",1)";
                datasets[i].pointStrokeColor = "#fff";
                datasets[i].pointHighlightFill = "#fff";
                datasets[i].pointHighlightStroke = "rgba(" + colors[i % colors.length] + ",1)";
            }
            chart_Data.datasets = datasets;

            var chart_Options = {
                scaleShowLine: true,
                angleShowLineOut: true,
                scaleShowLabels: false,
                scaleBeginAtZero: true,
                angleLineColor: "rgba(0,0,0,.1)",
                angleLineWidth: 1,
                pointLabelFontFamily: "'Arial'",
                pointLabelFontStyle: "normal",
                pointLabelFontSize: 10,
                pointLabelFontColor: "#666",
                pointDot: true,
                pointDotRadius: 3,
                pointDotStrokeWidth: 1,
                pointHitDetectionRadius: 20,
                datasetStroke: true,
                datasetStrokeWidth: 2,
                datasetFill: true,
                responsive: true
            };
            var ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx).Radar(chart_Data, chart_Options);
        }
    }
// })
