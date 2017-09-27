<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>活动统计管理列表</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="statistics.mvc" formId="queryForm">
		<input type="hidden" name="ids" />
		<reps:formcontent parentLayout="true" style="width:80%;">
			<input type="hidden" name="rewardId" value="${info.rewardId }"/>
		    <reps:formfield label="学校名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="school.organize.name" maxLength="30">${info.school.organize.name }</reps:input>
			</reps:formfield>
			 <reps:formfield label="活动参与者" labelStyle="width:23%;" textStyle="width:30%;">
				<reps:input name="student.person.name" maxLength="30">${info.student.person.name }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="infoList" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:ajax cssClass="batch-approval-a" confirm="确认批量审核通过吗?" beforeCall="checkChecked" formId="queryForm" callBack="my" value="批量审核" />
			<reps:button cssClass="return-a" onClick="back()" value="返回" />
			<span style="margin-left:20px;font-size:16px;font-weight:bold;color:red;">活动名称:${activity.name }</span>
			<span style="margin-left:20px;font-size:16px;font-weight:bold;color:red;">总参与人数:${participatedCount }</span>
			<span style="margin-left:20px;font-size:16px;font-weight:bold;color:red;">取消参与人数:${cancelCount }</span>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="infoList" items="${list}" form="queryForm" var="info" pagination="${pager}" flagSeq="false">
			<reps:gridrow>
				<reps:gridcheckboxfield checkboxName="id" align="center" title="" width="5">${info.id}</reps:gridcheckboxfield>
				<reps:gridfield title="活动参与者" width="15" align="center">${info.student.person.name}</reps:gridfield>
				<reps:gridfield title="学校" width="25" align="center">${info.school.organize.name }</reps:gridfield>
				<reps:gridfield title="年级" width="30" align="center">
					<sys:dictionary src="grade">${info.grade}</sys:dictionary>
				</reps:gridfield>
				<reps:gridfield title="班级" width="25" align="center">${info.classes.name}</reps:gridfield>
				<reps:gridfield title="操作" width="25" align="center">
					<reps:dialog cssClass="audit-table" id="audit" iframe="true" width="350"
						 height="250" url="audit.mvc?id=${info.id}" value="审核"></reps:dialog>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
	
</reps:container>
<script type="text/javascript">
	var my = function(data){
		window.location.href= "statistics.mvc";
	};
	
	function back() {
		//返回列表页
		window.location.href = "list.mvc";
	}
		
	var checkChecked = function() {
		if ($("input[type=checkbox][name=id]:checked").length == 0) {
			messager.info("请选择要批量审核通过的注册信息");
			return false;
		}
		var ids = $("input[type=hidden][name=ids]");
		ids.val("");
		$.each($("input[type=checkbox][name=id]:checked"), function(i, obj) {
			if (ids.val() == "") {
				ids.val($(obj).val());
			} else {
				ids.val(ids.val() + "," + $(obj).val());
			}
		});
		$("#queryForm").attr("action", "batchaudit.mvc");
		return true;
	};
	
	
</script>
</body>
</html>
