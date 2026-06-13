<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/includes/header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container flex-grow-1 mt-4">

    <div class="card shadow-lg">
        <div class="row g-0">

            <div class="col-md-7">
                <div class="card-body">

                    <h2 class="card-title mb-3">
                        ${carro.marca} ${carro.modelo}
                    </h2>

                    <h4 class="text-success mb-4">
                        <fmt:formatNumber value="${carro.valor}" type="currency"/>
                    </h4>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p><strong>Ano:</strong> ${carro.ano}</p>
                            <p><strong>Cor:</strong> ${carro.cor}</p>
                        </div>

                        <div class="col-md-6">
                            <p><strong>Combustível:</strong> ${carro.combustivel}</p>
                            <p><strong>Km: </strong><fmt:formatNumber value="${carro.quilometragem}" type="number"/></p>
                        </div>

                        <div class="col-md-6">
                            <p><strong>Avaliação: <fmt:formatNumber value="${carro.mediaAvaliacao}" maxFractionDigits="1"/> </p>
                        </div>
                    </div>

                    <hr>

                    <p class="card-text">
                        ${carro.descricao}
                    </p>

                    <div class="mt-4 d-flex gap-2">

                        <a href="${pageContext.request.contextPath}/carro?action=home"
                           class="btn btn-secondary">
                            Voltar
                        </a>

                        <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/carro?action=editar&id=${carro.id}"
                               class="btn btn-warning">
                                Editar
                            </a>
                        </c:if>

                        <c:if test="${usuarioLogado != null}">
                        <a class="btn btn-success" href="avaliacao.jsp?id=${carro.id}">
                            Avaliar
                        </a>
                        </c:if>

                    </div>

                </div>
            </div>

        </div>
    </div>

</div>

<c:import url="/includes/footer.jsp" />