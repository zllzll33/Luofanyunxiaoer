function connectWebViewJavascriptBridge(callback) {
	if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
	} else {
		document.addEventListener('WebViewJavascriptBridgeReady', function() {
			callback(WebViewJavascriptBridge)
		}, false)
	}
}
//connectWebViewJavascriptBridge(function(bridge) {
//	bridge.init(function(message, responseCallback) {
//	})
//});

function interactive(requestData,needMethod){

		connectWebViewJavascriptBridge(function(bridge) {
			bridge.send(requestData, function(response) {
				if(needMethod){eval(needMethod); }else{return response;}
			});
		});
	if(window._WebView_JS_Info){
		var response = window._WebView_JS_Info.VersionName(requestData);
		if(needMethod){eval(needMethod); }else{return response;}
	}else{
		if(needMethod){eval(needMethod);};
	}
}
