using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAT_207.Classes
{
    class Auxiliar : Conexao
    {
        public string GetCnxByCliente(string cliente)
        {
            string ret = "";

            if (cliente.ToLower() == "agrofield")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207AGROFIELD;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "albatros")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207ALBATROS;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "cambuci")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207CAMBUCI;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "dayco")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207DAYCO;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "ddl")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207DDL;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "elcoa")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207ELCOA;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "milwaukee")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207MILWAUKEE;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "sawem")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207SAWEM;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "sepsa")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207SEPSA;Integrated Security=True;Connection Timeout=60";
            else if (cliente.ToLower() == "stolle")
                ret = @"Data Source=Data Source;Initial Catalog=CAT207STOLLE;Integrated Security=True;Connection Timeout=120";

            return ret;
        }

        public string GetEFCnxByCliente(string cliente)
        {
            string ret = "";

            if (cliente.ToLower() == "agrofield")
                ret = @"data source=data source;initial catalog=CAT207AGROFIELD;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "albatros")
                ret = @"data source=data source;initial catalog=CAT207ALBATROS;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "cambuci")
                ret = @"data source=data source;initial catalog=CAT207CAMBUCI;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "dayco")
                ret = @"data source=data source;initial catalog=CAT207DAYCO;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "ddl")
                ret = @"data source=data source;initial catalog=CAT207DDL;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "elcoa")
                ret = @"data source=data source;initial catalog=CAT207ELCOA;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "milwaukee")
                ret = @"data source=data source;initial catalog=CAT207MILWAUKEE;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "sawem")
                ret = @"data source=data source;initial catalog=CAT207SAWEM;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "sepsa")
                ret = @"data source=data source;initial catalog=CAT207SEPSA;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";
            else if (cliente.ToLower() == "stolle")
                ret = @"data source=data source;initial catalog=CAT207STOLLE;integrated security=True;MultipleActiveResultSets=True;App=EntityFramework";

            return ret;
        }

        public string GetCNPJByCliente(string cliente)
        {
            string ret = "";

            if (cliente.ToLower() == "agrofield")
                ret = "CNPJ";
            else if (cliente.ToLower() == "albatros")
                ret = "CNPJ";
            else if (cliente.ToLower() == "cambuci")
                ret = "CNPJ";
            else if (cliente.ToLower() == "dayco")
                ret = "CNPJ";
            else if (cliente.ToLower() == "ddl")
                ret = "CNPJ";
            else if (cliente.ToLower() == "elcoa")
                ret = "CNPJ";
            else if (cliente.ToLower() == "milwaukee")
                ret = "CNPJ";
            else if (cliente.ToLower() == "sawem")
                ret = "CNPJ";
            else if (cliente.ToLower() == "sepsa")
                ret = "CNPJ";
            else if (cliente.ToLower() == "stolle")
                ret = "CNPJ";

            return ret;
        }

        public string GetCNAEByCliente(string cliente)
        {
            string ret = "";

            if (cliente.ToLower() == "agrofield")
                ret = "CNAE";
            else if (cliente.ToLower() == "albatros")
                ret = "CNAE";
            else if (cliente.ToLower() == "cambuci")
                ret = "CNAE";
            else if (cliente.ToLower() == "dayco")
                ret = "CNAE";
            else if (cliente.ToLower() == "ddl")
                ret = "CNAE";
            else if (cliente.ToLower() == "elcoa")
                ret = "CNAE";
            else if (cliente.ToLower() == "milwaukee")
                ret = "CNAE";
            else if (cliente.ToLower() == "sawem")
                ret = "CNAE";
            else if (cliente.ToLower() == "sepsa")
                ret = "CNAE";
            else if (cliente.ToLower() == "stolle")
                ret = "CNAE";

            return ret;
        }

        public List<string> GetCNPJ(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            List<string> ret = new List<string>();

            try
            {
                string comando = "SELECT CNPJ FROM CNPJ_ISENTOS where PERIODO = @periodo";
                
                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@periodo", mes + ano);

                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            ret.Add(dr["CNPJ"].ToString());
                        }
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }

            return ret;
        }

        public string GetIVAPMC(string mes, string ano, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            string ret = "";

            try
            {
                string comando = "SELECT IVA_PROPRIO, PMC FROM ARQUIVOS_GERADOS where PERIODO_ARQUIVO = @periodo";

                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cmd.Parameters.AddWithValue("@periodo", mes + ano);

                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        dr.Read();
                        if (dr.HasRows)
                        {
                            ret += dr["IVA_PROPRIO"].ToString();
                            ret += ";" + dr["PMC"].ToString();
                        }
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }

            return ret;
        }

        public decimal GetIVAMediana(string cnae, string conexao)
        {
            this.cnx.ConnectionString = conexao;
            decimal ret = 0;

            try
            {
                string comando = "SELECT IVA_MEDIANA FROM IVA   where cnae like '%" + cnae + "%'";

                using (SqlCommand cmd = new SqlCommand(comando, cnx))
                {
                    cnx.Open();

                    using (SqlDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            ret = Convert.ToDecimal(dr["IVA_MEDIANA"]);
                        }
                    }

                    cnx.Close();
                }
            }
            catch (Exception ex)
            {
                cnx.Close();
            }

            return ret;
        }

    }
}
