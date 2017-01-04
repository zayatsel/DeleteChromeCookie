import java.sql.SQLException;

import org.sqlite.SQLiteException;

/**
 * Delete chrome cookies with names
 */

/**
 * @author USER1
 *
 */
public class DeleteCromeCookie {

	static String[] filePaths = {"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies",
			"\\Application Data\\Google\\Chrome\\User Data\\Default\\Cookies",
			"/Library/Application Support/Google/Chrome/Default/Cookies",
			"/.config/chromium/Default/Cookies"};
	/**
	 * @param args - cookies names to delete
	 */
	public static void main(String[] args) {
		String domain = args.length>0?args[0]:null;
		String name = args.length>1?args[1]:null;
		System.out.println("Domain: " + domain + "; name: "+name);
		
		deleteCookies(domain, name);
	}
	
	public static void deleteCookies(String domain, String name){
		for(String filePath : filePaths){
			try {
				deleteCookies(System.getProperty("user.home")+filePath, domain, name);
			} catch (SQLException e) {
				if(e.getMessage().startsWith("path to ")){
					continue;
				}
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private static void deleteCookies(String filePath, String domain, String name) throws ClassNotFoundException, SQLException {
		DBCookieEditor editor = new DBCookieEditor(filePath);
		try {			
			Cookie[] cookies = editor.getAllCookies(null, null);			
			System.out.println("Total number: "+ cookies.length);
			
			editor.deleteCookies(domain, name);
			
			cookies = editor.getAllCookies(null, null);
			System.out.println("Total number: "+ cookies.length);
		} catch (SQLiteException e) {
			if(e.getMessage().startsWith("[SQLITE_IOERR_DELETE]"))
			{
				System.out.println("Can't delete cookies. Cookies file is being used. Probably chrome process is running");
			} else{
				throw e;
			}
		} 
		
	}

}
