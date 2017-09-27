<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>>积分等级设置修改</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="edit.mvc" formId="xform" validForm="true" >
		<reps:formcontent>
			<reps:formfield label="用户等级" labelStyle="width:21%" textStyle="width:30%"  fullRow="true">
			<input type="hidden" name="id" value="${pointLevel.id }"></input>
				<reps:input name="level" dataType="integernum" required="true">${pointLevel.level }</reps:input>
			</reps:formfield>
			<reps:formfield label="对应积分"  fullRow="true">
				<reps:input name="point" dataType="integernum" required="true">${pointLevel.point }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:formbar>
			<reps:ajax messageCode="edit.button.save" formId="xform" callBack="skip" type="link"
				confirm="确定要提交修改？" cssClass="btn_save_a">
			</reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function(){ back(); });
	};
	
	function back() {
		window.location.href= "list.mvc";
	}
	
</script>
</body>
</html>