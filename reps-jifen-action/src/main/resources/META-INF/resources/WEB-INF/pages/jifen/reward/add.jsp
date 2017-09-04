<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加物品页面</title>
	<reps:theme />
	<script type="text/javascript" src="<c:url value="/library/ueditor/ueditor.config.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/library/ueditor/ueditor.all.min.js"/>"></script>
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="add.mvc" formId="form" validForm="true" style="width:800px">
		<reps:formcontent>
		
			<reps:formfield label="物品名称" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="name" maxLength="30" required="true"></reps:input>
			</reps:formfield>
			
			 <reps:formfield label="所需积分" labelStyle="width:15%" textStyle="width:30%;">
				<reps:input name="points" dataType="integernum" required="true"></reps:input>
			</reps:formfield>
			
			<reps:formfield label="物品分类" labelStyle="width:20%" textStyle="width:20%;">
	           <input type="hidden" name="jfRewardCategory.id" id="categoryId" value="" />
	            <reps:input required="true" id="parentCodeName" name="parentName" readonly="true"></reps:input>
	           <reps:dialog cssClass="btnLook" scrolling="true" width="300" id="chooseParentCode" iframe="true" url="choose.mvc" title="选择物品分类" value="选择物品分类" style="margin-left:-27px;" />
            </reps:formfield>
           
            <reps:formfield label="库存数量" labelStyle="width:15%" textStyle="width:30%;">
				<reps:input name="numbers" dataType="integernum" required="true"></reps:input>
			</reps:formfield>
			
			<reps:formfield label="物品图片1" fullRow="true">
				<reps:upload id="file1" callBack="getPathNameOne" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/reward" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
				<input type="hidden" name="rewardUrlOne" id="rewardUrlOne"/>
				<span id="rewardPicOne"><font color="red">只能上传(png、jpg)格式,2M以内</font></span>
           </reps:formfield>
           
           <reps:formfield label="物品图片2" fullRow="true">
				<reps:upload id="file2" callBack="getPathNameTwo" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/reward" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
				<input type="hidden" name="rewardUrlTwo" id="rewardUrlTwo"/>
				<span id="rewardPicTwo"><font color="red">只能上传(png、jpg)格式</font></span>
           </reps:formfield>
           
           <reps:formfield label="物品图片3" fullRow="true">
				<reps:upload id="file3" callBack="getPathNameThree" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/reward" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
				<input type="hidden" name="rewardUrlThree" id="rewardUrlThree"/>
				<span id="rewardPicThree"><font color="red">只能上传(png、jpg)格式</font></span>
           </reps:formfield>
           
           <reps:formfield label="物品图片4" fullRow="true">
				<reps:upload id="file4" callBack="getPathNameFour" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/reward" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
				<input type="hidden" name="rewardUrlFour" id="rewardUrlFour"/>
				<span id="rewardPicFour"><font color="red">只能上传(png、jpg)格式</font></span>
           </reps:formfield>
           
           <reps:formfield label="物品图片5" fullRow="true">
				<reps:upload id="file5" callBack="getPathNameFive" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/reward" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2"></reps:upload>
				<input type="hidden" name="rewardUrlFive" id="rewardUrlFive"/>
				<span id="rewardPicFive"><font color="red">只能上传(png、jpg)格式</font></span>
           </reps:formfield>
			
			<reps:formfield label="物品详情" fullRow="true">
				<textarea class="fl"  name="description" id="contentId" style="width: 515px;height: 300px;"></textarea>
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
	
	var getPathNameOne = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("#rewardUrlOne").val(picture);
		$("#rewardPicOne").html(picture);
	};
	
	var getPathNameTwo = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("#rewardUrlTwo").val(picture);
		$("#rewardPicTwo").html(picture);
	};
	
	var getPathNameThree = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("#rewardUrlThree").val(picture);
		$("#rewardPicThree").html(picture);
	};
	
	var getPathNameFour = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("#rewardUrlFour").val(picture);
		$("#rewardPicFour").html(picture);
	};
	
	var getPathNameFive = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("#rewardUrlFive").val(picture);
		$("#rewardPicFive").html(picture);
	};
	
</script>
</html>