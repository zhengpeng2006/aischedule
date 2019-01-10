package com.asiainfo.socket.util;


public class ConvertUtil {

	public static final int ALIGNMENT_HIGHT = 0;		// 高位在低字节
	public static final int ALIGNMENT_LOW = 1;			// 低位在高字节
	
	/**
	 * 将指定的二进制数组转换成long型
	 * @param data		二进制数组对象
	 * @param length 	数据长度
	 * @param alignment	数据对齐方式
	 * @return long		转换后的数据
	 * @throws Exception
	 */
	public static long byteArrayToLong(byte[] data, int length, int alignment) throws Exception {
		long value = 0;
		
		switch( alignment ) {
		case ALIGNMENT_HIGHT:
			for( int i=0; i<length; i++ ) {
				value <<=8;
				value += toInt(data[i]);
			}
			break;
		case ALIGNMENT_LOW:
			for( int i=0; i<length; i++ ) {
				value <<=8;
				value += toInt(data[length - i - 1]);
			}
			break;
		default:
			throw new Exception("数据对齐方式非法！");
		}
		
		return value;
	}
	
	/**
	 * 将指定的long型转换成二进制数组
	 * @param value		long型数据
	 * @param length 	数据长度
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static byte[] longToByteArray(long value, int length, int alignment) throws Exception {
		byte[] data = new byte[length];
		
		switch( alignment ) {
		case ALIGNMENT_HIGHT:
			for( int i=0; i<length; i++ ) {
				data[length - i - 1] = (byte)(value&0xff);
				value >>>=8;
			}
			break;
		case ALIGNMENT_LOW:
			for( int i=0; i<length; i++ ) {
				data[i] = (byte)(value&0xff);
				value >>>=8;
			}
			break;
		default:
			throw new Exception("数据对齐方式非法！");
		}
		
		return data;
	}
	
	/**
	 * 将指定的二进制数组转换成int型
	 * @param data		二进制数组对象
	 * @param length 	数据长度
	 * @param alignment	数据对齐方式
	 * @return int		转换后的数据
	 * @throws Exception
	 */
	public static int byteArrayToInt(byte[] data, int length, int alignment) throws Exception {
		int value = 0;
		
		switch( alignment ) {
		case ALIGNMENT_HIGHT:
			for( int i=0; i<length; i++ ) {
				value <<=8;
				value += toInt(data[i]);
			}
			break;
		case ALIGNMENT_LOW:
			for( int i=0; i<length; i++ ) {
				value <<=8;
				value += toInt(data[length - i - 1]);
			}
			break;
		default:
			throw new Exception("数据对齐方式非法！");
		}
		
		return value;
	}
	
	/**
	 * 将指定的int型转换成二进制数组
	 * @param value		int型数据
	 * @param length 	数据长度
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static byte[] intToByteArray(int value, int length, int alignment) throws Exception {
		byte[] data = new byte[length];
		
		switch( alignment ) {
		case ALIGNMENT_HIGHT:
			for( int i=0; i<length; i++ ) {
				data[length - i - 1] = (byte)(value&0xff);
				value >>>=8;
			}
			break;
		case ALIGNMENT_LOW:
			for( int i=0; i<length; i++ ) {
				data[i] = (byte)(value&0xff);
				value >>>=8;
			}
			break;
		default:
			throw new Exception("数据对齐方式非法！");
		}
		
		return data;
	}
	
    private static int toInt(byte b){   
        if( b>=0 )
        	return (int)b;   
        else
        	return (int)(b + 256);   
    }    

	/**
	 * 将指定的二进制数组转换成浮点数
	 * @param data		二进制数组对象
	 * @param alignment	数据对齐方式
	 * @return int		转换后的数据
	 * @throws Exception
	 */
	public static float byteArrayToFloat(byte[] data, int alignment) throws Exception {
		int value = byteArrayToInt(data, 4, alignment);
		
		return Float.intBitsToFloat(value);
	}
	
	/**
	 * 将指定的浮点数转换成二进制数组
	 * @param value		float型数据
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static byte[] floatToByteArray(float value, int alignment) throws Exception {
		int i = Float.floatToIntBits(value);
		
		return intToByteArray(i, 4, alignment);
	}
	
	/**
	 * 将指定的二进制数组转换成int型
	 * @param data		二进制数组对象
	 * @param length 	数据长度
	 * @param alignment	数据对齐方式
	 * @return int		转换后的数据
	 * @throws Exception
	 */
	public static double byteArrayToDouble(byte[] data, int alignment) throws Exception {
		 long value = byteArrayToLong(data, 8, alignment);
		
		return Double.longBitsToDouble(value);
	}
	
