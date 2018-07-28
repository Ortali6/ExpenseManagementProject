package il.ac.hit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * HibernateExpenseManagmentDAO implements IExpenseManagerDAO interface, implement users, items and budgetPerMonths methods
 * @author ortal
 *
 */
@SuppressWarnings("deprecation")
public class HibernateExpenseManagmentDAO implements IExpenseManagerDAO 
{
	/**
	 * singleton HibernateExpenseManagmentDAO.
	 * the instance of HibernateExpenseManagmentDAO
	 */
	private static HibernateExpenseManagmentDAO instance;

	/**
	 * return the HibernateExpenseManagmentDAO instance
	 * @return instance - the HibernateExpenseManagmentDAO instance
	 */
	public static HibernateExpenseManagmentDAO getInstance() 
	{
		if(instance==null) 
		{
			instance = new HibernateExpenseManagmentDAO();
		}
		return instance;
	}
	
	/**
	 * instance of the session factory hibernate
	 */
	private SessionFactory factory;
	
	/**
	 * private c'tor
	 * Initialize session factory
	 */
	private HibernateExpenseManagmentDAO() 
	{
		factory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * adding a cost item to costItems DB  
	 * 
	 * @param itemToAdd the cost item to add
	 * @throws ExpenseManagmentException if failed to add the cost item to CostItem DB
	 */
	@Override
	public void addItem(CostItem itemToAdd) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		try 
		{
			session.beginTransaction();
			CostItem costitem = (CostItem)session.get(CostItem.class, itemToAdd.getCostItemID());
			if(costitem == null)
			{
				session.save(itemToAdd);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("add item error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
				session.close();
			}
		}	
	}

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
	@Override
	public void updateItem(CostItem itemToUpdate,double newExpenditureAmount, String newCategory, String newDescription, Date newDate)
			throws ExpenseManagmentException
	{
		Session session = factory.openSession();
		try 
		{
			session.beginTransaction();
			itemToUpdate.setExpenditureAmount(newExpenditureAmount);
			itemToUpdate.setDescription(newDescription);
			itemToUpdate.setDate(newDate);
			itemToUpdate.setCategory(newCategory);
			session.update(itemToUpdate);
			session.getTransaction().commit();
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("update item error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
			session.close();
			}
		}
		
	}

	/**
	 * delete a cost item from costItems DB 
	 * 
	 * @param itemToDelelte the cost item to delete
	 * @throws ExpenseManagmentException if failed to delete the cost item in CostItem DB
	 */
	@Override
	public void deleteItem(CostItem itemToDelelte) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		try 
		{
			session.beginTransaction();
			CostItem item = (CostItem) session.get(CostItem.class, itemToDelelte.getCostItemID());
			if(item != null)
			{
				session.delete(item);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("delete item error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
				session.close();
			}
		}	
	}

	/**
	 * Search  cost item in the costItems DB 
	 * 
	 * @param costItemID gets the cost item id to search by the cost item
	 * @return CostItem the cost item which contains the specific costItemID 
	 * @throws ExpenseManagmentException if searching failed
	 */
	@Override
	public CostItem getItem(int costItemID) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		CostItem costItem;
		try {
			session.beginTransaction();
			costItem = (CostItem) session.get(CostItem.class, costItemID);
			if(costItem != null)
				session.getTransaction().commit();
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("getting cost item error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
				session.close();
			}
		}
		return costItem;
	}

