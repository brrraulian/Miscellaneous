<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="novo.aspx.cs" Inherits="Valemobi.novo" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

    <script src="JavaScript/JavaScript.js" type="text/javascript"></script>
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
                            <td align="center">

                                <table class="tableNovo">
                                    <tr>
                                        <td class="tdLabel">
                                            <asp:Label ID="Label1" runat="server" Text="Tipo da Mercadoria: "></asp:Label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdCampo">
                                            <asp:TextBox ID="txt_tipoMerc" runat="server" CssClass="campoTxt" ToolTip="Ex: Alimento" MaxLength="30"></asp:TextBox>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdLabel">
                                            <asp:Label ID="Label2" runat="server" Text="Nome da Mercadoria: "></asp:Label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdCampo">
                                            <asp:TextBox ID="txt_nomeMerc" runat="server" CssClass="campoTxt" ToolTip="Ex: Banana" MaxLength="30"></asp:TextBox>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdLabel">
                                            <asp:Label ID="Label3" runat="server" Text="Quantidade: "></asp:Label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdCampo">
                                            <asp:TextBox ID="txt_qtdeMerc" runat="server" onkeypress="return teclaNumeroQtde(event)" ToolTip="Ex: 12" Width="100px" CssClass="campoTxt" MaxLength="8"></asp:TextBox>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdLabel">
                                            <asp:Label ID="Label4" runat="server" Text="Preço: "></asp:Label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdCampo">
                                            <asp:TextBox ID="txt_precoMerc" runat="server" onkeyup="Decimal()" onkeypress="return teclaNumeroPreco(event)" ToolTip="Ex: 9.75" Width="100px" CssClass="campoTxt" MaxLength="9"></asp:TextBox>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdLabel">
                                            <asp:Label ID="Label5" runat="server" Text="Tipo do Negócio: "></asp:Label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdCampo">
                                            <asp:DropDownList ID="cbox_tipoNeg" runat="server" CssClass="campoCbox" Width="100px">
                                                <asp:ListItem>Compra</asp:ListItem>
                                                <asp:ListItem>Venda</asp:ListItem>
                                            </asp:DropDownList>
                                        </td>
                                    </tr>
                                </table>

                            </td>
                        </tr>
                        <tr>
                            <td class="td">

                                <asp:Label ID="lbl_erro" runat="server" ForeColor="Red" Text=""></asp:Label>

                            </td>
                        </tr>
                        <tr>
                            <td class="td">
                                <center>
                                    <table class="tableNovoBtn">
                                        <tr>
                                            <td>
                                                <asp:Button ID="btn_salvar" CssClass="botao" OnClientClick="return Validar();" runat="server" Text="Salvar" OnClick="btn_salvar_Click" />
                                            </td>
                                            <td>
                                                <asp:Button ID="btn_voltar" CssClass="botao" runat="server" Text="Voltar" OnClientClick="location.href = 'Default.aspx'; return false;" />
                                            </td>
                                        </tr>
                                    </table>
                                </center>
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
