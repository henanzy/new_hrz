package com.hnzy.hot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface OpcDao {
  
	public void insertHistory(Map<String, Object> map);
	
	public List<Map<String, Object>>selHistory (Map<String, Object> map);
	
	public void insertRbb(Map<String, Object> map);
	
	public void insertZybb(Map<String, Object> map);
	
	public List<Map<String, Object>>selrbb (Map<String, Object> map);
	
	public List<Map<String, Object>>selzybb (Map<String, Object> map);
	
	public List<Map<String, Object>>selQx (Map<String, Object> map);
	
	public List<Map<String, Object>>getHrzXx (Map<String, Object> map);
	
    public void Insert(Map<String, Object> map);
	
	public void Update(Map<String, Object> map);
	
	public void Delete(String id);
	
	public void InsertBjxx(Map<String, Object> map);
	
	public void InsertBjdl(Map<String, Object> map);
	
	public void DeleteBjdl(Map<String, Object> map);
	
	public void UpdateBjxx(Map<String, Object> map);
	
	public int pdbj (Map<String, Object> map);
	
	public List<String>getbjdl (String hrz);
	
	public List<Map<String, Object>>getbjxx ();
	
	public List<Map<String, Object>>getlsbjxx (Map<String, Object> map);
	
	public List<Map<String, Object>>dbjc (Map<String, Object> map);
	
	public List<Map<String, Object>>sdrfx (Map<String, Object> map);
	
	public void updateSsb(Map<String, Object> map);
	
	public void updateXsj(Map<String, Object> map);
	
	public void updateXsj_zt(Map<String, Object> map);
	
	public List<Map<String, Object>>findSsb (@Param(value="hrz") String hrz);
	
	public List<Map<String, Object>>findXsj (@Param(value="hrz") String hrz);
}
