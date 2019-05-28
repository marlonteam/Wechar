package com.mazhe.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class StringUtil {
    public static boolean isEmpty(String s) {
        return (s == null || s.trim().equals(""));
    }

    public static String[] split(String s, String separator) {
        if (isEmpty(s)) return new String[]{};
        return s.split(separator);
    }
    
    public static String null2String(String s) {
        return (s == null ? "" : s);
    }
    
    public static String null2String(Object o) {
        return (o == null ? "" : o.toString());
    }
    
	public static String right(String s, int length) {
		if (isEmpty(s) || length <= 0)
			return s;
		
		int startIndex = s.length() - length;
		if (startIndex < 0) {
			startIndex = 0;
		}
		return s.substring(startIndex);
	}
	
	public static String left(String s, int length) {
		if (isEmpty(s) || length <= 0)
			return s;
		
		return s.substring(0, length);
	}
    
    public static String pad(String s, char padChar, int length, boolean prefix) {
        StringBuffer buffer = new StringBuffer(null2String(s).trim());
        if (prefix) buffer = buffer.reverse();
        
        while (buffer.length() < length) {
            buffer.append(padChar);
        }
        return prefix ? buffer.reverse().toString() : buffer.toString();
    }
    
    public static String repeat(String s, int num) {
        if (num <= 0 || isEmpty(s)) return s;
        
        StringBuffer buffer = new StringBuffer(s);
        while (num > 0) {
            buffer.append(s);
            num--;
        }
        return buffer.toString();
    }
    
    public static String covertToCharset(String s, String srcCharset, String tgtCharset) throws UnsupportedEncodingException {
        s = null2String(s);
        return new String(s.getBytes(srcCharset), tgtCharset);
    }
    
    public static String capitalize(String s) {
        if (isEmpty(s)) return s;
        
        StringBuffer buffer = new StringBuffer(s);
        buffer.setCharAt(0, String.valueOf(buffer.charAt(0)).toUpperCase().charAt(0));
        
        return buffer.toString();
    }
    
    public static String replace(String original, String replaced, String replacing) {
        String result = null;

        int i;
        if ((original == null) || (replaced == null) || (replacing == null))
            return original;
        
        do {
            i = original.indexOf(replaced);
            if (i != -1) {
               result = original.substring(0, i);
               result = result + replacing;
               result = result + original.substring(i + replaced.length());
               original = result;
            }
        } while (i != -1);

        return original;
    }
    
    public static String replaceSpecialChar(String orginalStr)
    {
    	if(orginalStr.indexOf("'")<0)
    	return orginalStr;
    	String tempstr=orginalStr;
    	String targetstr="";
    	while(tempstr.indexOf("'")>=0)
    	{ 
    		targetstr=targetstr+tempstr.substring(0,tempstr.indexOf("'")+1)+"'";
    		tempstr=tempstr.substring(tempstr.indexOf("'")+1);
        	
    	}
    	return targetstr+tempstr;
    }
    
    public static String changeString2Chinese(String theString)
    {
    	String newString = "";
    	char org = ' ';
    	int j = 0;
    	if ((theString == null) || (theString.length() < 1))
    		{
    		return theString;
    	}
    	do {
    		org = theString.charAt(j);
    		org = ansiToUnicode(org);
    		newString = newString + String.valueOf(org);
    		j++;

    	}
    	while (j < theString.length());

    	return newString;
    }
    
    public static char ansiToUnicode(char charIn)
    {

    	String textIn = new String();
    	textIn += charIn;

    	byte[] byteA;
    	byte[] byteU = new byte[2];
    	byteA = textIn.getBytes();

    	//if input is Ansi format, return
    	if (byteA.length == 2)
    		return charIn;

    	//if input is [space], return
    	if (byteA[0] == 32)
    		return '\u3000';//charIn;

    	byteU[0] = -93;
    	byteU[1] = (byte) (byteA[0] - 128);

    	String textOut = new String(byteU);

    	return textOut.charAt(0);
    }
    
    public static List<String> parseFieldIDFromContent(String theContent) {
		String strContent = theContent;
		List<String> fieldList = new ArrayList<String>();
		int posBegin = 0;
		int posEnd = 0;
		String FiledID = "";
		while (true) {
			posBegin = strContent.indexOf("#");

			if (posBegin >= 0) {
				posEnd = strContent.indexOf("#", posBegin + 1);
				if (posEnd >= 0) {
					FiledID = strContent.substring(posBegin + 1, posEnd);
					if (FiledID != null && FiledID.trim().length() > 0)
						fieldList.add(FiledID);
					if (posEnd < strContent.length() - 1)
						strContent = strContent.substring(posEnd + 1);
					else
						strContent = "";
				} else
					break;

			} else
				break;
		}
		return fieldList;
	}
    
    public static String mergeFieldValueForContent(String targeContent, String FieldID, String FieldValue) {
		if (targeContent == null || FieldID == null)
			return targeContent;
		if (FieldValue == null || FieldValue.trim().length() == 0)
			FieldValue = FieldID;
		else
			FieldValue = "" + FieldValue.trim() + "";
		try {
			while (true) {
				int pos = targeContent.indexOf("#" + FieldID + "#");
				if (pos < 0)
					break;
	
				targeContent =
					targeContent.substring(0, pos)
						+ FieldValue
						+ targeContent.substring(pos + FieldID.length() + 2);
	
			}
			return targeContent;
		} catch (Exception e) {
			return targeContent;
		}
    }
    
    public static String double2String(double doublevalue) {
		String stringValue = "";
		DecimalFormat numberformat = new DecimalFormat("###0.00#######");
		stringValue = numberformat.format(doublevalue);
		return stringValue;
	}
    
    public static int getByteLength(String value, String charSet) throws UnsupportedEncodingException {
    	if (value == null) {
    		return 0;
    	}
    	
    	return (new String(value.getBytes(), charSet)).length();
    }

    /**
     * 生成随机字符串
     * @param length 指定长度
     * @return
     */
    public static String getRandomString(int length){
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
	
		for(int i = 0 ; i < length; ++i){
		    int number = random.nextInt(62); // [0,62)
		    sb.append(str.charAt(number));
		}
		return sb.toString();
    }
    
    public static Long toLong(String s) {
    	if (StringUtil.isEmpty(s)) {
    		return null;
    	}
    	Long rst = null;
    	try {
    		rst = Long.valueOf(s);
    	} catch (NumberFormatException e) {
  
    	}
    	return rst;
    }
    
    public static Timestamp toTimestamp(String s) {
    	if (StringUtil.isEmpty(s)) {
    		return null;
    	}
    	Timestamp rst = null;
    	try {
    		if (s.length()>19 && s.indexOf("+")>0) {
    			s = s.substring(0, s.indexOf("+"));
    		}
    		s = s.replace("T", " ");
    		rst = Timestamp.valueOf(s);
    	} catch (IllegalArgumentException e) {
  
    	}
    	return rst;
    }
    
    public static Integer toInteger(String s) {
    	if (StringUtil.isEmpty(s)) {
    		return null;
    	}
    	Integer rst = null;
    	try {
    		rst = Integer.valueOf(s);
    	} catch (NumberFormatException e) {
  
    	}
    	return rst;
    }
    
    public static Double toDouble(String s) {
    	if (StringUtil.isEmpty(s)) {
    		return null;
    	}
    	Double rst = null;
    	try {
    		rst = Double.valueOf(s);
    	} catch (NumberFormatException e) {
  
    	}
    	return rst;
    }
    
    public static java.sql.Date toDate(String s) {
    	if (StringUtil.isEmpty(s)) {
    		return null;
    	}
    	java.sql.Date rst = null;
    	try {
    		rst = java.sql.Date.valueOf(s);
    	} catch (IllegalArgumentException e) {
  
    	}
    	return rst;
    }
}