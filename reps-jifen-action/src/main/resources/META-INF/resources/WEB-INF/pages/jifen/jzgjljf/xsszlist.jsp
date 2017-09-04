<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>奖励系数列表</title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="" method="post" formId="queryForm">
		<reps:footbar>
			<reps:dialog cssClass="add-a" id="add" iframe="true" width="350"
				 height="250" url="jlxssz.mvc" value="设置"></reps:dialog>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridfield title="班主任系数" width="15" align="center">
					${data.bzr}
				</reps:gridfield>
				<reps:gridfield title="任课教师系数" width="15" align="center">
					${data.rkjs}
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	
 	
</script>
</body>
</html>