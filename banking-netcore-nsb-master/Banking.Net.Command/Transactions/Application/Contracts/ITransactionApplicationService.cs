using Banking.Net.Command.Transactions.Application.Dtos;
using System.Threading.Tasks;

namespace Banking.Net.Command.Transactions.Application.Contracts
{
    public interface ITransactionApplicationService
    {
        Task<PerformDepositResponseDto> PerformDeposit(PerformDepositRequestDto dto);
        Task<PerformTransferResponseDto> PerformTransfer(PerformTransferRequestDto dto);
    }
}