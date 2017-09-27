<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>奖品分类详细信息浏览</title>
<reps:theme />
</head>
<body>
	<reps:container layout="true">
		<reps:panel id="myform" dock="none" formId="xform" validForm="false"
			action="" title="奖品分类基本信息">
			<reps:detail id="categoryInfo" borderFlag="true"
				textBackgroundFlag="false" style="width:800px">
				<reps:detailfield label="上级分类" fullRow="true"
					labelStyle="width:20%;">${not empty parentCategory ? parentCategory.name : ""}</reps:detailfield>
				<reps:detailfield label="分类名称" fullRow="true"
					labelStyle="width:20%;">${category.name}</reps:detailfield>
				<reps:detailfield label="分类类别" fullRow="true"
					labelStyle="width:20%;">
					<c:forEach items="${categoryTypeMap}" var="c">
						<c:if test="${c.key == category.type}">${c.value}</c:if>
					</c:forEach>
				</reps:detailfield>
				<reps:detailfield label="分类描述" fullRow="true"
					labelStyle="width:20%;">${category.description}</reps:detailfield>
			</reps:detail>
			<reps:formbar>
				<reps:button cssClass="btn_back" type="button" onClick="back()"
					messageCode="manage.action.return" />
			</reps:formbar>
		</reps:panel>
	</reps:container>
	<script type="text/javascript">
		function back() {
			//返回列表页
			window.location.href = "list.mvc?parentId=${category.parentId}";
		}
	</script>
</body>
</html>
