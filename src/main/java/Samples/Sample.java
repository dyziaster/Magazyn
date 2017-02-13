package Samples;


public class Sample {
////package Samples;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//import com.j256.ormlite.dao.BaseDaoImpl;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.dao.DaoManager;
//import com.j256.ormlite.jdbc.JdbcConnectionSource;
//import com.j256.ormlite.logger.Logger;
//import com.j256.ormlite.logger.LoggerFactory;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//import Entitys.Account;
//import Entitys.Magazyny;
//import Entitys.Narzedzie;
//import Entitys.Obszar;
//
//public class Sample {
//	public static void main(String[] args) throws Exception {
//		
//		Sample sample = new Sample();
//		
////		sample.setUp();
////		Session session = sample.sessionFactory.openSession();
////		session.beginTransaction();
////		session.save(new String("Asdasd"));
////		session.save(new String("Asdassd"));
////		session.getTransaction().commit();
////		session.close();
//		
////		Connection c = null;
////	    try {
////	      Class.forName("org.sqlite.JDBC");
////	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
////	    } catch ( Exception e ) {
////	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
////	      System.exit(0);
////	    }
////	    System.out.println("Opened database successfully");
//
//		
//		
//	       // this uses h2 by default but change to match your database
//        String databaseUrl = "jdbc:sqlite:test.db";
//        // create a connection source to our database
//        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
//        
//        // instantiate the dao
//        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
//        
//  
//        // if you need to create the 'accounts' table make this call
//       // TableUtils.createTable(connectionSource, Account.class);
////        TableUtils.createTable(connectionSource, Narzedzie.class);
////        TableUtils.createTable(connectionSource, Magazyny.class);
////        TableUtils.createTable(connectionSource, Obszar.class);
//        
////        Logger log = LoggerFactory.getLogger(Sample.class);
////        log.debug("sdkjkfhsdjfsds");
//        
//     	
//
//        // create an instance of Account
//        Account account = new Account();
//        account.setName("Jim Coakley");
//
//        // persist the account object to the database
//        accountDao.create(new Account("kuch","asd12"));
//        
//
//        // retrieve the account from the database by its id field (name)
////        Account account2 = accountDao.queryForId("Jim Coakley");
////        
////        System.out.println("Account: " + account2.getName());
//
//        // close the connection source
//        connectionSource.close();
//	}
//	
//	
//	SessionFactory sessionFactory ;
//	
//
//
//protected void setUp() throws Exception {
//	// A SessionFactory is set up once for an application!
//	File hibernatecfg = new File("D:/eclipse_workspace/Magazyn/src/resources/cfg.xml");
//	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//			.configure(hibernatecfg) // configures settings from hibernate.cfg.xml
//			.build();
//	try {
//		sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//	}
//	catch (Exception e) {
//		// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//		// so destroy it manually.
//		e.printStackTrace();
//		StandardServiceRegistryBuilder.destroy( registry );
//	}
//}
//
//
}
