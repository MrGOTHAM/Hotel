package com.acg.hotel.util;

/*
    专门用来生成  api  URL的类
 */

public class ServiceUrls {

    public static final String SP_MEMBER_PHONE="memberPhone";
    public static final String SP_MEMBER_PASSWORD="memberPassword";


    private static String serviceUrl="http://192.168.0.104:8080/hotelService/";
//    private static String serviceUrl="http://10.0.2.2:8080/hotelService/";
//    private static String serviceUrl="http://10.0.2.2:8080/hotelService/";
    private static String urlPostfix=".do";

    /**
     * 获取 AppReserveController 方法的路径
     *
     * @param method 方法
     * @return url
     */
    public static String getReserveMethodUrl(String method){
        return serviceUrl+"reserve/"+method;
    }

    /**
     * 获取 AppMemberController 方法的路径
     *
     * @param method 方法名称
     * @return url
     */
    public static String getMemberMethodUrl(String method){
        return serviceUrl+"member/"+method;
    }

    /**
     * 获取 AppMainPageController 方法的路径
     *
     * @param method 方法名称
     * @return url
     */
    public static String getMainPageMethodUrl(String method){
        return serviceUrl+"mainPage/"+method;
    }

    public static String getAdvertisementController(String method){
        return serviceUrl+"advertisement/"+method;
    }

}
