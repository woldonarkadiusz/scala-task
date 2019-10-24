import java.io.File

object Main {

  def main (args: Array[String]): Unit = {

    val inputPath:String = "C:\\Users\\Arkadiusz\\Desktop\\ScalaC\\photosAll_in"
    val outputPath: String = "C:\\Users\\Arkadiusz\\Desktop\\ScalaC\\photos_out"


    getListOfFiles(inputPath).foreach{println}

  }


  def getListOfFiles(inputPath: String):Array[String] = {
    val file = new File(inputPath)
    if (file.exists && file.isDirectory) {
      file.listFiles.filter { f => f.isFile && (f.getName.endsWith(".png") || f.getName.endsWith(".jpg")) }.
        map(_.getAbsolutePath)
    } else {
      Array[String] ()
    }
  }

}
