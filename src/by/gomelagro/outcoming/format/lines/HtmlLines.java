package by.gomelagro.outcoming.format.lines;

public class HtmlLines {
	
	public static String getHtmlText(String[] lines){
		String full = "<html>";
		for(int index=0;index<lines.length;index++){
			full+=lines[index];
			if(index != lines.length-1){
				full+="<br>";
			}
		}
		full+="</html>";
		return full;
	}
}
