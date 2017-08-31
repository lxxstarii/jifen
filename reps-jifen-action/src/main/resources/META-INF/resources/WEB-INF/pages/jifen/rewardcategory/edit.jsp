<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>指标分类修改</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="edit.mvc" formId="xform" validForm="true" style="width:800px">
		<reps:formcontent>
		
			<reps:formfield label="上级分类" labelStyle="width:20%" textStyle="width:82%;padding-left:10px;font-size:16px;font-weight:bold;color:blue;" fullRow="true">
				${not empty parentCategory ? parentCategory.name : ""}
				 <input type="hidden" name="parentId" value="${not empty parentCategory ? parentCategory.id : '-1'}">
			</reps:formfield>
			
			<input type="hidden" value="${category.id}" name="id">
			<reps:formfield label="分类名称" labelStyle="width:20%" textStyle="width:20%">
				<reps:input name="name" maxLength="30" required="true">${category.name }</reps:input>
			</reps:formfield>
			<reps:formfield label="分类类别" labelStyle="width:15%" textStyle="width:30%">
				<reps:select name="type" dataSource="${categoryTypeMap}" required="true">${category.type }</reps:select>
			</reps:formfield>
			<reps:formfield label="分类描述" fullRow="true">
				<reps:input name="description" maxLength="200" multiLine="true" style="width:516px;height:70px">${category.description }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:formbar>
			<reps:ajax messageCode="edit.button.save" formId="xform" callBack="skip" type="link"
				confirm="确定要提交修改？" cssClass="btn_save_a">
			</reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function() {
			messager.message(data, function(){ back(); });
		});
	};
	
	function back() {
		window.location.href= "list.mvc?parentId=${category.parentId}";
	}
	
</script>
</body>
</html>