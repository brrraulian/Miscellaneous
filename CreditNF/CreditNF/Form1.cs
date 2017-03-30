using System;
using System.IO;
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

            try
            {
                System.Data.OleDb.OleDbConnection MyConnection;
                System.Data.DataSet DtSet;
                System.Data.OleDb.OleDbDataAdapter MyCommand;
                MyConnection = new System.Data.OleDb.OleDbConnection("provider=Microsoft.Jet.OLEDB.4.0;Data Source='" + txt_sourceFolder.Text + "';Extended Properties=Excel 8.0;");
                MyCommand = new System.Data.OleDb.OleDbDataAdapter("select * from [Plan1$]", MyConnection);
                MyCommand.TableMappings.Add("Table", "TestTable");
                DtSet = new System.Data.DataSet();
                MyCommand.Fill(DtSet);

                for (int i = 0; i < DtSet.Tables[0].Rows.Count; i++)
                {
                    if (DtSet.Tables[0].Rows[i][1].ToString() != "" &&
                        DtSet.Tables[0].Rows[i][1].ToString().ToLower() != "cliente" &&
                        DtSet.Tables[0].Rows[i][1].ToString().ToLower() != "cancelada")
                    {
                        sb.AppendFormat("{0}{1}", "S1301", DtSet.Tables[0].Rows[i][2].ToString());
                        sb.Append("\r\n");
                    }
                }

                MyConnection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }

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

        #endregion

    }
}
