using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CAT_207
{
    public partial class sped : Form
    {
        string mes, ano, cliente, cliente_cnpj;
        Form1 form;

        public sped(string mes, string ano, string cliente, Form1 form)
        {
            InitializeComponent();

            this.mes = mes;
            this.ano = ano;
            this.cliente = cliente;

            Classes.Auxiliar aux = new Classes.Auxiliar();
            cliente_cnpj = aux.GetCNPJByCliente(cliente);

            this.form = form;
        }

        private void btn_openfolder_Click(object sender, EventArgs e)
        {
            if (ofd1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                txt_sourcefolder.Text = ofd1.FileName;
            }
        }

        private void btn_cancel_Click(object sender, EventArgs e)
        {            
            form.Show();
            Close();
        }

        private void btn_next_Click(object sender, EventArgs e)
        {
            lbl_msg.Text = "";
            string ret = Salvar();

            if (ret == "")
            {
                form.LabelText = "Sped inserido com sucesso!";
                form.Show();
                Close();
            }
            else
                lbl_msg.Text = "Erro ao ler Sped";
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

            if (txt_sourcefolder.Text != "")
            {
                string registro = null;
                string periodo = "";
                string cnpj = "";

                StreamReader sr = new StreamReader(txt_sourcefolder.Text, Encoding.Default);

                try
                {
                    while (periodo == "")
                    {
                        registro = sr.ReadLine();

                        if (registro.Contains("|0000|"))
                        {
                            string[] campo = registro.Split('|');

                            if (campo[1] == "0000")
                            {
                                periodo = campo[4].Remove(0, 2);
                                cnpj = campo[7];
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    ret = ex.Message;

                    sr.Close();
                }

                string mes_sped = periodo.Remove(2);
                string ano_sped = periodo.Remove(0, 2);

                if (cnpj != cliente_cnpj)
                    ret = "O contribuinte desse SPED não possui o CNPJ " + cliente_cnpj;
                else if (mes != mes_sped || ano != ano_sped)
                {
                    ret = "Os períodos selecionado e o do arquivo são diferentes. Período do arquivo: " + mes_sped + "/" + ano_sped;
                }

                if (ret != "")
                {
                    sr.Close();
                }
            }
            else
                ret = "Selecione um arquivo Sped";

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

            string registro = null;
            string periodo = "";
            bool limpa_0000 = false;
            bool limpa_0005 = false;
            bool limpa_0150 = false;
            bool limpa_0200 = false;
            bool limpa_C100 = false;
            bool limpa_C113 = false;
            bool limpa_C170 = false;
            bool limpa_C190 = false;
            string num_doc = "";

            StreamReader sr = new StreamReader(txt_sourcefolder.Text, Encoding.Default);
            Classes.Auxiliar aux = new Classes.Auxiliar();

            try
            {
                while ((registro = sr.ReadLine()) != null)
                {
                    //txt_obs.Text += registro + Environment.NewLine;

                    if (registro.Contains("|0000|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "0000")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.ENTIDADE_0000 par = new EF.ENTIDADE_0000();

                                #region Parametros

                                par.REG = campo[1];
                                par.COD_VER = campo[2];
                                par.COD_FIN = campo[3];
                                par.DT_INI = campo[4];
                                par.DT_FIN = campo[5];
                                par.NOME = campo[6];
                                par.CNPJ = campo[7];
                                par.CPF = campo[8];
                                par.UF = campo[9];
                                par.IE = campo[10];
                                par.COD_MUN = campo[11];
                                par.IM = campo[12];
                                par.SUFRAMA = campo[13];
                                par.IND_PERFIL = campo[14];
                                par.IND_ATIV = campo[15];
                                periodo = campo[4].Remove(0, 2);
                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_0000)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM ENTIDADE_0000 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ENTIDADE_0000', RESEED, 0)");
                                    limpa_0000 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|0005|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "0005")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.ENTIDADE_COMPL_0005 par = new EF.ENTIDADE_COMPL_0005();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.FANTASIA = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.CEP = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.END = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.NUM = campo[5];

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.COMPL = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.BAIRRO = campo[7];

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.FONE = campo[8];

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.FAX = campo[9];

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.EMAIL = campo[10];

                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_0005)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM ENTIDADE_COMPL_0005 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ENTIDADE_COMPL_0005', RESEED, 0)");
                                    limpa_0005 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|0150|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "0150")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.PARTICIPANTE_0150 par = new EF.PARTICIPANTE_0150();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.COD_PART = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.NOME = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.COD_PAIS = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.CNPJ = campo[5];

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.CPF = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.IE = campo[7];

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.COD_MUN = campo[8];

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.SUFRAMA = campo[9];

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.END = campo[10];

                                if (!string.IsNullOrEmpty(campo[11]))
                                    par.NUM = campo[11];

                                if (!string.IsNullOrEmpty(campo[12]))
                                    par.COMPL = campo[12];

                                if (!string.IsNullOrEmpty(campo[13]))
                                    par.BAIRRO = campo[13];

                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_0150)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM PARTICIPANTE_0150 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('PARTICIPANTE_0150', RESEED, 0)");
                                    limpa_0150 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|0200|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "0200")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.ITEM_0200 par = new EF.ITEM_0200();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.COD_ITEM = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.DESCR_ITEM = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.COD_BARRA = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.COD_ANT_ITEM = campo[5];

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.UNID_INV = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.TIPO_ITEM = campo[7];

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.COD_NCM = campo[8];

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.EX_IPI = campo[9];

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.COD_GEN = campo[10];

                                if (!string.IsNullOrEmpty(campo[11]))
                                    par.COD_LST = campo[11];

                                if (!string.IsNullOrEmpty(campo[12]))
                                    par.ALIQ_ICMS = Convert.ToDecimal(campo[12]);

                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_0200)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM ITEM_0200 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ITEM_0200', RESEED, 0)");
                                    limpa_0200 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|C100|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "C100")
                        {
                            num_doc = campo[8];
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.DOC_FISCAL_C100 par = new EF.DOC_FISCAL_C100();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.IND_OPER = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.IND_EMIT = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.COD_PART = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.COD_MOD = campo[5];

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.COD_SIT = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.SER = campo[7];

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.NUM_DOC = campo[8];

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.CHV_NFE = campo[9];

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.DT_DOC = campo[10];

                                if (!string.IsNullOrEmpty(campo[11]))
                                    par.DT_E_S = campo[11];

                                if (!string.IsNullOrEmpty(campo[12]))
                                    par.VL_DOC = Convert.ToDecimal(campo[12]);

                                if (!string.IsNullOrEmpty(campo[13]))
                                    par.IND_PGTO = campo[13];

                                if (!string.IsNullOrEmpty(campo[14]))
                                    par.VL_DESC = Convert.ToDecimal(campo[14]);

                                if (!string.IsNullOrEmpty(campo[15]))
                                    par.VL_ABAT_NT = Convert.ToDecimal(campo[15]);

                                if (!string.IsNullOrEmpty(campo[16]))
                                    par.VL_MERC = Convert.ToDecimal(campo[16]);

                                if (!string.IsNullOrEmpty(campo[17]))
                                    par.IND_FRT = campo[17];

                                if (!string.IsNullOrEmpty(campo[18]))
                                    par.VL_FRT = Convert.ToDecimal(campo[18]);

                                if (!string.IsNullOrEmpty(campo[19]))
                                    par.VL_SEG = Convert.ToDecimal(campo[19]);

                                if (!string.IsNullOrEmpty(campo[20]))
                                    par.VL_OUT_DA = Convert.ToDecimal(campo[20]);

                                if (!string.IsNullOrEmpty(campo[21]))
                                    par.VL_BC_ICMS = Convert.ToDecimal(campo[21]);

                                if (!string.IsNullOrEmpty(campo[22]))
                                    par.VL_ICMS = Convert.ToDecimal(campo[22]);

                                if (!string.IsNullOrEmpty(campo[23]))
                                    par.VL_BC_ICMS_ST = Convert.ToDecimal(campo[23]);

                                if (!string.IsNullOrEmpty(campo[24]))
                                    par.VL_ICMS_ST = Convert.ToDecimal(campo[24]);

                                if (!string.IsNullOrEmpty(campo[25]))
                                    par.VL_IPI = Convert.ToDecimal(campo[25]);

                                if (!string.IsNullOrEmpty(campo[26]))
                                    par.VL_PIS = Convert.ToDecimal(campo[26]);

                                if (!string.IsNullOrEmpty(campo[27]))
                                    par.VL_COFINS = Convert.ToDecimal(campo[27]);

                                if (!string.IsNullOrEmpty(campo[28]))
                                    par.VL_PIS_ST = Convert.ToDecimal(campo[28]);

                                if (!string.IsNullOrEmpty(campo[29]))
                                    par.VL_COFINS_ST = Convert.ToDecimal(campo[29]);

                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_C100)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM DOC_FISCAL_C100 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('DOC_FISCAL_C100', RESEED, 0)");
                                    limpa_C100 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|C113|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "C113")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.DEVOLUCAO_C113 par = new EF.DEVOLUCAO_C113();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.IND_OPER = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.IND_EMIT = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.COD_PART = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.COD_MOD = campo[5];

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.SER = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.SUB = campo[7];

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.NUM_DOC = campo[8];

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.DT_DOC = campo[9];

                                par.PERIODO = periodo;

                                #endregion

                                if (!limpa_C113)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM DEVOLUCAO_C113 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('DOC_FISCAL_C100', RESEED, 0)");
                                    limpa_C113 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|C170|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "C170")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.ITENS_DOC_C170 par = new EF.ITENS_DOC_C170();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.NUM_ITEM = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.COD_ITEM = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.DESCR_COMPL = campo[4];

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.QTD = Convert.ToDecimal(campo[5]);

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.UNID = campo[6];

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.VL_ITEM = Convert.ToDecimal(campo[7]);

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.VL_DESC = Convert.ToDecimal(campo[8]);

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.IND_MOV = campo[9];

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.CST_ICMS = campo[10];

                                if (!string.IsNullOrEmpty(campo[11]))
                                    par.CFOP = campo[11];

                                if (!string.IsNullOrEmpty(campo[12]))
                                    par.COD_NAT = campo[12];

                                if (!string.IsNullOrEmpty(campo[13]))
                                    par.VL_BC_ICMS = Convert.ToDecimal(campo[13]);

                                if (!string.IsNullOrEmpty(campo[14]))
                                    par.ALIQ_ICMS = Convert.ToDecimal(campo[14]);

                                if (!string.IsNullOrEmpty(campo[15]))
                                    par.VL_ICMS = Convert.ToDecimal(campo[15]);

                                if (!string.IsNullOrEmpty(campo[16]))
                                    par.VL_BC_ICMS_ST = Convert.ToDecimal(campo[16]);

                                if (!string.IsNullOrEmpty(campo[17]))
                                    par.ALIQ_ST = Convert.ToDecimal(campo[17]);

                                if (!string.IsNullOrEmpty(campo[18]))
                                    par.VL_ICMS_ST = Convert.ToDecimal(campo[18]);

                                if (!string.IsNullOrEmpty(campo[19]))
                                    par.IND_APUR = campo[19];

                                if (!string.IsNullOrEmpty(campo[20]))
                                    par.CST_IPI = campo[20];

                                if (!string.IsNullOrEmpty(campo[21]))
                                    par.COD_ENQ = campo[21];

                                if (!string.IsNullOrEmpty(campo[22]))
                                    par.VL_BC_IPI = Convert.ToDecimal(campo[22]);

                                if (!string.IsNullOrEmpty(campo[23]))
                                    par.ALIQ_IPI = Convert.ToDecimal(campo[23]);

                                if (!string.IsNullOrEmpty(campo[24]))
                                    par.VL_IPI = Convert.ToDecimal(campo[24]);

                                if (!string.IsNullOrEmpty(campo[25]))
                                    par.CST_PIS = campo[25];

                                if (!string.IsNullOrEmpty(campo[26]))
                                    par.VL_BC_PIS = Convert.ToDecimal(campo[26]);

                                if (!string.IsNullOrEmpty(campo[27]))
                                    par.ALIQ_PIS_PCT = Convert.ToDecimal(campo[27]);

                                if (!string.IsNullOrEmpty(campo[28]))
                                    par.QUANT_BC_PIS = Convert.ToDecimal(campo[28]);

                                if (!string.IsNullOrEmpty(campo[29]))
                                    par.ALIQ_PIS = Convert.ToDecimal(campo[29]);

                                if (!string.IsNullOrEmpty(campo[30]))
                                    par.VL_PIS = Convert.ToDecimal(campo[30]);

                                if (!string.IsNullOrEmpty(campo[31]))
                                    par.CST_COFINS = campo[31];

                                if (!string.IsNullOrEmpty(campo[32]))
                                    par.VL_BC_COFINS = Convert.ToDecimal(campo[32]);

                                if (!string.IsNullOrEmpty(campo[33]))
                                    par.ALIQ_COFINS_PCT = Convert.ToDecimal(campo[33]);

                                if (!string.IsNullOrEmpty(campo[34]))
                                    par.QUANT_BC_COFINS = Convert.ToDecimal(campo[34]);

                                if (!string.IsNullOrEmpty(campo[35]))
                                    par.ALIQ_COFINS = Convert.ToDecimal(campo[35]);

                                if (!string.IsNullOrEmpty(campo[36]))
                                    par.VL_COFINS = Convert.ToDecimal(campo[36]);

                                if (!string.IsNullOrEmpty(campo[37]))
                                    par.COD_CTA = campo[37];

                                par.PERIODO = periodo;

                                par.NUM_DOC = num_doc;

                                #endregion

                                if (!limpa_C170)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM ITENS_DOC_C170 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ITENS_DOC_C170', RESEED, 0)");
                                    limpa_C170 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                    else if (registro.Contains("|C190|"))
                    {
                        string[] campo = registro.Split('|');

                        if (campo[1] == "C190")
                        {
                            using (var cnx = new EF.ef_dbContainer())
                            {
                                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(cliente);

                                EF.ANAL_DOC_C190 par = new EF.ANAL_DOC_C190();

                                #region Parametros

                                par.REG = campo[1];

                                if (!string.IsNullOrEmpty(campo[2]))
                                    par.CST_ICMS = campo[2];

                                if (!string.IsNullOrEmpty(campo[3]))
                                    par.CFOP = campo[3];

                                if (!string.IsNullOrEmpty(campo[4]))
                                    par.ALIQ_ICMS = Convert.ToDecimal(campo[4]);

                                if (!string.IsNullOrEmpty(campo[5]))
                                    par.VL_OPR = Convert.ToDecimal(campo[5]);

                                if (!string.IsNullOrEmpty(campo[6]))
                                    par.VL_BC_ICMS = Convert.ToDecimal(campo[6]);

                                if (!string.IsNullOrEmpty(campo[7]))
                                    par.VL_ICMS = Convert.ToDecimal(campo[7]);

                                if (!string.IsNullOrEmpty(campo[8]))
                                    par.VL_BC_ICMS_ST = Convert.ToDecimal(campo[8]);

                                if (!string.IsNullOrEmpty(campo[9]))
                                    par.VL_ICMS_ST = Convert.ToDecimal(campo[9]);

                                if (!string.IsNullOrEmpty(campo[10]))
                                    par.VL_RED_BC = Convert.ToDecimal(campo[10]);

                                if (!string.IsNullOrEmpty(campo[11]))
                                    par.VL_IPI = Convert.ToDecimal(campo[11]);

                                if (!string.IsNullOrEmpty(campo[12]))
                                    par.COD_OBS = campo[12];

                                par.PERIODO = periodo;

                                par.NUM_DOC = num_doc;

                                #endregion

                                if (!limpa_C190)
                                {
                                    cnx.Database.ExecuteSqlCommand("DELETE FROM ANAL_DOC_C190 WHERE PERIODO = @PERIODO", new SqlParameter("@PERIODO", periodo));
                                    //cnx.Database.ExecuteSqlCommand("DBCC CHECKIDENT ('ANAL_DOC_C190', RESEED, 0)");
                                    limpa_C190 = true;
                                }

                                cnx.Entry(par).State = System.Data.Entity.EntityState.Added;
                                cnx.SaveChanges();
                            }
                        }
                    }
                }

                sr.Close();
            }
            catch (Exception ex)
            {
                ret = ex.Message;

                sr.Close();
            }

            trataComponentes();

            return ret;
        }

        #endregion
                
    }
}
