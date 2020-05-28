package co.edu.usbcali.bank.service;

import co.edu.usbcali.bank.dto.DepositDTO;
import co.edu.usbcali.bank.dto.TransferDTO;
import co.edu.usbcali.bank.dto.WithdrawDTO;

public interface BankTransactionService {

	public Long withdraw(WithdrawDTO withdrawDTO) throws Exception;

	public Long deposit(DepositDTO depositDTO) throws Exception;

	public Long transfer(TransferDTO transferDTO) throws Exception;
}
