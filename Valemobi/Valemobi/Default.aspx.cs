using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Valemobi
{
    public partial class Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            lbl_TITULO.Text = "MERCADORIAS";
            this.Title = lbl_TITULO.Text;

            if (!IsPostBack)
                bindGrid();
        }

        private void bindGrid()
        {
            Classes.DB db = new Classes.DB();
            Grid.DataSource = db.GetMercadorias();
            Grid.DataBind();
        }

        protected void Grid_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                TableCell statusCell = e.Row.Cells[5];
                if (statusCell.Text == "C")
                {
                    statusCell.Text = "Compra";
                }
                if (statusCell.Text == "V")
                {
                    statusCell.Text = "Venda";
                }
            }
        }
        
    }
}