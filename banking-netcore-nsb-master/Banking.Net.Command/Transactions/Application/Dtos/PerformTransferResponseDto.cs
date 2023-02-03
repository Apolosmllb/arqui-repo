namespace Banking.Net.Command.Transactions.Application.Dtos
{
    public class PerformTransferResponseDto
    {
        public string TransactionId { get; set; }
        public string TransactionType { get; set; }
        public string FromAccountNumber { get; set; }
        public string ToAccountNumber { get; set; }
        public decimal Amount { get; set; }
        public string Status { get; set; }
    }
}