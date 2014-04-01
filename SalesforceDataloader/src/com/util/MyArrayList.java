package com.util;

import java.util.ArrayList;

public class MyArrayList extends ArrayList<String>{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	    public boolean contains(Object o) {
	        String paramStr = (String)o;
	        for (String s : this) {
	            if (paramStr.equalsIgnoreCase(s)) return true;
	        }
	        return false;
	    }
}
