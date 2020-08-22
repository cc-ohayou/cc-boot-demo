package com.qunhe.toilet.facade.domain.common.util.log;

import org.slf4j.Logger;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/26 13:32.
 */
public abstract class BaseLog {

    public abstract Logger getLogger();

    public void warn(String line) {
        if (line != null) {
            this.getLogger().warn(line);
        }
    }



    public void error(String line) {
        if (line != null) {
            this.getLogger().error(line);
        }
    }

    public void info(String line) {
        if (line != null) {
            this.getLogger().info(line);
        }
    }


    public void warn(String line, Exception e) {
        if (line != null) {
            this.getLogger().warn(line, e);
        }
    }

    public void error(String line, Exception e) {
        if (line != null) {
            this.getLogger().warn(line, e);
        }
    }



}
