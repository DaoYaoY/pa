<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<head>
<%@ include file="../common/head.jsp"%>
</head>

<c:set target="${pagefunc}" property="name" value="部门月度计划" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/monthplan/gm" />  


<c:set var="pagesize" value="825" scope="request"/> 
<body>

<div class="headdiv" >
<div class="headleft" >
</div>
<div class="headright"  >
</div>
<div  class="headnone"></div>
</div>

<%@ include file="../common/message.jsp"%>
<table border=1 style="table-layout:fixed;width:800px;">
<thead>
	<tr>
		<th style="width:50px;">序号</th>
		<th style="width:500px;">工作完成情况表</th>
		<th style="width:150px;">部门</th>
		<th style="width:100px;">操作</th>
	</tr>
</thead>
<tbody>
<c:forEach var="item" items="${idrMonthPlanBills}"  varStatus="status">
	<tr>
	<td>
		${status.count}
	</td>
	<td>
		${item.year}年${item.month}月份${item.department.name}工作执行情况【${item.state.name}】
	</td>
	<td>
		${item.department.name}
	</td>
	<td>
		<c:choose>
		<c:when test="${item.state=='SUBMITTED'}">
			<input type="button" value="审核" onclick="javascript:window.open('/${ctx}/monthplan/gm/${item.id}','_self')"/>    
		</c:when>
		<c:otherwise>
			<input type="button" value="查看" onclick="javascript:window.open('/${ctx}/monthplan/gm/${item.id}','_self')"/> 
		</c:otherwise>  
		</c:choose>		
	</td>
				
   </tr>
</c:forEach>
</tbody>
</table>

</body>
</html>
