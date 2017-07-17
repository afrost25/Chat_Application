package trash;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Deprecated
public class Test 
{
	private StringProperty var;
	
	public Test()
	{
		var = new SimpleStringProperty(this, "var", "");
	}
	
	public void setVar(String var)
	{
		this.var.set(var);
	}
	
	public String getVar()
	{
		return var.get();
	}
	
	public StringProperty VarProperty()
	{
		return var;
	}
}
