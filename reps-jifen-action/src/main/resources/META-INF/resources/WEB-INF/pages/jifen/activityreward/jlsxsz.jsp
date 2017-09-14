<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>活动名称</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="savejlsx.mvc" formId="xform" validForm="true" method="post" title="教师系数设置">
		<reps:formcontent style="">
          	<reps:formfield label="截止时间" labelStyle="width:21%" textStyle="width:30%"  fullRow="true">
				<reps:datepicker id="finishTime" name="finishTimeDisp" format="yyyy-MM-dd" required="true" min="${minDate }"></reps:datepicker>
			</reps:formfield>
	   </reps:formcontent>
	    <reps:formbar style="margin-top:15px;">
  		   <reps:ajax confirm="你确定要提交吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
  
	var my = function(data){
		messager.message(data, function() {
			window.parent.location.href = "xsszlist.mvc";
		});
	}
  </script>
  </body>
</html>