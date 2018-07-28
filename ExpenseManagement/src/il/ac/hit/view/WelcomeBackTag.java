package il.ac.hit.view;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class WelcomeBackTag extends SimpleTagSupport{
	
	String userName;
	
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		out.print("<h2 style='color:purple'> Welcome Back "+ userName + "</h2>");
	}
}
