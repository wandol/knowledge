/* chart colors default */
var $chrt_border_color = "#efefef";
var $chrt_grid_color = "#DDD"
var $chrt_main = "#E24913";
/* sky      */
var $color_first = ["#7498b1"];
/* bule    */
var $color_second = ["#5bacd2"];

var $colorList = ["#305fc1","#4b8dde","#15b6c5","#42d085","#a0e242","#f5cb22","#d05fb9", "#855acc", "#c5b0d5","#fd4223"];
//["#38b656",'#42d085', "#a0e242", "#f5cb22", "#f29528","#f05353", "#f0599d", "#d05fb9", "#855acc", "#c5b0d5"];

// 코난 다비프 그래스
var drawDavif = {
	barChart : function(data, id, func) {
		
		$("#" + id).visualization({
		       data: data,
		       type: 'bar',
//		       barWidth : 20,
		       bar: {
		    	 width: 15
		       },
		       tooltip: {
					enabled:true,
					suffix : "건"
			   },
			   fontSize : 10,
		       tickPoint : 0,
		       tickSuffix : "건",
		       tickX : 10,
		       tickY : -5,
//		       width : 1500,
		       height :300,
		       margin : {top: 20, right: 20, bottom: 30, left: 50},
		       click : function(d) {
				console.log("horizon click :: " + d);
		       }
		});
	},
	horizonBarChart : function(data, id, func) {
		
		$("#" + id).visualization({
		       data: data,
		       barWidth : 20,
		       type: 'horizontal_bar',
		       tickSuffix : "건",
		       margin : {top: 30, right: 80, bottom: 30, left: 120},
		       tooltip: {
					enabled:true
			   },
		       rect : {
		    	   sort : false,
		    	   attr : {},
		    	   style : {
						/* fill : function(d) {
							return "#3182bd";
						} */
		    	   }
		       },
		       text : {
		    	   attr : {},
		    	   style : {
						cursor: "pointer"
					}
		       },
		       click : function(d) {
		    	   var _cate = $('#pfACategory option:selected').val();
		    	   
		    	   var _symptomKwd = "";
		    	   var _actionKwd = "";
		    	   
		    	   
		    	   $('#his_category').val(_cate);
		    	   $('#his_powerComp').val($('#pfAPowerComp').val());
		    	   $('#his_powerSt').val($('#pfAPowerSt').val());
		    	   $('#his_stNo').val($('#pfAStNo').val());
		    	   $('#his_partName').val($('#pfAPartName').val());
		    	   $('#his_powerType').val("all");
		    	   if ( d.id.indexOf("SYMPTOM") > -1 ) {
		    		   _symptomKwd = d.id.split("-")[1];
		    		   _actionKwd = d.name.replace(" ", "_");
		    	   } else {
		    		   _symptomKwd = d.name.replace(" ", "_");
		    		   _actionKwd = d.id.split("-")[1];
		    	   }
		    	   $('#his_symptomKwd').val(_symptomKwd);
		    	   $('#his_actionKwd').val(_actionKwd);
		    	   $('#historyForm').attr("action","pfSearch.do");
		    	   $('#historyForm').submit();
		    		
		       },
		       x : {
		    	   gridUse : false,
		       }
		});
	},
	cloudChart : function(data, id, func) {
		$('#' + id).visualization({
			type: "cloud",
			width: "800",
			height: "250",
			margin: {
				top: 5,
				right: 0,
				bottom: 20,
				left: 20
			},
			backgroundColor: "",
			font: {
				name: "sans-serif",
				color: "#343434"
			},
			opacityDomain: [1],
			text: {
				rotate: 0,
				range: [20,60],
				padding: 3,
				style: {
					cursor: "pointer"
				},
				attr: {

				}
			},
			click: function(d) {
				console.log(d);
			},
			cloud: {
				spiral: "rectangular"
			},
			data:data
		});
	},
	lineChart : function(data, id, func) {
		$("#" + id).visualization({
		       type:'line',
		       width:600,
		       height:300,
		       lineType: 'monotone',
		       maxPointUse: true,
		       fontSize: 10,
		       tickSuffix : "건",
		       tickY : -10,
			   tickSkip: 5,
			   inputFormat: "%Y.%m.",
			   dateFormat: "%Y.%m",
		       padding : 80,
		       tooltip: {
		              enabled:true,
		              useHtml:false,
		              suffix : "건",
		              css : {
		                     "background-color" : "#0083DD"
		              }
		       },
		       margin : {
		              top : 20,
		              right : 40,
		              bottom : 40,
		              left : 40
		       },
		       data:data
		});
	},
	pieChart : function(data, id, func) {
		$("#" + id).visualization({
			type:'pie',
			width: "100%",
			height: "100%",
			labels : {
		          enabled : true,
		          distance : 5,
		          radius: 3/4
			},
			tooltip: {
				enabled:true,
				useHtml:false,
				suffix: "건"
			},
			text : {
				//항목이 많은 경우 비추천 (텍스트 겹치는 등의 이슈가 발생할 수 있음)
				enabled : true,		//파이 항목 별 text 출력 여부 (default : false)
				style : {},
		
				//파이 항목 별 text 말줄임 기능 필요 시 주석 제거하여 사용
				//ellipsis : {
				//	suffix : "...",		//사용할 말줌임표 (default : '...')
				//	limit : 5,			//출력할 최소 텍스트 사이즈
				//}, 
			},
			line : {
				distance : 0.1,		//연결 라인 길이 (default : 0)
				attr : {},
				style : {},
			},
			pie : {
				radius : 0.7,		//출력되는 파이 크기 (default : 0.8) 
			},
			click: func,
			data:data
		});

	},
	dendrogramChart : function(data, id, func) {
		$("#" + id).visualization({
			type: "dendrogram",
			width: "100%",
			height: "100%",
			margin:{
				left: 120
			},
			removeBtn : false,
			duration : 350,
			//depthLength : [300, 300, 300],
			text : {
				aling : "start",
				//x : 150,
				legnth : 10,
				suffix : "..",
				style : {curcor:"pointer"}
			},
			color : $colorList,
			click : func,
			data: data
		});
	},
	hierarchyChart: function(data, id, func) {
		$("#" + id).visualization({
			type: "hierarchy_tree",
			width: "100%",
			height: "100%",
			margin: {
				top: 60,
				right: 0,
				bottom: 20,
				left: 80
			},
			backgroundColor: "",
			font: {
				name: "sans-serif",
				color: "#343434"
			},
			duration: 500,
			depthLength: [130,170,50,50],
			text: {
				length: 15,
				suffix: "..",
				style: {
					"text-anchor": "start",
					"letter-spacing": -1,
					"font-size": "14px",
					"fill": "#232330",
					//"font-family": "'Malgun Gothic', Arial, sans-serif"
				},
				attr: {
					y: -12,
					x: 15
				}
			},
			collapse: {
				open: false
			},
			node: {
				interval: 26
			},
			rect: {
				x: 85,
				y: 45,
				style: {
					stroke: function(d) {
						return d._children ? "#ffcf3e" : "#5d79f7";
					},
					fill: function(d) {
						//console.log(d.data.name)
						return d._children ? "#ffcf3e" : "#5d79f7";
					}
				},
				attr: {
					width: 152,
					height: 3,
					y: -1.5,
					rx: 3,
					ry: 3
				}
			},
			color: $colorList,
			click: func,
			link: {
				style: {
					"stroke": "#c0c0d8",
					"stroke-width": 1
				},
				attr: {

				}
			},
			parent: {
				rect: {
					style: {
						"stroke-width": 0,
						"fill": "#4b4b5f"
					},
					attr: {
						"width": 136,
						"height": 136,
						"rx": 136,
						"ry": 136,
						"x": -50,
						"y": -25,
						"stroke": "none"
					}
				},
				text: {
					style: {
						"text-anchor": "middle",
						"font-size": "17px",
						"letter-spacing": -1,
						"fill": "#fff"
					},
					attr: {

					}
				}
			},
			data: data
		});
	},
	networkMapChart: function(data, id, func) {
		$("#" + id).visualization({
			type: "networkmap",
			width: "100%",
			height: "100%",
			node : {
				/*type : {
					plus : "../images/img_plus.png",
					bg : "../images/img_bg.png",
				},*/
				range : [5, 20],
			},
			link : {
				distance : 80, 
				strength : -30,
				range : [1, 3],
				arrow : false,
			},
			text : {
				//size : 12,
				x : 5,
				y : 0,
				style : {
					'font-size' : 12,
				}
			},
			edge : {
				style : {
					'font-size' : 10,
				}
			},
			tooltip : {
				enabled : true,
				//useHtml:false,
	            suffix: "건",
				/*html : function(d) {
					var html = d.id + '<br/>' + d.value + '건';
					return html; 
				}*/
			},
			click : func,
			data: data
		});
	},
	radarChart : function(data, id, func){
		$("#" + id).visualization({
			type : "radar",
			width: "100%",
			height : "100%",
			margin : {
				"top": 0,
				"right": 0,
				"bottom": 0,
				"left": 0
			},
			color: $color_second,
			font : {
				size : 13,
			},
			axis : {
				label : false,
			},
			line : {
				type: "cardinal-closed"
			},
			duration: 650,
			tooltip : {
				cssClass: "davifTip",
				enabled : true,
				//useHtml:false,
	            suffix: "건",
				/*html : function(d) {
					var html = d.id + '<br/>' + d.value + '건';
					return html; 
				}*/
			},
			click : func,
			data: data
		});	
	}
};

