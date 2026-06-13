<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/includes/header.jsp" />

<div class="container flex-grow-1">

    <h2 class="mb-3">Lançamentos</h2>

    <div id="carouselCarros" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">

            <c:forEach var="carro" items="${lancamentos}" varStatus="status">
                <div class="carousel-item ${status.index == 0 ? 'active' : ''}">

                    <div class="d-flex justify-content-center">
                        <div class="card" style="width: 20rem;">

                            <img src="${pageContext.request.contextPath}/uploads/${carro.imagem}" width="150 "  class="card-img-top" alt="10">

                            <div class="card-body">
                                <h5 class="card-title">
                                    ${carro.marca} ${carro.modelo}
                                </h5>
                                <p class="card-text">
                                    Ano: ${carro.ano} <br>
                                    Cor: ${carro.cor} <br>
                                    Combustível: ${carro.combustivel} <br>
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
                                                Valor: <fmt:formatNumber value="${carro.valor}" type="currency"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <br>
                                            Avaliação: <fmt:formatNumber value="${carro.mediaAvaliacao}" maxFractionDigits="1"/>
                                </p>
                                <a href="${pageContext.request.contextPath}/carro?action=detalhes&id=${carro.id}"
                                       class="btn btn-primary">
                                        Ver mais
                                    </a>
                            </div>
                        </div>
                    </div>

                </div>
            </c:forEach>

        </div>

        <button class="carousel-control-prev" type="button" data-bs-target="#carouselCarros" data-bs-slide="prev">
            <span class="carousel-control-prev-icon bg-dark rounded"></span>
        </button>

        <button class="carousel-control-next" type="button" data-bs-target="#carouselCarros" data-bs-slide="next">
            <span class="carousel-control-next-icon bg-dark rounded"></span>
        </button>

    </div>

</div>


<br>

<div class="container mt-5">

     <h2 class="mb-4">Todos os carros</h2>

     <div class="row">

         <c:forEach var="carro" items="${listaCarros}">
             <div class="col-md-4 mb-4">

                 <div class="card h-100 shadow-sm" style="width: 15rem;">
                 <img src="${pageContext.request.contextPath}/uploads/${carro.imagem}" width="150 "  class="card-img-top" alt="10">
                     <div class="card-body">
                         <h5 class="card-title">
                             ${carro.marca} ${carro.modelo}
                         </h5>

                         <p class="card-text">
                             <strong>Ano:</strong> ${carro.ano} <br>
                             <strong>Cor:</strong> ${carro.cor} <br>
                             <strong>Valor:</strong>
                             <c:choose>
                                 <c:when test="${carro.emPromocao}">

                                     <div>
                                         <span style="text-decoration: line-through; color: gray;">
                                             <fmt:formatNumber value="${carro.valor}" type="currency"/>
                                         </span>
                                     </div>

                                     <div>
                                         <strong class="text-success">
                                             <fmt:formatNumber value="${carro.valorPromocional}" type="currency"/>
                                         </strong>
                                     </div>

                                     <span class="badge bg-danger">Promoção</span>

                                 </c:when>

                                 <c:otherwise>
                                     <fmt:formatNumber value="${carro.valor}" type="currency"/>
                                 </c:otherwise>
                             </c:choose>
                             <strong>Avaliação:</strong> <fmt:formatNumber value="${carro.mediaAvaliacao}" maxFractionDigits="1"/>
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
