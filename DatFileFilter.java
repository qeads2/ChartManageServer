import java.io.*;


public class DatFileFilter implements FilenameFilter {
	private String extend;
	
	public DatFileFilter(String extend)
	{
		this.extend = extend;
	}
	
	@Override
	public boolean accept(File dir, String name)
	{
		return name.endsWith(extend);
	}

}
