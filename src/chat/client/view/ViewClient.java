package chat.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import chat.client.control.ControlClient;


public class ViewClient extends JFrame implements Observer
{
//---------------------------------------------------- Constantes de classe
	public static final int WINDOW_WIDTH = (int)	GraphicsEnvironment.
													getLocalGraphicsEnvironment().
													getMaximumWindowBounds().
													getWidth();
	
	public static final int WINDOW_HEIGHT = (int)	GraphicsEnvironment.
													getLocalGraphicsEnvironment().
													getMaximumWindowBounds().
													getHeight();
	
//---------------------------------------------------- Attributs
	// Containers
	private JPanel globalPanel;
	
	// Boutons
	private JButton connectionButton;
	private JButton sendButton;
	
	// Champs
	private JTextArea screenField;
	private JLabel pseudoLabel;
	private JTextField pseudoField;
	private JLabel messageLabel;
	private JTextField messageField;
	private JLabel hostLabel;
	private JTextField hostField;
	private JLabel portLabel;
	private JSpinner portNumber;
	
	// Controleur
	private ControlClient control;
	
	
//---------------------------------------------------- Constructeur
	ViewClient(String windowName)
	{
		super(windowName);
		
		// Init proprietes
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH/3, WINDOW_HEIGHT/3));
		
		// Init attributs
		initializeComponents();
		control = new ControlClient();
		
		// Finalisation
		this.setContentPane(globalPanel);
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setVisible(true);
	}


	
//---------------------------------------------------- Methodes publiques
	public void mamethode()
	{
		
	}

	//-------------- Implementation des interfaces publiques
	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
	}
	
//---------------------------------------------------- Methodes privees
	private void initializeComponents()
	{
		// Constantes
		final int HOR_MAX_SIZE = 200;
		final int HOR_MIN_SIZE = 150;
		final int VER_MIN_SIZE = 30;
		final int LABEL_HOR_MIN_SIZE = 85;
		
		// Panels
		globalPanel = new JPanel();
			globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.Y_AXIS));
		JPanel connectionPanel = new JPanel();
			connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.X_AXIS));
			globalPanel.add(connectionPanel);
		JPanel screenPanel = new JPanel();
			screenPanel.setLayout(new GridLayout(1, 1));
			globalPanel.add(screenPanel);
		JPanel sendingPanel = new JPanel();
			sendingPanel.setLayout(new BoxLayout(sendingPanel, BoxLayout.X_AXIS));
			globalPanel.add(sendingPanel);
		
		// Connexions
		hostLabel = new JLabel("IP du serveur :", SwingConstants.RIGHT);
			hostLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			hostLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			connectionPanel.add(hostLabel);
		hostField = new JTextField();
			hostField.setMinimumSize(new Dimension(HOR_MIN_SIZE, VER_MIN_SIZE));
			hostField.setMaximumSize(new Dimension(HOR_MAX_SIZE, VER_MIN_SIZE));
			connectionPanel.add(hostField);
		portLabel = new JLabel("Port :", SwingConstants.RIGHT);
			portLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE - 30, VER_MIN_SIZE));
			portLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			connectionPanel.add(portLabel);
		portNumber = new JSpinner();
			portNumber.setModel(new SpinnerNumberModel(1099, 0, 50000, 1));
			portNumber.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE - 30, VER_MIN_SIZE));
			portNumber.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			connectionPanel.add(portNumber);
		connectionButton = new JButton("Connexion");
			connectionButton.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE+10, VER_MIN_SIZE));
			connectionButton.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE+10, VER_MIN_SIZE));
			connectionPanel.add(connectionButton);
		
		// Ecran
		screenField = new JTextArea("Bienvenue dans le chat");
			screenField.setEditable(false);
			screenField.setAutoscrolls(true);
			screenField.setMinimumSize(new Dimension(WINDOW_WIDTH/3, WINDOW_HEIGHT/3));
			screenPanel.add(screenField, BorderLayout.CENTER);
		
		// Messages
		pseudoLabel = new JLabel("Pseudo :");
			pseudoLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			sendingPanel.add(pseudoLabel);
		pseudoField = new JTextField();
			pseudoField.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoField.setPreferredSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoField.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE+10, VER_MIN_SIZE));
			sendingPanel.add(pseudoField);
		messageLabel = new JLabel("Message : ");
			messageLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE-10, VER_MIN_SIZE));
			messageLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			sendingPanel.add(messageLabel);
		messageField = new JTextField();
			messageField.setMinimumSize(new Dimension(HOR_MIN_SIZE, VER_MIN_SIZE));
			messageField.setMaximumSize(new Dimension(WINDOW_WIDTH, VER_MIN_SIZE));
			sendingPanel.add(messageField);
		sendButton = new JButton("Envoyer");
			sendingPanel.add(sendButton);
		
	}
	
	//------- TESTS ONLY
	public static void main(String[] args)
	{
		ViewClient v = new ViewClient("Mon chat client");
	}

}
