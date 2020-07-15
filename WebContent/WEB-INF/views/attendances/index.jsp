<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>勤怠　一覧</h2>
        <table id="attendance_list">
            <tbody>
                <tr>
                    <th class="attendance_name">氏名</th>
                    <th class="work_date">出勤日</th>
                    <th class="start_time">出勤時刻</th>
                    <th class="break_start_time">休憩開始時刻</th>
                    <th class="break_finish_time">休憩終了時刻</th>
                    <th class="finish_time">退勤時刻</th>
                    <th class="attendance_action">操作</th>
                </tr>
                <c:forEach var="attendance" items="${attendances}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="attendance_name"><c:out value="${attendance.employee.employeeName}" /></td>
                        <td class="work_date"><fmt:formatDate value='${attendance.work_date}' pattern='yyyy-MM-dd(E)' /></td>
                        <td class="start_time"><fmt:formatDate value='${attendance.start_time}' /></td>
                        <td class="break_start_time"><fmt:formatDate value='${attendance.break_start_time}' pattern='HH:mm' /></td>
                        <td class="break_finish_time"><fmt:formatDate value='${attendance.break_finish_time}' pattern='HH:mm' /></td>
                        <td class="finish_time"><fmt:formatDate value='${attendance.finish_time}' pattern='HH:mm' /></td>
                        <td class="attendance_action"><a href="<c:url value='/attendances/show?id=${attendance.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${attendances_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((attendances_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/attendances/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/attendances/new' />">新規勤怠の登録</a></p>

    </c:param>
</c:import>