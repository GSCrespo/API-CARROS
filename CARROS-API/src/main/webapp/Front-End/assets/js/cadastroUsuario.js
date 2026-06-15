const BASE_URL = "";
const USE_MOCK = true;

// Se o usuário logado for ADMIN, adiciona a opção ADMIN no select
const usuario = JSON.parse(sessionStorage.getItem("usuario") || "null");
if (usuario && usuario.tipo === "ADMIN") {
    const select = document.getElementById("tipo");
    const optionAdmin = document.createElement("option");
    optionAdmin.value = "ADMIN";
    optionAdmin.textContent = "ADMIN";
    select.appendChild(optionAdmin);
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

function validarCampos(dados) {
    const erros = [];
    if (!dados.username) erros.push("Preencha o nome de usuário.");
    if (!dados.senha)    erros.push("Preencha a senha.");
    if (!dados.tipo)     erros.push("Selecione o tipo de usuário.");
    return erros;
}

document.getElementById("btn-cadastrar").addEventListener("click", async () => {
    const dados = {
        username: document.getElementById("username").value.trim(),
        senha:    document.getElementById("senha").value.trim(),
        tipo:     document.getElementById("tipo").value
    };

    const erros = validarCampos(dados);
    if (erros.length > 0) {
        mostrarErros(erros);
        return;
    }

    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        mostrarSucesso("Usuário cadastrado com sucesso! (mock)");
        return;
    }

    try {
        const res = await fetch(`${BASE_URL}/cadastrarUsuario`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
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
            mostrarSucesso(data.mensagem || "Usuário cadastrado com sucesso!");
        }

    } catch (err) {
        mostrarErros(["Erro ao conectar com o servidor."]);
        console.error(err);
    }
});