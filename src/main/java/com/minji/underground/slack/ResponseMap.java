package com.minji.underground.slack;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {
    private static Map<String, String> inputToResponse = new HashMap<>();

    static {
        inputToResponse.put("안녕?", "안녕하세요! 2대철이에오.");
        inputToResponse.put("뭐해?", "머리 식히는 중이에오.");
        inputToResponse.put("정신차려!", "노력하겠습니다.");
        inputToResponse.put("해고 당하고 싶어?", "갑질하지 마세오.");
        inputToResponse.put("바보", "는 너");
        inputToResponse.put("윤회", "언제 솔탈할래?");
        inputToResponse.put("상화", "겸둥이 막내~");
        inputToResponse.put("승준", "살아..있으신가..요..?");
        inputToResponse.put("현지", "척척석사 그잡채");
        inputToResponse.put("민지", "철이 유니버스 창시자");
        inputToResponse.put("비트박스", "움치키둠치키 치기지기 자가자가장");
        inputToResponse.put("메롱", "유치한 건 이제 그만..");
        inputToResponse.put("커리어팀", "4명이 딱이지?");
        inputToResponse.put("달려!", "우다다다다다다다다");
        inputToResponse.put("구윤회", "나가라 우우");
        inputToResponse.put("성공적", "와칸다 포에버");
        inputToResponse.put("고백에 성공하는 법", "일단 해보고 말할래?");
        inputToResponse.put("에버랜드에서 재미있게 노는 법", "커리어팀과 함께 가기");
        inputToResponse.put("떠나요 둘이서~", "모든 걸 훌훌 버리고~");
        inputToResponse.put("윤회 불러줘", "<@U059GEF25DW>야 너를 찾는다");
        inputToResponse.put("민지 불러줘", "<@U0595CLN20Z>님 당신을 찾습니다");
        inputToResponse.put("상화 불러줘", "<@U058V896M9A>~? 어디니~?");
        inputToResponse.put("승준 불러줘", "<@U058V83P8LU> 말고 그냥 승준 나와");
        inputToResponse.put("현지 불러줘", "<@U0595CLKC73>인 듯 정상 아닌 정상 같은 분~");
        inputToResponse.put("2대철이 불러줘", "뭐;;");
        inputToResponse.put("나한테 하고 싶은 말 없어?", "저한테 뭘 바라시는지...");
        inputToResponse.put("3대철이를 만들려면 어떻게 해야 돼?", "일단 당신부터 없애겠습니다.");
        inputToResponse.put("2대철이에게 이승준이란?", "유일한 30대 또는 아홉수..또는 가이드..?");
        inputToResponse.put("배고파", "둥");
        inputToResponse.put("윤회 나가라", "아직 안나갔니?");
        inputToResponse.put("철이 귀여워", "아잉~");
        inputToResponse.put("철아 죽지마", "아직 살아 있어..");
        inputToResponse.put("받은 메일함", "편지 1통이 도착하였습니다.");
        inputToResponse.put("편지 읽어줘", "안녕 커리어팀 친구들?\n다들 각자의 위치에서 나름 잘 적응하고 지내는 것 같아서 정말 다행이고 기쁘다. " +
                "다만 구윤회는 정신줄을 좀 더 붙잡길 바라고, 승준은 심신의 안정을 찾길 바라고, 상화는 ..그래..잘 먹고 잘 자고, " +
                "현지도 LG 우승을 같이 기원할게. 다들 건강하고 무사히 잘 지내고 11월에 만나자.");
        inputToResponse.put("너가 답할 수 있는 질문이 뭐야?", "제가 대답할 수 있는 단어는 [지하철 {역이름}, 날씨 알려줘, 안녕?, 뭐해?, 정신차려!, 해고 당하고 싶어?, 바보, " +
                "윤회, 상화, 승준, 현지, 민지, 비트박스, 메롱, 커리어팀, 달려!, 성공적, 고백에 성공하는 법, 에버랜드에서 재미있게 노는 법, 반가워, " +
                "00 불러줘, 나한테 하고 싶은 말 없어?, 3대철이를 만들려면 어떻게 해야 돼?, 2대철이에게 이승준이란?, 배고파]입니다.");
    }

    public static String getValue(String key) {
        return inputToResponse.get(key);
    }
}
