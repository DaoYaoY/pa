<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<head>
<%@ include file="common/head.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#btn_out").click(function(){
			$('<form/>',{action:'/${ctx}/qs/logout',method:'post'})
 		 	.appendTo($("body"))
 		 	.submit();
		});
	});
</script>
</head>
<body>
<div style="margin-left:20px;margin-top: 50px;">
<%@ include file="common/message_nohide.jsp"%>
<div>
		调查问卷已经完成，感谢你的参与！
</div>

<div style="width: 600px;text-align: right;margin-top: 10px;">
	<input type="button" id="btn_out" value="安全退出">
</div>

</div>
</body>
</html>