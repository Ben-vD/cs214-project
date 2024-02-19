import java.io.PrintWriter;

/**
 *
 * Main class of the program
 *
 */
public class Animal {

  
  /**
   * Main method that runs the program. Input arguments are according
   * to the following description.
   * Arguments: <mode> <filename> [parameters]
   * 
   * @param args Program input arguments array
   */
  public static void main(String[] args) {

    boolean errPassed = true;
    
    if (!args[0].equals("4")) {
       errPassed = errChecking(args);
    }
    
    if (errPassed) {

      int mode = Integer.parseInt(args[0]);

      if (mode != 4) {

        String filename = args[1];

        Picture pic = new Picture(filename);
        
        int w = pic.width();
        int h = pic.height();

        filename = filename(filename); // Get rid of the extension

        if (mode == 0) {
          /** Grey-scale */
          GrayScale.grayScale(pic, w, h);
          pic.save("../out/" + filename + "_GS.png");
          // System.out.println(filename);Coding
          // pic.show();
        } else if (mode == 1) {
          /** Noise reduction */
          GrayScale.grayScale(pic, w, h);
          NoiseReduction.noiseReduction(pic, w, h);
          pic.save("../out/" + filename + "_NR.png");
          // pic.show();
        } else if (mode == 2) {
          /** Edge Detection */
          int epsilon = Integer.parseInt(args[2]);
          GrayScale.grayScale(pic, w, h);
          NoiseReduction.noiseReduction(pic, w, h);
          EdgeDetection.edgeDetection(pic, epsilon, w, h);
          pic.save("../out/" + filename + "_ED.png");
          // pic.show();
        } else if (mode == 3) {
          int epsilon = Integer.parseInt(args[2]);
          int lowLim = Integer.parseInt(args[3]);
          int upLim = Integer.parseInt(args[4]);

          GrayScale.grayScale(pic, w, h);
          NoiseReduction.noiseReduction(pic, w, h);
          EdgeDetection.edgeDetection(pic, epsilon, w, h);

          int count = SpotDetection.spotDetection(pic, lowLim, upLim, w, h);
          pic.save("../out/" + filename + "_SD.png");
          try {
            PrintWriter out = new PrintWriter("../out/" + filename + ".out");
            out.println(count);
            out.close();
          } catch (Exception e) {
            
          }
          // GUI
        }

        // pic.show();

      } else {
        // GUI
        GUI gui = new GUI();
      }
    }
  }

  /**
   * Method takes the input string of the file directory and removes all the
   * additional components up to the filename.
   *
   * @param file the input string of the file directory
   * @return returns only the filename excluding extension
   * @see String
   */
  public static String filename(String file) {
    return file.substring(file.lastIndexOf("/"), file.lastIndexOf("."));
  }

  /**
   * Function takes in the arguments and checks through the condtions
   * that are specified. If one case fails the the function will return \
   * a boolean false.
   *
   * @param arg the arguments provided in the main method.
   * @return returns true if all cases pass and false if even one fails
   * @see boolean
   */
  public static boolean errChecking(String[] arg) {

    boolean errPass = true;
    int mode = -1;
    int eps = -1;
    int n = arg.length;
    int loB = -1;
    int upB = -1;

    // Invalid nr of arguments
    if (n < 2) {
      errPass = false;
      System.err.println("ERROR: invalid number of arguments");
    }

    // Invalid Type ERROR
    if (errPass) {
      try {
        mode = Integer.parseInt(arg[0]);

        if ((mode == 0 || mode == 1) && n != 2) {
          errPass = false;
          System.err.println("ERROR: invalid number of arguments");
        } else if (mode == 2 && n != 3) {
          errPass = false;
          System.err.println("ERROR: invalid number of arguments");
        } else if (mode == 3 && n != 5) {
          errPass = false;
          System.err.println("ERROR: invalid number of arguments");
        }
        if ((n == 3 && mode == 2)) {
          eps = Integer.parseInt(arg[2]);
        }
        if ((n == 5 && mode == 3)) {
          eps = Integer.parseInt(arg[2]);
          loB = Integer.parseInt(arg[3]);
          upB = Integer.parseInt(arg[4]);
        }
      } catch (Exception e) {
        if (errPass) {
          System.err.println("ERROR: invalid argument type");
          errPass = false;
        }
      }
    }

    // Invalid mode
    if (errPass && (mode < 0 || mode > 3)) {
      errPass = false;
      System.err.println("ERROR: invalid mode");
    }

    // Invalid epsilon
    if (errPass && mode > 1 && (eps < 0 || eps > 255)) {
      errPass = false;
      System.err.println("ERROR: invalid epsilon");
    }



    // Invalid file
    if (errPass) {
      try {
        Picture pic = new Picture(arg[1]);
      } catch (Exception e) {
        errPass = false;
        System.err.println("ERROR: invalid or missing file");
      }
    }
    return errPass;
  }
}
