<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
<%@ include file="../../common/head.jsp"%>


<script type="text/javascript">
	var selChange=function(){
		$('<form/>',{action:'/${ctx}/finance/${person.id}/summarysnapshot/receive',method:'post'})
		.append($('<input/>',{type:'hidden',name:'year',value:$("select[name=year]").val()}))
		.append($('<input/>',{type:'hidden',name:'month',value:$("select[name=month]").val()}))
	 	.appendTo($("body"))
	 	.submit();
	}
	
	$(document).ready(function(){
		$("select[name=year],select[name=month]").bind("change",selChange);
	})
	
	function save(){
		$("div.person ul").formatName();
		$('form').submit();
	}
</script> 
</head>

<c:set target="${pagefunc}" property="name" value="员工考核结果" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/finance/${person.id}/summarysnapshot" />  

<c:set target="${pagetitle}" property="name" value="员工考核结果接收" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/finance/${person.id}/summarysnapshot/receive" /> 

<c:set var="pagesize" value="990" scope="request"/>  

<body>
<form action="/${ctx}/finance/${person.id}/summarysnapshot/receive/save" method="post">

<div class="headdiv" >
<div class="headleft" >
	考核周期:
	<select name="year">
		<c:forEach var="item" items="${dateTool.allYears}">
			<option value="${item}" <c:if test="${item==queryBean.year}">selected="true"</c:if> >${item}</option>
		</c:forEach>
	</select>年
	<select name="month">
		<c:forEach var="item" items="${dateTool.allMonths}">
			<option value="${item}" <c:if test="${item==queryBean.month}">selected="true"</c:if> >${item}</option>
		</c:forEach>
	</select>月
</div>
<div class="headright">
	<c:if test="${!existSummarySnapshot}">
	<input type="button" value="接收考核结果"  onclick="save()"/>
	</c:if>
</div>
<div  class="headnone"></div>
</div>


<%@ include file="../../common/message.jsp"%>
<%@ include file="../../fragment/snapshotitemview.jsp"%>


</form>

</body>
</html>
