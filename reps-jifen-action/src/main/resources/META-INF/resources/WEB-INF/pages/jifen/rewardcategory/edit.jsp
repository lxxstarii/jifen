<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>指标分类修改</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="edit.mvc" formId="xform" validForm="true" style="width:750px">
		<reps:formcontent>
			<input type="hidden" value="${itemCate.id}" name="id">
			<%--<input type="hidden" value="${itemCate.showOrder}" name="showOrder">
			--%><reps:formfield label="指标分类名称" labelStyle="width:20%" textStyle="width:30%">
				<reps:input name="name" maxLength="30" required="true">${itemCate.name}</reps:input>
			</reps:formfield>
			<reps:formfield label="上级指标类型" labelStyle="width:20%" textStyle="width:30%">
				<reps:select dataSource="${itemCateList}" name="parentId">${itemCate.parentId}</reps:select>
			</reps:formfield>
			<reps:formfield label="显示顺序" fullRow="true">
				<reps:input name="showOrder" required="true" dataType="integernum">${itemCate.showOrder}</reps:input>
			</reps:formfield>
			<reps:formfield label="备注" fullRow="true">
				<reps:input name="remark" maxLength="200" multiLine="true" style="width:546px;height:70px">${itemCate.remark}</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:formbar>
			<reps:ajax messageCode="edit.button.save" formId="xform" callBack="skip" type="link"
				confirm="确定要提交修改？" cssClass="btn_save_a">
			</reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" action="list.mvc"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function() {
			var gid = $("#gid").val();
			parent.location.href = "index.mvc?";
		});
	};
	
</script>
</body>
</html>