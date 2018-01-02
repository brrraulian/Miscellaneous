using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.DAL.Bloco0
{
    class Participante : Classes.Conexao
    {
        public DataTable GetParticipantes(string periodo, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT * FROM v_PARTICIPANTE WHERE periodo = @periodo";
                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", periodo);

                        da.SelectCommand.CommandTimeout = 120;
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

        public DataTable GetParticipantesSintegra(string periodo, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            using (DataTable dt = new DataTable())
            {
                try
                {
                    string comando = "SELECT * FROM v_PARTICIPANTE_SINTEGRA WHERE periodo = @periodo";
                    using (SqlDataAdapter da = new SqlDataAdapter(comando, cnx))
                    {
                        da.SelectCommand.Parameters.AddWithValue("@periodo", periodo);

                        da.SelectCommand.CommandTimeout = 240;
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

    }
}
