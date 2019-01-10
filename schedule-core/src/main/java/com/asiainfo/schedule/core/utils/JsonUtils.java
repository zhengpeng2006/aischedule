package com.asiainfo.schedule.core.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * json转换工具类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class JsonUtils {
	private static Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		return gson.fromJson(jsonString, clazz);
	}

	public static class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
		public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
			String dateFormatAsString = DateUtils.formatYYYYMMddHHmmss(new Date(src.getTime()));
			return new JsonPrimitive(dateFormatAsString);
		}

		public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if (!(json instanceof JsonPrimitive)) {
				throw new JsonParseException("The date should be a string value");
			}

			try {
				Date date = (Date) DateUtils.parseYYYYMMddHHmmss(json.getAsString());
				return new Timestamp(date.getTime());
			} catch (Exception e) {
				throw new JsonParseException(e);
			}
		}
	}

}
