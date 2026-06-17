const BASE_URL = "http://localhost:8080/CRUDCarros";
const USE_MOCK = false;

// Mostra aviso se veio de um redirecionamento 401
const params = new URLSearchParams(window.location.search);
if (params.get("expirado") === "true") {
    document.getElementById("msg-401").classList.remove("d-none");
}

document.getElementById("btn-login").addEventListener("click", async () => {
    const username = document.getElementById("username").value.trim();
    const senha = document.getElementById("senha").value.trim();
    const erroDiv = document.getElementById("erro");

    erroDiv.classList.add("d-none");

    if (!username || !senha) {
        erroDiv.textContent = "Preencha usuário e senha.";
        erroDiv.classList.remove("d-none");
        return;
    }

    if (USE_MOCK) {
        // Simula login: qualquer usuário com senha "123" entra como ADMIN
        await new Promise(r => setTimeout(r, 300));
        if (senha === "123") {
            const usuario = { userName: username, tipo: "ADMIN" };
            sessionStorage.setItem("usuario", JSON.stringify(usuario));
            window.location.href = "index.html";
        } else {
            erroDiv.textContent = "Usuário ou senha inválidos.";
            erroDiv.classList.remove("d-none");
        }
        return;
    }

    // --- Fetch real ---
    try {
        const res = await fetch(`${BASE_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, senha })
        });

        const data = await res.json();

        if (res.ok) {
            // Salva usuário no sessionStorage
            sessionStorage.setItem("usuario", JSON.stringify(data.usuario));
            window.location.href = "index.html";
        } else {
            erroDiv.textContent = data.mensagem || "Usuário ou senha inválidos.";
            erroDiv.classList.remove("d-none");
        }

    } catch (err) {
        erroDiv.textContent = "Erro ao conectar com o servidor.";
        erroDiv.classList.remove("d-none");
        console.error(err);
    }
});