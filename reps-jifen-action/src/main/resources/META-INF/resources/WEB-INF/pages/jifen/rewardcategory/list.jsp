<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>奖品分类管理列表</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="分类名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="name"></reps:input>
			</reps:formfield>
			<reps:formfield label="分类类别" labelStyle="width:23%;" textStyle="width:30%;">
				<reps:select dataSource="${categoryTypeMap}" name="type"></reps:select>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="categoryList" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:button cssClass="add-a" action="toadd.mvc?id=${category.parentId }" messageCode="manage.action.add" value="新增"></reps:button>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="categoryList" items="${list}" form="queryForm" var="category" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="分类名称" width="15" align="center">${category.name}</reps:gridfield>
				<reps:gridfield title="分类类别" width="25" align="center">
					<c:forEach items="${categoryTypeMap}" var="c">
							<c:if test="${c.key == category.type}">${c.value}</c:if>
					</c:forEach>
				</reps:gridfield>
				<reps:gridfield title="描述" width="40">${category.description}</reps:gridfield>
				<reps:gridfield title="操作" width="40">
					<reps:button cssClass="detail-table" action="show.mvc?id=${category.id }" value="详细"></reps:button>
					<reps:button cssClass="add-table" value="添加下级分类" action="toadd.mvc?id=${category.id }"></reps:button>
					<reps:button cssClass="modify-table" messageCode="manage.action.update" action="toedit.mvc?id=${category.id}"></reps:button>
					<reps:ajax cssClass="delete-table" messageCode="manage.action.delete" confirm="您确定要删除所选行吗？"
						callBack="my" url="delete.mvc?id=${category.id}">
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
