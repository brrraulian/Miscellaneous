using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.Classes
{
    class Fichas : Conexao
    {
        public string GerarFichas(string mes, string ano, List<System.Data.DataTable> data, List<string[]> colunas, String fileName)
        {
            string ret = "Relatório gerado com sucesso!";

            try
            {
                Microsoft.Office.Interop.Excel.Application xla = new Microsoft.Office.Interop.Excel.Application();
                Microsoft.Office.Interop.Excel.Workbook wb = xla.Workbooks.Add(Microsoft.Office.Interop.Excel.XlSheetType.xlWorksheet);
                Microsoft.Office.Interop.Excel.Sheets sheets = null;
                Microsoft.Office.Interop.Excel.Worksheet ws = null;

                if (data[6].Rows.Count > 0)
                {
                    #region Ficha6F

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha6F";

                    for (int i = 1; i <= colunas[6].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[6][i - 1];
                        ws.Cells[5, i] = "(" + i + ")";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 7]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 8]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 9]).EntireColumn.NumberFormat = "#,##0.00";

                    for (int i = 0; i < data[6].Rows.Count; i++)
                    {
                        string data_formatada = data[6].Rows[i]["DT_DOC"].ToString();
                        ws.Cells[i + 6, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                        ws.Cells[i + 6, 2] = data[6].Rows[i]["TIPO"];
                        ws.Cells[i + 6, 3] = data[6].Rows[i]["SER"];
                        ws.Cells[i + 6, 4] = data[6].Rows[i]["NUM_DOC"];
                        ws.Cells[i + 6, 5] = data[6].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 6] = data[6].Rows[i]["NUM_DECL_DESP_EXP"];
                        ws.Cells[i + 6, 7] = data[6].Rows[i]["VL_DOC"];
                        ws.Cells[i + 6, 8] = data[6].Rows[i]["VL_BC_ICMS"];
                        ws.Cells[i + 6, 9] = data[6].Rows[i]["VL_ICMS"];
                        ws.Cells[i + 6, 10] = data[6].Rows[i]["PCT_CRED_OUT"];
                        ws.Cells[i + 6, 11] = data[6].Rows[i]["VL_CRED_OUT"];
                    }

                    for (int i = 1; i <= colunas[6].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Demonstrativo das Operações Não Geradoras de Crédito Acumulado";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

                if (data[5].Rows.Count > 0)
                {
                    #region Ficha6E

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha6E";

                    for (int i = 1; i <= colunas[5].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[5][i - 1];

                        if (i == 11)
                            ws.Cells[5, i] = "(" + i + ") = (9) * (10) / 100";
                        else if (i == 14)
                            ws.Cells[5, i] = "(" + i + ") = (11) + (13)";
                        else
                            ws.Cells[5, i] = "(" + i + ")";

                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 7]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 9]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 11]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 14]).EntireColumn.NumberFormat = "#,##0.00";

                    for (int i = 0; i < data[5].Rows.Count; i++)
                    {
                        string data_formatada = data[5].Rows[i]["DT_DOC"].ToString();
                        ws.Cells[i + 6, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                        ws.Cells[i + 6, 2] = data[5].Rows[i]["TIPO"];
                        ws.Cells[i + 6, 3] = data[5].Rows[i]["SER"];
                        ws.Cells[i + 6, 4] = data[5].Rows[i]["NUM_DOC"];
                        ws.Cells[i + 6, 5] = data[5].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 6] = data[5].Rows[i]["COD_ENQUADRAMENTO"];
                        ws.Cells[i + 6, 7] = data[5].Rows[i]["VL_DOC"];
                        ws.Cells[i + 6, 8] = data[5].Rows[i]["IVA"];
                        ws.Cells[i + 6, 9] = data[5].Rows[i]["CUSTO_EST"];
                        ws.Cells[i + 6, 10] = data[5].Rows[i]["PMC"];
                        ws.Cells[i + 6, 11] = data[5].Rows[i]["CRED_EST_IMP"];
                        ws.Cells[i + 6, 12] = data[5].Rows[i]["PCT_CRED_OUT"];
                        ws.Cells[i + 6, 13] = data[5].Rows[i]["VL_CRED_OUT"];
                        ws.Cells[i + 6, 14] = data[5].Rows[i]["VL_CRED_ACUM_GER"];
                    }

                    for (int i = 1; i <= colunas[5].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Demonstrativo das Operações Geradoras de Crédito Acumulado do ICMS Artigo 71, Inciso III - Operações sem Pagamento de Imposto - Demais Casos";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

                if (data[4].Rows.Count > 0)
                {
                    #region Ficha6D

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha6D";

                    for (int i = 1; i <= colunas[4].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[4][i - 1];

                        if (i == 12)
                            ws.Cells[5, i] = "(" + i + ") = (10) * (11)";
                        else if (i == 16)
                            ws.Cells[5, i] = "(" + i + ") = (14) + (15)";
                        else
                            ws.Cells[5, i] = "(" + i + ")";

                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 8]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 10]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 12]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 15]).EntireColumn.NumberFormat = "#,##0.00";
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, 16]).EntireColumn.NumberFormat = "#,##0.00";

                    for (int i = 0; i < data[4].Rows.Count; i++)
                    {
                        string data_formatada = data[4].Rows[i]["DT_DOC"].ToString();
                        ws.Cells[i + 6, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                        ws.Cells[i + 6, 2] = data[4].Rows[i]["TIPO"];
                        ws.Cells[i + 6, 3] = data[4].Rows[i]["SER"];
                        ws.Cells[i + 6, 4] = data[4].Rows[i]["NUM_DOC"];
                        ws.Cells[i + 6, 5] = data[4].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 6] = data[4].Rows[i]["COD_ENQUADRAMENTO"];
                        ws.Cells[i + 6, 7] = (Convert.ToBoolean(data[4].Rows[i]["COMPROV_OP"])) ? "Sim" : "Não";
                        ws.Cells[i + 6, 8] = data[4].Rows[i]["VL_DOC"];
                        ws.Cells[i + 6, 9] = data[4].Rows[i]["IVA"];
                        ws.Cells[i + 6, 10] = data[4].Rows[i]["CUSTO_EST"];
                        ws.Cells[i + 6, 11] = data[4].Rows[i]["PMC"];
                        ws.Cells[i + 6, 12] = data[4].Rows[i]["CRED_EST_IMP"];
                        ws.Cells[i + 6, 13] = data[4].Rows[i]["PCT_CRED_OUT"];
                        ws.Cells[i + 6, 14] = data[4].Rows[i]["VL_CRED_OUT"];
                        ws.Cells[i + 6, 15] = data[4].Rows[i]["VL_ICMS_COMPR"];
                        ws.Cells[i + 6, 16] = data[4].Rows[i]["VL_CRED_ACUM_GER"];
                    }

                    for (int i = 1; i <= colunas[4].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Demonstrativo das Operações Geradoras de Crédito Acumulado do ICMS Artigo 71, Inciso III - Operações sem Pagamento de Imposto - Zona Franca de Manaus";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

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

                if (data[1].Rows.Count > 0)
                {
                    #region Ficha5D

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha5D";

                    for (int i = 1; i <= colunas[1].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[1][i - 1];
                        ws.Cells[5, i] = "(" + i + ")";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    for (int i = 0; i < data[1].Rows.Count; i++)
                    {
                        ws.Cells[i + 6, 1] = data[1].Rows[i]["COD_ENQUADRAMENTO"];
                        ws.Cells[i + 6, 2] = data[1].Rows[i]["COD_HIP_GER"];
                        ws.Cells[i + 6, 3] = data[1].Rows[i]["ANEXO"];
                        ws.Cells[i + 6, 4] = data[1].Rows[i]["ARTIGO"];
                        ws.Cells[i + 6, 5] = data[1].Rows[i]["INCISO"];
                        ws.Cells[i + 6, 6] = data[1].Rows[i]["ALINEA"];
                        ws.Cells[i + 6, 7] = data[1].Rows[i]["PARAGRAFO"];
                        ws.Cells[i + 6, 8] = data[1].Rows[i]["ITEM"];
                        ws.Cells[i + 6, 9] = data[1].Rows[i]["LETRA"];
                        ws.Cells[i + 6, 10] = data[1].Rows[i]["OBS"];
                    }

                    for (int i = 1; i <= colunas[1].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Enquadramento Legal da Operação/Prestação Geradora do Crédito Acumulado do ICMS";
                    ws.Cells[2, 1] = "PERÍODO: " + mes + "/" + ano;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignLeft;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 1]).Font.Bold = true;
                    ((Microsoft.Office.Interop.Excel.Range)ws.Cells[2, 1]).Font.Bold = true;

                    #endregion
                }

                if (data[0].Rows.Count > 0)
                {
                    #region Ficha5C

                    sheets = wb.Sheets;
                    ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                    ws.Name = "Ficha5C";

                    for (int i = 1; i <= colunas[0].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.NumberFormat = "@";
                        ws.Cells[4, i] = colunas[0][i - 1];
                        ws.Cells[5, i] = "(" + i + ")";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[5, i]).BorderAround2();
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Bold = true;
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).Font.Color = System.Drawing.Color.DarkOrange;
                    }

                    for (int i = 0; i < data[0].Rows.Count; i++)
                    {
                        ws.Cells[i + 6, 1] = data[0].Rows[i]["COD_PART"];
                        ws.Cells[i + 6, 2] = data[0].Rows[i]["NOME"];
                        ws.Cells[i + 6, 3] = data[0].Rows[i]["COD_PAIS"];
                        ws.Cells[i + 6, 4] = data[0].Rows[i]["CNPJ"];
                        ws.Cells[i + 6, 5] = data[0].Rows[i]["IE"];
                        ws.Cells[i + 6, 6] = data[0].Rows[i]["END"];
                        ws.Cells[i + 6, 7] = data[0].Rows[i]["NUM"];
                        ws.Cells[i + 6, 8] = data[0].Rows[i]["COMPL"];
                        ws.Cells[i + 6, 9] = data[0].Rows[i]["BAIRRO"];
                        ws.Cells[i + 6, 10] = data[0].Rows[i]["MUN"];
                        ws.Cells[i + 6, 11] = data[0].Rows[i]["CEP"];
                        ws.Cells[i + 6, 12] = data[0].Rows[i]["UF"];
                    }

                    for (int i = 1; i <= colunas[0].Length; i++)
                    {
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[4, i]).EntireColumn.AutoFit();
                    }

                    ws.Cells[1, 1] = "Cadastro de Participantes de Operações e Prestações";
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

        public DataTable Ficha5C(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT [COD_PART], [NOME], " +
                        "(select top(1) pais from aux_pais where p.cod_pais = cod_pais) as [COD_PAIS], " +
                    "[CNPJ], [CPF], [IE], [END], [NUM], [COMPL], [BAIRRO], " +
                    "(select top(1) mun from aux_uf_mun where concat(cod_uf, cod_mun) = p.cod_mun) as MUN, [CEP], [UF] " +
                      "FROM PARTICIPANTE_0150 as p where periodo = @periodo";
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

        public DataTable Ficha5D(string mes, string ano, string conexao)
        {
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT [COD_ENQUADRAMENTO], [COD_HIP_GER], [ANEXO], [ARTIGO], [INCISO], [ALINEA], " +
                            "[PARAGRAFO], [ITEM], [LETRA], [OBS] FROM ENQUADRAMENTO_5D where periodo = @periodo";
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

        #region SPED

        public DataTable Ficha6A(string mes, string ano, string conexao)
        {
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select DT_DOC, 'Nota Fiscal Eletrônica' TIPO, SER, NUM_DOC, COD_PART, " +
                       "(SELECT TOP (1) COD_ENQUADRAMENTO FROM dbo.ENQ_OP AS EO WHERE (NUM_DOC = s.NUM_DOC AND SER = s.SER)) AS COD_ENQUADRAMENTO, VL_DOC, " +
                       "VL_BC_ICMS, VL_ICMS, (@IVA) IVA, round(VL_DOC / (1 + @IVA), 2) CUSTO_EST, (@PMC) PMC, " +
                       "round((VL_DOC / (1 + @IVA)) * @PMC / 100, 2) CRED_EST_IMP, (0.00) PCT_CRED_OUT,  (0.00) VL_CRED_OUT, " +
                       "round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0, 2) VL_TOTAL_ICMS, round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0 - VL_ICMS, 2)  VL_CRED_ACUM_GER " +
                       "FROM v_150_C100_C190_ENQ_OP as s WHERE PERIODO = @periodo and COD_HIP_GER in (1, 2, 5) and aliq_icms in (4, 7, 12)";

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        string[] iva_pmc = GetIVAPMC(mes, ano, conexao).Split(';');

                        da.SelectCommand.Parameters.AddWithValue("@IVA", Convert.ToDecimal(iva_pmc[0]));
                        da.SelectCommand.Parameters.AddWithValue("@PMC", Convert.ToDecimal(iva_pmc[1]));
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

        public DataTable Ficha6B(string mes, string ano, string conexao)
        {
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select DT_DOC, 'Nota Fiscal Eletrônica' TIPO, SER, NUM_DOC, COD_PART, " +
                       "(SELECT TOP (1) COD_ENQUADRAMENTO FROM dbo.ENQ_OP AS EO WHERE (NUM_DOC = s.NUM_DOC AND SER = s.SER)) AS COD_ENQUADRAMENTO, VL_DOC, " +
                       "VL_BC_ICMS, VL_ICMS, (@IVA) IVA, round(VL_DOC / (1 + @IVA), 2) CUSTO_EST, (@PMC) PMC, " +
                       "round((VL_DOC / (1 + @IVA)) * @PMC / 100, 2) CRED_EST_IMP, (0.00) PCT_CRED_OUT,  (0.00) VL_CRED_OUT, " +
                       "round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0, 2) VL_TOTAL_ICMS, round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0 - VL_ICMS, 2)  VL_CRED_ACUM_GER " +
                       "FROM v_150_C100_C190_ENQ_OP as s WHERE PERIODO = @periodo and COD_HIP_GER = 6";

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        string[] iva_pmc = GetIVAPMC(mes, ano, conexao).Split(';');

                        da.SelectCommand.Parameters.AddWithValue("@IVA", Convert.ToDecimal(iva_pmc[0]));
                        da.SelectCommand.Parameters.AddWithValue("@PMC", Convert.ToDecimal(iva_pmc[1]));
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

        public DataTable Ficha6D(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select DT_DOC, 'Nota Fiscal Eletrônica' TIPO, SER, NUM_DOC, COD_PART, t.COD_ENQUADRAMENTO, " +
                        "COMPROV_OP,  VL_DOC, (@IVA) IVA, round(VL_DOC / (1 + @IVA), 2) CUSTO_EST, (@PMC) PMC, " +
                        "round((VL_DOC / (1 + @IVA)) * @PMC / 100, 2) CRED_EST_IMP, (0.00) PCT_CRED_OUT, " +
                        "(0.00) VL_CRED_OUT, round((VL_DOC / (1 + @IVA)) * @PMC / 100, 2) VL_ICMS_COMPR, " +
                        "round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0, 2) VL_CRED_ACUM_GER " +
                        "from (SELECT T1.NUM_DOC, T1.COD_PART, T1.SER, T1.DT_DOC, T1.VL_DOC, T1.PERIODO, EO.COMPROV_OP, EO.COD_ENQUADRAMENTO, " +
                        "(SELECT TOP (1) ALIQ_ICMS FROM dbo.ANAL_DOC_C190 AS t190 WHERE PERIODO = T1.PERIODO AND (NUM_DOC = T1.NUM_DOC)) AS ALIQ_ICMS, " +
                        "(SELECT TOP (1) VL_ICMS FROM dbo.ANAL_DOC_C190 AS t190 WHERE PERIODO = T1.PERIODO AND (NUM_DOC = T1.NUM_DOC)) AS VL_ICMS " +
                        "FROM (SELECT NUM_DOC, COD_PART, SER, DT_DOC, VL_DOC, PERIODO " +
                        "FROM dbo.DOC_FISCAL_C100 AS C100 WHERE (IND_OPER = 1)) AS T1 LEFT OUTER JOIN " +
                        "dbo.PARTICIPANTE_0150 AS _150 ON T1.COD_PART = _150.COD_PART AND T1.PERIODO = _150.PERIODO LEFT OUTER JOIN " +
                        "dbo.ENQ_OP AS EO ON T1.NUM_DOC = EO.NUM_DOC) as t inner join " +
                        "enquadramento_5d as enq on t.cod_enquadramento = enq.cod_enquadramento where t.periodo = @periodo and " +
                        "enq.COD_HIP_GER = 9 and ALIQ_ICMS = 0";

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        string[] iva_pmc = GetIVAPMC(mes, ano, conexao).Split(';');

                        da.SelectCommand.Parameters.AddWithValue("@IVA", Convert.ToDecimal(iva_pmc[0]));
                        da.SelectCommand.Parameters.AddWithValue("@PMC", Convert.ToDecimal(iva_pmc[1]));
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

        public DataTable Ficha6E(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select DT_DOC, 'Nota Fiscal Eletrônica' TIPO, SER, NUM_DOC, COD_PART, " +
                        "(SELECT TOP (1) COD_ENQUADRAMENTO FROM dbo.ENQ_OP AS EO WHERE (NUM_DOC = s.NUM_DOC AND SER = s.SER)) AS COD_ENQUADRAMENTO, VL_DOC, " +
                        "(@IVA) IVA, round(VL_DOC / (1 + @IVA), 2) CUSTO_EST, (@PMC) PMC, " +
                        "round((VL_DOC / (1 + @IVA)) * @PMC / 100, 2) CRED_EST_IMP, (0.00) PCT_CRED_OUT,  (0.00) VL_CRED_OUT, " +
                        "round(((VL_DOC / (1 + @IVA)) * @PMC / 100) + 0, 2)  VL_CRED_ACUM_GER " +
                        "FROM v_150_C100_C190_ENQ_OP as s WHERE PERIODO = @periodo and COD_HIP_GER in (10, 11)";

                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        string[] iva_pmc = GetIVAPMC(mes, ano, conexao).Split(';');

                        da.SelectCommand.Parameters.AddWithValue("@IVA", Convert.ToDecimal(iva_pmc[0]));
                        da.SelectCommand.Parameters.AddWithValue("@PMC", Convert.ToDecimal(iva_pmc[1]));
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

        public DataTable Ficha6F(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "select DT_DOC, 'Nota Fiscal Eletrônica' TIPO, SER, NUM_DOC, COD_PART, '' NUM_DECL_DESP_EXP, VL_DOC, VL_BC_ICMS, VL_ICMS, " +
                        "(0.00) PCT_CRED_OUT,  (0.00) VL_CRED_OUT " +
                        "FROM v_150_C100_C190_ENQ_OP as s WHERE PERIODO = @periodo and num_doc not in (SELECT NUM_DOC FROM ENQ_OP where periodo = @periodo)";

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

        #endregion


        public string GetIVAPMC(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            string ret = "";

            try
            {
                string comando = "SELECT IVA, PMC FROM ARQUIVOS_GERADOS where PERIODO_ARQUIVO = @periodo";

                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@periodo", mes + ano);

                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        dr.Read();
                        if (dr.HasRows)
                        {
                            ret += dr["IVA"].ToString();
                            ret += ";" + dr["PMC"].ToString();
                        }
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }

            return ret;
        }


    }
}
