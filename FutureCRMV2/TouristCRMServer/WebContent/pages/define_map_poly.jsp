<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.net.URLDecoder"%>
<%@page import="com.helper.StringHelperNew"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../theme/maps_inc.jsp"%>
</head>
<body>
	<%
	String areatype=StringHelperNew.n2s(request.getParameter("areatype")) ;
areatype=URLDecoder.decode(areatype);
%>
	<div>
		<div style="z-index: 9999">
			<input type="text" name="from" id="from" size="30"
				placeholder="Enter Source" /> <input type="text" name="to" id="to"
				size="30" placeholder="Enter Destination" />

		</div>
		<span style="float: left; z-index: 999;" id="labelEvacuation"><h1><%=areatype%></h1></span>
		<div id="main-map" style="height: 300px"></div>
	</div>
	<div id="side">


		<select id="typeId" name="typeId" onchange="fnType(this);">

			<option value="3">Point or Building</option>
		</select> <input id="resetId" value="Reset" type="button" class="navi"
			style="background-position: left; background: url('../imgs/refresh.png'); background-repeat: no-repeat; background-color: white; width: 150px;; height: 35px;" />
		<input id="showData" value="  Show Paths" type="button"
			style="background-position: left; background: url('../imgs/points.png'); background-repeat: no-repeat; height: 35px; background-color: white; width: 150px;"
			class="navi" /> <input id="showRoadmapBtnId"
			style="background-position: left; background: url('../imgs/maps.png'); background-repeat: no-repeat; width: 150px; height: 35px; background-color: white;"
			value="Show RoadMap"
			onclick="roadMap($('#from').val(),$('#to').val())" type="button"
			class="navi" /> <input id="showColor" value="Show Color"
			style="display: none;" type="button" class="navi" />
		<div id="dataPanel"></div>

	</div>
	<script type="text/javascript">
	function fnReLoadMap(){
		typeId=document.getElementById('typeId');
		fnChange(typeId);
		dataPanel=$('#dataPanel').html();
		res=dataPanel.split("(");
		var latlnglist = new Array();
		var firstpoint=null;
		var j=0;
		for ( var i= 0; i< res.length; i++) {
			latlng= res[i];
				if($.trim(latlng).length>0){
					latlng=latlng.split(")")[0];
					lat=latlng.split(",")[0];
					lng=latlng.split(",")[1];
					var slatlng=new google.maps.LatLng(lat, lng);
					latlnglist[j]=slatlng;
					j++;
				}
		}	
		if(typeId.value==1||typeId.value==3){
			for ( var i= 0; i< latlnglist.length; i++) {
				creator.pen.draw(latlnglist[i]);
			}
			if(typeId.value==1){
				creator.pen.currentDot=creator.pen.listOfDots[0];
				creator.pen.draw(latlnglist[0]);
			}
		  	map.panTo(latlnglist[0]);
		}else if(typeId.value==2){
			roadMap(latlnglist[0], latlnglist[1]);
		}
	}
	var creator;
	function fnType(typeId){
		$('#to').val("");
		$('#from').val("");
		$('#dataPanel').empty();
		fnChange(typeId);
	}
	
	function fnChange(typeId){
		if(typeId.value==1){	// area
			if(creator!=null)
				creator.destroy();
			  creator = new PolygonCreator(map);
			$('#to').attr('disabled','disabled');
			$('#showRoadmapBtnId').attr('disabled','disabled');
		}else if(typeId.value==2){	 // road
			if(creator!=null)
				creator.destroy();
	 		creator=null;
			$('#to').removeAttr('disabled');
			$('#from').focus();
			$('#showRoadmapBtnId').removeAttr('disabled');
		}else if(typeId.value==3){   // point
			if(creator!=null)
			creator.destroy();
			 creator = new PolygonCreator(map);
			$('#to').attr('disabled','disabled');
			$('#showRoadmapBtnId').attr('disabled','disabled');
		}
	}
	

	
$(document).ready(function(){
	var singapoerCenter=new google.maps.LatLng(18.508702, 73.812498);
	loadMap(singapoerCenter);
	addFromSuggest();
	fnType(document.getElementById('typeId'));
	$('#from').focus();
	
});

