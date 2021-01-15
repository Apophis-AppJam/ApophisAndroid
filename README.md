#  ApophisAndroid : *Never-Die-Zombieroid* 🌠🧛‍♀️

<br>



<img src="https://user-images.githubusercontent.com/63586451/104689431-d8683500-5745-11eb-8a57-5532602f7260.jpg" alt="프로필"/>

> SOPT 27기 17th APP-JAM : Apophis 🌠



<br>



### 📌 Part-meeting

------

> 매일 저녁 7시 30분

https://www.notion.so/b562e3c34a9e4641b5025506546260a0



<br> 

### 🎨 Kanban-board

------



https://github.com/Apophis-AppJam/ApophisAndroid/projects/1



<br>

### 🔧 Tools

------

- Android Studio 
- Zeplin
- Figma



<br>



### ✌ Communication tools

------

- Notion
- Slack
- Gather
- Zoom 


<br>



### 🧩 Branch naming

------

- feature : 기능 개발하는 브랜치

  **feature/[이슈번호] _ [기능] _ [layout/inflate]**



<br>

### 💬 Commit message

------



```
ex) [Add] 홈 화면 Layout 작성 완료

    1. 설명1
    2. 설명 2
```

<br>


[Add]         기능추가

[Delete]     삭제

[Update]   기능수정

[Fix]           버그수정

[Docs]       문서정리

[Chore]      잡일



<br>



### 🔗 Dependency

------

```
/* retrofit */
implementation 'com.squareup.retrofit2:retrofit:2.7.2'
implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'

/* gson */
implementation 'com.google.code.gson:gson:2.8.6'

/* glide */
implementation "com.github.bumptech.glide:glide:4.10.0"
kapt "com.github.bumptech.glide:compiler:4.10.0"

//lottie
implementation 'com.airbnb.android:lottie:3.4.0'

/* recyclerview */
implementation "androidx.recyclerview:recyclerview:1.2.0-alpha02"

/* kakao */
implementation 'com.kakao.sdk:usermgmt:1.28.0'

implementation platform('com.google.firebase:firebase-bom:26.2.0')
implementation 'com.google.firebase:firebase-analytics-ktx'
implementation 'com.google.firebase:firebase-auth:19.1.0'
implementation 'com.google.android.gms:play-services-auth:17.0.0'
compileOnly 'com.google.android.wearable:wearable:2.8.1'

/* camera */
def camerax_version = "1.0.0-beta07"
implementation "androidx.camera:camera-camera2:$camerax_version"
implementation "androidx.camera:camera-lifecycle:$camerax_version"
implementation "androidx.camera:camera-view:1.0.0-alpha14"
```




<br>




### 🧱 Project structure

------

```
🌠apophis_android
 ┣ 📂data
 ┃ ┣ 📂entity
 ┃ ┗ 📂remote
 ┃    ┣ 📂request
 ┃    ┗ 📂response
 ┗ 📂ui
   ┣ 📂firstDay
   ┃ ┗ 📂adapter
   ┣ 📂login
   ┣ 📂main
   ┃  ┣ 📂letter
   ┃ 📂onboarding
   ┃  ┗ 📂adapter
   ┣ 📂secondDay
   ┃ ┣ 📂adapter
   ┃ ┣ 📂findMe
   ┃ ┣ 📂time
   ┃ ┗ 📂value
   ┣ 📂seventhDay
   ┃ ┣ 📂adapter
   ┃ ┗ 📂tarot
   ┣ 📂sixthDay
   ┃ ┗ 📂adapter
   ┗ 📄ChipFactory.kt

  

```



<br>





### 🎵 Android developer & roles

------



|           👩 [김다혜](https://github.com/kimdahyee)           |           👨 [한재현](https://github.com/wogus0333)           |          👩 [조성림](https://github.com/CHOSUNGRIM)           |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/63586451/104689277-98a14d80-5745-11eb-9f92-77bb4dc2a470.jpg" alt="프로필" width="300px" /> | <img src="https://user-images.githubusercontent.com/63586451/104689261-90e1a900-5745-11eb-8bce-b32a6b463cd6.jpg" alt="프로필" width="300px" /> | <img src="https://user-images.githubusercontent.com/63586451/104689254-8f17e580-5745-11eb-93d1-654435cd19a2.jpg" alt="프로필" width="300px" /> |
|     스플래쉬<br/>음악 재생 <br/>메인<br/>편지 받기/쓰기      |                            온보딩                            |            아포피스 뷰 <br/>타이머<br />음성 송출            |
| 채팅 전체 로직 구현<br/>채팅 엔딩 뷰<br />2일차 - 채팅 구현<br />2일차 - 시간 설정<br />2일차 - Find me | 1일차 - 채팅 구현<br />1일차 - 나침반<br />1일차 - 카메라 촬영<br />1일차 / 2일차 - 백그라운드 이미지 전환 | 2일차 - 가치관<br />7일차 - 채팅 구현<br />7일차 - 유서 쓰기 |

<br>
