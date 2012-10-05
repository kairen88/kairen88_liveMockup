var img;
var originalWidth;
var originalHeight;
var iconsHeight;

function zoomIn()
{
	w = img.width * 1.2;
	img.width = w;
	
	saveZoom(w);
}

function zoomOut()
{
	w = img.width * 0.8
	img.width = w;

	saveZoom(w);
}

function fit()
{
	// determine size of window
	w = 640;
	h = 480;

	if (self.innerWidth) 
	{ 
		w = self.innerWidth; 
		h = self.innerHeight; 
	} 
	else if (document.documentElement && document.documentElement.clientWidth) 
	{ 
		w = document.documentElement.clientWidth; 
		h = document.documentElement.clientHeight; 
	} 
	else if (document.body) 
	{ 
		w = document.body.clientWidth; 
		h = document.body.clientHeight; 
	} 

	// Allow a margin around the image
	w = w - 30;
	h = h - 30 - iconsHeight;
	
	// Determine scale
	scaleX = w / originalWidth;
	scaleY = h / originalHeight;
	
	scale = Math.min (Math.min (scaleX, scaleY), 1);
	
	// Set zoom
	img.width = originalWidth * scale;
	
	saveZoom("fit");
}

function fullSize()
{
	img.width = originalWidth;
	saveZoom("fullSize");
}

function saveZoom(zoom)
{
	setCookie("stsn_zoom", zoom, null, null, null, null);
}

function loadZoom()
{
	img = document.mainImg;

	// Save original size of the main image	
	originalWidth = img.width;
	originalHeight = img.height;
	
	// Determine height of icons
	iconsHeight = document.zoomInIcon.height;
	
	// Restore zoom
	zoom = getCookie("stsn_zoom");
	if (zoom == null) zoom = "fit";
	
	if (zoom == "fit") fit();
	else if (zoom == "fullSize") fullSize();
	else img.width = zoom;
	
	// Show the image
	img.style.visibility = "visible";
	
	// Register listener for window resize
	window.onresize = resized;
}

function fetchImages(prev, next)
{
	if (prev != null && prev != "null") document.prevImage.src = prev;
	if (next != null && next != "null") document.nextImage.src = next;
}

function resized(e)
{
	zoom = getCookie("stsn_zoom");
	if (zoom == "fit") fit();
}

function setCookie(name, value, expires, path, domain, secure) 
{
	var curCookie = name + "=" + escape(value) +
		((expires) ? "; expires=" + expires.toGMTString() : "") +
		((path) ? "; path=" + path : "") +
		((domain) ? "; domain=" + domain : "") +
		((secure) ? "; secure" : "");
	document.cookie = curCookie;
}

function getCookie(name) 
{
	var dc = document.cookie;
	var prefix = name + "=";
	var begin = dc.indexOf("; " + prefix);
	if (begin == -1) 
	{
		begin = dc.indexOf(prefix);
		if (begin != 0) return null;
	} 
	else begin += 2;
	
	var end = document.cookie.indexOf(";", begin);
	if (end == -1) end = dc.length;
	
	return unescape(dc.substring(begin + prefix.length, end));
}
