<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${attendance != null}">
                <h2>勤怠　編集ページ</h2>
                <c:choose>
                    <c:when test="${sessionScope.break_id == 0}">
                        <form method="POST" action="<c:url value='/attendances/update' />">
                            <c:import url="_form_Bstart_Wfinish.jsp" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="POST" action="<c:url value='/attendances/update' />">
                            <c:import url="_form_Bfinish.jsp" />
                        </form>
                    </c:otherwise>
                </c:choose>

                <p><a href="#" onclick="confirmDestroy();">この勤怠管理を削除する</a></p>
                <form method="POST" action="<c:url value='/attendances/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/' />">自分の勤怠一覧に戻る</a></p>
    </c:param>
</c:import>