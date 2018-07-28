package il.ac.hit.model;

import java.util.Date;
import java.util.List;

/**
 * the interface Expense Management DAO - users, items and budgetPerMonths methods declaration
 * @author ortal
 *
 */
public interface IExpenseManagerDAO {

	/**
	 * change the budget per month
	 *  
	 * @param userName - the user name
	 * @param newBudget the new budget
	 * @param month
	 * @year year 
	 * @throws ExpenseManagmentException if failed to change the budget
	 */
	public void changeUserBudgetPerMonth(String userName, double newBudget, int month, int year) throws ExpenseManagmentException;
	
	/**
	 * return the budget per month for the current user 
	 * 
	 * @param userName
	 * @param month
	 * @param year 
	 * @return the budget per month 
	 * @throws ExpenseManagmentException if failed to get the budget
	 */
	public double getBudget(String userName, int month, int year) throws ExpenseManagmentException;
	
	/**
	 * add budget to budgetPerMonth DB if not exists already
	 * 
	 * @param userName
	 * @param month
	 * @param year 
	 * @throws ExpenseManagmentException if failed to add the budget to budgetPerMonth DB
	 */
	public void addBudgetIfNotExists(String userName, int month, int year) throws ExpenseManagmentException;
	
	/**
	 * adding a cost item to costItems DB  
	 * 
	 * @param itemToAdd the cost item to add
	 * @throws ExpenseManagmentException if failed to add the cost item to CostItem DB
	 */
	public void addItem(CostItem itemToAdd) throws ExpenseManagmentException;
	
	/**
	 * update a cost item to costItems DB 
	 * 
	 * @param itemToUpdate the cost item to update
	 * @param newExpenditureAmount the new expenditure amount
	 * @param newCategoty the new category
	 * @param newDescription the new description to update
	 * @param newDate the date to update
	 * @throws ExpenseManagmentException if failed to update the cost item in CostItem DB
	 */
	public void updateItem(CostItem itemToUpdate,double newExpenditureAmount, String newCategoty, String newDescription, Date newDate) throws ExpenseManagmentException;
	
	/**
	 * delete a cost item from costItems DB 
	 * 
	 * @param itemToDelelte the cost item to delete
	 * @throws ExpenseManagmentException if failed to delete the cost item in CostItem DB
	 */
	public void deleteItem(CostItem itemToDelelte) throws ExpenseManagmentException;
	
	/**
	 * Search  cost item in the costItems DB 
	 * 
	 * @param costItemID gets the cost item id to search by the cost item
	 * @return CostItem the cost item which contains the specific costItemID 
	 * @throws ExpenseManagmentException if searching failed
	 */
	public CostItem getItem(int costItemID) throws ExpenseManagmentException;
	
	/**
	 *  Return list of all cost items in costItems DB
	 *  
	 * @param user the user cost items we search for
	 * @return List<CostItem> list of all cost items in DB
	 * @throws ExpenseManagmentException if failed to get all cost items in costItems DB
	 */
	public List<CostItem> getAllUserCostItems(User user) throws ExpenseManagmentException;
	
	/**
	 * Delete user form users DB
	 * 
	 * @param UserToDelete user to delete
	 * @throws ExpenseManagmentException if delete user failed 
	 */
	public void deleteUser(User UserToDelete) throws ExpenseManagmentException;
	
	/**
	 * add user to users DB
	 * 
	 * @param userToAdd user to add
	 * @throws ExpenseManagmentException if adding user failed 
	 */
	public void addUser(User userToAdd) throws ExpenseManagmentException;
	
	/**
	 * Change user password at users DB
	 * 
	 * @param userToUpdate user to update 
	 * @param passwordToChange new password to change to
	 * @throws ExpenseManagmentException if update failed
	 */
	public void changeUserPassword(User userToUpdate, String passwordToChange) throws ExpenseManagmentException;
	
	/**
	 * Change user email at users DB
	 * 
	 * @param userToUpdate user to update 
	 * @param emailToChange new email to change to
	 * @throws ExpenseManagmentException if update failed
	 */
	public void changeUserEmail(User userToUpdate, String emailToChange) throws ExpenseManagmentException;
	
	/**
	 * return list of all users in users DB
	 * 
	 * @return List<User> list of all users in users DB
	 * @throws ExpenseManagmentException if getting the list failed
	 */
	public List<User> getAllUsers() throws ExpenseManagmentException;
	
	/**
	 * search the user by unique name in users DB
	 * 
	 * @param userName search user in user DB by user name
	 * @return User the user contains that unique name
	 * @throws ExpenseManagmentException if searching failed
	 */
	public User getUser(String userName) throws ExpenseManagmentException;
	
	/**
	 * Return true if user is exist, false otherwise
	 * 
	 * @param userName the user name 
	 * @param password the password of the user
	 * @return true if user is exist, false otherwise
	 * @throws ExpenseManagmentException if failed to search
	 */
	public boolean isUserExist(String userName,String password) throws ExpenseManagmentException;
	
	/**
	 * return list of cost item  for the specific user from costItems DB.
	 * 
	 * @param user the user cost items we search for
	 * @param currentMonth to search
	 * @param currentYear to search
	 * @return List<CostItem> the user's list of cost items by month and year
	 * @throws ExpenseManagmentException if getting user's cost items from DB failed
	 */
	public List<CostItem> getUserItemsPerYearMonth(User user, int currentMonth, int currentYear) throws ExpenseManagmentException;

	/**
	 * return list of cost item  for the specific user from costItems DB.
	 * 
	 * @param user the user cost items we search for
	 * @param currentYear to search
	 * @return List<CostItem> the user's list of cost items by year
	 * @throws ExpenseManagmentException if getting user's cost items from DB failed
	 */
	public List<CostItem> getAllUserItems(User user, int currentYear) throws ExpenseManagmentException;

	/**
	 * return a list of budget per month for specific user
	 * 
	 * @param userName the user name
	 * @return List of BudgetPerMonth for the specific user
	 * @throws ExpenseManagmentException if getting user's budgetPerMonths from DB field
	 */
	public List<BudgetPerMonth> getAllUserBudgets(String userName) throws ExpenseManagmentException;

	
}
