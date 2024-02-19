import java.awt.Color;


/**
 * This class contains the methods and functions that are use for noise
 * reduction
 */
public class NoiseReduction {

  /**
   * Method takes in the picture and turns it into a integer 2d array. Then
   * each cell(excluding edges) value is changed by determining the most
   * popular occurung element with in the von neumann neighborhood. After the
   * cell values are changed the image is rebuilt from the 2d array.
   *
   * @param picture The image provided by the user for nioise reduction
   * @param w       The width of the provided image
   * @param h       The height of the privided image
   */
  public static void noiseReduction(Picture picture, int w, int h) {

    int[][] picArr = HelpFunctions.picToInt(picture);
    int[][] refArr = HelpFunctions.picToInt(picture);

    /** Exclude edges and corners */
    for (int row = 1; row < h - 1; row++) {
      for (int col = 1; col < w - 1; col++) {
        picArr[row][col] = newCellVal(refArr, row, col);
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
   * Method takes the a reference array of the image cell values
   * as well as a row and column index of the current cell. The
   * neibourhood values are then placed in a array to determine
   * the most popular elemtent
   *
   * @param ref The refernce 2d int array of the image 
   * @param r   Row index of current cell
   * @param c   Column index of current cell
   * @return    the new value(the mode of the neighborhood cells)
   *            for the current cell.
   * @see       int
   */
  private static int newCellVal(int[][] ref, int r, int c) {

    int[] nArr = new int[5];

    nArr[0] = ref[r][c];
    nArr[1] = ref[r][c - 1];
    nArr[2] = ref[r - 1][c];
    nArr[3] = ref[r][c + 1];
    nArr[4] = ref[r + 1][c];

    return changeCell(nArr);
  }

  /**  
   * This method takes the current neighborhood array at for the current cell
   * on the picture 2d array and returns the most popular occurring
   * color value in the von neumann neighborhood. 
   * There are multiple cases where 2 most popular values could exist
   * We fix this problem by ranking the cells priorities in the following
   * order(starting with highest priority).
   * Current cell, left cell, Upper cell, Right cell, Lower cell
   * 
   * @param nArr the array containing the neighborhood values
   * @return     The mode of the neighborhood values as spesified
   * @see        int
   */
  private static int changeCell(int[] nArr) {

    int[][] counterArr = new int[5][2];

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (nArr[i] == counterArr[j][0] || counterArr[j][1] == 0) {
          counterArr[j][0] = nArr[i];
          counterArr[j][1]++;
          break;
        }
      }
    }

    int max = 0;
    int i = 0;
    while (i < (5 - 1) && counterArr[i][1] != 0) {
      i++;
      if (counterArr[i][1] > counterArr[max][1]) {
        max = i;
      }
    }
    // Animal.printArr(counterArr);
    return counterArr[max][0];
  }

   /** Test Client 
   *
   * @param args t
   */
  public static void main(String[] args) {

    Picture pic = new Picture("4shapes.png");
    int w = pic.width();
    int h = pic.height();

    GrayScale.grayScale(pic, w, h);
    // noiseReduction(pic, w, h);

    int[] t = {1, 2, 2, 3, 3};
    System.out.println(changeCell(t));
  }
}
