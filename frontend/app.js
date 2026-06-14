
const BACKEND_URL = "http://localhost:8080"; 

const API_CONTAS = `${BACKEND_URL}/api/contas`;
const API_VARIAVEIS = `${BACKEND_URL}/api/variaveis`;
const API_CARTAO = `${BACKEND_URL}/api/cartao`;

let totalContas = 0;
let totalVariaveis = 0;
let totalCartao = 0;

let usuarioId = localStorage.getItem('usuario_financeiro_id');
if (!usuarioId) {
    usuarioId = 'user_' + Math.random().toString(36).substr(2, 9) + Date.now().toString(36);
    localStorage.setItem('usuario_financeiro_id', usuarioId);
}

function atualizarTotalGeral() {
    const cardTotalGeral = document.getElementById("total-geral");
    if (cardTotalGeral) {
        const geral = totalContas + totalVariaveis + totalCartao;
        cardTotalGeral.textContent = `R$ ${geral.toFixed(2).replace('.', ',')}`;
    }
}

// --- FUNÇÃO GENÉRICA PARA DELETAR DE QUALQUER URL ---
async function deletarItem(urlBase, id, mensagem, callbackAtualizar) {
    try {
        const response = await fetch(`${urlBase}/${id}`, { method: "DELETE" });
        if (response.ok) {
            alert(mensagem);
            callbackAtualizar();
        } else {
            alert("Erro ao remover o registro do servidor.");
        }
    } catch (error) {
        console.error("Erro na requisição DELETE:", error);
    }
}

// ==========================================
// 1. SEÇÃO: CONTAS A PAGAR
// ==========================================
async function salvarConta(event) {
    event.preventDefault();
    const nome = document.getElementById("conta-nome").value;
    const valor = parseFloat(document.getElementById("conta-valor").value);
    const dataVencimento = document.getElementById("conta-data").value;

    try {
        const response = await fetch(API_CONTAS, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome, valor, dataVencimento, usuarioId })
        });
        if (response.ok) {
            document.getElementById("form-conta").reset();
            carregarContas();
        }
    } catch (error) { console.error(error); }
}

async function carregarContas() {
    try {
        const response = await fetch(`${API_CONTAS}/usuario/${usuarioId}`);
        const contas = await response.json();
        const listaElemento = document.getElementById("lista-contas");
        totalContas = 0;

        if (listaElemento) {
            listaElemento.innerHTML = "";
            contas.forEach(conta => {
                totalContas += conta.valor;
                const li = document.createElement("li");
                const dataFormatada = conta.dataVencimento ? conta.dataVencimento.split('-').reverse().join('/') : '';
                li.innerHTML = `
                    <div style="display: flex; flex-direction: column;">
                        <span><strong>${conta.nome}</strong></span>
                        <span style="font-size: 0.8em; opacity: 0.6;">📅 ${dataFormatada}</span>
                    </div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <span>R$ ${conta.valor.toFixed(2).replace('.', ',')}</span>
                        <button onclick="deletarItem(API_CONTAS, ${conta.id}, 'Conta paga!', carregarContas)" style="background: #2ecc71; color: white; border: none; padding: 2px 6px; border-radius: 4px; cursor: pointer; font-size: 0.85em;">✔</button>
                        <button onclick="deletarItem(API_CONTAS, ${conta.id}, 'Removido!', carregarContas)" style="background: #e74c3c; color: white; border: none; padding: 2px 6px; border-radius: 4px; cursor: pointer; font-size: 0.85em;">🗑</button>
                    </div>
                `;
                listaElemento.appendChild(li);
            });
        }
        const card = document.getElementById("total-contas");
        if (card) card.textContent = `R$ ${totalContas.toFixed(2).replace('.', ',')}`;
        atualizarTotalGeral();
    } catch (error) { console.error(error); }
}

// ==========================================
// 2. SEÇÃO: GASTOS VARIÁVEIS
// ==========================================
async function salvarGastoVariavel(event) {
    event.preventDefault();
    const categoria = document.getElementById("var-categoria").value;
    const valor = parseFloat(document.getElementById("var-valor").value);
    const dataGasto = document.getElementById("var-data").value;

    try {
        const response = await fetch(API_VARIAVEIS, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ categoria, valor, dataGasto, usuarioId })
        });
        if (response.ok) {
            document.getElementById("form-variavel").reset();
            carregarGastosVariaveis();
        }
    } catch (error) { console.error(error); }
}

