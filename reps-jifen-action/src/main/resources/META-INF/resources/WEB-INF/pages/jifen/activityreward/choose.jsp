<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
   <reps:theme/>
  </head>
  <body>
  <reps:container>
       <reps:panel id="myLeft" >
			<reps:tree id="mytree" items="${treelist}" var="category" cssClass="treeFolder" checkbox="false" expand="true">
			    <reps:treenode parentKey="${category.parentId}" key="${category.id}">
			       <a href="javascript:void(0);"><span onclick="changeVal('${category.id}','${category.name}')">${category.name}</span></a>
			    </reps:treenode>
			</reps:tree>
	   </reps:panel>
 </reps:container>
 <script type="text/javascript">
	function changeVal(code, name) {
		$("#categoryId", window.parent.document).val(code);
		$("#parentCodeName", window.parent.document).val(name);
		var pdialog = $("#dialogchooseParentCode", window.parent.document);
		$(pdialog).hide();
		$("div.shadow", window.parent.document).hide();
		$("#dialogBackground", window.parent.document).hide();
	}
</script>
</body>
</html>
