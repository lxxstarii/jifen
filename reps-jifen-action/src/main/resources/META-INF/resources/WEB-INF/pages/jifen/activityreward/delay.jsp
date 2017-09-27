<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>活动延期</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="delay.mvc" formId="xform" validForm="true" method="post" title="活动截止时间设置">
		<reps:formcontent style="">
          	<reps:formfield label="截止时间" labelStyle="width:21%" textStyle="width:30%"  fullRow="true">
          		<input name="id" type="hidden" value="${activity.id }"/>
				<reps:datepicker id="finishTime" name="finishTimeDisp" format="yyyy-MM-dd" required="true" min="${minDate }">${activity.finishTime }</reps:datepicker>
			</reps:formfield>
	   </reps:formcontent>
	    <reps:formbar style="margin-top:15px;">
  		   <reps:ajax confirm="你确定要延期吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
  
	var my = function(data){
		messager.message(data, function() {
			window.parent.location.href = "list.mvc";
		});
	}
  </script>
  </body>
</html>