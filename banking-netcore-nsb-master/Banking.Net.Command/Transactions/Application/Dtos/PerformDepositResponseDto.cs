namespace Banking.Net.Command.Transactions.Application.Dtos
{
    public class PerformDepositResponseDto
    {
        public string TransactionId { get; set; }
        public string TransactionType { get; set; }
        public string AccountNumber { get; set; }
        public decimal Amount { get; set; }
        public string Status { get; set; }
    }
}