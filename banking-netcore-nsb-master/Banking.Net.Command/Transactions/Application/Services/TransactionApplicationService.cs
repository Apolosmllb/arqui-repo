using Banking.Net.Command.Transactions.Application.Contracts;
using Banking.Net.Command.Transactions.Application.Dtos;
using Banking.Net.Command.Transactions.Domain.Enums;
using Banking.Net.Transactions.Messages.Commands;
using NServiceBus;
using System;
using System.Threading.Tasks;

namespace Banking.Net.Command.Transactions.Application.Services
{
    public class TransactionApplicationService : ITransactionApplicationService
    {
        private readonly IMessageSession _messageSession;

        public TransactionApplicationService(IMessageSession messageSession)
        {
            _messageSession = messageSession;
        }

        public async Task<PerformDepositResponseDto> PerformDeposit(PerformDepositRequestDto performDepositRequestDto)
        {
            try
            {
                var transactionId = Guid.NewGuid().ToString();
                var performDeposit = new PerformDeposit(
                    transactionId,
                    performDepositRequestDto.AccountNumber,
                    performDepositRequestDto.Amount
                );
                await _messageSession.Send(performDeposit).ConfigureAwait(false);
                return new PerformDepositResponseDto
                {
                    TransactionId = performDeposit.TransactionId,
                    TransactionType = nameof(TransactionType.DEPOSIT),
                    AccountNumber = performDeposit.AccountNumber,
                    Amount = performDeposit.Amount,
                    Status = TransactionStateId.STARTED.ToString(),
                };
            }
            catch
            {
                return new PerformDepositResponseDto
                {
                    TransactionId = "NONE",
                    TransactionType = "NONE",
                    AccountNumber = performDepositRequestDto.AccountNumber,
                    Amount = performDepositRequestDto.Amount,
                    Status = "ERROR"
                };
            }
        }

        public async Task<PerformTransferResponseDto> PerformTransfer(PerformTransferRequestDto performTransferRequestDto)
        {
            try
            {
                var transactionId = Guid.NewGuid().ToString();
                var performTransfer = new StartTransfer(
                    transactionId,
                    performTransferRequestDto.FromBankAccountNumber,
                    performTransferRequestDto.ToBankAccountNumber,
                    performTransferRequestDto.Amount
                );
                await _messageSession.Send(performTransfer).ConfigureAwait(false);
                return new PerformTransferResponseDto
                {
                    TransactionId = performTransfer.TransactionId,
                    TransactionType = nameof(TransactionType.TRANSFER),
                    FromAccountNumber = performTransfer.FromBankAccountNumber,
                    ToAccountNumber = performTransfer.ToBankAccountNumber,
                    Amount = performTransfer.Amount,
                    Status = TransactionStateId.STARTED.ToString(),
                };
            }
            catch
            {
                return new PerformTransferResponseDto
                {
                    TransactionId = "NONE",
                    TransactionType = "NONE",
                    FromAccountNumber = performTransferRequestDto.FromBankAccountNumber,
                    ToAccountNumber = performTransferRequestDto.ToBankAccountNumber,
                    Amount = performTransferRequestDto.Amount,
                    Status = "ERROR"
                };
            }
        }
    }
}