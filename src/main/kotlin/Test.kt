import org.jsoup.Jsoup

object DefaultConfig{
    const val BASE_URL = "http://www.ygdy8.net"
    const val NEW_MOVIE_URL = BASE_URL
}

///html/gndy/dyzz/index.html

fun main(args: Array<String>) {
    if(args.isEmpty()){
        println("arguments error")
        println("usage: ./dyttGet <plusUrl>")
        return
    }
    var doc = Jsoup.connect("${DefaultConfig.BASE_URL}${args[0]}").get()
    val links = doc.getElementsByClass("ulink")

    println(links)
    links.map {
        doc = Jsoup.connect("${DefaultConfig.BASE_URL}${it.attr("href")}").get()

        val zoom = doc.getElementById("Zoom")
        val textArr = zoom.text().split(Movie.DIVIDE_TAG)

        val movie = Movie.buildMovieByArrInfos(textArr = textArr)
        //获得磁力链接
        val magnet = zoom.selectFirst("a[href~=magnet(.*?)]")
        movie.magnet = magnet.attr("href")

        //获得电影封面
        val cover = zoom.selectFirst("img[src]")
        movie.cover = cover.attr("src")
        movie
    }.let {
        DataSupport.insert(it.toTypedArray(), clazz = Movie::class)
    }

}