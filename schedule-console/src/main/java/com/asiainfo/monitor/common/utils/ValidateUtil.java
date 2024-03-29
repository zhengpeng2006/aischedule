
package com.asiainfo.monitor.common.utils;

import java.util.Collection;


/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public final class ValidateUtil {
    private ValidateUtil() {
    }

    public final static boolean validateNotNull(String str) {
        return str != null;
    }

    public final static boolean validateNotEmpty(String str) {
        if (validateNotNull(str)) {
            return!str.trim().equals("");
        }
        return false;
    }

    public final static boolean validateNotNull(Long l) {
        return l != null;
    }

    public final static boolean validateNotNull(Object o) {
        return o != null;
    }

    public final static boolean validateNotEmpty(Object[] obj) {
        if (validateNotNull(obj)) {
            return (obj.length != 0);
        }
        return false;
    }

    public final static boolean validateNotEmpty(Collection col) {
        if (validateNotNull(col)) {
            return (col.size() != 0);
        }
        return false;
    }

   


    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (!ValidateUtil.validateNotNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">Assert.isNull(value);</pre>
     * @param object the object to check
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">Assert.notNull(clazz);</pre>
     * @param object the object to check
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object,
                "[Assertion failed] - this argument is required; it cannot be null");
    }


    /**
     * Assert that a string has valid text content; that is, it must not be <code>null</code>
     * and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "Name must not be empty");</pre>
     * @param text the string to check
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText(String text, String message) {
        if (!ValidateUtil.validateNotEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a string has valid text content; that is, it must not be <code>null</code>
     * and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "Name must not be empty");</pre>
     * @param text the string to check
     * @see StringUtils#hasText
     */
    public static void hasText(String text) {
        hasText(text,
                "[Assertion failed] - this String argument must have text; it cannot be <code>null</code>, empty, or blank");
    }


    /**
     * Assert that an array has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

   public static void notEmpty(String str,String message){
     if(!ValidateUtil.validateNotEmpty(str)){
       throw new IllegalArgumentException(message);
     }
   }

    /**
     * Assert that an array has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">Assert.notEmpty(array);</pre>
     * @param array the array to check
     * @throws IllegalArgumentException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @throws IllegalArgumentException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection) {
        notEmpty(collection,
                 "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }


    /**
     * Assert a boolean expression, throwing <code>IllegalStateException</code>
     * if the test result is <code>false</code>. Call isTrue if you wish to
     * throw IllegalArgumentException on an assertion failure.
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is <code>false</code>
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException}
     * if the test result is <code>false</code>.
     * <p>Call {@link #isTrue(boolean)} if you wish to
     * throw {@link IllegalArgumentException} on an assertion failure.
     * <pre class="code">Assert.state(id == null);</pre>
     * @param expression a boolean expression
     * @throws IllegalStateException if the supplied expression is <code>false</code>
     */
    public static void state(boolean expression) {
        state(expression,
              "[Assertion failed] - this state invariant must be true");
    }
    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>true</code>. zhang.nanyu added 2008-08-09
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is <code>false</code>
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>. zhang.nanyu added 2008-08-09
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
