using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Valemobi
{
    public partial class novo : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            lbl_TITULO.Text = "NOVA MERCADORIA";
            this.Title = lbl_TITULO.Text;
        }


        protected void btn_salvar_Click(object sender, EventArgs e)
        {
            Classes.DB db = new Classes.DB();
            Classes.DB.stMercadoria st = new Classes.DB.stMercadoria();

            st.TIPO_MERC = txt_tipoMerc.Text;
            st.NOME_MERC = txt_nomeMerc.Text;
            st.QTDE_MERC = Convert.ToInt32(txt_qtdeMerc.Text);
            st.PRECO_MERC = Convert.ToDecimal(txt_precoMerc.Text) / 100;
            st.TIPO_NEGOCIO = (cbox_tipoNeg.SelectedItem.Value == "Compra") ? 'C' : 'V';

            if (db.InserirCliente(st))
                Response.Redirect("Default.aspx", true);
            else
                lbl_erro.Text = "Erro ao inserir mercadoria.";
        }

    }
}