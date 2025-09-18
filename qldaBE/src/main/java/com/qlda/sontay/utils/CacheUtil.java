package com.qlda.sontay.utils;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class CacheUtil {

    CacheManager cacheManager;

    private final String MAP_Token_UserDetail = "MAP_Token_UserDetail";

    public Map<String, UserDetails> getMapTokenUserDetail(){
        Cache cache = cacheManager.getCache(this.getClass().getName());
        if(cache != null){
            Cache.ValueWrapper valueWrapper = cache.get(this.MAP_Token_UserDetail);
            if(valueWrapper != null){
                return  (Map<String, UserDetails>) valueWrapper.get();
            }
        }
        Map<String, UserDetails> mapTokenUserDetail = new HashMap<>();
        if(cache != null){
            cache.put(this.MAP_Token_UserDetail, mapTokenUserDetail);
        }
        return mapTokenUserDetail;
    }

    // ham update cached cho MAP_Token_UserDetail
    public void updateMapTokenUserDetail(String token, UserDetails userDetails){
        try {
            Cache cache = cacheManager.getCache(this.getClass().getName());
            if(cache != null){
                Cache.ValueWrapper valueWrapper = cache.get(this.MAP_Token_UserDetail);
                if(valueWrapper != null){
                    Map<String, UserDetails> mapTokenUserDetail = (Map<String, UserDetails>) valueWrapper.get();
                    if(mapTokenUserDetail != null){
                        mapTokenUserDetail.put(token, userDetails);
                        cache.put(this.MAP_Token_UserDetail, mapTokenUserDetail);
                    }
                }
            }
        }catch (Exception ex){
        }
    }
}
