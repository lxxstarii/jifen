<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>分类管理</title>
	<reps:theme />
</head>
<body onload="load()">
	<reps:container layout="true">
		<reps:panel id="myleft" dock="left"  border="true" style="width:200px;overflow-y:scroll;overflow-x:auto;">
			<reps:toolbar>
              <div style="height:20px;text-align: center;padding:2px;width:100%"><a href="list.mvc?parentId=-1" target="rightFrame">根目录</a></div>
           	 </reps:toolbar>
			<reps:tree id="mytree" items="${treelist}" var="li" cssClass="treeFolder" checkbox="false" expand="true">
				<reps:treenode parentKey="${li.parentId}" key="${li.id}">
					<a href="javascript:void(0);"
						onclick="showChilds(this, '${li.id}')" id="${li.id}" title="${li.name}" >${li.name}</a>
				</reps:treenode>
			</reps:tree>
		</reps:panel>
		<reps:panel id="myCenter" dock="center" border="false">
			<reps:iframe id="rightFrame" fit="true"></reps:iframe>
		</reps:panel>
	</reps:container>
</body>
<script>
	function load() {
	    var $iframe = $("#rightFrame");
	    $iframe.attr("src","list.mvc?parentId=-1");
	}

	function showChilds(obj, categoryId){
		//显示右侧下级列表
		$("#rightFrame").attr("src", "list.mvc?parentId=" + categoryId + "&run=" + Math.random());
	}
	
</script>
</html>

