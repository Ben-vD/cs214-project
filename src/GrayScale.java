import java.awt.Color;

/**
 *
 * Class contains the methods for greyscaling a provided image
 *
 */
public class GrayScale {

  /**
   * This method takes an image and returns the greyscale form of the
   * image
   *
   * @param pic The image provided by the user for greyscaling
   * @param w   The width of the provided image
   * @param h   The height of the privided image
   */
  public static void grayScale(Picture pic, int w, int h) {

    for (int col = 0; col < w; col++) {
      for (int row = 0; row < h; row++) {
        Color color = pic.get(col, row);
        Color gray = toGray(color);
        pic.set(col, row, gray);
      }
    }
  }

  
   /**
   * Method takes in a color of the current cell and returns the
   * grey version of it.
   *
   * @param color color of the current cell
   * @return      Return the grey version of the color
   * @see         Color
   */
  private static Color toGray(Color color) {
    int picLum = (int) ((intensity(color)));
    Color gray = new Color(picLum, picLum, picLum);
    return gray;
  }

   /**
   * Method takes in a color and returns the weighted average of the rgb
   * values as follows: (0.299*red) + (0.587*green) + (0.114*blue). This
   * returned value is defined as the insity of the color.
   *
   * @param col The color of the current cell
   * @return    Returns the weighted average of the rgb values accordingly
   * @see       double
   */
  private static double intensity(Color col) {
    int red = col.getRed();
    int green = col.getGreen();
    int blue = col.getBlue();

    return (0.299 * red) + (0.587 * green) + (0.114 * blue);
  }
}
