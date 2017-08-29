<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>指标分类添加</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="add.mvc" formId="form" validForm="true" style="width:750px">
		<reps:formcontent>
			<reps:formfield label="指标分类名称" labelStyle="width:20%" textStyle="width:30%">
				<reps:input name="name" maxLength="30" required="true"></reps:input>
			</reps:formfield>
			<reps:formfield label="指标类别" labelStyle="width:20%" textStyle="width:30%">
				<reps:select dataSource="${itemCateList}" name="parentId"></reps:select>
			</reps:formfield>
			<reps:formfield label="显示顺序" fullRow="true">
				<reps:input name="showOrder" required="true" dataType="integernum">${showOrder}</reps:input>
			</reps:formfield>
			<reps:formfield label="备注" fullRow="true">
				<reps:input name="remark" maxLength="200" multiLine="true" style="width:546px;height:70px"></reps:input>
			</reps:formfield>
		</reps:formcontent>
		<br/>
		<reps:formbar>
			<reps:ajax  messageCode="add.button.save" formId="form" callBack="skip" type="button" cssClass="btn_save"></reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" action="list.mvc"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function() {
			parent.location.href = "index.mvc";
		});
	};
</script>
</html>