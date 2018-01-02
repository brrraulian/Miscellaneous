namespace CAT_207
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lbl_msg = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.cbox_ano = new System.Windows.Forms.ComboBox();
            this.cbox_mes = new System.Windows.Forms.ComboBox();
            this.btn_gerar = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.cbox_cliente = new System.Windows.Forms.ComboBox();
            this.btn_sped = new System.Windows.Forms.Button();
            this.btn_gia = new System.Windows.Forms.Button();
            this.btn_fichas = new System.Windows.Forms.Button();
            this.btn_relatorio = new System.Windows.Forms.Button();
            this.btn_lista = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lbl_msg
            // 
            this.lbl_msg.AutoSize = true;
            this.lbl_msg.Location = new System.Drawing.Point(12, 171);
            this.lbl_msg.Name = "lbl_msg";
            this.lbl_msg.Size = new System.Drawing.Size(0, 13);
            this.lbl_msg.TabIndex = 14;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 95);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(26, 13);
            this.label2.TabIndex = 13;
            this.label2.Text = "Ano";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 57);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(27, 13);
            this.label1.TabIndex = 12;
            this.label1.Text = "Mês";
            // 
            // cbox_ano
            // 
            this.cbox_ano.FormattingEnabled = true;
            this.cbox_ano.Items.AddRange(new object[] {
            "2010",
            "2011",
            "2012",
            "2013",
            "2014",
            "2015",
            "2016"});
            this.cbox_ano.Location = new System.Drawing.Point(56, 92);
            this.cbox_ano.Name = "cbox_ano";
            this.cbox_ano.Size = new System.Drawing.Size(50, 21);
            this.cbox_ano.TabIndex = 11;
            // 
            // cbox_mes
            // 
            this.cbox_mes.FormattingEnabled = true;
            this.cbox_mes.Items.AddRange(new object[] {
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12"});
            this.cbox_mes.Location = new System.Drawing.Point(57, 54);
            this.cbox_mes.Name = "cbox_mes";
            this.cbox_mes.Size = new System.Drawing.Size(50, 21);
            this.cbox_mes.TabIndex = 10;
            // 
            // btn_gerar
            // 
            this.btn_gerar.Location = new System.Drawing.Point(15, 130);
            this.btn_gerar.Name = "btn_gerar";
            this.btn_gerar.Size = new System.Drawing.Size(100, 23);
            this.btn_gerar.TabIndex = 9;
            this.btn_gerar.Text = "Gerar Arquivo";
            this.btn_gerar.UseVisualStyleBackColor = true;
            this.btn_gerar.Click += new System.EventHandler(this.btn_gerar_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 19);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(39, 13);
            this.label4.TabIndex = 18;
            this.label4.Text = "Cliente";
            // 
            // cbox_cliente
            // 
            this.cbox_cliente.FormattingEnabled = true;
            this.cbox_cliente.Items.AddRange(new object[] {
            "AGROFIELD",
            "ALBATROS",
            "CAMBUCI",
            "DAYCO",
            "DDL",
            "ELCOA",
            "MILWAUKEE",
            "SAWEM",
            "SEPSA",
            "STOLLE"});
            this.cbox_cliente.Location = new System.Drawing.Point(57, 16);
            this.cbox_cliente.Name = "cbox_cliente";
            this.cbox_cliente.Size = new System.Drawing.Size(100, 21);
            this.cbox_cliente.TabIndex = 17;
            this.cbox_cliente.SelectedIndexChanged += new System.EventHandler(this.cbox_cliente_SelectedIndexChanged);
            // 
            // btn_sped
            // 
            this.btn_sped.Location = new System.Drawing.Point(172, 14);
            this.btn_sped.Name = "btn_sped";
            this.btn_sped.Size = new System.Drawing.Size(100, 23);
            this.btn_sped.TabIndex = 19;
            this.btn_sped.Text = "Sped";
            this.btn_sped.UseVisualStyleBackColor = true;
            this.btn_sped.Click += new System.EventHandler(this.btn_sped_Click);
            // 
            // btn_gia
            // 
            this.btn_gia.Location = new System.Drawing.Point(172, 52);
            this.btn_gia.Name = "btn_gia";
            this.btn_gia.Size = new System.Drawing.Size(100, 23);
            this.btn_gia.TabIndex = 20;
            this.btn_gia.Text = "GIA";
            this.btn_gia.UseVisualStyleBackColor = true;
            this.btn_gia.Click += new System.EventHandler(this.btn_gia_Click);
            // 
            // btn_fichas
            // 
            this.btn_fichas.Location = new System.Drawing.Point(172, 90);
            this.btn_fichas.Name = "btn_fichas";
            this.btn_fichas.Size = new System.Drawing.Size(100, 23);
            this.btn_fichas.TabIndex = 23;
            this.btn_fichas.Text = "Gerar Fichas";
            this.btn_fichas.UseVisualStyleBackColor = true;
            this.btn_fichas.Click += new System.EventHandler(this.btn_fichas_Click);
            // 
            // btn_relatorio
            // 
            this.btn_relatorio.Location = new System.Drawing.Point(172, 130);
            this.btn_relatorio.Name = "btn_relatorio";
            this.btn_relatorio.Size = new System.Drawing.Size(100, 23);
            this.btn_relatorio.TabIndex = 24;
            this.btn_relatorio.Text = "Gerar Relatório";
            this.btn_relatorio.UseVisualStyleBackColor = true;
            this.btn_relatorio.Click += new System.EventHandler(this.btn_relatorio_Click);
            // 
            // btn_lista
            // 
            this.btn_lista.Location = new System.Drawing.Point(172, 166);
            this.btn_lista.Name = "btn_lista";
            this.btn_lista.Size = new System.Drawing.Size(100, 23);
            this.btn_lista.TabIndex = 25;
            this.btn_lista.Text = "Gerar Lista";
            this.btn_lista.UseVisualStyleBackColor = true;
            this.btn_lista.Click += new System.EventHandler(this.btn_lista_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 212);
            this.Controls.Add(this.btn_lista);
            this.Controls.Add(this.btn_relatorio);
            this.Controls.Add(this.btn_fichas);
            this.Controls.Add(this.btn_gia);
            this.Controls.Add(this.btn_sped);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.cbox_cliente);
            this.Controls.Add(this.lbl_msg);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cbox_ano);
            this.Controls.Add(this.cbox_mes);
            this.Controls.Add(this.btn_gerar);
            this.Name = "Form1";
            this.Text = "CAT 207";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbl_msg;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox cbox_ano;
        private System.Windows.Forms.ComboBox cbox_mes;
        private System.Windows.Forms.Button btn_gerar;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox cbox_cliente;
        private System.Windows.Forms.Button btn_sped;
        private System.Windows.Forms.Button btn_gia;
        private System.Windows.Forms.Button btn_fichas;
        private System.Windows.Forms.Button btn_relatorio;
        private System.Windows.Forms.Button btn_lista;

    }
}

