<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <style>
        body {
            background-image: url('${pageContext.request.contextPath}/assets/showroom.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }
        </style>
        <style>
        body::before {
            content: "";
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.45);
            z-index: -1;
        }
        </style>
        <style>
        .container {
            position: relative;
            z-index: 1;
        }
        </style>
        <style>
        h1, h2, h3 {
            color: #f1f1f1;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.8);
        }
        </style>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <div class="container flex-grow-1">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container-fluid">


                    <a class="navbar-brand" href="${pageContext.request.contextPath}/carro?action=home">
                        CRUD Carros
                    </a>

                    <!-- button pro mobile -->
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>


                    <div class="collapse navbar-collapse" id="navbarNav">

                        <!-- Menu esquerdo -->
                        <ul class="navbar-nav me-auto">
                            <li class="nav-item">
                                <a class="nav-link active" href="${pageContext.request.contextPath}/carro?action=home">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#sobre">Sobre</a>
                            </li>

                            <li class="nav-item">
                                <c:choose>
                                    <c:when test="${usuarioLogado == null}">
                                        <a class="nav-link" href="login.jsp">Login</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="nav-link" href="${pageContext.request.contextPath}/perfil.jsp">
                                            Perfil (${usuarioLogado.userName})
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>


                        <div class="d-flex gap-2 me-3">
                            <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'ADMIN'}">
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/carro?action=novo">
                                    Cadastrar Carro
                                </a>

                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/cadastroUsuario.jsp">
                                    Cadastrar Usuário
                                </a>

                                <a class="btn btn-warning" href="${pageContext.request.contextPath}/carro?action=listar">
                                    Listar Carros
                                </a>
                            </c:if>
                        </div>


                        <form class="d-flex" method="get" action="${pageContext.request.contextPath}/carro">
                            <input type="hidden" name="action" value="buscar">
                            <input class="form-control me-2" type="search" placeholder="Buscar carro..." name="termoBusca">

                            <button class="btn btn-outline-light" type="submit">Buscar</button>
                        </form>

                    </div>
                </div>
            </nav>
        </div>