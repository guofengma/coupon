/**
 * 
 */
$(function() {
	var expandingMenus = getCookie("menus");
	//alert(expandingMenus);
	var expandingMenusArray = expandingMenus.split(',')
	for (var i = 0; i < expandingMenusArray.length; i++) {
		expendMenu($("#" + expandingMenusArray[i]));
	}
	//绑定下拉框展开的事件
	$('#navDiv .nav-list').on('shown.bs.collapse', function() {
		setCookie("menus", getExpandingMenus(), 15);
	});
	//绑定下拉框收起的事件
	$('#navDiv .nav-list').on('hidden.bs.collapse', function() {
		setCookie("menus", getExpandingMenus(), 15);
	});
	
//	window.onresize = function(){
//		location.reload();
//	}

});

/**
 * 展开菜单
 */
function expendMenu($ul) {
	if ($ul.length) {
		$ul.addClass("in").css("height", "auto");
	}
}

// 获得打开的菜单名称集合
function getExpandingMenus() {
	var $expandingMenus = $("ul .in");
	var menus = "";
	for (var i = 0; i < $expandingMenus.length; i++) {
		menus += $expandingMenus[i].id;
		if (i != $expandingMenus.length - 1) {
			menus += ",";
		}
	}
	//alert(menus);
	return menus;
}
// 设置cookie
function setCookie(name, value, exMinutes) {
	// 设置名称为name,值为value的Cookie
	var expdate = new Date(); // 初始化时间
	expdate.setTime(expdate.getTime() + (exMinutes * 60 * 1000)); // 时间
	document.cookie = name + "=" + value + ";expires=" + expdate.toGMTString()
			+ ";path=/";
	// 即document.cookie= name+"="+value+";path=/";
	// 时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
}
// 获取cookie
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}

// 清除cookie
function clearCookie(name) {
	setCookie(name, "", -1);
}