package il.ac.hit.controller;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import il.ac.hit.model.CostItem;
import il.ac.hit.model.ExpenseManagmentException;
import il.ac.hit.model.HibernateExpenseManagmentDAO;
import il.ac.hit.model.IExpenseManagerDAO;
import il.ac.hit.model.User;
import com.google.common.primitives.*;

/**
 * Servlet implementation class ExpenseManagementController
 * 
 * @author ortal
 */
@WebServlet("/ExpenseManagementController")
public class ExpenseManagementController extends HttpServlet 
{
	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * singleton HibernateExpenseManagmentDAO.
	 * the instance of HibernateExpenseManagmentDAO
	 */
	private IExpenseManagerDAO ExpenseManagement = HibernateExpenseManagmentDAO.getInstance();
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseManagementController() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Date currentDate = new Date();
		LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int currentMonth = localDate.getMonthValue();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String currentMonthInWords = new DateFormatSymbols().getMonths()[currentMonth-1];
		String command = request.getParameter("command");
		String userName, password, strItemId, itemCategory, itemDescription, itemAmount, date, email;
		Date itemDate;
		double itemExpenditureAmount, currentBudget, currenteExpenses, itemPrice;
		User currentUser = null;
		CostItem currentItemToDelete, currentItemToEdit;
		List<CostItem> userCostItemsPerMonth = null;
		List<CostItem> userAllCostItemsPerYear = null;	
		HttpSession session = request.getSession();
		
