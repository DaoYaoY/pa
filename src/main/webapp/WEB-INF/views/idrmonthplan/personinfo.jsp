<%@ page language="java" pageEncoding="UTF-8"%>

部门经理:${person.name}&nbsp;&nbsp;部门:${idrMonthPlanBill.department.name}&nbsp;&nbsp;<br>
计划周期:${idrMonthPlanBill.year}年${idrMonthPlanBill.month}月&nbsp;&nbsp;考核状态:${idrMonthPlanBill.state.name}
<input type="button" value="历史计划>>" onclick="javascript:window.open('/${ctx}/mange/${person.id}/idrmonthplan/history','_self')"/>
<input type="button" value="修改密码>>" onclick="javascript:window.open('/${ctx}/common/settings/person/${person.id}/password?backurl=/${ctx}/mange/${person.id}/idrmonthplan','_self')"/>
