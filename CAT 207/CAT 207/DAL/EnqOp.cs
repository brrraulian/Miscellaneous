using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.DAL
{
    class EnqOp : Classes.Conexao
    {
        public struct st_enq_op
        {
            public long ID_ENQ_OP;
            public long COD_ENQUADRAMENTO;
            public string NUM_DOC;
            public string SER;
            public string PERIODO;
            public bool COMPROV_OP;
        }

        public st_enq_op GetEnqOp(string NUM_DOC, string COD_PROD, string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            st_enq_op st = new st_enq_op();

            try
            {
                string comando = "SELECT * FROM ENQ_OP WHERE NUM_DOC = @NUM_DOC AND COD_PROD = @COD_PROD AND periodo = @periodo";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@NUM_DOC", NUM_DOC);
                    cmd.Parameters.AddWithValue("@COD_PROD", COD_PROD);
                    cmd.Parameters.AddWithValue("@periodo", mes + ano);
                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        dr.Read();

                        if (dr.HasRows)
                        {
                            st.COD_ENQUADRAMENTO = Convert.ToInt32(dr["COD_ENQUADRAMENTO"]);
                            st.NUM_DOC = dr["NUM_DOC"].ToString();
                            st.COMPROV_OP = (bool)dr["COMPROV_OP"];
                        }

                        dr.Close();
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }

            return st;
        }

        public bool InsertEnqOp(st_enq_op st, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            bool ret = true;

            try
            {
                string comando = "INSERT INTO ENQ_OP (COD_ENQUADRAMENTO, NUM_DOC, SER, PERIODO, COMPROV_OP)" +
                    " VALUES (@COD_ENQUADRAMENTO, @NUM_DOC, @SER,  @PERIODO, @COMPROV_OP)";
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@COD_ENQUADRAMENTO", st.COD_ENQUADRAMENTO);
                    cmd.Parameters.AddWithValue("@NUM_DOC", st.NUM_DOC);
                    cmd.Parameters.AddWithValue("@SER", st.SER);
                    cmd.Parameters.AddWithValue("@PERIODO", st.PERIODO);
                    cmd.Parameters.AddWithValue("@COMPROV_OP", st.COMPROV_OP);

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

        public bool DeleteEnqOpByPeriodo(string PERIODO, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            bool ret = true;

            try
            {
                string comando = "DELETE FROM ENQ_OP WHERE PERIODO = @PERIODO";
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
