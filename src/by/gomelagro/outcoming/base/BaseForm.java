package by.gomelagro.outcoming.base;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import by.gomelagro.outcoming.gui.frames.MainFrame;

public class BaseForm {
		
		static{
			try {
				javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			}
		}

		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {					
						@SuppressWarnings("unused")
						MainFrame main = new MainFrame();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

}
