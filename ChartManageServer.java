
import java.io.*;
import java.net.*;
import java.util.*;


public class ChartManageServer {
	private HashMap<String, DataOutputStream> clients;
	private ServerSocket serverSocket;
	
	public static void main(String[] args)
	{
		new ChartManageServer().start();
	}
	
	public ChartManageServer()
	{
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
	}
	public void start()
	{
		try{
			Socket socket = null;
			serverSocket = new ServerSocket(6000);
			System.out.println("차트 통합 관리 서버가 기동중입니다.");
			
			while(true)
			{
			socket = serverSocket.accept();
			System.out.println("호스트와 연결 완료.");
			
			ChartReceiver fr = new ChartReceiver(socket); //클라이언트와의
			fr.start();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}


class ChartReceiver extends Thread
{
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	public ChartReceiver(Socket socket)
	{
		this.socket = socket;
		try
		{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} 
		catch (IOException e)
		{
			
		}
		
	}

	public void run()
	{
		String host = "";
		
		try
		{
			while(true){
			host = dis.readUTF();
			System.out.println("HOST : "+host+" 지부가 파일 송/수신 대기 상태입니다.");
			clients.put(host, dos);
			
			String fName = dis.readUTF();
			FileOutputStream fos = new FileOutputStream("c:\\test\\" + fName);
			byte[] b = new byte[1024];
			int n = 0;
			long Filesize = 0;
			while ((n=dis.read(b)) != -1)
			{
				fos.write(b, 0, n);
				Filesize += n;
			}
			
			while(dis != null)
			{
				dis.readUTF();
			}
		}
		}
		catch (IOException e)
		{
			
		}
		
		finally
		{
			clients.remove(host);
			System.out.println("현재 "+ clients.size()+"개의 지부가 대기 중입니다.");
		}
		
		
	}
}
}
