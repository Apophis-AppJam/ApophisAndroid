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

class NewsActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter

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

        newsAdapter.commentList = mutableListOf(
            CommentNews(R.drawable.img_nickname_1, "오후 8:13", "선두리", "와 우리 코코는 어쩌지"),
            CommentNews(R.drawable.img_nickname_2, "오후 8:13", "이진수", "그럼 난 7일동안 뭐하지?"),
            CommentNews(R.drawable.img_nickname_3, "오후 8:14", "혜니포터", "나 아직 해리포터 못봤는데 ㅠ"),
            CommentNews(R.drawable.img_nickname_4, "오후 8:14", "지존세화", "나는 사과나무를 한그루 심겠어"),
            CommentNews(R.drawable.img_nickname_5, "오후 8:14", "곤구마", "헐ㅋㅋ 유튭각ㅋㅋ"),
            CommentNews(R.drawable.img_nickname_6, "오후 8:14", "안패트와매트", "너무 geek한 내용이라 안믿음"),
            CommentNews(R.drawable.img_nickname_2, "오후 8:14", "가토수연", "아직 틱톡 못찍었는데 망했다 ㅜ"),
            CommentNews(R.drawable.img_nickname_3, "오후 8:15", "바비", "7일 남았으니까 흑발해야지"),
            CommentNews(R.drawable.img_nickname_4, "오후 8:15", "착레", "삐빅-삐빅- 난 사실 레고다"),
            CommentNews(R.drawable.img_nickname_1, "오후 8:15", "52영재", "5252~ 다들 호들갑 떨지 말라구~"),
            CommentNews(R.drawable.img_nickname_6, "오후 8:15", "스무디두", "스무스하게 갑시다 여러분"),
            CommentNews(R.drawable.img_nickname_2, "오후 8:15", "최준♥유경", "지구가 망해도 최준은 영원해"),
            CommentNews(R.drawable.img_nickname_1, "오후 8:16", "다혜가 다해", "얼른 7일 게획 세우러 가야지"),
            CommentNews(R.drawable.img_nickname_4, "오후 8:16", "빵떡지훈", "피융 피융-! 겨빔 발사-!"),
            CommentNews(R.drawable.img_nickname_2, "오후 8:16", "코코", "아 사료 괜히 아껴먹었네"),
            CommentNews(R.drawable.img_nickname_3, "오후 8:16", "유경", "드르렁"),
            CommentNews(R.drawable.img_nickname_6, "오후 8:16", "공룡파출소", "뻥치시네"),
            CommentNews(R.drawable.img_nickname_5, "오후 8:16", "순소퓌", "은행 털러 가야겠다"),
            CommentNews(R.drawable.img_nickname_3, "오후 8:17", "아구몬선아", "죽기전에 내 발을 닦아야지"),
            CommentNews(R.drawable.img_nickname_1, "오후 8:17", "마피아혜인", "일단 마피아게임 할 사람?"),
        )
        newsAdapter.notifyDataSetChanged()

        newsAdapter.addComment(CommentNews(R.drawable.img_nickname_1, "오후 8:17", "마피아혜인", "일단 마피아게임 할 사람?"))

        Handler().postDelayed({
            //method
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }, 44000)
    }
}
