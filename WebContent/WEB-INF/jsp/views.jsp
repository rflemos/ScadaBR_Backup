<%--
    Mango - Open Source M2M - http://mango.serotoninsoftware.com
    Copyright (C) 2006-2011 Serotonin Software Technologies Inc.
    @author Matthew Lohbihler
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.
--%>
<%@ include file="/WEB-INF/jsp/include/tech.jsp"%>
<tag:page dwr="ViewDwr" js="view">
	<script type="text/javascript" src="resources/wz_jsgraphics.js"></script>
	<script type="text/javascript" src="resources/shortcut.js"></script>
	<script type="text/javascript" src="resources/jquery.js"></script>
	
	<script type="text/javascript">
	
	jQuery.noConflict();
	
	shortcut.add("Ctrl+Shift+F",function() {

		setCookie("fullScreen","no");
		
		document.getElementById('mainHeader').style.display = "compact";
  	  	document.getElementById('subHeader').style.display = "compact";
  	  	document.getElementById('graphical').style.display = "compact";
  	  	
  		location.reload(true);

		
	});
	
	<c:if test="${!empty currentView}">
      mango.view.initNormalView();
    </c:if>
    
    
    var nVer = navigator.appVersion;
    var nAgt = navigator.userAgent;
    var browserName  = navigator.appName;
    var fullVersion  = ''+parseFloat(navigator.appVersion); 
    var majorVersion = parseInt(navigator.appVersion,10);
    var nameOffset,verOffset,ix;

    // In Opera, the true version is after "Opera" or after "Version"
    if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
     browserName = "Opera";
     fullVersion = nAgt.substring(verOffset+6);
     if ((verOffset=nAgt.indexOf("Version"))!=-1) 
       fullVersion = nAgt.substring(verOffset+8);
    }
    // In MSIE, the true version is after "MSIE" in userAgent
    else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
     browserName = "Microsoft Internet Explorer";
     fullVersion = nAgt.substring(verOffset+5);
    }
    // In Chrome, the true version is after "Chrome" 
    else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
     browserName = "Chrome";
     fullVersion = nAgt.substring(verOffset+7);
    }
    // In Safari, the true version is after "Safari" or after "Version" 
    else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
     browserName = "Safari";
     fullVersion = nAgt.substring(verOffset+7);
     if ((verOffset=nAgt.indexOf("Version"))!=-1) 
       fullVersion = nAgt.substring(verOffset+8);
    }
    // In Firefox, the true version is after "Firefox" 
    else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
     browserName = "Firefox";
     fullVersion = nAgt.substring(verOffset+8);
    }
    // In most other browsers, "name/version" is at the end of userAgent 
    else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
              (verOffset=nAgt.lastIndexOf('/')) ) 
    {
     browserName = nAgt.substring(nameOffset,verOffset);
     fullVersion = nAgt.substring(verOffset+1);
     if (browserName.toLowerCase()==browserName.toUpperCase()) {
      browserName = navigator.appName;
     }
    }
    // trim the fullVersion string at semicolon/space if present
    if ((ix=fullVersion.indexOf(";"))!=-1)
       fullVersion=fullVersion.substring(0,ix);
    if ((ix=fullVersion.indexOf(" "))!=-1)
       fullVersion=fullVersion.substring(0,ix);

    majorVersion = parseInt(''+fullVersion,10);
    if (isNaN(majorVersion)) {
     fullVersion  = ''+parseFloat(navigator.appVersion); 
     majorVersion = parseInt(navigator.appVersion,10);
    }
    
    function unshare() {
        ViewDwr.deleteViewShare(function() { window.location = 'views.shtm'; });
    }
    
    function setCookie(c_name,value)
    {
    	var exdate=new Date();
    	exdate.setDate(exdate.getDate() + 365);
    	var c_value=escape(value) + ("; expires="+exdate.toUTCString());
    	document.cookie=c_name + "=" + c_value;
    }
    
    function getCookie(c_name)
    {
    	var i,x,y,ARRcookies=document.cookie.split(";");
    	
    	for (i=0;i<ARRcookies.length;i++)
    	{
      		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
      		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
      		x=x.replace(/^\s+|\s+$/g,"");
      		
      		if (x==c_name)
        	{
        		return unescape(y);
        	}
      	}
    	
    }
    
	function toggleDisplay(){
  	  	
		document.getElementById('mainHeader').style.display = "none";
  	  	document.getElementById('subHeader').style.display = "none";
  	  	document.getElementById('graphical').style.display = "none";
  	  	jQuery('#fsOut').fadeOut(10000, function(){});
  	  	
	}
	
	function fullScreen(){
  	  	
		document.getElementById('fsOut').style.display = "block";
		document.getElementById('mainHeader').style.display = "none";
  	  	document.getElementById('subHeader').style.display = "none";
  	  	document.getElementById('graphical').style.display = "none";
		jQuery('#fsOut').fadeOut(10000, function(){});

  	  	setCookie("fullScreen","yes");
  	  	
	}
	
	function checkFullScreen(){
  	  	
		var check = getCookie("fullScreen");
		
		if(check!=null && check!=""){
			
			if(check=="yes"){
				toggleDisplay();
// 				document.getElementById('fsOut').style.display = "block";
			}
			
			if(check=="no"){
				document.getElementById('fsOut').style.display = "none";
			}
		}
  	  	
	}
		
	function keyListen(e) {
        var keycode = e.keyCode;
        
        if(keycode == '116') {
        	e.returnValue=false;
        	e.keyCode=false;
        	return false;
        };
	}

	function callkeydownhandler(evnt) {
   		keyListen(evnt);
	}

	
</script>

	<table class="borderDiv" id="graphical">
		<tr>
			<td class="smallTitle"><fmt:message key="views.title" /> <tag:help
					id="graphicalViews" /></td>
			<td width="50"></td>
			<c:if test="${fn:length(views) != 0}">
				<td>
					<tag:img png="arrow_out" title="viewEdit.fullScreen" onclick="fullScreen()" />
					<!-- <input type="button" name="buttonFull" value="Full Screen" onClick="fullScreen();" /> -->
				</td>
			</c:if>
			<td width="50"></td>
			<td align="right">
				<sst:select value="${currentView.id}" onchange="window.location='?viewId='+ this.value;">
					<c:forEach items="${views}" var="aView">
						<sst:option value="${aView.key}">${sst:escapeLessThan(aView.value)}</sst:option>
					</c:forEach>
				</sst:select>
				<c:if test="${!empty currentView}">
					<c:choose>
						<c:when test="${owner}">
							</td><td width="50"></td><td>
							<a href="view_edit.shtm?viewId=${currentView.id}"><tag:img
									png="icon_view_edit" title="viewEdit.editView" /> </a>
						</c:when>
						<c:otherwise>
							</td><td width="50"></td><td>
							<!-- Apenas Admin pode remover compartilhamento
							 <tag:img png="icon_view_delete" title="viewEdit.deleteView"
								onclick="unshare()" />-->
						</c:otherwise>
					</c:choose>
				</c:if> 
				
				<c:if test="${sessionUser.admin || userAddedViews}">
					<a href="view_edit.shtm"><tag:img png="icon_view_new" title="views.newView" /></a>
				</c:if>
			</td>
						
		</tr>
		
	</table>
	
	<table>
		<tr>
			<td class="smallTitle" id="fsOut">
				<fmt:message key="fullScreenOut"/>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
		checkFullScreen();
	</script>

	<tag:displayView view="${currentView}" emptyMessageKey="views.noViews" />
</tag:page>