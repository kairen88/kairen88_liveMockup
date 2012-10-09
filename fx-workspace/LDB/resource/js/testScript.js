//create a div element and insert into code window
var newdiv = document.createElement('div');
var divIdName = 'div1';
newdiv.setAttribute('id',divIdName);
newdiv.style.width = "100px";
newdiv.style.height = "100px";
newdiv.style.position = "absolute";
newdiv.style.background = "#FF0000";
newdiv.innerHTML = 'this is 1st DIV';
// document.body.appendChild(newdiv);



//testing hover function
function addElement(position){
	var newdiv = document.createElement('div');
	var divIdName = 'div2';
	newdiv.setAttribute('id',divIdName);
	newdiv.style.width = "100px";
	newdiv.style.height = "100px";
	newdiv.style.position = "absolute";
	newdiv.style.background = "#FFA500";
	newdiv.innerHTML = 'this is 2nd DIV';
	document.body.appendChild(newdiv);
	$('#div2').offset({ top: position.top + 10, left: position.left + 10});
}

// $('.cm-variable').hover(function () {

	// var position = $(this).position();
	// addElement(position);
	// $(this).append($('<span> ***</span>'));
	
// }, function () {

	// $(this).find('span:last').remove();
	
// });



//attach a div element(tag) to each element in code window with class cm-variable
function addTag(position, varIdx)
{
	var newdiv = document.createElement('div');
	var divIdName = 'div' + varIdx;
	newdiv.setAttribute('id',divIdName);
	newdiv.style.width = "10px";
	newdiv.style.height = "10px";
	newdiv.style.position = "absolute";
	newdiv.style.background = "#FFA500";
	newdiv.innerHTML = '5';
	
	document.body.appendChild(newdiv);
	
	return newdiv;
}

var varIdx = 1; 

$('.cm-variable').each(function(){
	var position = $(this).position();
	var tag = addTag(position, varIdx);
	$(this).append(tag);
	$('#div'+ varIdx).offset({ top: position.top + 5, left: position.left + 35});varIdx += 1;
});