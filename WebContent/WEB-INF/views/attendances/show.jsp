<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${attendance != null}">
                <h2>勤怠　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${attendance.employee.employeeName}" /></td>
                        </tr>
                        <tr>
                            <th>出勤日</th>
                            <td><fmt:formatDate value="${attendance.work_date}" pattern="yyyy-MM-dd(E)" /></td>
                        </tr>

                        <tr>
                            <th>出勤時刻</th>
                            <td><fmt:formatDate value="${attendance.start_time}" pattern='HH:mm:ss' /></td>
                        </tr>
                        <tr>

                            <th>退勤時刻</th>
                            <td><fmt:formatDate value="${attendance.finish_time}" pattern='HH:mm:ss' /></td>
                        </tr>
                        <tr>
                            <th>休憩時間</th>
                            <td><fmt:formatDate value="${attendance.break_time}" pattern='HH:mm:ss' /></td>
                        </tr>
                        <tr>
                            <th>勤務時間</th>
                            <td><fmt:formatDate value="${attendance.working_hours}" pattern='HH:mm:ss' /></td>
                        </tr>

                        <tr>
                            <th>備考</th>
                            <td>
                                <pre><c:out value="${attendance.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${attendance.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${attendance.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>承認者</th>
                            <td>
                                <c:out value="${attendance.approver}" />
                            </td>
                        </tr>
                        <tr>
                            <th>承認日</th>
                            <c:choose>
                                <c:when test="${attendance.approval_date == '1000-00-00'}">
                                    <td>&nbsp;</td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:formatDate value="${attendance.approval_date}" pattern="yyyy-MM-dd(E)" /></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_employee.id == attendance.employee.id}">
                    <p><a href="<c:url value="/attendances/edit?id=${attendance.id}" />">この勤怠を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/" />">自分の勤怠一覧に戻る</a></p>
    </c:param>
</c:import>