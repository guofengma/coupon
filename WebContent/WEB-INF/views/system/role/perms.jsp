<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	permsTree=$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});


var setting = {
		  view: {
// 		    addHoverDom: addHoverDom,
// 		    removeHoverDom: removeHoverDom,
		    selectedMulti: true
		  },
		  check: {
		    enable: true
		  },
		  data: {
		    simpleData: {
		      enable: true
		    }
		  },
		  edit: {
		    enable: false
		  }
		};
		
		var zNodes = [
						{ id: 1, pId: 0, name: "用户管理", perm:"user:management"},
						{ id: 2, pId: 0, name: "客户管理", perm:"customer:management"},
						{ id: 3, pId: 0, name: "兑换码管理", perm:"redeem:management"},
						{ id: 4, pId: 0, name: "商品管理", perm:"product:management"},
						{ id: 5, pId: 0, name: "后台充值", perm:"recharge:management"}
		              ];
		 
		
	function addHoverDom(treeId, treeNode) {
		  var sObj = $("#" + treeNode.tId + "_span");
		  if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
		  var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		    + "' title='add node' onfocus='this.blur();'></span>";
		  sObj.after(addStr);
		  var btn = $("#addBtn_" + treeNode.tId);
		  if (btn) btn.bind("click", function () {
		    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		    alert(treeId);
		    alert(treeNode);
		    zTree.addNodes(treeNode, { id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++) });
		    return false;
		  });
	};
	function removeHoverDom(treeId, treeNode) {
		  $("#addBtn_" + treeNode.tId).unbind().remove();
	};

	
	function submitNodes(){
		var nodes = permsTree.getCheckedNodes(true);
		var str = "";
		var perms = "";
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].name!=null){
			    str += nodes[i].id+ ",";
			    perms += nodes[i].name+ ",";
			}
		}
		if(perms.length>0) {
			perms = perms.substring(0,perms.length-1);
		}
		alert("d"+perms);
		$("#perms").val(perms);
	}

</script>
<div id="menuContent" class="col-sm-5 menuContent" style="border:1px solid rgb(170,170,170);z-index:10;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:100%; height:auto;"></ul>
</div>