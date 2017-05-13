package by.gomelagro.outcoming.status;

public enum Status {
	COMPLETED,
	COMPLETED_SIGNED,
	ON_AGREEMENT,
	CANCELLED,
	ON_AGREEMENT_CANCEL,
	IN_PROGRESS,
	NOT_FOUND,
	ERROR;
	
	public String getRuValue(){
		String value = "";
		switch(this){
			case COMPLETED: value = "���������"; break;
			case COMPLETED_SIGNED: value = "���������. �������� �����������"; break;
			case ON_AGREEMENT: value = "�� ������������"; break;
			case CANCELLED: value = "�����������"; break;
			case ON_AGREEMENT_CANCEL: value = "���������. ����������� �����������"; break;
			case IN_PROGRESS: value = "���� ��������� � ���������. ��������� ������ �������� ����� 3 ����"; break;
			case NOT_FOUND: value = "���� ��� � ���� ��� ��� ����� ��� ��������� �������/�������� ���������"; break;
			case ERROR: value = "������ ��� ����������� ���� �� ������"; break;
			default: value = null; break;
		}
		return value;
	}
	
	public static String valueEnOf(String enStatus){
		String value = "";
		switch(enStatus){
			case "COMPLETED": value = "���������"; break;
			case "COMPLETED_SIGNED": value = "���������. �������� �����������"; break;
			case "ON_AGREEMENT": value = "�� ������������"; break;
			case "CANCELLED": value = "�����������"; break;
			case "ON_AGREEMENT_CANCEL": value = "���������. ����������� �����������"; break;
			case "IN_PROGRESS": value = "���� ��������� � ���������. ��������� ������ �������� ����� 3 ����"; break;
			case "NOT_FOUND": value = "���� ��� � ���� ��� ��� ����� ��� ��������� �������/�������� ���������"; break;
			case "ERROR": value = "������ ��� ����������� ���� �� ������"; break;
			default: value = null; break;
		}
		return value;
	}
	
	public String getEnValue(){
		String value = "";
		switch(this){
			case COMPLETED: value = "COMPLETED"; break;
			case COMPLETED_SIGNED: value = "COMPLETED_SIGNED"; break;
			case ON_AGREEMENT: value = "ON_AGREEMENT"; break;
			case CANCELLED: value = "CANCELLED"; break;
			case ON_AGREEMENT_CANCEL: value = "ON_AGREEMENT_CANCEL"; break;
			case IN_PROGRESS: value = "IN_PROGRESS"; break;
			case NOT_FOUND: value = "NOT_FOUND"; break;
			case ERROR: value = "ERROR"; break;
			default: value = null; break;
		}
		return value;
	}
	
	public static String valueRuOf(String ruStatus){
		String value = "";
		switch(ruStatus){
		case "���������": value = "COMPLETED"; break;
		case "���������. �������� �����������": value = "COMPLETED_SIGNED"; break;
		case "�� ������������": value = "ON_AGREEMENT"; break;
		case "�����������": value = "CANCELLED"; break;
		case "���������. ����������� �����������": value = "ON_AGREEMENT_CANCEL"; break;
		case "���� ��������� � ���������. ��������� ������ �������� ����� 3 ����": value = "IN_PROGRESS"; break;
		case "���� ��� � ���� ��� ��� ����� ��� ��������� �������/�������� ���������": value = "NOT_FOUND"; break;
		case "������ ��� ����������� ���� �� ������": value = "ERROR"; break;
		default: value = "��������� ������"; break;
		}
		return value;
	}
}

