package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.data.InvoiceSectionIntf;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.InvoiceSubSectionListIntf;

public class Validation {
	/*
	 * Проверка поля на его наличие или на наличие значения
	 */
	public static boolean verifyField(String field){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(field != null){
			//если в поле есть символы
			if(field.trim().length() > 0){
				//значение поля найдено
				result = true;
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyField(String field, String value){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(field != null){
			//если в поле есть символы
			if(field.trim().length() > 0){
				//если значение поля равно введенному значению 
				if(field.trim().equals(value)){
					result = true;
				}else{
					result = false;
				}
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifySection(InvoiceSectionIntf invoice){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(invoice != null){
			//значение поля найдено
			result = true;
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyList(InvoiceSubSectionListIntf invoice){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(invoice != null){
			//если в поле есть символы
			if(invoice.size() > 0){
				//значение поля найдено
				result = true;
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldMore(String field, int length){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(field != null){
			//если в поле есть символы
			if(field.trim().length() >= length){
				//значение поля найдено
				result = true;
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldLess(String field, int length){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(field != null){
			//если в поле есть символы
			if(field.trim().length() <= length){
				//значение поля найдено
				result = true;
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldEqual(String field, int length){
		//значение поля найдено
		boolean result = true;
		//если поле существует
		if(field != null){
			//если в поле есть символы
			if(field.trim().length() == length){
				//значение поля найдено
				result = true;
			}else{//иначе - не найдено
				result = false;
			}
		}else{//иначе - не найдено
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldBetween(String field, int left, int right){
		boolean result = true;
		if(left >= right){
			if(field != null){
				if((field.trim().length() >= left)&&(field.trim().length() <= right)){
					result = true;
				}else{
					result = false;
				}
			}else{
				result = false;
			}
		}else{
			result = false;
		}
		return result;
	}
	
}
