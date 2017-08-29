<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>指标分类管理</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="分类:${path}" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="指标分类名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="name"></reps:input>
			</reps:formfield>
			<reps:formfield label="指标类别" labelStyle="width:23%;" textStyle="width:30%;">
				<reps:select dataSource="${categoryList}" name="parentId">${info.parentId}</reps:select>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="itemlist" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:button cssClass="add-a" action="toadd.mvc" messageCode="manage.action.add" value="添加分类"></reps:button>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="itemlist" items="${list}" form="queryForm" var="itemCate" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="指标分类名称" width="25">${itemCate.name}</reps:gridfield>
				<reps:gridfield title="备注" width="50">${itemCate.remark}</reps:gridfield>
				<reps:gridfield title="操作" width="25">
					<reps:button cssClass="add-table" value="添加指标" action="${ctx}/reps/report/item/toadd.mvc?categoryId=${itemCate.id}"></reps:button>
					<reps:button cssClass="modify-table" messageCode="manage.action.update" action="toedit.mvc?id=${itemCate.id}"></reps:button>
					<reps:ajax cssClass="delete-table" messageCode="manage.action.delete" confirm="您确定要删除所选行吗？"
						callBack="my" url="delete.mvc?id=${itemCate.id}">
					</reps:ajax>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var my = function(data){
		messager.message(data, function(){ window.parent.location.reload(); });
	};
</script>
</body>
</html>
