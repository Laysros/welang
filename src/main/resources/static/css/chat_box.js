
            //this function can remove a array element.
            Array.remove = function(array, from, to) {
                var rest = array.slice((to || from) + 1 || array.length);
                array.length = from < 0 ? array.length + from : from;
                return array.push.apply(array, rest);
            };
       
            //this variable represents the total number of popups can be displayed according to the viewport width
            var total_popups = 0;
           
            //arrays of popups ids
            var popups = [];
       
            //this is usedload to close a popup
            function close_popup(id)
            {
            	hideDialogueControl(id);
            	endCall();
                for(var iii = 0; iii < popups.length; iii++)
                {
                    if(id == popups[iii])
                    {
                        Array.remove(popups, iii);
                       
                        document.getElementById(id).style.display = "none";
                       
                        calculate_popups();
                       
                        return;
                    }
                }  
            }
       
            //displays the popups. Displays based on the maximum number of popups that can be displayed on the current viewport width
            function display_popups()
            {
                var right = 220;
               
                var iii = 0;
                for(iii; iii < total_popups; iii++)
                {
                    if(popups[iii] != undefined)
                    {
                        var element = document.getElementById(popups[iii]);
                        element.style.right = right + "px";
                        right = right + 320;
                        element.style.display = "block";
                    }
                }
               
                for(var jjj = iii; jjj < popups.length; jjj++)
                {
                    var element = document.getElementById(popups[jjj]);
                    element.style.display = "none";
                }
            }
           
            //creates markup for a new popup. Adds the id to popups array.
            function register_popup(sender, id, name)
            {
               
                for(var iii = 0; iii < popups.length; iii++)
                {  
                    //already registered. Bring it to front.
                    if(id == popups[iii])
                    {
                        Array.remove(popups, iii);
                   
                        popups.unshift(id);
                       
                        calculate_popups();
                       
                       
                        return;
                    }
                }              
               
                
                var chatCount = document.getElementById("non-read-message" + id);
        	  	chatCount.innerHTML="0";
        	  	setSeenMessage(sender, id);
        	  	displayDialogueControl(id);
                //
                var element = '<div class="popup-box chat-popup" id="'+ id +'">';
                element = element + '<div class="popup-head">';
                element = element + '<div class="popup-head-left">'+ name +'</div>';
                element = element + '<div class="popup-head-right"><a href="javascript:close_popup(\''+ id +'\');">&#10005;</a></div>';
                element = element + '<button id="dial-box" onclick="callToId(\''+ id + '\')" value="'+ id +'" class="btn btn-primary "><span class="glyphicon glyphicon-earphone "></span></button>';
                element = element + '<div style="clear: both"></div></div><div class="popup-messages">';
                
                element += loadPage('chat_content', id);
                element += '<div class="input-box">'+ 
                	'<textarea onkeypress="return sendMessage(event, this,\''+ sender + '\',\''+ id + '\' )" placeholder="Enter message"></textarea>'+
                    '</div>'			
                
                
                document.getElementById("chat_panel").innerHTML += element;
                
                var elementTerminator = '</div></div>';
                document.getElementById("chat_panel").innerHTML += elementTerminator;
                
      
                
                
                popups.unshift(id);
                       
                calculate_popups();
               
            }
            
            function displayDialogueControl(id){
            	document.getElementById("friend-id-call").value = id;
            	document.getElementById("dial-control-bar").style.display = "block";
            }
            
            function hideDialogueControl(id){
            	document.getElementById("dial-control-bar").style.display = "none";
            }
            
            function loadPage(href, id)
            {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("GET", href + "/?id="+ id, false);               
                xmlhttp.send();                
                return xmlhttp.responseText;
            }
            
            function setSeenMessage(id, fromId){
    			 var xmlhttp = new XMLHttpRequest();
                 xmlhttp.open("GET", "setSeenMessage/?id="+ id + "&fromId="+ fromId , false);               
                 xmlhttp.send();           
    		}
            
            function loadUpdate(href)
            {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("GET", href, false);
               
                xmlhttp.send();                
                return xmlhttp.responseText;
            }
            
           
            //calculate the total number of popups suitable and then populate the toatal_popups variable.
            function calculate_popups()
            {
                var width = window.innerWidth;
                if(width < 540)
                {
                    total_popups = 0;
                }
                else
                {
                    width = width - 200;
                    //400 is width of a single popup box
                    total_popups = parseInt(width/400);
                }
               
                display_popups();
                
               
            }
           
            //recalculate when window is loaded and also when window is resized.
            window.addEventListener("resize", calculate_popups);
            window.addEventListener("load", calculate_popups);
            
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
*/ 
            
            
            
            
            
        
            