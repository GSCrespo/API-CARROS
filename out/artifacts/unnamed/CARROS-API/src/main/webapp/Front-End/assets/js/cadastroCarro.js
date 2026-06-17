const BASE_URL = "";
const USE_MOCK = true;

function isAutenticado() {
    return !!sessionStorage.getItem("usuario");
}

// Redireciona se não estiver logado
if (!isAutenticado()) {
    window.location.href = "login.html?expirado=true";
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
    if (!dados.marca)          erros.push("Preencha a marca.");
    if (!dados.modelo)         erros.push("Preencha o modelo.");
    if (!dados.ano || dados.ano < 1886) erros.push("Informe um ano válido.");
    if (!dados.cor)            erros.push("Preencha a cor.");
    if (!dados.combustivel)    erros.push("Selecione o combustível.");
    if (!dados.transmissao)    erros.push("Selecione a transmissão.");
    if (dados.quilometragem < 0) erros.push("Quilometragem não pode ser negativa.");
    if (dados.valor <= 0)      erros.push("Informe um valor válido.");
    if (!dados.descricao)      erros.push("Preencha a descrição.");
    return erros;
}

document.getElementById("btn-cadastrar").addEventListener("click", async () => {
    const dados = {
        marca:          document.getElementById("marca").value.trim(),
        modelo:         document.getElementById("modelo").value.trim(),
        ano:            Number(document.getElementById("ano").value),
        cor:            document.getElementById("cor").value.trim(),
        combustivel:    document.getElementById("combustivel").value,
        transmissao:    document.getElementById("transmissao").value,
        quilometragem:  Number(document.getElementById("quilometragem").value),
        valor:          Number(document.getElementById("valor").value),
        descricao:      document.getElementById("descricao").value.trim()
    };

    // Validação no frontend
    const erros = validarCampos(dados);
    if (erros.length > 0) {
        mostrarErros(erros);
        return;
    }

    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        mostrarSucesso("Carro cadastrado com sucesso! (mock)");
        return;
    }

    // Fetch real
    try {
        const res = await fetch(`${BASE_URL}/cadastrar`, {
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
            // Erros vindos do backend
            mostrarErros(data.problemas || [data.mensagem]);
            return;
        }

        if (res.ok) {
            mostrarSucesso(data.mensagem || "Carro cadastrado com sucesso!");
        }

    } catch (err) {
        mostrarErros(["Erro ao conectar com o servidor."]);
        console.error(err);
    }
});