package com.colorPicker;

/**
 * Created by aiden on 2017-06-14.
 */
public class testfinal {
    public static void main(String args[]) {
        ColorPickerMod test;
        String filepath = "C:\\Users\\aiden\\IdeaProjects\\toneMeUp\\img\\example.png";
        if (filepath != null) {
            test = new ColorPickerMod(filepath);
            System.out.println("성공");
        } else
            test = new ColorPickerMod();// 생성자는 기본, 스트링(파일패스), 파일, 버퍼 이미지 4가지로 생성 가능하다.

        //test.returnTone(); // 0 혹은 1을 리턴한다.

        switch (test.returnSeasonTone()) {

            case 1:
                System.out.println("봄" + test.tempReturnSeasonTone());
                break;

            case 2:
                System.out.println("여름" + test.tempReturnSeasonTone());
                break;

            case 3:
                System.out.println("가을" + test.tempReturnSeasonTone());
                break;

            case 4:
                System.out.println("겨울" + test.tempReturnSeasonTone());
                break;
            case 0:
                System.out.println("없음" + test.tempReturnSeasonTone());
                break;
        }

    }
}
