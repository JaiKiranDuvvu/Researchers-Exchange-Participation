/**
 * 
 */
package com.assignment4.business;

import java.io.Serializable;


/**
 * @author Vasudev Ramachandran
 *
 */
public class TempUserModel implements Serializable {

	/**
	 * 
	 */
	public TempUserModel() {
		// TODO Auto-generated constructor stub
	}
	
    private String Name;
    private String Email;
    private String Type;
    private String passWord; 
    
    private int NumCoins;
    private int NumPostedStudies;
    private int NumParticipation;
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}
	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}
	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	/**
	 * @return the numCoins
	 */
	public int getNumCoins() {
		return NumCoins;
	}
	/**
	 * @param numCoins the numCoins to set
	 */
	public void setNumCoins(int numCoins) {
		NumCoins = numCoins;
	}
	/**
	 * @return the numPostedStudies
	 */
	public int getNumPostedStudies() {
		return NumPostedStudies;
	}
	/**
	 * @param numPostedStudies the numPostedStudies to set
	 */
	public void setNumPostedStudies(int numPostedStudies) {
		NumPostedStudies = numPostedStudies;
	}
	/**
	 * @return the numParticipation
	 */
	public int getNumParticipation() {
		return NumParticipation;
	}
	/**
	 * @param numParticipation the numParticipation to set
	 */
	public void setNumParticipation(int numParticipation) {
		NumParticipation = numParticipation;
	}
   

}
