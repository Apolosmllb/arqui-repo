namespace Banking.Net.Common.Domain.Entities
{
    public class Error
    {
        public string Message { get; private set; }

        public Error(string message)
        {
            Message = message;
        }
    }
}