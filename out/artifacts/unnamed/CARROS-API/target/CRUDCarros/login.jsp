<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:import url="/includes/header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">

    <div class="card shadow-lg p-4" style="width: 100%; max-width: 400px;">

        <h2 class="text-center mb-4">Login</h2>

        <form action="login" method="post">

            <!-- Usuário -->
            <div class="mb-3">
                <label class="form-label">Usuário</label>
                <input type="text" name="username" class="form-control" required>
            </div>

            <!-- Senha -->
            <div class="mb-3">
                <label class="form-label">Senha</label>
                <input type="password" name="senha" class="form-control" required>
            </div>


            <c:if test="${not empty erro}">
                <div class="alert alert-danger text-center">
                        ${erro}
                </div>
            </c:if>


            <div class="d-grid gap-2 mt-3">
                <button type="submit" class="btn btn-success">Entrar</button>
                <a href="cadastroUsuario.jsp" class="btn btn-outline-primary">Cadastre-se</a>
            </div>

        </form>

    </div>

</div>

<c:import url="/includes/footer.jsp" />