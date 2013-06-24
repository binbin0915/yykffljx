package com.aw.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aw.bean.Bill;
import com.aw.dao.BillDAO;
import com.aw.entity.BillEntity;

public class BillBIZ {
	public List<BillEntity> getBillListByUid(String uid){
		return new BillDAO().getBillListByUid(uid);
	}
	
	public boolean addBill(String uid,String gids,String gnums,String btime,String btype,String address){
		Bill bill=new Bill();
		bill.setUid(uid);
		bill.setGids(gids);
		bill.setGnums(gnums);
		bill.setBtime(changeToWord(btime));
		bill.setBtype(changeToWord(btype));
		bill.setAddress(changeToWord(address));
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		bill.setCtime(s.format(new Date()));
		bill.setState("waiting");
		return new BillDAO().addBill(bill);
	}
	
	private static String changeToWord(String str) {
		String retData = null;
		String tempStr = new String(str);
		String[] chStr = new String[str.length()/4];
		for(int i=0;i<str.length();i++){
			if(i%4==3){
				chStr[i/4] = new String(tempStr.substring(0, 4));
				tempStr = tempStr.substring(4, tempStr.length());
			}
		}
		char[] retChar = new char[chStr.length];
		for(int i=0;i<chStr.length;i++){
			retChar[i] = (char) Integer.parseInt(chStr[i], 16);
		}
		retData = String.valueOf(retChar, 0, retChar.length);
		return retData;
	}
}
