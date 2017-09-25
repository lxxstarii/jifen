<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title></title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/jifen/orderinfo/list.mvc" method="post" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="订单号" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="orderNo">${query.orderNo}</reps:input>
			</reps:formfield>
			<reps:formfield label="订单状态" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:select name="status" jsonData="{'':'', '0':'未处理', '1':'已处理', '2':'已取消', '3':'已完成'}">${query.status}</reps:select>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm"  gridId="mygrid" callBack="setDisabled" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridfield title="订单号" width="10" align="center">${data.orderNo}</reps:gridfield>
				<reps:gridfield title="兑换日期" width="10" align="center">
					<fmt:formatDate value="${data.createTime }" pattern="yyyy-MM-dd"/>
				</reps:gridfield>
				<reps:gridfield title="物品名称" width="10" align="center">
					${data.reward.name}
				</reps:gridfield>
				<reps:gridfield title="兑换数量" width="9" align="center">
					${data.nums}
				</reps:gridfield>
				<reps:gridfield title="所需积分" width="9" align="center">
					${data.usedPoints * data.nums}
				</reps:gridfield>
				<reps:gridfield title="订单状态" width="9" align="center">
					<c:if test="${data.status=='0'}">未处理</c:if>
					<c:if test="${data.status=='1'}">已处理</c:if>
					<c:if test="${data.status=='2'}">已取消</c:if>
					<c:if test="${data.status=='3'}">已收货</c:if>
				</reps:gridfield>
				<reps:gridfield title="操作" width="18">
					<reps:button cssClass="modify-table" action="toedit.mvc?id=${data.id}" value="修改"></reps:button>
					<reps:dialog cssClass="modify-table" id="orgs" rel="orgs" type="button" width="300" title="运单信息" value="运单信息"
					url="tofill.mvc?id=${data.id}" iframe="true"  cancelCall="function(){}" ></reps:dialog>
					<c:if test="${data.status != '3' && data.status != '2'}">
						 <reps:ajax id="cancel"  url="cancel.mvc?id=${data.id}&personId=${data.personId}&points=${data.usedPoints * data.nums}" value="取消发货"
							cssClass="modify-table" confirm="你确定要取消发货吗？" redirect="list.mvc"></reps:ajax>
					</c:if>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
 	
</script>
</body>
</html>