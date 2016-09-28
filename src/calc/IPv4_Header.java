package calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IPv4_Header {
	//TODO: (1) add IP header checksum and computing logic
	//TODO: (2) add input integrity checks
	private int version = 0;
	private int ihl = 0; // IP header length
	private int tos = 0; // type of service
	private int total_length = 0;
	private int id = 0; // identification
	private String flag = "000";
	private int fragment_offset = 0;
	private int ttl = 0;
	private int protocol = 0;
	private String s_ip = ""; // source IP address
	private String d_ip =""; // destination IP address

	String[] input_text = {
			"Version: ",
			"IP Header Length (IHL): ",
			"type of service (TOS): ",
			"total length: ",
			"ID: ",
			"flag: ",
			"fragment-offset: ",
			"time-to-live (TTL): ",
			"protocol: ",
			"source IP adress: ",
			"target IP adress: "
	};

	private ArrayList<String> header = new ArrayList<String>();
	private ArrayList<String> b_header = new ArrayList<String>();

	public void setVersion(int a) {
		version = a;
	}
	public void setIHL(int a) {
		ihl = a;
	}
	public void setTOS(int a) {
		tos = a;
	}
	public void setTotalLength(int a) {
		total_length = a;
	}
	public void setID(int a) {
		id = a;
	}
	public void setFlag(String s) {
		flag = s;
	}
	public void setFragmentOffset(int a) {
		fragment_offset = a;
	}
	public void setTTL(int a) {
		ttl = a;
	}
	public void setProtocol(int a) {
		protocol = a;
	}
	public void setSIP(String s) {
		s_ip = s;
	}
	public void setDIP(String s) {
		d_ip = s;
	}

	public void setHeader() {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			for (int i = 0; i < 11; i++) {
				System.out.print(input_text[i]);
				header.add(bReader.readLine());
				if (header.size() == 11) {
					this.setVersion(Integer.parseInt(header.get(0)));
					this.setIHL(Integer.parseInt(header.get(1)));
					//ihl = Integer.parseInt(header.get(1)) * 0,25; // convert input bytes to bit and then divide by 32 (ihl*8/32)
					this.setTOS(Integer.parseInt(header.get(2)));
					this.setTotalLength(Integer.parseInt(header.get(3)));
					this.setID(Integer.parseInt(header.get(4)));
					this.setFlag(header.get(5));
					this.setFragmentOffset(Integer.parseInt(header.get(6)));
					this.setTTL(Integer.parseInt(header.get(7)));
					this.setProtocol(Integer.parseInt(header.get(8)));
					this.setSIP(header.get(9));
					this.setDIP(header.get(10));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void printStr() {
		String output = "";
		char seperator = '-';
		int i = 0;
		for (String string : header) {
			if (i < header.size()-1) {
				output = output + string + seperator;
				i++;
			} else {
				output = output + string;
			}
		}
		System.out.println("\n"	+ "Header information:\n" + output);
	}
	public String leadingZero(String s, int b) {
		while (s.length() < b) {
			s = '0' + s;
		}
		return s;
	}
	public String arrayListToString(ArrayList<String> array) {
		String out = "";
		System.out.println(array.size());
		for (int i = 0; i < array.size(); i++) {
			out += array.get(i);
			if(i != (array.size() - 1)) {
				out += " ";
			}
		}
		return out;
	}
	public void toBinary() {
		b_header.add(leadingZero(Integer.toBinaryString(version), 4));
		b_header.add(leadingZero(Integer.toBinaryString(ihl), 4));
		b_header.add(leadingZero(Integer.toBinaryString(tos), 8));
		// TEST: print the first 16 bit;
		String test = b_header.get(0).toString() + b_header.get(1).toString() + b_header.get(2).toString();
		int dec = Integer.parseInt(test, 2);
		String hex = Integer.toString(dec, 16);
		System.out.println(hex);
		// TEST end
		b_header.add(leadingZero(Integer.toBinaryString(total_length),16));
		b_header.add(leadingZero(Integer.toBinaryString(id), 16));
		b_header.add(flag);
		b_header.add(leadingZero(Integer.toBinaryString(fragment_offset), 13));
		b_header.add(leadingZero(Integer.toBinaryString(ttl), 8));
		b_header.add(leadingZero(Integer.toBinaryString(protocol), 8));

		// source IP address to binary
		String[] sip_temp = s_ip.split("\\."); // splits the ip address per "." and returns an array
		String b_sip = ""; // binary string
		for (int i = 0; i < sip_temp.length; i++) {
			String x = leadingZero(Integer.toBinaryString(Integer.parseInt(sip_temp[i])), 8);
			b_sip = b_sip + x;
		}
		b_header.add(b_sip);
		// destination IP address to binary
		String[] tip_temp = d_ip.split("\\.");
		String b_tip= "";
		for (int i = 0; i < tip_temp.length; i++) {
			String x = leadingZero(Integer.toBinaryString(Integer.parseInt(tip_temp[i])), 8);
			b_tip = b_tip + x;
		}
		b_header.add(b_tip);

		System.out.println("\n"
				+ "binary header information:"
				+ arrayListToString(b_header));
	}
}
