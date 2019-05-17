/*
	Network Microproject (6th Semester, CS692)

	TestEncrypt
	Tests methods of Encrypt class on a String.

	Oishik M | 18 May 2019
*/

import java.util.Scanner;
public class TestEncrypt {
	public static void main(String[] args) {
		Encrypt enc = new Encrypt();
		String str, cstr, pstr;
		Scanner cin = new Scanner(System.in);
		System.out.print("\nEnter String : ");
		str = cin.next();
		cstr = enc.encrypt(str);
		System.out.print("\nEncrypted String : " + cstr);
		pstr = enc.decrypt(cstr);
		System.out.print("\nDecrypted String : " + pstr);
	}
}