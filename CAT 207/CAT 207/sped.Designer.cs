namespace CAT_207
{
    partial class sped
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
            this.btn_cancel = new System.Windows.Forms.Button();
            this.btn_next = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.btn_openfolder = new System.Windows.Forms.Button();
            this.txt_sourcefolder = new System.Windows.Forms.TextBox();
            this.ofd1 = new System.Windows.Forms.OpenFileDialog();
            this.lbl_msg = new System.Windows.Forms.Label();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btn_cancel
            // 
            this.btn_cancel.Location = new System.Drawing.Point(253, 99);
            this.btn_cancel.Margin = new System.Windows.Forms.Padding(2);
            this.btn_cancel.Name = "btn_cancel";
            this.btn_cancel.Size = new System.Drawing.Size(68, 23);
            this.btn_cancel.TabIndex = 14;
            this.btn_cancel.Text = "Cancelar";
            this.btn_cancel.UseVisualStyleBackColor = true;
            this.btn_cancel.Click += new System.EventHandler(this.btn_cancel_Click);
            // 
            // btn_next
            // 
            this.btn_next.Location = new System.Drawing.Point(129, 99);
            this.btn_next.Margin = new System.Windows.Forms.Padding(2);
            this.btn_next.Name = "btn_next";
            this.btn_next.Size = new System.Drawing.Size(68, 23);
            this.btn_next.TabIndex = 13;
            this.btn_next.Text = "Ler";
            this.btn_next.UseVisualStyleBackColor = true;
            this.btn_next.Click += new System.EventHandler(this.btn_next_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.btn_openfolder);
            this.groupBox2.Controls.Add(this.txt_sourcefolder);
            this.groupBox2.Location = new System.Drawing.Point(11, 11);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(2);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(2);
            this.groupBox2.Size = new System.Drawing.Size(320, 67);
            this.groupBox2.TabIndex = 12;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Arquivo Sped";
            // 
            // btn_openfolder
            // 
            this.btn_openfolder.Location = new System.Drawing.Point(290, 38);
            this.btn_openfolder.Margin = new System.Windows.Forms.Padding(2);
            this.btn_openfolder.Name = "btn_openfolder";
            this.btn_openfolder.Size = new System.Drawing.Size(22, 19);
            this.btn_openfolder.TabIndex = 9;
            this.btn_openfolder.Text = "...";
            this.btn_openfolder.UseVisualStyleBackColor = true;
            this.btn_openfolder.Click += new System.EventHandler(this.btn_openfolder_Click);
            // 
            // txt_sourcefolder
            // 
            this.txt_sourcefolder.Location = new System.Drawing.Point(5, 38);
            this.txt_sourcefolder.Margin = new System.Windows.Forms.Padding(2);
            this.txt_sourcefolder.Name = "txt_sourcefolder";
            this.txt_sourcefolder.Size = new System.Drawing.Size(284, 20);
            this.txt_sourcefolder.TabIndex = 8;
            // 
            // ofd1
            // 
            this.ofd1.FileName = "openFileDialog1";
            this.ofd1.Filter = "Text files (*.txt)|*.txt|Sped files (*.sped)|*.sped";
            this.ofd1.Title = "Selecionar Sped";
            // 
            // lbl_msg
            // 
            this.lbl_msg.AutoSize = true;
            this.lbl_msg.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_msg.ForeColor = System.Drawing.Color.Red;
            this.lbl_msg.Location = new System.Drawing.Point(13, 145);
            this.lbl_msg.Name = "lbl_msg";
            this.lbl_msg.Size = new System.Drawing.Size(0, 13);
            this.lbl_msg.TabIndex = 15;
            // 
            // sped
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(344, 167);
            this.Controls.Add(this.lbl_msg);
            this.Controls.Add(this.btn_cancel);
            this.Controls.Add(this.btn_next);
            this.Controls.Add(this.groupBox2);
            this.Name = "sped";
            this.Text = "sped";
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_cancel;
        private System.Windows.Forms.Button btn_next;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btn_openfolder;
        private System.Windows.Forms.TextBox txt_sourcefolder;
        private System.Windows.Forms.OpenFileDialog ofd1;
        private System.Windows.Forms.Label lbl_msg;

    }
}