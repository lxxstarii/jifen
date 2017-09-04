<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>修改活动页面</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="edit.mvc" formId="form" validForm="true" style="width:800px">
		<reps:formcontent>
			<input type="hidden" name="id" value="${activity.id }"/>
			<reps:formfield label="活动名称" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="name" maxLength="30" required="true">${activity.name }</reps:input>
			</reps:formfield>
			
			<reps:formfield label="活动分类" labelStyle="width:15%" textStyle="width:30%;">
	           <input type="hidden" name="jfRewardCategory.id" id="categoryId" value="${activity.jfRewardCategory.id }" />
	            <reps:input required="true" id="parentCodeName" name="parentName" readonly="true">${activity.jfRewardCategory.name}</reps:input>
	           <reps:dialog cssClass="btnLook" scrolling="true" width="300" id="chooseParentCode" iframe="true" url="choose.mvc" title="选择活动分类" value="选择活动分类" style="margin-left:-27px;" />
           </reps:formfield>
			
			<reps:formfield label="兑换截至时间" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:datepicker name="finishTimeDisp" format="yyyy-MM-dd" required="true">${activity.finishTime }</reps:datepicker>
		    </reps:formfield>
		    
		    <reps:formfield label="所需积分" labelStyle="width:15%" textStyle="width:30%;">
				<reps:input name="points" dataType="integernum" required="true">${activity.points }</reps:input>
			</reps:formfield>
			
			<reps:formfield label="上线时间" labelStyle="width:20%;" textStyle="width:20%;" fullRow="true">
				<reps:datepicker name="showTimeDisp" format="yyyy-MM-dd" required="true">${activity.showTime }</reps:datepicker>
		    </reps:formfield>
		    
			<reps:formfield label="活动详情" fullRow="true">
				<reps:input name="description" maxLength="200" multiLine="true" style="width:515px;height:70px">${activity.description }</reps:input>
			</reps:formfield>
			
			 <reps:formfield label="图片" fullRow="true">
				<img name="img" width="128px",height="128px" src="${imagePath}${activity.picture}"/> <br>
				<input type="hidden" name="picture" value="${activity.picture }"/>
				<reps:upload id="pictureid" callBack="getPathName" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/activity" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
           </reps:formfield>
		</reps:formcontent>
		<br/>
		<reps:formbar>
			<reps:ajax  messageCode="add.button.save" formId="form" callBack="my" type="button" cssClass="btn_save"></reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
       </div>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	var my = function(data){
		messager.message(data, function(){ back(); });
	};
	
	function back() {
		window.location.href= "list.mvc";
	}
	
	var getPathName = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imagePath}","");
		var picUrl = "${imageUploadHttpPath}" + picture;
		$("input[name='picture']").val(getDirPath("${imagePath}") + picture);
		$("img[name='img']").attr("src", picUrl);
	};
	
	function getDirPath(path){
		var index = path.indexOf(":")
		if(index > -1){
			return path.substring(index + 1);
		}
		return path;
	}

</script>
</html>