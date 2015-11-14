/**
 * 
 */
package lib;

/**
 * @author ismail
 *
 */
public class KeyOperations {
	
	byte[] append(byte[] key1 , byte[] key2) {
		
		/*
		 * New array that holds whole key
		 */
		byte[] key=new byte[key1.length+key2.length];
		
		/*
		 * Get key lengths
		 */
		int key2_length=key2.length;
		int key1_length=key1.length;
		
		/*
		 * Get first key
		 */
		for(int i=1;i<key1.length;i++){
			key[i] = key1[i];
		}
		
		
		/*
		 * Get second key
		 */
		for(int i=0;i<key2_length;i++){
			key[key1_length+i] = key2[i];
		}
		
		/*
		 * Return new key
		 */
		return key;
		
	}
}
