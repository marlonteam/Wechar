package com.mazhe.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MSService {
    public static  String output="";
//    public static void main(String[] args) {
//       // String s=new String("Hello ");
//         String s="hello ";
//        update(s);
//        log.info(s);
//        foo(0);
//        foo(1);
//        log.info(output);
//        log.info(div(1,0)+"");
//        log.info(div(1,1)+"");
//    }

    public static void update(String s){
        s+="word";
    }

    public  static  void foo(int i){
        try {
            if(i==1){
                throw  new Exception();
            }
            output+="1";
            //return;

        }catch (Exception e){
            output+="2";
            return;
        }
        finally {
            output+="3";
        }
        output+="4";


    }

    public static int  div(int a,int b){
        try {
            return a/b;

        }catch (Exception e){
            return -1;
        }finally {
            return -2;
        }
        // return -7;

    }
}
