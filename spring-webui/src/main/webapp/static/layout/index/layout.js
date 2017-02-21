var tab = null;
var accordion = null;
var tabItems = [];

$(function() {
	
	left_menuItem_init(left_menu);
	
	$("#layout").ligerLayout({
		leftWidth : 190,
		height : '100%',
		heightDiff : -34,
		space : 4,
		onHeightChanged : f_heightChanged
	});

	var height = $(".l-layout-center").height();

	$("#framecenter").ligerTab({
		height : height,
		showSwitchInTab : true,
		showSwitch : true,
		onAfterAddTabItem : function(tabdata) {
			tabItems.push(tabdata);
		},
		onAfterRemoveTabItem : function(tabid) {
			for (var i = 0; i < tabItems.length; i++) {
				var o = tabItems[i];
				if (o.tabid == tabid) {
					tabItems.splice(i, 1);
					break;
				}
			}
		}
	});

	$("#accordion").ligerAccordion({
		height : height - 24,
		speed : null
	});

	$(".l-link").hover(function() {
		$(this).addClass("l-link-over");
	}, function() {
		$(this).removeClass("l-link-over");
	});
	
	left_menuData_init(left_menu);
	
	tab = liger.get("framecenter");
	accordion = liger.get("accordion");
	$("#pageloading").hide();
});

function left_menuItem_init(data){
	var str='';
	for(var i=0;i<data.length;i++){
		var tmp=data[i];
		str+='<div title="'+tmp.name+'" class="l-scroll"><ul id="'+tmp.id+'" style="margin-top:3px;"></div>';
	}
	$("#accordion").html(str);
};

function left_menuData_init(data){
	for(var i=0;i<data.length;i++){
		var tmp=data[i];
		$("#"+tmp.id).ligerTree({
			data : tmp.data,
			checkbox : false,
			slide : false,
			nodeWidth : 120,
			attribute : [ 'nodename', 'url' ],
			onSelect : function(node) {
				if (!node.data.url)
					return;
				var tabid = $(node.target).attr("tabid");
				if (!tabid) {
					tabid = new Date().getTime();
					$(node.target).attr("tabid", tabid);
				}
				f_addTab(tabid, node.data.text, node.data.url);
			}
		});
	}
};

function f_heightChanged(options) {
	if (tab) {
		tab.addHeight(options.diff);
	}
	if (accordion && options.middleHeight - 24 > 0) {
		accordion.setHeight(options.middleHeight - 24);
	}
};

function f_addTab(tabid, text, url) {
	tab.addTabItem({
		tabid : tabid,
		text : text,
		url : url
	});
};