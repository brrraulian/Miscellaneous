using System;
using System.Web.UI.WebControls;

namespace Demo
{
    public partial class Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            label_title.Text = "Products";
            this.Title = label_title.Text;

            if (!IsPostBack)
                bindGrid();
        }

        private void bindGrid()
        {
            Classes.Data data = new Classes.Data();
            Grid.DataSource = data.getProducts();
            Grid.DataBind();
        }

        protected void Grid_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                TableCell statusCell = e.Row.Cells[5];
                if (statusCell.Text == "B")
                {
                    statusCell.Text = "Buy";
                }
                if (statusCell.Text == "S")
                {
                    statusCell.Text = "Sell";
                }
            }
        }
        
    }
}