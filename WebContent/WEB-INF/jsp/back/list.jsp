<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
		<title>内容列表页面</title>
		<link href="<%=basePath %>resources/css/all.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>resources/js/common/jquery-1.8.0.min.js"></script>
		<script type="text/javascript">
			$(function(){
				//点击全选按钮
				$("#checkAll").click(function(){
					if(this.checked){
						$("input[name=checkBtn]").prop("checked",true);
					}else{
						$("input[name=checkBtn]").prop("checked",false);
					}
					
				});
				//点击所有条目，全选
				$("input[name=checkBtn]").click(function(){
					var all = $("input[name=checkBtn]").length;
					var aa =  $("input[name=checkBtn]:checked").length;
					if(all==aa){
						$("#checkAll").prop("checked",true);
					}else{
						$("#checkAll").prop("checked",false);
					}
				});
			});
			function deleteOne(id){//使用AJAX实现某条记录的删除
				if(window.confirm("你确定删除么")){
					$.ajax({
						type:'post',
						url:"<%=basePath%>messageServlet?method=deleteOne",
						data:{id:id},
						success:function(data){
							if(data=="OK"){
								alert("删除成功！");    
					            window.location.reload();    
							}else{
								alert("删除失败！");    
							}
							
						},
						error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
					});
				}
			}
			function deleteBatch(){
				var selectSpan = $("input[name=checkBtn]:checked");
				if(selectSpan.length==0){
					alert("请至少选择一条记录");
					return false;
				}
				var ids = "";
				selectSpan.each(function(){
					ids = ids + $(this).val() + ",";
				});
				$.ajax({
					type:'post',
					url:"<%=basePath%>messageServlet?method=deleteBatch",
					data:{ids:ids},
					success:function(data){
						if(data=="OK"){
							alert("删除成功！");    
				            window.location.reload();    
						}else{
							alert("删除失败！");    
						}
					},
					error : function() {    
				          // view("异常！");    
				          alert("异常！");    
				     }    
				});
			}
		</script>
	</head>
	<body style="background: #e1e9eb;">
		<form action="<%=basePath %>messageServlet?method=list" id="mainForm" method="post">
			<div class="right">
				<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">内容管理</a> &gt; 内容列表</div>
				<div class="rightCont">
					<p class="g_title fix">内容列表
						<a class="btn03" href="#">新 增</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="btn03" href="javascript:" onclick="deleteBatch();">删 除</a>
					</p>
					<table class="tab1">
						<tbody>
							<tr>
								<td width="90" align="right">指令名称：</td>
								<td>
									<input type="text" name="command" class="allInput" value="${command}"/>
								</td>
								<td width="90"  align="right">描述：</td>
								<td>
									<input type="text" name="description" class="allInput" value="${description}"/>
								</td>
	                            <td width="85" align="right"><input type="submit" class="tabSub" value="查 询" /></td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tbody>
								<tr>
								    <th><input type="checkbox"  id="checkAll"/></th>
								    <th>序号</th>
								    <th>指令名称</th>
								    <th>描述</th>
								    <th>操作</th>
								</tr>
								<c:forEach items="${messageList }" var="message" varStatus="status">
									<tr <c:if test="${status.index%2==1 }">style="background-color:#ECF6EE;"</c:if>>
										<td><input type="checkbox" name="checkBtn" value="${message.id }" id="${message.id }"/></td>
										<td>${message.id }</td>
										<td>${message.command }</td>
										<td>${message.description }</td>
										<td>
											<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
											<a href="javascript:" onclick="deleteOne(${message.id})">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class='page fix'>
							共 <b>4</b> 条
							<a href='###' class='first'>首页</a>
							<a href='###' class='pre'>上一页</a>
							当前第<span>1/1</span>页
							<a href='###' class='next'>下一页</a>
							<a href='###' class='last'>末页</a>
							跳至&nbsp;<input type='text' value='1' class='allInput w28' />&nbsp;页&nbsp;
							<a href='###' class='go'>GO</a>
						</div>
					</div>
				</div>
			</div>
	    </form>
	</body>
</html>