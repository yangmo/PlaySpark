package util

import java.io.{PrintWriter, BufferedReader, FileOutputStream, File}


import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
 * Created by moyang on 15/10/3.
 */
object FileUtil {

  def read(path: String): String = {
    val source = Source.fromFile(path)
    val str = source.mkString
    source.close()
    str
  }

  def getFilesUnder(dir: String): ListBuffer[String] = {
    var files = new ListBuffer[String]
    val root = new File(dir)

    if(!root.exists()) {
      return files;
    }

    for(file <- root.list()) {
      if(new File(root + "/" + file).isDirectory) {
        files.appendAll(getFilesUnder(root + "/" + file))
      } else {
        files.append(root + "/" + file)
      }
    }

    files
  }

  def mkdir(file: File): Unit = {
    if(!file.getParentFile.exists()){
      mkdir(file.getParentFile)
    }
    file.mkdir();
  }

  /**
   * Write content to path.
   *
   * @param path
   * @param content
   */
  def writeToFile(path: String, content: String) : Unit = {
    val file = new File(path)
    println(path)
    if(!file.getParentFile.exists()){
      new File(file.getParent).mkdirs();
    }
    file.createNewFile();
    val out = new PrintWriter(path)
    out.write(content)
    out.close()
  }

  def getParentPath(path: String): String = {
    val index = path.lastIndexOf("/")
    return path.substring(0, index)
  }
}
