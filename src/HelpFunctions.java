import java.awt.Color;


/**
 *
 * This class contains a few simple methods that
 *
 */
public class HelpFunctions {

  /**
   * This method takes in an array of int type and prints it out row by row.
   *
   * @param arr An input array that is to be printed by the method
   */
  public static void printArr(int[][] arr) {
    String line;
    for (int row = 0; row < arr.length; row++) {
      line = "";
      for (int col = 0; col < arr[0].length; col++) {
        line = line + arr[row][col] + " \t";
      }
      System.out.println(line);
    }
  }

  /**
   * This method converts a greyscale image to a 2d integer array where each
   * value in the array will represent the the grey color value corresponding
   * to the pixel in the image
   *
   * @param pic A greyscale image
   * @return Returns the input image to a 2d int array form
   * @see int
   */
  public static int[][] picToInt(Picture pic) {

    int w = pic.width();
    int h = pic.height();

    int[][] arr = new int[h][w];

    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        arr[row][col] = pic.get(col, row).getBlue();
      }
    }
    return arr;
  }

  /**
   * This method converts a int array to a greyscale image where the
   * value at an index represents pixels greyscale value
   *
   * @param arr The 2d integer array to be converted to a greyscale image
   * @return Returns the greyscale image of the 2d integer array provided.
   * @see Picture
   */
  public static Picture intToPic(int[][] arr) {

    int w = arr[0].length;
    int h = arr.length;

    Picture pic = new Picture(w, h);

    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        int c = arr[row][col];
        Color color = new Color(c, c, c);
        pic.set(col, row, color);
      }
    }
    return pic;
  }
}
