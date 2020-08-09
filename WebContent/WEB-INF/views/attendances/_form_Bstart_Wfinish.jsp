<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="work_date">出勤日</label><br />
<input type="date" name="work_date" value="<fmt:formatDate value='${attendance.work_date}' pattern='yyyy-MM-dd' />"/>
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_employee.employeeName}" />
<br /><br />

<label for="start_time">出勤時刻</label><br />
<input type="time" name="start_time" value="<fmt:formatDate value='${attendance.start_time}' pattern='HH:mm' />"/>
<br /><br />

<p><a href="<c:url value='/breaks/new?work_date=${attendance.work_date}' />">休憩開始</a></p>
<br /><br />

<label for="finish_time">退勤時刻</label><br />
<input type="time" name="finish_time" value="<fmt:formatDate value='${attendance.finish_time}' pattern='HH:mm' />"/>
<br /><br />

<label for="content">備考</label><br />
<textarea name="content" rows="5" cols="50">${attendance.content}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>