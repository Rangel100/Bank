package co.edu.usbcali.bank.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.dto.DepositDTO;
import co.edu.usbcali.bank.dto.TransferDTO;
import co.edu.usbcali.bank.dto.WithdrawDTO;

@Service
@Scope("singleton")
public class BankTransactionServiceImpl implements BankTransactionService {

	// Cambio por proyecto
	// private final static Logger log =
	// LoggerFactory.getLogger(BankTransactionServiceImpl.class);
	//

	@Autowired
	AccountService accountService;

	@Autowired
	UserService userService;

	@Autowired
	TransactionTypeService transactionTypeService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	SendMailServiceImpl sendMailServiceImpl;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long withdraw(WithdrawDTO withdrawDTO) throws Exception {
		if (withdrawDTO == null) {
			throw new Exception("El withdrawDTO es nulo");
		}
		if (withdrawDTO.getAccoId() == null || withdrawDTO.getAccoId().trim().isEmpty() == true) {
			throw new Exception("El AccoId es obligatorio");
		}
		if (withdrawDTO.getAmount() == null || withdrawDTO.getAmount().compareTo(BigDecimal.ONE) < 0) {
			throw new Exception("El Amount es obligatorio y debe ser mayor a cero");
		}
		if (withdrawDTO.getUserEmail() == null || withdrawDTO.getUserEmail().trim().isEmpty() == true) {
			throw new Exception("El UserEmail es obligatorio");
		}
		if (accountService.findById(withdrawDTO.getAccoId()).isPresent() == false) {
			throw new Exception("El account con id: " + withdrawDTO.getAccoId() + " no existe");
		}

		Account account = accountService.findById(withdrawDTO.getAccoId()).get();

		if (account.getEnable().trim().equals("N") == true) {
			throw new Exception("El account con id: " + withdrawDTO.getAccoId() + " se encuentra inactiva");
		}
		if (account.getBalance().compareTo(withdrawDTO.getAmount()) < 0) {
			throw new Exception("El account con id: " + withdrawDTO.getAccoId() + " no tiene saldo suficiente");
		}

		if (userService.findById(withdrawDTO.getUserEmail()).isPresent() == false) {
			throw new Exception("El user con id: " + withdrawDTO.getUserEmail() + " no existe");
		}

		User user = userService.findById(withdrawDTO.getUserEmail()).get();

		if (user.getEnable().trim().equals("N") == true) {
			throw new Exception("El user con id: " + withdrawDTO.getUserEmail() + " se encuentra inactiva");
		}
		// Cambio por proyecto
		if (user.getUserType().getUstyId() != 1L) {
			throw new Exception("El user con id: " + withdrawDTO.getUserEmail() + " no es un cajero");
		}
		//

		TransactionType transactionType = transactionTypeService.findById(1L).get();

		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(withdrawDTO.getAmount());
		transaction.setDate(new Timestamp(System.currentTimeMillis()));
		transaction.setTranId(null);
		transaction.setTransactionType(transactionType);
		transaction.setUser(user);

		BigDecimal nuevoSaldo = account.getBalance().subtract(withdrawDTO.getAmount());
		account.setBalance(nuevoSaldo);

		transaction = transactionService.save(transaction);
		accountService.update(account);
		// Cambio por proyeto,envio de correo por transaccion
		sendMailServiceImpl.sendMailTransactionWD(account, transaction);
		//

		return transaction.getTranId();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long deposit(DepositDTO depositDTO) throws Exception {
		if (depositDTO == null) {
			throw new Exception("El depositDTO es nulo");
		}
		if (depositDTO.getAccoId() == null || depositDTO.getAccoId().trim().isEmpty() == true) {
			throw new Exception("El AccoId es obligatorio2");
		}
		if (depositDTO.getAmount() == null || depositDTO.getAmount().compareTo(BigDecimal.ONE) < 0) {
			throw new Exception("El Amount es obligatorio y debe ser mayor a cero");
		}
		if (depositDTO.getUserEmail() == null || depositDTO.getUserEmail().trim().isEmpty() == true) {
			throw new Exception("El UserEmail es obligatorio");
		}
		if (accountService.findById(depositDTO.getAccoId()).isPresent() == false) {
			throw new Exception("El account con id: " + depositDTO.getAccoId() + " no existe");
		}

		Account account = accountService.findById(depositDTO.getAccoId()).get();

		// Cambio por proyecto
		// Deposito para activar cuenta
		if (account.getBalance().compareTo(new BigDecimal(0)) == 0 && account.getEnable().trim().equals("N") == true
				&& depositDTO.getAmount().compareTo(new BigDecimal(200000)) >= 0) {
			account.setEnable("S");
			accountService.update(account);
		}
		//

		if (account.getEnable().trim().equals("N") == true) {
			throw new Exception("El account con id: " + depositDTO.getAccoId() + " se encuentra inactiva");
		}

		if (userService.findById(depositDTO.getUserEmail()).isPresent() == false) {
			throw new Exception("El user con id: " + depositDTO.getUserEmail() + " no existe");
		}

		User user = userService.findById(depositDTO.getUserEmail()).get();

		if (user.getEnable().trim().equals("N") == true) {
			throw new Exception("El user con id: " + depositDTO.getUserEmail() + " se encuentra inactiva");
		}
		// Cambio por proyecto
		if (user.getUserType().getUstyId() != 1L) {
			throw new Exception("El user con id: " + depositDTO.getUserEmail() + " no es un cajero");
		}
		//

		TransactionType transactionType = transactionTypeService.findById(2L).get();

		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(depositDTO.getAmount());
		transaction.setDate(new Timestamp(System.currentTimeMillis()));
		transaction.setTranId(null);
		transaction.setTransactionType(transactionType);
		transaction.setUser(user);

		BigDecimal nuevoSaldo = account.getBalance().add(depositDTO.getAmount());
		account.setBalance(nuevoSaldo);

		transaction = transactionService.save(transaction);
		accountService.update(account);

		// Cambio por proyeto,envio de correo por transaccion
		sendMailServiceImpl.sendMailTransactionWD(account, transaction);
		//

		return transaction.getTranId();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long transfer(TransferDTO transferDTO) throws Exception {

		Account account = accountService.findById(transferDTO.getAccoIdOrigin()).get();
		// Cambio por proyecto, Complementacion para saber saldo insuficiente en
		// trancerencia por los 2000
		if (account.getBalance().compareTo(transferDTO.getAmount().add(new BigDecimal(2000))) < 0) {
			throw new Exception("Saldo insuficiente para transferencia");
		}
		//

		WithdrawDTO withdrawDTO = new WithdrawDTO(transferDTO.getAccoIdOrigin(), transferDTO.getAmount(),
				transferDTO.getUserEmail());
		withdraw(withdrawDTO);

		DepositDTO depositDTO = new DepositDTO(transferDTO.getAccoIdDestination(), transferDTO.getAmount(),
				transferDTO.getUserEmail());
		deposit(depositDTO);

		withdrawDTO = new WithdrawDTO(transferDTO.getAccoIdOrigin(), new BigDecimal(2000), transferDTO.getUserEmail());
		withdraw(withdrawDTO);

		depositDTO = new DepositDTO("9999-9999-9999-9999", new BigDecimal(2000), transferDTO.getUserEmail());
		deposit(depositDTO);

		TransactionType transactionType = transactionTypeService.findById(3L).get();

		User user = userService.findById(transferDTO.getUserEmail()).get();

		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(transferDTO.getAmount());
		transaction.setDate(new Timestamp(System.currentTimeMillis()));
		transaction.setTranId(null);
		transaction.setTransactionType(transactionType);
		transaction.setUser(user);

		transactionService.save(transaction);

		// Cambio de proyecto para mandar correo
//		Account accountDes = accountService.findById(transferDTO.getAccoIdDestination()).get();
//		sendMailServiceImpl.sendMailTransactionT(account, accountDes, transaction);
		//

		return transaction.getTranId();
	}

}
