package com.ara.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by XieXin on 2020/6/11.
 * BigDecimal工具
 * <p>
 * BigDecimal.setScale();//用于格式化小数点
 * setScale(1);//表示保留以为小数，默认用四舍五入方式
 * setScale(1,BigDecimal.ROUND_DOWN);//直接删除多余的小数位，如2.35会变成2.3
 * setScale(1,BigDecimal.ROUND_UP);//进位处理，2.35变成2.4
 * setScale(1,BigDecimal.ROUND_HALF_UP);//四舍五入，2.35变成2.4
 * setScaler(1,BigDecimal.ROUND_HALF_DOWN);//四舍五入，2.35变成2.3，如果是5则向下舍
 * 1、ROUND_UP
 * 舍入远离零的舍入模式。
 * 在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。
 * 注意，此舍入模式始终不会减少计算值的大小。
 * 2、ROUND_DOWN
 * 接近零的舍入模式。
 * 在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)。
 * 注意，此舍入模式始终不会增加计算值的大小。
 * 3、ROUND_CEILING
 * 接近正无穷大的舍入模式。
 * 如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同;
 * 如果为负，则舍入行为与 ROUND_DOWN 相同。
 * 注意，此舍入模式始终不会减少计算值。
 * 4、ROUND_FLOOR
 * 接近负无穷大的舍入模式。
 * 如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同;
 * 如果为负，则舍入行为与 ROUND_UP 相同。
 * 注意，此舍入模式始终不会增加计算值。
 * 5、ROUND_HALF_UP
 * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
 * 如果舍弃部分 >= 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。
 * 注意，这是我们大多数人在小学时就学过的舍入模式(四舍五入)。
 * 6、ROUND_HALF_DOWN
 * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。
 * 如果舍弃部分 > 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)。
 * 7、ROUND_HALF_EVEN
 * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
 * 如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;
 * 如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。
 * 注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。
 * 此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。
 * 如果前一位为奇数，则入位，否则舍去。
 * 以下例子为保留小数点1位，那么这种舍入方式下的结果。
 * 1.15>1.2 1.25>1.2
 * 8、ROUND_UNNECESSARY
 * 断言请求的操作具有精确的结果，因此不需要舍入。
 * 如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。
 * <p>
 * add(b2)                      加法(b1+b2)
 * subtract(b2)                 减法(b1-b2)
 * multiply(b2)                 乘法(b1*b2)
 * divide(b2)                   除法(b1/b2)
 * remainder(b2)                求余数(b1/b2=b3....b4(b4就是余数))
 * divideAndremainder(b2)       求商和余数(返回数组,0角标为商,1角标为余数)
 * max(b2) :                    求最大数(b1和b2中最大数)
 * min(b2) :                    求最小数(b1和b2中最大数)
 * abs()：                      求绝对值(b1的绝对值)
 * negate()：                   求相反数(b1的相反数)
 * <p>
 * a.compareTo(b)               -1、0、1，即左边比右边数大，返回1，相等返回0，比右边小返回-1
 * <p>
 * stripTrailingZeros()         去除末尾多余的0
 * toString()                   可能由于打印的数值太小而打印其科学计数法表示
 * toPlainString()              原值输出为字符串
 */
public class BigDecimalUtils {
    /**
     * Float转String
     *
     * @param f float
     * @return String
     */
    public static String toString(float f) {
        return new BigDecimal(Double.toString(f)).toPlainString();
    }

    /**
     * Float转String并去掉后面的0
     *
     * @param f float
     * @return String
     */
    public static String stripTrailingZeros(float f) {
        return new BigDecimal(Double.toString(f)).stripTrailingZeros().toPlainString();
    }

    /**
     * Double转String
     *
     * @param d double
     * @return String
     */
    public static String toString(double d) {
        return new BigDecimal(Double.toString(d)).toPlainString();
    }

    /**
     * Double转String并去掉后面的0
     *
     * @param d double
     * @return String
     */
    public static String stripTrailingZeros(double d) {
        return new BigDecimal(Double.toString(d)).stripTrailingZeros().toPlainString();
    }

    /**
     * 保留小数
     *
     * @param f     float
     * @param scale 保留几位小数
     * @return String
     */
    public static String retainDecimal(float f, int scale) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(f));
        return bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 保留2位小数
     *
     * @param f float
     * @return String
     */
    public static String retain2Decimal(float f) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(f));
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 保留2位小数
     *
     * @param b BigDecimal
     * @return String
     */
    public static String retain2Decimal(BigDecimal b) {
        return b.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 保留小数
     *
     * @param d     double
     * @param scale 保留几位小数
     * @return String
     */
    public static String retainDecimal(double d, int scale) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        return bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 保留小数
     *
     * @param b     BigDecimal
     * @param scale 保留几位小数
     * @return String
     */
    public static String retainDecimal(BigDecimal b, int scale) {
        return b.setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 保留2位小数
     *
     * @param d double
     * @return String
     */
    public static String retain2Decimal(double d) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * Double分组000,000,000.00
     *
     * @param d Double
     * @return String
     */
    public static String toGroupingUsed(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(true);
        return nf.format(d);
    }

    /**
     * Float分组000,000,000.00
     *
     * @param f float
     * @return String
     */
    public static String toGroupingUsed(float f) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(true);
        return nf.format(f);
    }
}
