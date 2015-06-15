import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChartManageServer extends JFrame {
	private HashMap<String, DataOutputStream> clients;
	private ServerSocket serverSocket;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame main_frame;
	private JFrame help_jframe;
	private JTextArea ta;
	JTextArea textArea = new JTextArea(15, 50);
	//public JTextArea textArea;
	//private JTextField textField;
	//private final JTable table = new JTable();
	
	private void initComponents()
	{         
		JFrame main_frame = new JFrame();
		Container panel = main_frame.getContentPane();
		
		main_frame.setTitle("CHART MANAGE SERVICE (SERVER)");
		main_frame.setResizable(false);
		main_frame.setVisible(true);
		main_frame.setSize(585,276);
		main_frame.getContentPane().setLayout(null);
		getContentPane().setVisible(true);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 216, 560, 21);
		panel.add(textField_1);
		textField_1.setText("........");
		
		/*JTextArea ta = new JTextArea("hello", 7, 20);
		ta.setBounds(12, 10, 454, 207);
		panel.add(new JScrollPane(ta));*/
		
		JButton btnNewButton = new JButton("\uCC28\uD2B8 \uD1B5\uD569");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 11));
		btnNewButton.setBounds(478, 14, 85, 31);
		panel.add(btnNewButton);
		
		JButton button = new JButton("\uCC28\uD2B8 \uBAA9\uB85D");
		button.setFont(new Font("굴림", Font.PLAIN, 11));
		button.setBounds(478, 64, 85, 31);
		panel.add(button);
		
		JButton button_1 = new JButton("\uCD08\uAE30\uD654");
		button_1.setFont(new Font("굴림", Font.PLAIN, 11));
		button_1.setBounds(478, 114, 85, 31);
		panel.add(button_1);
		
		JButton button_2 = new JButton("도움말");
		button_2.setFont(new Font("굴림", Font.PLAIN, 11));
		button_2.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame help_jframe = new JFrame();
				help_jframe.setTitle("도움말");
				help_jframe.setResizable(false);
				help_jframe.setVisible(true);
				help_jframe.setSize(300,300);
				help_jframe.getContentPane().setLayout(null);
			}
		}
		);
		
		button_2.setBounds(478, 164, 85, 31);
		panel.add(button_2);
		
		
		textArea.setBounds(12, 10, 454, 192);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		main_frame.getContentPane().add(textArea);
		
		
		try{
			Socket socket = null;
			serverSocket = new ServerSocket(6000);
			//System.out.println("차트 통합 관리 서버가 기동중입니다.");
			textArea.append("차트 통합 관리 서버가 기동중입니다...\n");
			
			while(true)
			{
			socket = serverSocket.accept();
			//System.out.println("호스트와 연결 완료.");
			textArea.append("호스트와 연결 완료...\n");
			
			ChartReceiver fr = new ChartReceiver(socket); //클라이언트와의
			fr.start();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		/*
		JFrame frame;
		JPanel east_panel;
		JTextField printWindow;
		
		frame = new JFrame("CHART MANAGE SERVICE -SERVER");
		east_panel = new JPanel();
		//printWindow = new JTextField("0");
		
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		
		//printWindow.setHorizontalAlignment(JTextField.RIGHT);   // 우측정렬
		//printWindow.setEditable(false);
		//printWindow.setVisible(true);
		
		frame.setSize(486,300);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 231, 446, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uCC28\uD2B8 \uD1B5\uD569");
		
		
		btnNewButton.setBounds(371, 10, 87, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("\uCC28\uD2B8 \uBAA9\uB85D");
		button.setBounds(371, 64, 87, 47);
		frame.getContentPane().add(button);
		
		
		JButton button_1 = new JButton("\uCD08\uAE30\uD654");
		button_1.setBounds(371, 121, 87, 46);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("\uB3C4\uC6C0\uB9D0");
		button_2.setBounds(371, 177, 87, 44);
		frame.getContentPane().add(button_2);
		table.setBounds(12, 10, 347, 211);
		frame.getContentPane().add(table);
		frame.setVisible(true);
		*/
		
	}
	
	
	public static void main(String[] args)
	{
		
		new ChartManageServer().start();
	}
	
	public ChartManageServer()
	{
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
		initComponents();
	}
	public void start()
	{
		
	}


class ChartReceiver extends Thread
{
	
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	//JTextArea textArea = new JTextArea();
	
	
	 
	
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
			
			//System.out.println("HOST : "+host+" 지부가 파일 송/수신 대기 상태입니다.");
			textArea.append("HOST : "+host+" 지부가 파일 송/수신 대기 상태입니다.\n");
			clients.put(host, dos);
		
			if (socket.isConnected() == false)
			{
				System.out.println("@@@@@@@@@@@@@@@@");
				clients.remove(host, dos);
			}
			
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
			
		}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			Set key = clients.keySet();

			System.out.println("현재 "+ clients.size()+"개의 지부가 대기 중입니다.");
			System.out.println("========== 대기 중 지부 목록 ==========");
			for (Iterator iterator = key.iterator(); iterator.hasNext();) 
			{
				String keyName = (String) iterator.next();
				System.out.println(keyName);
			}
			System.out.println("=================================");
		}
		
		
	}
}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
