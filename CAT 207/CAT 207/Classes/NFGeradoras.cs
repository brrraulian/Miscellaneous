using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.Classes
{
    class NFGeradoras : Conexao
    {
        public string GerarLista(string[] anos, String fileName, string conexao)
        {
            string ret = "Lista gerada com sucesso!";

            try
            {
                Microsoft.Office.Interop.Excel.Application xla = new Microsoft.Office.Interop.Excel.Application();
                Microsoft.Office.Interop.Excel.Workbook wb = xla.Workbooks.Add(Microsoft.Office.Interop.Excel.XlSheetType.xlWorksheet);
                Microsoft.Office.Interop.Excel.Sheets sheets = null;
                Microsoft.Office.Interop.Excel.Worksheet ws = null;

                #region Lista

                List<DataTable> data = new List<DataTable>();
                List<DataTable> dataTotal = new List<DataTable>();
                List<string[]> colunas = new List<string[]>();

                string[] nomesColunas = { "Data", "Número", "Série", "CFOP", "Nome", "CNPJ", "IE", "Valor Documento", "Base de Cálculo", "Alíquota", "Valor ICMS", "UF", "Hipótese de Geração" };
                colunas.Add(nomesColunas);
                bool temLista = false;

                for (int k = 0; k < anos.Length; k++)
                {
                    temLista = false;

                    for (int i = 0; i < 12; i++)
                    {
                        string mes = "";
                        if (i < 9)
                            mes = "0" + (i + 1).ToString();
                        else
                            mes = (i + 1).ToString();

                        data.Add(Lista(mes, anos[k], conexao));
                        dataTotal.Add(ListaTotal(mes, anos[k], conexao));

                        if (data[i].Rows.Count > 0)
                            temLista = true;
                    }

                    if (temLista)
                    {
                        sheets = wb.Sheets;
                        ws = (Microsoft.Office.Interop.Excel.Worksheet)sheets.Add(sheets[1], Type.Missing, Type.Missing, Type.Missing);
                        ws.Name = anos[k];

                        for (int i = 1; i <= colunas[0].Length; i++)
                        {
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).EntireColumn.HorizontalAlignment = Microsoft.Office.Interop.Excel.XlHAlign.xlHAlignCenter;
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).EntireColumn.NumberFormat = "@";
                            ws.Cells[1, i] = colunas[0][i - 1];
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).BorderAround2();
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).Font.Bold = true;
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).Font.Color = System.Drawing.Color.DarkOrange;
                        }

                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 8]).EntireColumn.NumberFormat = "#,##0.00";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 9]).EntireColumn.NumberFormat = "#,##0.00";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 10]).EntireColumn.NumberFormat = "#,##0.00";
                        ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, 11]).EntireColumn.NumberFormat = "#,##0.00";

                        int count = 0;
                        for (int j = 0; j < data.Count; j++)
                        {
                            if (data[j].Rows.Count > 0)
                            {
                                count += 2;
                                for (int i = 0; i < data[j].Rows.Count; i++)
                                {
                                    string data_formatada = data[j].Rows[i]["DT_DOC"].ToString();
                                    ws.Cells[count, 1] = (data_formatada == "") ? "" : data_formatada.Substring(0, 2) + "/" + data_formatada.Substring(2, 2) + "/" + data_formatada.Substring(4, 4);
                                    ws.Cells[count, 2] = data[j].Rows[i]["NUM_DOC"];
                                    ws.Cells[count, 3] = data[j].Rows[i]["SER"];
                                    ws.Cells[count, 4] = data[j].Rows[i]["CFOP"];
                                    ws.Cells[count, 5] = data[j].Rows[i]["NOME"];
                                    ws.Cells[count, 6] = (data[j].Rows[i]["CNPJ"] == DBNull.Value) ? data[j].Rows[i]["CPF"] : data[j].Rows[i]["CNPJ"];
                                    ws.Cells[count, 7] = data[j].Rows[i]["IE"];
                                    ws.Cells[count, 8] = data[j].Rows[i]["VL_DOC"];
                                    ws.Cells[count, 9] = data[j].Rows[i]["VL_BC_ICMS"];
                                    ws.Cells[count, 10] = data[j].Rows[i]["ALIQ_ICMS"];
                                    ws.Cells[count, 11] = data[j].Rows[i]["VL_ICMS"];
                                    ws.Cells[count, 12] = data[j].Rows[i]["UF"];

                                    string hip_ger = data[j].Rows[i]["COD_HIP_GER"].ToString();
                                    switch (hip_ger)
                                    {
                                        case "1":
                                            hip_ger = "Operações interestaduais com alíquota 7%";
                                            break;
                                        case "2":
                                            hip_ger = "Operações interestaduais com alíquota 12%";
                                            break;
                                        case "5":
                                            hip_ger = "Outras";
                                            break;
                                        case "6":
                                            hip_ger = "Redução de Base de Cálculo";
                                            break;
                                        case "7":
                                            hip_ger = "Saídas sem pagamento de Imposto – Exportação";
                                            break;
                                        case "9":
                                            hip_ger = "Saídas sem pagamento de Imposto – ZF Manaus";
                                            break;
                                        case "10":
                                            hip_ger = "Saídas sem pagamento de Imposto – Diferimento";
                                            break;
                                        case "11":
                                            hip_ger = "Saídas sem pagamento de Imposto – Isenção";
                                            break;
                                        default:
                                            hip_ger = "Sem Hipótese de Geração";
                                            break;
                                    }
                                    ws.Cells[count, 13] = hip_ger;

                                    count++;
                                }

                                ws.Cells[count, 8] = dataTotal[j].Rows[0]["VL_DOC"];
                                ws.Cells[count, 11] = dataTotal[j].Rows[0]["VL_ICMS"];
                                ((Microsoft.Office.Interop.Excel.Range)ws.Cells[count, 8]).Font.Bold = true;
                                ((Microsoft.Office.Interop.Excel.Range)ws.Cells[count, 11]).Font.Bold = true;
                            }
                        }

                        for (int i = 1; i <= colunas[0].Length; i++)
                        {
                            ((Microsoft.Office.Interop.Excel.Range)ws.Cells[1, i]).EntireColumn.AutoFit();
                        }
                    }

                    data.Clear();
                    dataTotal.Clear();
                }

                #endregion


                wb.SaveAs(fileName, Microsoft.Office.Interop.Excel.XlFileFormat.xlWorkbookNormal, Type.Missing, Type.Missing, Type.Missing, Type.Missing,
                        Microsoft.Office.Interop.Excel.XlSaveAsAccessMode.xlExclusive, Type.Missing, Type.Missing, Type.Missing, Type.Missing, Type.Missing);
                wb.Close();
                xla.Quit();
            }
            catch (Exception ex)
            {
                ret = "Erro ao gerar lista";
            }

            return ret;
        }

        public DataTable Lista(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT * FROM v_NF_GERADORAS where periodo = @periodo ORDER BY COD_HIP_GER, DT_DOC";
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

        public DataTable ListaTotal(string mes, string ano, string conexao)
        {
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT sum([VL_DOC]) as VL_DOC, sum([VL_ICMS]) as VL_ICMS FROM [v_NF_GERADORAS] where periodo = @periodo";
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
    }
}
