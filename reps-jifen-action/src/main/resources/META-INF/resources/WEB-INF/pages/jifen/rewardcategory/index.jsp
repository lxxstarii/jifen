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
		<reps:panel id="myleft" dock="left" title="分类管理" border="true" style="width:200px;overflow-y:scroll;overflow-x:auto;">
			<reps:tree id="mytree" items="${treelist}" var="li" cssClass="treeFolder" checkbox="false" expand="true">
				<reps:treenode parentKey="${li.parentId}" key="${li.id}">
					<a href="javascript:void(0);"
						onclick="showChilds(this, '${li.id}', '2')" id="${li.id}">${li.name}</a>
				</reps:treenode>
			</reps:tree>
		</reps:panel>
		<reps:panel id="myCenter" dock="center" border="false">
			<reps:iframe id="iframe" fit="true"></reps:iframe>
		</reps:panel>
	</reps:container>
</body>
<script>
	function load() {
	    var $iframe = $("#iframe");
	    $iframe.attr("src","list.mvc");
	}

	function showChilds(obj, id, isItem) {
		if($(obj).attr("level") == "0" && isItem == "1"){//是指标
			document.getElementById("iframe").src = "${ctx}/reps/report/item/show.mvc?id="+id;
		}else{
			document.getElementById("iframe").src = "list.mvc?parentId="+id;
		}
	}
	
</script>
</html>