	/**
	 *  Return list of all cost items in costItems DB
	 * 
	 * @return List<CostItem> list of all cost items in DB
	 * @throws ExpenseManagmentException if failed to get all cost items in costItems DB
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CostItem> getAllUserCostItems(User user) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		List<CostItem> itemsList = new ArrayList<CostItem>();
		try 
		{
			session.beginTransaction();
			itemsList = session.createQuery("from CostItem where userName='" + user.getUserName() + "'").list();
			if(itemsList != null)
				session.getTransaction().commit();
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("getting cost items list error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
		return itemsList;
	}

	/**
	 * Delete user form users DB
	 * 
	 * @param UserToDelete user to delete
	 * @throws ExpenseManagmentException if delete user failed 
	 */
	@Override
	public void deleteUser(User UserToDelete) throws ExpenseManagmentException {
		Session session = factory.openSession();
		List<CostItem> itemsList = getAllUserCostItems(UserToDelete);
		List<BudgetPerMonth> budgetList = getAllUserBudgets(UserToDelete.getUserName());
		try {
			session.beginTransaction();
			User user = (User) session.get(User.class, UserToDelete.getUserName());
			if(user != null) 
			{
				for(int i=0; i < itemsList.size(); i++) 
				{
					session.delete(itemsList.get(i));
				}
				for(int i=0; i<budgetList.size(); i++)
				{
					session.delete(budgetList.get(i));
				}
				session.delete(user);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("delete user error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
	}

	/**
	 * add user to users DB
	 * 
	 * @param userToAdd user to add
	 * @throws ExpenseManagmentException if adding user failed 
	 */
	@Override
	public void addUser(User userToAdd) throws ExpenseManagmentException {
		Session session = factory.openSession();
		try 
		{
			session.beginTransaction();
			User user = (User)session.get(User.class, userToAdd.getUserName());
			if(user == null) 
			{
				session.save(userToAdd);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("add user error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
	}

	/**
	 * Change user password at users DB
	 * 
	 * @param userToUpdate user to update 
	 * @param passwordToChange new password to change to
	 * @throws ExpenseManagmentException if update failed
	 */
	@Override
	public void changeUserPassword(User userToUpdate, String passwordToChange) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			User user = (User) session.get(User.class, userToUpdate.getUserName());
			if(user != null) 
			{
				user.setPassword(passwordToChange);
				session.update(user);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("change user password error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
			session.close();
			}
		}
	}
	
	/**
	 * return list of all users in users DB
	 * 
	 * @return List<User> list of all users in users DB
	 * @throws ExpenseManagmentException if getting the list failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		List<User> usersList = new ArrayList<User>();
		try {
			session.beginTransaction();
			usersList = session.createQuery("from User").list();
			if(usersList != null)
				session.getTransaction().commit();
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get users error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
		return usersList;
	}

	/**
	 * search the user by unique name in users DB
	 * 
	 * @param userName search user in user DB by user name
	 * @return User the user contains that unique name
	 * @throws ExpenseManagmentException if searching failed
	 */
	@Override
	public User getUser(String userName) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		User user;
		try 
		{
			session.beginTransaction();
			user = (User) session.get(User.class, userName.toLowerCase());
			if(user != null)
			{
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get user error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
			session.close();
			}
		}
		return user;
	}

	/**
	 * Return true if user is exist, false otherwise(if not exist or the password is incorrect)
	 * 
	 * @param userName the user name 
	 * @param password the password of the user
	 * @return true if user is exist, false otherwise
	 * @throws ExpenseManagmentException if failed to search
	 */
	@Override
	public boolean isUserExist(String userName, String password) throws ExpenseManagmentException 
	{
		boolean userExist = false;
		User user = getUser(userName);
		if(user!=null)
		{
			if(user.getPassword().equals(password))
			{
				userExist = true;
			}
		}
		return userExist;
	}

	/**
	 * return list of cost item  for the specific user from costItems DB.
	 * 
	 * @param user the user cost items we search for
	 * @param currentMonth to search
	 * @param currentYear to search
	 * @return List<CostItem> the user's list of cost items by month and year
	 * @throws ExpenseManagmentException if getting user's cost items from DB failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CostItem> getUserItemsPerYearMonth(User user, int currentMonth, int currentYear) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		List<CostItem> itemsList = null;
		try 
		{
			session.beginTransaction();
			User userChecker = getUser(user.getUserName());
			if(userChecker != null)
			{
				itemsList = session.createQuery("from CostItem where userName='" + user.getUserName() + "' and month(date)='" + currentMonth + "' and year(date)='" + currentYear + "'").list();
				if(itemsList != null)
					session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get user cost items error. please try again.", e);
		}
		finally 
		{
			if(session!= null) {
			session.close();
			}
		}
		return itemsList;
	}

	/**
	 * change the budget per month
	 *  
	 * @param userName - the user name
	 * @param newBudget the new budget
	 * @param month
	 * @year year 
	 * @throws ExpenseManagmentException if failed to change the budget
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void changeUserBudgetPerMonth(String userName, double newBudget, int month, int year) throws ExpenseManagmentException
	{	
		Session session = factory.openSession();
		BudgetPerMonth budgetPM = null;
		try 
		{
			session.beginTransaction();
			User userChecker = getUser(userName);
			if(userChecker != null) 
			{
				String hql = "FROM BudgetPerMonth" + " di WHERE di.userName=:userName and month=:month and year=:year";
				Query query = session.createQuery(hql);
				query.setParameter("userName", userName);
				query.setParameter("month", month);
				query.setParameter("year", year);
				List budgetList = query.list();
				
				if(budgetList != null && budgetList.size() > 0)
				{
					budgetPM = (BudgetPerMonth) budgetList.get(0);
					budgetPM.setBudget(newBudget);
					session.update(budgetPM);
					session.getTransaction().commit();
				}
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("change user budget per month error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
	}
	
	
	/**
	 * add budget to budgetPerMonth DB if not exists already
	 * 
	 * @param userName
	 * @param month
	 * @param year 
	 * @throws ExpenseManagmentException if failed to add the budget to budgetPerMonth DB
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addBudgetIfNotExists(String userName, int month, int year) throws ExpenseManagmentException
	{
		Session session = factory.openSession();
		BudgetPerMonth budgetPerMonth = null;
		try 
		{
			session.beginTransaction();
			User userChecker = getUser(userName);
			if(userChecker != null) 
			{
				String hql = "FROM BudgetPerMonth" + " di WHERE di.userName=:userName and month=:month and year=:year";
				Query query = session.createQuery(hql);
				query.setParameter("userName", userName);
				query.setParameter("month", month);
				query.setParameter("year", year);
				List budgetList = query.list();
				
				if(budgetList == null || budgetList.size() == 0)
				{
					budgetPerMonth = new BudgetPerMonth(userName, month, year);
					session.save(budgetPerMonth);
					session.getTransaction().commit();
				}
			
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("add item error. please try again.", e);
		}
		finally {
			if(session!= null) {
				session.close();
			}
		}	
	}
	
	/**
	 * return the budget per month for the current user 
	 * 
	 * @param userName
	 * @param month
	 * @param year 
	 * @return the budget per month 
	 * @throws ExpenseManagmentException if failed to get the budget
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getBudget(String userName, int month, int year) throws ExpenseManagmentException
	{
		Session session = factory.openSession();
		List<BudgetPerMonth> budgetList = null;
		double budgetPM = 0;
		try 
		{
			session.beginTransaction();
			User userChecker = getUser(userName);
			if(userChecker != null) 
			{
				String hql = "FROM BudgetPerMonth" + " di WHERE di.userName=:userName and month=:month and year=:year";
				Query query = session.createQuery(hql);
				query.setParameter("userName", userName);
				query.setParameter("month", month);
				query.setParameter("year", year);
				budgetList = query.list();
				
				if(budgetList != null && budgetList.size() > 0)
				{
					budgetPM = (double)budgetList.get(0).getBudget();
					session.getTransaction().commit();
				}
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get user budget per month error. please try again.", e);
		}
		finally 
		{
			if(session!= null) 
			{
				session.close();
			}
		}
		return budgetPM;
		
	}

	/**
	 * Change user email at users DB
	 * 
	 * @param userToUpdate user to update 
	 * @param emailToChange new email to change to
	 * @throws ExpenseManagmentException if update failed
	 */
	@Override
	public void changeUserEmail(User userToUpdate, String emailToChange) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			User user = (User) session.get(User.class, userToUpdate.getUserName());
			if(user != null) {
				user.setEmail(emailToChange);
				session.update(user);
				session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("change user email error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
		
	}

	/**
	 * return a list of budget per month for specific user
	 * 
	 * @param userName the user name
	 * @return List of BudgetPerMonth for the specific user
	 * @throws ExpenseManagmentException if getting user's budgetPerMonths from DB field
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetPerMonth> getAllUserBudgets(String userName) throws ExpenseManagmentException
	{
		Session session = factory.openSession();
		List<BudgetPerMonth> budgetPerMonthList = null;
		try 
		{
			session.beginTransaction();
			User userChecker = getUser(userName);
			if(userChecker != null)
			{
				budgetPerMonthList = session.createQuery("from BudgetPerMonth where userName='" + userChecker.getUserName() + "'").list();
				if(budgetPerMonthList != null)
					session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get user budgets error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
		return budgetPerMonthList;
	}
	
	/**
	 * return list of cost item  for the specific user from costItems DB.
	 * 
	 * @param user the user cost items we search for
	 * @param currentYear to search
	 * @return List<CostItem> the user's list of cost items by year
	 * @throws ExpenseManagmentException if getting user's cost items from DB failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CostItem> getAllUserItems(User user, int currentYear) throws ExpenseManagmentException 
	{
		Session session = factory.openSession();
		List<CostItem> itemsList = null;
		try {
			session.beginTransaction();
			User userChecker = getUser(user.getUserName());
			if(userChecker != null){
				itemsList = session.createQuery("from CostItem where userName='" + userChecker.getUserName() + "' and year(date)='" + currentYear + "'").list();
				if(itemsList != null)
					session.getTransaction().commit();
			}
		}
		catch(HibernateException e) 
		{
			if(session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new ExpenseManagmentException("get user cost items error. please try again.", e);
		}
		finally {
			if(session!= null) {
			session.close();
			}
		}
		return itemsList;
	}
}
