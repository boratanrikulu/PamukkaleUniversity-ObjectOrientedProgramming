/**
 * @author boratanrikulu
 * If you have any question about the project, you can contact me at http://boratanrikulu.me/contact
 */
package flighttickets.CustomerPages;

import flighttickets.DataBaseProcesses.DataBaseConnecter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PaymentPage extends javax.swing.JFrame {
	
	private Connection connection;
	private int planeId;
	private int customerId;
	private int seatNumber;
	private BuyTicket buyTicket;
	private ChooseSeat chooseSeat;
	private int firstClassPrice;
	private int premiumClassPrice;
	private int mainCabinPrice;
	
	public PaymentPage(int planeId, int customerId, int seatNumber, BuyTicket buyTicket, ChooseSeat chooseSeat) {
		initComponents();
		
		DataBaseConnecter connecter = new DataBaseConnecter(); // makes a connection to the database
		
		this.connection = connecter.getConnection();
		this.planeId = planeId;
		this.customerId = customerId;
		this.seatNumber = seatNumber;
		this.buyTicket = buyTicket;
		this.chooseSeat = chooseSeat;
		
		setPrices(); // set prices for all types
		if(1 <= this.seatNumber && this.seatNumber <= 12) { // shows price by the seat number
			this.jLabel1.setText("The Price: $" + firstClassPrice);
		}
		if(13 <= this.seatNumber && this.seatNumber <= 42) {
			this.jLabel1.setText("The Price: $" + premiumClassPrice);
		}
		if(43 <= this.seatNumber && this.seatNumber <= 159) {
			this.jLabel1.setText("The Price: $" + mainCabinPrice);
		}
	}
	
	public void setPrices() {
		String query = "select * from flights where id = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, this.planeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if( resultSet.next()) { // set prices for all types
				this.firstClassPrice = resultSet.getInt("firstClassPrice");
				this.premiumClassPrice = resultSet.getInt("premiumClassPrice");
				this.mainCabinPrice = resultSet.getInt("mainCabinPrice");
			}
		} catch (SQLException ex) {
			Logger.getLogger(PaymentPage.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void buyTheTicket(int planeId, int customerId, int seatNumber) {
		int seatCapacityTemp = 0;
		String query = "insert into sold_tickets(planeId, customerId, seatNumber) values(?, ?, ?)" ;
		String query2 = "select * from flights where id = ?";
		String query3 = "update flights set seatCapacity = ? where id = ?";
		PreparedStatement preparedStatement = null;
		
		try {			
			if (JOptionPane.showConfirmDialog(this, "Are you sure to buy the ticket ?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
				preparedStatement = connection.prepareStatement(query2);
				preparedStatement.setInt(1, planeId);
				ResultSet resultSet = preparedStatement.executeQuery();
				if( resultSet.next()) { // gets the seatCapacity value of the flight
					seatCapacityTemp = resultSet.getInt("seatCapacity");
				}
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, planeId);
				preparedStatement.setInt(2, customerId);
				preparedStatement.setInt(3, seatNumber);
				preparedStatement.executeUpdate(); // buys the ticket
				JOptionPane.showMessageDialog(this, "The ticket was bought by you.");
				
				seatCapacityTemp--;
				preparedStatement = connection.prepareStatement(query3);
				preparedStatement.setInt(1, seatCapacityTemp);
				preparedStatement.setInt(2, planeId);
				preparedStatement.executeUpdate(); // decreases the seatCapacity
				
				buyTicket.showAllFlights(); // refreshes the buyTicket object
				this.setVisible(false);
				chooseSeat.setUnvisibleTakenSeats(); // make invisible the seat that is taken
			}
		} catch (SQLException ex) {
			Logger.getLogger(ChooseSeat.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	 @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                payThePriceButton = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                jPanel2 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                cardNumberArea = new javax.swing.JTextField();
                expireDateArea2 = new javax.swing.JTextField();
                cvc2Area = new javax.swing.JTextField();
                expireDateArea1 = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("Flight Tickets");
                setBounds(new java.awt.Rectangle(630, 555, 0, 0));
                setResizable(false);

                jPanel1.setBackground(new java.awt.Color(225, 255, 255));

                payThePriceButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
                payThePriceButton.setText("Pay The Price");
                payThePriceButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                payThePriceButtonActionPerformed(evt);
                        }
                });

                jLabel1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
                jLabel1.setText("The Price: $300");

                jPanel2.setBackground(new java.awt.Color(192, 241, 255));

                jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                jLabel2.setText("Card Number");

                jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                jLabel3.setText("CVC2");

                jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                jLabel4.setText("Expire Date");

                cardNumberArea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

                expireDateArea2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

                cvc2Area.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

                expireDateArea1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

                jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
                jLabel5.setText("/");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addComponent(jLabel3))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(expireDateArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(expireDateArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(cardNumberArea))
                                        .addComponent(cvc2Area, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(cardNumberArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(expireDateArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(expireDateArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(cvc2Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(51, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(56, 56, 56)
                                                .addComponent(payThePriceButton)))
                                .addContainerGap(45, Short.MAX_VALUE))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(payThePriceButton))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(37, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void payThePriceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payThePriceButtonActionPerformed
		String cardNumber = this.cardNumberArea.getText();
		String expireDate1 = this.expireDateArea1.getText();
		String expireDate2 = this.expireDateArea2.getText();
		String cvc2 = this.cvc2Area.getText();

		if(cardNumber.equals("") || expireDate1.equals("") || expireDate2.equals("") || cvc2.equals("")){
			JOptionPane.showMessageDialog(this, "Please fill out all information."); // show an error if informations is not completed.
		}
		else {
			buyTheTicket(planeId, customerId, seatNumber);	
		}
        }//GEN-LAST:event_payThePriceButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		 /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(PaymentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PaymentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PaymentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PaymentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		 //</editor-fold>

		/* Create and display the form */
		/*java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PaymentPage().setVisible(true);
			}
		});*/
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JTextField cardNumberArea;
        private javax.swing.JTextField cvc2Area;
        private javax.swing.JTextField expireDateArea1;
        private javax.swing.JTextField expireDateArea2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JButton payThePriceButton;
        // End of variables declaration//GEN-END:variables

}
