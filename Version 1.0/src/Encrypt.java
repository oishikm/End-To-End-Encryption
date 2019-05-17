/*
	Network Microproject (6th Semester, CS692)

	Encrypt
	Library for end-to-end encryption.
	Uses cipherkey based on current date.

	Oishik M | 18 May 2019
*/


import java.util.Calendar;

public class Encrypt {
	
	public char ckey;	// Cipherkey
	
	public Encrypt() {
		int prime, day, flag, i, j;
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		prime = 3;
		for(i=day; i<99; i++) {	// i<99 to avoid infinite loop during any failure.
			flag = 1;
			for(j=2;j<i/2;j++) {
				if(i%j == 0) {
					flag = 0;
					break;
				}
			}
			if(flag == 1) {
				prime = i;
				break;
			}
		}
		ckey = (char)prime;
	}

	public String runXOR(String istr) {
		if(istr.equals("END") == true) {
			return istr;
		}
		else {
			String ostr = "", temp;
			char ith;
			int i;
			for(i=0;i<istr.length();i++) {
				ith = (char)(istr.charAt(i) ^ ckey);	// XOR Encryption
				temp = String.valueOf(ith);
				ostr = ostr + temp;
			}
			return ostr;
		}
	}
	
	public String encrypt(String ptext) {
		String ctext = runXOR(ptext);
		return ctext;
	}

	public String decrypt(String ctext) {
		String ptext = runXOR(ctext);
		return ptext;
	}
}