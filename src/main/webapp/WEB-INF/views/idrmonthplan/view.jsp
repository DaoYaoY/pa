<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<%@ include file="../common/head.jsp"%>
<style type="text/css">
	.currRow{
		background-color:#77BBFF;
	}
</style>


<script type="text/javascript">
var rowClick=function (){
	$(".tbldef tbody tr").removeClass("currRow");
	$(this).addClass("currRow");
};


$(document).ready(function() { 
	$(".tbldef tbody tr").click(rowClick);
});

</script>  


<body>
<h2>部门月度工作计划【查看】</h2>
<%@ include file="personinfo.jsp"%>
<%@ include file="../common/message.jsp"%>
<table border=1 style="table-layout:fixed;width:650px;" class="tbldef">
<thead>
	<tr>
		<td style="width:50px;">序号</td>
		<td style="width:600px;">工作计划</td>
	</tr>
</thead>
<tbody id="tbl">
	<c:forEach var="item" items="${idrMonthPlanBill.idrTasks}">
		<tr>
			<td style="display:none">
				<input type="text" name="idrTasks_id"   value="${item.id}" />
				<input type="text" name="idrTasks_sn"   value="${item.sn}" />
			</td>
			<td>
				${item.sn}
			</td>
			<td>
				${item.context}
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>

<input type="button" value="返回"  onclick="javascript:window.open('/${ctx}/mange/${person.id}/all','_self')"/>
<input type="button" value="退出"  onclick="javascript:window.open('/${ctx}/login','_self')"/>
</form>

</body>
</html>
