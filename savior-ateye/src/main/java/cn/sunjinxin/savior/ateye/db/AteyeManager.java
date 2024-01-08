package cn.sunjinxin.savior.ateye.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunjinxin
 * @since 2024/1/8 16:51
 */
public enum AteyeManager {

    /**
     * db
     */
    DB;

    private static final List<AteyeView> ATEYE_DB = new ArrayList<>();

    public List<AteyeView> query() {
        return ATEYE_DB;
    }

}
