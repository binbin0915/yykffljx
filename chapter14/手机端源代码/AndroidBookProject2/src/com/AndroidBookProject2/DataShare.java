package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.List;

public class DataShare {	
	public static User user=new User();//���浱ǰ��¼�û�����Ϣ	
	public static List<Goods> shopList=new ArrayList<Goods>();//���ﳵ�б�
	/**
	 * �ж��Ƿ��Ѿ������һ������Ʒ ����ֵ
	 * @param id����Ʒ���
	 * @return��-1��δ��ӹ�����Ʒ����������ӹ�����Ʒ
	 */
	public static int isExistGoods(int id){
		for(int i=0;i<shopList.size();i++){
			if(shopList.get(i).getId()==id){
				return i;
			}
		}
		return -1;
	}
	/**
	 * ��ȡ���ﳵ����Ʒ���ܼ۸�
	 * @return
	 */
	public static float getCartListMoney(){
		float money=0.0f;
		for(int i=0;i<shopList.size();i++){
			money=money+shopList.get(i).getPrice()*shopList.get(i).getBuyCount();
		}
		return money;
	}
}
