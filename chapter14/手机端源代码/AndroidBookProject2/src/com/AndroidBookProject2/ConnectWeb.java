package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class ConnectWeb {
	public static String path = "http://chinaroadbook.xicp.net:8080/AndroidWeb/";

	// 访问网站数据库获取数据
	private String connWeb(String url) {
		String str = "";
		try {
			HttpGet request = new HttpGet(url); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				str = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 获取推荐商品
	public List<Goods> getPopList() {
		List<Goods> mylist = new ArrayList<Goods>();
		String url = path + "goodsAction.action?type=pop";
		String str = connWeb(url);

		try {
			JSONObject job = new JSONObject(str);
			JSONArray jay = job.getJSONArray("glist");
			for (int i = 0; i < jay.length(); i += 1) {
				JSONObject temp = (JSONObject) jay.get(i);
				Goods goods = new Goods();
				goods.setId(temp.getInt("id"));
				goods.setBrand(temp.getString("brand"));
				goods.setPrice((float) temp.getDouble("price"));
				goods.setDiscount((float) temp.getDouble("discount"));
				goods.setBcount(temp.getInt("bcount"));
				goods.setDes(temp.getString("des"));
				goods.setPic(temp.getString("pic"));
				goods.setDir(path + temp.getString("dir"));
				goods.setGid(temp.getString("gid"));
				goods.setType(temp.getInt("type"));
				goods.setPop(temp.getInt("pop"));
				mylist.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist;
	}

	// 获取推荐商品
	// 1 家用电器 2 手机数码 3 电脑办公
	public List<Goods> getTypeList(int type) {
		List<Goods> mylist = new ArrayList<Goods>();
		String url = path + "goodsAction.action?type=type&gtype=" + type;
		String str = connWeb(url);

		try {
			JSONObject job = new JSONObject(str);
			JSONArray jay = job.getJSONArray("glist");
			for (int i = 0; i < jay.length(); i += 1) {
				JSONObject temp = (JSONObject) jay.get(i);
				Goods goods = new Goods();
				goods.setId(temp.getInt("id"));
				goods.setBrand(temp.getString("brand"));
				goods.setPrice((float) temp.getDouble("price"));
				goods.setDiscount((float) temp.getDouble("discount"));
				goods.setBcount(temp.getInt("bcount"));
				goods.setDes(temp.getString("des"));
				goods.setPic(temp.getString("pic"));
				goods.setDir(path + temp.getString("dir"));
				goods.setGid(temp.getString("gid"));
				goods.setType(temp.getInt("type"));
				goods.setPop(temp.getInt("pop"));
				mylist.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist;
	}
	
	//用户登录判断
	public User userLogin(String uid,String pwd) {
		User user=null;
		String url = path + "usersAction.action?uid="+uid+"&pwd="+pwd;
		String str = connWeb(url);
		try {
			JSONObject job = new JSONObject(str);
			JSONObject job2 =(JSONObject) job.get("user");

			user=new User();
			user.setId(job2.getInt("id"));
			user.setUid(job2.getString("uid"));
			user.setUserPwd(job2.getString("pwd"));
			System.out.println("aaa:"+job.getInt("id")+","+job.getString("uid")+","+job.getString("pwd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//获取用户订单
	public List<BillEntity> getBillList(String uid) {
		List<BillEntity> mylist = new ArrayList<BillEntity>();
		String url = path + "billAction.action?type=list&uid="+uid;
		String str = connWeb(url);
System.out.println("str:"+str);
		try {
			JSONObject job = new JSONObject(str);
			JSONArray jay = job.getJSONArray("blist");
			for (int i = 0; i < jay.length(); i += 1) {
				JSONObject temp = (JSONObject) jay.get(i);
				BillEntity be = new BillEntity();
				be.setId(temp.getInt("id"));
				be.setState(temp.getString("state"));
				be.setBtime(temp.getString("btime"));
				be.setBtype(temp.getString("btype"));
				be.setCtime(temp.getString("ctime"));
				List<GoodsListEntity> glist=new ArrayList<GoodsListEntity>();
				JSONArray gl = temp.getJSONArray("glist");
				for (int j = 0; j < gl.length(); j += 1) {
					GoodsListEntity ge=new GoodsListEntity();
					JSONObject gtemp = (JSONObject) gl.get(j);
					ge.setGname(gtemp.getString("gname"));
					ge.setGnum(gtemp.getInt("gnum"));
					glist.add(ge);
				}
				be.setGlist(glist);
				mylist.add(be);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist;
	}
	
	//增加用户订单
	/*
	 * uid 用户登录id
	 * gids 商品数据库编号，多个商品之间用,分开   如 1,5
	 * gnums 商品数量，多个数量之间用,分开   如 1,1  注意一个商品数据库编号对应一个商品数量
	 * btime  送货时间   周一至周五/周末
	 * btype  付款方式   现金/信用卡
	 * address  地址
	 * 
	 */
	public boolean addBill(String uid,String gids,String gnums,String btime,String btype,String address) {
		boolean pan=true;
		System.out.println("gid:"+gids+",gnums:"+gnums+",btype:"+btype+",address:"+address);
		String url = path + "billAction.action?type=add&uid="+uid+"&gids="+gids+"&gnums="+gnums+"&btime="+btime+"&btype="+btype+"&address="+address;
		String str = connWeb(url);

		try {
			JSONObject job = new JSONObject(str);
			pan=job.getBoolean("msg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pan;
	}
}
