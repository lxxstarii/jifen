<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>家庭行为评分详细信息浏览</title>
<reps:theme />
</head>
<body>
	<reps:container layout="true">
		<reps:panel id="myform" dock="none" formId="xform" validForm="false"
			action="" title="家庭行为评分详细信息">
			<reps:detail id="parentPjfzszInfo" borderFlag="true"
				textBackgroundFlag="false" style="width:800px">
				<reps:detailfield label="评分项" fullRow="true"
					labelStyle="width:20%;">${parentPjfzsz.item}</reps:detailfield>
				<reps:detailfield label="具体行为内容" fullRow="true"
					labelStyle="width:20%;">${parentPjfzsz.content }</reps:detailfield>
				<reps:detailfield label="奖励积分" fullRow="true"
					labelStyle="width:20%;">
					<c:choose>
						<c:when test="${parentPjfzsz.pointsScope eq 1}">0~1</c:when>
						<c:when test="${parentPjfzsz.pointsScope eq 2}">0~2</c:when>
						<c:when test="${parentPjfzsz.pointsScope eq 3}">0~3</c:when>
						<c:when test="${parentPjfzsz.pointsScope eq 4}">0~4</c:when>
						<c:when test="${parentPjfzsz.pointsScope eq 5}">0~5</c:when>
					</c:choose>	
				</reps:detailfield>
				<reps:detailfield label="适用年级" fullRow="true"
					labelStyle="width:20%;">
					<sys:dictionary src="grade">${parentPjfzsz.applyGrade}</sys:dictionary>
				</reps:detailfield>
				<reps:detailfield label="是否可用" fullRow="true"
					labelStyle="width:20%;">${parentPjfzsz.isEnabled == 1 ? '是' : '否'}</reps:detailfield>
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