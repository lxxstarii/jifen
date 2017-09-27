<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>人员信息查询</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel id="top" dock="top" method="post" action="teachers.mvc" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:85%;">
			<input name="dialogId" type="hidden" value="${dialogId}" />
			<input name="callBack" type="hidden" value="${callBack}" />
			<input name="showName" type="hidden" value="${showName}" />
			<input name="hideName" type="hidden" value="${hideName}" />
			<input name="hideNameValue" type="hidden" value="${hideNameValue}" />
			<reps:formfield label="姓名">
				<reps:input name="account.person.name" style="width:100px;">${user.account.person.name}</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons style="margin-right:20px;">
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="mygrid" cssClass="small-search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
	</reps:panel>
	<reps:panel id="mybody" dock="center" border="true">
		<reps:grid id="mygrid" items="${list}" form="queryForm" var="user" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="姓名" width="28" align="center">${user.account.person.name}</reps:gridfield>
				<reps:gridfield title="性别" width="12" align="center"><sys:dictionary src="sex">${user.account.person.sex}</sys:dictionary></reps:gridfield>
				<reps:gridfield title="民族" width="15" align="center"><sys:dictionary src="ethnicity">${user.account.person.ethnicity}</sys:dictionary></reps:gridfield>
				<reps:gridfield title="证件号码" width="35" align="center">${user.account.person.icNumber}</reps:gridfield>
				<reps:gridfield title="选择" width="10" align="center">
					<a class="btnSelect" href="javascript:choosePerson('${user.account.person.id}','${user.account.person.name}','${user.organizeId}','${callBack}');" title="查找带回">选择</a>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">

var choosePerson = function(id, name,organizeId, callBack){
	//判断父窗口元素是否存在
	var obj = $("input[name='"+$("input[name=showName]").val()+"']", window.parent.document);
	if (obj){
		obj.val(name);
	}
	obj = $("input[name='"+$("input[name=hideName]").val()+"']", window.parent.document);
	if (obj){
		obj.val(id);
	}

	var pdialog = $("#dialog"+$("input[name=dialogId]").val()+"", window.parent.document);
	$(pdialog).hide();
	$("div.shadow", window.parent.document).hide();
	$("#dialogBackground", window.parent.document).hide();
	
	//执行回调方法
	if (callBack){
		eval("window.parent." + callBack + "('" + id + "','" + name + "')");
	}
}
</script>
</body>
</html>