		switch(command) 
		{
		
		case "loginwindow":
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			break;
			
			
		case "registerwindow":
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			break;
		
		case "login":
		{
			//get the user name and password to check if exists in DB
			userName = request.getParameter("username");
			password = request.getParameter("password");
			if(userName.isEmpty() || password.isEmpty())
			{
				request.setAttribute("requestmessage", "Please enter values in all fields");
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
				break;
			}
			else
			{
				try
				{
					if(ExpenseManagement.isUserExist(userName, password))
					{
						//if user exists get his cost items and budget for current month
						currentUser = ExpenseManagement.getUser(userName);
						userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
						ExpenseManagement.addBudgetIfNotExists(userName, currentMonth, currentYear);
						currentBudget = ExpenseManagement.getBudget(userName, currentMonth, currentYear);
						
						//calculate the total sum of user cost items for the specific month
						currenteExpenses = calculateExpensene(userCostItemsPerMonth);
						
						//set the attributes for display at the jsp file
						session.setAttribute("currenteexpenses",currenteExpenses);
						session.setAttribute("currentbudget",currentBudget);
						session.setAttribute("userItems",userCostItemsPerMonth);
						session.setAttribute("loginUser", currentUser);
						session.setAttribute("currentmonthinwords",currentMonthInWords);
						session.setAttribute("currentmonth",currentMonth);
						session.setAttribute("currentyear", currentYear);
						//cookie for user
						Cookie cookie = new Cookie("userName", currentUser.getUserName());
						cookie.setMaxAge(1000000);
						response.addCookie(cookie);
						request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
						break;
					}
					else
					{
						request.setAttribute("requestmessage", "Wrong details, please try again");
						request.getRequestDispatcher("/Login.jsp").forward(request, response);
						break;
					}
				}
				catch(ExpenseManagmentException e)
				{
					e.printStackTrace();
					request.getRequestDispatcher("/Error.jsp").forward(request, response);
					return;
				}
				
			}
		}
			
		case "canclelogin":
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			break;
		
		case "okregister":
		{
			//get the user name, password, rePassword, email to create a new user 
			userName = request.getParameter("username");
			password = request.getParameter("password");
			String rePassword = request.getParameter("repassword");
			email = request.getParameter("email").toLowerCase();
			//validate checking
			if(userName.isEmpty() || password.isEmpty() || rePassword.isEmpty())
			{
				request.setAttribute("requestmessage", "Please enter values in all fields");
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				break;
			}
			else if(!password.equals(rePassword))
			{
				request.setAttribute("requestmessage", "Please make sure to enter maches passwords");
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				break;
			}
			else
			{
				//if all parameters are validate
				try 
				{
					//check to see if user already exists
					if(ExpenseManagement.isUserExist(userName, password))
					{
						request.setAttribute("requestmessage", "user alredy exists, please try to Login");
						request.getRequestDispatcher("/Register.jsp").forward(request, response);
						break;
					}
					else
					{
						//create a new user and add him to DB
						currentUser = new User(userName, password, email);
						ExpenseManagement.addUser(currentUser);
						session.setAttribute("loginUser", currentUser);
						request.getRequestDispatcher("/Login.jsp").forward(request, response);
						break;
					}
						
				}
				catch(ExpenseManagmentException e)
				{
					e.printStackTrace();
					request.getRequestDispatcher("/Error.jsp").forward(request, response);
					return;
				}
			}
		}
		
			
		case "cancleregister":
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			break;
			
		case "logout":
			if(session!=null) 
			{
				session.invalidate();
				session = request.getSession(false);
			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			break;	
			
		case "okerrorpage":
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			break;
			
		case "changeyearmonth":
			//get the user, month, year
			currentUser = (User)session.getAttribute("loginUser");
			String monthAndYear = request.getParameter("monthtogo");
			String[] dateParts = monthAndYear.split("-");
			//validate checking
			if(dateParts.length != 2)
			{
				request.setAttribute("requestmessage", "not a valid date");
			}
			else
			{
				String year = dateParts[0];
				String month = dateParts[1];
				int currYear = Calendar.getInstance().get(Calendar.YEAR);
				int monthInt = Integer.parseInt(month);
				int yearInt = Integer.parseInt(year);
				if(monthInt > 12 || monthInt < 1 || yearInt < currYear-10 || yearInt > currYear +10)
				{
					request.setAttribute("requestmessage", "not valid date. month between 1 to 12. year between " + (currYear-10) + " to " + (currYear+10));
					request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
					break;
				}
				else
				{
					//if parameters are validate get the user items and budget for the specific month
					currentMonth = monthInt;
					currentYear = yearInt;
					try 
					{
						userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
						ExpenseManagement.addBudgetIfNotExists(currentUser.getUserName(), currentMonth, currentYear);
						currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);
					} 
					catch (ExpenseManagmentException e) 
					{
						e.printStackTrace();
						request.getRequestDispatcher("/Error.jsp").forward(request, response);
						return;
					}
					//set the parameters for display at the jsp file
					currentMonthInWords =  new DateFormatSymbols().getMonths()[currentMonth-1];
					session.setAttribute("currentmonthinwords",currentMonthInWords);
					session.setAttribute("currentmonth",currentMonth);
					session.setAttribute("currentyear", currentYear);
					currenteExpenses = calculateExpensene(userCostItemsPerMonth);
					session.setAttribute("currentbudget", currentBudget);
					session.setAttribute("currenteexpenses",currenteExpenses);
					session.setAttribute("userItems",userCostItemsPerMonth);
					request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
					break;
				}
				
			}
			
		case "backmonth":
		{
			//get the user, month, year
			currentMonth = (int) session.getAttribute("currentmonth");
			currentYear = (int) session.getAttribute("currentyear");
			currentUser = (User)session.getAttribute("loginUser");
			//set the current month and year (backwards)
			if(currentMonth == 1)
			{
				currentMonth = 12;
				currentYear--;
			}
			else
			{
				currentMonth--;
			}
			//set the parameters for display at the jsp file
			currentMonthInWords =  new DateFormatSymbols().getMonths()[currentMonth-1];
			session.setAttribute("currentmonthinwords",currentMonthInWords);
			session.setAttribute("currentmonth",currentMonth);
			session.setAttribute("currentyear", currentYear);
			try 
			{
				//get the user items for the specific month
				userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
				ExpenseManagement.addBudgetIfNotExists(currentUser.getUserName(), currentMonth, currentYear);
				currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);
			} 
			catch (ExpenseManagmentException e) 
			{
				e.printStackTrace();
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
				return;
			}
			//get total expenses for the specific month 
			currenteExpenses = calculateExpensene(userCostItemsPerMonth);
			//set the parameters for display it at the jsp file
			session.setAttribute("currentbudget",currentBudget);
			session.setAttribute("currenteexpenses",currenteExpenses);
			session.setAttribute("userItems",userCostItemsPerMonth);
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
		}
			
			
		case "nextmonth":
		{
			//get the user, month, year
			currentMonth = (int) session.getAttribute("currentmonth");
			currentYear = (int) session.getAttribute("currentyear");
			currentUser = (User)session.getAttribute("loginUser");
			//set the current month and year (forwards)
			if(currentMonth == 12)
			{
				currentMonth = 1;
				currentYear++;
			}
			else
			{
				currentMonth++;
			}
			//set the parameters for display at the jsp file
			currentMonthInWords =  new DateFormatSymbols().getMonths()[currentMonth-1];
			session.setAttribute("currentmonthinwords",currentMonthInWords);
			session.setAttribute("currentmonth",currentMonth);
			session.setAttribute("currentyear", currentYear);
			try 
			{
				//get the user items and budget and total expenses for the specific month
				userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
				ExpenseManagement.addBudgetIfNotExists(currentUser.getUserName(), currentMonth, currentYear);
				currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);
			} 
			catch (ExpenseManagmentException e) 
			{
				e.printStackTrace();
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
				return;
			}
			//get total expenses for the specific month 
			currenteExpenses = calculateExpensene(userCostItemsPerMonth);
			//set the parameters for display it at the jsp file
			session.setAttribute("currentbudget",currentBudget);
			session.setAttribute("currenteexpenses",currenteExpenses);
			session.setAttribute("userItems",userCostItemsPerMonth);
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
		}
			
		case "changepassword":
			request.getRequestDispatcher("/ChangePassword.jsp").forward(request, response);
			return;
		
		case "okchangepassword":
		{
			//get the user, password, newPassword, reNewPassword
			currentUser = (User)session.getAttribute("loginUser");
			password = request.getParameter("oldpassword");
			String newPassword = request.getParameter("newpassword");
			String reNewPassword = request.getParameter("renewpassword");
			//validate checking
			if(password.isEmpty() || newPassword.isEmpty() || reNewPassword.isEmpty())
			{
				request.setAttribute("requestmessage", "Plaese enter values in all fields");
				request.getRequestDispatcher("/ChangePassword.jsp").forward(request, response);
				break;	
			}
			else
			{
				if(!password.intern().equals(currentUser.getPassword().intern()))
				{
					//checking if password matches the old password at DB
					request.setAttribute("requestmessage", "Old password is incorrect. please try again");
					request.getRequestDispatcher("/ChangePassword.jsp").forward(request, response);
					break;	
				}
				else
				{
					if(!newPassword.intern().equals(reNewPassword.toString().intern()))
					{
						//checking if new password matches the reNewPassword 
						request.setAttribute("requestmessage", "New password and re-new password is not equal. please try again");
						request.getRequestDispatcher("/ChangePassword.jsp").forward(request, response);
						break;	
					}
					else
					{
						try 
						{
							//if all parameters are validate  change the user password
							ExpenseManagement.changeUserPassword(currentUser, newPassword);
							currentUser = ExpenseManagement.getUser(currentUser.getUserName());
							session.setAttribute("loginUser", currentUser);
							request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
							break;
						} 
						catch (ExpenseManagmentException e) 
						{
							e.printStackTrace();
							request.getRequestDispatcher("/Error.jsp").forward(request, response);
							return;
						}	
					}
				}
			}
		}
			
		case "canclecnagepassword":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
			
		case "changeemail":
			request.getRequestDispatcher("/ChangeEmail.jsp").forward(request, response);
			return;
		
		case "okchangeemail":
		{
			//get the user, email, newEmail, reNewEmail
			currentUser = (User)session.getAttribute("loginUser");
			email = (String) request.getParameter("oldemail").toLowerCase();
			String newEmail = (String) request.getParameter("newemail").toLowerCase();
			String reNewEmail = (String) request.getParameter("renewemail").toLowerCase();
			//validate checking
			if(email.isEmpty() || newEmail.isEmpty() || reNewEmail.isEmpty())
			{
				request.setAttribute("requestmessage", "Plaese enter values in all fields");
				request.getRequestDispatcher("/ChangeEmail.jsp").forward(request, response);
				break;	
			}
			else
			{
				if(!email.intern().equals(currentUser.getEmail().intern()))
				{
					//checking if email matches the old email at DB
					request.setAttribute("requestmessage", "Old password is incorrect. please try again");
					request.getRequestDispatcher("/ChangeEmail.jsp").forward(request, response);
					break;	
				}
				else
				{
					if(!newEmail.intern().equals(reNewEmail.toString().intern()))
					{
						//checking if new email matches the reNewEmail 
						request.setAttribute("requestmessage", "New email and re-new email is not equal. please try again");
						request.getRequestDispatcher("/ChangeEmail.jsp").forward(request, response);
						break;	
					}
					else
					{
						try 
						{
							//if all parameters are validate  change the user email
							ExpenseManagement.changeUserEmail(currentUser, newEmail);
							currentUser = ExpenseManagement.getUser(currentUser.getUserName());
							session.setAttribute("loginUser", currentUser);
							request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
							break;
						} 
						catch (ExpenseManagmentException e) 
						{
							e.printStackTrace();
							request.getRequestDispatcher("/Error.jsp").forward(request, response);
							return;
						}
						
					}
				}
			}
		}
		
		case "canclechangeemail":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
			
		case "changebudget":
			request.getRequestDispatcher("/ChangeBudget.jsp").forward(request, response);
			return;
		
		case "okchangebudget":
		{
			//get the user, month, year
			currentMonth = (int) session.getAttribute("currentmonth");
			currentYear = (int)session.getAttribute("currentyear");
			currentUser = (User)session.getAttribute("loginUser");
			//validate checking
			if(Doubles.tryParse(request.getParameter("newbudget")) != null)
			{
				double money = Double.parseDouble(request.getParameter("newbudget"));
				if(money >= 0)
				{
					try
					{	
						//change the user monthly budget
						ExpenseManagement.changeUserBudgetPerMonth(currentUser.getUserName(), money, currentMonth, currentYear);
						session.setAttribute("currentbudget", money);
						request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
						break;	
					}
					catch (ExpenseManagmentException e) 
					{
						e.printStackTrace();
						request.getRequestDispatcher("/Error.jsp").forward(request, response);
						return;
					}
				}
				else //if new budget is not a positive number
				{
					request.setAttribute("requestmessage", "Expenditue amount can start from 0 and above");
					request.getRequestDispatcher("/ChangeBudget.jsp").forward(request, response);
					break;
				}
		
			}
			else //validate checking error
			{
				request.setAttribute("requestmessage", "Plaese enter value in the field");
				request.getRequestDispatcher("/ChangeBudget.jsp").forward(request, response);
				break;
			}
		}
		
		
		
		case "canclechangebudget":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
			
		case "deleteitem":
		{
			//get the current user, costItemID
			currentUser = (User)session.getAttribute("loginUser");
			strItemId = request.getParameter("itemid");
			//validate checking
			if(strItemId.isEmpty() || strItemId == null)
			{
				request.setAttribute("requestmessage", "Cannot delete the item");
				request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
				break;				
			}
			else
			{
				try
				{
					//if parameters are validate get the cost item from DB and deleting it
					int itemId = Integer.parseInt(strItemId);
					currentItemToDelete = ExpenseManagement.getItem(itemId);
					ExpenseManagement.deleteItem(currentItemToDelete);
					//get the current month and current year, the user cost items list and budget for the specific month
					currentMonth = (int) session.getAttribute("currentmonth");
					currentYear = (int)session.getAttribute("currentyear");
					userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
					currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);
					//calculate the new total sum of all user cost items for specific month
					currenteExpenses = calculateExpensene(userCostItemsPerMonth); 	
					//set parameters to display at the jsp file
					session.setAttribute("currenteexpenses",currenteExpenses);
					session.setAttribute("userItems",userCostItemsPerMonth);
					request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
					break;
				}
				catch (ExpenseManagmentException e) 
				{
					e.printStackTrace();
					request.getRequestDispatcher("/Error.jsp").forward(request, response);
					return;
				}
			}
		}
			
		
		case "edititem":
		{
			//get the cost item id 
			strItemId = request.getParameter("itemid");
			//validate checking
			if(strItemId.isEmpty() || strItemId == null)
			{
				request.setAttribute("requestmessage", "Cannot edit the item");
				request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
				break;				
			}
			else
			{
				session.setAttribute("itemId", strItemId);
				try 
				{
					//get the item from DB (and the item parameters) for display at the jsp file
					currentItemToEdit = ExpenseManagement.getItem(Integer.parseInt(strItemId));
					itemCategory = currentItemToEdit.getCategory();
					itemDescription = currentItemToEdit.getDescription();
					itemDate = currentItemToEdit.getDate();
					itemPrice = currentItemToEdit.getExpenditureAmount();
					session.setAttribute("itemid", strItemId);
					session.setAttribute("itemcategory", itemCategory);
					session.setAttribute("itemdescription", itemDescription);
					session.setAttribute("itemprice", itemPrice);
					session.setAttribute("itemdate", itemDate);
					request.getRequestDispatcher("/EditItem.jsp").forward(request, response);
					return;
				} 
				catch (NumberFormatException | ExpenseManagmentException e) 
				{
					e.printStackTrace();
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					return;
				}
			}
		}
		
		case "okedititem":
		{
			//get the user, cost item id, current Month, current Year, itemCategory, itemDescription, itemAmount, date
			currentMonth = (int) session.getAttribute("currentmonth");
			currentYear = (int) session.getAttribute("currentyear");
			currentUser = (User)session.getAttribute("loginUser");
			strItemId = (String) session.getAttribute("itemid");
			itemCategory = request.getParameter("category");
			itemDescription = request.getParameter("description");
			itemAmount = request.getParameter("expenditureamount");
			date = request.getParameter("date");
			//validate cheking
			if(itemCategory.isEmpty() || itemDescription.isEmpty() || itemAmount.isEmpty() || date.isEmpty())
			{
				request.setAttribute("requestmessage", "Plaese enter values in all fields");
				request.getRequestDispatcher("/EditItem.jsp").forward(request, response);
				break;
			}
			else 
			{
				if(Doubles.tryParse(request.getParameter("expenditureamount")) != null)
				{
					double money = Double.parseDouble(request.getParameter("expenditureamount"));
					if(money >= 0)
					{
						//if the expenditure amount is positive number 
						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
						try
						{	
							//get the item from DB 
							currentItemToEdit = ExpenseManagement.getItem(Integer.parseInt(strItemId));
							itemDate = dateFormatter.parse(date);
							itemExpenditureAmount = Double.parseDouble(itemAmount);
							//update the item at cost items DB 
							ExpenseManagement.updateItem(currentItemToEdit, itemExpenditureAmount, itemCategory, itemDescription, itemDate);
							//get the user cost items and budget for the specific month
							userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
							currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);	
						}
						catch(ParseException e)
						{
							request.setAttribute("requestmessage", "Not a valid date format");
							request.getRequestDispatcher("/EditItem.jsp").forward(request, response);
							break;
						}
						catch (ExpenseManagmentException e) 
						{
							e.printStackTrace();
							request.getRequestDispatcher("/Error.jsp").forward(request, response);
							return;
						}
						//calculate the new total sum of expenses for the current month 
						currenteExpenses = calculateExpensene(userCostItemsPerMonth);
						//save parameters for display at the jsp file
						session.setAttribute("currenteexpenses",currenteExpenses);
						session.setAttribute("userItems",userCostItemsPerMonth);
						request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
						break;	
					}
					else
					{
						request.setAttribute("requestmessage", "Expenditue amount can start from 0 and above");
						request.getRequestDispatcher("/EditItem.jsp").forward(request, response);
						break;
					}
				}
				else
				{
					request.setAttribute("requestmessage", "Not a valid expenditue amount");
					request.getRequestDispatcher("/EditItem.jsp").forward(request, response);
					break;
				}		
			}
		}
			
		case "graphs":
			//get the current user, current year, and list of cost items for the specific month
			currentUser = (User)session.getAttribute("loginUser");
			currentYear = (int) session.getAttribute("currentyear");
			userCostItemsPerMonth = (List<CostItem>) session.getAttribute("userItems");
			try 
			{
				//get list of all user cost items for the specific year
				userAllCostItemsPerYear = ExpenseManagement.getAllUserItems(currentUser, currentYear);
			} 
			catch (ExpenseManagmentException e) 
			{
				e.printStackTrace();
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
				return;
			}
			//set the parameters for display at the jsp file
			session.setAttribute("piejsonarr", createJsonContentAndSetAttribute(userCostItemsPerMonth));
			session.setAttribute("combrojsonarr", createAllYearJson(userAllCostItemsPerYear));
			request.getRequestDispatcher("/Graphs.jsp").forward(request, response);
			break;
			
		case "pieChart":
			//set the parameters for display at the jsp file
			request.setAttribute("graphtype", "piechart");
			request.getRequestDispatcher("/Graphs.jsp").forward(request, response);
			break;
		
		case "comboChart":
			//set the parameters for display at the jsp file
			request.setAttribute("graphtype", "combochart");
			request.getRequestDispatcher("/Graphs.jsp").forward(request, response);
			break;
			
		case "combograph":	
			request.getRequestDispatcher("/ComboChartGraph.jsp").forward(request, response);
			break;
		
		case "piegraph":
			request.getRequestDispatcher("/PieChartGraph.jsp").forward(request, response);
			break;
		
		case "backfromgraphs":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;	
			
		case "cancleedititem":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;	
				
		case "additem":
			request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
			break;
			
		case "okadditem":
		{
			//get the user, currentMonth, currentYear, itemCategory, itemDescription, itemAmount, date
			currentMonth = (int) session.getAttribute("currentmonth");
			currentYear = (int) session.getAttribute("currentyear");
			currentUser = (User)session.getAttribute("loginUser");
			itemCategory = request.getParameter("category");
			itemDescription = request.getParameter("description");
			itemAmount = request.getParameter("expenditureamount");
			date = request.getParameter("date");
			//validate checking if values not empty
			if(itemCategory.isEmpty() || itemDescription.isEmpty() || itemAmount.isEmpty() || date.isEmpty())
			{
				request.setAttribute("requestmessage", "plaese enter values in all fields");
				request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
				break;
			}
			else 
			{
				if(Doubles.tryParse(request.getParameter("expenditureamount")) != null)
				{
					double money = Double.parseDouble(request.getParameter("expenditureamount"));
					if(money >= 0)
					{
						//if expenditure amount is a positive number
						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
						try
						{
							//get the date
							itemDate = dateFormatter.parse(date);
						    Calendar cal = Calendar.getInstance();
						    cal.setTime(itemDate);
						    int year = cal.get(Calendar.YEAR);
						    int month = cal.get(Calendar.MONTH)+1;
						    int day = cal.get(Calendar.DAY_OF_MONTH);
							int currYear = Calendar.getInstance().get(Calendar.YEAR);
							Calendar monthStart = new GregorianCalendar(currYear, month-1, day);
							int maxDayInMonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
							//validate checking
							if(month > 12 || month < 1 || year < currYear-10 || year > currYear +10 || day > maxDayInMonth || day < 1)
							{					
								request.setAttribute("requestmessage", "Not a valid date. month between 1 to 12. year between " + (currYear-10) + " to " + (currYear+10));
								request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
								break;
							}
							else
							{
								//if all parameters are validate add a new item to DB 
								itemExpenditureAmount = Double.parseDouble(itemAmount);
								CostItem itemToAdd = new CostItem(currentUser.getUserName(), itemCategory, itemExpenditureAmount, itemDescription, itemDate);
								ExpenseManagement.addItem(itemToAdd);
								//get all of the user cost items and budget for the specific month 
								userCostItemsPerMonth = ExpenseManagement.getUserItemsPerYearMonth(currentUser, currentMonth, currentYear);
								currentBudget = ExpenseManagement.getBudget(currentUser.getUserName(), currentMonth, currentYear);
								//calculate the new total sum of the user cost items for the specific month 
								currenteExpenses = calculateExpensene(userCostItemsPerMonth);
								//set parameters for display at the jsp file
								session.setAttribute("currenteexpenses",currenteExpenses);
								session.setAttribute("userItems",userCostItemsPerMonth);
								request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
								break;
							}
						}
						catch(ParseException e)
						{
							request.setAttribute("requestmessage", "Not a valid date");
							request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
							break;
						}
						catch (ExpenseManagmentException e) 
						{
							e.printStackTrace();
							request.getRequestDispatcher("/Error.jsp").forward(request, response);
							return;
						}
					}
					else //if expenditure amount is not a positive number
					{
						request.setAttribute("requestmessage", "Expenditue amount can start from 0 and above");
						request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
						break;
					}
				}
				else // if validate checking failed
				{
					request.setAttribute("requestmessage", "Not a valid expenditue amount");
					request.getRequestDispatcher("/AddNewItem.jsp").forward(request, response);
					break;
				}		
			}
		}
			
		case "cancleadditem":
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			break;
			
		}
	}
	
	/**
	 * create a Json content of all user cost items for specific month 
	 * 
	 * @param userCostItemsPerMonth all user cost items for the specific month
	 * @return JSONArray as String
	 */
	private String createJsonContentAndSetAttribute(List<CostItem> userCostItemsPerMonth) 
	{
		JSONObject j = new JSONObject();
		JSONArray myArray = new JSONArray();
		Map<String,Double> chartMap = new HashMap<String,Double>();
		chartMap = setChartMap(userCostItemsPerMonth);
		// copy each entry at the map to the json object
		for (Map.Entry<String, Double> entry : chartMap.entrySet())
		{
			try 
			{			
				j.put(entry.getKey(),entry.getValue());
			}
			catch (JSONException e1) 
			{
				e1.printStackTrace();
			}
		}
		//put the json object at json array
		myArray.put(j);	
		return myArray.toString();
		
		
	}
	
	/**
	 * create a Json content of all user cost items for specific year 
	 * 
	 * @param userCostItemsAllYear all user cost items for the specific year
	 * @return JSONArray as String
	 */
	private String createAllYearJson(List<CostItem> userCostItemsAllYear)
	{	
		JSONArray myArray = new JSONArray();
		Map<Integer, List<CostItem>> currMonthItems = setCostItemsPerMonth(userCostItemsAllYear);
		Map<String,Double> chartMapCurrMonth = new HashMap<String,Double>();
		Map<String,Double> chartMapCurrMonthAllCategories = new HashMap<String,Double>();
		List<String> allCategorysPerYear = getAllYearCategory(userCostItemsAllYear);

		for(int i = 0; i < 12; i++)
		{
			JSONObject j = new JSONObject();					
			if(!chartMapCurrMonth.isEmpty())
				chartMapCurrMonth.clear();
			
			if(currMonthItems.get(i) != null)
			{
				chartMapCurrMonth = setChartMap(currMonthItems.get(i));
			}
			for(int p = 0; p < allCategorysPerYear.size(); p++)
			{
				String category = allCategorysPerYear.get(p);
				if(!chartMapCurrMonth.containsKey(category))
				{
					chartMapCurrMonthAllCategories.put(category, 0.0);
				}
				else
				{
					chartMapCurrMonthAllCategories.put(category,chartMapCurrMonth.get(category));
				}

			}
			
			chartMapCurrMonth =  new TreeMap<>(chartMapCurrMonthAllCategories);

			for (Map.Entry<String, Double> entry : chartMapCurrMonth.entrySet())
			{
				try 
				{				
					j.put(entry.getKey(),entry.getValue());
				}
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			}
			
			myArray.put(j);
		}
		
		return myArray.toString();
	}
	
	/** 
	 * setCostItemsPerMonth build map ordered by months, each month has list if cost items <Integer, List<CostItem>>
	 * 
	 * @param userCostItemsAllYear list of all user cost items for the specific year
	 * @return listOringinizedPerMonth  map ordered by months, each month has list if cost items <Integer, List<CostItem>>
	 */
	@SuppressWarnings({ "deprecation" })
	private Map<Integer, List<CostItem>> setCostItemsPerMonth(List<CostItem> userCostItemsAllYear) 
	{
		Map<Integer, List<CostItem>> listOringinizedPerMonth = new HashMap<>();
		for(int i = 0; i < 12; i++)
		{
			//put month value in map as a key
			List<CostItem> perMonth = null;
			listOringinizedPerMonth.put(i, perMonth);
		}
		
		int currMonth = 0;
		for(CostItem currCostItem : userCostItemsAllYear)
		{
			//put list of user cost items of the specific month as a value 
			List<CostItem> costItemList = new ArrayList<>();
			currMonth = currCostItem.getDate().getMonth();
			if(listOringinizedPerMonth.get(currMonth) != null)
				costItemList = listOringinizedPerMonth.get(currMonth);
			costItemList.add(currCostItem);
			listOringinizedPerMonth.put(currMonth, costItemList);
		}
		return listOringinizedPerMonth;
	}
	
	/**
	 * getAllYearCategory - bulid a list of all user categories for the specific year 
	 * 
	 * @param userCostItemsAllYear list of all user cost items for the specific year
	 * @return allCategorysPerYear return a list of all user categories for the specific year 
	 */
	private List<String> getAllYearCategory(List<CostItem> userCostItemsAllYear)
	{
		List<String> allCategorysPerYear = new ArrayList<>();
		for(CostItem currCostItem : userCostItemsAllYear)
		{
			//add the category if it not already exists in the list 
			if(!allCategorysPerYear.contains(currCostItem.getCategory()))
				allCategorysPerYear.add(currCostItem.getCategory());
		}
		
		return allCategorysPerYear;
	}
	
	/**
	 * setChartMap - set the chart map <category, expendetureAmount> (each category has a matching sum of all expendetureAmount)
	 * 
	 * @param userCostItemsPerMonth list of all user cost items for the specific month
	 * @return chartMap - return map of <category, expendetureAmount> according to the userCostItemsPerMonth
	 */
	private Map<String,Double> setChartMap(List<CostItem> userCostItemsPerMonth)
	{
		Map<String,Double> chartMap = new HashMap<String,Double>();
		String category = null;
		double sumPrices = 0;
		double sumPriceses = 0;
		for(int i=0; i<userCostItemsPerMonth.size();i++)
		{
			//get the category, expenditure amount
			category = userCostItemsPerMonth.get(i).getCategory();
			sumPrices = userCostItemsPerMonth.get(i).getExpenditureAmount();
			//if the category already exists add the expenditure amount to the sum
			if(chartMap.containsKey(category))
			{
				sumPriceses = chartMap.get(category) + sumPrices;
				chartMap.put(category, sumPriceses);
			}
			//add the category and the expenditure amount to the map
			else
			{
				chartMap.put(category, sumPrices);
			}
			
		}
		return chartMap;
	}
	
	/**
	 * calculateExpensene - calculate the total sum of the user cost items expenditure amount for specific month
	 * 
	 * @param userCostItemsPerMonth list of all user cost items for specific month
	 * @return the sum of all user cost items expenditure amount for specific month
	 */
	private double calculateExpensene(List<CostItem> userCostItemsPerMonth) 
	{
		double expensene = 0;
		for(CostItem item: userCostItemsPerMonth)
		{
			expensene += item.getExpenditureAmount();
		}
		return expensene;
	}

}
