# 2대철이
<img src="https://github.com/Joanne19-drive/secondGenerationChull/assets/84649662/4ca5e069-b84c-4d71-aa1c-2566d6a3954c" width="500"/>

## 프로젝트 설명
* slack에서 사용하는 봇 만들기 ~~(1대철이의 폐기 이후 호기롭게 등장한 2대철이)~~
* slack bot의 `Event Subscriptions`를 연결하여 봇 이름 언급시 api에 post 요청
* `Incoming Webhooks`를 활용하여 응답 제공

### 주요 기능
* 역이름 입력시 해당 지하철역에 접근하고 있는 열차의 혼잡도 제공 (명령어: `@2대철이` 지하철 <지하철역명>)
* 중기 날씨 예보 제공 (명령어: `2대철이` 날씨 알려줘)
* 프리미어리그 팀이름 입력시 해당 팀의 가장 최근 경기 결과 및 다가올 경기 일정 제공 (명령어: `2대철이` <팀이름> 경기 결과 알려줘/경기 일정 알려줘)
* 그외 사사로운 토킹 가능(proactive)

### 기술 스택
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">

### 사용한 API
* <a href="https://openapi.sk.com/products/detail?svcSeq=54&menuSeq=249">SK OPEN API - 지하철 혼잡도</a>
* <a href="https://www.data.go.kr/data/15059468/openapi.do">공공데이터포털 - 기상청 중기 예보</a>
* <a href="https://www.football-data.org">football-data.org</a>
