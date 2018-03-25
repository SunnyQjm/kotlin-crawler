import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.Reader

object KotlinShellUtil{
    fun Reader.easyClose(){
        try {
            this.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun execCmd(cmd: Array<String>, dir: File? = null): String {
        val result = StringBuilder()
        var process: Process? = null
        var bufIn: BufferedReader? = null
        var bufErr: BufferedReader? = null

        try {
            process = Runtime.getRuntime()
                    .exec(cmd, null, dir)

            process?.let {
                //等待进程执行完成
                it.waitFor()

                bufIn = BufferedReader(InputStreamReader(it.inputStream))
                bufErr = BufferedReader(InputStreamReader(it.errorStream))
                bufIn?.forEachLine {
                    result.append(it).append("\n")
                }
                bufErr?.forEachLine {
                    result.append(it).append("\n")
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            bufIn?.easyClose()
            bufErr?.easyClose()
            process?.destroy()
        }

        return result.toString()
    }
}


