package Model;

import javax.swing.JTextArea;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Logger {

	private static JTextArea jta;

	public static void setJta(JTextArea jta) {
		Logger.jta = jta;
	}

	public static void i(Object message) {
		Logger.i("", message);

	}

	public static void d(Object message) {
		Logger.d("", message);

	}

	public static void e(Object message) {
		Logger.e("", message);

	}

	public static void f(Object message) {
		Logger.f("", message);

	}

	public static void i(Object method, Object message) {
		print("<<INFO>>", method, message);
	}

	public static void d(Object method, Object message) {
		print("<<DEBUG>>", method, message);

	}

	public static void e(Object method, Object message) {
		print("<<ERROR>>", method, message);

	}

	public static void f(Object method, Object message) {
		print("<<FATAL>>", method, message);

	}

	private static void print(String code, Object method, Object message) {
		String s = code + " " + method.toString() + "///" + message.toString() + System.lineSeparator(); 
		jta.append(s);
		System.out.println(s);
        
	}
	
	public static String getMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName() +"() ";
	}
}
