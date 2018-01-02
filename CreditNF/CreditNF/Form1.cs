using System;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;

namespace CreditNF
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            CenterToScreen();
            prepareControls();
        }

        private void prepareControls()
        {
            ToolTip toolTip = new ToolTip();
            toolTip.ShowAlways = true;

            toolTip.SetToolTip(this.gb_sourceFolder, "Selecione a planilha que contém os dados de origem");
            toolTip.SetToolTip(this.txt_sourceFolder, "Selecione a planilha que contém os dados de origem");
            toolTip.SetToolTip(this.btn_sourceFolder, "Selecione a planilha que contém os dados de origem");

            toolTip.SetToolTip(this.gb_destinyFolder, "Selecione o local para criação do arquivo de texto");
            toolTip.SetToolTip(this.txt_destinyFolder, "Selecione o local para criação do arquivo de texto");
            toolTip.SetToolTip(this.btn_destinyFolder, "Selecione o local para criação do arquivo de texto");

            toolTip.SetToolTip(this.btn_convert, "Converter os dados da planilha para o código do arquivo de texto");
        }

        private void btn_sourceFolder_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                txt_sourceFolder.Text = openFileDialog1.FileName;
            }
        }

        private void btn_destinyFolder_Click(object sender, EventArgs e)
        {
            if (folderBrowserDialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                txt_destinyFolder.Text = folderBrowserDialog1.SelectedPath;
            }
        }

        private void btn_convert_Click(object sender, EventArgs e)
        {
            Convert();
            trataComponentes();
        }

        private void trataComponentes()
        {
            if (txt_sourceFolder.Enabled)
            {
                txt_sourceFolder.Enabled = false;
                btn_sourceFolder.Enabled = false;
                txt_destinyFolder.Enabled = false;
                btn_destinyFolder.Enabled = false;
                btn_convert.Enabled = false;
            }
            else
            {
                txt_sourceFolder.Enabled = true;
                btn_sourceFolder.Enabled = true;
                txt_destinyFolder.Enabled = true;
                btn_destinyFolder.Enabled = true;
                btn_convert.Enabled = true;
            }
        }

        #region Convert

        private bool validar()
        {
            trataComponentes();

            if (txt_sourceFolder.Text == "")
            {
                MessageBox.Show("Selecione a planilha de origem dos dados");
                return false;
            }
            else if (txt_destinyFolder.Text == "")
            {
                MessageBox.Show("Selecione um local para a criação do arquivo");
                return false;
            }

            return true;
        }

        private void Convert()
        {
            if (!validar())
                return;

            StringBuilder sb = new StringBuilder();

            #region OleDB

            //try
            //{
            //    System.Data.OleDb.OleDbConnection MyConnection;
            //    System.Data.DataSet DtSet;
            //    System.Data.OleDb.OleDbDataAdapter MyCommand;
            //    MyConnection = new System.Data.OleDb.OleDbConnection("provider=Microsoft.Jet.OLEDB.4.0;Data Source='" + txt_sourceFolder.Text + "';Extended Properties=Excel 8.0;");
            //    MyCommand = new System.Data.OleDb.OleDbDataAdapter("select * from [Plan1$]", MyConnection);
            //    MyCommand.TableMappings.Add("Table", "TestTable");
            //    DtSet = new System.Data.DataSet();
            //    MyCommand.Fill(DtSet);

            //    for (int i = 0; i < DtSet.Tables[0].Rows.Count; i++)
            //    {
            //        if (DtSet.Tables[0].Rows[i][1].ToString() != "" &&
            //            DtSet.Tables[0].Rows[i][1].ToString().ToLower() != "cliente" &&
            //            DtSet.Tables[0].Rows[i][1].ToString().ToLower() != "cancelada")
            //        {
            //            sb.AppendFormat("{0}{1}", "S1301", DtSet.Tables[0].Rows[i][2].ToString());
            //            sb.Append("\r\n");
            //        }
            //    }

            //    MyConnection.Close();
            //}
            //catch (Exception ex)
            //{
            //    MessageBox.Show(ex.ToString());
            //}

            #endregion

            int row = 0;
            int col = 0;

            string code = "S1301";
            string holderCNPJ = "";
            string competence = "";
            string contribution = "02631";
            string numberNF = "";
            string serieNF = "001";
            string emitionDateNF = "";
            string totalValueNF = "";
            string retainedValue = "";

            Microsoft.Office.Interop.Excel.Application xlApp = new Microsoft.Office.Interop.Excel.Application();
            Microsoft.Office.Interop.Excel.Workbook xlWorkbook = xlApp.Workbooks.Open(txt_sourceFolder.Text);
            Microsoft.Office.Interop.Excel._Worksheet xlWorksheet = xlWorkbook.Sheets[1];
            Microsoft.Office.Interop.Excel.Range xlRange = xlWorksheet.UsedRange;

            int rowCount = xlRange.Rows.Count;
            int colCount = xlRange.Columns.Count;

            //iterate over the rows and columns and print to the console as it appears in the file
            //excel is not zero based!!
            try
            {
                for (int i = 1; i <= rowCount; i++)
                {
                    row = i;

                    for (int j = 1; j <= colCount; j++)
                    {
                        col = j;

                        //write the value to the console
                        if (xlRange.Cells[i, j] != null && xlRange.Cells[i, j].Value2 != null)
                        {
                            if (i == 1 && j == 7)
                                holderCNPJ = xlRange.Cells[i, j].Value2.ToString().Replace("CNPJ: ", "").Replace(".", "").Replace("/", "").Replace("-", "");

                            if (i > 3 && j < 10)
                            {
                                if (xlRange.Cells[i, 2].Value2 != null && xlRange.Cells[i, 2].Value2.ToString().Trim() != String.Empty
                                    && xlRange.Cells[i, 2].Value2.ToString().Trim() != "" && xlRange.Cells[i, 2].Value2.ToString() != "CANCELADA")
                                {
                                    if (j == 1)
                                    {
                                        numberNF = xlRange.Cells[i, 1].Value2.ToString();
                                        while (numberNF.Length < 9)
                                            numberNF = "0" + numberNF;
                                    }
                                    else if (j == 4)
                                    {
                                        string date = xlRange.Cells[i, 4].Value2.ToString();
                                        DateTime dt;
                                        if (date.Contains(".") || date.Contains("-"))
                                        {
                                            date = date.Replace(".", "/").Replace("-", "/");
                                            dt = DateTime.FromOADate(DateTime.Parse(date).ToOADate());
                                        }
                                        else
                                            dt = DateTime.FromOADate(Double.Parse(date));

                                        string month = dt.Month.ToString();
                                        if (month.Length < 2)
                                            month = "0" + month;

                                        competence = dt.Year.ToString() + month;

                                        string day = dt.Day.ToString();
                                        if (day.Length < 2)
                                            day = "0" + day;

                                        emitionDateNF = day + month + dt.Year.ToString();
                                    }
                                    else if (j == 6)
                                    {
                                        totalValueNF = Math.Round(xlRange.Cells[i, 6].Value2, 2).ToString().Replace(",", "");
                                        while (totalValueNF.Length < 12)
                                            totalValueNF = "0" + totalValueNF;
                                    }
                                    else if (j == 9)
                                    {
                                        retainedValue = Math.Round(xlRange.Cells[i, 9].Value2, 2).ToString().Replace(",", "");
                                        while (retainedValue.Length < 16)
                                            retainedValue = "0" + retainedValue;

                                        sb.AppendFormat("{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{10}",
                 code,
                 holderCNPJ,
                 competence,
                 contribution,
                 xlRange.Cells[i, 3].Value2.ToString().Replace(".", "").Replace("/", "").Replace("-", ""),
                 numberNF,
                 serieNF,
                 emitionDateNF,
                 holderCNPJ,
                 totalValueNF,
                 retainedValue);
                                        sb.Append("\r\n");
                                    }
                                }
                            }
                        }
                    }
                }

                //cleanup
                GC.Collect();
                GC.WaitForPendingFinalizers();

                //rule of thumb for releasing com objects:
                //  never use two dots, all COM objects must be referenced and released individually
                //  ex: [somthing].[something].[something] is bad

                //release com objects to fully kill excel process from running in the background
                Marshal.ReleaseComObject(xlRange);
                Marshal.ReleaseComObject(xlWorksheet);

                //close and release
                xlWorkbook.Close();
                Marshal.ReleaseComObject(xlWorkbook);

                //quit and release
                xlApp.Quit();
                Marshal.ReleaseComObject(xlApp);


                TextWriter tw = null;

                string path = txt_destinyFolder.Text + @"\";

                int title = 0;
                if (System.IO.File.Exists(path + title + ".txt"))
                    title++;
                //System.IO.File.Delete(path + title + ".txt");

                tw = new StreamWriter(path + title + ".txt");

                tw.Write(sb);
                sb.Clear();
                tw.Close();

                MessageBox.Show("Arquivo criado com sucesso!");
            }
            catch (Exception e)
            {
                MessageBox.Show("Linha: " + row + "\nColuna: " + convertNumToAlphabetic(col), "Erro");
            }
        }

        #endregion

        private char convertNumToAlphabetic(int col)
        {
            switch (col)
            {
                case 1:
                    return 'A';
                    break;
                case 2:
                    return 'B';
                    break;
                case 3:
                    return 'C';
                    break;
                case 4:
                    return 'D';
                    break;
                case 5:
                    return 'E';
                    break;
                case 6:
                    return 'F';
                    break;
                case 7:
                    return 'G';
                    break;
                case 8:
                    return 'H';
                    break;
                case 9:
                    return 'I';
                    break;
                case 10:
                    return 'J';
                    break;
                case 11:
                    return 'K';
                    break;
                case 12:
                    return 'L';
                    break;
                case 13:
                    return 'M';
                    break;
                case 14:
                    return 'N';
                    break;
                case 15:
                    return 'O';
                    break;
                case 16:
                    return 'P';
                    break;
                case 17:
                    return 'Q';
                    break;
                case 18:
                    return 'R';
                    break;
                case 19:
                    return 'S';
                    break;
                case 20:
                    return 'T';
                    break;
                case 21:
                    return 'U';
                    break;
                case 22:
                    return 'V';
                    break;
                case 23:
                    return 'W';
                    break;
                case 24:
                    return 'X';
                    break;
                case 25:
                    return 'Y';
                    break;
                case 26:
                    return 'Z';
                    break;
                default:
                    return '?';
                    break;
            }
        }
    }
}
