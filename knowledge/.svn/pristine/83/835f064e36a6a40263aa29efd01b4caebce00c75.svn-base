var USER_KEY = "";
var DOMAIN_ID = "";
var BOTNAME = "";
var CONTEXTPATH = "http://" + $('#his_chatbotUrl').val() + "/konanbot/";
//var CONTEXTPATH = "http://127.0.0.1:6614/konanbot/";
var chat = {
    messageToSend: "",
    messageToShow: "",
    messageResponses: [],
    enableTTS : false,
    enableSTT : false,
    speechErrorMessage : "Speech synthesis can't be play.",
    ajaxCall : function(url, data) {
    	//console.log(url, data);
		return $.ajax({
			'type' : 'POST',
			'url' : url,//url "http://127.0.0.1:9614/konanbot/chat/open"
			'headers' : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			'data' : JSON.stringify(data),
		});
    },
    
    cacheDOM: function() {
    	this.$chatContainer = $("#chat-history");
        this.$chatHistory = $("#chat-history > ul");
        this.$button = $("#chat-send");
        this.$textarea = $("#chat-textarea");
    },
    cacheTemplate : function() {
    	this.loadingTmpl = $.getTemplateStr("message-loading");
    	this.questionTmpl = $.getTemplateStr("user-message");
    	this.answerTmpl = $.getTemplateStr("bot-message");
    	this.dialogTmpl = $.registTemplateStr("dialogTmpl", "dialog-response");
    	$.registTemplateStr("carouselTmpl", "carousel-response");
    },
    bindEvents: function() {
	   var self = this;
	   self.$button.on("click", self.addMessageEvent.bind(self));
	   self.$textarea.on("keydown", self.addMessageEvent.bind(self));
	   self.$chatContainer.on("slid.bs.carousel", ".carousel", function(e) {
		   if(typeof speechSynthesizer != 'undefined')
			   speechSynthesizer.speak($(e.target));
       });
    },
    changeDomain: function(domain){
		console.log("domain : " + domain);
		DOMAIN_ID = domain;
		chat.sendRichMessage('postback',domain, '');
	},
    init: function(domain, user, scenario, ajaxRequest) {
    	
    	var self = this;
    	if(ajaxRequest && typeof ajaxRequest == "function") 
    		self.ajaxCall = ajaxRequest;
    	
    	
    	
    	DOMAIN_ID = domain;
    	USER_KEY = user;
    	
    	self.cacheDOM();
    	//console.log(self.ajaxCall);
    	self.cacheTemplate();
    	//console.log(self.ajaxCall);
    	self.bindEvents();
    	
        var params = {};
        params.domain = domain;
        params.user = user;
        params.scenario = scenario;
        
        //console.log(params);
        self.ajaxCall(CONTEXTPATH + "chat/open", params)
        .done(function(ret){
        	BOTNAME = "KEPRIBOT";
    		//BOTNAME = ret.data.botname; //KONANBOT
    		self.afterGreeting(ret.data);
    		self.answer(ret.payloads);
    	})
    	.fail(function(xhr) {
    		self.failAnswer(xhr);
    	});
    },
    question: function() {
    	var self = this;
        if (self.messageToSend !== "") {
            var context = {
                text: self.messageToShow,
                time: self.getCurrentTime()
            };
            self.$chatHistory.append(self.questionTmpl.render(context) );
        }
        var contextResponse = {
        	botname : BOTNAME,
            time: self.getCurrentTime()
        };
        self.$chatHistory.append( $(self.loadingTmpl.render(contextResponse)) );
        self.scrollToBottom();
    },

    sender: function(domain, user) {
    	var self = this;
        if (self.messageToSend == undefined || self.messageToSend == "") {
            return;
        }
        var params = {};
        params.domain = domain;
        params.user = user;
        params.text = self.messageToSend;
        
        self.ajaxCall(CONTEXTPATH + "chat/message", params)
        .done(function(ret){
        	self.answer(ret.payloads);
    	})
    	.fail(function(xhr) {
    		self.failAnswer(xhr);
    	});
    },

    answer : function(payloads) {
    	//console.log(payloads);
    	$("li").remove("#loading");
    	
    	var self = this;
    	for(var i  in payloads) {
	        var $dialog = $.dialogResponse(payloads[i].contents, self.dialogTmpl);
	        var messageData = {
				ttsIcon : (self.enableTTS && $dialog.find("audio[name='tts']").length>0), 
				body : $dialog[0].outerHTML
			};
	        //console.log(messageData['body']);
	        messageData['body'] = messageData['body'].replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&quot;/g,"\"");
	    	
	        self._answer(messageData);
    	}
    },
    
    failAnswer : function(xhr) {
    	//console.log(xhr);
    	$("li").remove("#loading");
		this._answer({body:xhr.responseJSON.message});
    },
    
    _answer : function(appendData) {
    	var self = this;
    	var renderData = $.extend({}, {
    			ttsIcon : false,
				botname : BOTNAME, 
				time : self.getCurrentTime(),
				body : self.speechErrorMessage
		}, appendData);

		var $template = $(self.answerTmpl.render(renderData));
		
		self.$chatHistory.append( $template );
		self.scrollToBottom();
		self.$textarea.focus();
		
		if(typeof speechSynthesizer != 'undefined' && appendData.ttsIcon) {
			speechSynthesizer.matchSource($template);
		}
    },
    
    addMessageEvent: function(event) {
    	if (event.type === "click" || event.keyCode === 13) {
    		this.addMessage();
    		return false;
    	}
    },
    addMessage: function(sendmsg, showmsg) {
        this.messageToSend = sendmsg || this.$textarea.val().trim();
        this.messageToShow = showmsg || this.messageToSend;
        if ($.trim(this.messageToSend) == "") {
            return false;
        }
        this.$textarea.val("");
        this.question();
        this.sender(DOMAIN_ID, USER_KEY);
    },
    sendRichMessage: function(type, text, value) {
    	if(type == "web_url") {
    		var url = value || text;
    		window.open(url.toLowerCase().match(/^https?:/) ? url : '//' + url, '_blank');
    	} else {
    		this.addMessage(value || text, text);
    	}
    },
    scrollToBottom: function() {
    	 this.$chatContainer.animate({ scrollTop: this.$chatContainer[0].scrollHeight }, 500);
    },
    getCurrentDateTime : function() {
    	return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
    },
    getCurrentTime: function() {
    	var time = this.getCurrentDateTime();
        if (time.split(":")[2]) {
        	time = time.substring(0, time.length - 4);
        }
        return time; 
    },
    
    afterGreeting : function(data) {
    	var self = this;
		self.speechErrorMessage = data.ttserror;
    	self.enableSTT = data.enableSTT;
		self.enableTTS = data.enableTTS;
		
    	if(self.enableSTT) {
			if (!('webkitSpeechRecognition' in window)) {
				console.log("webkitSpeechRecognition is not available.");
			}
			else {
				var micIcon = $("<i class='microphone' aria-hidden=true id='message-mic' />").click(function() { speechRecognition.toggle(!$(this).hasClass("active"));	});
				micIcon.insertBefore(self.$textarea);
				speechRecognition = new SpeechRecognition(self.$textarea.attr("placeholder"), MSG_SPEAK_MESSAGE);
			}
		}
		if(self.enableTTS) {
			speechSynthesizer = new SpeechSynthesis(DOMAIN_ID);
		}
    },
    speechText : function(obj) {
    	if(typeof speechSynthesizer != 'undefined') {
    		var $bubble = $(obj).siblings(".bubble");
    		if($(obj).hasClass("active")) {
    			speechSynthesizer.stopall($bubble.find(".item.active"));
    		}
    		else {
	    		var speekCompleted = speechSynthesizer.speak($bubble, true);
	    		if(!speekCompleted) {
	    			this._answer({ttsIcon : false});
	    		}
    		}
    	}
	}
};