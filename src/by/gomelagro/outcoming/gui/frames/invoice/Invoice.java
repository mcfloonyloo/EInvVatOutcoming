package by.gomelagro.outcoming.gui.frames.invoice;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import by.gomelagro.outcoming.gui.frames.invoice.data.*;

public class Invoice {
		
	private String sender;
	private General general;
	private Provider provider;
	private Recipient recipient;
	private SenderReceiver senderReceiver;
	private DeliveryCondition deliveryCondition;
	private Roster roster;
	private byte[] content;
	private byte[] xsdSchema;
	
	public String getSender(){return sender;}
	public General getGeneral(){return general;}
	public Provider getProvider(){return provider;}
	public Recipient getRecipient(){return recipient;}
	public SenderReceiver getSenderReceiver(){return senderReceiver;}
	public DeliveryCondition getDeliveryCondition(){return deliveryCondition;}
	public Roster getRoster(){return roster;}
	public byte[] getContent(){return content;}
	public byte[] getXsdSchema(){return xsdSchema;}
	
	public Invoice setSender(String sender){
		this.sender = sender;
		return this;
	}
	public Invoice setGeneral(General general){
		this.general = general;
		return this;
	}
	public Invoice setProvider(Provider provider){
		this.provider = provider;
		return this;
	}
	public Invoice setRecipient(Recipient recipient){
		this.recipient = recipient;
		return this;
	}
	public Invoice setSenderReceiver(SenderReceiver senderReceiver){
		this.senderReceiver = senderReceiver;
		return this;
	}
	public Invoice setDeliveryCondition(DeliveryCondition deliveryCondition){
		this.deliveryCondition = deliveryCondition;
		return this;
	}
	public Invoice setRoster(Roster roster){
		this.roster = roster;
		return this;
	}
	public Invoice setContent(byte[] content){
		this.content = content;
		return this;
	}
	public Invoice setXsdSchema(byte[] xsdSchema){
		this.xsdSchema = xsdSchema;
		return this;
	}
	
	public Invoice setConvertContent(byte[] content){
		String strData;
		try {
			strData = new String(content,"Cp1251");
			this.content = strData.getBytes(Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			this.content = null;
		}
		return this;
	}	
	
	public Invoice(){}
}
