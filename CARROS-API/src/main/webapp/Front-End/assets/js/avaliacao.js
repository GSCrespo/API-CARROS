const BASE_URL = "";
const USE_MOCK = true;

// Protege a página: precisa estar logado
if (!sessionStorage.getItem("usuario")) {
    window.location.href = "login.html?expirado=true";
}

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

if (!id) {
    window.location.href = "index.html";
}

function mostrarErros(erros) {
    const div = document.getElementById("erros");
    const lista = document.getElementById("lista-erros");
    lista.innerHTML = erros.map(e => `<li>${e}</li>`).join("");
    div.classList.remove("d-none");
    document.getElementById("sucesso").classList.add("d-none");
}

function mostrarSucesso(mensagem) {
    const div = document.getElementById("sucesso");
    div.textContent = mensagem;
    div.classList.remove("d-none");
    document.getElementById("erros").classList.add("d-none");
}

document.getElementById("btn-avaliar").addEventListener("click", async () => {
    const nota = Number(document.getElementById("avaliacao").value);

    if (document.getElementById("avaliacao").value === "" || nota < 0 || nota > 5) {
        mostrarErros(["Informe uma nota válida entre 0 e 5."]);
        return;
    }

    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        mostrarSucesso("Avaliação enviada com sucesso! (mock)");
        return;
    }

    try {
        const res = await fetch(`${BASE_URL}/avaliacao`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: Number(id), avaliacao: nota })
        });

        const data = await res.json();

        if (res.status === 401) {
            sessionStorage.removeItem("usuario");
            window.location.href = "login.html?expirado=true";
            return;
        }

        if (res.status === 400) {
            mostrarErros(data.problemas || [data.mensagem]);
            return;
        }

        if (res.ok) {
            mostrarSucesso(data.mensagem || "Avaliação enviada com sucesso!");
        }

    } catch (err) {
        mostrarErros(["Erro ao conectar com o servidor."]);
        console.error(err);
    }
});
