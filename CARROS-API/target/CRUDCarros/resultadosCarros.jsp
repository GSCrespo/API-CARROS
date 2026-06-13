<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/includes/header.jsp" />

<div class="container flex-grow-1 mt-4">

    <h3>Resultados para: "${termoBusca}"</h3>

    <c:choose>
        <c:when test="${empty resultados}">
            <div class="alert alert-warning mt-3">
                Nenhum carro encontrado.
            </div>
        </c:when>

        <c:otherwise>
            <div class="row mt-3">

                <c:forEach var="carro" items="${resultados}">
                    <div class="col-md-4 mb-4">

                        <div class="card h-100 shadow-sm">

                            <c:choose>
                                <c:when test="${not empty carro.imagem}">
                                    <img src="${pageContext.request.contextPath}/uploads/${carro.imagem}"
                                         class="card-img-top">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://via.placeholder.com/300x200"
                                         class="card-img-top">
                                </c:otherwise>
                            </c:choose>

                            <div class="card-body">
                                <h5>${carro.marca} ${carro.modelo}</h5>

                                <p>
                                    Ano: ${carro.ano}<br>
                                    Valor: R$ ${carro.valor}
                                </p>
                            </div>

                            <div class="card-footer d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/carro?action=detalhes&id=${carro.id}"
                                   class="btn btn-primary btn-sm">
                                    Ver mais
                                </a>

                                <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                                    <a href="${pageContext.request.contextPath}/carro?action=editar&id=${carro.id}"
                                       class="btn btn-warning btn-sm">
                                        Editar
                                    </a>
                                </c:if>
                            </div>

                        </div>

                    </div>
                </c:forEach>

            </div>
        </c:otherwise>
    </c:choose>

</div>

<c:import url="/includes/footer.jsp" />
