namespace Banking.Net.Command.Transactions.Application.Dtos
{
    public class PerformDepositRequestDto
    {
        public string AccountNumber { get; set; }
        public decimal Amount { get; set; }
    }
}