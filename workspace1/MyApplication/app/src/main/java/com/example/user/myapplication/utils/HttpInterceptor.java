package com.example.user.myapplication.utils;

import android.widget.Switch;

import com.okhttplib.HttpInfo;
import com.okhttplib.interceptor.ExceptionInterceptor;
import com.okhttplib.interceptor.ResultInterceptor;

/**
 * Created by user on 2017/5/2.
 */
public class HttpInterceptor {
    public static ResultInterceptor  resultInterceptor= new ResultInterceptor(){

        @Override
        public HttpInfo intercept(HttpInfo httpInfo) throws Exception {
            return httpInfo;
        }
    };

    public static ExceptionInterceptor exceptionInterceptor = new ExceptionInterceptor() {
        @Override
        public HttpInfo intercept(HttpInfo httpInfo) throws Exception {
            switch (httpInfo.getRetCode()){
                case HttpInfo.NonNetwork:
                    httpInfo.setRetDetail("网络中断");
                    break;
                case HttpInfo.CheckURL:
                    httpInfo.setRetDetail("网络地址错误["+httpInfo.getNetCode()+"]");
                    break;
                case HttpInfo.ProtocolException:
                    httpInfo.setRetDetail("协议类型错误["+httpInfo.getNetCode()+"]");
                    break;
                case HttpInfo.CheckNet:
                    httpInfo.setRetDetail("请检查网络连接是否正常["+httpInfo.getNetCode()+"]");
                    break;
                case HttpInfo.ConnectionTimeOut:
                    httpInfo.setRetDetail("连接超时");
                    break;
                case HttpInfo.WriteAndReadTimeOut:
                    httpInfo.setRetDetail("读写超时");
                    break;
                case HttpInfo.ConnectionInterruption:
                    httpInfo.setRetDetail("连接中断");
                    break;
            }
            return httpInfo;
        }
    };
}