// Flot 차트
var drawFlot = {
		barChart : function(data, id, color) {
			
			//Display graph
			$.plot($("#" + id), [data], {
				colors : color,
				series: {
					bars: {
						show: true,
						barWidth: 0.3,
						align: "center",
						fill: 1,
						lineWidth: 0
					}
				},
				xaxis: {
					mode: "categories",
					showTicks: false,
					gridLines: false
				},
				grid : {
					show : true,
					hoverable : true,
					clickable : true,
					tickColor : $chrt_border_color,
					borderWidth : 0,
					borderColor : $chrt_border_color,
				},
				tooltip : true,
				tooltipOpts : {
					cssClass: "flotTip",
			        content: function(label,x,y){
			            return x.replace("<br>"," ") + "<br>" + y +" 건";
			        },
					defaultTheme : false,
					shifts: {
						x: 0,
						y: -20
					}
				}
			});
		},
		horizonBarChart : function(data, id, color) {
			
			//Display graph
			$.plot($("#" + id), [data], {
				colors : color,
				series: {
					bars: {
						show: true,
						barWidth: 0.5,
						align: "center",
						fill: 1,
						lineWidth: 0,
						horizontal: true
					}
				},
				yaxis: {
					mode: "categories",
					showTicks: false,
					gridLines: false
				},
				grid : {
					show : true,
					hoverable : true,
					clickable : true,
					tickColor : $chrt_border_color,
					borderWidth : 0,
					borderColor : $chrt_border_color,
				},
				tooltip : true,
				tooltipOpts : {
					cssClass: "flotTip",
			        content: function(label,x,y){
			            return y.replace("<br>"," ") + "<br>" + x +" 건";
			        },
					defaultTheme : false,
					shifts: {
						x: 0,
						y: -20
					}
				}
			});
		},
		lineChart: function(data, id, pOption) {
			var _Option = {};
			
			// 기본 옵션값 세팅
			if(pOption == undefined) {
				_Option.xaxis = {
						mode: "time",
						timeformat: "%Y",
//						timeformat: "%Y.%m",
//						tickDecimals: 0,
//				        mode: "categories",
//				        ticks: data.length > 10 ? 10 : data.length,
				        ticks: data[0].data.length > 10 ? 10 : data[0].data.length,
				        tickLength: 0,
//				        autoscaleMargin: 0.05,
					};
				_Option.tooltipOpts = {
					cssClass: "flotTip",
			        content: function(label,x,y){
			        	var date = new Date(x);
						var _rsDate = date.getFullYear(); 
//						var _rsDate = date.getFullYear()+"."+(date.getMonth()+1); 
			            return _rsDate + "<br>" + y +" 건";
			        },
					/*content: function(label,x,y,dataObject){
						var date = new Date(x);
						var _rsDate = date.getFullYear()+"."+(date.getMonth()+1); 
						
			            return  _rsDate +  " | " + y + "건";
			        },*/
					defaultTheme : false,
					shifts: {
						x: 0,
						y: -20
					}
				};
			}else{
				_Option = pOption;
			}
			
			
			$.plot($("#" + id), data,
//			[{
//				data : data
////				label : "CLICK"
//			}], 
				{
				series : {
					lines : {
						show : true,
						lineWidth : 1,
						fill : true,
						fillColor : {
							colors : [{
								opacity : 0.1
							}, {
								opacity : 0.1
							}]
						}
					},
					points : {
						show : true
					}
				},
				grid : {
					hoverable : true,
					clickable : true,
					tickColor : $chrt_border_color,
					borderWidth : 0,
					borderColor : $chrt_border_color,
				},
				tooltip : true,
				tooltipOpts : _Option.tooltipOpts,
				colors : $color_second,
				xaxis: _Option.xaxis
			});
		},
		pieChart: function(data, id, pOption, func) {
			var _Option = {};
			
			// 기본 옵션값 세팅
			if(pOption == undefined) {
				_Option.innerRadius = 0.5;
				_Option.legendShow = true;
				_Option.pie = {
						show : true,
						innerRadius : _Option.innerRadius,
//						radius : 0.5,
						label : {
							show : true,
//							radius : 0.8,
//							threshold : 0
						}
					};
			}else{
				_Option = pOption;
			}
			
			$.plot($("#" + id), data, {
				colors : $colorList,
				series : {
					pie : _Option.pie
				},
				legend : {
					show : _Option.legendShow,
					noColumns : 1, // number of colums in legend table
					labelFormatter : null, // fn: string -> string
					labelBoxBorderColor : "#000", // border color for the little label boxes
					container : null, // container (as jQuery object) to put legend in, null means default on top of graph
					position : "ne", // position of default legend container within plot
					margin : [5, 10], // distance from grid edge to default legend container within plot
					//backgroundColor : "#efefef", // null means auto-detect
					backgroundOpacity : 1 // set to 0 to avoid background
				},
				grid : {
					hoverable : true,
					clickable : true
				},
				tooltip : true,
				tooltipOpts: {
			        cssClass: "flotTip",
			        content: function(label,x,y){
			            return label + "<br>" + y +" 건";
			        },
			        shifts: {
			            x: 20,
			            y: 0
			        },
			        defaultTheme: false
			    }
			});
			
			$("#" + id).unbind("plotclick").bind("plotclick", func);
		}
};
