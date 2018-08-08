using System;

namespace Demo
{
    public partial class New : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            label_title.Text = "New Product";
            this.Title = label_title.Text;
        }


        protected void button_save_Click(object sender, EventArgs e)
        {
            Classes.Data data = new Classes.Data();
            Classes.Data.productStruct productStruct = new Classes.Data.productStruct();

            productStruct.PRODUCT_TYPE = text_type.Text;
            productStruct.PRODUCT_NAME= text_name.Text;
            productStruct.PRODUCT_AMOUNT = Convert.ToInt32(text_amount.Text);
            productStruct.PRODUCT_PRICE = Convert.ToDecimal(text_price.Text) / 100;
            productStruct.BUSINESS_TYPE = (comboBox_businessType.SelectedItem.Value == "Buy") ? 'B' : 'S';

            if (data.insertProduct(productStruct))
                Response.Redirect("Default.aspx", true);
            else
                label_error.Text = "Error: Can't insert product.";
        }

    }
}