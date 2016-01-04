package chat.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;

import chat.client.AbstractClient;
import chat.client.rmi.ChatClientRMI;
import chat.control.AbstractClientControler;
import chat.control.rmi.RMIClientControler;
import chat.control.socket.SocketClientControler;
import chat.protocol.Message;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class ViewClient extends JFrame implements Observer, WindowListener
{
	// ---------------------------------------------------- Constantes publiques
	public final int WINDOW_WIDTH = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getMaximumWindowBounds().getWidth();

	public final int WINDOW_HEIGHT = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getMaximumWindowBounds().getHeight();

	// ---------------------------------------------------- Attributs
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
	private JSpinner portField;

	// Controleur
	private AbstractClientControler controler;

	// ---------------------------------------------------- Constructeur
	ViewClient(String windowName, AbstractClientControler ctrl)
	{
		super(windowName);

		// Init proprietes
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setPreferredSize(new Dimension(WINDOW_WIDTH / 3, WINDOW_HEIGHT / 3));

		// Init attributs
		initializeComponents();
		controler = ctrl;
		controler.initEnable(this);

		// Finalisation
		setContentPane(globalPanel);
		pack();
		setMinimumSize(this.getSize());
		setVisible(true);
	}
	
	public void test()
	{
		
	}

	// ---------------------------------------------------- Methodes publiques
	public JButton getConnectionButton()
	{
		return connectionButton;
	}
	
	public JButton getSendButton()
	{
		return sendButton;
	}
	
	public JTextField getHostField()
	{
		return hostField;
	}
	
	public JSpinner getPortField()
	{
		return portField;
	}

	// -------------- Implementation des interfaces publiques
	@Override
	public void update(String msg)
	{
		screenField.append("\n" + msg);
		connectionButton.setEnabled(true);
	}

	// ---------------------------------------------------- Methodes privees

	/**
	 * Initialise les composants de la fenêtre
	 */
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
		portField = new JSpinner();
			portField.setModel(new SpinnerNumberModel(1099, 0, 50000, 1));
			portField.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE - 30, VER_MIN_SIZE));
			portField.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			connectionPanel.add(portField);
		connectionButton = new JButton("Connexion");
			connectionButton.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE + 25, VER_MIN_SIZE));
			connectionButton.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE + 25, VER_MIN_SIZE));
			connectionButton.addActionListener(new ConnectionListener(this));
			connectionPanel.add(connectionButton);

		// Ecran
		screenField = new JTextArea("Bienvenue dans le chat");
			screenField.setEditable(false);
			screenField.setAutoscrolls(true);
			screenField.setMinimumSize(new Dimension(WINDOW_WIDTH / 3, WINDOW_HEIGHT / 3));
			screenPanel.add(screenField, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(screenField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setAutoscrolls(true);
			screenPanel.add(scroll);

		// Messages
		pseudoLabel = new JLabel("Pseudo :");
			pseudoLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			sendingPanel.add(pseudoLabel);
		pseudoField = new JTextField();
			pseudoField.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoField.setPreferredSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			pseudoField.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE + 10, VER_MIN_SIZE));
			sendingPanel.add(pseudoField);
		messageLabel = new JLabel("Message : ");
			messageLabel.setMinimumSize(new Dimension(LABEL_HOR_MIN_SIZE - 10, VER_MIN_SIZE));
			messageLabel.setMaximumSize(new Dimension(LABEL_HOR_MIN_SIZE, VER_MIN_SIZE));
			sendingPanel.add(messageLabel);
		messageField = new JTextField();
			messageField.setMinimumSize(new Dimension(HOR_MIN_SIZE + 30, VER_MIN_SIZE));
			messageField.setMaximumSize(new Dimension(WINDOW_WIDTH, VER_MIN_SIZE));
			messageField.setText("Vous n'�tes pas encore connect�");
			messageField.setEnabled(false);
			messageField.addKeyListener(new SendingKeyListener());
			sendingPanel.add(messageField);
		sendButton = new JButton("Envoyer");
			sendButton.addActionListener(new SendingListener());
			sendButton.setEnabled(false);
			sendingPanel.add(sendButton);

	}
	
	// ---------------------------------------------------- Methodes privees

	/**
	 * Envoie un message au controleur
	 */
	private void sendMessage()
	{
		System.out.println("Tentative d'envoie d'un message");
		String username = pseudoField.getText();
		String msg = messageField.getText();
		if(username.length() == 0)
		{
			username = "Inconnu";
		}
		System.out.println("Le message est : " + new Message(msg, username));
		System.out.println("On l'envoie a sendMessageToCLient");
		controler.sendMessageToClient(new Message(msg, username));
		messageField.setText("");
		screenField.setCaretPosition(screenField.getDocument().getLength());
	}
	
	// ---------------------------------------------------- Classes privees - Ecouteurs
	
	private class ConnectionListener implements ActionListener
	// Permet de connecter/deconnecter le client du serveur
	{
		private ViewClient obs;
		
		public ConnectionListener(ViewClient o)
		{
			obs = o;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			// Si on est en phase de connexion
			if(((JButton)e.getSource()).getText() == "Connexion")
			{
				// Recuperation des options de connexions
				String serverIP = hostField.getText();
				int serverPort = (int) portField.getValue();
				String username = pseudoField.getText();
				if(username.length() == 0)
				{
					username = "Inconnu";
				}
				
				// Creation d'un client
				controler.addClient(username, serverIP, serverPort, obs);
				
				// Autorisation d'envoie
				connectionButton.setText("Deconnexion");
				messageField.setText("");
				messageField.setEnabled(true);
				sendButton.setEnabled(true);
			}
			else
			{
				connectionButton.setText("Connexion");
				controler.removeClient(obs);
				sendButton.setEnabled(false);
				messageField.setEnabled(false);
				messageField.setText("Vous n'�tes pas encore connect�");
			}
		}
	}
	
	private class SendingListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			sendMessage();
		}
		
	}
	
	private class SendingKeyListener extends KeyAdapter
	{
		private boolean enterKey;
		
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER && !enterKey)
			{
				sendMessage();
				enterKey = true;
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				enterKey = false;
			}
		}
	}
												
	// ---------------------------------------------------- Fonction principale
	public static void main(String[] args)
	{
		if(args.length != 1)
		{
			System.err.println("Must have only 1 argument : the type of the chat");
			System.exit(1);
		}
		
		AbstractClientControler controler = null;
		
		if(args[0].matches("-rmi"))
		{
			System.out.println("Controleur en mode RMI");
			controler = new RMIClientControler();
		}
		else if(args[0].matches("-socket"))
		{
			System.out.println("Controleur en mode socket");
			controler = new SocketClientControler();
		}

		ViewClient v = new ViewClient("Mon chat client", controler);
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// Pour fermer proprement la fenetre client (sinon erreur lorsque close sans disconnect)
		controler.removeClient(this);
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
