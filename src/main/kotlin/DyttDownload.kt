import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    var tgetPath = "tget"
    var savePath = "."
    var limit = -1
    val config = File("./DyttDownload.cfg")
    println(config.absolutePath)
    if(!config.exists()){
        println("配置文件不存在，请在bin目录下创建名为DyttDownload.cfg的配置文件")
        return
    }
    if(config.exists()){
        config.readLines()
                .forEach{
                    when {
                        it.startsWith("tgetPath=") -> tgetPath = it.substring("tgetPath=".length, it.length)
                        it.startsWith("savePath=") -> savePath = it.substring("savePath=".length, it.length)
                        it.startsWith("limit=") -> limit = it.substring("limit=".length, it.length).toInt()
                    }
                }
    }

    val threadExector = ThreadPoolExecutor(10, 10, 1, TimeUnit.HOURS,
            LinkedBlockingQueue())
    var where = "isDownload=0"
    if(limit > 0){
        where += " limit $limit"
    }
    DataSupport.query(Movie::class, where)
            .forEach{
                threadExector.execute{
                    println("=================== begin download =====================")
                    KotlinShellUtil.execCmd(arrayOf(tgetPath, it.magnet), File(savePath))
                    it.isDownload = "1"
                    it.update()
                    println("=================== download finish =====================")
                }
            }
}