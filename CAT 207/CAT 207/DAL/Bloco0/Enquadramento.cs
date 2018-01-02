using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.DAL.Bloco0
{
    class Enquadramento : Classes.Conexao
    {
        public struct st_enquadramento
        {
            public int COD_ENQUADRAMENTO;
            public int COD_HIP_GER;
            public string ANEXO;
            public string ARTIGO;
            public string INCISO;
            public string ALINEA;
            public string PARAGRAFO;
            public string ITEM;
            public string LETRA;
            public string OBS;
            public string PERIODO;
        }

        public DataTable GetEnquadramento(string periodo, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT * FROM ENQUADRAMENTO_5D WHERE periodo = @periodo";
                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", periodo);

                        cnx.Open();
                        da.Fill(dt);
                        cnx.Close();
                    }
                }
                catch (Exception ex)
                {
                    cnx.Close();
                }

                return dt;
            }
        }

        public long InsertEnquadramento(st_enquadramento st, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            long ret = 0;

            try
            {
                string comando = "INSERT INTO ENQUADRAMENTO_5D (COD_HIP_GER, ANEXO, ARTIGO, INCISO, ALINEA, PARAGRAFO, ITEM, LETRA, OBS, PERIODO)" +
                                    " VALUES (@COD_HIP_GER, @ANEXO, @ARTIGO, @INCISO, @ALINEA, @PARAGRAFO, @ITEM, @LETRA, @OBS, @PERIODO)";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@COD_HIP_GER", st.COD_HIP_GER);

                    if (st.ANEXO == null || st.ANEXO == string.Empty)
                        cmd.Parameters.Add("@ANEXO", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@ANEXO", st.ANEXO);

                    if (st.ARTIGO == null || st.ARTIGO == string.Empty)
                        cmd.Parameters.Add("@ARTIGO", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@ARTIGO", st.ARTIGO);

                    if (st.INCISO == null || st.INCISO == string.Empty)
                        cmd.Parameters.Add("@INCISO", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@INCISO", st.INCISO);

                    if (st.ALINEA == null || st.ALINEA == string.Empty)
                        cmd.Parameters.Add("@ALINEA", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@ALINEA", st.ALINEA);

                    if (st.PARAGRAFO == null || st.PARAGRAFO == string.Empty)
                        cmd.Parameters.Add("@PARAGRAFO", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@PARAGRAFO", st.PARAGRAFO);

                    if (st.ITEM == null || st.ITEM == string.Empty)
                        cmd.Parameters.Add("@ITEM", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@ITEM", st.ITEM);

                    if (st.LETRA == null || st.LETRA == string.Empty)
                        cmd.Parameters.Add("@LETRA", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@LETRA", st.LETRA);

                    if (st.OBS == null || st.OBS == string.Empty)
                        cmd.Parameters.Add("@OBS", SqlDbType.VarChar).Value = DBNull.Value;
                    else
                        cmd.Parameters.AddWithValue("@OBS", st.OBS);

                    cmd.Parameters.AddWithValue("@PERIODO", st.PERIODO);

                    cnx.Open();
                    cmd.ExecuteNonQuery();

                    cmd.CommandText = "SELECT @@Identity";
                    ret = Convert.ToInt64(cmd.ExecuteScalar());

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                ret = 0;
                cnx.Close();
            }

            return ret;
        }

        public bool DeleteEnquadramentoByPeriodo(string PERIODO, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            bool ret = true;

            try
            {
                string comando = "DELETE FROM ENQUADRAMENTO_5D WHERE PERIODO = @PERIODO";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@PERIODO", PERIODO);

                    cnx.Open();
                    cmd.ExecuteNonQuery();
                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                ret = false;
                cnx.Close();
            }

            return ret;
        }

    }
}
