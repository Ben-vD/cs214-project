import java.awt.Color;
import java.util.HashMap;

/**
 *
 * This class contains the methods for spot detection given an image with a
 * spesified epsilon for edge detection as well as a lower and upper bound
 * for the masks
 *
 */

public class SpotDetection {

  private static HashMap<Integer, Integer> diffM =
       new HashMap<Integer, Integer>(8);
  
  private static int count = 0;
  private static boolean[][] counted;

  /**
   * STILL NEED TO DO
   *
   * @param edges    T
   * @param lowerLim T
   * @param upperLim T
   * @param width    T
   * @param height   T
   * @return         T
   * @see int
   */

  public static int spotDetection(Picture edges, int lowerLim, int upperLim, 
      int width, int height) {

    counted = new boolean[height][width];


    HashMap<Integer, Integer> deltaM = new HashMap<Integer, Integer>(8);
    HashMap<Integer, Integer> widthM = new HashMap<Integer, Integer>(8);
    createMaps(diffM, widthM, deltaM);

    int[][] spots = new int[edges.height()][edges.width()];
    int[][] edgesArr = HelpFunctions.picToInt(edges);

    for (int radius = lowerLim; radius <= upperLim; radius++) {
      findSpots(edgesArr, spots, createSpotMask(radius, 
          widthM.get(radius), deltaM.get(radius)), height, width);
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int c = spots[row][col];
        Color color = new Color(c, c, c);
        edges.set(col, row, color);
      }
    }

    System.out.println(count);
    return count;

  }

  /**
  * TThis method takes in the spesified parameters and creates a mask accordinly
  *
  * @param radius Mask radius
  * @param width  Mask width
  * @param delta  Mask delta value
  * @return       2d int arr that is the mask
  * @see int[][]
  */

  private static int[][] createSpotMask(int radius, int width, int delta) {

    int bound = 2 * radius + 1;

    int[][] xx = new int[bound][bound];
    int[][] yy = new int[bound][bound];
    int[][] circle = new int[bound][bound];
    boolean[][] donut = new boolean[bound][bound];
    int[][] mask = new int[bound][bound];

    for (int i = 0; i < bound; i++) {
      for (int j = 0; j < bound; j++) {
        xx[i][j] = i;
        yy[i][j] = j;

        circle[i][j] =
            (xx[i][j] - radius) * (xx[i][j] - radius) + (yy[i][j] - radius)
            * (yy[i][j] - radius);

        donut[i][j] = (circle[i][j] < ((radius - delta)
            * (radius - delta)) + width) && (circle[i][j] > ((radius - delta)
            * (radius - delta)) - width);

        if (donut[i][j]) {
          mask[i][j] = 255;
        } else {
          mask[i][j] = 0;
        }

      }
    }

    // HelpFunctions.printArr(mask);

    return mask;

  }

  /**
   * T
   *
   * @param edgesArr The image of edges
   * @param spots    The spots image
   * @param mask     2d int array mask
   * @param height   height of image
   * @param width    width of image
   */

  private static void findSpots(int[][] edgesArr, int[][] spots,
      int[][] mask, int height, int width) {

    int startVal = mask.length / 2;

    // Loop over very pixel in the edges image.
    // Excluding the edge parts where the mask does not fit
    for (int col = startVal; col < width - startVal; col++) {
      for (int row = startVal; row < height - startVal; row++) {

        int[][] block = createBlock(row, col, edgesArr, mask, startVal);


        if (maxInBlock(block) == 255) {
          int sum = absSum(block, mask);

          // startVal is equall to the radius
          if (isSpot(sum, startVal)) {
            // addSpot(block, spots, row, col);
            if (!counted[row][col]) {
              count++;
            }
            addSpot(block, spots, row, col);
          }

        }
        // else do nothing.

      }
    }

  }

  /**
   * T
   *
   * @param block t
   * @param spots t
   * @param row   t
   * @param col   t
   */
   
  private static void addSpot(int[][] block, int[][] spots, int row, int col) {

    int radius = block.length / 2;

    for (int r = 0; r < block.length; r++) {
      for (int c = 0; c < block[0].length; c++) {

        spots[row - radius + r][col - radius + c] = block[r][c];
        counted[row - radius + r][col - radius + c] = true;

      }
    }

  }

  /**
   * T
   *
   * @param sum    t
   * @param radius t
   * @return       t
   * @see boolean
   */

  private static boolean isSpot(int sum, int radius) {

    if (sum < diffM.get(radius)) {
      return true;

    }
    return false;
  }

  /**
   * T
   *
   * @param block t
   * @param mask  t
   * @return      t
   * @see int
   */

  private static int absSum(int[][] block, int[][] mask) {

    int sum = 0;
    for (int r = 0; r < block.length; r++) {
      for (int c = 0; c < block[0].length; c++) {
        sum = sum + Math.abs(block[r][c] - mask[r][c]);
      }
    }
    return sum;
  }

  /**
   * T
   *
   * @param arr t
   * @return    t
   * @see int
   */

  private static int maxInBlock(int[][] arr) {

    for (int r = 0; r < arr.length; r++) {
      for (int c = 0; c < arr[0].length; c++) {
        if (arr[r][c] == 255) {
          return 255;
        }
      }
    }
    return 0;
  }

  /**
   * T
   *
   * @param row      t
   * @param col      t
   * @param edgesArr t
   * @param mask     t
   * @param startVal     t
   * @return         t
   * @see int
   */

  private static int[][] createBlock(int row, int col, int[][] edgesArr,
       int[][] mask, int startVal) {

    int s = mask.length;
    int[][] block = new int[s][s];

    for (int r = 0; r < s; r++) {
      for (int c = 0; c < s; c++) {
        block[r][c] = edgesArr[row - startVal + r][col - startVal + c];
      }
    }
    // HelpFunctions.printArr(block);
    return block;
  }

  /**
   * T
   *
   * @param diff  HashTable for difference threshhold 
   * @param width HashTable for width of mask with given radius
   * @param delta HashTable for delta of mask with given radius
   */

  private static void createMaps(HashMap<Integer, Integer> diff,
      HashMap<Integer, Integer> width, HashMap<Integer, Integer> delta) {

    for (int r = 4; r <= 11; r++) {

      width.put(r, r * 3 - 6);

      if (r == 4) {
        delta.put(r, 0);
      } else if (r < 10) {
        delta.put(r, 1);
      } else {
        delta.put(r, 2);
      }
    }

    diff.put(4, 4800);
    diff.put(5, 6625);
    diff.put(6, 11000);
    diff.put(7, 15000);
    diff.put(8, 19000);
    diff.put(9, 23000);
    diff.put(10, 28000);
    diff.put(11, 35000);

  }
}