async function carregarGastosVariaveis() {
    try {
        const response = await fetch(`${API_VARIAVEIS}/usuario/${usuarioId}`);
        const gastos = await response.json();
        const container = document.getElementById("container-categorias");
        totalVariaveis = 0;

        if (container) {
            container.innerHTML = "";
            const ul = document.createElement("ul");
            ul.className = "item-list";
            
            gastos.forEach(gasto => {
                totalVariaveis += gasto.valor;
                const li = document.createElement("li");
                const dataFormatada = gasto.dataGasto ? gasto.dataGasto.split('-').reverse().join('/') : '';
                li.innerHTML = `
                    <div style="display: flex; flex-direction: column;">
                        <span><strong>${gasto.categoria}</strong></span>
                        <span style="font-size: 0.8em; opacity: 0.6;">📅 ${dataFormatada}</span>
                    </div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <span>R$ ${gasto.valor.toFixed(2).replace('.', ',')}</span>
                        <button onclick="deletarItem(API_VARIAVEIS, ${gasto.id}, 'Gasto removido!', carregarGastosVariaveis)" style="background: #e74c3c; color: white; border: none; padding: 2px 6px; border-radius: 4px; cursor: pointer; font-size: 0.85em;">🗑</button>
                    </div>
                `;
                ul.appendChild(li);
            });
            container.appendChild(ul);
        }
        const card = document.getElementById("total-variaveis");
        if (card) card.textContent = `R$ ${totalVariaveis.toFixed(2).replace('.', ',')}`;
        atualizarTotalGeral();
    } catch (error) { console.error(error); }
}

// ==========================================
// 3. SEÇÃO: CARTÃO DE CRÉDITO
// ==========================================
// ==========================================
// 3. SEÇÃO: CARTÃO DE CRÉDITO (CORRIGIDA)
// ==========================================
async function salvarCompraCartao(event) {
    event.preventDefault();
    const estabelecimento = document.getElementById("cartao-nome").value;
    let valorInput = parseFloat(document.getElementById("cartao-valor").value);
    const tipoValor = document.getElementById("cartao-tipo-valor").value;
    const quantidadeParcelas = parseInt(document.getElementById("cartao-parcelas").value);
    const dataCompra = document.getElementById("cartao-data").value;

    let valorParcela = valorInput;
    if (tipoValor === "total") {
        valorParcela = valorInput / quantidadeParcelas;
    }

    // ALINHADO COM O JAVA: Chaves combinando perfeitamente com os atributos da Entidade
    const dadosCartao = {
        nome: estabelecimento,  
        valorParcela: valorParcela, 
        totalParcelas: quantidadeParcelas,
        dataCompra: dataCompra,
        usuarioId: usuarioId 
    };

    try {
        const response = await fetch(API_CARTAO, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dadosCartao)
        });
        if (response.ok) {
            alert("Compra no cartão lançada com sucesso!");
            document.getElementById("form-cartao").reset();
            carregarComprasCartao();
        } else {
            alert("Erro do servidor ao salvar o cartão. Verifique os logs do Spring Boot.");
        }
    } catch (error) { 
        console.error("Erro na requisição do cartão:", error); 
    }
}

async function carregarComprasCartao() {
    try {
        const response = await fetch(`${API_CARTAO}/usuario/${usuarioId}`);
        const compras = await response.json();
        const listaMes = document.getElementById("lista-cartao-mes");
        totalCartao = 0;

        if (listaMes) {
            listaMes.innerHTML = "";
            compras.forEach(compra => {
                // Soma usando o atributo correto vindo do banco
                totalCartao += compra.valorParcela;
                const li = document.createElement("li");
                const dataFormatada = compra.dataCompra ? compra.dataCompra.split('-').reverse().join('/') : '';
                
                // AJUSTADO: Lendo 'compra.nome' e 'compra.totalParcelas' retornados pela API
                li.innerHTML = `
                    <div style="display: flex; flex-direction: column;">
                        <span><strong>${compra.nome}</strong> (1/${compra.totalParcelas})</span>
                        <span style="font-size: 0.8em; opacity: 0.6;">📅 ${dataFormatada}</span>
                    </div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <span>R$ ${compra.valorParcela.toFixed(2).replace('.', ',')}</span>
                        <button onclick="deletarItem(API_CARTAO, ${compra.id}, 'Lançamento de cartão removido!', carregarComprasCartao)" style="background: #e74c3c; color: white; border: none; padding: 2px 6px; border-radius: 4px; cursor: pointer; font-size: 0.85em;">🗑</button>
                    </div>
                `;
                listaMes.appendChild(li);
            });
        }
        const card = document.getElementById("total-cartao-mes");
        if (card) card.textContent = `R$ ${totalCartao.toFixed(2).replace('.', ',')}`;
        atualizarTotalGeral();
    } catch (error) { console.error(error); }
}

// ==========================================
// INICIALIZAÇÃO
// ==========================================
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("form-conta")?.addEventListener("submit", salvarConta);
    document.getElementById("form-variavel")?.addEventListener("submit", salvarGastoVariavel);
    document.getElementById("form-cartao")?.addEventListener("submit", salvarCompraCartao);
    
    carregarContas();
    carregarGastosVariaveis();
    carregarComprasCartao();
});