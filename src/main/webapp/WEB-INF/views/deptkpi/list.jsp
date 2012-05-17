<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>

<head>
<%@ include file="../common/head.jsp"%>

<script type="text/javascript">
</script>

</head>
<body>
<h2>部门KPI分解</h2>
年度：${listPage.year}&nbsp;&nbsp;部门:${listPage.department.name}
<%@ include file="../common/message.jsp"%>


<table border="1" class="tbldef" >
		<thead>
			<tr>
				<th>序号</th>
				<th>公司KPI指标</th>
				<th>部门分解项数</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
	  		<c:forEach var="item" items="${listPage.pageItems}" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${item.idrCompany.context}</td>
				<td>${item.deptKpiItemNum}</td>
				<td>
					<input type="button" value="分解" onclick="window.open('/${ctx}/admin/deptkpi/${listPage.year}/department/${listPage.department.id}/idrcompany/${item.idrCompany.id}','_self');"/>
				</td>
			</tr>
			</c:forEach> 
		</tbody>
</table>

<br/>
<input type="button" value="提交" onclick="save()"/>

</body>
</html>
