using CAT_207.DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.Classes
{
    class Associador
    {
        public string Associar(string mes, string ano, string par_cliente, string conexao)
        {            
            string ret = "Gerando arquivo...";
            long COD_ENQUADRAMENTO_1 = 0;
            long COD_ENQUADRAMENTO_2 = 0;
            long COD_ENQUADRAMENTO_5 = 0;
            long COD_ENQUADRAMENTO_6 = 0;
            long COD_ENQUADRAMENTO_9 = 0;
            long COD_ENQUADRAMENTO_10 = 0;
            long COD_ENQUADRAMENTO_11 = 0;
            decimal icms_total = 0;
            decimal icms_gera = 0;
            decimal valor_crd_out = 0;

            string[] lista_cfop = { "6109", "6110" };
            Classes.Auxiliar aux = new Auxiliar();
            List<string> lista_cnpj = aux.GetCNPJ(mes, ano, conexao);
            string[] lista_uf_1 = { "AM", "RR", "AP", "PA", "TO", "RO", "AC", "MA", "PI", "CE", "RN", "PE", "PB", "SE", "AL", "BA", "ES" };
            string[] lista_uf_2 = { "RJ", "MG", "PR", "RS", "SC" };
            
            CAT_207.DAL.Bloco0.Enquadramento class_enquadramento = new CAT_207.DAL.Bloco0.Enquadramento();
            CAT_207.DAL.Bloco0.Enquadramento.st_enquadramento st_enquadramento;
            EnqOp class_enq_op = new EnqOp();
            EnqOp.st_enq_op st_enq_op;

            using (var cnx = new EF.ef_dbContainer())
            {
                cnx.Database.Connection.ConnectionString = aux.GetEFCnxByCliente(par_cliente);

                string periodo = mes + ano;

                string CNAE = aux.GetCNAEByCliente(par_cliente);                

                #region CALCULA IVA e PMC

                decimal saidas = 0;
                decimal entradas = 0;
                decimal pmc = 0;
                decimal iva_mediana = 0;

                #region Listas

                string[] lista_cfop_s1 = { "5.101", "5.102", "5.103", "5.104", "5.105", "5.106", 
                                 "5.109", "5.110", "5.115", "5.116", "5.117", "5.118", "5.119", "5.120", "5.122", "5.123", "5.917", "5.151", "5.152", "5.153", "5.155", "5.156", "5.251", "5.252", 
                                 "5.253", "5.254", "5.255", "5.256", "5.257", "5.258", "5.301", "5.302", "5.303", "5.304", "5.305", "5.306", "5.307", "5.351", "5.352", "5.353", "5.354", "5.355", 
                                 "5.356", "5.357", "5.359", "5.360", "5.401", "5.402", "5.403", "5.405", "5.651", "5.652", "5.653", "5.654", "5.655", "5.656", "5.408", "5.409", "5.658", "5.659", 
                                 "5.501", "5.502", "5.928", "5.910", "5.124", "5.125", "6.101", "6.102", "6.103", "6.104", "6.105", "6.106", "6.107", "6.108", "6.109", "6.110", "6.115", "6.116", 
                                 "6.117", "6.118", "6.119", "6.120", "6.122", "6.123", "6.917", "6.151", "6.152", "6.153", "6.155", "6.156", "6.251", "6.252", "6.253", "6.254", "6.255", "6.256", 
                                 "6.257", "6.258", "6.301", "6.302", "6.303", "6.304", "6.305", "6.306", "6.307", "6.351", "6.352", "6.353", "6.354", "6.355", "6.356", "6.357", "6.359", "6.401", 
                                 "6.402", "6.403", "6.404", "6.651", "6.652", "6.653", "6.654", "6.655", "6.656", "6.408", "6.409", "6.658", "6.659", "6.501", "6.502", "6.910", "6.124", "6.125", 
                                 "7.101", "7.102", "7.105", "7.106", "7.127", "7.651", "7.654", "7.251", "7.301", "7.358" };

                string[] lista_cfop_s2 = { "1.201", "1.202", "1.203", "1.204", "1.205", "1.206", "1.207", "1.208", "1.209",
                                "1.918", "1.410", "1.411", "1.660", "1.661", "1.662", "1.503", "1.504", "2.201", "2.202", "2.203", "2.204", "2.205", "2.206", "2.207", "2.208", "2.209", "2.918",
                                "2.410", "2.411", "2.660", "2.661", "2.662", "2.503", "2.504", "3.201", "3.202", "3.205", "3.206", "3.207", "3.211" };

                string[] lista_cfop_e1 = { "1.101", "1.102", "1.116", "1.117", "1.118", "1.120", "1.121", 
                                "1.122", "1.126", "1.917", "1.151", "1.152", "1.153", "1.154", "1.351", "1.352", "1.353", "1.354", "1.355", "1.356", "1.360", "1.931", "1.932", "1.401", "1.403", 
                                "1.651", "1.652", "1.408", "1.409", "1.658", "1.659", "1.910", "1.124", "1.125", "2.101", "2.102", "2.116", "2.117", "2.118", "2.120", "2.121", "2.122", "2.126", 
                                "2.917", "2.151", "2.152", "2.153", "2.154", "2.351", "2.352", "2.353", "2.354", "2.355", "2.356", "2.931", "2.932", "2.401", "2.403", "2.651", "2.652", "2.408", 
                                "2.409", "2.658", "2.659", "2.910", "2.124", "2.125", "3.101", "3.102", "3.126", "3.127", "3.651", "3.652", "3.351", "3.352", "3.353", "3.354", "3.355", "3.356", "3.930" };

                string[] lista_cfop_e2 = { "1.251", "1.252", "1.253", "1.254", "1.255", 
                                 "1.256", "1.257", "1.301", "1.302", "1.303", "1.304", "1.305", "1.306", "1.653", "2.251", "2.252", "2.253", "2.254", "2.255", "2.256", "2.257", "2.301", "2.302", 
                                 "2.303", "2.304", "2.305", "2.306", "2.653", "3.251", "3.301", "3.653" };

                string[] lista_cfop_e3 = { "5.201", "5.202", "5.205", "5.206", "5.207", "5.208", "5.209", 
                                "5.210", "5.918", "5.410", "5.411", "5.660", "5.661", "6.201", "6.202", "6.205", "6.206", "6.207", "6.208", "6.209", "6.210", "6.918", "6.410", "6.411", "6.660", 
                                "6.661", "7.201", "7.202", "7.205", "7.206", "7.207", "7.210", "7.211", "7.930" };

                string[] lista_cfop_e4 = { "5.662", "6.662" };

                string[] lista_cfop_pmc1 = { "1.101", "1.102", "1.116", "1.117", "1.118", "1.120", 
                                "1.121", "1.122", "1.126", "1.917", "1.151", "1.152", "1.153", "1.154", "1.251", "1.252", "1.253", "1.254", "1.255", "1.256", "1.257", "1.301", "1.302", "1.303", 
                                "1.304", "1.305", "1.306", "1.351", "1.352", "1.353", "1.354", "1.355", "1.356", "1.360", "1.931", "1.932", "1.401", "1.403", "1.651", "1.652", "1.408", "1.409", 
                                "1.658", "1.659", "1.653", "1.910", "1.124", "1.125", "2.101", "2.102", "2.116", "2.117", "2.118", "2.120", "2.121", "2.122", "2.126", "2.917", "2.151", "2.152", 
                                "2.153", "2.154", "2.251", "2.252", "2.253", "2.254", "2.255", "2.256", "2.257", "2.301", "2.302", "2.303", "2.304", "2.305", "2.306", "2.351", "2.352", "2.353", 
                                "2.354", "2.355", "2.356", "2.931", "2.932", "2.401", "2.403", "2.651", "2.652", "2.408", "2.409", "2.658", "2.659", "2.653", "2.910", "2.124", "2.125", "3.101", 
                                "3.102", "3.126", "3.127", "3.651", "3.652", "3.251", "3.301", "3.351", "3.352", "3.353", "3.354", "3.355", "3.356", "3.653", "3.930" };

                string[] lista_cfop_pmc2 = { "5.201", "5.202", "5.205", "5.206", "5.207", "5.208", "5.209", "5.210", "5.918", 
                                "5.410", "5.411", "5.660", "5.661", "5.662", "6.201", "6.202", "6.205", "6.206", "6.207", "6.208", "6.209", "6.210", "6.918", "6.410", "6.411", "6.660", "6.661", 
                                "6.662", "7.201", "7.202", "7.205", "7.206", "7.207", "7.210", "7.211", "7.930" };

                #endregion

                decimal saida1 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_s1.Contains(p.CFOP)).Sum(p => p.VALOR_CONTABIL));
                decimal saida2 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_s2.Contains(p.CFOP)).Sum(p => p.VALOR_CONTABIL));
                decimal entrada1 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_e1.Contains(p.CFOP)).Sum(p => p.VALOR_CONTABIL));
                decimal entrada2 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_e2.Contains(p.CFOP)).Sum(p => p.BASE_CALCULO));
                decimal entrada3 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_e3.Contains(p.CFOP)).Sum(p => p.VALOR_CONTABIL));
                decimal entrada4 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_e4.Contains(p.CFOP)).Sum(p => p.BASE_CALCULO));
                decimal pmc1 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_pmc1.Contains(p.CFOP)).Sum(p => p.IMPOSTO));
                decimal pmc2 = Convert.ToDecimal(cnx.GIA.Where(p => p.PERIODO_ARQUIVO == periodo && lista_cfop_pmc2.Contains(p.CFOP)).Sum(p => p.IMPOSTO));

                try
                {
                    iva_mediana = aux.GetIVAMediana(CNAE, conexao);
                }
                catch (Exception erro)
                {
                    iva_mediana = 0;
                }


                saidas = saida1 - saida2;
                entradas = (entrada1 + entrada2) - (entrada3 + entrada4);

                if (entradas == 0)
                    pmc = 0;
                else
                    pmc = Math.Round(((pmc1 - pmc2) / entradas) * 100, 4);

                decimal iva_proprio = (entradas == 0) ? 0 : Math.Round((saidas - entradas) / entradas, 4);

                decimal iva = 0;

                if (iva_mediana > iva_proprio)
                    iva = iva_mediana;
                else
                    iva = iva_proprio;

                #endregion

				

                var op_ger_cred = cnx.v_150_C100_C190_ENQ_OP.Where(p => p.PERIODO == periodo);
				

                if (class_enq_op.DeleteEnqOpByPeriodo(periodo, conexao))
                {
                    if (class_enquadramento.DeleteEnquadramentoByPeriodo(periodo, conexao))
                    {
                        try
                        {
                            foreach (var item in op_ger_cred)
                            {
                                if (item != null)
                                {
                                    st_enquadramento = new CAT_207.DAL.Bloco0.Enquadramento.st_enquadramento();
                                    st_enq_op = new EnqOp.st_enq_op();

                                    decimal cred_est_icms = Math.Round((Convert.ToDecimal(item.VL_DOC) / (1 + Convert.ToDecimal(iva))) * Convert.ToDecimal(pmc) / 100, 2);

                                    if (item.ALIQ_ICMS != null && item.UF != null)
                                    {
                                        if (/*REGRA DE NEGOCIO 9*/)
                                        {
                                            if (COD_ENQUADRAMENTO_9 == 0)
                                            {
                                                st_enquadramento.COD_HIP_GER = 9;
                                                st_enquadramento.ANEXO = "I";
                                                st_enquadramento.ARTIGO = "84";
                                                st_enquadramento.OBS = "Demonstrativo (Portaria CAT 63/2010) - Venda para Zona Franca de Manaus";
                                                st_enquadramento.PERIODO = periodo;

                                                COD_ENQUADRAMENTO_9 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                            }

                                            if (COD_ENQUADRAMENTO_9 != 0)
                                            {
                                                st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_9;
                                                st_enq_op.NUM_DOC = item.NUM_DOC;
                                                st_enq_op.SER = item.SER;
                                                st_enq_op.PERIODO = periodo;
                                                st_enq_op.COMPROV_OP = true;

                                                class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                            }
                                        }
                                        else if (/*REGRA DE NEGOCIO 1 E 5*/)
                                        {
                                            icms_total = cred_est_icms + valor_crd_out;
                                            icms_gera = icms_total - Convert.ToDecimal(item.VL_ICMS);

                                            if (icms_gera >= 0)
                                            {
                                                if (item.ALIQ_ICMS == 7)
                                                {
                                                    if (COD_ENQUADRAMENTO_1 == 0)
                                                    {
                                                        st_enquadramento.COD_HIP_GER = 1;
                                                        st_enquadramento.ARTIGO = "52";
                                                        st_enquadramento.INCISO = "II";
                                                        st_enquadramento.PERIODO = periodo;

                                                        COD_ENQUADRAMENTO_1 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                                    }

                                                    if (COD_ENQUADRAMENTO_1 != 0)
                                                    {
                                                        st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_1;
                                                        st_enq_op.NUM_DOC = item.NUM_DOC;
                                                        st_enq_op.SER = item.SER;
                                                        st_enq_op.PERIODO = periodo;
                                                        st_enq_op.COMPROV_OP = false;

                                                        class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                                    }
                                                }
                                                else if (item.ALIQ_ICMS == 4)
                                                {
                                                    if (COD_ENQUADRAMENTO_5 == 0)
                                                    {
                                                        st_enquadramento.COD_HIP_GER = 5;
                                                        st_enquadramento.ARTIGO = "52";
                                                        st_enquadramento.INCISO = "IV";
                                                        st_enquadramento.PERIODO = periodo;

                                                        COD_ENQUADRAMENTO_5 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                                    }

                                                    if (COD_ENQUADRAMENTO_5 != 0)
                                                    {
                                                        st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_5;
                                                        st_enq_op.NUM_DOC = item.NUM_DOC;
                                                        st_enq_op.SER = item.SER;
                                                        st_enq_op.PERIODO = periodo;
                                                        st_enq_op.COMPROV_OP = false;

                                                        class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                                    }
                                                }
                                            }
                                        }
                                        else if (/*REGRA DE NEGOCIO 2 E 5*/)
                                        {
                                            icms_total = cred_est_icms + valor_crd_out;
                                            icms_gera = icms_total - Convert.ToDecimal(item.VL_ICMS);

                                            if (icms_gera >= 0)
                                            {
                                                if (item.ALIQ_ICMS == 12)
                                                {
                                                    if (COD_ENQUADRAMENTO_2 == 0)
                                                    {
                                                        st_enquadramento.COD_HIP_GER = 2;
                                                        st_enquadramento.ARTIGO = "52";
                                                        st_enquadramento.INCISO = "III";
                                                        st_enquadramento.PERIODO = periodo;

                                                        COD_ENQUADRAMENTO_2 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                                    }

                                                    if (COD_ENQUADRAMENTO_2 != 0)
                                                    {
                                                        st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_2;
                                                        st_enq_op.NUM_DOC = item.NUM_DOC;
                                                        st_enq_op.SER = item.SER;
                                                        st_enq_op.PERIODO = periodo;
                                                        st_enq_op.COMPROV_OP = false;

                                                        class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                                    }
                                                }
                                                else if (item.ALIQ_ICMS == 4)
                                                {
                                                    if (COD_ENQUADRAMENTO_5 == 0)
                                                    {
                                                        st_enquadramento.COD_HIP_GER = 5;
                                                        st_enquadramento.ARTIGO = "52";
                                                        st_enquadramento.INCISO = "IV";
                                                        st_enquadramento.PERIODO = periodo;

                                                        COD_ENQUADRAMENTO_5 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                                    }

                                                    if (COD_ENQUADRAMENTO_5 != 0)
                                                    {
                                                        st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_5;
                                                        st_enq_op.NUM_DOC = item.NUM_DOC;
                                                        st_enq_op.SER = item.SER;
                                                        st_enq_op.PERIODO = periodo;
                                                        st_enq_op.COMPROV_OP = false;

                                                        class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                                    }
                                                }
                                            }
                                        }
                                        else if (/*REGRA DE NEGOCIO 10*/)
                                        {
                                            if (COD_ENQUADRAMENTO_10 == 0)
                                            {
                                                st_enquadramento.COD_HIP_GER = 10;
                                                st_enquadramento.ITEM = "II";
                                                st_enquadramento.ARTIGO = "396";
                                                st_enquadramento.OBS = "Demonstrativo (Portaria CAT 63/2010) - Operações com Componentes de equipamentos do Sistema Eletrônico de Processamento de dados";
                                                st_enquadramento.PERIODO = periodo;

                                                COD_ENQUADRAMENTO_10 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                            }

                                            if (COD_ENQUADRAMENTO_10 != 0)
                                            {
                                                st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_10;
                                                st_enq_op.NUM_DOC = item.NUM_DOC;
                                                st_enq_op.SER = item.SER;
                                                st_enq_op.PERIODO = periodo;
                                                st_enq_op.COMPROV_OP = false;

                                                class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                            }
                                        }
                                        else if (/*REGRA DE NEGOCIO 11*/)
                                        {
                                            if (COD_ENQUADRAMENTO_11 == 0)
                                            {
                                                st_enquadramento.COD_HIP_GER = 11;
                                                st_enquadramento.ANEXO = "I";
                                                st_enquadramento.ARTIGO = "41";
                                                st_enquadramento.PERIODO = periodo;

                                                COD_ENQUADRAMENTO_11 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                            }

                                            if (COD_ENQUADRAMENTO_11 != 0)
                                            {
                                                st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_11;
                                                st_enq_op.NUM_DOC = item.NUM_DOC;
                                                st_enq_op.SER = item.SER;
                                                st_enq_op.PERIODO = periodo;
                                                st_enq_op.COMPROV_OP = false;

                                                class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                            }
                                        }
                                        else if (/*REGRA DE NEGOCIO 6*/)
                                        {
                                            icms_total = cred_est_icms + valor_crd_out;
                                            icms_gera = icms_total - Convert.ToDecimal(item.VL_ICMS);

                                            if (icms_gera >= 0)
                                            {
                                                if (COD_ENQUADRAMENTO_6 == 0)
                                                {
                                                    st_enquadramento.COD_HIP_GER = 6;
                                                    st_enquadramento.ANEXO = "II";
                                                    st_enquadramento.ARTIGO = "9";
                                                    st_enquadramento.PERIODO = periodo;

                                                    COD_ENQUADRAMENTO_6 = class_enquadramento.InsertEnquadramento(st_enquadramento, conexao);
                                                }

                                                if (COD_ENQUADRAMENTO_6 != 0)
                                                {
                                                    st_enq_op.COD_ENQUADRAMENTO = COD_ENQUADRAMENTO_6;
                                                    st_enq_op.NUM_DOC = item.NUM_DOC;
                                                    st_enq_op.SER = item.SER;
                                                    st_enq_op.PERIODO = periodo;
                                                    st_enq_op.COMPROV_OP = false;

                                                    class_enq_op.InsertEnqOp(st_enq_op, conexao);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Classes.Gerador gerador = new Classes.Gerador();
                            ret = gerador.GeraArquivo(mes, ano, par_cliente, conexao, iva, pmc, CNAE);
                        }
                        catch (Exception ex)
                        {
                            ret = "Erro na associação do arquivo";
                        }
                    }
                    else
                        ret = "Erro na associação do arquivo";
                }
                else
                    ret = "Erro na associação do arquivo";
            }

            return ret;
        }           

    }
}
