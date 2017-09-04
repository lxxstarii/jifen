<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>教师奖励系数设置</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="savejlsx.mvc" formId="xform" validForm="true" method="post" title="教师系数设置">
		<reps:formcontent style="">
            <reps:formfield label="教师类型" fullRow="true" >
            	<input type="radio" name="option" checked="checked" value="bzr">班主任
            	<input type="radio" name="option"  value="rkjs">任课教师
		   </reps:formfield>
		   <reps:formfield label="奖励系数" fullRow="true" >
           		<reps:select name="ratio" jsonData="{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10'}" headerValue="" style="width:176px;" required="true"></reps:select>
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