package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.dto.DepositDTO;
import co.edu.usbcali.bank.dto.TransferDTO;
import co.edu.usbcali.bank.dto.WithdrawDTO;

@SpringBootTest
class BankTransactionServiceTest {

	@Autowired
	BankTransactionService bankTransaction;
	
	private String accoIdOrigin="0702-9602-7222-2537";
	private String accoIdDestination="0000-0000-0000-0000";
	private BigDecimal amount=new BigDecimal(10000);
	private String userEmail="jhonatan@hotmail.com";
	
	@Test
	@Rollback(false)
	void withdraw() {
		try {
			WithdrawDTO withdrawDTO= new WithdrawDTO(accoIdOrigin,amount,userEmail);
			Long idTransaction=bankTransaction.withdraw(withdrawDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@Rollback(false)
	void deposit() {
		try {
			DepositDTO depositDTO= new DepositDTO(accoIdOrigin,amount,userEmail);
			Long idTransaction=bankTransaction.deposit(depositDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@Rollback(false)
	void transfer() {
		try {
			TransferDTO transferDTO= new TransferDTO(accoIdOrigin,accoIdDestination,amount,userEmail);
			Long idTransaction=bankTransaction.transfer(transferDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}

}
