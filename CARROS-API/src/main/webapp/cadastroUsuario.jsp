<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/includes/header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">

    <div class="card shadow-lg p-4" style="width: 100%; max-width: 450px;">

        <h2 class="text-center mb-4">Cadastro de Usuário</h2>

        <form action="${pageContext.request.contextPath}/usuario" method="post">


            <div class="mb-3">
                <label class="form-label">Nome de Usuário</label>
                <input type="text" class="form-control"
                       name="username" value="${usuario.username}" placeholder="Seu Username" required>
            </div>


            <div class="mb-3">
                <label class="form-label">Senha</label>
                <input type="password" class="form-control" name="senha" placeholder="Sua Senha" required>
            </div>


            <div class="mb-3">
                <label class="form-label">Tipo de Usuário</label>
                <select name="tipo" class="form-control" required>
                    <option value="">Selecione</option>

                    <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                        <option value="ADMIN">ADMIN</option>
                    </c:if>

                    <option value="COMUM">COMUM</option>
                </select>
            </div>


            <c:if test="${not empty erros}">
                <div class="alert alert-danger">
                    <ul class="mb-0">
                        <c:forEach var="erro" items="${erros}">
                            <li>${erro}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>


            <div class="d-grid gap-2 mt-3">
                <button type="submit" class="btn btn-primary">Cadastrar</button>
                <a href="login.jsp" class="btn btn-outline-secondary">Voltar</a>
            </div>

        </form>

    </div>

</div>

<c:import url="/includes/footer.jsp" />