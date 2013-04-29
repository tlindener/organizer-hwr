using System.IdentityModel.Selectors;
using System.IdentityModel.Tokens;
using System.Net;

namespace Organizer.WebService
{
    public class UserNameValidator : UserNamePasswordValidator
    {
        public override void Validate(string userName, string password)
        {
            TimePlanner timeplanner = new TimePlanner();
           // Validate user against Organizer database
            //if (!timeplanner.ValidateUser(userName, password))
            //{
            //    SecurityTokenException ex = new SecurityTokenException("Validation Failed!");

            //    // Doesn't always work: http://blogs.msdn.com/b/drnick/archive/2010/02/02/fix-to-allow-customizing-the-status-code-when-validation-fails.aspx
            //    ex.Data["HttpStatusCode"] = HttpStatusCode.Unauthorized;

            //    throw ex;
            //}
        }
    }

}