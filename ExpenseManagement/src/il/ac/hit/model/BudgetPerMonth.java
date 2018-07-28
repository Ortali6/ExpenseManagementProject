package il.ac.hit.model;

import java.io.Serializable;

/**
 * represent budget per each mouth for user
 * 
 * @author ortal
 *
 */
public class BudgetPerMonth implements Serializable{

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the budget id 
	 */
	private int budgetID;
	
	/**
	 * the user name
	 */
	private String userName;
	
	/**
	 * the user budget
	 */
	private double budget;
	
	/**
	 * the user current month
	 */
	private int month;
	
	/**
	 * the user current year
	 */
	private int year;
	
	/**
	 * Default C'tor
	 */
	public BudgetPerMonth(){
		super();
	}
	
	/**
	 * c`tor initialize BudgetPerMonth
	 * 
	 * @param userName 
	 * @param month
	 * @param year
	 * @throws ExpenseManagmentException if one or more params is null
	 */
	public BudgetPerMonth(String userName, int month, int year) throws ExpenseManagmentException
	{
		this.setUserName(userName);
		this.setMonth(month);
		this.setYear(year);
		this.setBudget(0);
	}
	
	/**
	 * return the budget id
	 * @return budgetID
	 */
	public int getBudgetID()
	{
		return budgetID;
	}
	
	/**
	 * set the budget id
	 * @param budgetID
	 */
	public void setBudgetID(int budgetID)
	{
		this.budgetID = budgetID;
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
		else throw new ExpenseManagmentException("please enter user name. (BudgetPerMonth)");
	}
	
	/**
	 * set the user budget
	 * @param budget
	 * @throws ExpenseManagmentException if budget not positive number
	 */
	public void setBudget(double budget) throws ExpenseManagmentException
	{
		if(budget >= 0)
		{
			this.budget = budget;
		}
		else throw new ExpenseManagmentException("budget must be positive number. please try again (BudgetPerMonth)");
	}
	
	/**
	 * return the budget
	 * @return budget
	 */
	public double getBudget() 
	{
		return budget;
	}

	/**
	 * return the month
	 * @return month
	 */
	public int getMonth() 
	{
		return month;
	}

	/**
	 * set the month
	 * @param month
	 * @throws ExpenseManagmentException if month value is not between 1-12
	 */
	public void setMonth(int month) throws ExpenseManagmentException
	{
		if(month > 0 && month < 13)
		{
			this.month = month;
		}
		else throw new ExpenseManagmentException("please enter month value between 1-12 (BudgetPerMonth)");
	}

	/**
	 * get the year
	 * @return year
	 */
	public int getYear() 
	{
		return year;
	}

	/**
	 * set the year
	 * @param year
	 * @throws ExpenseManagmentException if year value is not YYYY format
	 */
	public void setYear(int year) throws ExpenseManagmentException
	{
		if(year - 1900 > 0)
		{
			this.year = year;
		}
		else throw new ExpenseManagmentException("please enter year value format: YYYY (BudgetPerMonth)");
	}
	
	/**
	 * toString of budgetPerMonth: [userName, budget, year/month]
	 */
	@Override
	public String toString() {
		return "[" + userName + ", " + budget + "," + year + "/" + month + "]";
	}	
}
