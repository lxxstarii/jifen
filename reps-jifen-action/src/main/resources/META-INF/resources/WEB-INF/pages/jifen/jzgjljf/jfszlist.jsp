<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>奖励积分列表</title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/jifen/jljfsz/jfszlist.mvc" method="post" formId="queryForm">
		<input type="hidden" name="ids">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="姓名" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="teacher.person.name">${query.teacher.person.name}</reps:input>
			</reps:formfield>
			<reps:formfield label="证件号码" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="teacher.person.icNumber">${query.teacher.person.icNumber}</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm"  gridId="mygrid" callBack="setDisabled" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:dialog cssClass="add-a" id="add" iframe="true" width="800"
				 height="600" url="jljfsz.mvc" value="设置"></reps:dialog>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridfield title="" width="2" align="center">
				</reps:gridfield>
				<reps:gridfield title="姓名" width="15" align="center">
					${data.teacher.person.name}
				</reps:gridfield>
				<reps:gridfield title="单位或学校" width="30" align="center">
					${data.organize.name}
				</reps:gridfield>
				<reps:gridfield title="可分配积分" width="15" align="center">
					${data.pointsLeft}
				</reps:gridfield>
				<reps:gridfield title="总积分" width="15" align="center">
					${data.totalPointsAuthorized}
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	
 	
</script>
</body>
</html>