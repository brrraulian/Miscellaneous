<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="Valemobi.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

    <link href="CSS/StyleSheet.css" rel="stylesheet" type="text/css" />

</head>
<body>
    <form id="form1" runat="server">
        <table style="width: 100%">
            <tr>
                <td class="bg"></td>
                <td>
                    <table class="table">
                        <tr>
                            <td class="titulo">

                                <asp:Label ID="lbl_TITULO" runat="server" Text=""></asp:Label>

                            </td>
                        </tr>
                        <tr>
                            <td class="tdBtn">
                                <asp:Button ID="btn_novo" CssClass="botao" runat="server" Text="Novo" OnClientClick="location.href = 'novo.aspx'; return false;" ToolTip="Nova Mercadoria" />

                            </td>
                        </tr>
                        <tr>
                            <td class="td">

                                <asp:GridView ID="Grid" runat="server" AutoGenerateColumns="False" OnRowDataBound="Grid_RowDataBound" EmptyDataText="Sem Mercadorias" BorderColor="LightGray" BorderStyle="Solid" BorderWidth="3px">
                                    <Columns>
                                        <asp:BoundField DataField="ID_MERC" HeaderText="Código da Mercadoria">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                        <asp:BoundField DataField="TIPO_MERC" HeaderText="Tipo de Mercadoria">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                        <asp:BoundField DataField="NOME_MERC" HeaderText="Nome">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                        <asp:BoundField DataField="QTDE_MERC" HeaderText="Quantidade">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                        <asp:BoundField DataField="PRECO_MERC" HeaderText="Preço">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                        <asp:BoundField DataField="TIPO_NEGOCIO" HeaderText="Tipo de Negócio">
                                            <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                        </asp:BoundField>
                                    </Columns>
                                    <EmptyDataRowStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                                    <HeaderStyle Font-Size="Medium" Height="50px" Font-Bold="True" Font-Names="Verdana" ForeColor="Gray" />
                                    <RowStyle BorderStyle="Solid" BorderWidth="3px" Height="30px" />
                                </asp:GridView>

                            </td>
                        </tr>
                    </table>
                </td>

                <td class="bg"></td>
            </tr>
        </table>
    </form>
</body>
</html>
