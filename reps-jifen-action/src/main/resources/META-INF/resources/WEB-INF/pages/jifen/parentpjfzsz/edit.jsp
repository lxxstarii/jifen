<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>>家庭行为评分修改</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="edit.mvc" formId="xform" validForm="true" style="width:800px">
		<reps:formcontent>
			<reps:formfield label="评分项"  fullRow="true">
				<reps:input name="item" maxLength="30" required="true">${parentPjfzsz.item }</reps:input>
			</reps:formfield>
			<reps:formfield label="具体行为内容" fullRow="true">
				<reps:input name="content" maxLength="200" multiLine="true" style="width:516px;height:70px" required="true">${parentPjfzsz.content }</reps:input>
			</reps:formfield>
			<reps:formfield label="奖励积分" fullRow="true">
				<reps:select name="pointsScope" jsonData="{'1':'0~1','2':'0~2','3':'0~3','4':'0~4','5':'0~5'}" required="true">${parentPjfzsz.pointsScope }</reps:select>
			</reps:formfield>
			<reps:formfield label="适用年级" fullRow="true">
			<sys:dictionary id="grade" src="grade" name="applyGrade"
					headerValue="" headerText="" filter="2,3,4" validRecord="true" required="true">${parentPjfzsz.applyGrade}</sys:dictionary>
			</reps:formfield>
			<reps:formfield label="是否可用" fullRow="true">
				<reps:select name="isEnabled" jsonData="{'1':'是','0':'否'}" required="true">${parentPjfzsz.isEnabled }</reps:select>
			</reps:formfield>
			<input type="hidden" value="${parentPjfzsz.id}" name="id">
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
		messager.message(data, function() {
			messager.message(data, function(){ back(); });
		});
	};
	
	function back() {
		window.location.href= "list.mvc";
	}
	
</script>
</body>
</html>