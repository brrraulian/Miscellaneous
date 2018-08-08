
namespace Demo.Classes
{
    public class Connection
    {
        public string getConnection()
        {
            System.Configuration.Configuration rootWebConfig = System.Web.Configuration.WebConfigurationManager.OpenWebConfiguration("/Demo");
            System.Configuration.ConnectionStringSettings connString = rootWebConfig.ConnectionStrings.ConnectionStrings["CnxDemo"];

            return connString.ConnectionString;
        }

    }
}