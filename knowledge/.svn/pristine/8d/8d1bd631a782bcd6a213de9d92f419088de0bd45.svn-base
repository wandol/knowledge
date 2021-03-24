var graphDefaultColor = ["#61a6c5", "#b49b77", "#efaa3d", "#f28353", "#c6cf6a",
     	        		"#9c60b6", "#77bea5", "#e09b90", "#d83a4f", "#3864a9",
     	        		"#a37245", "#dd6eb0", "#6087e8", "#6c798f", "#9d9fa3"];
var graphPie = {
	init : function(id, data) {
		$("#" + id).unbind();
		
		$.plot($("#" + id), data, {
			series: {
				pie: { 
					show: true,
					radius: 1
				}
			},
			colors: graphDefaultColor,
			legend: {
				show: true
			},
		    grid: {
		        hoverable: true
		    },
			tooltip: true,
		    tooltipOpts: {
		    	content: "%s <br> %p.2%(%n)",
		        shifts: {
		            x: 20,
		            y: 0
		        },
		        defaultTheme: true
		    }
		});
	},
	
	formatter : function(label, series) {
		return "<div style='font-size:x-small;text-align:center;padding:2px;color:white;'>"
				+ label
				+ "<br/>"
				+ Math.round(series.percent)
				+ "% ("
				+ series.data[0][1] + ")</div>";
	}
};

var graphLine = {
	
	init : function(id, data) {
		$("#" + id).unbind();
		
		var chart = $.plot($("#" + id), data, {
			series: {
				lines: {
					show: true,
					lineWidth: 1
				},
				shadowSize: 0,
				points: {
					show: true
				}
			},
			colors: graphDefaultColor,
			yaxis: {
				min: 0,
				tickDecimals: 0,
				tickFormatter: function(x) {
					var parts = x.toString().split(".");
					parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					return parts.join("."); 
				}
			},
			xaxis: {
				min : 0,
				ticks : [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
				tickDecimals: 0,
				tickColor: 'transparent'
			},
			legend:{
				backgroundColor: "transparent",
				container : $("#" + id).next()
			},
		    grid: {
		        hoverable: true,
		        margin: 20,
		        borderWidth : 0
		    },
			tooltip: true,
		    tooltipOpts: {
		    	content: "%s <br> %y",
		        shifts: {
		            x: 20,
		            y: 0
		        },
		        defaultTheme: true
		    }
		});
	}
};


var graphBar = {
	init : function(id, data) {
		$("#" + id).unbind();
		
		$.plot($("#" + id), this.getColorsData(data), {
			series: {
				bars: {
					show: true,
					barWidth: 0.8,
					align: "center"
				}
			},
			yaxis: {
				min: 0,
				tickDecimals: 0,
				tickFormatter: function(x) {
					var parts = x.toString().split(".");
					parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					return parts.join("."); 
				},
				axisLabelUseCanvas: true
			},
			xaxis: {
				mode: "categories",
				tickLength: 0,
				labelWidth: 45,
				rotateTicks: 135
			},
			grid: {
			    hoverable: true,
			    margin: 20,
		        borderWidth : 0
			},
			tooltip: true,
		    tooltipOpts: {
		    	content: "%x <br> %y",
		        shifts: {
		            x: 20,
		            y: 0
		        },
		        defaultTheme: true
		    }
		});
	},
	
	getColorsData : function(pdata) {
		var data = [];
		for(var i=0; i<pdata.length; i++) {
			var map = {};
			map['color'] = graphDefaultColor[i];
			map['data'] = [pdata[i]];
			data.push(map);
		}
		return data;
	}
};

var graphCloud = {
	init : function(id, data) {
		$("#" + id).unbind();
		$("#" + id).visualization({
			data: this.getColorsData(data),
			dataType: "json",
			x : 0,
			y : 5,
			fontRange : [15, 45],
			rotate : 0,
			dataParams : {},
			type:'cloud',
			fontSize : 12
		});
	},
	
	getColorsData : function(pdata) {
		var data = [];
		for(var i=0; i<pdata.length; i++) {
			var map = {};
			map['color'] = graphDefaultColor[i];
			map['name'] = pdata[i].name;
			map['value'] = pdata[i].value;
			data.push(map);
		}
		return data;
	}
}
