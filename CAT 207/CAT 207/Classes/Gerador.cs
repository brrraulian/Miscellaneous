using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CAT_207.Classes
{
    class Gerador
    {
        public string GeraArquivo(string par_mes, string par_ano, string par_cliente, string conexao, decimal iva, decimal pmc, string CNAE)
        {
            string ret = "Arquivo gerado com sucesso!";
            TextWriter tw = null;
            Classes.Auxiliar aux = new Auxiliar();

            try
            {
                StringBuilder sb = new StringBuilder();

                string periodo = par_mes + par_ano;
                string path = Path.GetDirectoryName(Application.ExecutablePath).Replace(@"\bin\Debug", @"\Classes\Arquivos Gerados\");

                if (System.IO.File.Exists(path + periodo + "_cat207.txt"))
                {
                    System.IO.File.Delete(path + periodo + "_cat207.txt");
                }

                tw = new StreamWriter(path + periodo + "_cat207.txt");

                using (var cnx = new EF.ef_dbContainer())
                {
                    cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(par_cliente);

                    // BLOCO 0
                    #region BLOCO_0

                    #region Gets_0

                    int count_0 = 0;
                    string tem_dados_0 = "1";
                    int count_0000 = 0;
                    int count_0001 = 0;
                    int count_0150 = 0;
                    int count_0300 = 0;
                    int count_0990 = 0;
                    bool entidade_cadastrada = false;

                    #endregion

                    var entidade = cnx.ENTIDADE_0000.Where(p => p.PERIODO == periodo).FirstOrDefault();
                    var entidade_compl = cnx.ENTIDADE_COMPL_0005.Where(p => p.PERIODO == periodo).FirstOrDefault();
                    
                    // REGISTRO 0000: ABERTURA DO ARQUIVO DIGITAL E IDENTIFICAÇÃO DO CONTRIBUINTE
                    sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}", "0000", "LASIMCA", "01", "01", periodo, entidade.NOME, entidade.CNPJ, entidade.IE, CNAE, entidade.COD_MUN, entidade.IE);
                    sb.Append("\r\n");
                    count_0++;
                    count_0000++;

                    int qtd_participantes = cnx.PARTICIPANTE_0150.Count();

                    if (qtd_participantes > 0)
                        tem_dados_0 = "0";
                    else
                        tem_dados_0 = "1";

                    // REGISTRO 0001: ABERTURA DO BLOCO 0
                    sb.AppendFormat("{0}|{1}", "0001", tem_dados_0);
                    sb.Append("\r\n");
                    count_0++;
                    count_0001++;

                    DAL.Bloco0.Participante part = new DAL.Bloco0.Participante();
                    DataTable dt_participante = part.GetParticipantes(periodo, conexao);


                    for(int k = 0; k < dt_participante.Rows.Count; k++)
                    {
                        // REGISTRO 0150: CADASTRO DE PARTICIPANTES DE OPERAÇÕES E PRESTAÇÕES

                        if (dt_participante.Rows[k]["COD_PART"].ToString() != "" && dt_participante.Rows[k]["NOME"].ToString() != "" && dt_participante.Rows[k]["COD_PAIS"].ToString() != null)
                        {
                            string cod_pais = dt_participante.Rows[k]["COD_PAIS"].ToString();
                            if (cod_pais.Length < 5)
                            {
                                for (int i = 0; i < 5 - dt_participante.Rows[k]["COD_PAIS"].ToString().Length; i++)
                                    cod_pais = "0" + cod_pais;
                            }

                            string cnpj_cpf = dt_participante.Rows[k]["CNPJ"].ToString();
                            if (cnpj_cpf == null || cnpj_cpf.Trim() == "")
                            {
                                cnpj_cpf = dt_participante.Rows[k]["CPF"].ToString();

                                if (cnpj_cpf != null || cnpj_cpf == "")
                                {
                                    if (cnpj_cpf.Length < 11)
                                    {
                                        for (int i = 0; i < 11 - dt_participante.Rows[k]["CPF"].ToString().Length; i++)
                                            cnpj_cpf = "0" + cnpj_cpf;
                                    }
                                }
                                else
                                    cnpj_cpf = "";
                            }
                            else if (cnpj_cpf.Length < 14)
                            {
                                for (int i = 0; i < 14 - dt_participante.Rows[k]["CNPJ"].ToString().Length; i++)
                                    cnpj_cpf = "0" + cnpj_cpf;
                            }

                            string NUM = "";
                            if (dt_participante.Rows[k]["NUM"].ToString() == null || dt_participante.Rows[k]["NUM"].ToString().Trim() == "")
                                NUM = "S/N";
                            else
                                NUM = dt_participante.Rows[k]["NUM"].ToString();

                            string CEP = "";
                            if (dt_participante.Rows[k]["CEP"].ToString() == null || dt_participante.Rows[k]["CEP"].ToString().Trim() == "")
                                CEP = "00000000";
                            else
                                CEP = dt_participante.Rows[k]["CEP"].ToString();

                            CEP = CEP.Trim();

                            string IE = "";
                            if (dt_participante.Rows[k]["IE"].ToString() == null || dt_participante.Rows[k]["IE"].ToString().Trim() == "")
                                IE = "000000000000";
                            else
                                IE = dt_participante.Rows[k]["IE"].ToString();

                            IE = IE.Trim();

                            if (cod_pais == "01058")
                            {
                                sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}CRLF",
                                "0150", dt_participante.Rows[k]["COD_PART"].ToString(), dt_participante.Rows[k]["NOME"].ToString(), cod_pais, cnpj_cpf, IE, dt_participante.Rows[k]["UF"].ToString(),
                                CEP, dt_participante.Rows[k]["END"].ToString(), NUM, dt_participante.Rows[k]["COMPL"].ToString(), dt_participante.Rows[k]["BAIRRO"].ToString(),
                                dt_participante.Rows[k]["COD_MUN"].ToString(), "");
                            }
                            else
                            {
                                sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}",
                                "0150", dt_participante.Rows[k]["COD_PART"].ToString(), dt_participante.Rows[k]["NOME"].ToString(), cod_pais, "", "", "",
                                "", "", "", "", "", "", "");
                            }

                            sb.Append("\r\n");
                            count_0++;
                            count_0150++;

                            if (dt_participante.Rows[k]["CNPJ"].ToString() == entidade.CNPJ)
                                entidade_cadastrada = true;
                        }
                    }

                    if (!entidade_cadastrada)
                    {
                        string NUM = "";
                            if (entidade_compl.NUM == null || entidade_compl.NUM.Trim() == "")
                                NUM = "S/N";
                            else
                                NUM = entidade_compl.NUM;

                        // ENTIDADE
                        sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}CRLF",
                                "0150", "0000000001", entidade.NOME, "01058", entidade.CNPJ, entidade.IE, entidade.UF,
                                entidade_compl.CEP, entidade_compl.END, NUM, entidade_compl.COMPL, entidade_compl.BAIRRO, entidade.COD_MUN, entidade_compl.FONE);
                        sb.Append("\r\n");
                        count_0++;
                        count_0150++;
                    }

                    var qtd_enquadramento = cnx.ENQUADRAMENTO_5D.Count();
                    var enquadramento = cnx.ENQUADRAMENTO_5D.Where(p => p.PERIODO == periodo);

                    foreach (var item in enquadramento)
                    {
                        string cod_enq = item.COD_ENQUADRAMENTO.ToString();
                        if (cod_enq.Length < 4)
                        {
                            for (int i = 0; i < 4 - item.COD_ENQUADRAMENTO.ToString().Length; i++)
                                cod_enq = "0" + cod_enq;
                        }

                        string desc = item.COD_HIP_GER.ToString();
                        if (desc.Length < 2)
                            desc = "0" + desc;

                        // REGISTRO 0300: ENQUADRAMENTO LEGAL DA OPERAÇÃO/PRESTAÇÃO GERADORA DE CRÉDITO ACUMULADO DO ICMS
                        sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}CRLF",
                                    "0300", cod_enq, desc, item.ANEXO, item.ARTIGO,
                                    item.INCISO, item.ALINEA, item.PARAGRAFO, item.ITEM, item.LETRA, item.OBS);

                        sb.Append("\r\n");
                        count_0++;
                        count_0300++;
                    }

                    // REGISTRO 0990: ENCERRAMENTO DO BLOCO 0
                    count_0++;
                    sb.AppendFormat("{0}|{1}", "0990", count_0.ToString());
                    sb.Append("\r\n");
                    count_0990++;

                    #endregion

                    // BLOCO 5

                    #region BLOCO_5

                    #region Gets_5

                    int count_5 = 0;
                    int count_5001 = 0;
                    int count_5315 = 0;
                    int count_5320 = 0;
                    int count_5325 = 0;
                    int count_5330 = 0;
                    int count_5335 = 0;
                    int count_5340 = 0;
                    int count_5350 = 0;
                    int count_5990 = 0;
                    string tem_dados_5 = "1";
                    decimal perc_crd_out = 0;
                    decimal valor_crd_out = 0;
                    decimal icms_total = 0;
                    string st_perc_crdout = "0,00";
                    string st_valor_crdout = "0,00";
                    string comprov_op = "1";
                    string num_decl_exp_ind = "";

                    // REGISTRO 5315: OPERAÇÕES DE SAÍDA
                    var c100 = cnx.v_150_C100_C190_ENQ_OP.Where(p => p.PERIODO == periodo);

                    // REGISTRO 5320 DEVOLUÇÃO DE SAÍDA
                    string[] cfop_devolucao = { "52", "53", "54", "55", "56", "59" };
                    var c190_devolucao = cnx.ANAL_DOC_C190.Where(p => p.PERIODO == periodo && cfop_devolucao.Contains(p.CFOP)).Select(p => p.NUM_DOC);
                    var c100_devolucao = cnx.DOC_FISCAL_C100.Where(p => p.PERIODO == periodo && p.IND_OPER == "0" && c190_devolucao.Contains(p.NUM_DOC));
                    var c113 = cnx.DEVOLUCAO_C113.Where(p => p.PERIODO == periodo);

                    #endregion

                    // REGISTRO 5001: ABERTURA DO BLOCO 5
                    if (c100.Count() > 0)
                        tem_dados_5 = "0";
                    else
                        tem_dados_5 = "1";

                    sb.AppendFormat("{0}|{1}", "5001", tem_dados_5);
                    sb.Append("\r\n");
                    count_5++;
                    count_5001++;

                    foreach (var item in c100)
                    {
                        if (item != null)
                        {
                            // REGISTRO 5315: OPERAÇÕES DE SAÍDA
                            string tip_doc = item.COD_MOD;
                            if (item.COD_MOD == "55")
                                tip_doc = "31";

                            if ((item.DT_DOC != "" && item.DT_DOC != null) && (item.COD_PART != "" && item.COD_PART != null) && (item.VL_DOC.ToString() != "" && item.VL_DOC != null))
                            {
                                string SER = item.SER;
                                if (SER == null)
                                    SER = "1";

                                sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}",
                                        "5315", item.DT_DOC, tip_doc, SER, item.NUM_DOC, item.COD_PART, item.VL_DOC.ToString().Replace(".", ","), st_perc_crdout.ToString().Replace(".", ","), 
										st_valor_crdout.ToString().Replace(".", ","));
                                sb.Append("\r\n");
                                count_5++;
                                count_5315++;

                                // REGISTRO 5320 DEVOLUÇÃO DE SAÍDA
                                foreach (var item_devolucao in c100_devolucao)
                                {
                                    if (item.NUM_DOC == item_devolucao.NUM_DOC)
                                    {
                                        sb.AppendFormat("{0}|{1}|{2}|{3}|{4}CRLF", "5320", item_devolucao.DT_DOC, item_devolucao.COD_MOD, item_devolucao.SER, item_devolucao.NUM_DOC);
                                        sb.Append("\r\n");
                                        count_5++;
                                        count_5320++;
                                    }
                                }

                                foreach (var item_c113 in c113)
                                {
                                    if (item.NUM_DOC == item_c113.NUM_DOC)
                                    {
                                        sb.AppendFormat("{0}|{1}|{2}|{3}|{4}CRLF", "5320", item_c113.DT_DOC, item_c113.COD_MOD, item_c113.SER, item_c113.NUM_DOC);
                                        sb.Append("\r\n");
                                        count_5++;
                                        count_5320++;
                                    }
                                }


                                decimal cred_est_icms = Math.Round((Convert.ToDecimal(item.VL_DOC) / (1 + Convert.ToDecimal(iva))) * Convert.ToDecimal(pmc) / 100, 2);
                                decimal icms_gera = 0;
                                string cod_legal = "";

                                var enq_op = cnx.ENQ_OP.Where(p => p.PERIODO == periodo && p.NUM_DOC == item.NUM_DOC && p.SER == item.SER).FirstOrDefault();

                                if (enq_op == null)
                                    cod_legal = "";
                                else
                                {
                                    if (enq_op.COD_ENQUADRAMENTO == null)
                                        cod_legal = "";
                                    else
                                    {
                                        cod_legal = enq_op.COD_ENQUADRAMENTO.ToString();

                                        if (cod_legal.Length < 4)
                                        {
                                            for (int i = 0; i < 4 - enq_op.COD_ENQUADRAMENTO.ToString().Length; i++)
                                                cod_legal = "0" + cod_legal;
                                        }
                                    }
                                }

                                if (item.COD_HIP_GER == 9 && item.ALIQ_ICMS == 0)
                                {
                                    int enq_op_count = cnx.ENQ_OP.Where(p => p.PERIODO == periodo && p.NUM_DOC == item.NUM_DOC && p.SER == item.SER).Count();

                                    if (enq_op_count > 0)
                                    {
                                        if (enq_op.COMPROV_OP == true)
                                        {
                                            icms_gera = cred_est_icms + valor_crd_out;
                                            comprov_op = "0";
                                        }
                                        else
                                        {
                                            icms_gera = Math.Round(Convert.ToDecimal("0"), 2);
                                            comprov_op = "1";
                                        }
                                    }

                                    // REGISTRO 5325 OPERAÇÕES GERADORAS DE CRÉDITO ACUMULADO
                                    sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}", "5325", cod_legal, String.Format("{0:0.0000}", iva).ToString().Replace(".", ","), pmc.ToString().Replace(".", ","), 
									cred_est_icms.ToString().Replace(".", ","), icms_gera.ToString().Replace(".", ","));
                                    sb.Append("\r\n");
                                    count_5++;
                                    count_5325++;


                                    // REGISTRO 5335 OPERAÇÕES GERADORAS APURADAS NA FICHA 6C OU 6D
                                    sb.AppendFormat("{0}|{1}|{2}", "5335", "", comprov_op);
                                    sb.Append("\r\n");
                                    count_5++;
                                    count_5335++;
                                }
                                else if (item.COD_HIP_GER == 10 || item.COD_HIP_GER == 11)
                                {
                                    icms_gera = cred_est_icms + valor_crd_out;

                                    // REGISTRO 5325 OPERAÇÕES GERADORAS DE CRÉDITO ACUMULADO
                                    sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}", "5325", cod_legal, String.Format("{0:0.0000}", iva).ToString().Replace(".", ","), pmc.ToString().Replace(".", ","), 
									cred_est_icms.ToString().Replace(".", ","), icms_gera.ToString().Replace(".", ","));
                                    sb.Append("\r\n");
                                    count_5++;
                                    count_5325++;
                                }
                                else if (item.COD_HIP_GER == 1 || item.COD_HIP_GER == 2 || item.COD_HIP_GER == 5 || item.COD_HIP_GER == 6)
                                {
                                    string VL_BC_ICMS = item.VL_BC_ICMS.ToString().Replace(".", ",");
                                    if (VL_BC_ICMS == "")
                                        VL_BC_ICMS = "0,00";

                                    string VL_ICMS = item.VL_ICMS.ToString().Replace(".", ",");
                                    if (VL_ICMS == "")
                                        VL_ICMS = "0,00";
                                    else
                                        VL_ICMS = String.Format("{0:0.00}", (Convert.ToDecimal(VL_BC_ICMS) * (Convert.ToDecimal(item.ALIQ_ICMS) / 100))).ToString().Replace(".", ",");

                                    icms_total = cred_est_icms + valor_crd_out;
                                    icms_gera = icms_total - (Convert.ToDecimal(VL_BC_ICMS) * (Convert.ToDecimal(item.ALIQ_ICMS) / 100));

                                    if (icms_gera >= 0 && (item.ALIQ_ICMS == 4 || item.ALIQ_ICMS == 7 || item.ALIQ_ICMS == 12) && (VL_BC_ICMS != "0,00" && VL_ICMS != "0,00"))
                                    {
                                        // 6A
                                        // REGISTRO 5325 OPERAÇÕES GERADORAS DE CRÉDITO ACUMULADO - FICHA 6A
                                        sb.AppendFormat("{0}|{1}|{2}|{3}|{4}|{5}",
                                            "5325", cod_legal, String.Format("{0:0.0000}", iva).ToString().Replace(".", ","), pmc.ToString().Replace(".", ","), cred_est_icms.ToString().Replace(".", ","),
                                            String.Format("{0:0.00}", icms_gera).ToString().Replace(".", ","));
                                        sb.Append("\r\n");
                                        count_5++;
                                        count_5325++;

                                        // REGISTRO 5330 OPERAÇÕES GERADORAS APURADAS NAS FICHAS 6A OU 6B
                                        sb.AppendFormat("{0}|{1}|{2}", "5330", VL_BC_ICMS, VL_ICMS);
                                        sb.Append("\r\n");
                                        count_5++;
                                        count_5330++;
                                    }
                                    else
                                    {
                                        // REGISTRO 5350: OPERAÇÕES NÃO GERADORAS DE CRÉDITO ACUMULADO – FICHA 6A -> FICHA 6F                                
                                        sb.AppendFormat("{0}|{1}|{2}|{3}CRLF", "5350", VL_BC_ICMS, VL_ICMS, num_decl_exp_ind);
                                        sb.Append("\r\n");
                                        count_5++;
                                        count_5350++;
                                    }
                                }
                                else
                                {
                                    string VL_BC_ICMS = item.VL_BC_ICMS.ToString().Replace(".", ",");
                                    if (VL_BC_ICMS == "")
                                        VL_BC_ICMS = "0,00";

                                    string VL_ICMS = item.VL_ICMS.ToString().Replace(".", ",");
                                    if (VL_ICMS == "")
                                        VL_ICMS = "0,00";
                                    else
                                        VL_ICMS = String.Format("{0:0.00}", (Convert.ToDecimal(VL_BC_ICMS) * (Convert.ToDecimal(item.ALIQ_ICMS) / 100))).ToString().Replace(".", ",");

                                    // REGISTRO 5350: OPERAÇÕES NÃO GERADORAS DE CRÉDITO ACUMULADO – FICHA 6F                                
                                    sb.AppendFormat("{0}|{1}|{2}|{3}CRLF", "5350", VL_BC_ICMS, VL_ICMS, num_decl_exp_ind);
                                    sb.Append("\r\n");
                                    count_5++;
                                    count_5350++;

                                }
                            }
                        }
                    }

                    // REGISTRO 5990: ENCERRAMENTO DO BLOCO 5
                    count_5++;
                    sb.AppendFormat("{0}|{1}", "5990", count_5.ToString());
                    sb.Append("\r\n");
                    count_5990++;


                    #endregion


                    // BLOCO 9

                    #region BLOCO_9

                    int count_9 = 0;
                    string tem_dados_9 = "0";

                    // REGISTRO 9001: ABERTURA DO BLOCO 9
                    sb.AppendFormat("{0}|{1}", "9001", tem_dados_9);
                    sb.Append("\r\n");
                    count_9++;

                    ArrayList registros = new ArrayList();
                    ArrayList count_registros = new ArrayList();

                    registros.Add("0000");
                    registros.Add("0001");
                    registros.Add("0150");

                    if (count_0300 > 0)
                        registros.Add("0300");

                    registros.Add("0990");
                    registros.Add("5001");
                    registros.Add("5315");

                    if (count_5320 > 0)
                        registros.Add("5320");

                    if (count_5325 > 0)
                        registros.Add("5325");

                    if (count_5330 > 0)
                        registros.Add("5330");

                    if (count_5335 > 0)
                        registros.Add("5335");

                    if (count_5350 > 0)
                        registros.Add("5350");

                    registros.Add("5990");
                    registros.Add("9001");
                    registros.Add("9900");
                    registros.Add("9990");
                    registros.Add("9999");
                    count_registros.Add(count_0000);
                    count_registros.Add(count_0001);
                    count_registros.Add(count_0150);

                    if (count_0300 > 0)
                        count_registros.Add(count_0300);

                    count_registros.Add(count_0990);
                    count_registros.Add(count_5001);
                    count_registros.Add(count_5315);

                    if (count_5320 > 0)
                        count_registros.Add(count_5320);
                    
                    if (count_5325 > 0)
                        count_registros.Add(count_5325);

                    if (count_5330 > 0)
                        count_registros.Add(count_5330);

                    if (count_5335 > 0)
                        count_registros.Add(count_5335);

                    if (count_5350 > 0)
                        count_registros.Add(count_5350);

                    count_registros.Add(count_5990);
                    count_registros.Add(1);
                    count_registros.Add(0);
                    count_registros.Add(1);
                    count_registros.Add(1);



                    for (int i = 0; i < registros.Count; i++)
                    {
                        if (registros[i] != "9900")
                            sb.AppendFormat("{0}|{1}|{2}", "9900", registros[i], count_registros[i].ToString());
                        else
                            sb.AppendFormat("{0}|{1}|{2}", "9900", registros[i], registros.Count.ToString());

                        sb.Append("\r\n");
                        count_9++;
                    }


                    // REGISTRO 9990: ENCERRAMENTO DO BLOCO 9
                    count_9++;
                    sb.AppendFormat("{0}|{1}", "9990", (count_9 + 1).ToString());
                    sb.Append("\r\n");


                    // REGISTRO 9999: ENCERRAMENTO DO ARQUIVO DIGITAL
                    sb.AppendFormat("{0}|{1}", "9999", (count_0 + count_5 + count_9 + 1).ToString());


                    #endregion

                    tw.Write(sb);
                    sb.Clear();
                    tw.Close();

                    bool arq_ger_existe = true;
                    var arq_ger = cnx.ARQUIVOS_GERADOS.Where(p => p.PERIODO_ARQUIVO == periodo).FirstOrDefault();

                    if (arq_ger == null)
                    {
                        arq_ger_existe = false;
                        arq_ger = new EF.ARQUIVOS_GERADOS();
                    }

                    arq_ger.ARQUIVO = path + periodo + "_cat207.txt";
                    arq_ger.IVA = iva;
                    arq_ger.PMC = pmc;
                    arq_ger.PERIODO_ARQUIVO = periodo;
                    arq_ger.PERIODO_GERACAO = DateTime.Now.ToString("MMyyyy");

                    if (!arq_ger_existe)
                        cnx.Entry(arq_ger).State = System.Data.Entity.EntityState.Added;

                    cnx.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                ret = "Erro na geração do arquivo";
                tw.Close();
            }

            return ret;
        }

    }
}
