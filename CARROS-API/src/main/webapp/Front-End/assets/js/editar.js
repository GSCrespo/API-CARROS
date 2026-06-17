const BASE_URL = "http://localhost:8080/CRUDCarros";
const USE_MOCK = false;

const carrosMock = [
    { id: 1, marca: "Volkswagen", modelo: "Fusca",  ano: 1980, cor: "Vermelho",        combustivel: "Gasolina", quilometragem: 120000, transmissao: "Manual", valor: 11000, descricao: "Tanque de guerra raiz" },
    { id: 2, marca: "Volkswagen", modelo: "Kombi",  ano: 1995, cor: "Verde Samambaia",  combustivel: "Gasolina", quilometragem: 200000, transmissao: "Manual", valor: 19000, descricao: "Com folga no volante" },
    { id: 3, marca: "Chevrolet",  modelo: "Celta",  ano: 2012, cor: "Branco",           combustivel: "Flex",     quilometragem: 90000,  transmissao: "Manual", valor: 25000, descricao: "Barato, confiável e econômico" }
];

// Redireciona se não estiver logado
if (!sessionStorage.getItem("usuario")) {
    window.location.href = "login.html?expirado=true";
}

async function buscarCarro(id) {
    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        return carrosMock.find(c => c.id === Number(id)) || null;
    }
    const res = await fetch(`${BASE_URL}/detalhes?id=${id}`);
    if (res.status === 401) {
        sessionStorage.removeItem("usuario");
        window.location.href = "login.html?expirado=true";
        return null;
    }
    return res.json();
}

function preencherFormulario(carro) {
    document.getElementById("marca").value         = carro.marca;
    document.getElementById("modelo").value        = carro.modelo;
    document.getElementById("ano").value           = carro.ano;
    document.getElementById("cor").value           = carro.cor;
    document.getElementById("combustivel").value   = carro.combustivel;
    document.getElementById("transmissao").value   = carro.transmissao;
    document.getElementById("quilometragem").value = carro.quilometragem;
    document.getElementById("valor").value         = carro.valor;
    document.getElementById("descricao").value     = carro.descricao;
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
    if (!dados.marca)            erros.push("Preencha a marca.");
    if (!dados.modelo)           erros.push("Preencha o modelo.");
    if (!dados.ano || dados.ano < 1886) erros.push("Informe um ano válido.");
    if (!dados.cor)              erros.push("Preencha a cor.");
    if (!dados.combustivel)      erros.push("Selecione o combustível.");
    if (!dados.transmissao)      erros.push("Selecione a transmissão.");
    if (dados.quilometragem < 0) erros.push("Quilometragem não pode ser negativa.");
    if (dados.valor <= 0)        erros.push("Informe um valor válido.");
    if (!dados.descricao)        erros.push("Preencha a descrição.");
    return erros;
}

// Carrega o carro ao abrir a página
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

if (!id) {
    window.location.href = "index.html";
} else {
    buscarCarro(id).then(carro => {
        if (!carro) {
            window.location.href = "index.html";
        } else {
            preencherFormulario(carro);
        }
    });
}

document.getElementById("btn-salvar").addEventListener("click", async () => {
    const dados = {
        id:             Number(id),
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

    const erros = validarCampos(dados);
    if (erros.length > 0) {
        mostrarErros(erros);
        return;
    }

    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        mostrarSucesso("Carro atualizado com sucesso! (mock)");
        return;
    }

    try {
        const res = await fetch(`${BASE_URL}/editar`, {
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
            mostrarSucesso(data.mensagem || "Carro atualizado com sucesso!");
        }

    } catch (err) {
        mostrarErros(["Erro ao conectar com o servidor."]);
        console.error(err);
    }
});