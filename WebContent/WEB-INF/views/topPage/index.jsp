<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>勤怠管理システムへようこそ</h2>
        <h3>【自分の勤怠　一覧】</h3>
        <table id="attendance_list">
            <tbody>
                <tr>
                    <th class="attendance_name">氏名</th>
                    <th class="work_date">出勤日</th>
                    <th class="start_time">出勤時刻</th>
                    <th class="finish_time">退勤時刻</th>
                    <th class="break_time">休憩時間</th>
                    <th class="working_hours">勤務時間</th>
                    <th class="attendance_action">操作</th>
                </tr>
                <c:forEach var="attendance" items="${attendances}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="attendance_name"><c:out value="${attendance.employee.employeeName}" /></td>
                        <td class="work_date"><fmt:formatDate value='${attendance.work_date}' pattern='yyyy-MM-dd(E)' /></td>
                        <td class="start_time"><fmt:formatDate value='${attendance.start_time}' pattern='HH:mm'/></td>
                        <td class="finish_time"><fmt:formatDate value='${attendance.finish_time}' pattern='HH:mm' /></td>
                        <td class="break_time"><fmt:formatDate value='${attendance.break_time}' pattern='HH:mm' /></td>
                        <td class="working_hours"><fmt:formatDate value='${attendance.working_hours}' pattern='HH:mm' /></td>
                        <td class="attendance_action"><a href="<c:url value='/attendances/show?id=${attendance.id}' />">詳細</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${attendances_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((attendances_count - 1) / 20) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/attendances/new' />">出勤登録</a></p>
    </c:param>
</c:import>