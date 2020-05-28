package co.edu.usbcali.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;

@Service
@Scope("singleton")
public class SendMailServiceImpl {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMailUpdatePassword(String receptor, String asunto, String menssage) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(receptor);
		simpleMailMessage.setSubject("Cambio de contraseña a la cuenta: " + asunto);
		simpleMailMessage.setText("La nueva contraseña es: " + menssage);

		javaMailSender.send(simpleMailMessage);
	}

	public void sendMailNewClient(String receptor, Account account) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		String message = "El numero de identificacion (clieId) es: " + account.getClient().getClieId() + "\n"
				+ "Su cuenta es: " + account.getAccoId() + "\n" 
				+ "Su conotraseña es: " + account.getPassword() + "\n"
				+ "El estado de su cuenta es: " + account.getEnable()
				+ " y para activarla deve hacer una consignacion de 200.000";

		simpleMailMessage.setTo(receptor);
		simpleMailMessage.setSubject("Nuevo cliente");
		simpleMailMessage.setText(message);

		javaMailSender.send(simpleMailMessage);
	}
	
	public void sendMailNewAccount(String receptor, Account account) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		String message = "Su cuenta es: " + account.getAccoId() + "\n" 
				+ "Su conotraseña es: " + account.getPassword() + "\n"
				+ "El estado de su cuenta es: " + account.getEnable()+ "\n"
				+ "El balance es: " + account.getBalance() + "$";

		simpleMailMessage.setTo(receptor);
		simpleMailMessage.setSubject("Nueva cuenta");
		simpleMailMessage.setText(message);

		javaMailSender.send(simpleMailMessage);
	}

	public void sendMailTransactionWD(Account account, Transaction transaction) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		String message = "Se registro una transaccion en la cuenta: " + account.getAccoId() + "\n"
				+ "El tipo de  transaccion es: " + transaction.getTransactionType().getName() + "\n"
				+ "El valor de la transaccion es: " + transaction.getAmount() + "$" + "\n"
				+ "El nuevo saldo de la cuenta es: " + account.getBalance() + "$";

		simpleMailMessage.setTo(account.getClient().getEmail());
		simpleMailMessage.setSubject("Transaccion");
		simpleMailMessage.setText(message);

		javaMailSender.send(simpleMailMessage);
	}

	public void sendMailTransactionT(Account accountOri, Account accountDes, Transaction transaction) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		String message = "Se registro una transferencia a la cuenta: " + accountDes.getAccoId() + "\n"
				+ "Desde la cuenta: " + accountOri.getAccoId() + "\n" + "El tipo de  transaccion es: "
				+ transaction.getTransactionType().getName() + "\n" + "El valor de la transferencia es: "
				+ transaction.getAmount() + "$";

		simpleMailMessage.setTo(accountDes.getClient().getEmail());
		simpleMailMessage.setSubject("Transferencia");
		simpleMailMessage.setText(message);

		javaMailSender.send(simpleMailMessage);
	}
}
