package il.ac.hit.model;

/**
 * represent a user in the ExpenseManagement application
 * @author ortal 
 *
 */
public class User {
	
	/**
	 * user name
	 */
	private String userName;
	
	/**
	 * user password
	 */
	private String password;
	
	/**
	 * user email
	 */
	private String email;
	
	/**
	 * Default C'tor
	 */
	public User(){
		super();
	}
	
	/**
	 *  c`tor initialize the user 
	 * @param userName
	 * @param password
	 * @param email
	 * @throws ExpenseManagmentException if one or more params is null
	 */
	public User(String userName, String password, String email) throws ExpenseManagmentException
	{
		this.setUserName(userName);
		this.setPassword(password);
		this.setEmail(email);
	}

	/**
	 * return the user name
	 * @return userName
	 */
	public String getUserName() 
	{
		return userName;
	}

	/**
	 * set the user name
	 * @param userName
	 * @throws ExpenseManagmentException if userName is null
	 */
	public void setUserName(String userName) throws ExpenseManagmentException
	{
		if(userName != null)
		{
			this.userName = userName.toLowerCase();
		}
		else throw new ExpenseManagmentException("please enter user name (User)");
	}

	/**
	 * return the user password
	 * @return password
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * set the user password
	 * @param password
	 * @throws ExpenseManagmentException if password is null
	 */
	public void setPassword(String password) throws ExpenseManagmentException
	{
		if(password != null)
		{
			this.password = password;
		}
		else throw new ExpenseManagmentException("please enter user password (User)");
	}
	
	/**
	 * return the user email
	 * @return user email
	 */
	public String getEmail() 
	{
		return email;
	}
	
	/**
	 * set the user email
	 * @param email - the new user email 
	 * @throws ExpenseManagmentException if email is null
	 */
	public void setEmail(String email) throws ExpenseManagmentException
	{
		if(email != null)
		{
			this.email = email.toLowerCase();
		}
		else throw new ExpenseManagmentException("please enter email (User)");
	}
	
	/**
	 * toString of user: [userName, email]
	 */
	@Override
	public String toString() {
		return "[" + userName + ", " + email +"]";
	}	
}
