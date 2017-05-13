package by.gomelagro.outcoming.gui.frames;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.format.lines.HtmlLines;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.InvoicePanel;

public class ShowInvoiceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	

	/**
	 * Create the frame.
	 */
	
	private InvoicePanel invoicePanel;
	
	public ShowInvoiceFrame(){}
	
	public ShowInvoiceFrame initialize(Invoice invoice){
		String date = " от ";
		try {
			date = date + new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getGeneral().getDateTransaction()));
		} catch (ParseException e) {
			date = "";
		}
		setTitle("ЭСЧФ "+invoice.getGeneral().getNumber()+date);
		invoicePanel = new InvoicePanel(invoice);
		setType(Type.UTILITY);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 950, 700);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Основные"}), null, invoicePanel.getGeneralPane(), null);
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Реквизиты","поставика"}), null, invoicePanel.getProviderPane(), null);
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Реквизиты","получателя"}), null, invoicePanel.getRecipientPane(), null);		
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Реквизиты","грузоотправителя","и грузополучателя"}), null, invoicePanel.getSenderReceiverPanel(), null);
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Условия","поставки"}), null, invoicePanel.getDeliveryConditionPanel(), null);
		tabbedPane.addTab(HtmlLines.getHtmlText(new String[]{"Данные","по товарам"}), null, invoicePanel.getRosterPane(), null);
		
		return this;
	}
}

