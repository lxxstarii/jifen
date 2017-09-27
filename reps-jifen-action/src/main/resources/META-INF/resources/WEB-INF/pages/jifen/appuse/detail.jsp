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
		   <reps:detail id="parentPjfzszInfo" borderFlag="true"
						textBackgroundFlag="false" style="width:800px">
			   <reps:detailfield label="应用名称" fullRow="true"
								 labelStyle="width:20%;">${info.applicationName}</reps:detailfield>

			   <reps:detailfield label="积分项" fullRow="true"
								 labelStyle="width:20%;">${info.item}</reps:detailfield>

			   <reps:detailfield label="标识码" fullRow="true"
								 labelStyle="width:20%;">${info.code}</reps:detailfield>

			   <reps:detailfield label="分值" fullRow="true"
								 labelStyle="width:20%;">${info.points}</reps:detailfield>

			   <reps:detailfield label="奖励/扣除" fullRow="true"
								 labelStyle="width:20%;">
				   <c:if test="${info.mark eq 1}">扣除</c:if>
				   <c:if test="${info.mark eq 0}">奖励</c:if>
			   </reps:detailfield>

			   <reps:detailfield label="是否需要审核" fullRow="true"
								 labelStyle="width:20%;">
				   <c:if test="${info.needCheck eq 1}">需要审核</c:if>
				   <c:if test="${info.needCheck eq 0}">不需要审核</c:if>
			   </reps:detailfield>

			   <reps:detailfield label="是否启用" fullRow="true"
								 labelStyle="width:20%;">
				   <c:if test="${info.isEnabled eq 1}">启用</c:if>
				   <c:if test="${info.isEnabled eq 0}">不启用</c:if>
			   </reps:detailfield>
	        </reps:detail>
		   <reps:formbar>
			   <reps:button cssClass="btn_cancel" type="button" onClick="back()" messageCode="add.button.cancel" />
		   </reps:formbar>
  	</reps:panel>
  </reps:container>
 <script type="text/javascript">
	 function back() {
		 window.location.href= "list.mvc";
	 }
</script>
</body>
</html>