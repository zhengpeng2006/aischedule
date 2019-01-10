package com.asiainfo.monitor.tools.license;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;



public class Base64 implements BinaryDecoder, BinaryEncoder {

	private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;

	private static final int DEFAULT_BUFFER_SIZE = 8192;

	
	static final int CHUNK_SIZE = 76;

	static final byte[] CHUNK_SEPARATOR = { '\r', '\n' };


	private static final byte[] STANDARD_ENCODE_TABLE = { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };


	private static final byte[] URL_SAFE_ENCODE_TABLE = { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '-', '_' };

	private static final byte PAD = '=';


	private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
			-1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };


	private static final int MASK_6BITS = 0x3f;


	private static final int MASK_8BITS = 0xff;


	private final byte[] encodeTable;


	private final int lineLength;

	private final byte[] lineSeparator;


	private final int decodeSize;


	private final int encodeSize;


	private byte[] buffer;


	private int pos;


	private int readPos;


	private int currentLinePos;


	private int modulus;


	private boolean eof;


	private int x;


	public Base64() {
		this(false);
	}


	public Base64(boolean urlSafe) {
		this(CHUNK_SIZE, CHUNK_SEPARATOR, urlSafe);
	}


	public Base64(int lineLength) {
		this(lineLength, CHUNK_SEPARATOR);
	}


	public Base64(int lineLength, byte[] lineSeparator) {
		this(lineLength, lineSeparator, false);
	}


	public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe) {
		if (lineSeparator == null) {
			lineLength = 0; // disable chunk-separating
			lineSeparator = CHUNK_SEPARATOR; // this just gets ignored
		}
		this.lineLength = lineLength > 0 ? (lineLength / 4) * 4 : 0;
		this.lineSeparator = new byte[lineSeparator.length];
		System.arraycopy(lineSeparator, 0, this.lineSeparator, 0,
				lineSeparator.length);
		if (lineLength > 0) {
			this.encodeSize = 4 + lineSeparator.length;
		} else {
			this.encodeSize = 4;
		}
		this.decodeSize = this.encodeSize - 1;
		if (containsBase64Byte(lineSeparator)) {
			String sep = StringTransform.newStringUtf8(lineSeparator);
			throw new IllegalArgumentException(
					"lineSeperator must not contain base64 characters: [" + sep
							+ "]");
		}
		this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE
				: STANDARD_ENCODE_TABLE;
	}

	public boolean isUrlSafe() {
		return this.encodeTable == URL_SAFE_ENCODE_TABLE;
	}

	boolean hasData() {
		return this.buffer != null;
	}

	int avail() {
		return buffer != null ? pos - readPos : 0;
	}

	private void resizeBuffer() {
		if (buffer == null) {
			buffer = new byte[DEFAULT_BUFFER_SIZE];
			pos = 0;
			readPos = 0;
		} else {
			byte[] b = new byte[buffer.length * DEFAULT_BUFFER_RESIZE_FACTOR];
			System.arraycopy(buffer, 0, b, 0, buffer.length);
			buffer = b;
		}
	}

	int readResults(byte[] b, int bPos, int bAvail) {
		if (buffer != null) {
			int len = Math.min(avail(), bAvail);
			if (buffer != b) {
				System.arraycopy(buffer, readPos, b, bPos, len);
				readPos += len;
				if (readPos >= pos) {
					buffer = null;
				}
			} else {
				// Re-using the original consumer's output array is only
				// allowed for one round.
				buffer = null;
			}
			return len;
		}
		return eof ? -1 : 0;
	}

	void setInitialBuffer(byte[] out, int outPos, int outAvail) {
		// We can re-use consumer's original output array under
		// special circumstances, saving on some System.arraycopy().
		if (out != null && out.length == outAvail) {
			buffer = out;
			pos = outPos;
			readPos = outPos;
		}
	}

	void encode(byte[] in, int inPos, int inAvail) {
		if (eof) {
			return;
		}
		// inAvail < 0 is how we're informed of EOF in the underlying data we're
		// encoding.
		if (inAvail < 0) {
			eof = true;
			if (buffer == null || buffer.length - pos < encodeSize) {
				resizeBuffer();
			}
			switch (modulus) {
			case 1:
				buffer[pos++] = encodeTable[(x >> 2) & MASK_6BITS];
				buffer[pos++] = encodeTable[(x << 4) & MASK_6BITS];
				// URL-SAFE skips the padding to further reduce size.
				if (encodeTable == STANDARD_ENCODE_TABLE) {
					buffer[pos++] = PAD;
					buffer[pos++] = PAD;
				}
				break;

			case 2:
				buffer[pos++] = encodeTable[(x >> 10) & MASK_6BITS];
				buffer[pos++] = encodeTable[(x >> 4) & MASK_6BITS];
				buffer[pos++] = encodeTable[(x << 2) & MASK_6BITS];
				// URL-SAFE skips the padding to further reduce size.
				if (encodeTable == STANDARD_ENCODE_TABLE) {
					buffer[pos++] = PAD;
				}
				break;
			}
			if (lineLength > 0 && pos > 0) {
				System.arraycopy(lineSeparator, 0, buffer, pos,
						lineSeparator.length);
				pos += lineSeparator.length;
			}
		} else {
			for (int i = 0; i < inAvail; i++) {
				if (buffer == null || buffer.length - pos < encodeSize) {
					resizeBuffer();
				}
				modulus = (++modulus) % 3;
				int b = in[inPos++];
				if (b < 0) {
					b += 256;
				}
				x = (x << 8) + b;
				if (0 == modulus) {
					buffer[pos++] = encodeTable[(x >> 18) & MASK_6BITS];
					buffer[pos++] = encodeTable[(x >> 12) & MASK_6BITS];
					buffer[pos++] = encodeTable[(x >> 6) & MASK_6BITS];
					buffer[pos++] = encodeTable[x & MASK_6BITS];
					currentLinePos += 4;
					if (lineLength > 0 && lineLength <= currentLinePos) {
						System.arraycopy(lineSeparator, 0, buffer, pos,
								lineSeparator.length);
						pos += lineSeparator.length;
						currentLinePos = 0;
					}
				}
			}
		}
	}

	void decode(byte[] in, int inPos, int inAvail) {
		if (eof) {
			return;
		}
		if (inAvail < 0) {
			eof = true;
		}
		for (int i = 0; i < inAvail; i++) {
			if (buffer == null || buffer.length - pos < decodeSize) {
				resizeBuffer();
			}
			byte b = in[inPos++];
			if (b == PAD) {
				// We're done.
				eof = true;
				break;
			} else {
				if (b >= 0 && b < DECODE_TABLE.length) {
					int result = DECODE_TABLE[b];
					if (result >= 0) {
						modulus = (++modulus) % 4;
						x = (x << 6) + result;
						if (modulus == 0) {
							buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
							buffer[pos++] = (byte) ((x >> 8) & MASK_8BITS);
							buffer[pos++] = (byte) (x & MASK_8BITS);
						}
					}
				}
			}
		}

		// Two forms of EOF as far as base64 decoder is concerned: actual
		// EOF (-1) and first time '=' character is encountered in stream.
		// This approach makes the '=' padding characters completely optional.
		if (eof && modulus != 0) {
			x = x << 6;
			switch (modulus) {
			case 2:
				x = x << 6;
				buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
				break;
			case 3:
				buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
				buffer[pos++] = (byte) ((x >> 8) & MASK_8BITS);
				break;
			}
		}
	}

	public static boolean isBase64(byte octet) {
		return octet == PAD
				|| (octet >= 0 && octet < DECODE_TABLE.length && DECODE_TABLE[octet] != -1);
	}

	public static boolean isArrayByteBase64(byte[] arrayOctet) {
		for (int i = 0; i < arrayOctet.length; i++) {
			if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean containsBase64Byte(byte[] arrayOctet) {
		for (int i = 0; i < arrayOctet.length; i++) {
			if (isBase64(arrayOctet[i])) {
				return true;
			}
		}
		return false;
	}

	public static byte[] encodeBase64(byte[] binaryData) {
		return encodeBase64(binaryData, false);
	}

	public static String encodeBase64String(byte[] binaryData) {
		return StringTransform.newStringUtf8(encodeBase64(binaryData, true));
	}


	public static byte[] encodeBase64URLSafe(byte[] binaryData) {
		return encodeBase64(binaryData, false, true);
	}


	public static String encodeBase64URLSafeString(byte[] binaryData) {
		return StringTransform.newStringUtf8(encodeBase64(binaryData, false, true));
	}

	public static byte[] encodeBase64Chunked(byte[] binaryData) {
		return encodeBase64(binaryData, true);
	}


	public Object decode(Object pObject) throws DecoderException {
		if (pObject instanceof byte[]) {
			return decode((byte[]) pObject);
		} else if (pObject instanceof String) {
			return decode((String) pObject);
		} else {
			throw new DecoderException(
					"Parameter supplied to Base64 decode is not a byte[] or a String");
		}
	}


	public byte[] decode(String pArray) {
		return decode(StringTransform.getBytesUtf8(pArray));
	}


	public byte[] decode(byte[] pArray) {
		reset();
		if (pArray == null || pArray.length == 0) {
			return pArray;
		}
		long len = (pArray.length * 3) / 4;
		byte[] buf = new byte[(int) len];
		setInitialBuffer(buf, 0, buf.length);
		decode(pArray, 0, pArray.length);
		decode(pArray, 0, -1); // Notify decoder of EOF.
		byte[] result = new byte[pos];
		readResults(result, 0, result.length);
		return result;
	}


	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
		return encodeBase64(binaryData, isChunked, false);
	}

	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked,
			boolean urlSafe) {
		return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
	}


	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked,
			boolean urlSafe, int maxResultSize) {
		if (binaryData == null || binaryData.length == 0) {
			return binaryData;
		}

		long len = getEncodeLength(binaryData, CHUNK_SIZE, CHUNK_SEPARATOR);
		if (len > maxResultSize) {
			throw new IllegalArgumentException(
					"Input array too big, the output array would be bigger ("
							+ len + ") than the specified maxium size of "
							+ maxResultSize);
		}

		Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0,
				CHUNK_SEPARATOR, urlSafe);
		return b64.encode(binaryData);
	}


	public static byte[] decodeBase64(String base64String) {
		return new Base64().decode(base64String);
	}


	public static byte[] decodeBase64(byte[] base64Data) {
		return new Base64().decode(base64Data);
	}

	/**
	 * Discards any whitespace from a base-64 encoded block.
	 *
	 * @param data The base-64 encoded data to discard the whitespace from.
	 * @return The data, less whitespace (see RFC 2045).
	 * @deprecated This method is no longer needed
	 */
	static byte[] discardWhitespace(byte[] data) {
		byte groomedData[] = new byte[data.length];
		int bytesCopied = 0;
		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
			case ' ':
			case '\n':
			case '\r':
			case '\t':
				break;
			default:
				groomedData[bytesCopied++] = data[i];
			}
		}
		byte packedData[] = new byte[bytesCopied];
		System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
		return packedData;
	}

	private static boolean isWhiteSpace(byte byteToCheck) {
		switch (byteToCheck) {
		case ' ':
		case '\n':
		case '\r':
		case '\t':
			return true;
		default:
			return false;
		}
	}


	public Object encode(Object pObject) throws EncoderException {
		if (!(pObject instanceof byte[])) {
			throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
		}
		return encode((byte[]) pObject);
	}


	public String encodeToString(byte[] pArray) {
		return StringTransform.newStringUtf8(encode(pArray));
	}


	public byte[] encode(byte[] pArray) {
		reset();
		if (pArray == null || pArray.length == 0) {
			return pArray;
		}
		long len = getEncodeLength(pArray, lineLength, lineSeparator);
		byte[] buf = new byte[(int) len];
		setInitialBuffer(buf, 0, buf.length);
		encode(pArray, 0, pArray.length);
		encode(pArray, 0, -1); // Notify encoder of EOF.
		// Encoder might have resized, even though it was unnecessary.
		if (buffer != buf) {
			readResults(buf, 0, buf.length);
		}
		// In URL-SAFE mode we skip the padding characters, so sometimes our
		// final length is a bit smaller.
		if (isUrlSafe() && pos < buf.length) {
			byte[] smallerBuf = new byte[pos];
			System.arraycopy(buf, 0, smallerBuf, 0, pos);
			buf = smallerBuf;
		}
		return buf;
	}


	private static long getEncodeLength(byte[] pArray, int chunkSize,
			byte[] chunkSeparator) {
		// base64 always encodes to multiples of 4.
		chunkSize = (chunkSize / 4) * 4;

		long len = (pArray.length * 4) / 3;
		long mod = len % 4;
		if (mod != 0) {
			len += 4 - mod;
		}
		if (chunkSize > 0) {
			boolean lenChunksPerfectly = len % chunkSize == 0;
			len += (len / chunkSize) * chunkSeparator.length;
			if (!lenChunksPerfectly) {
				len += chunkSeparator.length;
			}
		}
		return len;
	}

	public static BigInteger decodeInteger(byte[] pArray) {
		return new BigInteger(1, decodeBase64(pArray));
	}


	public static byte[] encodeInteger(BigInteger bigInt) {
		if (bigInt == null) {
			throw new NullPointerException(
					"encodeInteger called with null parameter");
		}
		return encodeBase64(toIntegerBytes(bigInt), false);
	}


	static byte[] toIntegerBytes(BigInteger bigInt) {
		int bitlen = bigInt.bitLength();
		// round bitlen
		bitlen = ((bitlen + 7) >> 3) << 3;
		byte[] bigBytes = bigInt.toByteArray();

		if (((bigInt.bitLength() % 8) != 0)
				&& (((bigInt.bitLength() / 8) + 1) == (bitlen / 8))) {
			return bigBytes;
		}
		// set up params for copying everything but sign bit
		int startSrc = 0;
		int len = bigBytes.length;

		// if bigInt is exactly byte-aligned, just skip signbit in copy
		if ((bigInt.bitLength() % 8) == 0) {
			startSrc = 1;
			len--;
		}
		int startDst = bitlen / 8 - len; // to pad w/ nulls as per spec
		byte[] resizedBytes = new byte[bitlen / 8];
		System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
		return resizedBytes;
	}

	private void reset() {
		buffer = null;
		pos = 0;
		readPos = 0;
		currentLinePos = 0;
		modulus = 0;
		eof = false;
	}
	
	public static byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}

    public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}
}
