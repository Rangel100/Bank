package co.edu.usbcali.bank.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.Transaction;

@SpringBootTest
class JpqlTest {

	private final static Logger log = LoggerFactory.getLogger(JpqlTest.class);

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@Transactional(readOnly = true)
	void SelectAll() {
		String jpql = "SELECT cli FROM Client cli";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).getResultList();
		for (Client client : clients) {
			log.info("ID " + client.getClieId() + ", " + "Name " + client.getName());
			// log.info("Name "+ client.getName());
		}
	}

	@Test
	@Transactional(readOnly = true)
	void SelectWhere() {
		String jpql = "SELECT cli FROM Client cli WHERE cli.documentType.dotyId= :dotyId";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).setParameter("dotyId", 1L).getResultList();

		clients.forEach(client -> {
			log.info("ID " + client.getClieId() + ", " + "Name " + client.getName());
		});
	}

	@Test
	@Transactional(readOnly = true)
	void SelectLike() {
		String jpql = "SELECT cli FROM Client cli WHERE cli.name LIKE :name AND" + " cli.documentType.dotyId= :dotyId";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).setParameter("name", "A%")
				.setParameter("dotyId", 1L).getResultList();

		clients.forEach(client -> {
			log.info("ID: " + client.getClieId() + ", " + "Name: " + client.getName());
		});
	}

	@Test
	@Transactional(readOnly = true)
	void SelectAritmeticas() {
		String jpql = "select count(acco) from Account acco";
		Long count = entityManager.createQuery(jpql, Long.class).getSingleResult();
		log.info("count: " + count);

		jpql = "select count(acco)," + "avg(acco.balance)," + "max(acco.balance),"
				+ "sum(acco.balance) from Account acco";

		Object[] object = entityManager.createQuery(jpql, Object[].class).getSingleResult();
		log.info("count: " + object[0]);
		log.info("avg: " + object[1]);
		log.info("max: " + object[2]);
		log.info("sum: " + object[3]);
	}

	@Test
	@Transactional(readOnly = true)
	void SelectSize() {
		String jpql = "SELECT cli FROM Client cli WHERE size(cli.accounts)>3";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).getResultList();

		clients.forEach(client -> {
			log.info("ID: " + client.getClieId() + ", " + "Name: " + client.getName());
		});
	}

	// Consultar la cuenta y el nombre del cliente propietario de la cuenta que
	// tiene mas dinero. Use MAX.
	@Test
	@Transactional(readOnly = true)
	void Uno() {
		String jpql = "select  acco from Account acco where acco.balance=(select max(acc.balance) from Account acc)";
		Account account = entityManager.createQuery(jpql, Account.class).getSingleResult();

		log.info("Client: " + account.getClient().getName() + ", " + "Account: " + account.getAccoId() + ", "
				+ "Balance: " + account.getBalance());
	}

//	Consultar la suma de los saldos disponibles en las cuentas del
//	banco de los clientes que tienen mas de 3 cuentas. Use SUM y
//	muestre en pantalla el nombre del cliente y su saldo total.
	@Test
	@Transactional(readOnly = true)
	void Dos() {
		String jpql = "select acco.client.name,sum(acco.balance) from Account acco where size(acco.client.accounts)>3 group by acco.client.name";
		List<Object[]> objects = entityManager.createQuery(jpql, Object[].class).getResultList();

		objects.forEach(object -> {
			log.info("Name: " + object[0] + ", " + "Balance: " + object[1]);
		});
	}

//	Consultar el promedio de los saldos disponibles en las cuentas del
//	banco. Use AVG
	@Test
	@Transactional(readOnly = true)
	void Tres() {
		String jpql = "select avg(acco.balance) from Account acco";
		Double avg = entityManager.createQuery(jpql, Double.class).getSingleResult();

		log.info("Avg: " + avg);
	}

//	Consultar todos los retiros cuya cantidad este entre 5’000.000 y
//	15.000.000. Use Between
	@Test
	@Transactional(readOnly = true)
	void Cuatro() {
		String jpql = "select tran from Transaction tran where tran.transactionType.trtyId= :trtyId and tran.amount between 5000000 and 15000000";
		List<Transaction> transactions = entityManager.createQuery(jpql, Transaction.class).setParameter("trtyId", 1L)
				.getResultList();

		transactions.forEach(transaction -> {
			log.info("Transaction: " + transaction.getTranId() + ", " + "Amount: " + transaction.getAmount());
		});
	}

//	Consultar la suma de todos las consignaciones que un cliente a
//	realizado a una cuenta conociendo el numero de la cuenta.
	@Test
	@Transactional(readOnly = true)
	void Cinco() {
		String jpql = "select sum(tran.amount) from Transaction tran where tran.transactionType.trtyId = :trtyId and tran.account.accoId = :accoId";
		Object sumConsignaciones = entityManager.createQuery(jpql, Object.class).setParameter("trtyId", 2L)
				.setParameter("accoId", "1215-0877-5497-4162").getSingleResult();

		log.info("sum of consignments" + sumConsignaciones);
	}

//	Consultar todos los clientes que tengan en el nombre una o más
//	apariciones de la letra a. Use LIKE
	@Test
	@Transactional(readOnly = true)
	void Seis() {
		String jpql = "select cli from Client cli where cli.name LIKE :name";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).setParameter("name", "%a%")
				.getResultList();

		clients.forEach(client -> {
			log.info("Name: " + client.getName());
		});
	}

//	Consultar todas las consignaciones que pertenecen a una cuenta
//	por número de cuenta. Mostrar el numero de la cuenta, el nombre
//	del propietario, valor de la consignación.
	@Test
	@Transactional(readOnly = true)
	void Siete() {
		String jpql = "select tran.account.accoId, tran.account.client.name, tran.amount from Transaction tran where tran.transactionType.trtyId = :trtyId and tran.account.accoId = :accoId";
		List<Object[]> consignaciones = entityManager.createQuery(jpql, Object[].class).setParameter("trtyId", 2L)
				.setParameter("accoId", "1215-0877-5497-4162").getResultList();

		consignaciones.forEach(object -> {
			log.info("Account: " + object[0] + ", " + "Name: " + object[1] + ", " + "consignments" + object[2]);
		});
	}

//	Consultar el cliente con numero de cuenta que ha realizado retiros
//	menores a 500.000.
	@Test
	@Transactional(readOnly = true)
	void Ocho() {
		String jpql = "select tran.account.client.name from Transaction tran where tran.transactionType.trtyId = :trtyId and tran.account.accoId = :accoId and tran.amount<500000";
		List<String> transactions = entityManager.createQuery(jpql, String.class).setParameter("trtyId", 1L)
				.setParameter("accoId", "1215-0877-5497-4162").getResultList();
		transactions.forEach(transaction -> {
			log.info("Name: " + transaction);
		});
	}

//	Consultar el número de las cuentas y el nombre del propietario de
//	las cuentas registradas por identificación de cliente que registro la
//	cuenta.
	@Test
	@Transactional(readOnly = true)
	void Nueve() {
		String jpql = "select reac.account.accoId, reac.account.client.name, reac.client.name from RegisteredAccount reac ";
		List<Object[]> consignaciones = entityManager.createQuery(jpql, Object[].class)
				.getResultList();
		consignaciones.forEach(consig -> {
			log.info("Account: " + consig[0] + ", " + "Name: " + consig[1]+ ", " + "Name registered: " + consig[2]);
		});
	}
}
