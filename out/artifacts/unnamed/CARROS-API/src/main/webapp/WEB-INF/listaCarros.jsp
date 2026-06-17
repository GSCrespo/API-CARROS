<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/includes/header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


<div class="container flex-grow-1 mt-4">

    <h2 class="text-center mb-4">Lista de Carros</h2>

    <div class="row">

        <c:forEach var="carro" items="${listaCarros}">
            <div class="col-md-4 col-sm-6 mb-4">

                <div class="card h-100 shadow-sm">

                    <img src="${pageContext.request.contextPath}/uploads/${carro.imagem}"
                         class="card-img-top" style="height: 180px; object-fit: cover;">

                    <div class="card-body">
                        <h5>${carro.marca} ${carro.modelo}</h5>

                        <p>
                            <strong>Ano:</strong> ${carro.ano} <br>
                            <strong>Cor:</strong> ${carro.cor} <br>
                            <c:choose>
                                <c:when test="${carro.emPromocao}">

                                    <span class="badge bg-danger">Promoção</span><br>

                                    <span style="text-decoration: line-through; color: gray;">
                                        <fmt:formatNumber value="${carro.valor}" type="currency"/>
                                    </span><br>

                                    <strong class="text-success">
                                        <fmt:formatNumber value="${carro.valorPromocional}" type="currency"/>
                                    </strong>

                                </c:when>

                                <c:otherwise>
                                    <strong>
                                        <fmt:formatNumber value="${carro.valor}" type="currency"/>
                                    </strong>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="card-footer d-flex justify-content-between">

                        <a href="carro?action=detalhes&id=${carro.id}"
                           class="btn btn-primary btn-sm">
                            Ver
                        </a>

                        <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                            <a href="carro?action=editar&id=${carro.id}"
                               class="btn btn-warning btn-sm">
                                Editar
                            </a>
                        </c:if>

                        <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                            <a href="carro?action=excluir&id=${carro.id}"
                            class="btn btn-danger btn-sm" onclick="return confirm('Certeza que deseja excluir esse item?')" >
                                Excluir
                            </a>
                        </c:if>


                        <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">

                            <c:choose>

                                <c:when test="${carro.emPromocao}">
                                    <a href="carro?action=removerPromocao&id=${carro.id}"
                                       class="btn btn-secondary btn-sm">
                                        Remover Promoção
                                    </a>
                                </c:when>

                                <c:otherwise>
                                    <a href="carro?action=promocao&id=${carro.id}"
                                       class="btn btn-danger btn-sm">
                                        Promoção
                                    </a>
                                </c:otherwise>

                            </c:choose>

                        </c:if>


                    </div>

                </div>

            </div>
        </c:forEach>

    </div>
</div>

<c:import url="/includes/footer.jsp" />