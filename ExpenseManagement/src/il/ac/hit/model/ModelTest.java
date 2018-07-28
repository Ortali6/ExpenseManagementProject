package il.ac.hit.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * test for the model classes
 * @author ortal
 *
 */
public class ModelTest {
	/**
	 * main method. project entry point 
	 * @param args
	 * @throws ParseException if date parse failed
	 */
	public static void main(String[] args) throws ParseException {
		
		IExpenseManagerDAO expences = HibernateExpenseManagmentDAO.getInstance();
		try {
		User user1 = new User("Ortal","aaa", "a@a.a");
		User user2 = new User("Nofar","123", "b@b.b");
		User user3 = new User("Dani","bbb", "c@c.c");
		User user4 = new User("Roni", "nmnm", "d@d.d");
		
		CostItem costItem1 = new CostItem("Roni", "shopping", 750.00, "shopping clothing at the mall", new SimpleDateFormat("yyyyMMdd").parse("20170827")); 
		CostItem costItem2 = new CostItem("Nofar", "bils", 250.00, "paying electricity bil", new SimpleDateFormat("yyyyMMdd").parse("20170828")); 
		CostItem costItem3 = new CostItem("Ortal", "online courses", 300.00, "design pattern online course", new SimpleDateFormat("yyyyMMdd").parse("20160902")); 
		
		BudgetPerMonth budget1 = new BudgetPerMonth("Dani", 10, 2017);
		BudgetPerMonth budget2 = new BudgetPerMonth("Nofar", 9, 2017);
		BudgetPerMonth budget3 = new BudgetPerMonth("Ortal", 7, 2017);
		
			expences.addUser(user1);
			expences.addUser(user2);
			expences.addUser(user3);
			expences.addUser(user4);
			
			expences.addItem(costItem1);
			expences.addItem(costItem2);
			expences.addItem(costItem3);
			
			expences.updateItem(costItem3, 53.42, "online courses", "front-end online course", new SimpleDateFormat("yyyyMMdd").parse("20160902"));
			
			expences.getAllUsers();
			List<CostItem> allitemsofuser1 = expences.getAllUserCostItems(user1);
			System.out.println(allitemsofuser1);
			
			expences.addBudgetIfNotExists("Dani", 10, 2017);
			expences.addBudgetIfNotExists("Nofar", 9, 2017);
			expences.addBudgetIfNotExists("Ortal", 7, 2017);
			budget1.setBudget(40);
			budget2.setBudget(100);
			budget3.setBudget(100);
			
			expences.deleteUser(user3);
			
			expences.getAllUsers();
			List<CostItem> allitemsofuser2 = expences.getAllUserCostItems(user2);
			System.out.println(allitemsofuser2);
			
			expences.deleteItem(costItem2);
			
			List<CostItem> itemsOfUser4PerYearMonth =  expences.getUserItemsPerYearMonth(user4,8,2017);
			System.out.println(itemsOfUser4PerYearMonth);
		}
		catch(ExpenseManagmentException e) {
			e.printStackTrace();
		}
	}
}
