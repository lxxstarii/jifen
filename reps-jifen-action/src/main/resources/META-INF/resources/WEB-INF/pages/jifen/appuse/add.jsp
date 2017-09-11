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
       <reps:panel id="myform" dock="center" action="add.mvc" formId="xform" validForm="false" method="post" style="text-align:-webkit-center;">
	       <reps:formcontent>
	           <reps:formfield label="应用名称" fullRow="true">
					<reps:input name="applicationName" maxLength="15" required="true"></reps:input>
	           </reps:formfield>
			   <reps:formfield label="积分项" fullRow="true">
				   <reps:input name="item" maxLength="20" required="true"></reps:input>
			   </reps:formfield>
			   <reps:formfield label="标识码" fullRow="true">
				   <reps:input name="code" maxLength="20" required="true" dataType="alphanumeric"></reps:input>
			   </reps:formfield>
			   <reps:formfield label="分值" fullRow="true">
				   <reps:input name="points" maxLength="20" required="true" dataType="integernum"></reps:input>
			   </reps:formfield>
			   <reps:formfield label="奖励/扣除" fullRow="true">
				   <reps:select name="mark" jsonData="{'1':'奖励','0':'扣除'}" headerValue="" required="true"></reps:select>
			   </reps:formfield>
			   <reps:formfield label="是否需要审核" fullRow="true">
				   <reps:select name="needCheck" jsonData="{'0':'不需要审核','1':'需要审核'}" required="true"></reps:select>
			   </reps:formfield>
			   <reps:formfield label="是否启用" fullRow="true">
				   <reps:select name="isEnabled" jsonData="{1:'是',0:'否'}" headerValue="" style="width:180px;" required="true"></reps:select>
			   </reps:formfield>
	        </reps:formcontent>
		   <reps:formbar>
			   <reps:ajax cssClass="btn_save" type="button" callBack="my" formId="xform"  messageCode="add.button.save" />
			   <reps:button cssClass="btn_cancel" type="button" onClick="back()" messageCode="add.button.cancel" />
		   </reps:formbar>
  	</reps:panel>
  </reps:container>
 <script type="text/javascript">
	 function back() {
		 window.location.href= "list.mvc";
	 }
	function submitSave(){
		$.ajax({
			url: "add.mvc",
			type: "POST",
			dataType: "json",
			data: $("#xform").serializeArray(),
			async: false,
			success: function(data){
				messager.message(data,function(){ back(); });
			}
		});
	}

	var my=function(data) {
		messager.message(data, function(){back();});
	};

</script>
</body>
</html>