function selectMenuItem(navlistid) {
	var ulElement = document.getElementById("mainMenu");
	for(var i=0; i < ulElement.getElementsByTagName("li").length; i++) {
		var liElement = ulElement.getElementsByTagName("li")[i];
		var ulUlElement = liElement.getElementsByTagName("ul")[0];
		if(liElement.id == navlistid) {
			if(ulUlElement != null) {
				if(ulUlElement.style.display == "block") {
					liElement.className = removeStyle(liElement.className, "selectedWithSubMenus");
					ulUlElement.style.display = "none";
				} else {
					liElement.className = liElement.className + " selectedWithSubMenus";
					ulUlElement.style.display = "block";
				}
			} else {
				liElement.className = liElement.className + " selected";
			}
		} else {
			if(ulUlElement != null) {
				ulUlElement.style.display = "none";
				liElement.className = removeStyle(liElement.className, "selectedWithSubMenus");
			} else {
				liElement.className = removeStyle(liElement.className, "selected");
			}
		}
	}
}

function selectSubMenuItem(idSubmenu) {
	var liElement = document.getElementById(idSubmenu);
	var ulElement = liElement.parentNode;
	for(var i=0; i < ulElement.getElementsByTagName("li").length; i++) {
		var liElementIt = ulElement.getElementsByTagName("li")[i];
		if(liElement.id == liElementIt.id) {
			liElement.className = "subSelected";
		} else {
			liElementIt.className = removeStyle(liElementIt.className, "subSelected");
		}
	}
}

function removeStyle(styles, styleToRemove) {
	var classes = new Array();
	var stylesTemp = "";
	
	classes = styles.split(" ");
	for(var i=0; i<classes.length; i++) {
		if(classes[i] != "" && classes[i] != styleToRemove) {
			stylesTemp = stylesTemp + classes[i] + " ";
		}
	}
	
	if(stylesTemp.length > 0) {
		return stylesTemp.substring(0, stylesTemp.length-1);
	} else {
		return stylesTemp;
	}
}

/*Funcion que agrega un item ó subItem al menú
	idItem - Id del elemento del menú a agregar (requerido y único en la pagina)
	textItem - Texto que se mostrará como elemento del menú
	idAfterItem - Id del elemento que se situara posteriormente al elemento del menú a insertar (si no se indica se insertará al último)
	idParentItem - Id del Elemento padre (requerido para agregar subMenus)
	isWithSubMenus - True si contendrá submenus (requerido solo para item principales)
	isStartMenuGroup - True si comenzará un nuevo grupo
*/
function addMenuItem(idItem, textItem, idAfterItem, idParentItem, isWithSubMenus, isStartMenuGroup){
	var liParentElement = document.getElementById(idParentItem);
	var liAfterElement = document.getElementById(idAfterItem);
	var ulElement = document.getElementById("mainMenu");
	var liElement = document.createElement("li");
	var aElement = document.createElement("a");
	var spanElement = document.createElement("span");
	var textElement = document.createTextNode(textItem);
	var subMenuIcon = document.createTextNode(">");
	var ulUlElement = document.createElement("ul");
	
	liElement.id = idItem;
	if(esVacio(idParentItem)) {
		if(isStartMenuGroup) {
			liElement.className = "startMenuGroup";
		}
	
		aElement.href = "javascript:selectMenuItem('"+idItem+"')";
		
		spanElement.appendChild(textElement);
		aElement.appendChild(spanElement);
		liElement.appendChild(aElement);
		
		if(isWithSubMenus) {
			liElement.className = liElement.className + " withSubMenus";
			liElement.appendChild(ulUlElement);
		}
		
		ulElement.insertBefore(liElement, liAfterElement);
	} else {
		aElement.href = "javascript:selectSubMenuItem('"+idItem+"')";
		spanElement.className = "subMenuText";
		
		spanElement.appendChild(textElement);
		aElement.appendChild(subMenuIcon);
		aElement.appendChild(spanElement);
		liElement.appendChild(aElement);
		
		var ulUlElement = liParentElement.getElementsByTagName("ul")[0];
		
		ulUlElement.insertBefore(liElement, liAfterElement);
	}
}

function removeMenuItem(idMenuItem) {
	var liElementToRemove = document.getElementById(idMenuItem);
	var ulElement = liElementToRemove.parentNode;
	ulElement.removeChild(liElementToRemove);
}

function disabledMenuItem(idMenuItem) {
	var liElementToDisabled = document.getElementById(idMenuItem);
	var aElement = liElementToDisabled.getElementsByTagName("a")[0];
	aElement.onclick = function(a) {return false;}
	liElementToDisabled.className = liElementToDisabled.className + " disabled";
}

function enabledMenuItem(idMenuItem) {
	var liElementToEnable = document.getElementById(idMenuItem);
	var aElement = liElementToEnable.getElementsByTagName("a")[0];
	aElement.onclick = "";
	liElementToEnable.className = removeStyle(liElementToEnable.className, "disabled");
}

function enabledMenuItems(menuItems, enabledMenu, ruleMenu) {
	//Si menú dinámico se encuentra habilitado habilitado
	if(enabledMenu == "1") {
		var arrayItems = new Array();
		//Se obtiene regla
		var isExclusive = true;
		//Si regla es excluyente
		if(ruleMenu == "0") {
			enabledAllMenu(true);
		} else {
			enabledAllMenu(false);
		}
		arrayItems = menuItems.split(",");
		for(var i=0; i<arrayItems.length; i++) {
			if(ruleMenu == "0") {
				try{
					disabledMenuItem(arrayItems[i]);
				} catch(error){}
			} else {
				try{
					enabledMenuItem(arrayItems[i]);
				} catch(error){}
			}
		}
	}
}

function enabledAllMenu(enabled) {
	var ulElement = document.getElementById("mainMenu");
	for(var i=0; i < ulElement.getElementsByTagName("li").length; i++) {
		var liElement = ulElement.getElementsByTagName("li")[i];
		var aElement = liElement.getElementsByTagName("a")[0];
		
		if(enabled) {
			aElement.onclick = "";
			liElement.className = removeStyle(liElement.className, "disabled");
		} else {
			aElement.onclick = function(a) {return false;}
			liElement.className = liElement.className + " disabled";
		}
		
		var ulUlElement = liElement.getElementsByTagName("ul")[0];
		if(ulUlElement != null) {
			for(var j=0; j < ulUlElement.getElementsByTagName("li").length; j++) {
				var liliElement = ulUlElement.getElementsByTagName("li")[j];
				var aaElement = liliElement.getElementsByTagName("a")[0];
				if(enabled) {
					aaElement.onclick = "";
					liliElement.className = removeStyle(liliElement.className, "disabled");
				} else {
					aaElement.onclick = function(a) {return false;}
					liliElement.className = liliElement.className + " disabled";
				}
			}
		}
	}
}