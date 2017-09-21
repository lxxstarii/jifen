<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>活动详细信息浏览</title>
<reps:theme />
</head>
<body>
	<reps:container layout="true">
		<reps:panel id="myform" dock="none" formId="xform" validForm="false"
			action="" title="活动详细信息">
			<reps:detail id="activityInfo" borderFlag="true"
				textBackgroundFlag="false" style="width:800px">
				<reps:detailfield label="活动名称" fullRow="true"
					labelStyle="width:20%;">${activity.name}</reps:detailfield>
				<reps:detailfield label="活动分类" fullRow="true"
					labelStyle="width:20%;">${activity.jfRewardCategory.name }</reps:detailfield>
				<reps:detailfield label="兑换截至时间" fullRow="true"
					labelStyle="width:20%;"><fmt:formatDate value="${activity.finishTime }" pattern="yyyy-MM-dd"/></reps:detailfield>
				<reps:detailfield label="所需积分" fullRow="true"
					labelStyle="width:20%;">${activity.points }</reps:detailfield>
				<reps:detailfield label="上线时间" fullRow="true"
					labelStyle="width:20%;"><fmt:formatDate value="${activity.showTime }" pattern="yyyy-MM-dd"/></reps:detailfield>
				<reps:detailfield label="活动状态" fullRow="true"
					labelStyle="width:20%;"><c:if test="${activity.isShown == '1'}">进行中</c:if><c:if test="${activity.isShown == '0' }">未发布</c:if><c:if test="${activity.isShown == '2' }">已结束</c:if></reps:detailfield>
				<reps:detailfield label="活动详情" fullRow="true"
					labelStyle="width:20%;">${activity.description }</reps:detailfield>
				<reps:detailfield label="图片" fullRow="true"
					labelStyle="width:20%;"><img name="img" width="128px",height="128px" src="${imagePath}${activity.picture}"/></reps:detailfield>
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
