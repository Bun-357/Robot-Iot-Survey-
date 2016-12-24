<html>
  
  <head>
    <title>Robot NCU</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
    rel="stylesheet" type="text/css">
    <link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
    rel="stylesheet" type="text/css">
    <style type="text/css">
      #myImg {
                                
                                /* Preserve aspet ratio */
                                min-width: 40%;
                                min-height: 40%;
                              }
    </style>
    <script src="https://netpie.io/microgear.js"></script>
    <script>
      //const APPID     = "Led8x8Mono";
                                      //const APPKEY    = "8NH4EmFlB4qIRe9";
                                      //const APPSECRET = "Za2VI7miO3Zq1PkXfLmXyzR06";
                                      //const ALIAS = "myX";
      								
      								const APPID     = "<?php echo $app_id; ?>";
                                      const APPKEY    = "<?php echo $app_key; ?>";
                                      const APPSECRET = "<?php echo $app_secret; ?>";
                                      const ALIAS = "<?php echo $alias; ?>";
                                    	
                                    	var w = 87;
                                    var a = 65;
                                    var s = 83;
                                    var d = 68;
                                    var keyOut = "1234";
                                    var x = 0;
                                    	var ss = 0;
                                    	var ssO = 0;
                                    	var robot_name = " ";
      								var pub = "/robot_video/"
      								robot_name = "<?php echo $userRobot; ?>";
       var sup = "/control/" ;
      sup = sup.concat(robot_name);
                                      
                                      var microgear = Microgear.create({
                                            key: APPKEY,
                                            secret: APPSECRET,
                                            alias : ALIAS         /*  optional  */
                                        });
                                      
                                      var imgX = "data:image/jpeg;base64,";
                                      
                                      microgear.on('message',function(topic,msg) {
                                        	//var res = str.substring(0, 4);//str.substring(4);
      document.getElementById("videoState").innerHTML = "Video online ";
                                    	  var temPp = "";
                                    	 // temPp = msg;
                                    	  temPp = msg.substring(0,4);
                                    	  var apower = parseFloat(temPp)
                                    	 
                                    	  apower = ((5/1024)*apower)*1.68;
                                    	  apower = parseFloat(apower).toFixed(2);
                                    	document.getElementById("power").innerHTML = apower+" V";
                                         //document.getElementById("myImg").src = imgX+msg;
                                    	 // var temVp = "";
                                    	 // temVp = msg;
                                    	 // temVp = temVp.substring(4);
                                    	  document.getElementById("myImg").src = imgX+msg.substring(4);
                                    	  
                                    	
                                        });
                                        
                                         microgear.on('connected', function() {
                                           
                                            document.getElementById("data").innerHTML = "Now I am connected with netpie...";
      										document.getElementById("btS").disabled = false;
      										document.getElementById("btSP").disabled = false;
      										pub = pub.concat(robot_name);
                                    	      microgear.subscribe(pub);
      
        document.getElementById("data").innerHTML = sup;                          	     
                                    	     //
                                    	     	 document.getElementById("demo").innerHTML = keyOut;
                                    //
                                    //key down
                                    document.addEventListener('keydown', function(event) {
                                        
                                        if (event.keyCode == w) {
                                            keyOut = keyOut.replace("1", "w");
                                          }
                                    if (event.keyCode == a) {
                                            keyOut = keyOut.replace("2", "a");
                                          }
                                    if (event.keyCode == s) {
                                            keyOut = keyOut.replace("3", "s");
                                          }
                                    if (event.keyCode == d) {
                                            keyOut = keyOut.replace("4", "d");
                                          }
                                        
                                    }, true);
                                    //
                                    //key up
                                    document.addEventListener('keyup', function(event) {
                                        
                                        if (event.keyCode == w) {
                                            keyOut = keyOut.replace("w", "1");
                                          }
                                    if (event.keyCode == a) {
                                            keyOut = keyOut.replace("a", "2");
                                          }
                                    if (event.keyCode == s) {
                                            keyOut = keyOut.replace("s", "3");
                                          }
                                    if (event.keyCode == d) {
                                            keyOut = keyOut.replace("d", "4");
                                          }
                                        
                                    }, true);
                                    	     
                                     setInterval(function() {
                                    	 
                                    	 
                                    	 if(ss == 1){
                                             	document.getElementById("demo").innerHTML = keyOut;
                                    	 	 //microgear.chat(robot_name,keyOut);
                                                  try{
                                                             microgear.publish(sup,keyOut); 
                                                       }catch(err) {
      
                                                        }
                                     	}else{
                                    	     document.getElementById("demo").innerHTML = "press button";	
                                    	}
                                    	 
                                    	 if(ssO == 1){
                                    	    	//microgear.chat(robot_name,"start");
                                               
                                                try{
                                                      microgear.publish(sup,"start"); 
                                                     }catch(err) {
      
                                                      }
                                    		 ssO = 0;
                                    	    }
                                    	 
                                    	 
                                     },250);
                                    	     
                                    	     
                                    	     
                                    	     
                                    	     //
                                        });
                                        
                                         microgear.on('present', function(event) {
                                            console.log(event);
                                        });
                                        microgear.on('absent', function(event) {
                                            console.log(event);
                                        });
                                    	
                                    	microgear.on("error", function(err) {
                                    		console.log("Error: "+err);
                                        document.getElementById("data").innerHTML = err;
                                    	
                                    	});
                                        microgear.connect(APPID);
                                    	
                                    	function stratVideo(){
                                    		
                                    		ss = 1;
                                    		//microgear.chat("aVideo","start");
                                    		//microgear.chat("aVideo","start");
                                    		ssO = 1;
                                    		//document.getElementById("videoState").innerHTML = "Video online ";
                                    		
                                    	}
                                    	
                                    	function stopVideo(){
                                    		
                                    		ss = 0;
                                    		//microgear.chat(robot_name,"stop");
                                                try{
                                    		         microgear.publish(sup,"stop"); 
                                                   }catch(err) {
      
                                                      }
                                        document.getElementById("videoState").innerHTML = "Video offline ,press Start to control robot";
                                    	//location.reload();	
                                    	}
    </script>
  </head>
  
  <body>
    <div class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand"><span>Wellcome   <?php echo $username; ?></span></a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-ex-collapse">
          <ul class="nav navbar-nav navbar-right"></ul>
          <a class="btn btn-sm btn-warning navbar-btn" href="home/logout" data-toggle="modal">Logout</a>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-7 text-center">
            <img id="myImg" src="data:image/jpeg;base64,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"
            class="img-responsive" alt="Cinque Terre" width="640" height="480">
            <h3>Live video from robot :
              <?php echo $userRobot; ?>
            </h3>
            <h3 id="videoState">Video offline ,press Start to control robot</h3>
          </div>
          <div class="col-md-5">
            <img src="..\..\WASD_Controls_2.png" class="img-responsive">
            <div class="section">
              <div class="container">
                <div class="row">
                  <div class="col-md-2">
                    <button class="btn btn-danger btn-lg" onclick="stopVideo();" id="btSP">Stop control</button>
                  </div>
                  <div class="col-md-6">
                    <button class="btn btn-lg btn-success" onclick="stratVideo();" id="btS">Start control</button>
                  </div>
                  <div class="col-md-4"></div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <h4>Robot's battery voltage</h4>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <h4 id="power" class="text-warning">V</h4>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-3"></div>
          <div class="col-md-1"></div>
          <div class="col-md-2"></div>
          <div class="col-md-2"></div>
          <div class="col-md-1"></div>
          <div class="col-md-1"></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div id="data">Waite for conect NetPie</div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <p id="demo" class="text-center">-</p>
          </div>
        </div>
      </div>
    </div>
    <script>
      document.getElementById("btS").disabled = true;
    document.getElementById("btSP").disabled = true;
    </script>
  </body>

</html>