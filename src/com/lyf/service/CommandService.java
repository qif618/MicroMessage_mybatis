package com.lyf.service;

import java.util.List;
import java.util.Random;

import com.lyf.dao.CommandDao;
import com.lyf.model.Command;
import com.lyf.model.CommandContent;
import com.lyf.util.Iconst;

public class CommandService {
	CommandDao commandDao = new CommandDao();
	/**
	 * 通过指令查询自动回复的内容
	 * @param command 指令
	 * @return 自动回复的内容
	 */
	public String queryByCommand(String command) {
		List<Command> commandList;
		if(Iconst.HELP_COMMAND.equals(command)) {
			commandList = commandDao.queryCommandList(null, null);
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < commandList.size(); i++) {
				if(i != 0) {
					result.append("<br/>");
				}
				result.append("回复[" + commandList.get(i).getName() + "]可以查看");
			}
			return result.toString();
		}
		commandList = commandDao.queryCommandList(command, null);
		if(commandList.size() > 0) {
			List<CommandContent> contentList = commandList.get(0).getContentList();
System.out.println("数量："+contentList.size()+"随机数："+new Random().nextInt(contentList.size()));
			return contentList.get(new Random().nextInt(contentList.size())).getContent();
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
}



