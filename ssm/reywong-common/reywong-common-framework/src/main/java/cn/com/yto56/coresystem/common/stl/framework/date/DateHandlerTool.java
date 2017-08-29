package cn.com.yto56.coresystem.common.stl.framework.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 处理日期的工具，统一pattern格式的字符串，返回pattern格式的字符串</br>
 *
 * @author reywong
 * @version 1.0
 */
public class DateHandlerTool {
    /**
     * SECOND:秒
     */
    public static final int SECOND = 6;
    /**
     * MINUTE:分
     */
    public static final int MINUTE = 5;
    /**
     * HOUR:时
     */
    public static final int HOUR = 4;

    /**
     * DAY:天
     */
    public static final int DAY = 3;
    /**
     * WEEK:周
     */
    public static final int WEEK = 2;
    /**
     * MONTH:月
     */
    public static final int MONTH = 1;
    /**
     * SUNDAY:星期天
     */
    public static final int SUNDAY = Calendar.SUNDAY;
    /**
     * MONDAY:星期一
     */
    public static final int MONDAY = Calendar.MONDAY;
    /**
     * FEBRUARY:星期二
     */
    public static final int TUESDAY = Calendar.TUESDAY;
    /**
     * WEDNESDAY:星期三
     */
    public static final int WEDNESDAY = Calendar.WEDNESDAY;
    /**
     * THURSDAY:星期四
     */
    public static final int THURSDAY = Calendar.THURSDAY;
    /**
     * FRIDAY:星期五
     */
    public static final int FRIDAY = Calendar.FRIDAY;
    /**
     * SATURDAY:星期六
     */
    public static final int SATURDAY = Calendar.SATURDAY;

