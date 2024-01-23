package com.kkyoungs.news.data

import com.kkyoungs.news.data.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.concurrent.Volatile
import kotlin.system.measureTimeMillis


class RemoteNewsData(): DataSource {

    companion object{
        /**
         * volatile 이란, 사전적 의미로는 휘발성의 라는 뜻을 가지며, 변수 선언할 때 volatile 을 지정하면 값을 메인 메모리에만 적재
         */
        @Volatile private var instance : RemoteNewsData? = null
        fun getInstance() = instance ?: synchronized(this){
            instance ?: RemoteNewsData().also {
                instance = it
            }
        }
    }

    /**
     * Dispatcher : 스레드에 코루틴을 보낸다.
     * 스래드  풀을 만들고 dispatcher을 통해 코루틴 배분.
     * 즉, 코루틴을 만든 다음 해당 코루틴을 dispatcher에 전송하면 dispatcher은 자신이 관리하는 스래드풀 내의 스레드의 부하 상황에 맞춰 코루틴 배분.
     * - Dispatchers.Main : Android 메인 스레드에서 코루틴을 실행하는 디스패처
     * - Dispatcher.IO : 디스크 또는 네트워크 작업을 실행하는데 최적화 되어있는 디스패처
     * - Dispatcher.Default : CPU를 많이 사용하는 작업을 기본 스레드 외부에서 실행하도록 최적화 되어있는 디스패처
     */
    override fun getAllNews(): Flow<News> = flow  {
        val time = measureTimeMillis {
            val googleResUrl = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
            val newUrls = getUrlsFromRss(googleResUrl)
            val newsAsync = mutableListOf<Deferred<News?>>()
            for (newsUrl in newUrls){
                CoroutineScope(Dispatchers.Default).async { getNewsFromUrl(newsUrl) }.also { newsAsync.add(it) }
            }
            for (newsDeferred in newsAsync){
                newsDeferred.await()?.let { emit(it) }
            }
        }
    }.flowOn(Dispatchers.IO)

    // 기사 url 로 부터 NEWS 추출
    private fun getNewsFromUrl(newsUrl : String) : News? {
        try {
            val doc by lazy {
                Jsoup.connect(newsUrl)
                    .timeout(1500)
                    .get()
                    .head()
            }
            val time = measureTimeMillis { doc }
            val title = doc.select("meta[property=og:title]").first()?.attr("content")?:doc.select("title").first().html()
            val image = doc.select("meta[property=og.image]").first()?.attr("content")?: ""
            val description = doc.select("meta[property=og:description]").first()?.attr("content")
                ?: doc.select("description").first().text()
                ?: doc.select("meta[name=description").attr("content")
            return News(newsUrl, title, image, description)
        }catch (e:Exception){
            e.printStackTrace()
            return null
        }
    }

    //rss 페이지로부터 기사 url 추출
    private fun getUrlsFromRss(rssUrl:String) : MutableList<String>{
        val newsUrl = mutableListOf<String>()
        //xml 파싱을 위한 xml parser
        val parser = Jsoup.connect(rssUrl).get().let { document ->
            val rssXML = document.html()
            val rssXmlStrReader = StringReader(rssXML)
            // XmlPullParserFactory을 사용하여 파싱을 진행
            // isNamespaceAware : 팩토리가 네임스페이스 인식 파서를 생성하도록 구성되었는지 여부를 나타냅니다
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware }
            factory.newPullParser().apply { setInput(rssXmlStrReader) }
        }
        // 기사 url 추출
        var eventType = parser.eventType
        var isNewsAddress = false
        while (eventType != XmlPullParser.END_DOCUMENT){
            when(eventType){
                XmlPullParser.START_TAG -> if (parser.depth >3 && parser.name == "link"){
                    isNewsAddress = true
                }
                XmlPullParser.TEXT -> if (isNewsAddress){
                    newsUrl.add(parser.text)
                    isNewsAddress = false
                }
            }
            /**
             * next()와 nextToken()이라는 두 가지 주요 메서드가 있습니다.
             * next()는 높은 수준의 구문 분석 이벤트에 대한 액세스를 제공하는 반면, nextToken()은 낮은 수준의 토큰에 대한 액세스를 허용
             */
            eventType = parser.next()
        }
        return newsUrl
    }

}