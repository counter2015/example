import java.io.InputStream
import java.net.URI
import java.text.SimpleDateFormat

import ConfigInstance.{config => projectConfig}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}
import org.apache.hadoop.io.IOUtils

import scala.util.{Failure, Success, Try}

object SimpleHDFSClient {

  /* helper function for hadoop config */
  def initHadoopConfig(): Configuration = {
    val conf = new Configuration()
    val configList = Array(
      "fs.hdfs.impl",
      "fs.file.impl",
      "fs.defaultFS")
    configList.foreach(str => conf.set(str, projectConfig.getString(str)))
    conf
  }

  val conf: Configuration = initHadoopConfig()

  /**
    * Upload file from local file system to HDFS file system
    * if the file exists in HDFS, let user choose to append data to
    * remain file or overwrite the remain file.
    *
    * usage:
    * --copyFromLocal <local_file_name> <hdfs_path>
    *
    * @param filename the local file name
    * @param HDFSPath the upload HDFS path
    */
  def upload(filename: String, HDFSPath: String): Unit = {
    val fs = FileSystem.get(conf)
    fs.copyFromLocalFile(new Path(filename), new Path(HDFSPath))
    fs.close()
  }

  /**
    * Download file from HDFS to local, if there is a file which has the same name of HDFS file,
    * the file downloaded should be auto rename.
    *
    * usage:
    * --get <hdfs_path> <download_path>
    *
    * @param HDFSPath the download HDFS path
    */
  def download(HDFSPath: String, downloadPath: String = ""): Unit = {
    def extractFilename(str: String): String = str.split("/").last

    // if this function is invoked without `downloadPath`, then it will use the last element which is split by '/'
    val downPath = if (downloadPath == "") extractFilename(HDFSPath) else downloadPath

    val fs = FileSystem.get(conf)
    fs.copyToLocalFile(new Path(HDFSPath), new Path(downPath))
    fs.close()
  }

  /**
    * output HDFS file content to console
    *
    * usage:
    * --cat <hdfs_path>
    *
    * @param HDFSPath the file you want to output its content
    */
  def cat(HDFSPath: String): Unit = {
    val fs = FileSystem.get(URI.create(HDFSPath), conf)

    val in: InputStream = fs.open(new Path(HDFSPath))
    IOUtils.copyBytes(in, System.out, 4096, false)
    fs.close()
  }

  /**
    * show HDFS file attributes: read-write permission, size, create time, full path name,
    * it should be like `hdfs dfs -ls /`
    * "drwxr-xr-x   - root supergroup          0 2019-07-30 14:29 /hbase"
    *
    * usage:
    * --describe <hdfs_path>
    *
    * @param HDFSPath the file you want to get the full status
    */
  def describeFile(HDFSPath: String): Unit = {
    val fs = FileSystem.get(conf)
    val status: Array[FileStatus] = fs.listStatus(new Path(HDFSPath))

    val result = status.map { s =>
      val permission = s.getPermission
      val owner = s.getOwner
      val group = s.getGroup
      val dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getModificationTime)
      val path = s.getPath
      val res = Array(permission, owner, group, dateString, path)
      res.mkString("\t")
    }.mkString("\n")
    println(result)
    fs.close()
  }

  /**
    * make directory in HDFS file system, this will auto create directory
    * if the parent directories doesn't exist
    *
    * usage:
    * --mkdir <hdfs_path>
    *
    * @param HDFSPath the path you want to make directory
    *
    */
  def mkDir(HDFSPath: String): Unit = {
    val fs = FileSystem.get(conf)
    fs.mkdirs(new Path(HDFSPath))
    fs.close()
  }

  /**
    * delete file or directory in HDFS file system, if `isRecursive` is `true` and
    *
    * @param HDFSPath    the path you want to delete
    * @param isRecursive boolean
    */
  def remove(HDFSPath: String, isRecursive: Boolean = false): Unit = {
    val fs = FileSystem.get(URI.create(HDFSPath), conf)
    fs.delete(new Path(HDFSPath), isRecursive)
    fs.close()
  }

  /**
    * append string text to exist file in HDFS, if the file not exist, create it first.
    *
    * @param HDFSPath   the path you want to append content
    * @param appendText the content you want to append
    */
  def appendContent(HDFSPath: String, appendText: String): Unit = {
    val fs = FileSystem.get(URI.create(HDFSPath), conf)
    val path = new Path(HDFSPath)

    if (fs.exists(path)) {
      val appendStream = fs.append(new Path(HDFSPath))
      appendStream.writeBytes(appendText)
      appendStream.close()
    } else {
      val stream = fs.create(path)
      stream.writeBytes(appendText)
      stream.close()
    }
    fs.close()
  }

  /**
    * rename a file
    *
    * @param sourcePath the original name
    * @param targetPath the new name
    */
  def move(sourcePath: String, targetPath: String): Unit = {
    val fs = FileSystem.get(URI.create(sourcePath), conf)
    val sp = new Path(sourcePath)
    val tp = new Path(targetPath)
    if (!fs.exists(sp)) println(s"Error: the source file cant find: $sourcePath")
    else if (fs.exists(tp)) println(s"Error: The target file exist already: $targetPath")
    else {
      fs.rename(sp, tp)
    }
    fs.close()
  }


  /**
    * show usages
    *
    * @return string
    */
  def usage: String = {
    """
      |A simple implementation of hdfs shell commands
      |  --copyFromLocal [local file name] [hdfs path]
      |    copy `local file name` to `hdfs path`
      |
      |  --cat [hdfs path]
      |    output HDFS file content to console
      |
      |  --get [hdfs path] [local path]
      |    get HDFS file to local file system.
      |
      |  --describe [hdfs path]
      |    show HDFS file content
      |
      |  --removeFile | -rmf [hdfs path]
      |    delete a HDFS file
      |
      |  --removeDir | -rmd [hdfs path]
      |    delete a HDFS dir
      |
      |  --append | -ap [hdfs path]
      |    append content to HDFS file
      |
      |  --move | -mv [source hdfs path] [target hdfs path]
      |    rename a HDFS file
      |
      |  --help | -h
      |    print this help message
      |
      |""".stripMargin
  }

  def funcGate(args: Array[String]): Unit = {
    Try(args.head match {
      case "--copyFromLocal" =>
        upload(filename = args(1), HDFSPath = args(2))
      case "--get" =>
        download(HDFSPath = args(1), downloadPath = args(2))
      case "--cat" =>
        cat(HDFSPath = args(1))
      case "--describe" =>
        describeFile(HDFSPath = args(1))
      case "--mkdir" =>
        mkDir(HDFSPath = args(1))
      case "--removeFile" | "-rmf" =>
        remove(HDFSPath = args(1))
      case "--removeDir" | "-rmd" =>
        remove(HDFSPath = args(1), isRecursive = true)
      case "--append" | "-ap" =>
        appendContent(HDFSPath = args(1), appendText = args(2))
      case "--move" | "-mv" =>
        move(sourcePath = args(1), targetPath = args(2))
      case "--help" | "-h" =>
        println(usage)
      case _ =>
        println(usage)
    }) match {
      case Success(_) => ()
      case Failure(exception) => println(exception)
    }

  }

  def main(args: Array[String]): Unit = {
    funcGate(args)
  }
}
