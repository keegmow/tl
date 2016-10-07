<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.keegmow.tasklist.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task List</title>
</head>
<body>
<h1>To-Do List</h1>

<p>Views:</p>
<a href="<c:url value="/" />">All Tasks</a><br>
<a href="<c:url value="/?view=active" />">Only Active Tasks</a><br>
<a href="<c:url value="/?view=completed" />">Only Completed Tasks</a><br>


	<c:if test="${param.view == 'active' || param.view == '' || param.view == null}">
		<form name="addtask" method="POST" action="addTask">
			<h4>Add New Task:</h4>	
			<label class="fieldLabel">Description:
				<input type="text" name="description" size="50" placeholder="" required autofocus/>
			</label>
				<input type="submit" value="Add new task"/>
		</form>
		
		<h3>Active Tasks</h3>
			<c:choose>
				<c:when test="${active.size() > 0}">
					<h5>Task Count: <c:out value="${active.size()}"></c:out></h5>
						<table>
							<c:forEach var="task" items="${active}">
								<tr>
									<td><c:out value="${task.getTaskDesc()}"></c:out></td>
									<td><a href="<c:url value="/toggle?id=${task.getId()}&view=${param.view}" />">Completed</a></td>
									<td><a href="<c:url value="/delete?id=${task.getId()}&view=${param.view}" />">Delete</a></td>
								</tr>
							</c:forEach>
						</table>
				</c:when>
				<c:otherwise>You do not have any active tasks.</c:otherwise>	
			</c:choose>
	</c:if>
	<c:if test="${param.view == 'completed' || param.view == '' || param.view == null}">		
		<h3>Completed Tasks</h3>
			<c:choose>
				<c:when test="${complete.size() > 0}">
					<table>
						<c:forEach var="task" items="${complete}">
							<tr>
								<td><s><i><c:out value="${task.getTaskDesc()}"></c:out></i></s></td>
								<td><a href="<c:url value="/toggle?id=${task.getId()}&view=${param.view}" />">Reactivate</a></td>
								<td><a href="<c:url value="/delete?id=${task.getId()}&view=${param.view}" />">Delete</a></td>
							</tr>
						</c:forEach>
					</table>
				<br>
					<form:form name="deleteComplete" method="POST" action="deleteComplete">
						<input type="submit" value="Delete All Completed" />
					</form:form>
				</c:when>
				<c:otherwise><i>You do not have any completed tasks.</i></c:otherwise>
			</c:choose>
	</c:if>
</body>
</html>