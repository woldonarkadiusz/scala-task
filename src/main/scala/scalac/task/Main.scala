package scalac.task


//To run the application properly, you need the external library opencv version 3.4.6
//The application should start with the main function arguments:
// 1. path to the input directory
// 2. path to the output directory


import java.io.File
import org.opencv.core.{Core, Mat}
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

object Main {

  val rParam: Int = 0;
  val gParam: Int = 1;
  val bParam: Int = 2;
  val minimalGrayScaleValue: Int = 60;
  val brightnessFactorThreshold: Int = 30;

  def main(args: Array[String]): Unit = {

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    val inputPath: String = "C:\\Users\\Arkadiusz\\Desktop\\ScalaC\\photos_in"
    val outputPath: String = "C:\\Users\\Arkadiusz\\Desktop\\ScalaC\\photos_out"

    val imgList = getListOfFiles(inputPath);

    imageClassification(inputPath, outputPath, imgList);
  }

  def getListOfFiles(inputPath: String): Array[String] = {
    val file = new File(inputPath)
    file.listFiles.filter { f => f.isFile && (f.getName.endsWith(".png") || f.getName.endsWith(".jpg")) }.
      map(_.getName)
  }

  def imageClassification(inputPath: String, outputPath: String, lisOfImg: Array[String]): Unit = {
    for (e <- lisOfImg) {
      val source: Mat = Imgcodecs.imread(inputPath + "\\" + e, Imgproc.COLOR_RGB2GRAY)
      var row: Int = source.rows()
      var col: Int = source.cols()
      var numberOfPixels: Double = row * col
      var whitePixels: Int = 0
      var blackPixels: Int = 0

      for (i <- 0 to row - 1) {
        for (j <- 0 to col - 1) {
          var rgb = source.get(i, j)
          var greyScaleValue = (rgb(rParam).toInt + rgb(gParam).toInt + rgb(bParam).toInt) / 3
          if (greyScaleValue < minimalGrayScaleValue) {
            blackPixels += 1
          } else {
            whitePixels += 1
          }
        }
      }

      var brightnessFactor: Double = (whitePixels / numberOfPixels) * 100

      var fullNameImg =  e.split("\\.")
      if (brightnessFactor > brightnessFactorThreshold) {
        Imgcodecs.imwrite(outputPath + "\\" + fullNameImg(0) + "_bright_" + brightnessFactor.toInt + "." + fullNameImg(1), source)
      } else {
        Imgcodecs.imwrite(outputPath + "\\" + fullNameImg(0) + "_dark_" + brightnessFactor.toInt + "." + fullNameImg(1), source)
      }
    }
  }

}
