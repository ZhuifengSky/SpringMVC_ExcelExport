<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login1-User.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="js/fomvalidator4.0/jquery_last.js" type="text/javascript"></script>
<script defer="defer" src="js/fomvalidator4.0/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="js/fomvalidator4.0/formValidator.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/fomvalidator4.0/formValidatorRegex.js"
	type="text/javascript" charset="UTF-8"></script>
<script language="javascript" src="js/fomvalidator4.0/DateTimeMask.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function regester() {
		window.location.href = "index.jsp"
		
	}
	function editStudent(sid, op) {
		if (op == 'del') {
			if (window.confirm("确实要删除此条数据吗？")) {
				window.location = "./user/deleteUser.do?sid=" + sid
			}
		} else {
			window.location = "./user/queryUser.do?sid=" + sid
		}
	}
	function exportExcel(){
		var url = "user/exportExcel.do";
		 $.ajax({
	            //提交数据的类型 POST GET
	            type:"POST",
	            //提交的网址
	            url:url,
	            //提交的数据
	            data:{queryBean:"queryBean"},
	            //返回数据的格式
	            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
	            //在请求之前调用的函数
	            beforeSend:function(){
	            	/* $("#msg").html("logining"); */
	                alert("ajax请求前执行方法!");	
	            },
	            //成功返回之后调用的函数             
	            success:function(data){
	               /* $("#msg").html(decodeURI(data)); */
	               alert("成功了!");
	            },
	            //调用执行后调用的函数
	            complete: function(XMLHttpRequest, textStatus){
	               alert(XMLHttpRequest.responseText);
	               alert(textStatus);
	                //HideLoading();
	            },
	            //调用出错执行的函数
	            error: function(){
	                //请求出错处理
	                 alert("出错了!")
	            }         
	         });
	}

	function test(){
		window.location = "./user/exportExcel.do";
	}
	 function outputExcel() {
		 var queryForm = document.getElementById("queryForm");
		 queryForm.action="user/exportExcel.do";
		 queryForm.submit();			
		 queryForm.action="user/listUser.do";
	 } 
	function test2(){
		window.location = "./opExcel/exportPdf.do";
	}
	
</script>
</head>

<body>
	<form action="user/listUser.do" method="post" id="queryForm">
		姓名：<input type="text" name="studentName"
			value="${queryBean.studentName}">
			<input type="submit" value="查找">
			<input type="button" value="导出Excel" onclick="outputExcel()">
			<input type="button" value="导出PDF" onclick="test2()">
		<hr>
	</form>
		<table border="1px">
			<tr>
				<td>姓名</td>
				<td colspan="3">年龄</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${page.data}" var="t">
				<tr>
					<td>${t.studentName}</td>
					<td colspan="3">${t.age}</td>
					<td><a href="javascript:editStudent(${t.id},'edit')">编辑</a>&nbsp;&nbsp;&nbsp;<a
						href="javascript:editStudent(${t.id},'del')">删除</a></td>
				</tr>
			</c:forEach>
			<%--  <s:iterator value="#request.students" var="s">
  		 <tr>
  		 <td>${s.studentName}</td>
  			<td>${s.age}</td>
  			</tr>
  			</s:iterator> --%>
  			<tr><td> <input type="hidden"
			name="pageBean.pageSize" value="6"> <input type="hidden"
			name="pageBean.currentPage" value="0"> </td></tr>
			<tr>
			    <td><a href="./user/listUser.do?currentPage=1">首页</a></td>
				<td>第${pageBean.currentPage}页</td>
				<td><c:if test="${pageBean.hasPrePage eq  true}">
						<a href="./user/listUser.do?currentPage=${pageBean.currentPage-1}">上一页</a>
					</c:if>
					<c:if test="${pageBean.hasNextPage eq  true}">
						<a href="./user/listUser.do?currentPage=${pageBean.currentPage+1}">下一页</a>
					</c:if>
					</td>
				<td>共${pageBean.totalPage}页</td>
				<td><a href="./user/listUser.do?currentPage=${pageBean.totalPage}">尾页</a></td>
			</tr>
		</table>
	
</body>
</html>
