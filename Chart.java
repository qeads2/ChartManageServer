import java.io.*;


public class Chart implements Serializable {
	int number;
	String name;
	int age;
	String area;
	String problem;
	String etc;
	
	public Chart(int number, String name, int age, String area, String problem, String etc)
	{
		this.number = number;
		this.name = name;
		this.age = age;
		this.area = area;
		this.problem = problem;
		this.etc = etc;
	}
	
	public int getNumber()
	{
		return number;
	}
	public String getName()
	{
		return name;
	}
	public int getAge()
	{
		return age;
	}
	public String getArea()
	{
		return area;
	}
	public String getProblem()
	{
		return problem;
	}
	public String getEtc()
	{
		return etc;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public void setArea(String area)
	{
		this.area = area;
	}
	public void setProblem(String problem)
	{
		this.problem = problem;
	}
	public void setEtc(String etc)
	{
		this.etc = etc;
	}
	public String toString()
	{
		return getNumber()+ ","+getName() +","+ getAge() +","+getArea()+","+getProblem()+","+getEtc();
	}
}
