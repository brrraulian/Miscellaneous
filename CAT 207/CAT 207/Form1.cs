using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CAT_207
{
    public partial class Form1 : Form
    {
        Classes.Conexao conexao = new Classes.Conexao();
        public string LabelText
        {
            get
            {
                return this.lbl_msg.Text;
            }
            set
            {
                this.lbl_msg.Text = value;
            }
        }

        public Form1()
        {
            InitializeComponent();
        }
                
        private bool validar()
        {
            bool ret = true;
            lbl_msg.Text = "";

            if (cbox_cliente.SelectedItem == null)
            {
                lbl_msg.Text = "Selecione o cliente";
                ret = false;
            }
            else if (cbox_mes.SelectedItem == null)
            {
                lbl_msg.Text = "Selecione o mês";
                ret = false;
            }
            else if (cbox_ano.SelectedItem == null)
            {
                lbl_msg.Text = "Selecione o ano";
                ret = false;
            }

            return ret;
        }

        private void trataComponentes()
        {
            if (cbox_cliente.Enabled)
            {
                cbox_cliente.Enabled = false;
                cbox_mes.Enabled = false;
                cbox_ano.Enabled = false;
                btn_gerar.Enabled = false;
                btn_sped.Enabled = false;
                btn_gia.Enabled = false;
                btn_fichas.Enabled = false;
                btn_relatorio.Enabled = false;
                btn_lista.Enabled = false;
            }
            else
            {
                cbox_cliente.Enabled = true;
                cbox_mes.Enabled = true;
                cbox_ano.Enabled = true;
                btn_gerar.Enabled = true;
                btn_sped.Enabled = true;
                btn_gia.Enabled = true;
                btn_fichas.Enabled = true;
                btn_relatorio.Enabled = true;
                btn_lista.Enabled = true;
            }
        }

        private void btn_gerar_Click(object sender, EventArgs e)
        {
            if (validar())
            {
                lbl_msg.Text = "Gerando arquivo...";

                trataComponentes();

                Classes.Associador associador = new Classes.Associador();
                string ret = associador.Associar(cbox_mes.SelectedItem.ToString(), cbox_ano.SelectedItem.ToString(), cbox_cliente.SelectedItem.ToString(), conexao.cnx.ConnectionString);

                lbl_msg.Text = ret;

                //if (ret == "Gerando arquivo...")
                //{
                //    lbl_msg.Text = ret;

                //    Classes.Gerador gerador = new Classes.Gerador();
                //    ret = gerador.GeraArquivo(cbox_mes.SelectedItem.ToString(), cbox_ano.SelectedItem.ToString(), cbox_cliente.SelectedItem.ToString(), conexao.cnx.ConnectionString);

                //    if (ret == "Arquivo gerado com sucesso!")
                //        lbl_msg.Text = ret;
                //    else
                //        lbl_msg.Text = "Erro na geração do arquivo";
                //}
                //else
                    //lbl_msg.Text = "Erro na associação do arquivo";

                trataComponentes();
            }
        }

        private void btn_sped_Click(object sender, EventArgs e)
        {
            if (validar())
            {
                sped sped = new sped(cbox_mes.SelectedItem.ToString(), cbox_ano.SelectedItem.ToString(), cbox_cliente.SelectedItem.ToString(), this);
                sped.Show();
                Hide();
            }
        }

        private void btn_gia_Click(object sender, EventArgs e)
        {
            if (validar())
            {
                gia gia = new gia(cbox_mes.SelectedItem.ToString(), cbox_ano.SelectedItem.ToString(), cbox_cliente.SelectedItem.ToString(), this);
                gia.Show();
                Hide();
            }
        }
        
        private void btn_fichas_Click(object sender, EventArgs e)
        {
            if (validar())
            {
                lbl_msg.Text = "Gerando fichas...";

                string mes = cbox_mes.SelectedItem.ToString();
                string ano = cbox_ano.SelectedItem.ToString();

                SaveFileDialog sfd = new SaveFileDialog();
                sfd.Filter = "Excel Documents (*.xls)|*.xls";
                sfd.FileName = "Fichas CAT 207 - " + cbox_cliente.SelectedItem.ToString() + " " + mes + ano + ".xls";

                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    trataComponentes();

                    Classes.Fichas fichas = new Classes.Fichas();
                    List<DataTable> data = new List<DataTable>();
                    List<string[]> colunas = new List<string[]>();

                    data.Add(fichas.Ficha5C(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_5C = { "Código", "Nome", "País", "CNPJ", "IE", "Endereço", "Número", "Complemento", "Bairro", "Município", "CEP", "UF", "Telefone" };
                    colunas.Add(colunas_5C);

                    data.Add(fichas.Ficha5D(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_5D = { "Código do Enquadramento Legal", "Código da Hipótese Geradora", "Anexo", "Artigo", "Inciso", "Alínea", "Parágrafo", "Item", "Letra", "Observação" };
                    colunas.Add(colunas_5D);

                    data.Add(fichas.Ficha6A(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_6A = { "Data do Documento Fiscal", "Tipo do Documento Fiscal", "Série do Documento Fiscal", "Número do Documento Fiscal", "Código do Destinatário", "Classificação da Operação Geradora", "Valor de Saída", "Valor da Base de Cálculo", "Valor do ICMS debitado", "IVA Utilizado", "Valor do Custo Estimado", "Percentual Médio de Crédito do Imposto", "Crédito Estimado do Imposto", "Percentual do Crédito Outorgado", "Valor do Crédito Outorgado", "Valor total do ICMS", "Valor do Crédito Acumulado Gerado" };
                    colunas.Add(colunas_6A);

                    data.Add(fichas.Ficha6B(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_6B = { "Data do Documento Fiscal", "Tipo do Documento Fiscal", "Série do Documento Fiscal", "Número do Documento Fiscal", "Código do Destinatário", "Classificação da Operação Geradora", "Valor de Saída", "Valor da Base de Cálculo", "Valor do ICMS debitado", "IVA Utilizado", "Valor do Custo Estimado", "Percentual Médio de Crédito do Imposto", "Crédito Estimado do Imposto", "Percentual do Crédito Outorgado", "Valor do Crédito Outorgado", "Valor total do ICMS", "Valor do Crédito Acumulado Gerado" };
                    colunas.Add(colunas_6B);

                    data.Add(fichas.Ficha6D(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_6D = { "Data do Documento Fiscal", "Tipo do Documento Fiscal", "Série do Documento Fiscal", "Número do Documento Fiscal", "Código do Destinatário", "Classificação da Operação Geradora", "Comprovação da Operação (sim ou não)", "Valor de Saída", "IVA Utilizado", "Valor do Custo Estimado", "Percentual Médio de Crédito do Imposto", "Crédito Estimado do Imposto", "Percentual do Crédito Outorgado", "Valor do Crédito Outorgado", "Valor do ICMS Comprovado", "Valor do Crédito Acumulado Gerado" };
                    colunas.Add(colunas_6D);

                    data.Add(fichas.Ficha6E(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_6E = { "Data do Documento Fiscal", "Tipo do Documento Fiscal", "Série do Documento Fiscal", "Número do Documento Fiscal", "Código do Destinatário", "Classificação da Operação Geradora", "Valor de Saída", "IVA Utilizado", "Valor do Custo Estimado", "Percentual Médio de Crédito do Imposto", "Crédito Estimado do Imposto", "Percentual do Crédito Outorgado", "Valor do Crédito Outorgado", "Valor do Crédito Acumulado Gerado" };
                    colunas.Add(colunas_6E);

                    data.Add(fichas.Ficha6F(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunas_6F = { "Data do Documento Fiscal", "Tipo do Documento Fiscal", "Série do Documento Fiscal", "Número do Documento Fiscal", "Código do Destinatário", "Número da Declaração para Despacho de Exportação", "Valor de Saída", "Valor da Base de Cálculo", "Valor do ICMS debitado", "Percentual do Crédito Outorgado", "Valor do Crédito Outorgado" };
                    colunas.Add(colunas_6F);

                    lbl_msg.Text = fichas.GerarFichas(mes, ano, data, colunas, sfd.FileName);

                    trataComponentes();
                }
            }
        }

        private void cbox_cliente_SelectedIndexChanged(object sender, EventArgs e)
        {
            Classes.Auxiliar aux = new Classes.Auxiliar();
            conexao.cnx.ConnectionString = aux.GetCnxByCliente(cbox_cliente.SelectedItem.ToString());
        }

        private void btn_relatorio_Click(object sender, EventArgs e)
        {
            if (validar())
            {
                lbl_msg.Text = "Gerando relatório...";

                string mes = cbox_mes.SelectedItem.ToString();
                string ano = cbox_ano.SelectedItem.ToString();

                SaveFileDialog sfd = new SaveFileDialog();
                sfd.Filter = "Word Documents (*.doc)|*.doc";
                sfd.FileName = "Relatório CAT 207 - " + cbox_cliente.SelectedItem.ToString() + " " + mes + ano + ".doc";

                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    trataComponentes();

                    Classes.Relatorio relatorio = new Classes.Relatorio();
                    List<DataTable> data = new List<DataTable>();
                    List<string[]> colunas = new List<string[]>();

                    data.Add(relatorio.getEntidade(mes, ano, conexao.cnx.ConnectionString));
                    data.Add(relatorio.getIvaPmc(mes, ano, conexao.cnx.ConnectionString));

                    data.Add(relatorio.getEnquadramento(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunasEnq = { "Cod. Enquadr.", "Hipotese Geradora", "Anexo", "Artigo", "Inciso", "Alínea", "Parágrafo", "Item", "Letra" };
                    colunas.Add(colunasEnq);

                    data.Add(relatorio.getValores(mes, ano, conexao.cnx.ConnectionString));
                    string[] colunasVal = { "Aliq. ICMS", "Valor Operação", "Base Cálculo ICMS", "Valor ICMS", "Cod. Enquadr." };
                    colunas.Add(colunasVal);

                    lbl_msg.Text = relatorio.GerarRelatorio(mes, ano, data, colunas, sfd.FileName);

                    trataComponentes();
                }
            }
        }

        private void btn_lista_Click(object sender, EventArgs e)
        {
            bool ret = true;
            lbl_msg.Text = "";

            if (cbox_cliente.SelectedItem == null)
            {
                lbl_msg.Text = "Selecione o cliente";
                ret = false;
            }

            if (ret)
            {
                lbl_msg.Text = "Gerando lista...";

                //string mes = cbox_mes.SelectedItem.ToString();
                //string ano = cbox_ano.SelectedItem.ToString();
                string[] anos = new string[] { "2012", "2013", "2014", "2015", "2016" };

                SaveFileDialog sfd = new SaveFileDialog();
                sfd.Filter = "Excel Documents (*.xls)|*.xls";
                sfd.FileName = "NF Geradoras CAT 207 - " + cbox_cliente.SelectedItem.ToString() + ".xls";

                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    trataComponentes();

                    Classes.NFGeradoras lista = new Classes.NFGeradoras();

                    lbl_msg.Text = lista.GerarLista(anos, sfd.FileName, conexao.cnx.ConnectionString);

                    trataComponentes();
                }
            }
        }

    }
}
