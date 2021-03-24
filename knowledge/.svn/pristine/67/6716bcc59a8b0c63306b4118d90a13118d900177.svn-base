var INIT_MESSAGE = "";

var USER_KEY = '';
var DOMAIN_ID = '';
var BOTNAME = '';
var CONTEXTPATH = "http://" + $('#his_chatbotUrl').val() + "/konanbot/";

var chat = {
    messageToSend: '',
    messageToShow: '',
    messageResponses: [],
    init: function(domain, user, scenario) {
    	var self = this;
        this.cacheDOM();
        this.bindEvents();
        
        USER_KEY = user;
        DOMAIN_ID = domain;

        var params = {};
        params.domain = domain;
        params.user = user;
        params.scenario = scenario;
        
        $.ajax({
        	url: CONTEXTPATH + "chat/open" , 
    		dataType : "json",
    		type : "POST",
    		contentType : "application/json; charset=utf-8",
    		data : JSON.stringify(params),
    		processData : false
    	})
    	.done(function(ret){
    		//console.log(ret);
    		BOTNAME = ret.data.botname;
    		self.answer(ret.payload.contents);
    	});
    },
    cacheDOM: function() {
        this.$chatHistory = $('#chat-history>ul');
        this.$button = $('#send');
        this.$buttonInit= $('#init');
        this.$textarea = $('#chat-textarea');
    },
    bindEvents: function() {
        this.$button.on('click', this.addMessageEvent.bind(this));
        this.$textarea.on('keyup', this.addMessageEvent.bind(this));
    },
    question: function() {
    	this.scrollToBottom();
        if ($.trim(this.messageToSend) !== '') {
            var template = $.templates("#message-response-template");
            var time = this.getCurrentTime();
            if (time.split(":")[2]) {
            	time = time.substring(0, time.length - 4);
            }
            var context = {
                messageOutput: this.messageToShow,
                time: time
            };
            this.$chatHistory.append( $(template.render(context)) );
            this.scrollToBottom();
        }
        var contextResponse = {
            	botname : BOTNAME,
                time: time
            };
        template = $.templates('#message-loading');
        chat.$chatHistory.append( $(template.render(contextResponse)) );
    },

    answer: function(contents) {
        var time = this.getCurrentTime();
        if (time.split(":")[2]) {
        	time = time.substring(0, time.length - 4);
        }
        var contextResponse = {
        		contents: contents, 
        		contextpath: CONTEXTPATH,
        		functionName: 'chat.sendRichMessage'
        };
        $("li").remove("#loading");
        
		var $response =  $(dialogResponseTemplate.render(contextResponse));
		
    	var template = $.templates('#message-template');
    	var $template = $(template.render({botname: BOTNAME, time: time}))
    	$template.find('.bubble').prepend($response);
        chat.$chatHistory.append( $template );
        chat.scrollToBottom();
        $('#chat-textarea').focus();
    },

    sender: function(domain, user) {
        if (this.messageToSend == undefined || this.messageToSend == "") {
            return;
        }
        var time = this.getCurrentTime();
        if (time.split(":")[2]) {
        	time = time.substring(0, time.length - 4);
        }
        
        var self = this;
        var params = {};
        params.domain = domain;
        params.user = user;
        params.text = this.messageToSend;
        
        var contextResponse = {
            	botname : BOTNAME,
                time: time
            };
        
        $.ajax({
        	url: CONTEXTPATH + "chat/message",
    		dataType : "json",
    		type : "POST",
    		contentType : "application/json; charset=utf-8",
    		data : JSON.stringify(params),
    		processData : false
    	})
    	.done(function(ret){
    		//console.log(ret);
    		self.answer(ret.payload.contents);
    	});
        
    },

    initMessage: function() {
    	var msg = "";
    	switch (KEY) {
		case KEY:
			msg = INIT_MESSAGE;
			break;
		}
    	
        this.messageToSend = this.messageToShow = msg;
        this.question();
    },

    addMessage: function(sendmsg, showmsg) {
        this.messageToSend = sendmsg || $('#chat-textarea').val();
        this.messageToShow = showmsg || $('#chat-textarea').val();
        if ($.trim(this.messageToSend) == '') {
            return false;
        }
        this.$textarea.val('');
        this.question();
        this.sender(DOMAIN_ID, USER_KEY);
    },

   addMessageEvent: function(event) {
        // enter was pressed
        if (event.type === 'click' || event.keyCode === 13) {
            this.addMessage();
        }
    },
    scrollToBottom: function() {
    	 var $div = $('#chat-history');
         $div.animate({ scrollTop: $div[0].scrollHeight }, 500);
    },
    getCurrentTime: function() {
        return new Date().toLocaleTimeString().
        replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
    },
    getRandomItem: function(arr) {
        return arr[Math.floor(Math.random() * arr.length)];
    },
    sendRichMessage: function(type, text, value) {
    	if(type == "web_url") {
    		window.open(value || text);
    	} else {
    		this.addMessage(value || text, text);
    	}
    }
};

