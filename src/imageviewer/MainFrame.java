package imageviewer;

import imageviewer.control.Command;
import imageviewer.ui.ImageDisplay;
import imageviewer.ui.swing.SwingImageDisplay;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    private ImageDisplay imageDisplay;
    private Map<String, Command> commands = new HashMap<>();
    
    public MainFrame() {
        this.setTitle("Image Viewer");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(image());
        this.add(toolbar(), BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public void add(Command command){
        commands.put(command.name(), command);
    }
    
    private Component image() {
        SwingImageDisplay display = new SwingImageDisplay();
        imageDisplay = display;
        return display;
    }
    
    ImageDisplay getImageDisplay() {
        return imageDisplay;
    }
    
    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.add(button("prev"));
        panel.add(button("next"));
        return panel;
    }

    private Component button(String name) {
        JButton button = new JButton(name);
        button.addActionListener(execute(name));
        return button;
    }

    private ActionListener execute(String name) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(name).execute();
            }
        };
    }
}
