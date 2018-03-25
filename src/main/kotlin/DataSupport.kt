import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

open class DataSupport(var id: Int = 0) {

    companion object {
        fun <T : DataSupport> query(clazz: KClass<T>, where: String? = null,
                                    groupBy: String? = null, having: String? = null, orderBy: String? = null): MutableList<T> {
            val sp = StringBuilder()
            sp.append("SELECT * FROM ${clazz.simpleName?.toLowerCase()}")
            where?.let { sp.append(" WHERE $where") }
            groupBy?.let { sp.append(" GROUP BY $groupBy") }
            having?.let { sp.append(" HAVING $having") }
            orderBy?.let { sp.append(" ORDER BY $orderBy") }

            println(sp.toString())

            val resultList = mutableListOf<T>()
            MySQLDemo.executeQuery(sp.toString()) { rs ->
                while (rs?.next() == true) {
                    val t = clazz.java.newInstance()
                    clazz.declaredMemberProperties.forEach { prop ->
                        val filed = clazz.java.getDeclaredField(prop.name)
                        filed.isAccessible = true
                        filed.set(t, rs.getString(prop.name))
                    }
                    t.id = rs.getInt("id")
                    resultList.add(t)
                }
            }
            return resultList
        }


        /**
         * 插入操作
         */
        fun <T : DataSupport> insert(model: Array<T>, clazz: KClass<T>) {
            model.map {
                val sp = StringBuilder()
                sp.append("INSERT INTO ${clazz.simpleName?.toLowerCase()} (")
                clazz.declaredMemberProperties.forEach { p ->
                    sp.append("${p.name}, ")
                }
                sp.delete(sp.length - 2, sp.length - 1)
                sp.append(") VALUES (")
                clazz.declaredMemberProperties.forEach { p ->
                    sp.append("'${p.get(it)}', ")
                }
                sp.delete(sp.length - 2, sp.length - 1)
                sp.append(")")
                return@map sp.toString()
            }.let {
                //执行插入操作
                MySQLDemo.execute(*it.toTypedArray())
            }
        }


        fun <T : DataSupport> update(model: T, clazz: KClass<T>, where: String? = null) {
            val sp = StringBuilder()
            sp.append("UPDATE ${clazz.simpleName?.toLowerCase()} SET")
            clazz.declaredMemberProperties.forEach { p ->
                sp.append(" ${p.name} = '${p.get(model)}',")
            }
            sp.deleteCharAt(sp.length - 1)
            where?.let { sp.append(" WHERE $where") } ?: sp.append(" WHERE id=${model.id}")

            MySQLDemo.execute(sp.toString())
        }

        fun <T : DataSupport> delete(model: T, clazz: KClass<T>, where: String? = null) {
            val sp = StringBuilder()
            sp.append("DELETE FROM ${clazz.simpleName?.toLowerCase()}")
            where?.let { sp.append(" WHERE $it") } ?: sp.append(" WHERE id = " + model.id)

            MySQLDemo.execute(sp.toString())
        }

    }

    fun update(where: String? = null) {
        Companion.update(this, this.javaClass.kotlin, where)
    }

    fun save() {
        Companion.insert(arrayOf(this), clazz = this.javaClass.kotlin)
    }

    fun delete() {
        Companion.delete(this, this.javaClass.kotlin)
    }

}

fun main(args: Array<String>) {
    args.forEach(::println)
    val movies = DataSupport.query(Movie::class, "translationName = 'mdzz'")
    movies.forEach{
        it.delete()
    }
}

