using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CAT_207
{
    public partial class gia : Form
    {
        string mes, ano, cliente, cliente_cnpj;
        Form1 form;

        public gia(string mes, string ano, string cliente, Form1 form)
        {
            InitializeComponent();

            this.mes = mes;
            this.ano = ano;
            this.cliente = cliente;

            Classes.Auxiliar aux = new Classes.Auxiliar();
            cliente_cnpj = aux.GetCNPJByCliente(cliente);

            this.form = form;
        }

        private void btn_cancel_Click(object sender, EventArgs e)
        {
            form.Show();
            Close();
        }

        private void btn_openfolder_Click(object sender, EventArgs e)
        {
            if (ofd1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                txt_sourcefolder.Text = ofd1.FileName;
            }
        }

        private void btn_next_Click(object sender, EventArgs e)
        {
            lbl_msg.Text = "";
            string ret = Salvar();

            if (ret == "")
            {
                form.LabelText = "GIA inserido com sucesso!";
                form.Show();
                Close();
            }
            else
                lbl_msg.Text = "Erro ao ler GIA";
        }

        private void trataComponentes()
        {
            if (txt_sourcefolder.Enabled)
            {
                txt_sourcefolder.Enabled = false;
                btn_openfolder.Enabled = false;
                btn_next.Enabled = false;
                btn_cancel.Enabled = false;
            }
            else
            {
                txt_sourcefolder.Enabled = true;
                btn_openfolder.Enabled = true;
                btn_next.Enabled = true;
                btn_cancel.Enabled = true;
            }
        }

        #region Salvar

        private string Validar()
        {
            string ret = "";

            foreach (var gia in ofd1.FileNames)
            {
                if (gia != "")
                {
                    string saida = "";
                    string[] saida_split;
                    string cnpj_gia = "";
                    string periodo_gia = "";

                    try
                    {
                        using (DataTable dt = new DataTable())
                        {
                            using (OleDbConnection conection = new OleDbConnection("Provider=Microsoft.JET.OLEDB.4.0;" + "data source=" + gia + ";Jet OLEDB:Database Password=kamisama2"))
                            {
                                string query_cnpjo = "SELECT CNPJ FROM tblContribuinte";
                                string query_periodo = "SELECT Ref1 FROM tblGIA";

                                OleDbCommand cmd = new OleDbCommand(query_cnpjo, conection);

                                conection.Open();

                                OleDbDataReader dr = cmd.ExecuteReader();
                                dr.Read();

                                if (dr.HasRows)
                                    saida = (dr["CNPJ"] == DBNull.Value) ? "" : dr["CNPJ"].ToString();

                                dr.Close();
                                cmd.CommandText = query_periodo;
                                dr = cmd.ExecuteReader();
                                dr.Read();

                                if (dr.HasRows)
                                    periodo_gia = (dr["Ref1"] == DBNull.Value) ? "" : Convert.ToDateTime(dr["Ref1"]).ToString("MMyyyy");

                                dr.Close();
                                conection.Close();
                            }
                        }

                        if (saida != "")
                        {
                            saida_split = saida.Split('.', '/', '-');
                            cnpj_gia = saida_split[0] + saida_split[1] + saida_split[2] + saida_split[3] + saida_split[4];

                            if (cnpj_gia != cliente_cnpj)
                                ret = "O contribuinte desse GIA não possui o CNPJ " + cliente_cnpj;
                        }


                        if (periodo_gia != "")
                        {
                            string protocolo_mes = DateTime.Now.Month.ToString();
                            string protocolo_ano = DateTime.Now.Year.ToString();
                            string gia_mes = periodo_gia.Remove(2, 4);
                            string gia_ano = periodo_gia.Remove(0, 2);

                            if (Convert.ToInt32(protocolo_ano) > Convert.ToInt32(ano))
                            {
                                if (gia_ano != ano)
                                    ret = "O GIA deve ser referente ao mesmo ano do período da geração do crédito informado";
                            }
                            else if (protocolo_ano == ano)
                            {
                                if (int.Parse(protocolo_mes) <= 6)
                                {
                                    if (Convert.ToInt32(gia_ano) >= Convert.ToInt32(ano))
                                        ret = "O GIA deve ser referente ao ano anterior ao do período da geração do crédito informado";
                                }
                                else
                                {
                                    if (gia_ano == ano)
                                    {
                                        if (Convert.ToInt32(gia_mes) >= Convert.ToInt32(protocolo_mes))
                                            ret = "O GIA deve ser referente a no máximo o mês anterior ao do período do protocolo informado";
                                    }
                                    else
                                        ret = "O GIA deve ser referente ao mesmo ano do período da geração do crédito informado";
                                }
                            }
                            else
                                ret = "O ano do protocolo deve ser igual ou posterior ao do ano da geração do crédito informado";
                        }
                    }
                    catch (Exception ex)
                    {
                        ret = ex.Message;
                    }
                }
                else
                    ret = "Selecione um arquivo GIA";
            }

            return ret;
        }

        private string Salvar()
        {
            trataComponentes();

            string ret = Validar();

            if (ret != "")
            {
                return ret;
            }

            string periodo = mes + ano;
            string periodo_gia = "";
            bool limpa_gia = false;
            var gia_select = "SELECT ValorContábil AS VALOR_CONTABIL, BaseCálculo AS BASE_CALCULO, Imposto AS IMPOSTO, CFOP FROM tblDetalhesCFOPs";
            string query_periodo = "SELECT Ref1 FROM tblGIA";
            Classes.Auxiliar aux = new Classes.Auxiliar();

            try
            {
                foreach (var gia in ofd1.FileNames)
                {
                    using (OleDbConnection conection = new OleDbConnection("Provider=Microsoft.JET.OLEDB.4.0;" + "data source=" + gia + ";Jet OLEDB:Database Password=kamisama2"))
                    {
                        using (DataTable dt = new DataTable())
                        {
                            using (OleDbDataAdapter da = new OleDbDataAdapter(gia_select, conection))
                            {
                                conection.Open();
                                da.Fill(dt);

                                da.SelectCommand.CommandText = query_periodo;
                                using (OleDbDataReader dr = da.SelectCommand.ExecuteReader())
                                {
                                    dr.Read();
                                    if (dr.HasRows)
                                        periodo_gia = (dr["Ref1"] == DBNull.Value) ? "" : Convert.ToDateTime(dr["Ref1"]).ToString("MMyyyy");

                                    using (var cnx = new EF.ef_dbContainer())
                                    {
                                        cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);
                                        
                                        EF.GIA par = new EF.GIA();

                                        for (int i = 0; i < dt.Rows.Count; i++)
                                        {
                                            par.VALOR_CONTABIL = Convert.ToDecimal(dt.Rows[i]["VALOR_CONTABIL"]);
                                            par.BASE_CALCULO = Convert.ToDecimal(dt.Rows[i]["BASE_CALCULO"]);
                                            par.IMPOSTO = Convert.ToDecimal(dt.Rows[i]["IMPOSTO"]);
                                            par.CFOP = dt.Rows[i]["CFOP"].ToString();
                                            par.PERIODO_GIA = periodo_gia;
                                            par.PERIODO_ARQUIVO = periodo;

                                            if (!limpa_gia)
                                            {
                                                cnx.Database.ExecuteSqlCommand("DELETE FROM GIA WHERE PERIODO_ARQUIVO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                                //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ITENS_DOC_C170', RESEED, 0)");
                                                limpa_gia = true;
                                            }

                                            cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                            cnx.SaveChanges();
                                        }
                                    }
                                }

                                dt.Clear();
                                conection.Close();
                            }
                        }
                    }
                }

            }
            catch (Exception ex)
            {
                ret = ex.Message;
            }

            trataComponentes();

            return ret;
        }

        #endregion

    }
}
