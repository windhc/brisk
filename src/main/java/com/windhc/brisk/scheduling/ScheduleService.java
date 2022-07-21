package com.windhc.brisk.scheduling;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.windhc.brisk.exception.BriskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author windhc
 */
public final class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private ScheduleService() {
    }

    private static final ScheduleService scheduleService = new ScheduleService();

    public static ScheduleService instance() {
        return scheduleService;
    }

    public void addSchedule(final String cron, final Object object, final Method method) {
        log.info("add schedule {}", cron);
        CronUtil.schedule(cron, (Task) () -> {
            try {
                method.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BriskException(e);
            }
        });
        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        if (!CronUtil.getScheduler().isStarted()) {
            CronUtil.start();
        }
    }
}
