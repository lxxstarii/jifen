<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>积分等级设置详细信息</title>
<reps:theme />
</head>
<body>
	<reps:container layout="true">
		<reps:panel id="myform" dock="none" formId="xform" validForm="false"
			action="" title="积分等级设置详细信息">
			<reps:detail id="pointLevelInfo" borderFlag="true"
				textBackgroundFlag="false" style="width:800px">
				<reps:detailfield label="用户等级" fullRow="true"
					labelStyle="width:20%;">${pointLevel.level}</reps:detailfield>
				<reps:detailfield label="对应积分" fullRow="true"
					labelStyle="width:20%;">${pointLevel.point }</reps:detailfield>
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
			window.location.href = "list.mvc";
		}
	</script>
</body>
</html>