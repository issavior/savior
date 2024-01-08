package cn.sunjinxin.savior.ateye.res;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author sunjinxin
 * @since 2024/1/8 18:47
 */
public class AteyeRes extends ResponseEntity<Object> {

    private AteyeRes(Object body) {
        super(body, HttpStatus.OK);
    }

    public static AteyeRes of(Object body) {
        return new AteyeRes(body);
    }

}
