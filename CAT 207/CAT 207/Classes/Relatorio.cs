using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CAT_207.Classes
{
    class Relatorio : Conexao
    {
        public string GerarRelatorio(string mes, string ano, List<System.Data.DataTable> data, List<string[]> colunas, String fileName)
        {
            string ret = "Relatório gerado com sucesso!";

            try
            {
                Microsoft.Office.Interop.Excel.Application xla = new Microsoft.Office.Interop.Excel.Application();
                Microsoft.Office.Interop.Excel.Workbook wb = xla.Workbooks.Add(Microsoft.Office.Interop.Excel.XlSheetType.xlWorksheet);
                Microsoft.Office.Interop.Excel.Sheets sheets = null;
                Microsoft.Office.Interop.Excel.Worksheet ws = null;


                if (data[3].Rows.Count > 0)
                {
                    #region Ficha6B

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha6B";

                    for (int i = 1; i <= colunas[3].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[3][i - 1];

                        if (i == 13)
                            ws.Cells[5, i] = "(" + i + ") = (11) * (12)";
                        else if (i == 16)
                            ws.Cells[5, i] = "(" + i + ") = (13) + (15)";
                        else if (i == 17)
                            ws.Cells[5, i] = "(" + i + ") = (16) - (9)";
                        else
                            ws.Cells[5, i] = "(" + i + ")";

                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 7]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 8]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 9]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 11]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 13]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 16]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 17]).EntireColumn.NumberFormat = "#,##0.00";


                    for (int i = 0; i < data[3].Rows.Count; i++)
                    {
                        string data_formatada = data[3].Rows[i]["DT_DOC"].ToString();
                        ws.Cells[i + 6, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                        ws.Cells[i + 6, 2] = data[3].Rows[i]["TIPO"];
                        ws.Cells[i + 6, 3] = data[3].Rows[i]["SER"];
                        ws.Cells[i + 6, 4] = data[3].Rows[i]["NUM_DOC"];
                        ws.Cells[i + 6, 5] = data[3].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 6] = data[3].Rows[i]["COD_ENQUADRAMENTO"];
                        ws.Cells[i + 6, 7] = data[3].Rows[i]["VL_DOC"];
                        ws.Cells[i + 6, 8] = data[3].Rows[i]["VL_BC_ICMS"];
                        ws.Cells[i + 6, 9] = data[3].Rows[i]["VL_ICMS"];
                        ws.Cells[i + 6, 10] = data[3].Rows[i]["IVA"];
                        ws.Cells[i + 6, 11] = data[3].Rows[i]["CUSTO_EST"];
                        ws.Cells[i + 6, 12] = data[3].Rows[i]["PMC"];
                        ws.Cells[i + 6, 13] = data[3].Rows[i]["CRED_EST_IMP"];
                        ws.Cells[i + 6, 14] = data[3].Rows[i]["PCT_CRED_OUT"];
                        ws.Cells[i + 6, 15] = data[3].Rows[i]["VL_CRED_OUT"];
                        ws.Cells[i + 6, 16] = data[3].Rows[i]["VL_TOTAL_ICMS"];
                        ws.Cells[i + 6, 17] = data[3].Rows[i]["VL_CRED_ACUM_GER"];
                    }

                    for (int i = 1; i <= colunas[3].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Demonstrativo das Operações Geradoras de Crédito Acumulado do ICMS Artigo 71, Inciso II - Operações com Redução de Base de Cálculo";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

                if (data[2].Rows.Count > 0)
                {
                    #region Ficha6A

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha6A";

                    for (int i = 1; i <= colunas[2].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[2][i - 1];

                        if (i == 13)
                            ws.Cells[5, i] = "(" + i + ") = (11) * (12)";
                        else if (i == 16)
                            ws.Cells[5, i] = "(" + i + ") = (13) + (15)";
                        else if (i == 17)
                            ws.Cells[5, i] = "(" + i + ") = (16) - (9)";
                        else
                            ws.Cells[5, i] = "(" + i + ")";

                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 7]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 8]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 9]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 11]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 13]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 16]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 17]).EntireColumn.NumberFormat = "#,##0.00";


                    for (int i = 0; i < data[2].Rows.Count; i++)
                    {
                        string data_formatada = data[2].Rows[i]["DT_DOC"].ToString();
                        ws.Cells[i + 6, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                        ws.Cells[i + 6, 2] = data[2].Rows[i]["TIPO"];
                        ws.Cells[i + 6, 3] = data[2].Rows[i]["SER"];
                        ws.Cells[i + 6, 4] = data[2].Rows[i]["NUM_DOC"];
                        ws.Cells[i + 6, 5] = data[2].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 6] = data[2].Rows[i]["COD_ENQUADRAMENTO"];
                        ws.Cells[i + 6, 7] = data[2].Rows[i]["VL_DOC"];
                        ws.Cells[i + 6, 8] = data[2].Rows[i]["VL_BC_ICMS"];
                        ws.Cells[i + 6, 9] = data[2].Rows[i]["VL_ICMS"];
                        ws.Cells[i + 6, 10] = data[2].Rows[i]["IVA"];
                        ws.Cells[i + 6, 11] = data[2].Rows[i]["CUSTO_EST"];
                        ws.Cells[i + 6, 12] = data[2].Rows[i]["PMC"];
                        ws.Cells[i + 6, 13] = data[2].Rows[i]["CRED_EST_IMP"];
                        ws.Cells[i + 6, 14] = data[2].Rows[i]["PCT_CRED_OUT"];
                        ws.Cells[i + 6, 15] = data[2].Rows[i]["VL_CRED_OUT"];
                        ws.Cells[i + 6, 16] = data[2].Rows[i]["VL_TOTAL_ICMS"];
                        ws.Cells[i + 6, 17] = data[2].Rows[i]["VL_CRED_ACUM_GER"];
                    }

                    for (int i = 1; i <= colunas[2].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Demonstrativo das Operações Geradoras de Crédito Acumulado do ICMS Artigo 71, Inciso I - Operações com Aplicação de Alíquotas Diversificadas";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

                wb.SaveAs(fileName, Microsoft.Office.Interop.Excel.XlFileFormat.xlWorkbookNormal, Type.Missing, Type.Missing, Type.Missing, Type.Missing,
                        Microsoft.Office.Interop.Excel.XlSaveAsAccessMode.xlExclusive, Type.Missing, Type.Missing, Type.Missing, Type.Missing, Type.Missing);
                wb.Close();
                xla.Quit();
            }
            catch (Exception ex)
            {
                ret = "Erro ao gerar relatório";
            }

            return ret;
        }

        public DataTable getEntidade(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT NOME, CNPJ, IE FROM ENTIDADE_0000 where periodo = @periodo";
                    DataSet ds_Excel = new DataSet();

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", mes + ano);

                        da.SelectCommand.CommandTimeout = 120;

                        cnx.Open();
                        da.Fill(dt);
                        cnx.Close();
                    }
                }
                catch (Exception ex)
                {
                    cnx.Close();
                }
                return dt;
            }
        }

        public DataTable getIvaPmc(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT IVA, PMC FROM ARQUIVOS_GERADOS where periodo = @periodo";
                    DataSet ds_Excel = new DataSet();

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", mes + ano);

                        da.SelectCommand.CommandTimeout = 120;

                        cnx.Open();
                        da.Fill(dt);
                        cnx.Close();
                    }
                }
                catch (Exception ex)
                {
                    cnx.Close();
                }
                return dt;
            }
        }

        public DataTable getEnquadramento(string mes, string ano, string conexao)
        {
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT [COD_ENQUADRAMENTO], [HIP_GER], [ANEXO], [ARTIGO], [INCISO], [ALINEA], " +
                            "[PARAGRAFO], [ITEM], [LETRA] FROM v_ENQUADRAMENTO where periodo = @periodo";
                    DataSet ds_Excel = new DataSet();

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", mes + ano);

                        da.SelectCommand.CommandTimeout = 120;

                        cnx.Open();
                        da.Fill(dt);
                        cnx.Close();
                    }
                }
                catch (Exception ex)
                {
                    cnx.Close();
                }
                return dt;
            }
        }

        public DataTable getValores(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select ALIQ_ICMS, sum(VL_DOC) VL_DOC, sum(VL_BC_ICMS) VL_BC_ICMS, sum(VL_ICMS) VL_ICMS, cod_enquadramento, cod_hip_ger " +
                        "from v_150_C100_C190_ENQ_OP where periodo = @periodo and cod_hip_ger in (1, 2, 5, 6, 7, 9, 10, 11) group by COD_ENQUADRAMENTO, ALIQ_ICMS, cod_hip_ger";

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", mes + ano);

                        da.SelectCommand.CommandTimeout = 120;

                        cnx.Open();
                        da.Fill(dt);
                        cnx.Close();
                    }
                }
                catch (Exception ex)
                {
                    cnx.Close();
                }

                return dt;
            }
        }


    }
}
