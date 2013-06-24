package com.tyhj;

import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;

public class WAnalysisFile {
	/**
	 * 读取文件并解密
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public String readFileByByte(Context context, String path) {
		try {
			InputStream iStream = context.getAssets().open(path);// 获取文件路径
			byte[] b = new byte[iStream.available()];// 初始化byte数组
			iStream.read(b);// 读取文件到byte[]
			iStream.close();// 关闭文件流
			b = DESCoder.decrypt(b, Keyfile.ROAD_BOOK_KEY);// 解密
			String content = new String(b);// byte数组转String
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取route对象，根据routeId
	 * 
	 * @param context
	 * @param routeId
	 * @return
	 */
	public Route getRouteById(Context context, String routeId) {
		String path = "SmallRoute/" + routeId + "/" + routeId + "_route.txt";// 获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {// 判断文件字符是否为NULL或空字符
			return setRoute(content);// 调用解析字符串方法，获取route对象
		} else {
			return new Route();// 返回默认route对象
		}
	}

	/**
	 * 获取route对象，根据bigRouteId,routeId
	 * 
	 * @param context
	 * @param bigRouteId
	 * @param routeId
	 * @return
	 */
	public Route getRouteById(Context context, String bigRouteId, String routeId) {
		String path = bigRouteId + "/SmallRoute/" + routeId + "/" + routeId
				+ "_route.txt";// 获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {// 判断文件字符是否为NULL或空字符
			return setRoute(content);// 调用解析字符串方法，获取route对象
		} else {
			return new Route();// 返回默认route对象
		}
	}

	/**
	 * 获取route集合
	 * 
	 * @param context
	 * @return
	 */
	public List<Route> getRouteList(Context context) {
		List<Route> routeList = new ArrayList<Route>();// 初始化Route集合
		try {
			String[] routes = context.getAssets().list("SmallRoute");// 获取Route在资源文件夹的名称数组
			for (int i = 0; i < routes.length; i++) {
				Route route = getRouteById(context, routes[i]);// 调用根据routeId获取Route对象方法
				routeList.add(route);// 添加Route到Route集合
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routeList;// 返回Route集合
	}

	/**
	 * 获取route集合,根据bigRouteId
	 * 
	 * @param context
	 * @param bigRouteId
	 * @return
	 */
	public List<Route> getRouteList(Context context, String bigRouteId) {
		List<Route> routeList = new ArrayList<Route>();// 初始化Route集合
		try {
			String[] routes = context.getAssets().list(
					bigRouteId + "/SmallRoute");// 获取Route在资源文件夹的名称数组
			for (int i = 0; i < routes.length; i++) {
				Route route = getRouteById(context, bigRouteId, routes[i]);// 调用根据bigRouteId，routeId获取Route对象方法
				routeList.add(route);// 添加Route到Route集合
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routeList;// 返回Route集合
	}

	/**
	 * 填充Route对象
	 * 
	 * @param content
	 * @return
	 */
	public Route setRoute(String content) {
		String[] routes = content.split("@#@");// 分割字符串，获取route相应的字符串数组
		Route route = new Route();// 初始化Route对象
		if (routes.length > 0) {
			String[] routeProperty = routes[0].split("!#!");// 分割字符串，获取route相应的字符串数组
			route.setId(routeProperty[0].trim());// 设置Id
			route.setName(routeProperty[1].trim());// 设置Name
			route.setWayPoint(routeProperty[2].trim());// 设置WayPoint
			route.setStartPointName(routeProperty[3].trim());// 设置StartPointName
			route.setStartPoint(routeProperty[4].trim());// 设置StartPoint
			route.setEndPointName(routeProperty[5].trim());// 设置EndPointName
			route.setEndPoint(routeProperty[6].trim());// 设置EndPoint
			route.setCity(routeProperty[7].trim());// 设置City
			route.setMileage(routeProperty[8].trim());// 设置Mileage
			route.setRoadToll(routeProperty[9].trim());// 设置RoadToll
			route.setDrivingTime(routeProperty[10].trim());// 设置DrivingTime
			route.setProposedItinerary(routeProperty[11].trim());// 设置ProposedItinerary
			route.setBestSeason(routeProperty[12].trim());// 设置BestSeason
			route.setRecommend(Integer.parseInt(routeProperty[13].trim()));// 设置Recommend
			route.setScenic(Integer.parseInt(routeProperty[14].trim()));// 设置Scenic
			route.setRenwen(Integer.parseInt(routeProperty[15].trim()));// 设置Renwen
			route.setFood(Integer.parseInt(routeProperty[16].trim()));// 设置Food
			route.setTip(routeProperty[17].trim());// 设置Tip
			route.setTrend(routeProperty[18].trim());// 设置Trend
			route.setImage(routeProperty[19].trim().toLowerCase());// 设置Image
			route.setHighlights(routeProperty[20].trim());// 设置Highlights
			route.setDrivingTips(routeProperty[21].trim());// 设置DrivingTips
			if (routeProperty.length >= 23) {
				route.setDesc(routeProperty[22].trim());// 设置Desc
			}
		}
		return route;// 返回route对象
	}

	/**
	 * 填充PoiPoint对象
	 * 
	 * @param pointProperty
	 * @return
	 */
	public PoiPoint setPoint(String[] pointProperty) {
		PoiPoint point = new PoiPoint();// 初始化PoiPoint对象
		point.setId(pointProperty[0].trim());// 设置Id
		point.setName(pointProperty[1].trim());// 设置Name
		point.setRouteId(pointProperty[2].trim());// 设置RouteId
		point.setMp3Id(pointProperty[3].trim());// 设置Mp3Id
		point.setMp3Path(pointProperty[4].trim());// 设置Mp3Path
		point.setLat(pointProperty[5].trim());// 设置Lat
		point.setLon(pointProperty[6].trim());// 设置Lon
		point.setCategoryId(pointProperty[7].trim());// 设置CategoryId
		String imgString = pointProperty[8].trim();// 获取img字符
		// img
		if (!"".equals(imgString)) {
			List<String> imgList = new ArrayList<String>();// 初始化img集合
			String[] imgs = imgString.split("[|]");// 获取img数组
			for (int j = 0; j < imgs.length; j++) {
				if (!"".equals(imgs[j])) {
					imgList.add(imgs[j].toLowerCase());// 将img添加到img集合
				}
			}
			point.setImgList(imgList);// 设置ImgList
		}
		if ("null".equals(pointProperty[9].trim())) {
			point.setDesc("");// 设置默认Desc
		} else {
			point.setDesc(pointProperty[9].trim());// 设置Desc
		}
		point.setTel(pointProperty[10].trim());// 设置Tel
		point.setAddress(pointProperty[11].trim());// 设置Address
		point.setMp3Range(Integer.parseInt(pointProperty[12].trim()));// 设置Mp3Range
		if (pointProperty.length >= 14) {
			point.setTime(pointProperty[13].trim());// 设置Time
		}
		return point;// 返回PoiPoint对象
	}

	/**
	 * 获取PoiPoint对象，根据PoiPoint的id
	 * 
	 * @param content
	 * @param pointId
	 * @return
	 */
	public PoiPoint getPointById(String content, String pointId) {
		String[] points = content.split("@#@");// 分割字符串
		PoiPoint point = new PoiPoint();// 初始化PoiPoint对象
		if (points.length > 0) {
			for (int i = 0; i < points.length; i++) {
				String[] pointProperty = points[i].split("!#!");// 分割字符串，获取相应的字符串数组
				if (pointProperty[0].equals(pointId)) {
					point = setPoint(pointProperty);// 调用填充PoiPoint对象方法
				}
			}
		}
		return point;// 返回PoiPoint对象
	}

	/**
	 * 获取大路书详情
	 * 
	 * @param context
	 * @return
	 */
	public Route getBigRoute(Context context) {
		String path = "ljls_route.txt";// 获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setRoute(content);// 调用填充Route对象方法，返回Route对象
		} else {
			return new Route();// 返回默认的Route对象
		}
	}

	/**
	 * 获取大路书详情根据bigRouteId)
	 * 
	 * @param context
	 * @param bigRouteId
	 * @return
	 */
	public Route getBigRoute(Context context, String bigRouteId) {
		String path = bigRouteId + "/ljls_route.txt";// 获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			Route route = setRoute(content);// 调用填充Route对象方法，获取Route对象
			if (route != null) {
				String desc = route.getDesc();// 获取Route的Desc，并过滤内容
				if (desc.indexOf("“跨越中国�?途观之旅”之") >= 0) {
					desc = desc.replaceAll("“跨越中国�?途观之旅”之", "");
				}
				if (desc.indexOf("“跨越中国�?途观之旅”�?�?") >= 0) {
					desc = desc.replaceAll("“跨越中国�?途观之旅”�?�?", "");
				}
				route.setDesc(desc);// 设置过滤后的Desc
			}
			return route;// 返回Route对象
		} else {
			return new Route();// 返回默认的Route对象
		}
	}

	/**
	 * 获取大路书集
	 * 
	 * @param context
	 * @return
	 */
	public List<Route> getBigRouteList(Context context) {
		List<Route> routeList = new ArrayList<Route>();// 初始化Route集合
		try {
			String[] routeDirs = context.getAssets().list("");// 获取文件的名称数组
			for (int i = 0; i < routeDirs.length; i++) {
				Route route = new Route();// 初始化Route对象
				String path = routeDirs[i] + "/ljls_route.txt";// 获取文件路径
				String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
				if (content != null && !"".equals(content)) {
					route = setRoute(content);// 调用填充Route对象方法，获取Route对象
					route.setId(routeDirs[i]);// 设置Id
					routeList.add(route);// 添加Route对象到Route集合
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routeList;//返回Route集合
	}

	/**
	 * 获取每条route的PoiPoint集合
	 * 
	 * @param context
	 * @param routeId
	 * @return
	 */
	public List<PoiPoint> getSmallPoiPointList(Context context, String routeId) {
		String path = "SmallRoute/" + routeId + "/" + routeId + "_point.txt";// 获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		List<PoiPoint> pointList = new ArrayList<PoiPoint>();// 初始化PoiPoint集合
		if (content != null && !"".equals(content)) {
			String[] points = content.split("@#@");// 分割字符串
			if (points.length > 0) {
				for (int i = 0; i < points.length; i++) {
					String[] pointProperty = points[i].split("!#!");// 分割字符串
					PoiPoint point = setPoint(pointProperty);// 调用填充PoiPoint对象方法，获取PoiPoint对象
					pointList.add(point);// 添加PoiPoint对象到PoiPoint集合
				}
			}
		}
		return pointList;//返回PoiPoint集合
	}

	/**
	 * 获取每条route的PoiPoint集合
	 * 
	 * @param context
	 * @param routeId
	 * @return
	 */
	public List<PoiPoint> getSmallPoiPointList(Context context, String routeId,
			String bigRouteId) {
		String path = bigRouteId + "/SmallRoute/" + routeId + "/" + routeId
				+ "_point.txt";// 获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		List<PoiPoint> pointList = new ArrayList<PoiPoint>();// 初始化PoiPoint集合
		if (content != null && !"".equals(content)) {
			String[] points = content.split("@#@");// 分割字符串
			if (points.length > 0) {
				for (int i = 0; i < points.length; i++) {
					String[] pointProperty = points[i].split("!#!");// 分割字符串
					PoiPoint point = setPoint(pointProperty);// 调用填充PoiPoint对象方法，获取PoiPoint对象
					pointList.add(point);// 添加PoiPoint对象到PoiPoint集合
				}
			}
		}
		return pointList;//返回PoiPoint集合
	}

	/**
	 * 得到大路书的PoiPoint集合
	 * 
	 * @param context
	 * @return
	 */
	public List<PoiPoint> getBigPoiPointList(Context context) {
		try {
			List<PoiPoint> pointList = new ArrayList<PoiPoint>();// 初始化PoiPoint集合
			String[] routes = context.getAssets().list("SmallRoute");//获取文件名称数组
			for (int i = 0; i < routes.length; i++) {
				List<PoiPoint> pointSmallList = getSmallPoiPointList(context,
						routes[i]);//获取每条route的PoiPoint集合
				for (int j = 0; j < pointSmallList.size(); j++) {
					pointList.add(pointSmallList.get(j));// 添加PoiPoint对象到PoiPoint集合
				}
			}
			pointList = removeRepeatObject(pointList);//调用去重方法，去掉重复的PoiPoint对象
			return pointList;//返回PoiPoint集合
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PoiPoint>();//返回默认PoiPoint集合
		}
	}

	/**
	 * 得到大路书的PoiPoint集合
	 * 
	 * @param context
	 * @return
	 */
	public List<PoiPoint> getBigPoiPointList(Context context, String bigRouteId) {
		try {
			List<PoiPoint> pointList = new ArrayList<PoiPoint>();// 初始化PoiPoint集合
			String[] routes = context.getAssets().list(
					bigRouteId + "/SmallRoute");//获取文件名称数组
			for (int i = 0; i < routes.length; i++) {
				List<PoiPoint> pointSmallList = getSmallPoiPointList(context,
						routes[i], bigRouteId);//获取每条route的PoiPoint集合
				for (int j = 0; j < pointSmallList.size(); j++) {
					pointList.add(pointSmallList.get(j));// 添加PoiPoint对象到PoiPoint集合
				}
			}
			pointList = removeRepeatObject(pointList);//调用去重方法，去掉重复的PoiPoint对象
			return pointList;//返回PoiPoint集合
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PoiPoint>();//返回默认PoiPoint集合
		}
	}

	/**
	 * 去重复
	 * 
	 * @param pointList
	 * @return
	 */
	public List<PoiPoint> removeRepeatObject(List<PoiPoint> pointList) {
		List<PoiPoint> newPointList = new ArrayList<PoiPoint>();// 初始化PoiPoint集合
		Set<String> pointSet = new HashSet<String>();// 初始化Set集合
		for (int i = 0; i < pointList.size(); i++) {
			String source = pointList.get(i).getName();//获取PoiPoint的Name
			if (pointSet.add(source)) {//判断是否加入set集合成功
				newPointList.add(pointList.get(i));// 添加PoiPoint对象到PoiPoint集合
			}
		}
		return newPointList;//返回PoiPoint集合
	}

	/**
	 * 根据route的id查询该route的所有TrackPoint
	 * 
	 * @param routeId
	 * @return
	 */
	public List<TrackPoint> getTrackPointListByRouteId(Context context,
			String routeId) {
		String path = "SmallRoute/" + routeId + "/" + routeId
				+ "_trackPoint.txt";//获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setTrackPointList(content);//调用填充TrackPoint集合，并返回TrackPoint集合
		} else {
			return new ArrayList<TrackPoint>();//返回默认TrackPoint集合
		}
	}

	/**
	 * 根据route的id查询该route的所有TrackPoint
	 * 
	 * @param routeId
	 * @return
	 */
	public List<TrackPoint> getTrackPointListByRouteId(Context context,
			String routeId, String bigRouteId) {
		String path = bigRouteId + "/SmallRoute/" + routeId + "/" + routeId
				+ "_trackPoint.txt";//获取文件路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setTrackPointList(content);//调用填充TrackPoint集合，并返回TrackPoint集合
		} else {
			return new ArrayList<TrackPoint>();//返回默认TrackPoint集合
		}
	}

	/**
	 * 填充TrackPoint集合
	 * 
	 * @param content
	 * @return
	 */
	public List<TrackPoint> setTrackPointList(String content) {
		String[] trackPoints = content.split("@#@");//分割字符串
		List<TrackPoint> trackPointList = new ArrayList<TrackPoint>();//初始化TrackPoint集合
		if (trackPoints.length > 0) {
			for (int i = 0; i < trackPoints.length; i++) {
				String[] trackPointProperty = trackPoints[i].split("!#!");//分割字符串
				TrackPoint trackPoint = new TrackPoint();//初始化TrackPoint对象
				trackPoint.setId(trackPointProperty[0].trim());//设置Id
				trackPoint.setName(trackPointProperty[1].trim());//设置Name
				trackPoint.setRouteId(trackPointProperty[2].trim());//设置RouteId
				trackPoint.setDesc(trackPointProperty[3].trim());//设置Desc
				trackPoint.setLat(trackPointProperty[4].trim());//设置Lat
				trackPoint.setLon(trackPointProperty[5].trim());//设置Lon
				trackPoint.setCategoryId(trackPointProperty[6].trim());//设置CategoryId
				if (trackPointProperty.length >= 8) {
					trackPoint.setTrackPoints(trackPointProperty[7].trim());//设置TrackPoints
				}
				if (trackPointProperty.length >= 9) {
					trackPoint.setTime(trackPointProperty[8].trim());//设置Time
				}
				trackPointList.add(trackPoint);//添加TrackPoint对象到TrackPoint集合
			}
		}
		return trackPointList;//返回TrackPoint集合
	}

	/**
	 * 获取Beetle对象集合
	 * 
	 * @return
	 */
	public List<Beetle> getAllBeetleList(Context context) {
		String path = "Beetle.txt";//获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setBeetleList(content);//调用填充Beetle集合方法，返回Beetle集合
		} else {
			return new ArrayList<Beetle>();//返回默认Beetle集合
		}
	}

	/**
	 * 获取Beetle对象集合
	 * 
	 * @return
	 */
	public List<Beetle> getAllBeetleList(Context context, String bigRouteId) {
		String path = bigRouteId + "/Beetle.txt";//获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setBeetleList(content);//调用填充Beetle集合方法，返回Beetle集合
		} else {
			return new ArrayList<Beetle>();//返回默认Beetle集合
		}
	}

	/**
	 * 根据routeId获取Beetle集合
	 * 
	 * @param context
	 * @param routeId
	 * @return
	 */
	public List<Beetle> getRouteBeetleList(Context context, String routeId) {
		String path = "SmallRoute/" + routeId + "/route_beetle.txt";//获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setBeetleList(content);//调用填充Beetle集合方法，返回Beetle集合
		} else {
			return new ArrayList<Beetle>();//返回默认Beetle集合
		}
	}

	/**
	 * 根据routeId，bigRouteId获取Beetle集合
	 * 
	 * @param context
	 * @param routeId
	 * @return
	 */
	public List<Beetle> getRouteBeetleList(Context context, String routeId,
			String bigRouteId) {
		String path = bigRouteId + "/SmallRoute/" + routeId
				+ "/route_beetle.txt";//获取路径
		String content = readFileByByte(context, path);// 调用文件读取方法，获取字符串
		if (content != null && !"".equals(content)) {
			return setBeetleList(content);//调用填充Beetle集合方法，返回Beetle集合
		} else {
			return new ArrayList<Beetle>();//返回默认Beetle集合
		}
	}

	/**
	 * 填充Beetle集合
	 * 
	 * @param content
	 * @return
	 */
	public List<Beetle> setBeetleList(String content) {
		String[] beetles = content.split("@#@");//分割字符串
		List<Beetle> beetlesList = new ArrayList<Beetle>();//初始化Beetle集合
		if (beetles.length > 0) {
			for (int i = 0; i < beetles.length; i++) {
				String[] beetleProperty = beetles[i].split("!#!");//分割字符串
				Beetle beetle = new Beetle();//初始化Beetle对象
				beetle.setId(beetleProperty[0].trim());//设置Id
				beetle.setName(beetleProperty[1].trim());//设置Name
				beetle.setLat(beetleProperty[2].trim());//设置Lat
				beetle.setLon(beetleProperty[3].trim());//设置Lon
				beetle.setContacts(beetleProperty[4].trim());//设置Contacts
				if (beetleProperty.length >= 6) {
					beetle.setTel(beetleProperty[5].trim());//设置Tel
				}
				if (beetleProperty.length >= 7) {
					beetle.setAddress(beetleProperty[6].trim());//设置Address
				}
				if (beetleProperty.length >= 8) {
					beetle.setZip(beetleProperty[7].trim());//设置Zip
				}
				if (beetleProperty.length >= 9) {
					beetle.setFax(beetleProperty[8].trim());//设置Fax
				}
				if (beetleProperty.length >= 10) {
					beetle.setEmail(beetleProperty[9].trim());//设置Email
				}
				beetlesList.add(beetle);//添加Beetle对象到Beetle集合
			}
		}
		return beetlesList;//返回Beetle集合
	}

	/**
	 * 省市关键字查询
	 * 
	 * @param context
	 * @param city
	 * @param prov
	 * @return
	 */
	public List<Beetle> searchBeetlsByKeyword(Context context, String city,
			String prov) {
		List<Beetle> beetleList = getAllBeetleList(context);//获取Beetle对象集合
		List<Beetle> beetles = new ArrayList<Beetle>();//初始化Beetle对象集合
		if (beetleList != null && beetleList.size() > 0) {
			if ("".equals(city) && "".equals(prov)) {
				beetleList = sortBeetls(beetleList);//调用排序方法，对Beetle集合排序
				return beetleList;//返回Beetle集合
			}
			for (int i = 0; i < beetleList.size(); i++) {
				String address = beetleList.get(i).getAddress().trim();//获取地址
				if ((!"".equals(city) && (address.indexOf(city) >= 0))
						|| (!"".equals(prov) && (address.indexOf(prov) >= 0))) {
					beetles.add(beetleList.get(i));//添加Beetle对象到Beetle集合
				}
			}
		}
		beetles = sortBeetls(beetles);//调用排序方法，对Beetle集合排序
		return beetles;//返回Beetle集合
	}

	/**
	 * 排序
	 * 
	 * @param beetleList
	 * @return
	 */
	public List<Beetle> sortBeetls(List<Beetle> beetleList) {
		List<Beetle> newBeetles = new ArrayList<Beetle>();//初始化Beetle对象集合
		String[] address = new String[beetleList.size()];//初始化地址数组
		for (int i = 0; i < beetleList.size(); i++) {
			address[i] = beetleList.get(i).getAddress().trim();//添加地址到集合
		}
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);//初始化Comparator
		Arrays.sort(address, cmp);//数组排序方法
		for (int i = 0; i < address.length; i++) {
			for (int j = 0; j < beetleList.size(); j++) {
				if (address[i].equals(beetleList.get(j).getAddress().trim())) {
					newBeetles.add(beetleList.get(j));//添加Beetle对象到Beetle集合

				}
			}
		}
		return newBeetles;//返回Beetle集合
	}

	/**
	 * 排序，按大路书名
	 * 
	 * @param context
	 * @return
	 */
	public List<Route> sortBigRoute(Context context) {
		List<Route> bigRouteList = getBigRouteList(context);//获取Route对象集合
		List<Route> newBigRoutes = new ArrayList<Route>();//初始化Route对象集合
		String[] bigRouteNames = new String[bigRouteList.size()];//初始化名称数组
		for (int i = 0; i < bigRouteList.size(); i++) {
			String desc = bigRouteList.get(i).getDesc();//获取Route的Desc
			if (desc.indexOf("“跨越中国�?途观之旅”之") >= 0) {
				desc = desc.replaceAll("“跨越中国�?途观之旅”之", "");
			}
			if (desc.indexOf("“跨越中国�?途观之旅”�?�?") >= 0) {
				desc = desc.replaceAll("“跨越中国�?途观之旅”�?�?", "");
			}
			bigRouteList.get(i).setDesc(desc);//设置Desc
			bigRouteNames[i] = bigRouteList.get(i).getName().trim();//添加名称到数组
		}
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);//初始化Comparator
		Arrays.sort(bigRouteNames, cmp);//数组排序方法
		for (int i = 0; i < bigRouteNames.length; i++) {
			for (int j = 0; j < bigRouteList.size(); j++) {
				if (bigRouteNames[i].equals(bigRouteList.get(j).getName()
						.trim())) {
					newBigRoutes.add(bigRouteList.get(j));//添加Route对象到Route集合
				}
			}
		}

		return newBigRoutes;//返回Route集合
	}
}