function addToSuggest(){


}
function addBtns(){
	var to = /** @type {HTMLInputElement} */(
		      document.getElementById('to'));
	map.controls[google.maps.ControlPosition.LEFT].push(to);
	var labelEvacuation= /** @type {HTMLInputElement} */(
		      document.getElementById('labelEvacuation'));
	map.controls[google.maps.ControlPosition.TOP].push(labelEvacuation);
	  
	
	
	 var searchBox = new google.maps.places.SearchBox(
			    /** @type {HTMLInputElement} */(to));
	var showData = /** @type {HTMLInputElement} */(
		      document.getElementById('showRoadmapBtnId'));
	map.controls[google.maps.ControlPosition.LEFT].push(showData);
	showData = /** @type {HTMLInputElement} */(
		      document.getElementById('showData'));
	map.controls[google.maps.ControlPosition.LEFT].push(showData);
	showData = /** @type {HTMLInputElement} */(
		      document.getElementById('resetId'));
	map.controls[google.maps.ControlPosition.LEFT].push(showData);
	showData = /** @type {HTMLInputElement} */(
		      document.getElementById('showColor'));
	map.controls[google.maps.ControlPosition.LEFT].push(showData);
}
function addFromSuggest(){
	var typeId=document.getElementById('typeId');
	map.controls[google.maps.ControlPosition.LEFT].push(typeId);
	
	var input = /** @type {HTMLInputElement} */(
		      document.getElementById('from'));
	map.controls[google.maps.ControlPosition.LEFT].push(input);
	
	 var searchBox = new google.maps.places.SearchBox(
			    /** @type {HTMLInputElement} */(input));

	 
	 google.maps.event.addListener(searchBox, 'places_changed', function() {
		    var places = searchBox.getPlaces();

		    for (var i = 0, marker; marker = markers[i]; i++) {
		      marker.setMap(null);
		    }

		    // For each place, get the icon, place name, and location.
		    markers = [];
		    var bounds = new google.maps.LatLngBounds();
		    for (var i = 0, place; place = places[i]; i++) {
		      var image = {
		        url: place.icon,
		        size: new google.maps.Size(71, 71),
		        origin: new google.maps.Point(0, 0),
		        anchor: new google.maps.Point(17, 34),
		        scaledSize: new google.maps.Size(25, 25)
		      };

		      // Create a marker for each place.
		      var marker = new google.maps.Marker({
		        map: map,
		        icon: image,
		        title: place.name,
		        position: place.geometry.location
		      });

		      markers.push(marker);

		      bounds.extend(place.geometry.location);
		    }

		    map.fitBounds(bounds);
		  });
	 addBtns();
}
var markers = [];


