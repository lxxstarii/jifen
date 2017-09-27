<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>教师奖励分值设置</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="savejljf.mvc" formId="xform" validForm="true" method="post" title="教职工奖励积分">
		<input type="hidden" name="teacherId" id="teacherId" >
		<input type="hidden" name="organizeId" id="organizeId">
		<div class="block_title" id="divUser">
			<reps:formcontent style="">
		   		<reps:formfield label="选择教师" labelStyle="width:18%" textStyle="width:30%"  fullRow="true">
					<reps:input name="user.person.name" required="true" minLength="2" maxLength="60" />
					<reps:dialog cssClass="add-a" id="selectUser" iframe="true" width="600" height="360" value="选择教师"
						url="teachers.mvc?dialogId=selectUser&callBack=chooseUserBack"></reps:dialog>
				</reps:formfield>
				<reps:formfield label="分配积分" fullRow="true" >
           			<reps:input name="jljf" required="true" minLength="1" maxLength="3" />
		   		 </reps:formfield>
			 </reps:formcontent>
		</div>
	    <reps:formbar style="margin-top:15px;">
  		   <reps:ajax confirm="你确定要提交吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
  
	var my = function(data){
		messager.message(data, function() {
			window.parent.location.href = "jfszlist.mvc";
		});
	}
	
	var chooseUserBack = function(id, name, organizeId){
		$("#teacherId").val(id);
		$("#organizeId").val(organizeId);
		$.ajax({
			type: "GET",
			url: "${ctx}${actionBasePath}/sys/chooseperson/getdetail.mvc",
			dataType: "json",
			data: {"id" : id, "run" : Math.random()},
			success: function(data){
				$("#divUser").find(".txtInput,select,input[type=hidden]").each(function(){
					var objName = $(this).attr("name");
					if (objName.indexOf('user.person') > -1) {
						var name = objName.replace("user.person.", "");
						var value = eval("data." + name);
						$("[name='" + objName + "']").val(value);
					}
				});
				
				//如果选择机构，名称不许修改。如果放开会有重写其他机构数据的风险。
				//比如：选择一个机构，此时发现机构选错了，操作员想新增加一个学校，在页面上直接修改，后台无法判断是修改的学校还是想新增的学校
				$("input[name='user.person.name']").attr("readonly", "readonly");
				$("input[name='user.person.name']").css("background-color", "rgb(233, 233, 233)");
			}
		});
	}
  </script>
  </body>
</html>