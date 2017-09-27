<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>修改订单信息</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container >
  	<reps:panel id="myform" dock="none" border="true" action="edit.mvc" formId="xform" validForm="true" method="post" title="修改评价指标" style="width:900px;">
		<input type="hidden" name="id" value="${data.id}">
		<reps:formcontent style="margin-top:30px">
           <reps:formfield label="物品名称 " labelStyle="width:20%;" textStyle="width:20%;">
           		${data.reward.name}
           </reps:formfield>
            <reps:formfield label="订单号" labelStyle="width:20%;" textStyle="width:20%;">
            	${data.orderNo}
		   </reps:formfield>
		   <reps:formfield label="收货人" labelStyle="width:20%;" textStyle="width:20%;">
		   		<reps:input name="consigneeName"  maxLength="20" required="true">${data.consigneeName}</reps:input>
		   </reps:formfield>
		    <reps:formfield label="收货地址" labelStyle="width:20%;" textStyle="width:20%;">
           		<reps:input name="address"  maxLength="100" required="true" >${data.address}</reps:input>
		   </reps:formfield>
		   <reps:formfield label="联系电话"  labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="phone"  maxLength="15" required="true" >${data.phone}</reps:input>
           </reps:formfield>
           <reps:formfield label="快递公司"  labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="expressCompany"  maxLength="15" >${data.expressCompany}</reps:input>
           </reps:formfield>
           <reps:formfield label="运单号"  labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="shipmentNo"  maxLength="15" >${data.shipmentNo}</reps:input>
           </reps:formfield>
	   </reps:formcontent>
	    <reps:formbar style="margin-top:50px">
  		   <reps:ajax confirm="你确定要提交吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
           <reps:button cssClass="btn_cancel" type="button" onClick="back()" messageCode="add.button.cancel" />
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
	var back = function() {
		window.location.href = "list.mvc";
	}
	
	$(function(){
  		 $("#div").hide();
  		 $("#showImage").hide();
  	});
	var my = function(data){
		messager.message(data, function() {
			window.location.href = "list.mvc";
		});
	}
  </script>
  </body>
</html>