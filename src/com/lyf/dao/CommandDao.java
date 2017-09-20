package com.lyf.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.lyf.db.DbAccess;
import com.lyf.model.Command;

/**
 * 和Command表相关的数据库查询
 * @author Administrator
 *
 */

public class CommandDao {
	DbAccess dbAccess = new DbAccess();
	
	public List<Command> queryCommandList(String name,String description){
		List<Command> CommandList = new ArrayList<Command>();
		SqlSession sqlSession = null;
		try {
			Command command = new Command();
			command.setName(name);
			sqlSession = dbAccess.getSqlSession();
			//通过SQL语句执行查询
			CommandList = sqlSession.selectList("Command.queryCommandList",command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null){
				sqlSession.close();
			}
		}
		return CommandList;
	}
	//删除单个
	public boolean deleteOne(int id) {
		SqlSession sqlSession = null;
		boolean flag = false;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQL语句执行查询
			sqlSession.selectOne("Command.deleteOne",id);
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
			sqlSession.selectOne("Command.deleteBatch",ids);
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













