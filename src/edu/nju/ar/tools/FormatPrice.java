package edu.nju.ar.tools;

public class FormatPrice {

	public static String formatPrice(String price) {
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<price.length();i++){
			char c=price.charAt(i);
			if((c<='9'&&c>='0')||c=='.')
				builder.append(c);
		}
		return builder.toString();
	}
}
