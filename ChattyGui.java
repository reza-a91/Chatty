import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ChattyGui implements IGui {

	class AddGroup extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AddGroup(String myName, Icon myIcon) {
			super(myName, myIcon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("");

			if (notRegisteredGroups.getModel().getSize() >= 0
					&& notRegisteredGroups.getSelectedIndex() >= 0) {

				guiClient.joinGroup(notRegisteredGroups.getSelectedValue());

			} else {
				statusLabel
						.setText("Notification: Please select group to join");
			}

		}

	}

	class CreateGroup extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateGroup(String myName, Icon myIcon) {
			super(myName, myIcon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("");

			if (!createGroupTextField.getText().equals("")
					&& createGroupTextField.getText() != null) {
				String text = createGroupTextField.getText();
				if (text != null) {
					guiClient.createGroup(text);
				}

				createGroupTextField.setText("");
			} else {
				statusLabel
						.setText("Notification: Enter valid group name (no blank string).");
			}

		}

	}

	class DeleteGroup extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DeleteGroup(String myName, Icon myIcon) {
			super(myName, myIcon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("");

			if (notRegisteredGroups.getModel().getSize() >= 0
					&& notRegisteredGroups.getSelectedIndex() >= 0) {

				guiClient.deleteGroup(notRegisteredGroups.getSelectedValue());

			} else {
				statusLabel
						.setText("Notification: Please select 'available group' to delete.");
			}
		}
	}

	class RevokeGroup extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RevokeGroup(String myName, Icon myIcon) {
			super(myName, myIcon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("");

			if (registeredGroups.getModel().getSize() >= 0
					&& registeredGroups.getSelectedIndex() >= 0) {

				guiClient.leaveGroup(registeredGroups.getSelectedValue());
			} else {
				statusLabel
						.setText("Notification: Please select a 'registered group' to leave.");
			}

		}
	}

	class SendMessage extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SendMessage(String myName, Icon myIcon) {
			super(myName, myIcon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("");

			if (registeredGroups.getModel().getSize() >= 0
					&& registeredGroups.getSelectedIndex() >= 0) {
				IChattyGroup group = registeredGroups.getSelectedValue();
				String msgText = sendMessageTextField.getText().toString();
				String client = name;

				ChattyMessage msg = new ChattyMessage(group, client, msgText);

				guiClient.sendMessage(msg);

				sendMessageTextField.setText("");
			} else {
				statusLabel
						.setText("Notification: Please select a 'registered group' to send to.");
			}

		}

	}

	class exitWindow extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			guiClient.unregister();
			System.out.println("Client deaktiviert: " + name);
		}
	}

	private static final String CHATTY_NAME = "Chatty Client: ";
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 455;
	private static final int GROUPLIST_WIDTH = 70;
	private static final int GROUPLIST_HEIGHT = 100;

	private static final int CHATBOX_WIDTH = 460;
	private static final int CHATBOX_HEIGHT = 260;
	private String name;

	private IGuiClient guiClient;
	private JFrame guiFrame;
	private JList<IChattyGroup> notRegisteredGroups;
	private JList<IChattyGroup> registeredGroups;
	private JButton createGroupButton;
	private JButton deleteGroupButton;
	private JButton addGroupButton;

	private JButton removeGroupButton;

	private JButton sendMessageButton;

	private JTextField createGroupTextField;

	private JTextField sendMessageTextField;

	private JTextArea messageHistory;

	private JLabel statusLabel;

	public ChattyGui(IGuiClient guiClient, String name)
			throws NullPointerException, Exception {

		if (guiClient == null) {
			throw new NullPointerException("ChattyGUI: No GUI client given");
		}

		if (name == null) {
			throw new NullPointerException("ChattyGUI: No name given");
		}

		if (name.equals("")) {
			throw new Exception("ChattyGUI: No name given");
		}

		this.name = name;
		this.guiClient = guiClient;
		initGUI();
	}

	@Override
	public void deliverMessage(ChattyMessage chattyMessage) {
		// TODO Auto-generated method stub
		messageHistory.append(chattyMessage.toString() + "\n");

	}

	private void initGUI() {
		guiFrame = new JFrame();
		guiFrame.setSize(ChattyGui.FRAME_WIDTH, ChattyGui.FRAME_HEIGHT);
		guiFrame.setResizable(false);
		guiFrame.setTitle(ChattyGui.CHATTY_NAME + name);
		guiFrame.setLayout(new FlowLayout(FlowLayout.LEFT));

		guiFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		guiFrame.addWindowListener(new exitWindow());

		JPanel createGroupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Group Creation

		JLabel createGroupLabel = new JLabel("Create Group");
		createGroupPanel.add(createGroupLabel);

		Icon imgNewGroup = null;
		Icon imgDeleteGroup = null;
		Icon imgAddGroup = null;
		Icon imgRemoveGroup = null;
		Icon imgSendMail = null;
		try {

			imgNewGroup = new ImageIcon(ImageIO.read(getClass().getResource(
					"/ressources/Notes.png")));
			imgDeleteGroup = new ImageIcon(ImageIO.read(getClass().getResource(
					"/ressources/Trash.png")));
			imgAddGroup = new ImageIcon(ImageIO.read(getClass().getResource(
					"/ressources/Add.png")));
			imgRemoveGroup = new ImageIcon(ImageIO.read(getClass().getResource(
					"/ressources/Delete.png")));
			imgSendMail = new ImageIcon(ImageIO.read(getClass().getResource(
					"/ressources/Letter.png")));

		} catch (IOException ex) {
			System.out.println("Image file not found!");
		}

		createGroupTextField = new JTextField();
		createGroupTextField.setColumns(20);
		createGroupPanel.add(createGroupTextField);

		CreateGroup createGroupAction = new CreateGroup("New Group",
				imgNewGroup);
		createGroupButton = new JButton(createGroupAction);
		createGroupPanel.add(createGroupButton);

		DeleteGroup deleteGroupAction = new DeleteGroup("Delete Group",
				imgDeleteGroup);
		deleteGroupButton = new JButton(deleteGroupAction);
		createGroupPanel.add(deleteGroupButton);

		// Group Editing

		JPanel groupListPanel = new JPanel();
		groupListPanel
				.setLayout(new BoxLayout(groupListPanel, BoxLayout.Y_AXIS));

		// Definiert Bereich für die nicht registrierten Gruppen
		JLabel notRegisteredLabel = new JLabel("Available Groups");
		notRegisteredLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		groupListPanel.add(notRegisteredLabel);

		notRegisteredGroups = new JList<IChattyGroup>();
		notRegisteredGroups.setAlignmentX(Component.LEFT_ALIGNMENT);
		notRegisteredGroups.setSelectedIndex(0);
		notRegisteredGroups
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notRegisteredGroups.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JScrollPane notRegisteredGroupsScroll = new JScrollPane(
				notRegisteredGroups);
		notRegisteredGroupsScroll.setPreferredSize(new Dimension(
				ChattyGui.GROUPLIST_WIDTH, ChattyGui.GROUPLIST_HEIGHT));
		groupListPanel.add(notRegisteredGroupsScroll);

		// Definiert bereich mit den Gruppen Aktionen
		JPanel actionGroupPanel = new JPanel();
		actionGroupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		actionGroupPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		AddGroup addGroupAction = new AddGroup("Join Group", imgAddGroup);
		addGroupButton = new JButton(addGroupAction);

		actionGroupPanel.add(addGroupButton);

		RevokeGroup revokeGroupAction = new RevokeGroup("Leave Group",
				imgRemoveGroup);
		removeGroupButton = new JButton(revokeGroupAction);
		actionGroupPanel.add(removeGroupButton);

		groupListPanel.add(actionGroupPanel);

		// Definiert den Bereich mit den registrierten Gruppen
		JLabel registeredGroupsLabel = new JLabel("Registered Groups");
		registeredGroupsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		groupListPanel.add(registeredGroupsLabel);

		registeredGroups = new JList<IChattyGroup>();
		registeredGroups.setAlignmentX(Component.LEFT_ALIGNMENT);
		registeredGroups.setSelectedIndex(0);
		registeredGroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		registeredGroups.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JScrollPane registeredGroupsScroll = new JScrollPane(registeredGroups);
		registeredGroupsScroll.setPreferredSize(new Dimension(
				ChattyGui.GROUPLIST_WIDTH, ChattyGui.GROUPLIST_HEIGHT));
		groupListPanel.add(registeredGroupsScroll);

		// TextArea for Messages
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		JLabel messageLabel = new JLabel("Messages");
		messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		messageHistory = new JTextArea();
		messageHistory.setLineWrap(true);
		messageHistory.setPreferredSize(new Dimension(ChattyGui.CHATBOX_WIDTH,
				ChattyGui.CHATBOX_HEIGHT));
		messageHistory.setEditable(false);
		JScrollPane messageScrollPane = new JScrollPane(messageHistory);
		messageScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		messagePanel.add(messageLabel, BorderLayout.CENTER);
		messagePanel.add(messageScrollPane, BorderLayout.WEST);

		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		midPanel.add(groupListPanel);
		midPanel.add(messagePanel);

		// Sending Message Panel
		JPanel sendTextPanel = new JPanel();
		sendTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel sendMessageLabel = new JLabel("Send Message");
		sendMessageTextField = new JTextField(30);
		sendMessageButton = new JButton("Send", imgSendMail);

		sendMessageButton.setAction(new SendMessage("Send", imgSendMail));

		sendTextPanel.add(sendMessageLabel);
		sendTextPanel.add(sendMessageTextField);
		sendTextPanel.add(sendMessageButton);

		// create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		statusPanel.setPreferredSize(new Dimension(FRAME_WIDTH - 2, 15));
		statusLabel = new JLabel(" ");
		statusPanel.add(statusLabel);

		guiFrame.add(createGroupPanel);
		guiFrame.add(midPanel);
		guiFrame.add(sendTextPanel);
		guiFrame.add(statusPanel);
		guiFrame.setVisible(true);

	}

	@Override
	public void updateGUI() {
		// TODO Auto-generated method stub

		notRegisteredGroups.setListData(new Vector<IChattyGroup>(guiClient
				.getNotRegisteredGroups()));
		registeredGroups.setListData(new Vector<IChattyGroup>(guiClient
				.getRegisteredGroups()));

	}

}
