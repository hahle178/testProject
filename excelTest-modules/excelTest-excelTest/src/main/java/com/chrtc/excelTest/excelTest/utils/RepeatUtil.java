package com.chrtc.excelTest.excelTest.utils;

import java.util.HashSet;
import java.util.Set;

public class RepeatUtil {
    Set<Integer> hashset=new HashSet();
    public boolean fileRepeat(String key){
        HashUtil hashUtil=new HashUtil();
        int hashcode=hashUtil.toHash(key);


        boolean hashboolean=false;

        if (hashset.isEmpty()){
            hashset.add(hashcode);
        }else{
            if (hashset.contains(hashcode)) {
                hashboolean=true;
                return hashboolean;
            }
            }
            hashset.add(hashcode);
        return  hashboolean;
    }

    public RepeatUtil() {
        hashset=new HashSet<Integer>();
    }
}




