// jQuery Alert Dialogs Plugin
//
// Version 1.1
//
// 9
// A Beautiful Site (http://abeautifulsite.net/)
// 14 May 2009
//
// Visit http://abeautifulsite.net/notebook/87 for more information
//
// Usage:
//		jAlert( message, [title, callback] )
//		jError( message, [value, title, callback] )
//		jConfirm( message, [title, callback] )
//		jPrompt( message, [value, title, callback] )
// 
// History:
//
//		1.00 - Released (29 December 2008)
//
//		1.01 - Fixed bug where unbinding would destroy all resize events
//
// License:
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
(function($) {
	
	$.alerts = {
		
		// These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time
		
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .01,                // transparency level of overlay
		overlayColor: '#FFF',               // base color of overlay
		draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
		okButton: '&nbsp;OK&nbsp;',         // text for the OK button
		cancelButton: '&nbsp;Cancelar&nbsp;', // text for the Cancel button
		dialogClass: null,                  // if specified, this class will be applied to all dialogs
		
		// Public methods
		
		alert: function(message, title, errorCode, recomandation, callback) {
			if( title == null ) title = 'Alert';
			$.alerts._show(title, jqueryAlerta, errorCode, recomandation, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		
		error: function(message, title, errorCode, recomandation, callback) {
			if( title == null ) title = 'Error';
			$.alerts._show(title, jqueryError, errorCode, recomandation, message, null, 'error', function(result) {
				if( callback ) callback(result);
			});
		},
		
		info: function(message, title, errorCode, recomandation, callback) {
			if( title == null ) title = 'Info';
			$.alerts._show(title, jqueryInfo, errorCode, recomandation, message, null, 'info', function(result) {
				if( callback ) callback(result);
			});
		},
		
		help: function(message, title, errorCode, recomandation, callback) {
			if( title == null ) title = 'Help';
			$.alerts._show(title, jqueryAyuda, errorCode, recomandation, message, null, 'help', function(result) {
				if( callback ) callback(result);
			});
		},
		
		confirm: function(message, title, errorCode, recomandation, callback, btnCancelar) {
			if( title == null ) title = 'Confirm';
			$.alerts._show(title, jqueryConfirmar, errorCode, recomandation, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			}, btnCancelar);
		},
			
		prompt: function(message, title, errorCode, recomandation, value, callback) {
			if( title == null ) title = 'Prompt';
			$.alerts._show(title, jqueryAviso, errorCode, recomandation, message, value, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		
		// Private methods
		
		_show: function(title, typeMsg, errorCode, recomandation, msg, value, type, callback, btnCancelar) {
			
			$.alerts._hide();
			$.alerts._overlay('show');
			
			$("BODY").append(
			  '<div id="popup_container">' +
			    '<h1 id="popup_title">&nbsp;</h1>' +
			    '<div id="popup_content">' +
				'<div id="imgMsg" style="left: 0px;"></div>' +
				'<div id="img"></div>' +
			      '<div id="popup_message">' +
			        '<div id="tipoMsg"></div>' +
					'<div id="contenido">' +
						'<div id="titulo"></div>' +
						'<div id="descripcion"></div>' +
						'<div id="formulario"></div>' +
						'<div id="recomendacion"></div>' +
					'</div>' +
				  '</div>' +
				'</div>' +
			  '</div>');
			
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);
			
			// IE6 Fix
			var pos = ($.browser.msie && parseInt($.browser.version) <= 8 ) ? 'absolute' : 'fixed';
			
			$("#popup_container").css({
				position: pos,
				zIndex: 99999,
				padding: 0,
				margin: 0
			});
			
			$('#popup_message').bgiframe({top: 0, left: 0, width: 427, height: 310});
			
			if($.browser.msie && parseInt($.browser.version) < 8) {
				$("#popup_message").css({
					width: "323px"
				});
			}
			
			//$("#popup_title").text('');
			$("#titulo").text(title);
			$("#tipoMsg").html('<table width="100%"><tr><td width="100%" align="right">' + errorCode + '</td></tr></table>');
			$("#imgMsg").html('<table width="100%"><tr><td width="100%" align="center">' + typeMsg + '</td></tr></table>');
			$("#descripcion").text(msg);
			//$("#descripcion").html( $("#popup_message").text().replace(/\n/g, '<br />') );
			//$("#img").addClass(type);
			var contextPath = document.getElementById("contextPath").value;
			if(recomandation != null && recomandation != "") {
				$("#recomendacion").html('<img src="' + contextPath + '/img/dialogBox/nuve.gif" width="12" height="12" />' + recomandation);
			}
			//$("#popup_message").text(msg);
			//$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
			
			$("#popup_container").css({
				minWidth: $("#popup_container").outerWidth(),
				maxWidth: $("#popup_container").outerWidth()
			});
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);
			
			switch( type ) {
				case 'alert':
					$("#img").css({
						background: "#FFFFFF url(" + contextPath + "/img/dialogBox/advertencia.gif) no-repeat 12px top"
					});
					$("#tipoMsg").css({
						color: "#FFCC00"
					});
					
					$("#imgMsg").css({
						color: "#FFCC00"
					});
					
					//$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a></h1>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'error':
					$("#img").css({
						background: "#FFFFFF url(" + contextPath + "/img/dialogBox/error.gif) no-repeat 12px top"
					});
					$("#tipoMsg").css({
						color: "#FF0000"
					});
					
					$("#imgMsg").css({
						color: "#FF0000"
					});
					
					//$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a></h1>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'info':
					$("#img").css({
						background: "#FFFFFF url(" + contextPath + "/img/dialogBox/informacion.gif) no-repeat 12px top"
					});
					$("#tipoMsg").css({
						color: "#0071F8"
					});
					
					$("#imgMsg").css({
						color: "#0071F8"
					});
					
					//$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a></h1>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'help':
					$("#img").css({
						background: "#FFFFFF url(" + contextPath + "/img/dialogBox/ayuda.gif) no-repeat 12px top"
					});
					$("#tipoMsg").css({
						color: "#1DA8AF"
					});
					
					$("#imgMsg").css({
						color: "#1DA8AF"
					});
					
					//$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a></h1>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'confirm':
					$("#img").css({
						background: "#FFFFFF url(" + contextPath + "/img/dialogBox/ayuda.gif) no-repeat 12px top"
					});
					$("#tipoMsg").css({
						color: "#1DA8AF"
					});
					
					$("#imgMsg").css({
						color: "#1DA8AF"
					});
					
					if (btnCancelar == null || btnCancelar=='undefined')
						$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a><a id="popup_cancel" href="#">' + $.alerts.cancelButton + '</a></h1>');
					else
						$("#popup_message").after('<h1 id="popup_panel"><a id="popup_ok" href="#">' + $.alerts.okButton + '</a><a id="popup_cancel" href="#">' + btnCancelar + '</a></h1>');
					
					$("#popup_ok").click( function() {
						$.alerts._hide();
						if( callback ) callback(true);
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback(false);
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
				break;
				case 'prompt':
					$("#recomendacion").append('<br /><input type="text" size="30" id="popup_prompt" />');
					$("#popup_message").after('<h1 id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></h1>');
					//$("#popup_prompt").width( $("#popup_message").width() );
					$("#popup_ok").click( function() {
						var val = $("#popup_prompt").val();
						$.alerts._hide();
						if( callback ) callback( val );
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback( null );
					});
					$("#popup_prompt, #popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
					if( value ) $("#popup_prompt").val(value);
					$("#popup_prompt").focus().select();
				break;
			}
			
			// Make draggable
			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ handle: $("#popup_title") });
					$("#popup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		_hide: function() {
			$("#popup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},
		
		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="popup_overlay"></div>');
					$("#popup_overlay").css({
						position: 'absolute',
						zIndex: 99998,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),
						background: $.alerts.overlayColor,
						opacity: $.alerts.overlayOpacity
					});
				break;
				case 'hide':
					$("#popup_overlay").remove();
				break;
			}
		},
		
		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			// IE6 fix
			if( $.browser.msie && parseInt($.browser.version) <= 8 ) { 
				top = top + $(window).scrollTop();
				if ($("#popup_container").outerWidth() < 301) { 
					$("#popup_container").css({
						width: 300
					});
				}
			}
			
			if ($("#popup_container").outerWidth() > 600) { 
				$("#popup_container").css({
					width: 600
				});
			}
			
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height( $(document).height() );
			
			if ($("#popup_container").outerHeight() > 300) { 
				$("#popup_container").css({
					height: 300
				});
			}
		},
		
		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
		
	}
	
	// Shortuct functions
	jAlert = function(message, title, errorCode, recomandation, callback) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.alert(message, title, errorCode, recomandation, callback);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.alert(message, title, errorCode, recomandation, callback);
				}
			);
		}
	};
	
	jError = function(message, title, errorCode, recomandation, callback) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.error(message, title, errorCode, recomandation, callback);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.error(message, title, errorCode, recomandation, callback);
				}
			);
		}
	};
	
	jInfo = function(message, title, errorCode, recomandation, callback) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.info(message, title, errorCode, recomandation, callback);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.info(message, title, errorCode, recomandation, callback);
				}
			);
		}
	};
	
	jHelp = function(message, title, errorCode, recomandation, callback) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.help(message, title, errorCode, recomandation, callback);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.help(message, title, errorCode, recomandation, callback);
				}
			);
		}
	};
	
	jConfirm = function(message, title, errorCode, recomandation, callback, btnCancelar) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.confirm(message, title, errorCode, recomandation, callback, btnCancelar);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.confirm(message, title, errorCode, recomandation, callback, btnCancelar);
				}
			);
		}
	};
		
	jPrompt = function(message, title, errorCode, recomandation, value, callback) {
		if(($.browser.mozilla && parseFloat($.browser.version) >= 1.9)) {
			$.alerts.prompt(message, title, errorCode, recomandation, value, callback);
		} else {
			jQuery(document).ready(
				function(){
					$.alerts.prompt(message, title, errorCode, recomandation, value, callback);
				}
			);
		}
	};
	
})(jQuery);