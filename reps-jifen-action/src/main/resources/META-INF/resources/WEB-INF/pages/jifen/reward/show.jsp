<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>物品详细信息浏览</title>
<reps:theme />
</head>
<body>
	<reps:container layout="true">
		<reps:panel id="myform" dock="none" formId="xform" validForm="false"
			action="" title="物品详细信息">
			<reps:detail id="rewardInfo" borderFlag="true"
				textBackgroundFlag="false" style="width:800px">
				<reps:detailfield label="物品名称" fullRow="true"
					labelStyle="width:20%;">${reward.name}</reps:detailfield>
				<reps:detailfield label="所需积分" fullRow="true"
					labelStyle="width:20%;">${reward.points }</reps:detailfield>
				<reps:detailfield label="物品分类" fullRow="true"
					labelStyle="width:20%;">${reward.jfRewardCategory.name }</reps:detailfield>
				<reps:detailfield label="库存数量" fullRow="true"
					labelStyle="width:20%;">${reward.numbers }</reps:detailfield>
				<reps:detailfield label="物品状态" fullRow="true"
					labelStyle="width:20%;"><c:if test="${reward.isShown == '1'}">已发布</c:if><c:if test="${reward.isShown == '0' }">未发布</c:if><c:if test="${reward.isShown == '2' }">已下架</c:if></reps:detailfield>
				<reps:detailfield label="物品图片1" fullRow="true"
					labelStyle="width:20%;">${reward.rewardUrlOne }</reps:detailfield>
				<reps:detailfield label="物品图片2" fullRow="true"
					labelStyle="width:20%;">${reward.rewardUrlTwo }</reps:detailfield>
				<reps:detailfield label="物品图片3" fullRow="true"
					labelStyle="width:20%;">${reward.rewardUrlThree }</reps:detailfield>
				<reps:detailfield label="物品图片4" fullRow="true"
					labelStyle="width:20%;">${reward.rewardUrlFour }</reps:detailfield>
				<reps:detailfield label="物品图片5" fullRow="true"
					labelStyle="width:20%;">${reward.rewardUrlFive }</reps:detailfield>
				<reps:detailfield label="物品详情" fullRow="true"
					labelStyle="width:20%;">${reward.description }</reps:detailfield>
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
