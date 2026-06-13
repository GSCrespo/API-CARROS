<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:import url="/includes/header.jsp" />

<div class="container mt-4">

    <h2>Avaliar Carro</h2>

    <form action="${pageContext.request.contextPath}/avaliacao" method="post">

        <input type="hidden" name="id" value="${param.id}">

        <div class="mb-3">
            <label class="form-label">Nota (0 a 5)</label>
            <input type="number" name="avaliacao" step="0.1" min="0" max="5" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Avaliar</button>

    </form>

</div>

<c:import url="/includes/footer.jsp" />