var myLatLng;
	function fnSearch(address){
	var geo = new google.maps.Geocoder;
	geo.geocode({'address':address},function(results, status){
	    if (status == google.maps.GeocoderStatus.OK) {              
	         myLatLng = results[0].geometry.location;
	         $('#dataPanel').append("("+myLatLng.lat()+","+myLatLng.lng()+")");
			//alert(myLatLng);
	        // Add some code to work with myLatLng              
			
	    } else {
	        alert("Geocode was not successful for the following reason: " + status);
	    }
	});
	
	}  
	function fnGetAddress(latlng){
		//  var latlng = new google.maps.LatLng(lat, lng);

		var geo = new google.maps.Geocoder;
		geo.geocode({'latLng': latlng},function(results, status){
		    if (status == google.maps.GeocoderStatus.OK) {              
		    	results[1].formatted_address;
				alert(results[1].formatted_address);
		        // Add some code to work with myLatLng              
				
		    } else {
		        alert("Reverse Geocode was not successful for the following reason: " + status);
		    }
		});
		
		}  
	function roadMap(from,to){
		 directionsService = new google.maps.DirectionsService();
	        var directionsRequest = {
	          origin:from,
	          destination: to,
	          travelMode: google.maps.DirectionsTravelMode.TRANSIT ,
	          unitSystem: google.maps.UnitSystem.METRIC
	        };
	        directionsService.route(
	          directionsRequest,
	          function(response, status)
	          {
	            if (status == google.maps.DirectionsStatus.OK)  
	            {
	            	
	              new google.maps.DirectionsRenderer({
	                map: map,
	                directions: response
	              });
	              showSteps(response);
	            }
	            else
	              $("#error").append("Unable to retrieve your route<br />");
	          }
	        );
	    

	       
		 
	}
    // Now, clear the array itself.
    markerArray = [];
    var directionsDisplay;
    var directionsService;  
    var stepDisplay;
    stepDisplay = new google.maps.InfoWindow();
	 function showSteps(directionResult) {
     	  // For each step, place a marker, and add the text to the marker's
     	  // info window. Also attach the marker to an array so we
     	  // can keep track of it and remove it when calculating new
     	  // routes.
     	  var myRoute = directionResult.routes[0].legs[0];
     	 
     	  for (var i = 0; i < myRoute.steps.length; i++) {
     	    var marker = new google.maps.Marker({
     	      position: myRoute.steps[i].start_location,
     	      map: map
     	    });
     	    attachInstructionText(marker, myRoute.steps[i].instructions);
     	    markerArray[i] = marker;
     	   //markerArray.push(marker);

     	  }
   	}
	 	function attachInstructionText(marker, text) {
	      	  google.maps.event.addListener(marker, 'click', function() {
	      	    // Open an info window when the marker is clicked on,
	      	    // containing the text of the step.
	      	    stepDisplay.setContent(text);
	      	    stepDisplay.open(map, marker);
	      	  });
	      	}
	 
		function loadMap(singapoerCenter){
		  //create map
		// var singapoerCenter=new google.maps.LatLng(1.37584, 103.829);
		 var myOptions = {
		  	zoom: 18,
		  	center: singapoerCenter,
		  	mapTypeId: google.maps.MapTypeId.ROADMAP
		  }
		 map = new google.maps.Map(document.getElementById('main-map'), myOptions);
		
	
		//  creator.pen.polygon.setColor('#00FF00');   
		 //reset
		 $('#resetId').click(function(){
			 if(creator)
		 		creator.destroy();
		 		creator=null;
		 		
				window.location.reload();
		 });		 
		 
		 
		 //show paths
		 $('#showData').click(function(){ 
			 fnShowPath();
		 });
		 
		 //show color
		 $('#showColor').click(function(){ 
		 		$('#dataPanel').empty();
		 		if(null==creator.showData()){
		 			$('#dataPanel').append('Please first create a polygon');
		 		}else{
		 				$('#dataPanel').append(creator.showColor());
		 		}
		 });
	}
		function fnShowPath(){
			$('#dataPanel').empty();
			
			 if(typeId.value==1){	// area
		 	
		 		
		 		if(null==creator.showData()){
		 			$('#dataPanel').append('Please first create a polygon');
		 		}else{
		 			$('#dataPanel').append(creator.showData());
		 		}
			 }else if(typeId.value==2){	// Road
					 fnSearch($('#from').val());
			 
					//$('#dataPanel').append("("+myLatLng.lat()+","+myLatLng.lng()+")");
					fnSearch($('#to').val());
					//$('#dataPanel').append("\n("+myLatLng.lat()+","+myLatLng.lng()+")");
			 }else if(typeId.value==3){	// Points
				 
				 for ( var j= 0; j< creator.pen.listOfDots.length; j++) {
					 latlng=creator.pen.listOfDots[j].latLng;
					 $('#dataPanel').append("\n("+latlng.lat()+","+latlng.lng()+")");
				}
			 }
			 
			 
			 
		}
	google.maps.LatLng.prototype.distanceFrom = function(newLatLng) {
		// setup our variables
		var lat1 = this.lat();
		var radianLat1 = lat1 * ( Math.PI / 180 );
		var lng1 = this.lng();
		var radianLng1 = lng1 * ( Math.PI / 180 );
		var lat2 = newLatLng.lat();
		var radianLat2 = lat2 * ( Math.PI / 180 );
		var lng2 = newLatLng.lng();
		var radianLng2 = lng2 * ( Math.PI / 180 );
		// sort out the radius, MILES or KM?
		var earth_radius = 3959; // (km = 6378.1) OR (miles = 3959) - radius of the earth

		// sort our the differences
		var diffLat = ( radianLat1 - radianLat2 );
		var diffLng = ( radianLng1 - radianLng2 );
		// put on a wave (hey the earth is round after all)
		var sinLat = Math.sin( diffLat / 2 );
		var sinLng = Math.sin( diffLng / 2 );

		// maths - borrowed from http://www.opensourceconnections.com/wp-content/uploads/2009/02/clientsidehaversinecalculation.html
		var a = Math.pow(sinLat, 2.0) + Math.cos(radianLat1) * Math.cos(radianLat2) * Math.pow(sinLng, 2.0);

		// work out the distance
		var distance = earth_radius * 2 * Math.asin(Math.min(1, Math.sqrt(a)));

		// return the distance
		return distance;
		};
</script>
</body>
</html>
