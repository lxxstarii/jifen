<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>积分等级设置</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<reps:footbar>
			<reps:button cssClass="add-a" action="toadd.mvc" messageCode="manage.action.add" value="新增"></reps:button>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="pointLevelList" items="${list}" form="queryForm" var="pointLevel" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="用户等级" width="15" align="center">${pointLevel.level}</reps:gridfield>
				<reps:gridfield title="对应积分" width="50" align="center">${pointLevel.point}个积分</reps:gridfield>
				<reps:gridfield title="操作" width="30">
					<reps:button cssClass="detail-table" action="show.mvc?id=${pointLevel.id }" value="详细"></reps:button>
					<reps:button cssClass="modify-table" messageCode="manage.action.update" action="toedit.mvc?id=${pointLevel.id}"></reps:button>
					<reps:ajax cssClass="delete-table" messageCode="manage.action.delete" confirm="您确定要删除所选行吗？"
						redirect="list.mvc" url="delete.mvc?id=${pointLevel.id}">
					</reps:ajax>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var my = function(data){
		window.location.href= "list.mvc";
	};
	
</script>
</body>
</html>
