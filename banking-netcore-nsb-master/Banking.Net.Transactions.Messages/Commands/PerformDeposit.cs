using NServiceBus;

namespace Banking.Net.Transactions.Messages.Commands
{
    public class PerformDeposit : ICommand
    {
        public string TransactionId { get; private set; }
        public string AccountNumber { get; private set; }
        public decimal Amount { get; private set; }

        public PerformDeposit(string transactionId, string accountNumber, decimal amount)
        {
            TransactionId = transactionId;
            AccountNumber = accountNumber;
            Amount = amount;
        }
    }
}