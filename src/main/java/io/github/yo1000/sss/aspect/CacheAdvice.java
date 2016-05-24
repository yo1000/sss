// src/main/java/io/github/yo1000/sss/aspect/CacheAdvice.java
package io.github.yo1000.sss.aspect;

import io.github.yo1000.sss.config.CacheConfiguration;
import io.github.yo1000.sss.model.Memo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAdvice.class);

    private CacheConfiguration.CacheStoreMap<String, Memo> cacheStoreMap;

    @Autowired
    public CacheAdvice(CacheConfiguration.CacheStoreMap<String, Memo> cacheStoreMap) {
        this.cacheStoreMap = cacheStoreMap;
    }

    @Around(value = "io.github.yo1000.sss.aspect.RepositoryPointcut.findOneByKey(key)")
    public Object cacheFind(ProceedingJoinPoint proceedingJoinPoint, String key) throws Throwable {
        LOGGER.info("getSignature().toLongString() " + proceedingJoinPoint.getSignature().toLongString());

        if (getCacheStoreMap().containsKey(key)) {
            LOGGER.info("Cache hit");
            return getCacheStoreMap().get(key);
        }

        LOGGER.info("Cache miss");
        Object returnValue = proceedingJoinPoint.proceed();

        if (!(returnValue instanceof Memo)) {
            return returnValue;
        }

        Memo memoValue = (Memo) returnValue;
        getCacheStoreMap().put(key, memoValue);

        return memoValue;
    }

    public CacheConfiguration.CacheStoreMap<String, Memo> getCacheStoreMap() {
        return cacheStoreMap;
    }
}
