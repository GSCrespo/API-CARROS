const BASE_URL = "";
const USE_MOCK = true;

const carrosMock = [
    { id: 1, marca: "Volkswagen", modelo: "Fusca",  ano: 1980, cor: "Vermelho",        combustivel: "Gasolina", valor: 11000, mediaAvaliacao: 4.5, imagem: "" },
    { id: 2, marca: "Volkswagen", modelo: "Kombi",  ano: 1995, cor: "Verde Samambaia",  combustivel: "Gasolina", valor: 19000, mediaAvaliacao: 3.8, imagem: "" },
    { id: 3, marca: "Chevrolet",  modelo: "Celta",  ano: 2012, cor: "Branco",           combustivel: "Flex",     valor: 25000, mediaAvaliacao: 4.2, imagem: "" }
];

async function listarCarros() {
    if (USE_MOCK) {
        await new Promise(r => setTimeout(r, 300));
        return [...carrosMock];
    }
    const res = await fetch(`${BASE_URL}/listar`);
    if (res.status === 401) {
        sessionStorage.removeItem("usuario");
        window.location.href = "login.html";
    }
    return res.json();
}

function imagemCarro(carro) {
    return carro.imagem
        ? `${BASE_URL}/uploads/${carro.imagem}`
        : "https://placehold.co/300x200?text=Sem+foto";
}

function avaliacaoTexto(media) {
    if (!media || media === 0) return "Sem avaliações";
    return `⭐ ${Number(media).toFixed(1)}`;
}

function isAutenticado() {
    return !!sessionStorage.getItem("usuario");
}

async function montarCarousel(carros) {
    const inner = document.getElementById("carousel-inner");

    // destaques = top 5 por valor
    const destaques = [...carros].sort((a, b) => b.valor - a.valor).slice(0, 5);

    if (destaques.length === 0) {
        inner.innerHTML = `<div class="text-center text-light py-4">Nenhum destaque disponível.</div>`;
        return;
    }

    inner.innerHTML = destaques.map((carro, i) => `
        <div class="carousel-item ${i === 0 ? "active" : ""}">
            <div class="d-flex justify-content-center">
                <div class="card" style="width: 20rem;">
                    <img src="${imagemCarro(carro)}" class="card-img-top" alt="${carro.modelo}">
                    <div class="card-body">
                        <h5 class="card-title">${carro.marca} ${carro.modelo}</h5>
                        <p class="card-text">
                            Ano: ${carro.ano}<br>
                            Cor: ${carro.cor}<br>
                            Combustível: ${carro.combustivel}<br>
                            Valor: R$ ${carro.valor.toLocaleString("pt-BR")}<br>
                            Avaliação: ${avaliacaoTexto(carro.mediaAvaliacao)}
                        </p>
                        <a href="detalhes.html?id=${carro.id}" class="btn btn-primary">Ver mais</a>
                    </div>
                </div>
            </div>
        </div>
    `).join("");
}

async function montarListaCarros(carros) {
    const lista = document.getElementById("lista-carros");
    const autenticado = isAutenticado();

    if (carros.length === 0) {
        lista.innerHTML = `<div class="alert alert-warning">Nenhum carro cadastrado.</div>`;
        return;
    }

    lista.innerHTML = carros.map(carro => `
        <div class="col-md-4 mb-4">
            <div class="card h-100 shadow-sm">
                <img src="${imagemCarro(carro)}" class="card-img-top" alt="${carro.modelo}">
                <div class="card-body">
                    <h5 class="card-title">${carro.marca} ${carro.modelo}</h5>
                    <p class="card-text">
                        <strong>Ano:</strong> ${carro.ano}<br>
                        <strong>Cor:</strong> ${carro.cor}<br>
                        <strong>Valor:</strong> R$ ${carro.valor.toLocaleString("pt-BR")}<br>
                        <strong>Avaliação:</strong> ${avaliacaoTexto(carro.mediaAvaliacao)}
                    </p>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <a href="detalhes.html?id=${carro.id}" class="btn btn-primary btn-sm">Ver mais</a>
                    ${autenticado ? `<a href="editar.html?id=${carro.id}" class="btn btn-warning btn-sm">Editar</a>` : ""}
                </div>
            </div>
        </div>
    `).join("");
}

async function init() {
    try {
        const carros = await listarCarros();
        await montarCarousel(carros);
        await montarListaCarros(carros);
    } catch (err) {
        console.error(err);
        document.getElementById("lista-carros").innerHTML =
            `<div class="alert alert-danger">Erro ao carregar carros.</div>`;
    }
}

init();