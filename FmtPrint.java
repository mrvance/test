package test.lang;

import java.util.Arrays;
import java.util.List;

/**
 * 以table的方式格式化输出到控制台，左对齐
 * 一个汉字占两个字符，用" "拼接以完成对齐显示
 * 在eclipse和idea控制台显示不对齐，但在cmd和notpad++中显示可以对齐
 * 
 *
 */
public class FmtPrint {
	private int[] cellLengths = null;
	private List<String[]> list = null;
	
	public FmtPrint(List<String[]> list) {
		cellLengths = new int[list.get(0).length];
		this.list = list;
		checkCellMaxLength(list);
	}

	private void checkCellMaxLength(List<String[]> list) {
		list.forEach(e -> {
			for (int i = 0; i < e.length; i++) {
				String cell = e[i];
				char[] chars = cell.toCharArray();
				int cellLen = 0;
				//每个汉字占两字符长度
				for (int j = 0; j < chars.length; j++) {
					cellLen = isChinese(chars[j]) ? cellLen + 2 : cellLen + 1;
				}
				cellLengths[i] = Math.max(cellLengths[i], cellLen);
			}
		});
	}

	public void print(String[] stringArray) {
		for(int i=0;i<stringArray.length;i++){
			String str = stringArray[i];
			int maxLen = cellLengths[i];
			fmtPrint(str,maxLen);
		}
		System.out.println();
	}

	public String generatePrintString(){
		StringBuffer sb = new StringBuffer();
		list.forEach(e -> {
			for (int i = 0; i < e.length; i++) {
				String cell = e[i];
				int maxLen = cellLengths[i];
				sb.append(fmtString(cell,maxLen));
			}
			sb.append("\n");
		});
		return sb.toString();
	}
	private void fmtPrint(String str, int maxLen) {
		System.out.print(fmtString(str,maxLen));
	}

	private String fmtString(String str, int maxLen){
		StringBuffer sb = new StringBuffer(str);
		int len = len(str);
		while (len < maxLen) {
			sb.append(" ");
			len++;
		}
		return sb.toString();
	}
	private int len(String str) {
		char[] values = str.toCharArray();
		int len = 0;
		for (int i = 0; i < values.length; i++) {
			char c = values[i];
			if (isChinese(c)) {
				len += 2;
			} else {
				len++;
			}
		}
		return len;
	}

	private boolean isChinese(char ch) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		List<String[]> list = Arrays.asList(new String[][]{
				new String[]{"CentOS","1","成功"},
				new String[]{"结束sendmail地址","account@domain.com","失败"},
				new String[]{"发送邮件","user@host.your.domain.name","成功"},
				new String[]{"配置宏文件Addr","基础命令","成功"},
				new String[]{"默认从标砖输入读取内容","373","成功"},
				new String[]{"但目前主流的邮箱都会将源地址","1","成功"}
		});
		System.out.println(new FmtPrint(list).generatePrintString());
	}
}
