<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Google Map V3 Polygon Creator</title>
<meta name="keywords" content="polygon,creator,google map,v3,draw,paint">
<meta name="description" content="Google Map V3 Polygon Creator">


<link rel="stylesheet" type="text/css" href="style.css">


<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script
	src="<%=request.getContextPath()%>/jquery.autocomplete-1.1.3/jquery-1.7.2.js"></script>
<script type="text/javascript" src="polygon.min.js"></script>

<script type="text/javascript">
	$(function() {
		//create map
		var singapoerCenter = new google.maps.LatLng(1.37584, 103.829);
		var myOptions = {
			zoom : 10,
			center : singapoerCenter,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		map = new google.maps.Map(document.getElementById('main-map'),
				myOptions);

		var creator = new PolygonCreator(map);

		//reset
		$('#reset').click(function() {
			creator.destroy();
			creator = null;

			creator = new PolygonCreator(map);
		});

		//show paths
		$('#showData').click(function() {
			$('#dataPanel').empty();
			if (null == creator.showData()) {
				$('#dataPanel').append('Please first create a polygon');
			} else {
				$('#dataPanel').append(creator.showData());
			}
		});

		//show color
		$('#showColor').click(function() {
			$('#dataPanel').empty();
			if (null == creator.showData()) {
				$('#dataPanel').append('Please first create a polygon');
			} else {
				$('#dataPanel').append(creator.showColor());
			}
		});
	});
	google.maps.LatLng.prototype.distanceFrom = function(newLatLng) {
		// setup our variables
		var lat1 = this.lat();
		var radianLat1 = lat1 * (Math.PI / 180);
		var lng1 = this.lng();
		var radianLng1 = lng1 * (Math.PI / 180);
		var lat2 = newLatLng.lat();
		var radianLat2 = lat2 * (Math.PI / 180);
		var lng2 = newLatLng.lng();
		var radianLng2 = lng2 * (Math.PI / 180);
		// sort out the radius, MILES or KM?
		var earth_radius = 3959; // (km = 6378.1) OR (miles = 3959) - radius of the earth

		// sort our the differences
		var diffLat = (radianLat1 - radianLat2);
		var diffLng = (radianLng1 - radianLng2);
		// put on a wave (hey the earth is round after all)
		var sinLat = Math.sin(diffLat / 2);
		var sinLng = Math.sin(diffLng / 2);

		// maths - borrowed from http://www.opensourceconnections.com/wp-content/uploads/2009/02/clientsidehaversinecalculation.html
		var a = Math.pow(sinLat, 2.0) + Math.cos(radianLat1)
				* Math.cos(radianLat2) * Math.pow(sinLng, 2.0);

		// work out the distance
		var distance = earth_radius * 2 * Math.asin(Math.min(1, Math.sqrt(a)));

		// return the distance
		return distance;
	};
</script>
</head>
<body>



	<div id="header">

		<p>
			<span class="instruction">Demo Instruction:</span> Left click on the
			map to create markers, when last marker meets first marker, it will
			form a polygon. Right click on the polygon to change its color.
		</p>
	</div>
	<div id="main-map" style="width: 100%;"></div>
	<div id="side">
		<input id="reset" value="Reset" type="button" class="navi" /> <input
			id="showData" value="Show Paths (class function) " type="button"
			class="navi" /> <input id="showColor"
			value="Show Color (class function) " type="button" class="navi" />
		<div id="dataPanel"></div>
	</div>
</body>
</html>