package com.lyf.service;

import java.util.ArrayList;
import java.util.List;

import com.lyf.dao.MessgeDao;
import com.lyf.model.Message;
import com.lyf.util.Iconst;

public class MessageService {
	MessgeDao messageDao = new MessgeDao();
	//查询Message列表
	public List<Message> queryMessageList(String command,String description){
		return messageDao.queryMessageList(command, description);
	}
	public boolean deleteOne(String id) {
		if(id!=null){
			return messageDao.deleteOne(Integer.parseInt(id));
		}
		return false;
	}
	
	public boolean deleteBatch(String ids) {
		if(ids!=null){
			String[] idStr = ids.substring(0, ids.length()).split(",");
			List<Integer> idList = new ArrayList<Integer>();
			for(String id : idStr){
				idList.add(Integer.valueOf(id));
			}
System.out.println("数组"+idList.toString());
			return messageDao.deleteBatch(idList);
		}
		return false;
	}
	/**
	 * 通过指令查询自动回复的内容
	 * @param command 指令
	 * @return 自动回复的内容
	 */
	public String queryByCommand(String command) {
		List<Message> messageList;
		if(Iconst.HELP_COMMAND.equals(command)) {
			messageList = messageDao.queryMessageList(null, null);
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < messageList.size(); i++) {
				if(i != 0) {
					result.append("<br/>");
				}
				result.append("回复[" + messageList.get(i).getCommand() + "]可以查看" + messageList.get(i).getDescription());
			}
			return result.toString();
		}
		messageList = messageDao.queryMessageList(command, null);
		if(messageList.size() > 0) {
			return messageList.get(0).getContent();
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
}



