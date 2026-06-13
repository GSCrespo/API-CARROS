<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/includes/header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container mt-5">

    <h2 class="text-center mb-4">Meu Perfil</h2>

    <c:if test="${empty usuarioLogado}">
        <div class="alert alert-danger text-center">
            Você precisa estar logado para acessar esta página.
        </div>
    </c:if>

    <c:if test="${not empty usuarioLogado}">
        <div class="card shadow p-4">

            <!-- Nome -->
            <div class="mb-3">
                <label class="form-label"><strong>Nome:</strong></label>
                <p class="form-control">${usuarioLogado.userName}</p>
            </div>

            <!-- Senha -->
            <div class="mb-3">
                <label class="form-label"><strong>Senha:</strong></label>
                <p class="form-control">********</p>
            </div>

            <!-- Tipo -->
            <div class="mb-3">
                <label class="form-label"><strong>Tipo de Usuário:</strong></label>
                <p class="form-control">${usuarioLogado.tipo}</p>
            </div>

            <!-- Botões -->
            <div class="d-flex justify-content-between mt-4">
                <a href="${pageContext.request.contextPath}/carro?action=home" class="btn btn-secondary">Voltar</a>

                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/Sair" onclick="return confirm('tem certeza que deseja ser desconectado da conta?')" >
                        Sair
                    </a>
            </div>

        </div>
    </c:if>

</div>

<c:import url="/includes/footer.jsp" />