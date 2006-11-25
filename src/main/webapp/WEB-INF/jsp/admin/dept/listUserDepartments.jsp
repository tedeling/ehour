<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table CLASS="contentTable" CELLSPACING=2>
	<tr>
		<td><fmt:message key="general.department" /></td>
		<td>&nbsp;</td>
		<td><fmt:message key="admin.dept.users" /></td>
		<td>&nbsp;</td>
		<td colspan=2></td>
	</tr>

	<tr>
		<td colspan="5"><img src="<c:url  value="/img/eh_pixel.gif" />"
			alt="pix" height="1" width="100%"><br>
		</td>
	</tr>
	
	<c:forEach items="${userDepartments}" var="userDept">
		<tr>
			<td><c:out value="${userDept.name}" /></td>
			<td>&nbsp;</td>
			<td><c:out value="${fn:length(userDept.users)}" /></td>
			<td><c:if test="${fn:length(userDept.users) == 0}"><fmt:message key="general.delete" /></c:if></td>
			<td>&nbsp;</td>			
			<td><a href=""
				onClick="return editDepartment(<c:out value="${userDept.departmentId}" />)"><fmt:message key="general.edit" /></a></td>
		</tr>
	</c:forEach>
</table>
	