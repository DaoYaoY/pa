<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<%@ include file="../common/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		setTimeout(function(){$("#msg").slideToggle(1000);},3000);
	 });
</script>  
<body>
<h1>管理员页面</h1>
<c:if test="${msg!=null}">
 <div id="msg" style="background-color:red;width:300px">${msg}</div>
</c:if>
<input type="button" value="退出"  onclick="javascript:window.open('/pa','_self')"/><br><br>
<a href="/pa/person">用户管理</a><br>
<br>
<a href="/pa/rpt/point">考核报表</a><br>
</body>
</html>
