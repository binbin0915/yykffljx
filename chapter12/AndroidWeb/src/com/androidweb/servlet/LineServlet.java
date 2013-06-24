package com.androidweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LineServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LineServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String res="";
		String src="39.897090000000006,116.308#39.89708,116.30004000000001#39.89723,116.29957#39.897180000000006,116.29725#39.89725000000001,116.29609#39.89681,116.29609#39.89685,116.2998#39.89681,116.30517#39.89696,116.30575#39.89697,116.30673000000002#39.89658,116.30743000000001#39.89636,116.30801000000001#39.89636,116.30976000000001#39.89634,116.30989000000001#39.896190000000004,116.31008000000001#39.8956,116.31014#39.895300000000006,116.31024000000001#39.886030000000005,116.31031000000002#39.86507,116.31266000000001#39.86395,116.31284000000001#39.862550000000006,116.31332#39.861650000000004,116.31377#39.86057,116.31449#39.859790000000004,116.31512000000001#39.85237,116.32190000000001#39.851800000000004,116.32252000000001#39.851290000000006,116.32320000000001#39.85080000000001,116.32395000000001#39.85022,116.32504000000002#39.849790000000006,116.32607000000002#39.849380000000004,116.32739000000001#39.84904,116.32912#39.848940000000006,116.33018000000001#39.84888,116.34080000000002#39.848940000000006,116.34197#39.849090000000004,116.34320000000001#39.849090000000004,116.34408#39.84893,116.34453#39.848420000000004,116.34502#39.847750000000005,116.34545000000001#39.847100000000005,116.34573#39.84644,116.34593000000001#39.84514,116.34581000000001#39.84313,116.34575000000001#39.82907,116.34574#39.825010000000006,116.34540000000001#39.82079,116.34483000000002#39.819050000000004,116.34475#39.816970000000005,116.3448#39.811460000000004,116.34507#39.806230000000006,116.34542#39.80427,116.34573#39.79965,116.34668#39.79773,116.34688000000001#39.79647000000001,116.34719000000001#39.795370000000005,116.34754000000001#39.79272,116.34805000000001";
		String str[]=src.split("#");
		for(int i=0;i<str.length;i+=1){
			String temp[]=str[i].split(",");
			res=res+"{\"lat\":\""+temp[0]+"\",\"lng\":\""+temp[1]+"\"},";
		}
		res=res.substring(0, (res.length() - 1));
		out.println("["+res+"]");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
