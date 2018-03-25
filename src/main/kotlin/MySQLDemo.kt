import java.sql.*

object MySQLDemo {
    private const val JDBC_DRIVER = "com.mysql.jdbc.Driver"
    private const val DB_URL = "jdbc:mysql://你的服务器ip:3306/movie?useUnicode=true&autoReconnect=true&failOverReadOnly=false"
    private const val username = "数据库用户名"
    private const val password = "数据库密码"

    @Throws(SQLException::class)
    private fun getConnection(): Connection? {
        Class.forName(JDBC_DRIVER)
        return DriverManager.getConnection(DB_URL, username, password)
    }

    fun executeQuery(sql: String, dealResult: (rs: ResultSet?) -> Unit) {
        var connection: Connection? = null
        var stmt: Statement? = null
        var resultSet: ResultSet?
        try {
            connection = MySQLDemo.getConnection()
            connection?.let {
                stmt = connection.createStatement()
                resultSet = stmt?.executeQuery(sql)
                dealResult(resultSet)
                resultSet?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            stmt?.close()
            connection?.close()
        }
    }

    fun execute(vararg sql: String): Boolean{
        var connection: Connection? = null
        var stmt: Statement? = null
        var result = false
        try {
            connection = MySQLDemo.getConnection()
            connection?.let {
                stmt = connection.createStatement()
                sql.forEach { s ->
                    println(s)
                    try {
                        result = stmt?.execute(s) ?: false
                    } catch (e: SQLException){
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            stmt?.close()
            connection?.close()
        }
        return result
    }





}

