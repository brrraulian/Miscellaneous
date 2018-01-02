using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace Valemobi.Classes
{
    public class DB : Conexao
    {        
        public struct stMercadoria
        {
            public int ID_MERC;
            public string TIPO_MERC;
            public string NOME_MERC;
            public int QTDE_MERC;
            public decimal PRECO_MERC;
            public char TIPO_NEGOCIO;

        }

        public bool InserirCliente(stMercadoria st)
        {
            bool ret = false;

            string comando = "INSERT INTO teste (TIPO_MERC, NOME_MERC, QTDE_MERC, PRECO_MERC, TIPO_NEGOCIO) " +
                            "VALUES (@TIPO_MERC, @NOME_MERC, @QTDE_MERC, @PRECO_MERC, @TIPO_NEGOCIO)";

            System.Configuration.Configuration cnx_string =
                System.Web.Configuration.WebConfigurationManager.OpenWebConfiguration("/MyWebSiteRoot");
            using (SqlConnection cnx = new SqlConnection(getCnx()))
            {
                cnx.Open();

                try
                {
                    using (SqlCommand cmd = new SqlCommand(comando, cnx))
                    {
                        #region Parametros
                        
                        if (st.TIPO_MERC == null || st.TIPO_MERC == "")
                            cmd.Parameters.Add("@TIPO_MERC", SqlDbType.VarChar).Value = DBNull.Value;
                        else
                            cmd.Parameters.AddWithValue("@TIPO_MERC", st.TIPO_MERC);
                        
                        if (st.NOME_MERC == null || st.NOME_MERC == "")
                            cmd.Parameters.Add("@NOME_MERC", SqlDbType.VarChar).Value = DBNull.Value;
                        else
                            cmd.Parameters.AddWithValue("@NOME_MERC", st.NOME_MERC);

                        cmd.Parameters.AddWithValue("@QTDE_MERC", st.QTDE_MERC);
                        cmd.Parameters.AddWithValue("@PRECO_MERC", st.PRECO_MERC);

                        if (st.TIPO_NEGOCIO == null)
                            cmd.Parameters.Add("@TIPO_NEGOCIO", SqlDbType.Char).Value = DBNull.Value;
                        else
                            cmd.Parameters.AddWithValue("@TIPO_NEGOCIO", st.TIPO_NEGOCIO);
                        
                        #endregion
                    
                        cmd.ExecuteNonQuery();

                        cmd.CommandText = "SELECT @@Identity";
                        if (Convert.ToInt32(cmd.ExecuteScalar()) > 0)
                            ret = true;
                    }
                }
                catch (Exception ex)
                {
                    ret = false;
                }
                finally
                {
                    cnx.Close();
                }
            }

            return ret;
        }

        public DataTable GetMercadorias()
        {
            DataTable dt = new DataTable();

            string comando = "SELECT * FROM teste";

            using (SqlConnection cnx = new SqlConnection(getCnx()))
            {
                using (SqlDataAdapter da = new SqlDataAdapter())
                {
                    da.SelectCommand = new SqlCommand(comando, cnx);

                    cnx.Open();

                    try
                    {
                        da.Fill(dt);
                    }
                    catch (Exception ex)
                    {

                    }
                    finally
                    {
                        cnx.Close();
                    }
                }
            }

            return dt;
        }


    }
}