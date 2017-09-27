<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/commons/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>积分规则设置</title>
    <reps:theme/>
</head>
<body>
<reps:container layout="true">
    <reps:panel id="mytop" dock="top" method="post" action="list.mvc"  formId="xform" >
        <reps:formcontent parentLayout="true" style="width:80%;">
            <reps:formfield label="应用名称">
                <reps:input name="applicationName" >${info.applicationName}</reps:input>
            </reps:formfield>
        </reps:formcontent>
        <reps:querybuttons>
            <reps:ajaxgrid messageCode="manage.button.query" formId="xform" gridId="mygrid" cssClass="search-form-a"></reps:ajaxgrid>
        </reps:querybuttons>
        <reps:footbar>
            <reps:button messageCode="manage.action.add" action="toadd.mvc" cssClass="add-a" menuCode="jifen070101"></reps:button>
        </reps:footbar>
    </reps:panel>

    <reps:panel id="mybody" dock="center" border="false">
        <reps:grid id="mygrid" items="${list}" var="item"  form="xform" pagination="${pager}" flagSeq="true">
            <reps:gridrow>
                <reps:gridfield title="应用名称" width="8" align="center">${item.applicationName}</reps:gridfield>
                <reps:gridfield title="积分项" width="8" align="center">${item.item}</reps:gridfield>
                <reps:gridfield title="标识码" width="8" align="center">${item.code}</reps:gridfield>
                <reps:gridfield title="分值" width="8" align="points">${item.points}</reps:gridfield>
                <reps:gridfield title="奖励/扣除" width="8" align="center">
                    <c:if test="${item.mark eq 1}">奖励</c:if>
                    <c:if test="${item.mark eq 0}">扣除</c:if>
                </reps:gridfield>
                <reps:gridfield title="需要审核" width="8" align="center">
                    <c:if test="${item.needCheck eq 1}">√</c:if>
                </reps:gridfield>
                <reps:gridfield title="是否启用" width="8" align="center">
                    <c:if test="${item.isEnabled eq 1}">√</c:if>
                </reps:gridfield>
                <reps:gridfield title="操作" width="10" align="center">
                    <reps:button cssClass="detail-table" action="show.mvc?id=${item.id}" messageCode="manage.action.detail" menuCode="jifen070104"></reps:button>
                    <reps:button cssClass="modify-table" action="toedit.mvc?id=${item.id}" messageCode="manage.action.update" menuCode="jifen070102"></reps:button>
                    <sys:authority code="jifen070103">
                        <reps:ajax value="删除" id="delete" url="delete.mvc?id=${item.id}" cssClass="delete-table" confirm="您确定要删除所选行吗?" redirect="list.mvc" ></reps:ajax>
                    </sys:authority>
                </reps:gridfield>
            </reps:gridrow>
        </reps:grid>
    </reps:panel>
</reps:container>
</body>
</html>