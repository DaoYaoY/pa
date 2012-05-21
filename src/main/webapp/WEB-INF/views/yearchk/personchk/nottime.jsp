<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
<%@ include file="../../common/head.jsp"%>
<script type="text/javascript">
	function saveAllChecks(){
		var actionFrom=$("form");
		var oldAction=actionFrom.attr("action");
		actionFrom.attr("action",oldAction+"/saveAllChecks").submit();
	}
</script>
</head>
<c:set target="${pagefunc}" property="name" value="年终员工考核" />
<c:set target="${pagefunc}" property="url" value="/${ctx}/person/${person.id}/yearchk" />  

<c:set target="${pagetitle}" property="name" value="年终员工考核列表" /> 
<c:set target="${pagetitle}" property="url" value="/${ctx}/person/${person.id}/yearchk" /> 

<c:set var="pagesize" value="825" scope="request"/> 
<body>

<div class="headdiv" >
<div class="headleft"  >
&nbsp;
</div>
<div class="headright" >
&nbsp;
</div>
<div  class="headnone"></div>
</div>
<%@ include file="../../common/message.jsp"%>
</body>
</html>
