<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
<%@ include file="../../common/head.jsp"%>
<script type="text/javascript">

	var optColorArr=["#FF8080","#FECF78","#B9EA48","#00C462","#1E8EFF"];

	$(document).ready(function() {
		$(".flagchk").bind('click',function(){
			var nextSel=$(this).parent().next().find("select");
			if($(this).attr("checked")){
				$(this).next().val("true");
				nextSel.show();
			}else{
				$(this).next().val("false");
				nextSel.hide();
			}
		});
		
		$(".select_val").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		});
	 });
	
	function save(){
		var actionFrom=$("form");
		var oldAction=actionFrom.attr("action");
		actionFrom.attr("action",oldAction+"/save").submit();
	}
</script> 
</head>  
<c:set target="${pagefunc}" property="name" value="年度绩效评价" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/mangesc" />  

<c:set target="${pagetitle}" property="name" value="部门员工年度绩效评价" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/mangesc/person/${checkPerson.id}" />

<c:set var="pagesize" value="768" scope="request"/>
<body>


<div class="headdiv" >
<div class="headleft"  >
考核年份:${year}&nbsp;&nbsp;员工:${checkPerson.name}
</div>
<div class="headright" >
</div>
<div  class="headnone"></div>
</div>
<%@ include file="../../common/message.jsp"%>


<form action="/${ctx}/mangesc/person/${checkPerson.id}" method="post">
<input name="session_token" type="hidden" value="${session_token}"/>
<input name="year" type="hidden" value="${year}"/>

<table border=1>
<tr>
	<td>考核要素</td><td>合计：${sumall}</td><td>得分</td><td>考核内容</td><td>必选</td><td>评价</td>
</tr>
<c:forEach items="${chkmangeTabs}" var="item" varStatus="status">
	<tr style="height:30px;">
	<c:if test="${item.special=='Y'}">
		<td rowspan="${item.rownum}">${item.type}</td>
		<td rowspan="${item.rownum}">${item.typesum}</td>
	</c:if>
	<td>${item.chkitem.point}</td>
	<td>${item.chkitem.content}</td>
	<td>
	<input type="checkbox" class="flagchk"  <c:if test="${item.ischeck}">checked</c:if> <c:if test="${item.chkitem.must}">disabled</c:if> />
	<input type="hidden" name="checkItems[${status.index}].flag" value="${item.ischeck}">
	</td>
	<td>
		<select class="select_val" name="checkItems[${status.index}].val" <c:if test="${!item.ischeck}">style="display:none"</c:if> >
			<option value="5" <c:if test="${item.chkmange.val=='5'}">selected="true"</c:if> >优秀</option>
			<option value="4" <c:if test="${item.chkmange.val=='4'}">selected="true"</c:if> >良好</option>
			<option value="3" <c:if test="${item.chkmange.val=='3'}">selected="true"</c:if> >尽职</option>
			<option value="2" <c:if test="${item.chkmange.val=='2'}">selected="true"</c:if> >需改进</option>
			<option value="1" <c:if test="${item.chkmange.val=='1'}">selected="true"</c:if> >差</option>
		  </select>
		  <input type="hidden" name="checkItems[${status.index}].id" value="${item.chkmange.id}">
		  <input type="hidden" name="checkItems[${status.index}].itemid" value="${item.chkitem.id}">
	</td>
	</tr>
</c:forEach>
</table>
<br>
<input type="button" value="保存" onclick="save()"/>
<input type="button" value="<<返回"  onclick="javascript:window.open('/pa/mangesc','_self')"/>
</form>
</body>
</html>