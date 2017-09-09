package com.sweettracker.controller;

import com.colorPicker.ColorPickerMod;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/**
 * Created by annakim on 7/6/17.
 */
@RestController
public class ZoommaController {

    // 키보드
    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public String keyboard() {

        System.out.println("/keyboard");

        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "text");

        return jobjBtn.toJSONString();
    }

    /*// 메세지
    @RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String message(@RequestBody JSONObject resObj) {

        System.out.println("/message");
        System.out.println(resObj.toJSONString());

        String content;
        content = (String) resObj.get("content");
        JSONObject jobjRes = new JSONObject();
        JSONObject jobjText = new JSONObject();

        // 사용자 구현
        content = (String) resObj.get("content");
        if(content.contains("안녕")){
            jobjText.put("text","안녕 하세요");
        } else if(content.contains("사랑")){
            jobjText.put("text","나도 너무너무 사랑해");
        } else if(content.contains("잘자")){
            jobjText.put("text","꿈 속에서도 너를 볼꺼야");
        } else if(content.contains("졸려")){
            jobjText.put("text","졸리면 언능 세수하러 가용!");
        } else if(content.contains("시간")||content.contains("몇 시")){
            jobjText.put("text","섹시");
        } else {
            jobjText.put("text","흠... 아직 지정해 두지 않은 말인걸.");
        }

        jobjRes.put("message", jobjText);
        System.out.println(jobjRes.toJSONString());

        return  jobjRes.toJSONString();
    }*/
    @RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String message(@RequestBody JSONObject resObj) {

        System.out.println("/message");
        System.out.println(resObj.toJSONString());

        String content;
        content = (String) resObj.get("content");
        JSONObject jobjRes = new JSONObject();
        JSONObject jobjText = new JSONObject();

        // 사용자 구현
        ColorPickerMod test = null;
        String filepath = "";
        if((String) resObj.get("type")=="photo")
            filepath = (String) resObj.get("content");
        if (filepath != null) {
            try {
                URL url = new URL(filepath);
                BufferedImage img = ImageIO.read(url);
                test = new ColorPickerMod();
                System.out.println("성공");
            }catch (IOException e) {
                e.printStackTrace();
            }

        } else
            test = new ColorPickerMod();// 생성자는 기본, 스트링(파일패스), 파일, 버퍼 이미지 4가지로 생성 가능하다.

        //test.returnTone(); // 0 혹은 1을 리턴한다.
        String msgText="";
        switch (test.returnSeasonTone()) {

            case 1:
                jobjText.put("text","봄" + test.tempReturnSeasonTone());
                break;

            case 2:
                jobjText.put("text","여름" + test.tempReturnSeasonTone());
                break;

            case 3:
                jobjText.put("text","가을" + test.tempReturnSeasonTone());
                break;

            case 4:
                jobjText.put("text","겨울" + test.tempReturnSeasonTone());
                break;
            case 0:
                jobjText.put("text","없음" + test.tempReturnSeasonTone());
                break;
        }



        jobjRes.put("message", jobjText);
        System.out.println(jobjRes.toJSONString());

        return  jobjRes.toJSONString();
    }
}
