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
						{ id: 1, pId: 0, name: "员工管理", perm:"user:management"},
						{ id: 2, pId: 0, name: "客户管理", perm:"customer:management"},
						{ id: 21, pId: 2, name: "增加客户", perm:"customer:add"},
						{ id: 22, pId: 2, name: "修改客户", perm:"customer:edit"},
						{ id: 23, pId: 2, name: "删除客户", perm:"customer:delete"},
						{ id: 24, pId: 2, name: "审核客户", perm:"customer:check"},
						{ id: 3, pId: 0, name: "商品管理", perm:"product:management"},
						{ id: 4, pId: 0, name: "后台充值", perm:"recharge:management"},
						{ id: 41, pId: 4, name: "充值", perm:"recharge:recharge"},
						{ id: 42, pId: 4, name: "充值审核", perm:"recharge:check"},
						{ id: 43, pId: 4, name: "查询", perm:"recharge:search"},
						{ id: 431, pId: 43, name: "商品兑换码查询", perm:"search:redeemCode"},
						{ id: 432, pId: 43, name: "客户信息查询", perm:"search:customerInfo"},
						{ id: 433, pId: 43, name: "积分兑换码查询", perm:"search:rechargeCode"},
						{ id: 44, pId: 4, name: "积分码申请", perm:"rechargeCode:apply"},
						{ id: 45, pId: 4, name: "积分码审核", perm:"rechargeCode:check"},
						{ id: 5, pId: 0, name: "系统管理", perm:"system:management"}
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