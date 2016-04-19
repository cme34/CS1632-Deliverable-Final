import javax.swing.JFrame;

/**
 * This Class creates a JFrame and creates an instance of Game. 
 * The Main Method is located here.
 * @author Cory
 */
public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "CS1632 Deliverable Final";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	/**
	 * Initializes a JFrame and it creates an instance of Game and adds it to the window.
	 */
	public Window() {
		//Initialize
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create game
		Game game = new Game();
		add(game);
		
		//Display
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new Window();
	}
}
