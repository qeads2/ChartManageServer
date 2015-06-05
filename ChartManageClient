import java.io.*;
import java.net.*;
import java.util.*;


public class ChartManageClient {
	private String host;
	private Socket socket;
	private String serverIp = "127.0.0.1";
	
	public static void main (String[] args)
	{
		new ChartManageClient().start();
	}
	
	public void start()
	{
		try
		{
			socket = new Socket(serverIp, 6000);
			System.out.println("서버 접속에 성공했습니다. 지부를 입력해주세요.");
			Scanner s = new Scanner(System.in);
			host = s.nextLine();
			
			ClientReceiver clientReceiver = new ClientReceiver(socket);
			ClientSender clientSender = new ClientSender(socket);
			
			clientReceiver.start();
			clientSender.start();
		}
		catch (IOException e)
		{
			
		}
		
		
	}
	
	class ClientReceiver extends Thread
	{
		Socket socket;
		DataInputStream dis;
		
		public ClientReceiver(Socket socket)
		{
			this.socket = socket;
			try
			{
				dis = new DataInputStream(socket.getInputStream());
			}
			catch (IOException e)
			{
				System.out.println("ddddd");
			}
			
		}
		
		public void run()
		{
			while(dis != null)
			{
				try
				{
					System.out.println(dis.readUTF());
					
				}
				catch (IOException e)
				{
					
				}
				
			}
		}
		
		
	}
	
	class ClientSender extends Thread
	{
		int number;
		Socket socket;
		DataOutputStream dos;
		ArrayList<Chart> chart = new ArrayList<>();
		
		public ClientSender(Socket socket)
		{
			this.socket = socket;
			try
			{
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(host);
				System.out.println("성공적으로 서버와 연결되었습니다.");
			}
			catch (IOException e)
			{
				
			}
		}
		public void  run()
		{
			Scanner s = new Scanner(System.in);
			int reply;
			
			while(dos != null)
			{
				try
				{
					System.out.println("메뉴를 입력해주세요. (1.차트 편집 / 2.차트 전송 / 0.종료)");
					reply = s.nextInt();
					
					if(reply == 0)
					{
						System.out.println(host+"지부가 송/수신 상태를 해제했습니다.");
						System.exit(0);
						
					}
					else if(reply == 1)
					{
						int menu = 0;
						while (true)
						{
							System.out.println("차트를 편집할 수 있습니다. (1.추가 / 2.삭제 / 3.수정 / 4.파일로 저장 / 5.이전 메뉴)");
							menu = s.nextInt();
							if (menu == 1)
							{
								System.out.println("차트 정보를 추가합니다.");
								System.out.println("이름을 입력해주세요.");
								String name = s.next();
								System.out.println("나이를 입력해주세요.");
								int age = s.nextInt();
								System.out.println("지역을 입력해주세요.");
								String area = s.next();
								System.out.println("병명을 입력해주세요.");
								String problem = s.next();
								System.out.println("비고를 입력해주세요.");
								String etc = s.next();
								number += 1;
								chart.add(new Chart(number, name, age, area, problem, etc));
								System.out.println("성공적으로 등록을 완료했습니다.");
								
								
							}
							else if (menu == 2)
							{
								System.out.println("차트 정보를 삭제합니다.");
								for (int i=0; i<chart.size(); i++)
								{
									System.out.println(chart.get(i).toString());
								}
								System.out.println("정보를 삭제할 환자의 번호를 입력해주세요.");
								int rem = s.nextInt();
								for (int i=0; i<chart.size(); i++)
								{
									chart.remove(i);
								}
								System.out.println("삭제를 완료했습니다.");
								
							}
							else if (menu == 3)
							{
								System.out.println("차트 정보를 수정합니다.");
							}
							else if (menu == 4)
							{
								try
								{
								System.out.println("저장된 객체를 파일로 현재 로컬 경로에 저장합니다.");
								ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("chart.dat"));
								oos.writeObject(chart);
								oos.close();
								}
								catch(IOException e)
								{
									System.out.println(e);
								}
							}
							else if (menu == 5)
							{
								break;
							}
						}
					}
					else if(reply == 2) //차트 전송
					{
						try
						{
							String fName ="";
							System.out.println("서버로 차트를 전송합니다.");
							System.out.println("차트의 파일이름을 확장자까지 입력해주세요.");
							fName = s.next();
							
							File sendFile = new File(fName); // 보낼 파일을 위한 객체 생성
							dos.writeUTF(sendFile.getName()); // 파일명을 우선적으로 서버로 전송
							dos.flush();
							byte b[] = new byte[1024]; // 파일의 내용이 저장될 바이트 배열 생성
							int n = 0;
							
							FileInputStream fis = new FileInputStream(fName); // 파일에서 내용을 읽어올 때 사용할 스트림
							long Filesize = 0;
							
							while((n=fis.read(b)) != -1) // null 내용이 아닌 이상 계속해서 쓰고, 사이즈를 변수에 저장
							{
								dos.write(b, 0, n);
								Filesize += n;
							}
							dos.close();
							fis.close();
							socket.close();
						} // try end
						
						catch (IOException e)
						{
							System.out.println(e);
						}
					}
				}
				finally
				{
					
				}
				
			}
			
		}
	}
}
