
data class Movie(
        val movieName: String = "",          //电影名字
        val translationName: String = "",    //译名
        val releaseTime: String = "",        //上映时间
        val producePlace: String = "",       //产地
        val subtitle: String = "",           //字幕
        val category: String = "",           //类别
        val introduction: String = "",       //简介
        var isDownload: String = "0",             //是否下载
        var cover: String = "",              //封面
        var magnet: String = ""              //磁力下载链接
        ): DataSupport(){

    companion object {
        private const val TRANSLATION_NAME = "译　　名"
        private const val MOVIE_NAME = "片　　名"
        private const val RELEASE_TIME = "上映日期"
        private const val CATEGORY = "类　　别"
        private const val SUBTITLE = "字　　幕"
        private const val PRODUCE_PLACE = "产　　地"
        private const val INTRODUCTION = "简　　介"
        const val DIVIDE_TAG = "◎"


        fun buildMovieByArrInfos(textArr: List<String>): Movie{
            var movieName = ""
            var translationName = ""
            var releaseTime = ""
            var productPlace = ""
            var subtitle = ""
            var category = ""
            var introduction = ""
            textArr.forEach {
                when {
                    it.startsWith(MOVIE_NAME) -> movieName = it.substring(MOVIE_NAME.length).trim()
                    it.startsWith(TRANSLATION_NAME) -> translationName = it.substring(TRANSLATION_NAME.length).trim()
                    it.startsWith(RELEASE_TIME) -> releaseTime = it.substring(RELEASE_TIME.length).trim()
                    it.startsWith(PRODUCE_PLACE) -> productPlace = it.substring(PRODUCE_PLACE.length).trim()
                    it.startsWith(SUBTITLE) -> subtitle = it.substring(SUBTITLE.length).trim()
                    it.startsWith(CATEGORY) -> category = it.substring(CATEGORY.length).trim()
                    it.startsWith(INTRODUCTION) -> introduction = it.substring(INTRODUCTION.length).trim()
                }
            }
            return Movie(
                    movieName = movieName,
                    translationName = translationName,
                    releaseTime = releaseTime,
                    producePlace = productPlace,
                    subtitle = subtitle,
                    category = category,
                    introduction = introduction
            )
        }
    }


}

