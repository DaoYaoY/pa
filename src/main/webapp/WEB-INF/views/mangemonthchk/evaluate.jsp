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
		$("#tbl tr").removeClass("currRow");
		$(this).addClass("currRow");
	};
	
	$(document).ready(function() {
		setTimeout(function() {
			$("#msg").slideToggle(1000);
		}, 3000);
		$("#tbl tr").click(rowClick);
	});
	
	function save(){
		var oldAction=$("#monthChk").attr("action");
		$("#monthChk").attr("action",oldAction+"/save").submit();
	}
	
	function finish(){
		var oldAction=$("#monthChk").attr("action");
		var msg="单据完成以后，将无法撤销，确定完成？";
		if(confirm(msg)){
			$("#monthChk").attr("action",oldAction+"/finish").submit();
		}
	}
	
	function back(){
		var oldAction=$("#monthChk").attr("action");
		var msg="单据打回以后，将交由提交人重新修改，确定打回？";
		if(confirm(msg)){
			$("#monthChk").attr("action",oldAction+"/back").submit();
		}
	}
	
</script>  
<body>
经理${mange.name}对员工${monthChk.person.name}${monthChk.year}年${monthChk.month}月份工完成情况评价

<c:if test="${msg!=null}">
 <div id="msg" style="background-color:red;width:300px">${msg}</div>
</c:if>

<form id="monthChk" action="/${ctx}/mange/${mange.id}/monthchk/${monthChk.id}" method="post">

<table border=1 style="table-layout:fixed;width:800px;">
<thead>
	<tr>
		<th style="width:50px;">序号</th>
		<th style="width:600px;">工作内容</th>
		<th style="width:150px;">评价</th>
	</tr>
</thead>
<tbody id="tbl">
	<c:forEach var="item" items="${monthChk.monthChkItems}">
		<tr>
			<td style="display:none">
				<input type="text" name="monthChkItems_id"   value="${item.id}" />
			</td>
			<td>
				${item.sn}
			</td>
			<td>
				${item.task}
			</td>
			<td>
				<select name="monthChkItems_point" >
					<option value="5" <c:if test="${item.point=='5'}">selected="true"</c:if> >优秀</option>
					<option value="4" <c:if test="${item.point=='4'}">selected="true"</c:if> >良好</option>
					<option value="3" <c:if test="${item.point==null||item.point=='3'}">selected="true"</c:if> >尽职</option>
					<option value="2" <c:if test="${item.point=='2'}">selected="true"</c:if> >需改进</option>
					<option value="1" <c:if test="${item.point=='1'}">selected="true"</c:if> >差</option>
				</select>
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>

<input type="button" value="保存"  onclick="save()"/>
<input type="button" value="完成"  onclick="finish()"/>
<input type="button" value="打回"  onclick="back()"/>
<input type="button" value="返回"  onclick="javascript:window.open('/${ctx}/mange/${mange.id}/monthchk','_self')"/>
</form>

</body>
</html>
