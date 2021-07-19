/*
 * Copyright: 2017 dingxiang-inc.com Inc. All rights reserved.
 */

package com.example.register.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @FileName: CollectionUtils.java
 * @Description: CollectionUtils.java类说明
 * @Author: XDreamc
 * @Date: 2019/6/14 15:12
 */
public class CollectionUtils {

    private static final Comparator<String> SIMPLE_NAME_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            int i1 = s1.lastIndexOf('.');
            if (i1 >= 0) {
                s1 = s1.substring(i1 + 1);
            }
            int i2 = s2.lastIndexOf('.');
            if (i2 >= 0) {
                s2 = s2.substring(i2 + 1);
            }
            return s1.compareToIgnoreCase(s2);
        }
    };

    private CollectionUtils() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> sort(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Collections.sort((List) list);
        }
        return list;
    }

    public static List<String> sortSimpleName(List<String> list) {
        if (list != null && list.size() > 0) {
            Collections.sort(list, SIMPLE_NAME_COMPARATOR);
        }
        return list;
    }

    public static Map<String, Map<String, String>> splitAll(Map<String, List<String>> list, String separator) {
        if (list == null) {
            return null;
        }
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, List<String>> entry : list.entrySet()) {
            result.put(entry.getKey(), split(entry.getValue(), separator));
        }
        return result;
    }

    public static Map<String, List<String>> joinAll(Map<String, Map<String, String>> map, String separator) {
        if (map == null) {
            return null;
        }
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            result.put(entry.getKey(), join(entry.getValue(), separator));
        }
        return result;
    }

    public static Map<String, String> split(List<String> list, String separator) {
        if (list == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        if (list.isEmpty()) {
            return map;
        }
        for (String item : list) {
            int index = item.indexOf(separator);
            if (index == -1) {
                map.put(item, "");
            } else {
                map.put(item.substring(0, index), item.substring(index + 1));
            }
        }
        return map;
    }

    public static List<String> split(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return Collections.EMPTY_LIST;
        }
        String[] split = StringUtils.split(str, separator);
        return Arrays.asList(split);
    }

    public static List<String> join(Map<String, String> map, String separator) {
        if (map == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (map.size() == 0) {
            return list;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.length() == 0) {
                list.add(key);
            } else {
                list.add(key + separator + value);
            }
        }
        return list;
    }


    public static String join(Map<String, String> map, String kvSeparator, String separator) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (map.size() == 0) {
            return sb.toString();
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.length() == 0) {
                sb.append(key);
            } else {
                sb.append(key + kvSeparator + value + separator);
            }
        }
        return sb.substring(0, sb.length() - separator.length());
    }

    public static String join(Collection<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String ele : list) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(ele);
        }
        return sb.toString();
    }

    public static boolean mapEquals(Map<?, ?> map1, Map<?, ?> map2) {
        if (map1 == null && map2 == null) {
            return true;
        }
        if (map1 == null || map2 == null) {
            return false;
        }
        if (map1.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<?, ?> entry : map1.entrySet()) {
            Object key = entry.getKey();
            Object value1 = entry.getValue();
            Object value2 = map2.get(key);
            if (!objectEquals(value1, value2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean objectEquals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static Map<String, String> toStringMap(String... pairs) {
        Map<String, String> parameters = new HashMap<String, String>();
        if (pairs.length > 0) {
            if (pairs.length % 2 != 0) {
                throw new IllegalArgumentException("pairs must be even.");
            }
            for (int i = 0; i < pairs.length; i = i + 2) {
                parameters.put(pairs[i], pairs[i + 1]);
            }
        }
        return parameters;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Object... pairs) {
        Map<K, V> ret = new HashMap<K, V>();
        if (pairs == null || pairs.length == 0) {
            return ret;
        }

        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Map pairs can not be odd number.");
        }
        int len = pairs.length / 2;
        for (int i = 0; i < len; i++) {
            ret.put((K) pairs[2 * i], (V) pairs[2 * i + 1]);
        }
        return ret;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }


    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    @SafeVarargs
    public static <T> boolean isEmpty(T... list) {
        return list == null || list.length == 0;
    }
}