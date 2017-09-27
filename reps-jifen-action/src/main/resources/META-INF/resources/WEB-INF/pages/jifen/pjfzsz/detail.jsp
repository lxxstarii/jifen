<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评价分值信息</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="update.mvc" formId="xform" validForm="true" method="post" title="新增评价分值">
		<reps:formcontent style="margin-top:30px">
           <reps:formfield label="指标项 " fullRow="true">
				<reps:input id="item" name="item" maxLength="30" required="true">${data.item}</reps:input>
           </reps:formfield>
            <reps:formfield label="指标类型" fullRow="true" >
           		<reps:select name="mark" jsonData="{1:'奖励',0:'惩罚'}" headerValue="" style="width:176px;" required="true">${data.mark}</reps:select>
		   </reps:formfield>
		   <reps:formfield label="奖励/扣除积分" fullRow="true" >
           		<reps:select name="pointsScope" jsonData="{'1':'0~1','2':'0~2','3':'0~3','4':'0~4','5':'0~5','-1':'-1~0','-2':'-2~0','-3':'-3~0','-4':'-4~0','-5':'-5~0'}"
           			headerValue="" style="width:176px;" required="true">
           			${data.pointsScope}
           		</reps:select>
		   </reps:formfield>
		   <reps:formfield label="上传标签/勋章" fullRow="true" >
				<reps:upload id="picture" callBack="getPathName" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/res/images/upload/link" reName="true" cssClass="uploading-a" fileType="png,jpg" ></reps:upload>
				<img name="img" width="50px",height="40px" src="${imagePath}${data.icon}"></img>
				<input type="hidden" name="icon" value = "${data.icon}"/>
           </reps:formfield>
           <reps:formfield label="是否可用" fullRow="true" >
           		<reps:select name="isEnable" jsonData="{1:'是',0:'否'}" headerValue="" style="width:176px;" required="true">${data.isEnable}</reps:select>
		   </reps:formfield>
            <reps:formfield label="详细说明" fullRow="true">
				<reps:input name="description"  multiLine="true" style="width:500px;height:80px;">${data.description}</reps:input>
           </reps:formfield>
	   </reps:formcontent>
  	</reps:panel>
  </reps:container>
</body>
</html>
