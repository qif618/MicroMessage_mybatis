package com.lyf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyf.model.Message;
import com.lyf.service.CommandService;
import com.lyf.service.MessageService;


@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet{
	MessageService messageService = new MessageService();
	CommandService commandService = new CommandService();
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String methodName = req.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String command = req.getParameter("command");
		String description = req.getParameter("description");
		//查询Message列表信息
		List<Message> messageList = messageService.queryMessageList(command, description);
		//向页面传值
		req.setAttribute("messageList", messageList);
		req.setAttribute("command", command);
		req.setAttribute("description", description);
		req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
	}
	
	public void deleteOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String id = req.getParameter("id");
		boolean flag = messageService.deleteOne(id);
		resp.setContentType("text/html;charset=UTF-8");
		if(flag){
			resp.getWriter().print("OK");
		}else{
			resp.getWriter().print("NO");
		}
		
	}
	/**
	 * 批量删除
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteBatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String ids = req.getParameter("ids");
		boolean flag = messageService.deleteBatch(ids);

		resp.setContentType("text/html;charset=UTF-8");
		if(flag){
			resp.getWriter().print("OK");
		}else{
			resp.getWriter().print("NO");
		}
		
	}
	//初始化公众号页面
	public void initTalk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/front/talk.jsp").forward(req, resp);
	}
	
	//自动回复
	public void autoReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.write(commandService.queryByCommand(req.getParameter("content")));
		out.flush();
		out.close();
	}
}













