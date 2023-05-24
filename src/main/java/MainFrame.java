import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final String BANNER_IMG_DIR = "";
    @Deprecated
    private static final String ICON_IMG_DIR = "/home/duo/IdeaProjects/PSMK/src/ICON_main.png";
    public static final String BUTTON_SIMPLE_MODE = "Simple mode";
    public static final String BUTTON_ADVANCED_MODE = "Advanced mode";
    private CardLayout cardLayout;
    private JPanel masterPanel, mainContentPanel, mainBanner;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    public MainFrame() {
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMasterPanel();

        setVisible(true);
    }

    private void initMasterPanel() {
        cardLayout = new CardLayout();
        masterPanel = new JPanel(cardLayout);

        JPanel tmp_layout_panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initMainBanner();
        initMainContentPanel();

        // Agregar mainBanner
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        tmp_layout_panel.add(mainBanner, c);

        // Agregar mainContentPanel
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 1.0;
        c.weighty = 2.0;
        tmp_layout_panel.add(mainContentPanel, c);

        masterPanel.add(tmp_layout_panel);

        add(masterPanel);
    }

    private void initMainContentPanel() {
        mainContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Panel de botones
        JPanel tmp_button_menu_panel = initMainContentPanelButtons();
        // Panel de icono
        JPanel icon_panel = initMainContentPanelIcon();

        // Agregar ambos paneles
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 1.0;
        mainContentPanel.add(icon_panel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 1.0;
        mainContentPanel.add(tmp_button_menu_panel, c);
    }

    private JPanel initMainContentPanelIcon() {
        JPanel icon_panel = new JPanel();
        JLabel icon_label = new JLabel(getImageIcon(new Dimension(400,400), ICON_IMG_DIR));
        icon_panel.add(icon_label);
        return icon_panel;
    }

    private JPanel initMainContentPanelButtons() {
        JPanel tmp_button_menu_panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 10, 20);

        JButton button1 = new JButton(BUTTON_SIMPLE_MODE);
        button1.setPreferredSize(new Dimension(250, 45));
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        tmp_button_menu_panel.add(button1, c);

        JButton button2 = new JButton(BUTTON_ADVANCED_MODE);
        button2.setPreferredSize(new Dimension(250, 45));
        c.gridx = 0;
        c.gridy = 1;
        tmp_button_menu_panel.add(button2, c);

        return tmp_button_menu_panel;
    }

    private void initMainBanner() {
        JLabel bannerImage = new JLabel();

        bannerImage.setIcon(getImageIcon(new Dimension(1000, 300), BANNER_IMG_DIR));

        mainBanner = new JPanel();
        mainBanner.add(bannerImage);
    }

    private Icon getImageIcon(Dimension dimension, String absolutePath) {
        ImageIcon icon = new ImageIcon(absolutePath);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }


    public void nextPanel() {
        cardLayout.next(masterPanel);
    }

    public void previousPanel() {
        cardLayout.previous(masterPanel);
    }

}
