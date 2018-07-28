package il.ac.hit.model;

import java.io.Serializable;
import java.util.Date;

/**
 * represent an cost item in the ExpenseManagement application
 * @author ortal
 */
public class CostItem implements Serializable{

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the cost item id 
	 */
	private int costItemID;

	/**
	 * the expenditure amount of the cost item 
	 */
	private double expenditureAmount;
	
	/**
	 * the cost item description
	 */
	private String description;
	
	/**
	 * the cost item category
	 */
	private String category;
	
	/**
	 * the cost item creation date
	 */
	private Date date;
	
	/**
	 * set the userName of cost item
	 */
	private String userName;
	
	/**
	 * Construct an empty cost item
	 */
	public CostItem(){
		super();
	}
	
	/**
	 * Construct a cost item by the given params
	 * 
	 * @param costItemID
	 * @param expenditureAmount
	 * @param description
	 * @param category
	 * @param date
	 * @param userName
	 * @throws ExpenseManagmentException if one or more params is null
	 */
	public CostItem(String userName, String category, double expenditureAmount, String description, Date date) throws ExpenseManagmentException
	{
		this.setCostItemID(costItemID);
		this.setExpenditureAmount(expenditureAmount);
		this.setDescription(description);
		this.setCategory(category);
		this.setDate(date);
		this.setUserName(userName);
	}
	
	/**
	 * return the cost item id
	 * @return costItemID
	 */
	public int getCostItemID() 
	{
		return costItemID;
	}
	
	/**
	 * set the cost item id
	 * @param costItemID
	 */
	public void setCostItemID(int costItemID) 
	{
		this.costItemID = costItemID;
	}
	
	/**
	 * return the cost item Expenditure amount
	 * @return expenditureAmount
	 */
	public double getExpenditureAmount() 
	{
		return expenditureAmount;
	}
	
	/**
	 * set the cost item Expenditure amount
	 * @param expenditureAmount the amount spent
	 * @throws ExpenseManagmentException if Expenditure amount is not positive number
	 */
	public void setExpenditureAmount(double expenditureAmount) throws ExpenseManagmentException
	{
		if(expenditureAmount > 0) 
		{
			this.expenditureAmount = expenditureAmount;
		}
		else throw new ExpenseManagmentException("please enter positive value to expenditureAmount. (CostItem)");
	}
	
	/**
	 * return the cost item description
	 * @return description
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * set the cost item description
	 * @param description
	 * @throws ExpenseManagmentException if description is null
	 */
	public void setDescription(String description) throws ExpenseManagmentException
	{
		if(description != null)
		{
			this.description = description;
		}
		else throw new ExpenseManagmentException("please enter value to description. (CostItem)");
	}

	/**
	 * return the category of cost item
	 * @return category
	 */
	public String getCategory() 
	{
		return category;
	}

	/**
	 * set the category of cost item
	 * @param category
	 * @throws ExpenseManagmentException if category is null
	 */
	public void setCategory(String category) throws ExpenseManagmentException
	{
		if(category != null)
		{
			this.category = category.toLowerCase();
		}
		else throw new ExpenseManagmentException("please enter value to category. (CostItem)");
	}

	/**
	 * return the cost item creation date
	 * @return date
	 */
	public Date getDate() 
	{
		return date;
	}

	/**
	 * set the cost item creation date
	 * @param date
	 * @throws ExpenseManagmentException if date is null
	 */
	public void setDate(Date date) throws ExpenseManagmentException
	{
		if(date != null)
		{
		this.date = date;
		}
		else throw new ExpenseManagmentException("please enter a valid date. (CostItem)");
	}
	
	/**
	 * return the userName of the cost item
	 * @return userName
	 */
	public String getUserName() 
	{
		return userName;
	}

	/**
	 * set the userName of the cost item
	 * @param userName
	 * @throws ExpenseManagmentException if userName is null
	 */
	public void setUserName(String userName) throws ExpenseManagmentException
	{
		if(userName != null)
		{
			this.userName = userName.toLowerCase();
		}
		else throw new ExpenseManagmentException("please enter user name (CostItem)");
	}
	
	/**
	 * toString of costItem: [costItemID, category, expenditureAmount, description, date]
	 */
	@Override
	public String toString()
	{
		return "[" + costItemID + ", " + category + ", " + expenditureAmount + ", " + description + ", " + date + "]";
	}

}