    /**
     * 取得给定时间一个时间间隔后是周几</br>
     *
     * @param currentDate 给定格时间
     * @param num         时间间隔 正数为给定时间以后，负数为给定时间以前
     * @param pattern     数据格式
     * @return 返回周几，可用该类提供的常量来判断
     */
    public static int getWeekNum(String currentDate, int num, String pattern) {
        int result = 0;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_WEEK, num);
        result = c.get(Calendar.DAY_OF_WEEK);
        return result;
    }

    /**
     * 给定时间按天为间隔获得日期
     *
     * @param currentDate 给定时间
     * @param num         正数为给定时间之后，负数为给定时间之前
     * @return 返回时间
     */
    public static String getDay(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 获取两个时间直接间隔列表包括给定的两个时间格式为（pattern）,时间不分先后</br>
     * 举例：getDateList("2012-01-01","2012-04-30",DateHandlerTool.WEEK,2)
     * 2012年1月1日到2012年4月30日中从2012年1月1日开始每隔两周的所有yyyy-MM-dd格式日期列表
     *
     * @param oneDate   pattern格式的时间
     * @param otherDate pattern格式的另一个时间
     * @param type      为间隔的类型 如 天，周，月
     * @param num       为间隔的幅度
     * @param pattern   数据格式
     * @return 返回一个格式为yyyy-MM-dd的时间列表
     */
    public static List<String> getDateList(String oneDate, String otherDate, int type, int num, String pattern) {
        List<String> result = new ArrayList<String>();
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        num = Math.abs(num);
        try {
            startDate = sdf.parse(oneDate);
            endDate = sdf.parse(otherDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        long startMillis = startCalendar.getTimeInMillis();
        long endMillis = endCalendar.getTimeInMillis();
        int days = 0;
        if (startMillis > endMillis) {
            startCalendar.setTime(endDate);
            endCalendar.setTime(startDate);
        }
        result.add(sdf.format(startCalendar.getTime()));
        while (startCalendar.before(endCalendar)) {
            if (type == DateHandlerTool.DAY) {
                startCalendar.add(Calendar.DAY_OF_YEAR, num);
            } else if (type == DateHandlerTool.WEEK) {
                startCalendar.add(Calendar.WEEK_OF_YEAR, num);
            } else if (type == DateHandlerTool.MONTH) {
                startCalendar.add(Calendar.MONTH, num);
            }
            if (!startCalendar.after(endCalendar)) {
                result.add(sdf.format(startCalendar.getTime()));
            }
        }
        return result;
    }

    /**
     * 获取两个时间间隔（不分先后）除去星期六和星期天的日期列表 </br>
     *
     * @param oneDate   格式为pattern的时间
     * @param otherDate 格式为pattern的时间
     * @return 返回一个时间列表
     */
    public static List<String> getWorkDay(String oneDate, String otherDate, String pattern) {
        List<String> result = new ArrayList<String>();
        List<String> tmp = DateHandlerTool.getDateList(oneDate, otherDate, DateHandlerTool.DAY, 1, pattern);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        for (String str_date : tmp) {
            int num = DateHandlerTool.getWeekNum(str_date, 0, pattern);
            if (num == 1 || num == 7) {
                continue;
            }
            result.add(str_date);
        }
        return result;
    }

    /**
     * 给定时间按周间隔获取时间</br>
     * 如：getWeek("2012-01-01",-1,"yyyy-MM-dd") 返回2012年1月1号前一个星期时间</br>
     *
     * @param currentDate 给定格式为pattern的时间
     * @param num         时间间隔正数为以后 负数为以前
     * @return 返回pattern格式的时间
     */
    public static String getWeek(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_MONTH, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 给定时间按月间隔获取时间</br>
     * 如：getMonth("2012-01-01",-1,"yyyy-MM-dd") 返回2012年1月1号前一个月时间 </br>
     *
     * @param currentDate 给定格式为pattern的时间
     * @param num         时间间隔正数为以后 负数为以前
     * @return 返回yyyy-MM-dd格式的时间
     */
    public static String getMonth(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 相差小时
     *
     * @param currentDate
     * @param num
     * @param pattern
     * @return
     */
    public static String getHour(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 分钟
     *
     * @param currentDate
     * @param num
     * @param pattern
     * @return
     */
    public static String getMinute(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 秒钟
     *
     * @param currentDate
     * @param num
     * @param pattern
     * @return
     */
    public static String getSecond(String currentDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, num);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 根据间隔类型和间隔跨度来取得目标时间</br>
     * <p/>
     * 如：getDesDate("2012-01-01",-2,DateHandlerTool.WEEK) 返回2012年1月1日两周前的日期</br>
     *
     * @param currentDate 给定格式为pattern格式时间，为空或null默认为当前时间
     * @param num         时间跨度
     * @param type        间隔类型
     * @return 返回yyyy-MM-dd格式时间
     */
    public static String getDesDate(String currentDate, int num, int type, String pattern) {
        if (currentDate == null || currentDate.trim().equals("")) {
            currentDate = new SimpleDateFormat(pattern).format(new Date());
        }
        String result = currentDate;

        if (type == DateHandlerTool.DAY) {
            result = getDay(currentDate, num, pattern);
        } else if (type == DateHandlerTool.WEEK) {
            result = getWeek(currentDate, num, pattern);
        } else if (type == DateHandlerTool.MONTH) {
            result = getMonth(currentDate, num, pattern);
        } else if (type == DateHandlerTool.HOUR) {
            result = getHour(currentDate, num, pattern);
        } else if (type == DateHandlerTool.MINUTE) {
            result = getMinute(currentDate, num, pattern);
        } else if (type == DateHandlerTool.SECOND) {
            result = getSecond(currentDate, num, pattern);
        } else {

        }
        return result;
    }

    /**
     * 获取目标时间
     *
     * @param currentDate
     * @param num
     * @param type
     * @param pattern
     * @return
     */
    public static String getDesDate(Date currentDate, int num, int type, String pattern) {
        return getDesDate(new SimpleDateFormat(pattern).format(currentDate), num, type, pattern);
    }

    /**
     * 给定时间时间跨度是
     *
     * @param currentDate 给定时间
     * @param weekDate    星期数
     * @param num         相隔时间段
     * @param pattern     数据类型
     */
    public static String getWeekDay(String currentDate, int weekDate, int num, String pattern) {
        String result = "";
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_index = c.get(Calendar.DAY_OF_WEEK);
        if (week_index == Calendar.SUNDAY) {
            if (weekDate == Calendar.SUNDAY) {

            } else {
                num -= 1;
            }
        } else {
            if (weekDate == Calendar.SUNDAY) {
                num += 1;
            }

        }
        c.add(Calendar.DATE, num * 7);
        c.set(Calendar.DAY_OF_WEEK, weekDate);
        result = sdf.format(c.getTime());
        return result;
    }

    /**
     * 获得给定天数为一年或一月或一周的的第几天    星期天为一周的第一天
     *
     * @param currentDate 给定时间
     * @param dateType    给定时间类型
     * @param pattern     时间格式
     * @return
     */

    public static int getDayNum(String currentDate, int dateType, String pattern) {
        int result = 0;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        result = c.get(dateType);
        return result;
    }

    /**
     * 通过编码显示星期
     *
     * @param weekNum
     * @return
     */
    public static String weekNumToWeek(int weekNum) {
        String result = "";
        if (SUNDAY == weekNum) {
            result = "星期天";
        } else if (MONDAY == weekNum) {
            result = "星期一";
        } else if (TUESDAY == weekNum) {
            result = "星期二";
        } else if (WEDNESDAY == weekNum) {
            result = "星期三";
        } else if (THURSDAY == weekNum) {
            result = "星期四";
        } else if (FRIDAY == weekNum) {
            result = "星期五";
        } else if (SATURDAY == weekNum) {
            result = "星期六";
        } else {
            result = "编码错误";
        }

        return result;
    }

}
