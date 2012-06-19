<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<head>
<%@ include file="../common/head.jsp"%>
<style type="text/css">
tbody ul{list-style-type:none; margin:0;padding:0;width:100%; }
tbody ul li{ width:150px; float:left;background-color:#FFFFFF;margin: 4px;border: 1px solid #97CBFF; }
</style>
<script type="text/javascript">

	var selchange=function(){
		var selval=$(this).val();
		if(selval=="all"){
			$(".tbldef li:hidden").show();
			$(".tbldef td:hidden").show();
		}else{
			$(".tbldef li:not(."+selval+")").hide();
			$(".tbldef li."+selval).show();
			$(".tbldef td").has("ul").each(function(){
				if($(this).is(":has(li."+selval+")")){
					$(this).show();
				}else{
					$(this).hide();
				}	
			});
		}
	}

	 $(document).ready(function() {
		$("#swithbtn").bind("change",selchange);
	 });
</script>
</head>

<c:set target="${pagefunc}" property="name" value="公司考核情况查询" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/gmange/${person.id}/totalreport" />  

<c:set target="${pagetitle}" property="name" value="公司部门月度工作概况" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/gmange/${person.id}/analysis/idrmonthplan" /> 

<body>

<form action="" method="get">
年份：
<select name="year">
	<c:forEach var="item" items="${dateTool.allYears}">
		<option value="${item}" <c:if test="${item==queryBean.year}">selected="true"</c:if> >${item}</option>
	</c:forEach>
</select>
月份：
<select name="month">
	<c:forEach var="item" items="${dateTool.allMonths}">
		<option value="${item}" <c:if test="${item==queryBean.month}">selected="true"</c:if> >${item}</option>
	</c:forEach>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value="查询" />
</form>
<%@ include file="../common/message.jsp"%>
<table border=1  style="table-layout:fixed;width:810px;" class="tbldef">
<thead>
	<tr>
		<th>公司部门${queryBean.year}年${queryBean.month}月工作概况</th>
	</tr>
</thead>
		<tbody>
			<tr>
				<td>
				<div style="width:50%;float: left;">
				合计参与考核部门${analysisIdrMonthPlanBean.totalNum}个,<c:choose><c:when test="${analysisIdrMonthPlanBean.totalNum==analysisIdrMonthPlanBean.finishNum}">全部完成考核。</c:when><c:otherwise>${analysisIdrMonthPlanBean.finishNum}个已完成。</c:otherwise></c:choose>
				</div>
				<div style="width:50%;float:left;text-align: right;">
					<select id="swithbtn">
						<option value="all">所有部门</option>
						<c:forEach var="item" items="${stateEnum}">
							<option value="${fn:toLowerCase(item)}">${item.name}</opiton>
						</c:forEach>
					</select>
				</div>
				</td>
			</tr>
			<tr>
				<td>
				<ul>
					<c:forEach var="list" items="${analysisIdrMonthPlanBean.departmentIdrMonthPlanBillStateBeans}">
						<li class="${fn:toLowerCase(list.state)} ">${list.departmentName}:${list.state.name}</li>
					</c:forEach>
				</ul>
				</td>
			</tr>
		</tbody>
</table>
</body>
</html>
