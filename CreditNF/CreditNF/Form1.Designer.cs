namespace CreditNF
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.gb_sourceFolder = new System.Windows.Forms.GroupBox();
            this.btn_sourceFolder = new System.Windows.Forms.Button();
            this.txt_sourceFolder = new System.Windows.Forms.TextBox();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.gb_destinyFolder = new System.Windows.Forms.GroupBox();
            this.btn_destinyFolder = new System.Windows.Forms.Button();
            this.txt_destinyFolder = new System.Windows.Forms.TextBox();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.btn_convert = new System.Windows.Forms.Button();
            this.gb_sourceFolder.SuspendLayout();
            this.gb_destinyFolder.SuspendLayout();
            this.SuspendLayout();
            // 
            // gb_sourceFolder
            // 
            this.gb_sourceFolder.Controls.Add(this.btn_sourceFolder);
            this.gb_sourceFolder.Controls.Add(this.txt_sourceFolder);
            this.gb_sourceFolder.Location = new System.Drawing.Point(12, 12);
            this.gb_sourceFolder.Name = "gb_sourceFolder";
            this.gb_sourceFolder.Size = new System.Drawing.Size(219, 53);
            this.gb_sourceFolder.TabIndex = 0;
            this.gb_sourceFolder.TabStop = false;
            this.gb_sourceFolder.Text = "Planilha";
            // 
            // btn_sourceFolder
            // 
            this.btn_sourceFolder.Location = new System.Drawing.Point(183, 17);
            this.btn_sourceFolder.Name = "btn_sourceFolder";
            this.btn_sourceFolder.Size = new System.Drawing.Size(30, 23);
            this.btn_sourceFolder.TabIndex = 1;
            this.btn_sourceFolder.Text = "...";
            this.btn_sourceFolder.UseVisualStyleBackColor = true;
            this.btn_sourceFolder.Click += new System.EventHandler(this.btn_sourceFolder_Click);
            // 
            // txt_sourceFolder
            // 
            this.txt_sourceFolder.Location = new System.Drawing.Point(6, 19);
            this.txt_sourceFolder.Name = "txt_sourceFolder";
            this.txt_sourceFolder.Size = new System.Drawing.Size(171, 20);
            this.txt_sourceFolder.TabIndex = 0;
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            // 
            // gb_destinyFolder
            // 
            this.gb_destinyFolder.Controls.Add(this.btn_destinyFolder);
            this.gb_destinyFolder.Controls.Add(this.txt_destinyFolder);
            this.gb_destinyFolder.Location = new System.Drawing.Point(12, 81);
            this.gb_destinyFolder.Name = "gb_destinyFolder";
            this.gb_destinyFolder.Size = new System.Drawing.Size(219, 53);
            this.gb_destinyFolder.TabIndex = 2;
            this.gb_destinyFolder.TabStop = false;
            this.gb_destinyFolder.Text = "Arquivo de texto";
            // 
            // btn_destinyFolder
            // 
            this.btn_destinyFolder.AccessibleDescription = "";
            this.btn_destinyFolder.AccessibleName = "";
            this.btn_destinyFolder.Location = new System.Drawing.Point(183, 17);
            this.btn_destinyFolder.Name = "btn_destinyFolder";
            this.btn_destinyFolder.Size = new System.Drawing.Size(30, 23);
            this.btn_destinyFolder.TabIndex = 1;
            this.btn_destinyFolder.Tag = "";
            this.btn_destinyFolder.Text = "...";
            this.btn_destinyFolder.UseVisualStyleBackColor = true;
            this.btn_destinyFolder.Click += new System.EventHandler(this.btn_destinyFolder_Click);
            // 
            // txt_destinyFolder
            // 
            this.txt_destinyFolder.Location = new System.Drawing.Point(6, 19);
            this.txt_destinyFolder.Name = "txt_destinyFolder";
            this.txt_destinyFolder.Size = new System.Drawing.Size(171, 20);
            this.txt_destinyFolder.TabIndex = 0;
            // 
            // btn_convert
            // 
            this.btn_convert.Font = new System.Drawing.Font("Calibri", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_convert.Image = global::CreditNF.Properties.Resources.image;
            this.btn_convert.Location = new System.Drawing.Point(12, 152);
            this.btn_convert.Name = "btn_convert";
            this.btn_convert.Size = new System.Drawing.Size(219, 49);
            this.btn_convert.TabIndex = 2;
            this.btn_convert.UseVisualStyleBackColor = true;
            this.btn_convert.Click += new System.EventHandler(this.btn_convert_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(243, 213);
            this.Controls.Add(this.gb_destinyFolder);
            this.Controls.Add(this.btn_convert);
            this.Controls.Add(this.gb_sourceFolder);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form1";
            this.Text = "Crédito NF";
            this.gb_sourceFolder.ResumeLayout(false);
            this.gb_sourceFolder.PerformLayout();
            this.gb_destinyFolder.ResumeLayout(false);
            this.gb_destinyFolder.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox gb_sourceFolder;
        private System.Windows.Forms.Button btn_sourceFolder;
        private System.Windows.Forms.TextBox txt_sourceFolder;
        private System.Windows.Forms.Button btn_convert;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.GroupBox gb_destinyFolder;
        private System.Windows.Forms.Button btn_destinyFolder;
        private System.Windows.Forms.TextBox txt_destinyFolder;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
    }
}