	/**
	 * 将指定的双精度数据转换成二进制数组
	 * @param value		双精度数据
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static byte[] doubleToByteArray(double value, int alignment) throws Exception {
		long l = Double.doubleToLongBits(value);
		
		return longToByteArray(l, 8, alignment);
	}
	

    
    
	/**
	 * 将指定的BCD码型转换成字符串
	 * @param data		bcd码数据
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static String bcdToString(byte[] data, int alignment) throws Exception {
		StringBuffer str = new StringBuffer();
		
		boolean flag = true;
		for( int i=0; flag && i<data.length; i++ ) {
			char ch;
			int value;
			switch( alignment ) {
			case ALIGNMENT_HIGHT:
				value = (data[i]&0xf0)>>>4;
				if( value>=0 && value<=9 ) {
					ch = '0';
					ch += value;
					str.append(ch);
				}
				else if( value==0x0f ) {
					flag = false;
				}
				else {
					throw new Exception("输入数据为非BCD码！");
				}
				value = data[i]&0x0f;
				if( value>=0 && value<=9 ) {
					ch = '0';
					ch += value;
					str.append(ch);
				}
				else if( value==0x0f ) {
					flag = false;
				}
				else {
					throw new Exception("输入数据为非BCD码！");
				}
				break;
			case ALIGNMENT_LOW:
				value = data[i]&0x0f;
				if( value>=0 && value<=9 ) {
					ch = '0';
					ch += value;
					str.append(ch);
				}
				else if( value==0x0f ) {
					flag = false;
				}
				else {
					throw new Exception("输入数据为非BCD码！");
				}
				value = (data[i]&0xf0)>>>4;
				if( value>=0 && value<=9 ) {
					ch = '0';
					ch += value;
					str.append(ch);
				}
				else if( value==0x0f ) {
					flag = false;
				}
				else {
					throw new Exception("输入数据为非BCD码！");
				}
				break;
			default:
				throw new Exception("数据对齐方式非法！");
			}
		}
		
		return str.toString();
	}

	/**
	 * 将String转换成BCD码
	 * @param value		bcd码数据
	 * @param alignment	数据对齐方式
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static byte[] stringToBcd(String value, int alignment) throws Exception {
		byte[] data = null;
		
		if( value!=null ) {
			byte[] bytes = value.getBytes();
			data = new byte[(bytes.length + 1)/2];
			for( int i=0; i<bytes.length; i++ ) {
				if( bytes[i]=='f' || bytes[i]== 'F' ) {
					break;
				}
				else if( bytes[i]<'0' || bytes[i]>'9' ) {
					throw new Exception("输入字符串参数为非BCD码！");
				}
				if( i%2==0 ) {
					data[i/2] = (byte)0xff;
				}
				switch( alignment ) {
				case ALIGNMENT_HIGHT:
					if( i%2==0 ) {
						data[i/2] &= 0x0f;
						data[i/2] += (bytes[i] - '0')<<4;
					}
					else {
						data[i/2] &= 0xf0;
						data[i/2] += bytes[i] - '0';
					}
					break;
				case ALIGNMENT_LOW:
					if( i%2==0 ) {
						data[i/2] &= 0xf0;
						data[i/2] += bytes[i] - '0';
					}
					else {
						data[i/2] &= 0x0f;
						data[i/2] += (bytes[i] - '0')<<4;
					}
					break;
				default:
					throw new Exception("数据对齐方式非法！");
				}
			}
		}

		return data;
	}

	/**
	 * 获取String指定字符集编码
	 * @param value			字符串对象
	 * @param charsetName	字符集名称
	 * @return byte[]		转换后的数据
	 * @throws Exception
	 */
	public static byte[] getBytes(String value, String charsetName) throws Exception {
		byte[] data = null;

		if( value!=null && value.length()>0 ) {
			if( "unicode".equalsIgnoreCase(charsetName) ) {
				data = value.getBytes("unicodebig");
			}
			else {
				data = value.getBytes(charsetName);
			}
			if( data!=null && "unicode".equalsIgnoreCase(charsetName) ) {
				byte[] temp = new byte[data.length - 2];
				System.arraycopy(data, 2, temp, 0, temp.length);
				data = temp;
			}
		}
		
		return data;
	}

	/**
	 * 根据指定字符集编码生成String对象
	 * @param data			二进制数据
	 * @param charsetName	字符集名称
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static String getString(byte[] data, String charsetName) throws Exception {
		String value = null;

		if( data!=null && data.length>0 ) {
			if( "unicode".equalsIgnoreCase(charsetName) ) {
				if( data[0]!=(0xfe-256) || data[1]!=(0xff-256) ) {
					byte[] data0 = new byte[data.length + 2];
					data0[0] = (0xfe-256);
					data0[1] = (0xff-256);
					System.arraycopy(data, 0, data0, 2, data.length);
					value = new String(data0, charsetName);
				}
				else {
					value = new String(data, charsetName);
				}
			}
			else {
				value = new String(data, charsetName);
			}
		}
		
		return value;
	}

	/**
	 * 根据指定字符集编码生成String对象
	 * @param data			二进制数据
	 * @param offset		偏移量
	 * @param length		字节长度
	 * @param charsetName	字符集名称
	 * @return byte[]	转换后的数据
	 * @throws Exception
	 */
	public static String getString(byte[] data, int offset, int length, String charsetName) throws Exception {
		String value = null;

		if( data!=null && data.length>0 ) {
			if( data[offset]!=(0xfe-256) || data[offset + 1]!=(0xff-256) ){
				byte[] data0 = new byte[length + 2];
				data0[0] = (0xfe-256);
				data0[1] = (0xff-256);
				System.arraycopy(data, offset, data0, 2, length);
				value = new String(data0, charsetName);
			}
			else {
				value = new String(data, offset, length, charsetName);
			}
		}
		
		return value;
	}

}
