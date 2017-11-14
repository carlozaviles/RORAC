function initialize(menuItem, menuSubitem) {
	try{
		if(!esVacio(menuItem)) {
			selectMenuItem(menuItem);
			if(!esVacio(menuSubitem)) {
				selectSubMenuItem(menuSubitem);
			}
		}
		ini();
	}catch(errorInfo){
		
	}
}

function estableceAyuda(lnkAyuda) {
	try{
		if(!esVacio(lnkAyuda)) {
			linkAyuda = lnkAyuda;
		}
		else{
			linkAyuda = "";
		}
	}
	catch(errorInfo){
		
	}
}


function changeSelectedTab(idTab, idDiv) {
	hideDivsTabs();
	document.getElementById(idDiv).style.display = "block";
	var ulElement = document.getElementById("tabs");
	for(var i=0; i < ulElement.getElementsByTagName("li").length; i++) {
		var liElement = ulElement.getElementsByTagName("li")[i];
		if(liElement.id == idTab) {
			liElement.className  = "active";
		} else {
			liElement.className = "";
		}
	}
}

function setTabsWidth(containerId){
	var fontWidth = 6.5;
	var borderWidth = 28;
	
	var container = document.getElementById(containerId);
	var totalTabs = container.getElementsByTagName("li");
	var containerWidth = 0;			
	var tabsWidth = 0;
	var tabsByLine = new Array();
	var subArray = new Array();
	var lineIndex = 0;
	var tabInLineIndex = 0;
	
	if(container != null){
		containerWidth = container.scrollWidth - 15;
		for(var tabIndex = 0; tabIndex < totalTabs.length; tabIndex++){
			var currentTab = totalTabs[tabIndex];
			var currentHref = totalTabs[tabIndex].getElementsByTagName("a");
			if(tabsWidth + borderWidth + currentHref[0].innerHTML.length * fontWidth <= containerWidth){
				tabsWidth += borderWidth + currentHref[0].innerHTML.length * fontWidth;
				subArray[tabInLineIndex] = currentTab;
				tabInLineIndex++;
			} else {
				tabsByLine[lineIndex] = subArray;
				tabInLineIndex = 1;
				lineIndex++;
				subArray = new Array();
				tabsWidth = borderWidth + currentHref[0].innerHTML.length * fontWidth;
				subArray[0] = currentTab;  
			}
		}
		tabsByLine[lineIndex] = subArray;
		
		for(var tabIndex = 0; tabIndex < tabsByLine.length; tabIndex++){
			var tabs = tabsByLine[tabIndex];
			var totalWidth = 0;
			for(var subtabIndex = 0; subtabIndex < tabs.length; subtabIndex++){
				var currentHref = tabs[subtabIndex].getElementsByTagName("a");
				totalWidth += borderWidth + currentHref[0].innerHTML.length * fontWidth;
			}
			
			var remainningWidth = containerWidth - totalWidth;
			var remainningByTab = remainningWidth > 10 ? Math.round((remainningWidth / tabs.length)*10)/10 : 0;
			
			for(var subtabIndex = 0; subtabIndex < tabs.length; subtabIndex++){
				var hrefInTab = tabs[subtabIndex].getElementsByTagName("a");
				tabs[subtabIndex].style.width = (borderWidth + hrefInTab[0].innerHTML.length * fontWidth + remainningByTab) + "px";
			}
		}
		
		container.style.height = (tabsByLine.length * 19) + "px";
	}
}

function createCalendar(dateField,shooter){
	Calendar.setup({
	    inputField: dateField,
	    ifFormat:   '%d/%m/%Y',
	    button:     shooter,
	    weekNumbers: false,
	    firstDay: 0,
	    electric: false
	  });
}

function esVacio(valor){
	try{
		if (valor == null){
			return true;
		}else{
			if(valor.length == 0 || valor == '' || valor == -1){
				return true;
			}else{
				return false;
			}
		}
	}catch(errorInfo){}
	return false;
}

getDimensions = function(oElement) {
    var x, y, w, h;
    x = y = w = h = 0;
    if (document.getBoxObjectFor) { // Mozilla
      var oBox = document.getBoxObjectFor(oElement);
      x = oBox.x-1;
      w = oBox.width;
      y = oBox.y-1;
      h = oBox.height;
    }
    else if (oElement.getBoundingClientRect) { // IE
      var oRect = oElement.getBoundingClientRect();
      x = oRect.left-2;
      w = oElement.clientWidth;
      y = oRect.top-2;
      h = oElement.clientHeight;
    }
    return {x: x, y: y, w: w, h: h};
  }

//<!-- FUNCION JAVASCRIPT QUE FILTRA SIEMPRE POR PRINCIPIO-->
function filtra(txt,num) {
	  var colum=num-1;// num=columna por la que se filtrar&aacute;			 
	  t = document.getElementById('tab');
	  filas = t.getElementsByTagName('tr');
	  for (i=1; ele=filas[i]; i++) {
		texto = ele.getElementsByTagName('td')[colum].innerHTML.toUpperCase();				
		posi = (texto.indexOf(txt.toUpperCase()) == 0);
		ele.style.display = (posi) ? '' : 'none';
	  } 
}

function trim(valorInput) {
	return valorInput.replace(/^\s+|\s+$/g,"");
}

