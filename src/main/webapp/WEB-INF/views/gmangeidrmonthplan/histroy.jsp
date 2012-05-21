<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<head>
<%@ include file="../common/head.jsp"%>
</head>

<c:set target="${pagefunc}" property="name" value="部门月度工作任务执行" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/gmange/${person.id}/idrmonthplan" />  

<c:set target="${pagetitle}" property="name" value="部门月度工作计划历史" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/gmange/${person.id}/idrmonthplan/history" />


<c:set var="pagesize" value="720" scope="request"/>
<body>

<div class="headdiv" >
<div class="headleft" >
</div>
<div class="headright" >
<input type="button" value="<<返回" onclick="javascript:window.open('/${ctx}/gmange/${person.id}/idrmonthplan','_self')"/>
</div>
<div  class="headnone"></div>
</div>

<%@ include file="../common/message.jsp"%>
<%@ include file="../fragment/idrmonthplantable.jsp"%>
</body>
</html>
