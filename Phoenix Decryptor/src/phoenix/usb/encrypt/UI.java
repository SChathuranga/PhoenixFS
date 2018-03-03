package phoenix.usb.encrypt;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class UI extends JFrame {

	public String filePath;
	public String phoenixFilePath;
	public String ashFilePath;
	public String usbPath;
	private JPanel contentPane;
	public static URL checkedUrl;
	public static URL logoUrl;
	public static URL waitTop, waitBot;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			checkedUrl = UI.class.getResource("/checked.png");
			logoUrl = UI.class.getResource("/phoenix.png");
			waitTop = UI.class.getResource("/waitTop.gif");
			waitBot = UI.class.getResource("/waitBot.gif");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ })
	public UI() {
		setResizable(false);
		java.awt.Image image;
		try {
			image = ImageIO.read(logoUrl);
			setIconImage(image);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//setIconImage(Toolkit.getDefaultToolkit().getImage(appLogo));
		setTitle("Innoviax SecureFiles");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 604, 417);
		addWindowListener(new java.awt.event.WindowAdapter()
				{
					@Override
					public void windowClosing(java.awt.event.WindowEvent windowEvent)
					{
						try
						{
							String tempDirectory = System.getProperty("java.io.tmpdir");
							File outputDir = new File(tempDirectory + "/phoenix");
							FileUtils.cleanDirectory(outputDir);
							System.exit(0);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
							System.exit(0);
						}
					}
				});
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSecureYourFiles = new JLabel("Educo Soft Decryptor");
		lblSecureYourFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblSecureYourFiles.setForeground(Color.WHITE);
		lblSecureYourFiles.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSecureYourFiles.setBounds(204, 40, 189, 26);
		contentPane.add(lblSecureYourFiles);
		
		JLabel lblPleaseWaitText = new JLabel("Please wait...");
		lblPleaseWaitText.setForeground(Color.WHITE);
		lblPleaseWaitText.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblPleaseWaitText.setVisible(false);
		lblPleaseWaitText.setBounds(204, 340, 105, 24);
		contentPane.add(lblPleaseWaitText);
		
		JLabel lblPleaseWaitBot = new JLabel("New label");
		lblPleaseWaitBot.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseWaitBot.setIcon(new ImageIcon(waitBot));
		lblPleaseWaitBot.setVisible(false);
		lblPleaseWaitBot.setBounds(327, 329, 85, 50);
		contentPane.add(lblPleaseWaitBot);
		
		JLabel lblPleaseWaitTop = new JLabel("");
		lblPleaseWaitTop.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseWaitTop.setIcon(new ImageIcon(waitTop));
		lblPleaseWaitTop.setVisible(false);
		lblPleaseWaitTop.setBounds(405, 31, 55, 50);
		contentPane.add(lblPleaseWaitTop);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 80, 586, 266);
		contentPane.add(panel_1);
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setLayout(null);
		
		JButton btnRevealUsb = new JButton("Reveal USB");
		btnRevealUsb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPleaseWaitTop.setVisible(true);
				lblPleaseWaitBot.setVisible(true);
				lblPleaseWaitText.setVisible(true);
				SampleMain reveal = new SampleMain();
				if(reveal.fileDeCrypt(ashFilePath, phoenixFilePath))
				{
					JOptionPane.showMessageDialog(null, "Files are readable now!", "Phoenix", JOptionPane.INFORMATION_MESSAGE);
					lblPleaseWaitTop.setVisible(false);
					lblPleaseWaitBot.setVisible(false);
					lblPleaseWaitText.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Unable to Reveal your protected files", "Phoenix", JOptionPane.ERROR_MESSAGE);
					lblPleaseWaitTop.setVisible(false);
					lblPleaseWaitBot.setVisible(false);
					lblPleaseWaitText.setVisible(false);
				}
			}
		});
		btnRevealUsb.setBounds(152, 192, 107, 28);
		panel_1.add(btnRevealUsb);
		btnRevealUsb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblSelectPhoenixFile = new JLabel("Select Phoenix File");
		lblSelectPhoenixFile.setForeground(Color.WHITE);
		lblSelectPhoenixFile.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSelectPhoenixFile.setBounds(105, 57, 127, 16);
		panel_1.add(lblSelectPhoenixFile);
		
		JLabel lblSelectAshFile = new JLabel("Select Ash File");
		lblSelectAshFile.setForeground(Color.WHITE);
		lblSelectAshFile.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSelectAshFile.setBounds(105, 124, 127, 16);
		panel_1.add(lblSelectAshFile);
		
		JLabel lblPhoenixChecked = new JLabel("");
		lblPhoenixChecked.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhoenixChecked.setIcon(new ImageIcon(checkedUrl));
		lblPhoenixChecked.setBounds(489, 51, 28, 28);
		lblPhoenixChecked.setVisible(false);
		panel_1.add(lblPhoenixChecked);
		
		JButton btnSelectPhoenixFile = new JButton("Select Phoenix File");
		btnSelectPhoenixFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter phoenixFilefilter = new FileNameExtensionFilter("Phoenix File", "phoenix");
				fileChooser.setFileFilter(phoenixFilefilter);
				fileChooser.setDialogTitle("Choose files to secure");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fileChooser.showOpenDialog(btnSelectPhoenixFile) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					phoenixFilePath = file.getAbsolutePath();
					lblPhoenixChecked.setVisible(true);
					
				}
				else
					JOptionPane.showMessageDialog(null, "No files selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JLabel lblAshChecked = new JLabel("");
		lblAshChecked.setIcon(new ImageIcon(checkedUrl));
		lblAshChecked.setHorizontalAlignment(SwingConstants.CENTER);
		lblAshChecked.setBounds(489, 118, 28, 28);
		lblAshChecked.setVisible(false);
		panel_1.add(lblAshChecked);
		btnSelectPhoenixFile.setBounds(325, 51, 155, 28);
		panel_1.add(btnSelectPhoenixFile);
		
		JButton btnSelectAshFile = new JButton("Select Ash File");
		btnSelectAshFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter ashFileFilter = new FileNameExtensionFilter("Ash File", "ash");
				fileChooser.setFileFilter(ashFileFilter);
				fileChooser.setDialogTitle("Choose files to secure");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fileChooser.showOpenDialog(btnSelectAshFile) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					ashFilePath = file.getAbsolutePath();
					lblAshChecked.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "No files selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnSelectAshFile.setBounds(325, 118, 155, 28);
		panel_1.add(btnSelectAshFile);
		
		JButton btnViewFiles = new JButton("View Files");
		btnViewFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				
				String tempDirectory = System.getProperty("java.io.tmpdir");
				File outputDir = new File(tempDirectory + "/phoenix");
				fileChooser.setCurrentDirectory(outputDir);
				
				fileChooser.setDialogTitle("Choose file to view");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fileChooser.showOpenDialog(btnViewFiles) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					try 
					{
						Desktop.getDesktop().open(file);
					} 
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnViewFiles.setBounds(335, 192, 107, 28);
		panel_1.add(btnViewFiles);
		
		JLabel lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//code goes here
				AboutUI aboutWindow = new AboutUI();
				aboutWindow.setUndecorated(true);
				aboutWindow.setVisible(true);
			}
		});
		lblAbout.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAbout.setForeground(Color.GRAY);
		lblAbout.setBounds(537, 366, 55, 16);
		contentPane.add(lblAbout);
	}
}
