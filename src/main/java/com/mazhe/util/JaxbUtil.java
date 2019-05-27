package com.mazhe.util;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jia on 2015/12/10.
 */
public class JaxbUtil {

    private static Map<Class, JAXBContext> jaxbContextOnClassMap = new HashMap();

    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     *
     * @param obj
     * @param
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    public static String convertToXml(Object obj, boolean withHeader) {
        return convertToXml(obj, "UTF-8", withHeader);
    }


//    public static String convertUnescapeXml(String xml){
//        return StringEscapeUtils.unescapeXml(xml);
//    }

    /**
     * JavaBean转换成xml
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {

        return convertToXml(obj, encoding, true);
    }

    private static JAXBContext getJAXBContext(Class clazz) throws JAXBException {
        JAXBContext ctx = (JAXBContext)JaxbUtil.jaxbContextOnClassMap.get(clazz);
        if (ctx == null) {
            ctx = JAXBContext.newInstance(clazz);
            Map var4 = jaxbContextOnClassMap;
            synchronized(jaxbContextOnClassMap) {
                jaxbContextOnClassMap.put(clazz, ctx);
            }
        }

        return ctx;
    }

    public static String convertToXml(Object obj, String encoding, boolean withHeader) {
        String result = null;
        try {
            JAXBContext jc = JaxbUtil.getJAXBContext(obj.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//            marshaller.setListener(new MarshallerListener());
            //marshaller.setProperty("com.sun.xml.bind.xmlHeaders",
            //        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringWriter writer = new StringWriter(4000);
            if (withHeader)
                writer.append("<?xml version=\"1.0\" encoding=\""+encoding+"\" standalone=\"no\" ?>\n");
            marshaller.marshal(obj, writer);
//            result = convertUnescapeXml(writer.toString());
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            //System.out.println("before unmarshaller: "+xml);
            JAXBContext jc = JaxbUtil.getJAXBContext(c);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <T> T convertToJavaBean(XMLStreamReader xsr, Class<T> c) {
        T t = null;
        try {
            JAXBContext jc = JaxbUtil.getJAXBContext(c);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(xsr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

//    public static Document convertToDocument(Object obj) {
//
//        try {
//            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//            JAXBContext context = JAXBContext.newInstance(obj.getClass());
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.marshal(obj, document);
//            return document;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
}
