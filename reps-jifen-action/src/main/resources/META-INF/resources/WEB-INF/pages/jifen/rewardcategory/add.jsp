<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>奖品分类分类添加</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="add.mvc" formId="form" validForm="true" style="width:800px">
		<reps:formcontent>
		
		    <reps:formfield label="上级分类" labelStyle="width:20%" textStyle="width:82%;padding-left:10px;font-size:16px;font-weight:bold;color:blue;" fullRow="true">
				${not empty category.name ? category.name : ""}
				 <input type="hidden" name="parentId" value="${not empty category.id ? category.id : '-1'}">
			</reps:formfield>
			
			<reps:formfield label="分类名称" labelStyle="width:20%" textStyle="width:20%">
				<reps:input name="name" maxLength="30" required="true"></reps:input>
			</reps:formfield>
			
			<reps:formfield label="分类类别" labelStyle="width:15%" textStyle="width:30%" fullRow="true">
				<reps:select name="type" dataSource="${categoryTypeMap}" required="true">${not empty category.type ? category.type : ""}</reps:select>
			</reps:formfield>
			
			<reps:formfield label="分类描述" fullRow="true">
				<reps:input name="description" maxLength="90" multiLine="true" style="width:516px;height:70px"></reps:input>
			</reps:formfield>
		</reps:formcontent>
		<br/>
		<reps:formbar>
			<reps:ajax  messageCode="add.button.save" formId="form" callBack="skip" type="button" cssClass="btn_save"></reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function(){ window.parent.location.reload(); });
	};
	
	function back() {
		window.location.href= "list.mvc?parentId=${not empty category.id ? category.id : '-1'}";
	}

</script>
</html>