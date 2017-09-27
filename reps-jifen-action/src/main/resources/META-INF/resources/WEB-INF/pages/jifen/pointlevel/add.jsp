<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>积分等级新增</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="add.mvc" formId="form" validForm="true" >
		<reps:formcontent>
			<reps:formfield label="用户等级" labelStyle="width:21%" textStyle="width:30%"  fullRow="true">
				<reps:input name="level" dataType="integernum" required="true"></reps:input>
			</reps:formfield>
			<reps:formfield label="对应积分"  fullRow="true">
				<reps:input name="point" dataType="integernum" required="true"></reps:input>
			</reps:formfield>
		</reps:formcontent>
		<br/>
		<reps:formbar >
			<reps:ajax  messageCode="add.button.save" formId="form" callBack="skip" type="button" cssClass="btn_save"></reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function(){ back(); });
	};
	
	function back() {
		window.location.href= "list.mvc";
	}

</script>
</html>