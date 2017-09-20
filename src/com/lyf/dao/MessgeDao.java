package com.lyf.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.lyf.db.DbAccess;
import com.lyf.model.Message;

/**
 * 和Message表相关的数据库查询
 * @author Administrator
 *
 */

public class MessgeDao {
	DbAccess dbAccess = new DbAccess();
	
	public List<Message> queryMessageList(String command,String description){
		List<Message> messageList = new ArrayList<Message>();
		SqlSession sqlSession = null;
		try {
			Message message = new Message();
			message.setCommand(command);
			message.setDescription(description);
			sqlSession = dbAccess.getSqlSession();
			//通过SQL语句执行查询
			messageList = sqlSession.selectList("Message.queryMessageList",message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null){
				sqlSession.close();
			}
		}
		return messageList;
	}
	//删除单个
	public boolean deleteOne(int id) {
		SqlSession sqlSession = null;
		boolean flag = false;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQL语句执行查询
			sqlSession.selectOne("Message.deleteOne",id);
			sqlSession.commit();
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null){
				sqlSession.close();
			}
		}
		return flag;
	}
	//批量删除
	public boolean deleteBatch(List<Integer> ids) {
		SqlSession sqlSession = null;
		boolean flag = false;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQL语句执行查询
			sqlSession.selectOne("Message.deleteBatch",ids);
			sqlSession.commit();
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null){
				sqlSession.close();
			}
		}
		return flag;
	}

}













