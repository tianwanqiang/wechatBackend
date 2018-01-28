package com.twq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class SerialUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialUtil.class);
    private static SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        batchRandom(10,50);
/*        LeftMoneyPackage left = new LeftMoneyPackage(1,1);
        for (int i = 0; i < 1; i++) {
            int amt = (int)(getRandomMoney(left) * 100) ;
            System.out.println(amt);
        }*/
//
//        System.out.println("返回一个定长的随机字符串(只包含大小写字母、数字):" + generateString(10));
//        System.out
//                .println("返回一个定长的随机纯字母字符串(只包含大小写字母):" + generateMixString(10));
//        System.out.println("返回一个定长的随机纯大写字母字符串(只包含大小写字母):"
//                + generateLowerString(10));
//        System.out.println("返回一个定长的随机纯小写字母字符串(只包含大小写字母):"
//                + generateUpperString(10));
//        System.out.println("生成一个定长的纯0字符串:" + generateZeroString(10));
//        System.out.println("根据数字生成一个定长的字符串，长度不够前面补0:"
//                + toFixdLengthString(123, 10));
//        int[] in = { 1, 2, 3, 4, 5, 6, 7 };
//        System.out.println("每次生成的len位数都不相同:" + getNotSimple(in, 3));
    }
    public static HashSet<Integer> batchRandom(int retSize, int allSize) {
        Random random = new Random();
        HashSet<Integer> set = new HashSet<Integer>();
        while(set.size()<=retSize){
            int randomNumber = random.nextInt(allSize);
            //System.out.println(randomNumber);
            set.add(randomNumber);
        }

        return set ;
    }
    /*以生成[10,20]随机数为例，首先生成0-20的随机数，然后对(20-10+1)取模得到[0-10]之间的随机数，然后加上min=10，最后生成的是10-20的随机数
        int max=20;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
     */
    //min<= result <=max
    public static int random(int min,int max) {
        if(min >= max)
            return min ;
        Random random = new Random();
        int result = random.nextInt(max)%(max-min+1) + min;
        return result;
    }



    //number 编号最大值
    //length 所需编号总数
    public static Integer[] randomArray(int number,int length) {
        List<Integer> ret = new ArrayList<Integer>();
        int serial ;
        while(true){
            serial = random(0,number) ;
            if(number>serial && !ret.contains(serial)){
                ret.add(serial) ;
                if(ret.size() == length)
                    break ;
            }
        }
        return ret.toArray(new Integer[length]);
    }



    /**
     * 生成ping++订单号后七位
     * @return
     */
    public static String randomString(int length) {
        String str = new BigInteger(130, random).toString(32);
        return str.substring(0, length);
    }
    /**
     * 生成USERID
     * @return
     */
    public static String getUserIdSerial() {
        return  getSerialFromFile("USERID", 15);
    }

    public static String getRPID() {
        return getSerialFromFile("RPID",14);
    }

    public static String getSerialFromFile(String serialName, int length) {
        long serialNo = Serial.next(serialName);
        return "8" + StringUtil.leftPad(Long.toString(serialNo), length, "0");
    }


    public static int fmanNo(int max){
        //(数据类型)(最小值+Math.random()*(最大值-最小值+1))
        return (int)(Math.random()*(max)) ;
    }

    public static String random4() throws IOException {
        Random ran=new Random();
        int r=0;
        m1:while(true){
            int n=ran.nextInt(10000);
            r=n;
            int[] bs=new int[4];
            for(int i=0;i<bs.length;i++){
                bs[i]=n%10;
                n/=10;
            }
            Arrays.sort(bs);
            for(int i=1;i<bs.length;i++){
                if(bs[i-1]==bs[i]){
                    continue m1;
                }
            }
            break;
        }

        if(r<1000)
            return "0"+r ;
        return r+"";
    }

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERCHAR = "0123456789";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    private static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
    public static String generateSessionId(){
        return generateString(32);
    }

    public static String generateString() {
        return generateString(32);
    }
    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }


}
