using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.DAL
{
    class ArquivosGerados : Classes.Conexao
    {
        public struct st_arquivos_gerados
        {
            public string ID_ARQUIVO;
            public string ARQUIVO;
            public decimal IVA_PROPRIO;
            public decimal PMC;
            public string PERIODO_ARQUIVO;
            public string PERIODO_GERACAO;
            public string FILIAL;
        }

        public long GetArquivo(string periodo, string filial)
        {
            long id_arquivo = 0;

            try
            {
                string comando = "SELECT * FROM ARQUIVOS_GERADOS WHERE periodo = @periodo AND filial = @filial";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@periodo", periodo);
                    cmd.Parameters.AddWithValue("@filial", filial);
                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        dr.Read();

                        if (dr.HasRows)
                            id_arquivo = Convert.ToInt64(dr["ID_ARQUIVO"]);

                        dr.Close();
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                id_arquivo = 0;
                cnx.Close();
            }

            return id_arquivo;
        }

        public void DeleteArquivo(long ID_ARQUIVO)
        {
            try
            {
                string comando = "DELETE FROM ARQUIVOS_GERADOS WHERE ID_ARQUIVO = @ID_ARQUIVO";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@ID_ARQUIVO", ID_ARQUIVO);

                    cnx.Open();
                    cmd.ExecuteNonQuery();
                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }
        }

        public bool InsertArquivo(st_arquivos_gerados st)
        {
            bool ret = true;

            try
            {
                string comando = "INSERT INTO ARQUIVOS_GERADOS (ARQUIVO, IVA_PROPRIO, PMC, PERIODO_ARQUIVO, PERIODO_GERACAO, FILIAL)" +
                    " VALUES (@ARQUIVO, @IVA_PROPRIO, @PMC, @PERIODO_ARQUIVO, @PERIODO_GERACAO, @FILIAL)";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@ARQUIVO", st.ARQUIVO);
                    cmd.Parameters.AddWithValue("@IVA_PROPRIO", st.IVA_PROPRIO);
                    cmd.Parameters.AddWithValue("@PMC", st.PMC);
                    cmd.Parameters.AddWithValue("@PERIODO_ARQUIVO", st.PERIODO_ARQUIVO);
                    cmd.Parameters.AddWithValue("@PERIODO_GERACAO", st.PERIODO_GERACAO);
                    cmd.Parameters.AddWithValue("@FILIAL", st.FILIAL);

                    cnx.Open();
                    cmd.ExecuteNonQuery();

                    cmd.CommandText = "SELECT @@Identity";
                    long id = Convert.ToInt64(cmd.ExecuteScalar());

                    if (id == 0 || id == null)
                        ret = false;

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
