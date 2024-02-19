import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

/**
 *
 * Mode 4 of program. Allows for testing of program using a GUI
 *
 */

public class GUI implements ActionListener {

  JFrame frame = new JFrame("My GUI");
  JButton buttonM0 = new JButton("Mode 0 (Gray Scale)");
  JButton buttonM1 = new JButton("Mode 1 (Noise Reduction)");
  JButton buttonM2 = new JButton("Mode 2 (Edge Detection)");
  JButton buttonM3 = new JButton("Mode 3 (Spot Detection)");


  JLabel lblEps = new JLabel("Epsilon");
  JLabel lblUpB = new JLabel("Upper Bound Mask");
  JLabel lblLoB = new JLabel("Lower Bound Mask");
  JTextField txtEps = new JTextField(0);
  JTextField txtUpB = new JTextField(7);
  JTextField txtLoB = new JTextField(11);

  /**
   * Constructor for the GUI class
   *
   */
  
  public GUI() {

    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(buttonM0);
    buttonM0.addActionListener(this);

    frame.add(buttonM1);
    buttonM1.addActionListener(this);

    frame.add(buttonM2);
    buttonM2.addActionListener(this);

    frame.add(buttonM3);
    buttonM3.addActionListener(this);

    frame.add(lblEps);
    frame.add(txtEps);

    frame.add(lblLoB);
    frame.add(txtLoB);

    frame.add(lblUpB);
    frame.add(txtUpB);


    frame.setLayout(new GridLayout(0, 1));

    frame.setVisible(true);

  }

  /**
   * STILL NEED TO DO
   *
   *
   *
   * @param e Method listens for actions event performed by one of the buttons
   *
   */
   
  public void actionPerformed(ActionEvent e) {


    JFileChooser fc = new JFileChooser();
    fc.showOpenDialog(frame);
    File file = fc.getSelectedFile();
    String path = file.getPath();

    
    Picture pic = new Picture(path);
    int w = pic.width();
    int h = pic.height();
    
    if (e.getSource() == buttonM0) {
      GrayScale.grayScale(pic, w, h);
      pic.show();
    } else if (e.getSource() == buttonM1) {
      GrayScale.grayScale(pic, w, h);
      NoiseReduction.noiseReduction(pic, w, h);
      pic.show();
    } else if (e.getSource() == buttonM2) {
      int eps = Integer.parseInt(txtEps.getText());
      
      GrayScale.grayScale(pic, w, h);
      NoiseReduction.noiseReduction(pic, w, h);
      EdgeDetection.edgeDetection(pic, eps, w, h);
      pic.show();
    } else {
      int eps = Integer.parseInt(txtEps.getText());
      int lb = Integer.parseInt(txtLoB.getText());
      int ub = Integer.parseInt(txtUpB.getText());
      
      GrayScale.grayScale(pic, w, h);
      NoiseReduction.noiseReduction(pic, w, h);
      EdgeDetection.edgeDetection(pic, eps, w, h);
      int count = SpotDetection.spotDetection(pic, lb, ub, w, h);

      pic.show();
    }

  }

}
