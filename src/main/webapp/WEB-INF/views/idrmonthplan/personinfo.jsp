<%@ page language="java" pageEncoding="UTF-8"%>

<div class="headdiv" >
<div class="headleft" style="width: 60%">
	部门:${idrMonthPlanBill.department.name}&nbsp;&nbsp;计划周期:${idrMonthPlanBill.year}年${idrMonthPlanBill.month}月&nbsp;&nbsp;考核状态:${idrMonthPlanBill.state.name}
</div>
<div class="headright"  style="width: 38%">
	<input type="button" value="历史计划>>" onclick="javascript:window.open('/${ctx}/mange/${person.id}/idrmonthplan/history','_self')"/>
</div>
<div  class="headnone"></div>
</div>