function merchant(type,data)
{
var data = {type:"WCA_Merchant",mid:data,utype:type};
connectWebViewJavascriptBridge(function(bridge) {
			bridge.send(data, function(response) {
			});
		})
}
function getVesion()
{
var data = {type:"WCA_Version"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {
                  alert(responseData);
                }
            );
}
function getMid()
{
var data = {type:"WCA_Mid"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {
                  alert(responseData);
                }
            );
}
function closeWin()
{
var data = {type:"WCA_CloseWin"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {
                }
            );
}
function message()
{
var data = {type:"WCA_Message"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {

                }
            );
}

function app_scan()
{
 var data = {type:"WCA_Scan"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {

                }
            );
}

function  singleChat(user_id)
{
   var data = {type:"WCA_SingleChat",userid:user_id};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {

                }
            );
}
function  chatList()
{
   var data = {type:"WCA_ChatList"};
            window.WebViewJavascriptBridge.send(
                data
                , function(responseData) {

                }
            );
}
 function connectWebViewJavascriptBridge(callback) {
            if (window.WebViewJavascriptBridge) {
                callback(WebViewJavascriptBridge)
            } else {
                document.addEventListener(
                    'WebViewJavascriptBridgeReady'
                    , function() {
                        callback(WebViewJavascriptBridge)
                    },
                    false
                );
            }
        }
