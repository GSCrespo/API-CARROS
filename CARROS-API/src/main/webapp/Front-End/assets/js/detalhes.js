const BASE_URL = "http://localhost:8080/CRUDCarros";
const USE_MOCK = false;

const carrosMock = [
    { id: 1, marca: "Volkswagen", modelo: "Fusca",  ano: 1980, cor: "Vermelho",        combustivel: "Gasolina", quilometragem: 120000, transmissao: "Manual", valor: 11000, mediaAvaliacao: 4.5, descricao: "Tanque de guerra raiz",              imagem: "" },
    { id: 2, marca: "Volkswagen", modelo: "Kombi",  ano: 1995, cor: "Verde Samambaia",  combustivel: "Gasolina", quilometragem: 200000, transmissao: "Manual", valor: 19000, mediaAvaliacao: 3.8, descricao: "Com folga no volante",                imagem: "" },
    { id: 3, marca: "Chevrolet",  modelo: "Celta",  ano: 2012, cor: "Branco",           combustivel: "Flex",     quilometragem: 90000,  transmissao: "Manual", valor: 25000, mediaAvaliacao: 4.2, descricao: "Barato, confiável e econômico",      imagem: "" }
];

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

function isAutenticado() {
    return !!sessionStorage.getItem("usuario");
}

async function init() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    const msgErro = document.getElementById("msg-erro");
    const conteudo = document.getElementById("conteudo");

    if (!id) {
        msgErro.textContent = "Nenhum carro informado.";
        msgErro.classList.remove("d-none");
        return;
    }

    const carro = await buscarCarro(id);

    if (!carro) {
        msgErro.classList.remove("d-none");
        return;
    }

    // Preenche os campos
    document.getElementById("carro-titulo").textContent       = `${carro.marca} ${carro.modelo}`;
    document.getElementById("carro-valor").textContent        = `R$ ${carro.valor.toLocaleString("pt-BR")}`;
    document.getElementById("carro-ano").textContent          = carro.ano;
    document.getElementById("carro-cor").textContent          = carro.cor;
    document.getElementById("carro-transmissao").textContent  = carro.transmissao;
    document.getElementById("carro-combustivel").textContent  = carro.combustivel;
    document.getElementById("carro-quilometragem").textContent = carro.quilometragem.toLocaleString("pt-BR") + " km";
    document.getElementById("carro-descricao").textContent    = carro.descricao;

    const media = carro.mediaAvaliacao;
    document.getElementById("carro-avaliacao").textContent =
        media && media > 0 ? `⭐ ${Number(media).toFixed(1)}` : "Sem avaliações";

    const imagem = document.getElementById("carro-imagem");
    imagem.src = carro.imagem
        ? `${BASE_URL}/uploads/${carro.imagem}`
        : "https://placehold.co/400x300?text=Sem+foto";

    // Botões condicionais
    if (isAutenticado()) {
        const btnEditar = document.getElementById("btn-editar");
        btnEditar.href = `editar.html?id=${carro.id}`;
        btnEditar.classList.remove("d-none");

        const btnAvaliar = document.getElementById("btn-avaliar");
        btnAvaliar.href = `avaliacao.html?id=${carro.id}`;
        btnAvaliar.classList.remove("d-none");
    }

    conteudo.classList.remove("d-none");
}

init();