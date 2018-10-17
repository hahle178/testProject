package com.chrtc.excelTest.excelTest.utils;

import java.util.HashSet;
import java.util.Set;

public class RepeatUtil {
    private  HashUtil hashUtil;
    public boolean fileRepeat(String key){
        int hashcode=hashUtil.toHash(key);
        Set<Integer> hashset=new HashSet();
        boolean hashboolean=false;

        if (hashset.isEmpty()){
            hashset.add(hashcode);
        }else{
            if (hashset.contains(hashcode)) {
                return hashboolean=true;
            }
            }
        return  hashboolean;
    }

    }




