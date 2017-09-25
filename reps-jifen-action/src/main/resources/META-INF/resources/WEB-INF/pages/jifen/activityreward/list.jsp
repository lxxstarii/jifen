<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>活动管理列表</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<input type="hidden" name="ids" />
		<reps:formcontent parentLayout="true" style="width:80%;">
		    <reps:formfield label="活动名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="name" maxLength="30">${jfReward.name }</reps:input>
			</reps:formfield>
			<reps:formfield label="活动分类" labelStyle="width:23%;" textStyle="width:30%;">
				<reps:select  dataSource="${activityTypeMap }" name="categoryId">${jfReward.categoryId }</reps:select>
			</reps:formfield>
			<reps:formfield label="活动状态" labelStyle="width:23%;" textStyle="width:30%;">
				<reps:select name="isShown" jsonData="{'':'全部','0':'未发布','1':'进行中','2':'已结束'}">${jfReward.isShown }</reps:select>
			</reps:formfield>
			<%-- <reps:formfield label="兑换截至时间"><reps:datepicker name="finishTimeDisp" format="yyyy-MM-dd" /></reps:formfield> --%>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="activityList" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:button cssClass="add-a" action="toadd.mvc?id=${activity.id }" messageCode="manage.action.add" value="新增"></reps:button>
			<!-- <reps:ajax cssClass="batch-approval-a" confirm="确认批量发布吗?" beforeCall="checkPublishChecked" formId="queryForm" redirect="list.mvc" value="批量发布" /> -->
			<!-- <reps:ajax cssClass="delete-a" confirm="确认批量删除吗?" beforeCall="checkDeleteChecked" formId="queryForm" redirect="list.mvc" value="批量删除" /> -->
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="activityList" items="${list}" form="queryForm" var="activity" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<input type="hidden" name="activityStatus" value="${activity.isShown }">
				<%-- <reps:gridcheckboxfield checkboxName="id" align="center" title="" width="5">${activity.id}</reps:gridcheckboxfield> --%>
				<reps:gridfield title="活动分类" width="15" align="center">${activity.jfRewardCategory.name}</reps:gridfield>
				<reps:gridfield title="活动名称" width="25" align="center">${activity.name }</reps:gridfield>
				<reps:gridfield title="所需积分" width="15" align="center">${activity.points}</reps:gridfield>
				<reps:gridfield title="兑换截至时间" width="25" align="center">
					<fmt:formatDate value="${activity.finishTime }" pattern="yyyy-MM-dd"/>
				</reps:gridfield>
				<reps:gridfield title="上线时间" width="25" align="center">
					<fmt:formatDate value="${activity.showTime }" pattern="yyyy-MM-dd"/>
				</reps:gridfield>
				<reps:gridfield title="活动详情" width="30" >${activity.description}</reps:gridfield>
				<reps:gridfield title="活动状态" width="15" align="center"><c:if test="${activity.isShown == '1'}">进行中</c:if><c:if test="${activity.isShown == '0' }">未发布</c:if><c:if test="${activity.isShown == '2' }">已结束</c:if></reps:gridfield>
				<%-- <reps:gridfield title="已参与/已兑换" width="25" align="center"></reps:gridfield> --%>
				<reps:gridfield title="操作" width="50">
					<reps:button cssClass="detail-table" action="show.mvc?id=${activity.id }" value="详细"></reps:button>
					<c:if test="${activity.isShown == '1'}">
						<reps:ajax cssClass="publish-table" value="取消发布" confirm="您确定要取消发布吗？" redirect="list.mvc" url="batchpublish.mvc?ids=${activity.id }&status=0"></reps:ajax>
					</c:if>
					<c:if test="${activity.isShown == '2'}">
						<reps:dialog cssClass="publish-table" id="add" iframe="true" width="350"
					 height="250" url="todelay.mvc?id=${activity.id}" value="延期"></reps:dialog>
					<%-- 	<reps:ajax cssClass="publish-table" value="延期" confirm="您确定要重新发布吗？" redirect="list.mvc" url="batchpublish.mvc?ids=${activity.id }&status=1"></reps:ajax> --%>
					</c:if>
					<c:if test="${activity.isShown == '0'}">
						<reps:ajax cssClass="publish-table" value="发布" confirm="您确定要发布吗？" redirect="list.mvc" url="batchpublish.mvc?ids=${activity.id }&status=1"></reps:ajax>
					</c:if>
					<c:if test="${activity.isShown != '1' }">
						<reps:button cssClass="modify-table" messageCode="manage.action.update" action="toedit.mvc?id=${activity.id}" ></reps:button>
						<reps:ajax cssClass="delete-table" messageCode="manage.action.delete" confirm="您确定要删除所选行吗？"
							redirect="list.mvc" url="delete.mvc?id=${activity.id}&validRecord=9">
						</reps:ajax>
					</c:if>
					<reps:button cssClass="look-summary-table"  value="统计管理" action="statistics.mvc?rewardId=${activity.id}" ></reps:button>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var my = function(data){
		window.location.href= "list.mvc";
	};
		
	function buildIdParams(msg){
		if ($("input[type=checkbox][name=id]:checked").length == 0) {
			messager.info(msg);
			return false;
		}
		var ids = $("input[type=hidden][name=ids]");
		ids.val("");
		$.each($("input[type=checkbox][name=id]:checked"), function(i, obj) {
			if (ids.val() == "") {
				ids.val($(obj).val());
			} else {
				ids.val(ids.val() + "," + $(obj).val());
			}
		});
		return true;
	}
	
	var checkDeleteChecked = function() {
		if(buildIdParams("请选择要批量删除的活动信息")){
			$("#queryForm").attr("action", "batchdelete.mvc");
			return true;
		}else{
			return false;
		}
	};
	
	var checkPublishChecked = function(){
		if(buildIdParams("请选择要批量发布的活动信息") && checkActivityStatus()){
			$("#queryForm").attr("action", "batchpublish.mvc");
			return true;
		}else{
			return false;
		}
	};
	
	
</script>
</body>
</html>
