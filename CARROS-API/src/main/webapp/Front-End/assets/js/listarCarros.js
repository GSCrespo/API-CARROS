const BASE_URL = "";
const USE_MOCK = true;

const carrosMock = [
    { id: 1, marca: "Volkswagen", modelo: "Fusca",  ano: 1980, cor: "Vermelho",        combustivel: "Gasolina", quilometragem: 120000, transmissao: "Manual", valor: 11000, mediaAvaliacao: 4.5, imagem: "" },
    { id: 2, marca: "Volkswagen", modelo: "Kombi",  ano: 1995, cor: "Verde Samambaia",  combustivel: "Gasolina", quilometragem: 200000, transmissao: "Manual", valor: 19000, mediaAvaliacao: 3.8, imagem: "" },
    { id: 3, marca: "Chevrolet",  modelo: "Celta",  ano: 2012, cor: "Branco",           combustivel: "Flex",     quilometragem: 90000,  transmissao: "Manual", valor: 25000, mediaAvaliacao: 4.2, imagem: "" }
];

async function listarCarros() {
    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        return [...carrosMock];
    }
    const res = await fetch(`${BASE_URL}/listar`);
    if (res.status === 401) {
        sessionStorage.removeItem("usuario");
        window.location.href = "login.html?expirado=true";
    }
    return res.json();
}

function imagemCarro(carro) {
    return carro.imagem
        ? `${BASE_URL}/uploads/${carro.imagem}`
        : "https://via.placeholder.com/300x200?text=Sem+foto";
}

function avaliacaoTexto(media) {
    if (!media || media === 0) return "Sem avaliações";
    return `⭐ ${Number(media).toFixed(1)}`;
}

function isAutenticado() {
    return !!sessionStorage.getItem("usuario");
}

function criarCard(carro, autenticado) {
    return `
        <div class="col-md-4 mb-4">
            <div class="card h-100 shadow-sm">
                <img src="${imagemCarro(carro)}" class="card-img-top" alt="${carro.modelo}">
                <div class="card-body">
                    <h5 class="card-title">${carro.marca} ${carro.modelo}</h5>
                    <p class="card-text">
                        <strong>Ano:</strong> ${carro.ano}<br>
                        <strong>Cor:</strong> ${carro.cor}<br>
                        <strong>Combustível:</strong> ${carro.combustivel}<br>
                        <strong>Km:</strong> ${carro.quilometragem.toLocaleString("pt-BR")}<br>
                        <strong>Valor:</strong> R$ ${carro.valor.toLocaleString("pt-BR")}<br>
                        <strong>Avaliação:</strong> ${avaliacaoTexto(carro.mediaAvaliacao)}
                    </p>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <a href="detalhes.html?id=${carro.id}" class="btn btn-primary btn-sm">Ver mais</a>
                    ${autenticado ? `
                        <a href="editar.html?id=${carro.id}" class="btn btn-warning btn-sm">Editar</a>
                        <button class="btn btn-danger btn-sm" onclick="excluirCarro(${carro.id})">Excluir</button>
                    ` : ""}
                </div>
            </div>
        </div>
    `;
}

async function excluirCarro(id) {
    if (!confirm("Tem certeza que deseja excluir este carro?")) return;

    if (USE_MOCK) {
        alert("Carro excluído! (mock)");
        return;
    }

    try {
        const res = await fetch(`${BASE_URL}/excluir`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id })
        });

        if (res.status === 401) {
            sessionStorage.removeItem("usuario");
            window.location.href = "login.html?expirado=true";
            return;
        }

        const data = await res.json();
        if (res.ok) {
            alert(data.mensagem);
            init(); // recarrega a lista
        }
    } catch (err) {
        console.error(err);
        alert("Erro ao excluir carro.");
    }
}

async function init() {
    const lista = document.getElementById("lista-carros");
    const msgVazio = document.getElementById("msg-vazio");
    const msgErro = document.getElementById("msg-erro");

    // Verifica se veio de uma busca: listar.html?busca=fusca
    const params = new URLSearchParams(window.location.search);
    const termoBusca = params.get("busca");

    if (termoBusca) {
        document.getElementById("titulo-listagem").textContent =
            `Resultados para: "${termoBusca}"`;
    }

    try {
        let carros = await listarCarros();

        // Filtra localmente se veio busca
        if (termoBusca) {
            const t = termoBusca.toLowerCase();
            carros = carros.filter(c =>
                c.marca.toLowerCase().includes(t) ||
                c.modelo.toLowerCase().includes(t)
            );
        }

        lista.innerHTML = "";

        if (carros.length === 0) {
            msgVazio.classList.remove("d-none");
            return;
        }

        const autenticado = isAutenticado();
        lista.innerHTML = carros.map(c => criarCard(c, autenticado)).join("");

    } catch (err) {
        console.error(err);
        lista.innerHTML = "";
        msgErro.classList.remove("d-none");
    }
}

init();