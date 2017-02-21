var fastAJax = function(args) {

	var xmlhttp = null;

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (xmlhttp == null) {
		args.handler("notSupport");
	} else {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				console.log(xmlhttp);
				if (xmlhttp.status == 200) {
					args.handler(xmlhttp.responseText);
				} else {
					args.handler(xmlhttp.statusText);
				}
			}
		};
		
		xmlhttp.open(args.type, args.url, false);
		xmlhttp.send(JSON.stringify(args.data));
	}
};