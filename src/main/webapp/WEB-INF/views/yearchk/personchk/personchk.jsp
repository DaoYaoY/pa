<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
<%@ include file="../../common/head.jsp"%>
<style type="text/css">
.mainul{list-style-type:none; margin:0;padding:0;width:100%; }
.mainul li{ width:84px; float:left;background-color:#FFFFFF;margin: 10px;border: 1px solid #000000; }
.mainul li select{width:80px;}
.mainul li div {text-align: center; padding: 2px;}
</style>
<script type="text/javascript">
	function save(){
		var actionFrom=$("form");
		var oldAction=actionFrom.attr("action");
		$(".mainul").formatName();
		actionFrom.attr("action",oldAction+"/save").submit();
	}
	
	function personCompare(comparePersonId){
		if(comparePersonId=='') return;
		OpenEnvDefineWin("/${ctx}/person/${person.id}/yearchk/personchk/${pageBean.personChkBean.id}/comparework/"+comparePersonId,880,600);
	}
</script>
</head>
<c:set target="${pagefunc}" property="name" value="年终员工考核" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/person/${person.id}/yearchk" />  

<c:set target="${pagetitle}" property="name" value="年终员工考核列表" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/person/${person.id}/yearchk/personchk/${pageBean.personChkBean.id}" /> 

<c:set var="pagesize" value="820" scope="request"/> 
<body>

<div class="headdiv" >
<div class="headleft"  >
考核年份:${pageBean.year}&nbsp;&nbsp;员工:${pageBean.personChkBean.name}&nbsp;&nbsp;
状态:胜${pageBean.personChkBean.win}&nbsp;负${pageBean.personChkBean.lose}&nbsp;平${pageBean.personChkBean.draw}&nbsp;</td>
</div>
<div class="headright" >
</div>
<div  class="headnone"></div>
</div>
<%@ include file="../../common/message.jsp"%>
<form action="/${ctx}/person/${person.id}/yearchk/personchk/${pageBean.personChkBean.id}" method="post">
<table border=1 style="table-layout:fixed;width:800px;" >
<thead>
	<tr>
		<th style="width:100px;">员工</th>
		<th style="width:700px;">对比考核结果</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${pageBean.personChkBean.name}</td>
		<td>
		<ul class="mainul">
			<c:forEach var="item" items="${pageBean.cellBeans}" varStatus="status">
				<li>
					<div>
						${item.rowPerson.name}
						<input type="hidden" name="fychecks_id" value="${item.fycheck.id}"/>
						<input type="hidden" name="fychecks_year" value="${pageBean.year}"/>
						<input type="hidden" name="fychecks_colId" value="${item.colPerson.id}"/>
						<input type="hidden" name="fychecks_rowId" value="${item.rowPerson.id}"/>
						<input type="hidden" name="fychecks_chkId" value="${person.id}"/>
					</div>
					<div>
						<select name="fychecks_val">
							<option value="1"    <c:if test="${item.fycheck.val=='1'}">selected="true"</c:if> >${item.colPerson.name}胜</option>
							<option value="0"    <c:if test="${item.fycheck.val=='0'}">selected="true"</c:if> >平</option>
							<option value="-1"   <c:if test="${item.fycheck.val=='-1'}">selected="true"</c:if> >${item.rowPerson.name}胜</option>
						</select>
					</div>
					<div>
						<input type="button" value="对比两人" onclick="personCompare('${item.rowPerson.id}')">
					</div>
				</li>
			</c:forEach>
		</ul>
		</td>
	</tr>
</tbody>
</table>
<br/>
<input type="button" value="保存" onclick="save()"/>
<input type="button" value="<<返回" onclick="javascript:window.open('/${ctx}/person/${person.id}/yearchk','_self')"/>
</form>
</body>
</html>
