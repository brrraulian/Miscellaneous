window.onload = function () {
    document.getElementById("txt_tipoMerc").focus();
};


lbl_erro;
txt_tipoMerc;
txt_nomeMerc;
txt_qtdeMerc;
txt_precoMerc;
window.onload = function () {
    lbl_erro = document.getElementById('lbl_erro').innerHTML;
    txt_tipoMerc = document.getElementById('txt_tipoMerc').innerHTML;
    txt_nomeMerc = document.getElementById('txt_nomeMerc').innerHTML;
    txt_qtdeMerc = document.getElementById('txt_qtdeMerc').innerHTML;
    txt_precoMerc = document.getElementById('txt_precoMerc').innerHTML;
}


var txt_precoMercLenght;
var txt_precoMercAnt;
function Decimal() {    
    if (txt_precoMerc.value.length <= 8) {
        if (txt_precoMerc.value.length == 1) {
            txt_precoMerc.value = txt_precoMerc.value / 100;
        } else {

            if (txt_precoMerc.value.length > txt_precoMercLenght) {
                txt_precoMerc.value = parseFloat(txt_precoMerc.value * 10).toFixed(2);
            } else {
                if (txt_precoMerc.value.length <= 8) {
                    txt_precoMerc.value = parseFloat(txt_precoMerc.value / 10).toFixed(2);
                }
            }
        }

        txt_precoMercLenght = txt_precoMerc.value.length;
        txt_precoMercAnt = txt_precoMerc.value;
    } else {
        txt_precoMerc.value = txt_precoMercAnt;
    }
}


function teclaNumeroQtde(tecla) {
    var codTecla = (tecla.which) ? tecla.which : tecla.keyCode;

    if (codTecla > 31 && (codTecla < 48 || codTecla > 57) && codTecla != 47) {
        lbl_erro.innerHTML = "- Digite apenas números.\n";
        txt_qtdeMerc.focus();
        return false;
    }
    else {     
        return true;
    }
}


function teclaNumeroPreco(tecla) {
    var codTecla = (tecla.which) ? tecla.which : tecla.keyCode;

    if (codTecla > 31 && (codTecla < 48 || codTecla > 57) && codTecla != 47) {
        lbl_erro.innerHTML = "- Digite apenas números.\n";
        txt_precoMerc.focus();
        return false;
    }
    else {
        return true;
    }
}


function Validar() {
    lbl_erro.innerHTML = "";

    if (txt_tipoMerc.value == "") {
        lbl_erro.innerHTML = "- Informe o tipo da mercadoria.\n";
        txt_tipoMerc.focus();
        return false;
    }
    if (txt_nomeMerc.value == "") {
        lbl_erro.innerHTML = "- Informe o nome da mercadoria.\n";
        txt_nomeMerc.focus();
        return false;
    }
    if (txt_qtdeMerc.value == "" || txt_qtdeMerc.value == "0") {
        lbl_erro.innerHTML = "- Informe a quantidade da mercadoria.\n";
        txt_qtdeMerc.focus();
        return false;
    }
    if (txt_precoMerc.value == "" || txt_precoMerc.value == "0.00") {
        lbl_erro.innerHTML = "- Informe o preço da mercadoria.\n";
        txt_precoMerc.focus();
        return false;
    }

    return true;
}