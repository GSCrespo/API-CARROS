const BASE_URL = "http://localhost:8080/CRUDCarros";
const USE_MOCK = false;

function init() {
    const usuario = JSON.parse(sessionStorage.getItem("usuario") || "null");

    if (!usuario) {
        document.getElementById("msg-nao-logado").classList.remove("d-none");
        return;
    }

    document.getElementById("perfil-nome").textContent = usuario.userName;
    document.getElementById("perfil-tipo").textContent =
        usuario.isAdmin || usuario.tipo === "ADMIN" ? "ADMIN" : "COMUM";

    document.getElementById("card-perfil").classList.remove("d-none");
}

document.getElementById("btn-sair").addEventListener("click", async () => {
    if (!confirm("Tem certeza que deseja ser desconectado da conta?")) return;

    if (!USE_MOCK) {
        try {
            await fetch(`${BASE_URL}/logout`, { method: "POST" });
        } catch (err) {
            console.error(err);
        }
    }

    sessionStorage.removeItem("usuario");
    window.location.href = "login.html";
});

init();