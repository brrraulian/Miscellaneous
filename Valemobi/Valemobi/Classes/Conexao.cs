using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Valemobi.Classes
{
    public class Conexao
    {
        public string getCnx()
        {
            System.Configuration.Configuration rootWebConfig = System.Web.Configuration.WebConfigurationManager.OpenWebConfiguration("/Valemobi");
            System.Configuration.ConnectionStringSettings connString = rootWebConfig.ConnectionStrings.ConnectionStrings["CnxValemobi"];

            return connString.ConnectionString;
        }

    }
}