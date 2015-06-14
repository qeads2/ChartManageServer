import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;


public class ChartManageClient extends JFrame implements ActionListener{
	
	DataOutputStream dos;
	//=========프레임 류==========
	private JFrame first_frame;
	private JFrame main_frame;
	private JFrame menu1_frame;
	JTextArea textArea = new JTextArea();
	JTextField input = new JTextField();
	JLabel subtitle = new JLabel();
	//=========차트 편집 =========
	JTable fileTable;
	JScrollPane list;
	JPanel listPanel;
	Vector<String> column = new Vector<String>();
	DefaultTableModel model;
	Vector<String> row;
	//=========================
	
	public ChartManageClient() 
	{
		first_frame = new JFrame(); // 클라이언트 초기 지부명 입력 윈도우
		first_frame.setTitle("INFO REGISTRATION");
		Container first_panel = first_frame.getContentPane();
		first_frame.setVisible(true);
		first_frame.setResizable(false);
		first_frame.setSize(280, 100);
		first_frame.getContentPane().setLayout(null);
		
		
		input.setVisible(true); // 지부명 입력 텍스트 필드
		input.setBounds(105,25,135,20);
		first_panel.add(input);
		
		
		
		
		
		subtitle.setVisible(true); // 지부명 입력 글씨
		subtitle.setBounds(35,25,100,20);
		first_panel.add(subtitle);
		subtitle.setText("지부명");
		
		main_frame = new JFrame(); // 클라이언트 메인 윈도우
		main_frame.setTitle("CHART MANAGE SERVICE (CLIENT)");
		
		Container panel = main_frame.getContentPane();
		main_frame.setVisible(true);
		main_frame.setSize(450,290);
		main_frame.getContentPane().setLayout(null);
		
		//textArea.setEditable(false);
		
		/* 스크롤 팬 등록..?
		JScrollPane main_scroll = new JScrollPane(textArea);
		main_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		main_scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		main_frame.add(main_scroll);
		
		panel.add(textArea);
		*/
		
		JButton btnNewButton = new JButton("\uCC28\uD2B8 \uD3B8\uC9D1"); // 편집 버튼
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 11));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				menu1_frame = new JFrame();
				menu1_frame.setTitle("CHART MODIFY");
				menu1_frame.setResizable(false);
				menu1_frame.setSize(500,500);
				menu1_frame.setVisible(true);
				menu1_frame.getContentPane().setLayout(null);
				
				Container menu1 = menu1_frame.getContentPane();
				column.addElement("No");
				column.addElement("File");
				column.addElement("Date");
				
				FileList filelist = new FileList();
				
				row = new Vector<String>();
				//=============================
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
				File file = new File("C:\\test\\");
				file = sortFileList(file,COMPARETYPE_DATE);
				String modi_date = null;
				String[] ex_name = file.list(new DatFileFilter(".dat"));
				for(int i=0; i<ex_name.length; i++)
				{
					File find_file = new File("C:\\test\\"+ex_name[i]);
					modi_date = sdf.format(find_file.lastModified()); 
					System.out.println(modi_date);
				}
				//=============================
				
				model = new DefaultTableModel(column,0);
				fileTable = new JTable(model);
				list = new JScrollPane(fileTable);
				
				for(int i=0; i<ex_name.length; i++)
				{
					System.out.println(ex_name[i]);
					row.addElement(Integer.toString(i+1));
					row.addElement(ex_name[i]);
					row.addElement(modi_date);
					
					
				}
				model.addRow(row);
				
				list.setBounds(50,50,300,400);
				fileTable.getColumn("No").setPreferredWidth(6);
				menu1.add(list);
				
				
				
				/*
				listPanel = new JPanel(new BorderLayout());
				listPanel.setLayout(null);
				
				listPanel.add(list, BorderLayout.CENTER);
				listPanel.setSize(300,300);
				
				menu1.add(listPanel);
				
				add(listPanel);
				*/
				column.removeElement("No");
				column.removeElement("File");
				column.removeElement("Date");
				
				
			}
		});
		
		btnNewButton.setBounds(22, 206, 89, 33);
		panel.add(btnNewButton);
		
		JButton button_2 = new JButton("\uCC28\uD2B8 \uC804\uC1A1"); // 전송 버튼
		button_2.setFont(new Font("굴림", Font.PLAIN, 11));
		button_2.setBounds(174, 206, 89, 33);
		panel.add(button_2);
		
		JButton button = new JButton("\uC885\uB8CC"); // 종료 버튼
		button.setFont(new Font("굴림", Font.PLAIN, 11));
		button.setBounds(320, 206, 89, 33);
		panel.add(button);
		
		textField = new JTextField(); // 메인 윈도우 입력창
		textField.setBounds(12, 176, 410, 21);
		main_frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		textArea.setBounds(12, 10, 410, 154);
		main_frame.getContentPane().add(textArea);
		//textField.addActionListener(this);     java.lang.NullPointerException
		
		try
		{
			socket = new Socket(serverIp, 6000);
			//Scanner s = new Scanner(System.in);
			//host = s.nextLine();
			input.addActionListener(this);
			
			
			ClientReceiver clientReceiver = new ClientReceiver(socket);
			ClientSender clientSender = new ClientSender(socket);
			
			clientReceiver.start();
			clientSender.start();
		}
		catch (IOException e)
		{
			
		}
		
	}
	
	//
	public void actionPerformed(ActionEvent e)
	{
		DataOutputStream dos;
		if(e.getSource() == textField)
		{
			host = textField.getText();
			textField.setText("");
			//textField.requestFocus();
			//textArea.setCaretPosition(textArea.getDocument().getLength);
		}
		else if (e.getSource() == input)
		{
			host = input.getText();
			input.setText("");
			
			try
			{
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(host);
				//System.out.println("성공적으로 서버와 연결되었습니다.");
				textArea.append("성공적으로 서버와 연결되었습니다.");
				first_frame.dispose();
			}
			
			catch(IOException a)
			{
				a.printStackTrace();
			}
		}
	}
	
	
	private String host;
	private Socket socket;
	private String serverIp = "127.0.0.1";
	public JTextField textField;
	
	public static void main (String[] args)
	{
		new ChartManageClient().start();
	}
	
	public void start()
	{
		
		
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
		private ChartManageClient text;
		int number;
		Socket socket;
		DataOutputStream dos;
		ArrayList<Chart> chart = new ArrayList<>();
		
		public ClientSender(Socket socket)
		{
			this.socket = socket;
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
						try
						{
						dos.writeUTF(host);
						System.out.println(host+"지부가 송/수신 상태를 해제했습니다.");
						System.exit(0);
						}
						catch(IOException e)
						{
							System.out.println(e);
						}
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
									chart.remove(rem);
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
								System.out.println("저장을 위해 파일 이름을 설정해주세요.");
								String filename = s.next();
								
								ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename+".dat"));
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
							
							fis.close();
							
							System.out.println("성공적으로 전송했습니다.");
						} // try end
						
						catch (IOException e)
						
						{
							System.out.println(e);
						}
						finally
						{
							
							
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
