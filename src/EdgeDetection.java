import java.awt.Color;

/**
 *
 * Class contains methods that performs edge detection on the given image
 * given a spesified epsilon value
 */
public class EdgeDetection {

   
  /**
   * Method takes image and converts it to an 2d int array. It changes all
   * the edge cells to black and then provides all the other cells to
   * newCellVal(refArr, row, col, epsilon) which determines if the cells are
   * adges or not given the threshold epsilon value 
   * 
   * @param picture The image provided by the user for edge Detection
   * @param epsilon The given threshold value for determining an edge,
   *                wich we call epsilon.
   * @param w       The width of the provided image
   * @param h       The height of the image provided
   */
  public static void edgeDetection(Picture picture, int epsilon, int w, int h) {

    /** Convert Picture to int arr */
    int[][] picArr = HelpFunctions.picToInt(picture);
    int[][] refArr = HelpFunctions.picToInt(picture);

    for (int i = 0; i < h; i++) {
      picArr[i][0] = 0;
      picArr[i][w - 1] = 0;
    }

    for (int j = 0; j < w; j++) {
      picArr[0][j] = 0;
      picArr[h - 1][j] = 0;
    }

    /** Exclude Edges */
    for (int row = 1; row < h - 1; row++) {
      for (int col = 1; col < w - 1; col++) {
        picArr[row][col] = newCellVal(refArr, row, col, epsilon);
      }
    }

    /** Rebuild image */
    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        int c = picArr[row][col];
        Color color = new Color(c, c, c);
        picture.set(col, row, color);
      }
    }
  }

  /**
   * Method takes the current cell and determines according to the following
   * rules whether it is a edge or not:
   * If the differnce between the value of the central/current cell and
   * each of the 4 surrounding cell values are less than the given 
   * threshold value epsilon then the current cell will be 0. Otherwise it
   * will not be an edge and we change the value to 255.
   * 
   * @param ref The refernce 2d int array of the image 
   * @param r   The row index of the current cell on the lattice array
   * @param c   The column index of the current cell on the lattice array
   * @param ep  The epsilon value provided as an argument in Animal.java
   * @return    Returns 255 if the current cell is a edge or 0 if not
   * @see int
   */
  private static int newCellVal(int[][] ref, int r, int c, int ep) {

    int[] nArr = new int[5];
    int[] epDifArr = new int[4];

    nArr[0] = ref[r][c];
    nArr[1] = ref[r][c - 1];
    nArr[2] = ref[r - 1][c];
    nArr[3] = ref[r][c + 1];
    nArr[4] = ref[r + 1][c];

    epDifArr[0] = Math.abs(nArr[0] - nArr[1]);
    epDifArr[1] = Math.abs(nArr[0] - nArr[2]);
    epDifArr[2] = Math.abs(nArr[0] - nArr[3]);
    epDifArr[3] = Math.abs(nArr[0] - nArr[4]);

    boolean edge = false;
    for (int i = 0; i < 4; i++) {
      if (epDifArr[i] > ep) {
        edge = true;
        break;
      }
    }

    if (edge) {
      return 255;
    } else {
      return 0;
    }
  }
}
