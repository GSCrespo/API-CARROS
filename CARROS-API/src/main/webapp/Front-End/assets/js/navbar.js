(function () {
    const usuario = JSON.parse(sessionStorage.getItem("usuario") || "null");

    const navLinks = document.getElementById("nav-links");
    const navActions = document.getElementById("nav-actions");
    const formBusca = document.getElementById("form-busca");

    if (navLinks) {
        if (usuario) {
            navLinks.innerHTML = `
                <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="sobre.html">Sobre o Sistema</a></li>
            `;
        } else {
            navLinks.innerHTML = `
                <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="sobre.html">Sobre o Sistema</a></li>
            `;
        }
    }

    if (navActions) {
        if (usuario) {
            navActions.innerHTML = `
                <a class="btn btn-success btn-sm" href="cadastrar.html">Adicionar Carro</a>
                <a class="btn btn-warning btn-sm" href="listar.html">Meus Carros</a>
                <button class="btn btn-outline-light btn-sm" id="btn-logout">Logout</button>
            `;

            document.getElementById("btn-logout")?.addEventListener("click", async () => {
                await fetch("http://localhost:8080/logout", { method: "POST" });
                sessionStorage.removeItem("usuario");
                window.location.href = "login.html";
            });
        } else {
            navActions.innerHTML = `
                <a class="btn btn-outline-light btn-sm" href="login.html">Login</a>
            `;
        }
    }

    if (formBusca) {
        formBusca.addEventListener("submit", function (e) {
            e.preventDefault();
            const termo = document.getElementById("input-busca").value.trim();
            if (termo) {
                window.location.href = `listar.html?busca=${encodeURIComponent(termo)}`;
            }
        });
    }

    // Marca link ativo
    document.querySelectorAll(".nav-link").forEach(link => {
        if (link.href === window.location.href) {
            link.classList.add("active");
        }
    });
})();