package by.gomelagro.outcoming.gui.frames.invoice.test;

import java.sql.SQLException;

import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.Description;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class TablesValuesTest extends AbstractTest{

	public TablesValuesTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		 
		//Проверка табличных значений Country
		if(Validation.verifySection(getInvoice().getProvider())){
			if(Validation.verifyField(getInvoice().getProvider().getCountryCode())){
				try {
					if(WorkingOutcomingTable.Count.isCountryCode(getInvoice().getProvider().getCountryCode())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("Поставщик - код страны: не найдено табличное значение").setFlagError(false));
					}
				} catch (SQLException e) {
					String message = "Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
					System.err.println(message);
					list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Поставщик - код страны: не пройдена верификация поля").setFlagError(false));
			}
		}
	
		if(Validation.verifySection(getInvoice().getRecipient())){
			if(Validation.verifyField(getInvoice().getRecipient().getCountryCode())){
				try{
					if(WorkingOutcomingTable.Count.isCountryCode(getInvoice().getRecipient().getCountryCode())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("Покупатель - код страны: не найдено табличное значение").setFlagError(false));
					}
				} catch (SQLException e) {
					String message = "Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
					System.err.println(message);
					list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Покупатель - код страны: не пройдена верификация поля").setFlagError(false));
			}
		}
		
		if(Validation.verifySection(getInvoice().getSenderReceiver())){
			if(Validation.verifyList(getInvoice().getSenderReceiver().getConsignors())){
				int index = 0;
				for(SenderItem item: getInvoice().getSenderReceiver().getConsignors()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifyField(item.getCountryCode())){
							try{
								if(WorkingOutcomingTable.Count.isCountryCode(item.getCountryCode())){
									list.add(new ErrorBooleanItem().setFlagError(true));
								}else{
									list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузоотправителя - код страны: не найдено табличное значение").setFlagError(false));
								}
							} catch (SQLException e) {
								String message = "Строка "+index+". Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
								System.err.println(message);
								list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузоотправителя - код страны: не пройдена верификация поля").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузоотправителя: отсутствует строка значений").setFlagError(false));
					}
				}
			}
			if(Validation.verifyList(getInvoice().getSenderReceiver().getConsignees())){
				if(Validation.verifyList(getInvoice().getSenderReceiver().getConsignees())){
					int index = 0;
					for(SenderItem item: getInvoice().getSenderReceiver().getConsignees()){
						index++;
						if(Validation.verifySection(item)){
							if(Validation.verifyField(item.getCountryCode())){
								try{
									if(WorkingOutcomingTable.Count.isCountryCode(item.getCountryCode())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузополучателя - код страны: не найдено табличное значение").setFlagError(false));
									}
								} catch (SQLException e) {
									String message = "Строка "+index+". Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
									System.err.println(message);
									list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
								}
							}else{
								list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузополучателя - код страны: не пройдена верификация поля").setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Реквизиты грузополучателя: отсутствует строка значений").setFlagError(false));
						}
					}
				}
			}
		}
		
		//проверка табличных значений Branch
		if(Validation.verifySection(getInvoice().getProvider())){
			if(Validation.verifyField(getInvoice().getProvider().getBranchCode())){
				try {
					if(WorkingOutcomingTable.Count.isBranch(getInvoice().getProvider().getBranchCode())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("Поставщик - код филиала: не найдено табличное значение").setFlagError(false));
					}
				} catch (SQLException e) {
					String message = "Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
					System.err.println(message);
					list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
				}
			}
		}
		if(Validation.verifySection(getInvoice().getRecipient())){
			if(Validation.verifyField(getInvoice().getRecipient().getBranchCode())){
				try {
					if(WorkingOutcomingTable.Count.isBranch(getInvoice().getRecipient().getBranchCode())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("Покупатель - код филиала: не найдено табличное значение").setFlagError(false));
					}
				} catch (SQLException e) {
					String message = "Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
					System.err.println(message);
					list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
				}
			}
		}
		
		//проверка значения DocumentType
		if(Validation.verifySection(getInvoice().getDeliveryCondition())){
			if(Validation.verifySection(getInvoice().getDeliveryCondition().getContract())){
				if(Validation.verifyList(getInvoice().getDeliveryCondition().getContract().getDocuments())){
					int index = 0;
					for(DocumentItem item: getInvoice().getDeliveryCondition().getContract().getDocuments()){
						index++;
						if(Validation.verifySection(item)){
							if(Validation.verifySection(item.getDocType())){
								if(Validation.verifyField(item.getDocType().getCode())){
									try {
										if(WorkingOutcomingTable.Count.isTypeDocument(item.getDocType().getCode())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код документа: не найдено табличное значение").setFlagError(false));
										}
									} catch (SQLException e) {
										String message = "Строка "+index+". Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
										System.err.println(message);
										list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
									}
								}else{
									list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код документа: не пройдена верификация поля").setFlagError(false));
								}
							}else{
								list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - документ: не пройдена верификация секции").setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - документ: отсутствует строка значений").setFlagError(false));
						}
					}
				}
			}
		}
		
		//Проверка значения Invoice DocumentType
		if(Validation.verifySection(getInvoice().getGeneral())){
			if(Validation.verifyField(getInvoice().getGeneral().getDocumentType())){
				if(InnerVerification.isInvoiceDocType(getInvoice().getGeneral().getDocumentType())){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("Основная информация - тип ЭСЧФ: не найдено табличное значение").setFlagError(false));
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Основная информация - тип ЭСЧФ: не пройдена верификация поля").setFlagError(false));
			}
		}
		
		//Проверка значения Provider StatusType
		if(Validation.verifySection(getInvoice().getProvider())){
			if(Validation.verifyField(getInvoice().getProvider().getProviderStatus())){
				if(InnerVerification.isProviderStatusType(getInvoice().getProvider().getProviderStatus())){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("Поставщик - статус: не найдено табличное значение").setFlagError(false));
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Поставщик - статус: не пройдена верификация поля").setFlagError(false));
			}
		}
		
		//Проверка значения Recipient StatusType
		if(Validation.verifySection(getInvoice().getRecipient())){
			if(Validation.verifyField(getInvoice().getRecipient().getRecipientStatus())){
				if(InnerVerification.isRecipientStatusType(getInvoice().getRecipient().getRecipientStatus())){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("Покупатель - статус: не найдено табличное значение").setFlagError(false));
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Покупатель - статус: не пройдена верификация поля").setFlagError(false));
			}
		}
		
		//Проверка значения RosterItem DescriptionType
		if(Validation.verifySection(getInvoice().getRoster())){
			if(Validation.verifyList(getInvoice().getRoster().getRosters())){
				int index = 0;
				for(RosterItem item: getInvoice().getRoster().getRosters()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifyList(item.getDescriptions())){
							for(Description description: item.getDescriptions()){
								if(Validation.verifySection(description)){
									if(InnerVerification.isDescriptionType(description.getDescription())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - тип дополнительных данных: не найдено табличное значение").setFlagError(false));
									}
								}else{
									list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - тип дополнительных данных: не пройдена верификация поля").setFlagError(false));
								}
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - дополнительные данные: не пройдена верификация секции").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам: отсутствует строка значений").setFlagError(false));
					}
				}
			}
		}
		
		//Проверка значения RosterItem RateType
		if(Validation.verifySection(getInvoice().getRoster())){
			if(Validation.verifyList(getInvoice().getRoster().getRosters())){
				int index = 0;
				for(RosterItem item: getInvoice().getRoster().getRosters()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifySection(item.getVat())){
							if(Validation.verifyField(item.getVat().getRateType())){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - тип НДС: не пройдена верификация поля").setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - НДС: не пройдена верификация секции").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам: отсутствует строка значений").setFlagError(false));
					}
				}
			}
		}
		
		//проверка табличных значений Unit
		if(Validation.verifySection(getInvoice().getRoster())){
			if(Validation.verifyList(getInvoice().getRoster().getRosters())){
				int index = 0;
				for(RosterItem item: getInvoice().getRoster().getRosters()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifyField(item.getUnits())){
							try {
								if(WorkingOutcomingTable.Count.isUnit(item.getUnits())){
									list.add(new ErrorBooleanItem().setFlagError(true));
								}else{
									list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - единица измерения: не найдено табличное значение").setFlagError(false));
								}
							} catch (SQLException e) {
								String message = "Строка "+index+". Метод проверки табличных значений ЭСЧФ "+getInvoice().getGeneral().getNumber()+": ошибка получения значения кода страны поставщика";
								System.err.println(message);
								list.add(new ErrorBooleanItem().setError(message).setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам - единица измерения: не пройдена верификация поля").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка "+index+". Данные по товарам: отсутствует строка значений").setFlagError(false));
					}
				}
			}
		}
		return list;
	}

}
