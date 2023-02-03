using Banking.Net.Command.Accounts.Domain.Entities;
using Banking.Net.Command.Transactions.Domain.Entities;
using Banking.Net.Command.Transactions.Domain.Enums;
using Banking.Net.Command.Transactions.Domain.ValueObjects;
using Banking.Net.Common.Domain.ValueObjects;
using Banking.Net.Transactions.Messages.Commands;
using NServiceBus;
using NServiceBus.Logging;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Banking.Net.Transactions.Handlers.Commands
{
    public class PerformDepositHandler : IHandleMessages<PerformDeposit>
    {
        static readonly ILog log = LogManager.GetLogger<PerformDepositHandler>();

        public async Task Handle(PerformDeposit performDeposit, IMessageHandlerContext context)
        {
            try
            {
                log.Info($"PerformDepositHandler, TransactionId = {performDeposit.TransactionId}");
                var nHibernateSession = context.SynchronizedStorageSession.Session();
                var bankAccount = nHibernateSession.Query<BankAccount>().FirstOrDefault
                    (x => x.BankAccountNumber.Number == performDeposit.AccountNumber) ?? BankAccount.NonExisting();
                if (bankAccount.DoesNotExist())
                {
                    return;
                }
                bankAccount.Deposit(performDeposit.Amount);
                bankAccount.ChangeUpdatedAt();
                nHibernateSession.Save(bankAccount);
                var transactionId = TransactionId.FromExisting(performDeposit.TransactionId);
                var money = Money.Dollars(performDeposit.Amount);
                var transactionState = TransactionStateId.COMPLETED;
                var now = DateTime.UtcNow;
                var transaction = new Transaction(
                    transactionId,
                    TransactionType.DEPOSIT,
                    null,
                    bankAccount.BankAccountId,
                    money,
                    transactionState,
                    now,
                    now
                );
                nHibernateSession.Save(transaction);
            }
            catch (Exception ex)
            {
                log.Error(ex.Message + " ** " + ex.StackTrace);
            }
        }
    }
}