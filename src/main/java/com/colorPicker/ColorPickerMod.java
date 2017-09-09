package com.colorPicker;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Created by aiden on 2017-04-27.
 */
public class ColorPickerMod extends Component {

    File imgFile = new File("C:\\Users\\kinso\\workspace\\ToneMeUp_Final\\WebContent\\image\\example.png"); // 임시 파일.
    BufferedImage img;
    int pointX = 0; //색추출하는 포인트의 디폴트 값
    int pointY = 0;
    public ColorPickerMod(File imgFile, int pointX, int pointY){
        this.imgFile = imgFile;
        this.pointX = pointX;
        this.pointY = pointY;
        try{
            this.loadImg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ColorPickerMod(File imgFile){
        this.imgFile = imgFile;
        this.pointX = 0;
        this.pointY = 0;

        try{
            this.loadImg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ColorPickerMod(String filePath){
        this.imgFile = new File(filePath);
        this.pointX = 0;
        this.pointY = 0;

        try{
            this.loadImg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ColorPickerMod() {
        try {
            img = ImageIO.read(imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// 임시
    public ColorPickerMod(BufferedImage img) {
        this.img = img;

    }

    public void loadImg() throws IOException {
        img = ImageIO.read(imgFile);
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null); //그림을 그리는 메소드
    }
    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100,100); //그림 파일이 없을 경우 프레임의 크기를 100,100으로 만든다
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null)); //그림의 크기에 따라 화면의 크기를 변경한다.
        }
    }

    public Color getColor(BufferedImage img){
        Color pointColor = new Color(img.getRGB(pointX,pointY));

        return pointColor;
    }   // 버퍼 이미지를 받아 pointXY의 RGB를 컬러 객체로 반환.

    public Color getAvgColor(){ // 평균 값
        int sumR = 0;
        int sumG = 0;
        int sumB = 0;

        for (int i = 0; i < img.getHeight(); i += 1) {
            for (int y = 0; y < img.getWidth(); y += 1) {
                Color colors = new Color(img.getRGB(y, i));
                sumR += colors.getRed();
                sumG += colors.getGreen();
                sumB += colors.getBlue();
            }
        }
        int avgR = sumR/(img.getHeight()*img.getWidth());
        int avgG = sumG/(img.getHeight()*img.getWidth());
        int avgB = sumB/(img.getHeight()*img.getWidth());

        return new Color(avgR,avgG,avgB);
    }
    public Color getCenterColor(){
        int centerX = img.getWidth()/2;
        int centerY = img.getHeight()/2;
        Color pointColor = new Color(img.getRGB(pointX,pointY));
        return pointColor;
    }   // 버퍼 이미지를 받아 pointXY의 RGB를 컬러 객체로 반환.
    public Color getPerColor(double perX, double perY) throws IOException {
        Color pointColor = new Color(img.getRGB((int)(perX*(img.getWidth()/100)),(int)(perY*(img.getHeight()/100))));
        return pointColor;
    }   // 버퍼 이미지를 받아 pointXY의 RGB를 컬러 객체로 반환.
    public int getSeasonTone(Color color){                          // 봄 1 여름 2 가을 3 겨울4
        int resultTone = 0;
        if(color.getRed() + color.getGreen() + color.getBlue()>100)
            resultTone = 1;
        else resultTone = 2;                                        // 수식 예시
        return  resultTone;
    }
    public static double[] RGBtoHSV(double r, double g, double b){

        double h, s, v;

        double min, max, delta;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);

        // V
        v = max;

        delta = max - min;

        // S
        if( max != 0 )
            s = delta / max;
        else {
            s = 0;
            h = -1;
            return new double[]{h,s,v};
        }

        // H
        if( r == max )
            h = ( g - b ) / delta; // between yellow & magenta
        else if( g == max )
            h = 2 + ( b - r ) / delta; // between cyan & yellow
        else
            h = 4 + ( r - g ) / delta; // between magenta & cyan

        h *= 60;    // degrees

        if( h < 0 )
            h += 360;

        return new double[]{h,s,v};
    }
    public int convertWormCool(Color toneColor){
        double toneR = 0;
        double toneG = 0;
        double toneB = 0;
        double Z = 0;
        int resultTone = 0;

        toneR = toneColor.getRed();
        toneG = toneColor.getGreen();
        toneB = toneColor.getBlue();
        double[] toneHSV = RGBtoHSV(toneR,toneG,toneB);

        System.out.println("rgbhsv:"+toneR+" , "+toneG+" , "+toneB+" , "+toneHSV[0]+" , "+toneHSV[1]+" , "+toneHSV[2]);

        toneR = standardization(toneR,227,46);
        toneG = standardization(toneG,218,48);
        toneB = standardization(toneB,222,0);
        toneHSV[0] = standardization( toneHSV[0],359,0);
        toneHSV[1] = standardization(toneHSV[1],100,0);
        toneHSV[2] = standardization(toneHSV[2],89,22);

/*            *//*Z=-245.075*x1+314.311*x2-309.514*x3+148.119*x4+39.137*x5+130.140*x6-1.675*//*
        Z =-245.075*toneHSV[0]+314.311*toneHSV[1]-309.514*toneHSV[2]+148.119*toneR+39.137*toneG+130.140*toneB-1.675;*/
        Z = -4.221*toneHSV[0] + 2.139*toneHSV[1] - 2.780*toneB + 2.582;
        System.out.println("쿨웜: "+ Math.exp(Z)/(1+Math.exp(Z))+" ,   " + Math.exp(Z)+" ,   " + Z );
            /*pi=exp(Z)/(1+exp(Z))*/
        if((Math.exp(Z)/(1+Math.exp(Z)))>0.5)
            resultTone = 1;
        else resultTone = 0;

        return resultTone;
    }
    public int tempConvertWormCool(){
        return 1;
    }
    public int returnTone(){
        final int howManyTime = 5; //25번을 검사한다

        final double whatPerGetW = 25;
        final double whatPerGetH = 50;

        int[] results = new int[howManyTime^2];
        int result = 0;

        int startPerW = (int)(whatPerGetW - howManyTime/2);
        int startPerH = (int)(whatPerGetH - howManyTime/2);

        for(int i = 0; i < howManyTime;i++){
            for(int j = 0;j < howManyTime;j++){
                try {
                    results[i + j] = convertWormCool(getPerColor(startPerW + i, startPerH + j));
                }catch (Exception e){

                }
            }
        }

        double total = 0;
        for(int c = 0;c < (howManyTime^2);c++){
            total = total + results[c];
        }
        result = (int)(total /(howManyTime^2));

        return result;
    }
    /*/////////////////////////////////////////////////          ver 0.2              //////////////////////////////////////////////////////////////*/
    public int returnSeasonTone(){
        final int howManyTime = 5; //25번을 검사한다

        final double whatPerGetW = 25;
        final double whatPerGetH = 50;

        int[] results = new int[howManyTime^2];
        int result = 0;

        int spring = 0;
        int summer = 0;
        int fall = 0;
        int winter = 0;

        int startPerW = (int)(whatPerGetW - howManyTime/2);//22
        int startPerH = (int)(whatPerGetH - howManyTime/2);//47

        for(int i = 0; i < howManyTime;i++){
            for(int j = 0;j < howManyTime;j++){
                try {
                    result = convertSeason(getPerColor(startPerW + i, startPerH + j));
                }catch (Exception e){
                    return 0;
                }
                if(result == 1)
                    spring++;
                else if(result==2)
                    summer++;
                else if(result==3)
                    fall++;
                else winter++;
            }
        }

        if(spring >= summer && spring >= fall && spring >= winter)
            return 1;
        else if(summer >= fall && summer>= winter)
            return 2;
        else if(fall >= winter)
            return 3;
        else return 4;

    }
    public int convertSeason(Color toneColor){              // 봄여름가을겨울 1234
        double toneR = 0;
        double toneG = 0;
        double toneB = 0;
        double Z = 0;
        int resultTone = 0;
        try {


        toneR = toneColor.getRed();
        toneG = toneColor.getGreen();
        toneB = toneColor.getBlue();
        double[] toneHSV = RGBtoHSV(toneR,toneG,toneB);

        toneR = standardization(toneR,227,46);
        toneG = standardization(toneG,218,48);
        toneB = standardization(toneB,222,0);
        toneHSV[0] = standardization( toneHSV[0],359,0);
        toneHSV[1] = standardization(toneHSV[1],100,0);
        toneHSV[2] = standardization(toneHSV[2],89,22);

        if(convertWormCool(toneColor) == 0) {
/*            *//*0.053*x1-2.042*x2+9.435*x3-4.699*x4+1.384*x5-5.040-23.194*x7+0.392*//*
            Z = 0.053 * toneHSV[0] - 2.042 * toneHSV[1] + 9.435 * toneHSV[2] - 4.699 * toneR + 1.384 * toneG - 5.040 * toneB - 23.194 * 0 + 0.392;*/
            Z = 3.047 * toneB -20.985 * 0 -1.216;
            System.out.println("쿨시즌: "+ Math.exp(Z)/(1+Math.exp(Z))+" ,   " + Math.exp(Z)+" ,   " + Z);
            if((Math.exp(Z)/(1+Math.exp(Z)))<0.5)
                resultTone = 4;
            else resultTone = 2;
        }
        else{
            /*Z = -10.459*toneHSV[0]-3.175*toneHSV[1]+19.512*toneHSV[2]-15.931*toneR+0.981*toneG-5.296*toneB+18.724*1-17.056;
*/          Z = 2.713*toneR + 21.203 * 1 - 22.638;
            System.out.println("웜시즌: "+ Math.exp(Z)/(1+Math.exp(Z))+" ,   " + Math.exp(Z)+" ,   " + Z);
            if((Math.exp(Z)/(1+Math.exp(Z)))<0.5)
                resultTone = 3;
            else resultTone = 1;
        }
    }catch (Exception e) {
        return 0;
    }
        return resultTone;
    }
    /*/////////////////////////////////////////////////          ver 0.2              //////////////////////////////////////////////////////////////*/
    public String tempReturnSeasonTone(){

            final int howManyTime = 5; //25번을 검사한다

            final double whatPerGetW = 25;
            final double whatPerGetH = 50;

            int[] results = new int[howManyTime ^ 2];
            int result = 0;

            int spring = 0;
            int summer = 0;
            int fall = 0;
            int winter = 0;

            int startPerW = (int) (whatPerGetW - howManyTime / 2);
            int startPerH = (int) (whatPerGetH - howManyTime / 2);

            for (int i = 0; i < howManyTime; i++) {
                for (int j = 0; j < howManyTime; j++) {
                    try {
                        result = convertSeason(getPerColor(startPerW + i, startPerH + j));
                    }catch(Exception e){
                        return "";
                    }
                    if (result == 1)
                        spring++;
                    else if (result == 2)
                        summer++;
                    else if (result == 3)
                        fall++;
                    else winter++;
                }
            }

            return spring + "," + summer + "," + fall + "," + winter;
    }
    /*/////////////////////////////////////////////////          ver 0.3              //////////////////////////////////////////////////////////////*/
    public double standardization(double value, double max, double min){
        return (value - min)/(max-min);
    }
}
/// 완료