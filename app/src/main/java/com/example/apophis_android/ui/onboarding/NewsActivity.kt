package com.example.apophis_android.ui.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.CommentNews
import com.example.apophis_android.ui.onboarding.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*
import java.text.SimpleDateFormat
import java.util.*

class NewsActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var commentData: MutableList<CommentNews>
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val news = findViewById<VideoView>(R.id.vv_news_onboarding)
        //Video Uri
        val videoUri =
            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.onboarding)
        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        news.setMediaController(MediaController(this))
        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        news.setVideoURI(videoUri)
        //동영상을 읽어오는데 시간이 걸리므로..
        //비디오 로딩 준비가 끝났을 때 실행하도록..
        //리스너 설정

        news.start()

        newsAdapter = NewsAdapter(this)
        rcv_news.adapter = newsAdapter

        commentStart()


        val currentDateTime = Calendar.getInstance().time
        var time = SimpleDateFormat("HH:mm", Locale.KOREA).format(currentDateTime)

        commentData = mutableListOf(
                CommentNews(R.drawable.img_nickname_1, time, "선두리", "와 우리 코코는 어쩌지"),
                CommentNews(R.drawable.img_nickname_2, time, "이진수", "그럼 난 7일동안 뭐하지?"),
                CommentNews(R.drawable.img_nickname_3, time, "혜니포터", "나 아직 해리포터 못봤는데 ㅠ"),
                CommentNews(R.drawable.img_nickname_4, time, "지존세화", "나는 사과나무를 한그루 심겠어"),
                CommentNews(R.drawable.img_nickname_5, time, "곤구마", "헐ㅋㅋ 유튭각ㅋㅋ"),
                CommentNews(R.drawable.img_nickname_6, time, "안패트와매트", "너무 geek한 내용이라 안믿음"),
                CommentNews(R.drawable.img_nickname_2, time, "가토수연", "아직 틱톡 못찍었는데 망했다 ㅜ"),
                CommentNews(R.drawable.img_nickname_3, time, "바비", "7일 남았으니까 흑발해야지"),
                CommentNews(R.drawable.img_nickname_4, time, "착레", "삐빅-삐빅- 난 사실 레고다"),
                CommentNews(R.drawable.img_nickname_1, time, "52영재", "5252~ 다들 호들갑 떨지 말라구~"),
                CommentNews(R.drawable.img_nickname_6, time, "스무디두", "스무스하게 갑시다 여러분"),
                CommentNews(R.drawable.img_nickname_2, time, "최준♥유경", "지구가 망해도 최준은 영원해"),
                CommentNews(R.drawable.img_nickname_1, time, "다혜가 다해", "얼른 7일 게획 세우러 가야지"),
                CommentNews(R.drawable.img_nickname_4, time, "빵떡지훈", "피융 피융-! 겨빔 발사-!"),
                CommentNews(R.drawable.img_nickname_2, time, "코코", "아 사료 괜히 아껴먹었네"),
                CommentNews(R.drawable.img_nickname_3, time, "유경", "드르렁"),
                CommentNews(R.drawable.img_nickname_6, time, "공룡파출소", "뻥치시네"),
                CommentNews(R.drawable.img_nickname_5, time, "순소퓌", "은행 털러 가야겠다"),
                CommentNews(R.drawable.img_nickname_3, time, "아구몬선아", "죽기전에 내 발을 닦아야지"),
                CommentNews(R.drawable.img_nickname_1, time, "꽃다운스무살", "안돼 내 스무살!!"),
                CommentNews(R.drawable.img_nickname_3, time, "사랑꾼", "익명아 사랑해"),
                CommentNews(R.drawable.img_nickname_2, time, "오열중", "ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ"),
                CommentNews(R.drawable.img_nickname_5, time, "미쳐돌아간드아", "드디어 세상이 돌았군!!"),
                CommentNews(R.drawable.img_nickname_4, time, "사기꾼", "여러분 저거 뇌피셜입니다."),
                CommentNews(R.drawable.img_nickname_6, time, "먹방러", "지구멸망?? 그래도 난 먹죽"),
                CommentNews(R.drawable.img_nickname_1, time, "방랑자", "남은 일주일. 방황을 시작한다."),
                CommentNews(R.drawable.img_nickname_6, time, "인성파탄자", "홀리 ㅆㅟㅆ !!!!!"),
                CommentNews(R.drawable.img_nickname_5, time, "일본인", "어이가 아리마셍 입니다만?"),
                CommentNews(R.drawable.img_nickname_4, time, "영재52", "캐치마인드 딱 한판만 하자 응?"),
                CommentNews(R.drawable.img_nickname_3, time, "SOPT", "28기는 없는건가.."),
                CommentNews(R.drawable.img_nickname_2, time, "빛이나는솔로", "저 모쏠인데.. 실화냐구요 ㅠㅠ"),
                CommentNews(R.drawable.img_nickname_1, time, "사랑은아프다", "난 너밖에 없는데.. 이제 너마저 없겠구나"),
                CommentNews(R.drawable.img_nickname_2, time, "현지야사랑해", "현지야사랑해"),
                CommentNews(R.drawable.img_nickname_3, time, "이태원클라쓰", "술맛이 어떠냐..?"),
                CommentNews(R.drawable.img_nickname_5, time, "one", "버즈라이트 출동!"),
                CommentNews(R.drawable.img_nickname_6, time, "닥터슬럼프박사", "아리야 아포피스 막아라~"),
                CommentNews(R.drawable.img_nickname_4, time, "아리", "앞머리 망한김에 죽는것도.. 괜찮겠다")
        )

        //newsAdapter.commentList = commentData
        newsAdapter.notifyDataSetChanged()

        Handler().postDelayed({
            //method
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }, 45000)
    }
    private lateinit var second: TimerTask
    private val handler = Handler()
    val timer = Timer()
    fun commentStart() {

        second = object : TimerTask() {
            override fun run() {
                Update()
            }
        }
        timer.schedule(second, 0, 1200)
    }

    protected fun Update() {
        val updater = object : Runnable {
            override fun run() {
                if (index == commentData.size) {
                    timer.cancel()
                } else {
                    newsAdapter.addComment(commentData[index++])
                }
            }
        }
        handler.post(updater)
    }
}
