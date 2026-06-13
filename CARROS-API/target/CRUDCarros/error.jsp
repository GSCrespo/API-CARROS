<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/includes/header.jsp" />

<div class="container flex-grow-1 mt-5 text-center">

    <div class="card shadow-lg p-4">
            <h1 class="text-danger mb-3">Acesso Negado</h1>

            <p class="mb-4">
                Você não tem permissão para acessar esta página.
            </p>

            <c:if test="${not empty exception}">
                <p class="text-muted">
                    ${exception.message}
                </p>
            </c:if>

            <a href="${pageContext.request.contextPath}/carro?action=home"
               class="btn btn-primary mt-3">
                Voltar para Home
            </a>

        </div>

</div>


<c:import url="/includes/footer.jsp" />
