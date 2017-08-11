package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.data.InvoiceSectionIntf;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.InvoiceSubSectionListIntf;

public class Validation {
	/*
	 * �������� ���� �� ��� ������� ��� �� ������� ��������
	 */
	public static boolean verifyField(String field){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(field != null){
			//���� � ���� ���� �������
			if(field.trim().length() > 0){
				//�������� ���� �������
				result = true;
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifyField(String field, String value){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(field != null){
			//���� � ���� ���� �������
			if(field.trim().length() > 0){
				//���� �������� ���� ����� ���������� �������� 
				if(field.trim().equals(value)){
					result = true;
				}else{
					result = false;
				}
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifySection(InvoiceSectionIntf invoice){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(invoice != null){
			//�������� ���� �������
			result = true;
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifyList(InvoiceSubSectionListIntf invoice){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(invoice != null){
			//���� � ���� ���� �������
			if(invoice.size() > 0){
				//�������� ���� �������
				result = true;
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldMore(String field, int length){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(field != null){
			//���� � ���� ���� �������
			if(field.trim().length() >= length){
				//�������� ���� �������
				result = true;
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldLess(String field, int length){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(field != null){
			//���� � ���� ���� �������
			if(field.trim().length() <= length){
				//�������� ���� �������
				result = true;
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
			result = false;
		}
		return result;
	}
	
	public static boolean verifyFieldEqual(String field, int length){
		//�������� ���� �������
		boolean result = true;
		//���� ���� ����������
		if(field != null){
			//���� � ���� ���� �������
			if(field.trim().length() == length){
				//�������� ���� �������
				result = true;
			}else{//����� - �� �������
				result = false;
			}
		}else{//����� - �� �������
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
