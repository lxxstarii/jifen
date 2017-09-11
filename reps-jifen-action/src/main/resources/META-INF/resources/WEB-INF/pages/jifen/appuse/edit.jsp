<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
     <title>积分规则设置</title>
	 <reps:theme/>	 
  </head>
  <body>
    <reps:container layout="true" >
       <reps:panel id="myform" dock="center" action="edit.mvc" formId="xform" validForm="false" method="post" style="text-align:-webkit-center;">
		   <input type="hidden" name="id" value="${info.id}">
	       <reps:formcontent>
	           <reps:formfield label="应用名称" fullRow="true">
					<reps:input name="applicationName" maxLength="15" required="true">${info.applicationName}</reps:input>
	           </reps:formfield>
			   <reps:formfield label="积分项" fullRow="true">
				   <reps:input name="item" maxLength="20" required="true">${info.item}</reps:input>
			   </reps:formfield>
			   <reps:formfield label="标识码" fullRow="true">
				   <reps:input name="code" maxLength="20" required="true" dataType="alphanumeric">${info.code}</reps:input>
			   </reps:formfield>
			   <reps:formfield label="分值" fullRow="true">
				   <reps:input name="points" maxLength="20" required="true" dataType="integernum">${info.points}</reps:input>
			   </reps:formfield>
			   <reps:formfield label="奖励/扣除" fullRow="true">
				   <reps:select name="mark" jsonData="{'0':'扣除','1':'奖励'}" required="true">${info.mark}</reps:select>
			   </reps:formfield>
			   <reps:formfield label="是否需要审核" fullRow="true">
				   <reps:select name="needCheck" jsonData="{'0':'不需要审核','1':'需要审核'}" required="true">${info.needCheck}</reps:select>
			   </reps:formfield>
			   <reps:formfield label="是否启用" fullRow="true">
				   <reps:select name="isEnabled" jsonData="{0:'否',1:'是'}" style="width:180px;" required="true">${info.isEnabled}</reps:select>
			   </reps:formfield>
	        </reps:formcontent>
		   <reps:formbar>
			   <reps:ajax messageCode="edit.button.save" formId="xform" callBack="my" type="link"
						  confirm="确定要提交修改？" cssClass="btn_save_a">
			   </reps:ajax>
			   <reps:button cssClass="btn_cancel" type="button" onClick="back()" messageCode="add.button.cancel" />
		   </reps:formbar>
  	</reps:panel>
  </reps:container>
 <script type="text/javascript">
	 function back() {
		 window.location.href= "list.mvc";
	 }
	var my=function(data) {
		messager.message(data, function(){back();});
	};

</script>
</body>
</html>