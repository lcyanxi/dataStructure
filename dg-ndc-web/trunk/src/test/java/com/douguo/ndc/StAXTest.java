package com.douguo.ndc;

import org.apache.commons.lang3.StringUtils;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/10/18
 */
public class StAXTest {
    private static InputStream is;

    public static void main(String[] args) {
        is = StAXTest.class.getClassLoader().getResourceAsStream("static\\other\\chinaBoudler.xml");
        try {
            System.out.println(testRetrieveByCursor(88.1325900000,47.8276300000));
        }catch (Exception e){
        }
    }

    /**
     * 基于指针的方式读取xml文档——XMLStreamReader
     * px  经度
     * py  纬度
     * @throws Exception
     */
    private static List testRetrieveByCursor(double px,double py) throws Exception {
        List list = new ArrayList();
        //创建读取流工厂对象
        XMLInputFactory factory = XMLInputFactory.newInstance();
        //创建基于指针的读取流对象
        XMLStreamReader streamReader = factory.createXMLStreamReader(is);
        String province = "";
        //用指针迭代
        while (streamReader.hasNext()) {
            //事件的ID
            int eventId = streamReader.next();

            switch (eventId) {
                case XMLStreamConstants.START_DOCUMENT:
                    System.out.println("start docmuent");
                    break;

                case XMLStreamConstants.START_ELEMENT:
                    if (streamReader.getLocalName().equals("province")) {
                        province = streamReader.getAttributeValue(2);
                    }
                    if (streamReader.getLocalName().equals("City") && StringUtils
                        .isNotBlank(streamReader.getAttributeValue(2))) {
                        if (isPointInPolygon(px, py, streamReader.getAttributeValue(2))) {
                            list.add(province);
                            list.add(streamReader.getAttributeValue(1));
                            return list;
                        }
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    if (streamReader.isWhiteSpace()) {
                        break;
                    }
                    System.out.println(streamReader.getText());
                    break;
                case XMLStreamConstants.END_ELEMENT:

                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    System.out.println("end docmuent");
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 是否有 横断<br/> 参数为四个点的坐标
     */
    private static boolean isIntersect(double px1, double py1, double px2, double py2, double px3, double py3,
        double px4, double py4) {
        boolean flag = false;
        double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
        if (d != 0) {
            double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3)) / d;
            double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1)) / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 目标点是否在目标边上边上<br/>
     *
     * @param px0 目标点的经度坐标
     * @param py0 目标点的纬度坐标
     * @param px1 目标线的起点(终点)经度坐标
     * @param py1 目标线的起点(终点)纬度坐标
     * @param px2 目标线的终点(起点)经度坐标
     * @param py2 目标线的终点(起点)纬度坐标
     * @return
     */
    private static boolean isPointOnLine(double px0, double py0, double px1, double py1, double px2, double py2) {
        boolean flag = false;
        double ESP = 1e-9;// 无限小的正数
        if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP) && ((px0 - px1) * (px0 - px2) <= 0) && (
            (py0 - py1) * (py0 - py2) <= 0)) {
            flag = true;
        }
        return flag;
    }

    private static double Multiply(double px0, double py0, double px1, double py1, double px2, double py2) {
        return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
    }

    /**
     * 判断目标点是否在多边形内(由多个点组成)<br/>
     *
     * @param px 目标点的经度坐标
     * @param py 目标点的纬度坐标
     * @param s1 经纬度字符串
     * @return
     */
    private static boolean isPointInPolygon(double px, double py, String s1) {
        ArrayList<Double> polygonXA = new ArrayList<>();
        ArrayList<Double> polygonYA = new ArrayList<>();
        String[] line = s1.split(",");

        for (String s : line) {
            double x = Double.valueOf(s.split(" ")[0].trim());
            double y = Double.valueOf(s.split(" ")[1].trim());
            polygonXA.add(x);
            polygonYA.add(y);
        }
        boolean isInside = false;
        double ESP = 1e-9;
        int count = 0;
        double linePoint1x;
        double linePoint1y;
        double linePoint2x = 180;
        double linePoint2y;

        linePoint1x = px;
        linePoint1y = py;
        linePoint2y = py;

        for (int i = 0; i < polygonXA.size() - 1; i++) {
            double cx1 = polygonXA.get(i);
            double cy1 = polygonYA.get(i);
            double cx2 = polygonXA.get(i + 1);
            double cy2 = polygonYA.get(i + 1);
            // 如果目标点在任何一条线上
            if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
                return true;
            }
            // 如果线段的长度无限小(趋于零)那么这两点实际是重合的，不足以构成一条线段
            if (Math.abs(cy2 - cy1) < ESP) {
                continue;
            }
            // 第一个点是否在以目标点为基础衍生的平行纬度线
            if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                // 第二个点在第一个的下方,靠近赤道纬度为零(最小纬度)
                if (cy1 > cy2) {
                    count++;
                }

            }
            // 第二个点是否在以目标点为基础衍生的平行纬度线
            else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                // 第二个点在第一个的上方,靠近极点(南极或北极)纬度为90(最大纬度)
                if (cy2 > cy1) {
                    count++;
                }
            }
            // 由两点组成的线段是否和以目标点为基础衍生的平行纬度线相交
            else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                count++;
            }
        }
        if (count % 2 == 1) {
            isInside = true;
        }

        return isInside;
    }

}
