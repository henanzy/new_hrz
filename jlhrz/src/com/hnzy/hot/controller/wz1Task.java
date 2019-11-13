package com.hnzy.hot.controller;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hnzy.hot.service.OpcService;
import com.hnzy.hot.util.DUtil;
import com.hnzy.hot.util.OPCConfiguration;
import com.hnzy.hot.util.XUtil;

import net.sf.json.JSONObject;


@Component 
public class wz1Task{
	
	@Autowired
	OpcService opcService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
	
	@Scheduled(cron="0 0/10 * * * ? ")  //每5执行一次   
    public void aTask(){      
		 ConnectionInformation ci=OPCConfiguration.getCLSIDConnectionInfomation();
	       final Server server = new Server ( ci, Executors.newSingleThreadScheduledExecutor () );
	       String[] d=DUtil.dsj(DUtil.ywz);
	       
	       Map<String,Object> dmap=new HashMap<String, Object>();
	       Map<String, Item> map = null;
            	     Group group = null;
            				try {
								server.connect ();
            					 group = server.addGroup ( "groupyx" );
            					 group.setActive ( true );
				                try {
									map = group.addItems(d);
								} catch (AddFailedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (JIException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (AlreadyConnectedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NotConnectedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DuplicateGroupException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            				
        					for (Entry<String, Item> temp : map.entrySet()) {
        						try {
        							Thread.sleep(100);
        						} catch (InterruptedException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
        					
								try {
									String id= temp.getValue().getId();
	        						Object value = temp.getValue().read(true).getValue().getObject();
	        						System.out.println("------------value----------"+value);
									 String[] a=id.split("读数据.");
						            	String key=a[1];
						            	if(key.contains("#")){
						            		key=key.replace("#", "");
						            	}
						            	boolean b=isNumeric(String.valueOf(value));
						            	System.out.println("------b---"+b);
						            	if(b==false){
						            		value = temp.getValue().read(true).getValue().getObject();
						            	}
						                  String s=String.format("%.1f",value);
						                  dmap.put(key, s);
								} catch (JIException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        						
        					}
        					
        					   server.dispose();
        					   
        					   Map<String, Object> insMap=new HashMap<>();
        					   
        					   insMap.put("hrz", "一委站"); 
        					   insMap.put("ycssgll", dmap.get("一次供水瞬时流量"));insMap.put("ycssgrl", dmap.get("一次供水瞬时热量"));insMap.put("ycljgll", dmap.get("一次供水累计流量"));
        					   insMap.put("ycljgrl", dmap.get("一次供水累计热量"));insMap.put("ycgsyl", dmap.get("一次供水压力"));insMap.put("ycgswd", dmap.get("一次供水温度"));
        					   insMap.put("ychsyl", dmap.get("一次回水压力"));insMap.put("ychswd", dmap.get("一次回水温度"));insMap.put("bbssll", dmap.get("补水瞬时流量"));
        					   insMap.put("bbljll", dmap.get("补水累计流量"));insMap.put("sxyw", dmap.get("水箱液位"));insMap.put("zlszbsl", dmap.get("自来水总补水量"));
        					   insMap.put("zdl", dmap.get("电量实际值"));insMap.put("Ady", dmap.get("A相电压值"));insMap.put("Bdy", dmap.get("B相电压值"));
        					   insMap.put("Cdy", dmap.get("A相电压值"));insMap.put("Adl", dmap.get("A相电流值"));insMap.put("Bdl", dmap.get("B相电流值"));
        					   insMap.put("Cdl", dmap.get("C相电流值"));insMap.put("ecgssll", dmap.get("二次供水瞬时流量"));insMap.put("ecgljll", dmap.get("二次供水累计流量"));
        					   insMap.put("ecgsyl", dmap.get("二次供水压力"));insMap.put("ecgswd", dmap.get("二次供水温度"));insMap.put("echsyl", dmap.get("二次回水压力"));
        					   insMap.put("echswd", dmap.get("二次回水温度"));insMap.put("eccwhyl", dmap.get(""));
        					   insMap.put("snwd", dmap.get("室内温度"));insMap.put("xhb1fk", dmap.get("循环泵1频率反馈"));insMap.put("xhb2fk", dmap.get("循环泵2频率反馈"));
        					   insMap.put("bsb1fk", dmap.get("补水泵1频率反馈"));insMap.put("bsb2fk", dmap.get(""));insMap.put("tjfkdfk", dmap.get("调节阀反馈"));
        					   Date date = new Date();
        					   insMap.put("time", dateFormat.format(date));
        					   
        					   opcService.insertHistory(insMap);
    }      
    
	@Scheduled(cron="0 0 */1  * * ? ")    
	
    public void rbbTask(){      
		 ConnectionInformation ci=OPCConfiguration.getCLSIDConnectionInfomation();
	       final Server server = new Server ( ci, Executors.newSingleThreadScheduledExecutor () );
	       String[] d=DUtil.dsj(DUtil.ywz);
	       
	       Map<String,Object> dmap=new HashMap<String, Object>();
	       Map<String, Item> map = null;
            	     Group group = null;
            				try {
								server.connect ();
            					 group = server.addGroup ( "groupyx" );
            					 group.setActive ( true );
				                try {
									map = group.addItems(d);
								} catch (AddFailedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (JIException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (AlreadyConnectedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NotConnectedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DuplicateGroupException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            				
        					for (Entry<String, Item> temp : map.entrySet()) {
        						try {
        							Thread.sleep(100);
        						} catch (InterruptedException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
        					
								try {
									String id= temp.getValue().getId();
	        						Object value = temp.getValue().read(true).getValue().getObject();
	        						System.out.println("------------value----------"+value);
									 String[] a=id.split("读数据.");
						            	String key=a[1];
						            	if(key.contains("#")){
						            		key=key.replace("#", "");
						            	}
						            	boolean b=isNumeric(String.valueOf(value));
						            	System.out.println("------b---"+b);
						            	if(b==false){
						            		value = temp.getValue().read(true).getValue().getObject();
						            	}
						                  String s=String.format("%.1f",value);
						                  dmap.put(key, s);
								} catch (JIException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        						
        					}
        					
        					   server.dispose();
        					   
        					   Map<String, Object> insMap=new HashMap<>();
        					   
        					   insMap.put("hrz", "一委站"); 
        					   insMap.put("ycssgll", dmap.get("一次供水瞬时流量"));insMap.put("ycssgrl", dmap.get("一次供水瞬时热量"));insMap.put("ycljgll", dmap.get("一次供水累计流量"));
        					   insMap.put("ycljgrl", dmap.get("一次供水累计热量"));insMap.put("ycgsyl", dmap.get("一次供水压力"));insMap.put("ycgswd", dmap.get("一次供水温度"));
        					   insMap.put("ychsyl", dmap.get("一次回水压力"));insMap.put("ychswd", dmap.get("一次回水温度"));insMap.put("bbssll", dmap.get("补水瞬时流量"));
        					   insMap.put("bbljll", dmap.get("补水累计流量"));insMap.put("sxyw", dmap.get("水箱液位"));insMap.put("zlszbsl", dmap.get("自来水总补水量"));
        					   insMap.put("zdl", dmap.get("电量实际值"));insMap.put("Ady", dmap.get("A相电压值"));insMap.put("Bdy", dmap.get("B相电压值"));
        					   insMap.put("Cdy", dmap.get("A相电压值"));insMap.put("Adl", dmap.get("A相电流值"));insMap.put("Bdl", dmap.get("B相电流值"));
        					   insMap.put("Cdl", dmap.get("C相电流值"));insMap.put("ecgssll", dmap.get("二次供水瞬时流量"));insMap.put("ecgljll", dmap.get("二次供水累计流量"));
        					   insMap.put("ecgsyl", dmap.get("二次供水压力"));insMap.put("ecgswd", dmap.get("二次供水温度"));insMap.put("echsyl", dmap.get("二次回水压力"));
        					   insMap.put("echswd", dmap.get("二次回水温度"));insMap.put("eccwhyl", dmap.get(""));
        					   insMap.put("snwd", dmap.get("室内温度"));insMap.put("xhb1fk", dmap.get("循环泵1频率反馈"));insMap.put("xhb2fk", dmap.get("循环泵2频率反馈"));
        					   insMap.put("bsb1fk", dmap.get("补水泵1频率反馈"));insMap.put("bsb2fk", dmap.get(""));insMap.put("tjfkdfk", dmap.get("调节阀反馈"));
        					   Date date = new Date();
        					   insMap.put("time", dateFormat.format(date));
        					   
        					   opcService.insertRbb(insMap);
    }  
	
	
	@Scheduled(cron="0 0 12 * * ? ")   //每天12点执行一次  
    public void zybbTask(){      
		 ConnectionInformation ci=OPCConfiguration.getCLSIDConnectionInfomation();
	       final Server server = new Server ( ci, Executors.newSingleThreadScheduledExecutor () );
	       String[] d=DUtil.dsj(DUtil.ywz);
	       
	       Map<String,Object> dmap=new HashMap<String, Object>();
	       Map<String, Item> map = null;
            	     Group group = null;
            				try {
								server.connect ();
            					 group = server.addGroup ( "groupyx" );
            					 group.setActive ( true );
				                try {
									map = group.addItems(d);
								} catch (AddFailedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (JIException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (AlreadyConnectedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NotConnectedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DuplicateGroupException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            				
        					for (Entry<String, Item> temp : map.entrySet()) {
        						try {
        							Thread.sleep(100);
        						} catch (InterruptedException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
        					
								try {
									String id= temp.getValue().getId();
	        						Object value = temp.getValue().read(true).getValue().getObject();
	        						System.out.println("------------value----------"+value);
									 String[] a=id.split("读数据.");
						            	String key=a[1];
						            	if(key.contains("#")){
						            		key=key.replace("#", "");
						            	}
						            	boolean b=isNumeric(String.valueOf(value));
						            	System.out.println("------b---"+b);
						            	if(b==false){
						            		value = temp.getValue().read(true).getValue().getObject();
						            	}
						                  String s=String.format("%.1f",value);
						                  dmap.put(key, s);
								} catch (JIException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        						
        					}
        					
        					   server.dispose();
        					   
        					   Map<String, Object> insMap=new HashMap<>();
        					   
        					   insMap.put("hrz", "一委站"); 
        					   insMap.put("ycssgll", dmap.get("一次供水瞬时流量"));insMap.put("ycssgrl", dmap.get("一次供水瞬时热量"));insMap.put("ycljgll", dmap.get("一次供水累计流量"));
        					   insMap.put("ycljgrl", dmap.get("一次供水累计热量"));insMap.put("ycgsyl", dmap.get("一次供水压力"));insMap.put("ycgswd", dmap.get("一次供水温度"));
        					   insMap.put("ychsyl", dmap.get("一次回水压力"));insMap.put("ychswd", dmap.get("一次回水温度"));insMap.put("bbssll", dmap.get("补水瞬时流量"));
        					   insMap.put("bbljll", dmap.get("补水累计流量"));insMap.put("sxyw", dmap.get("水箱液位"));insMap.put("zlszbsl", dmap.get("自来水总补水量"));
        					   insMap.put("zdl", dmap.get("电量实际值"));insMap.put("Ady", dmap.get("A相电压值"));insMap.put("Bdy", dmap.get("B相电压值"));
        					   insMap.put("Cdy", dmap.get("A相电压值"));insMap.put("Adl", dmap.get("A相电流值"));insMap.put("Bdl", dmap.get("B相电流值"));
        					   insMap.put("Cdl", dmap.get("C相电流值"));insMap.put("ecgssll", dmap.get("二次供水瞬时流量"));insMap.put("ecgljll", dmap.get("二次供水累计流量"));
        					   insMap.put("ecgsyl", dmap.get("二次供水压力"));insMap.put("ecgswd", dmap.get("二次供水温度"));insMap.put("echsyl", dmap.get("二次回水压力"));
        					   insMap.put("echswd", dmap.get("二次回水温度"));insMap.put("eccwhyl", dmap.get(""));
        					   insMap.put("snwd", dmap.get("室内温度"));insMap.put("xhb1fk", dmap.get("循环泵1频率反馈"));insMap.put("xhb2fk", dmap.get("循环泵2频率反馈"));
        					   insMap.put("bsb1fk", dmap.get("补水泵1频率反馈"));insMap.put("bsb2fk", dmap.get(""));insMap.put("tjfkdfk", dmap.get("调节阀反馈"));
        					   Date date = new Date();
        					   insMap.put("time", dateFormat.format(date));
        					   
        					   opcService.insertZybb(insMap);
    }  
	
	@Scheduled(cron="0 0/10 * * * ? ")
	public void  bjxx() throws NotConnectedException, DuplicateGroupException, AddFailedException, UnsupportedEncodingException, JIException, IllegalArgumentException, UnknownHostException, AlreadyConnectedException{
		
       ConnectionInformation ci=OPCConfiguration.getCLSIDConnectionInfomation();
       final Server server = new Server ( ci, Executors.newSingleThreadScheduledExecutor () );
           String hrz="吉利.一委站.读数据.";
	       String[] d=DUtil.bjxx("吉利.一委站.读状态.");
	     
	      
	       Map<String, Item> map = null;
            	     Group group = null;
            				try {
								server.connect ();
            					 group = server.addGroup ( "groupyx" );
            					 group.setActive ( true );
				                map = group.addItems(d);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (JIException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (AlreadyConnectedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
            				
        					for (Entry<String, Item> temp : map.entrySet()) {
        						try {
        							Thread.sleep(10);
        						} catch (InterruptedException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
        					
								try {
									String id= temp.getValue().getId();
	        						Object value = temp.getValue().read(true).getValue().getObject();
	        						System.out.println("------------value----------"+value);
									 String[] a=id.split("读状态.");
						            	String key=a[1];
						            	if(key.contains("#")){
						            		key=key.replace("#", "");
						            	}
						            		try {
												Thread.sleep(10);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}						           						            	
						            	String val =String.valueOf(value);
						            	
						            	if(val.equals("true")){
						            		Map<String, Object> pdMap = new HashMap<>();
						            		pdMap.put("hrz", "一委站");
						            		pdMap.put("bjlx", key);
						            		int count = opcService.pdbj(pdMap);
						            		if(count==0){
						            			Map<String, Object> insMap = new HashMap<>();
						            			insMap.put("hrz", "一委站");
						            			insMap.put("bjlx", key);
						            			opcService.InsertBjdl(insMap);
						            			Date t=temp.getValue().read(true).getTimestamp().getTime();
								                String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ").format(t.getTime());
								                insMap.put("bjsj", time);
								                opcService.InsertBjxx(insMap);
						            		}
						            		
						            	}
						                 
						                  //dmap.put(key);
								} catch (JIException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        						
        					}
        					   server.dispose();        			                 			        				          
        					}
	
	@Scheduled(cron="0 0/10 * * * ? ")
	public void  updatebjxx() throws NotConnectedException, DuplicateGroupException, AddFailedException, UnsupportedEncodingException, JIException, IllegalArgumentException, UnknownHostException, AlreadyConnectedException{
		
       ConnectionInformation ci=OPCConfiguration.getCLSIDConnectionInfomation();
       final Server server = new Server ( ci, Executors.newSingleThreadScheduledExecutor () );
          
        					   
        					
        			          List<String> list =opcService.getbjdl("一委站");
        			          String[] kStrings = new String[list.size()];
        			          for (int i = 0; i < list.size(); i++) {
        			        	  kStrings[i]="吉利.一委站.读状态."+list.get(i);
								
							}
        			          
        			          Group group1 = null;
        			          Map<String, Item> map1 = null;
        			          
        			         server.connect ();
         					 group1 = server.addGroup ( "groupyx" );
         					 group1.setActive ( true );
				             map1 = group1.addItems(kStrings);
				             
				             for (Entry<String, Item> temp : map1.entrySet()) {
	        						try {
	        							Thread.sleep(10);
	        						} catch (InterruptedException e) {
	        							// TODO Auto-generated catch block
	        							e.printStackTrace();
	        						}
	        					
									try {
										String id= temp.getValue().getId();
		        						Object value = temp.getValue().read(true).getValue().getObject();
		        						System.out.println("------------value----------"+value);
										 String[] a=id.split("读状态.");
							            	String key=a[1];
							            	if(key.contains("#")){
							            		key=key.replace("#", "");
							            	}
							            		try {
													Thread.sleep(10);
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}						           						            	
							            	String val =String.valueOf(value);
							            	if(val.equals("false")){
							            		Map<String, Object> updateMap = new HashMap<>();
							            		
							            		Date t=temp.getValue().read(true).getTimestamp().getTime();
								                String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ").format(t.getTime());
								                updateMap.put("jcsj", time);
								                updateMap.put("hrz", "一委站");
								                updateMap.put("bjlx", key);
							            		opcService.UpdateBjxx(updateMap);
							            		opcService.DeleteBjdl(updateMap);
							            	}
							                 
							                  //dmap.put(key);
									} catch (JIException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	        						
	        					}server.dispose();
        					}
	
	 public boolean isNumeric(String value){
         Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
         Matcher isNum = pattern.matcher(value);
         if( !isNum.matches() ){
             return false;
         }
         return true;
  }
	 @Scheduled(cron="0/30 * *  * * ? ")
	 public void csxs()
				throws NotConnectedException, DuplicateGroupException, AddFailedException, UnsupportedEncodingException {
		
	ConnectionInformation ci = OPCConfiguration.getCLSIDConnectionInfomation();
			final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
			
			
			String[] d = XUtil.xsj("吉利.一委站.写数据.");

			Map<String, Object> dmap = new HashMap<String, Object>();
			Map<String, Item> map = null;
			Group group = null;
			try {
				server.connect();
				group = server.addGroup("groupyx");
				group.setActive(true);
				map = group.addItems(d);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JIException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AlreadyConnectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (Entry<String, Item> temp : map.entrySet()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					String id = temp.getValue().getId();
					Object value = temp.getValue().read(true).getValue().getObject();
					
					String[] a = id.split("写数据.");
					String key = a[1];
					if (key.contains("#")) {
						key = key.replace("#", "");
					}
					if (key.contains("时间")) {
						value = temp.getValue().read(true).getValue().getObjectAsUnsigned().getValue();
					}
					boolean b = isNumeric(String.valueOf(value));
					
					if (b == false) {
						value = temp.getValue().read(true).getValue().getObject();
					}
					
					dmap.put(key, value);
				} catch (JIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			server.dispose();
		
			Map<String, Object> insMap=new HashMap<>();
			  insMap.put("hrz", "一委站"); 
			  Date date = new Date();
			   insMap.put("time", dateFormat.format(date));
			  insMap.put("fmkdsd", dmap.get("阀门开度设定"));
			 insMap.put("ycgsylsx", dmap.get("一次供水压力上限"));insMap.put("ycgsylxx", dmap.get("一次供水压力下限"));insMap.put("ycgswdsx", dmap.get(XUtil.ycgswdsx));
			 insMap.put("ycgswdxx", dmap.get("一次供水温度下限"));insMap.put("ychsylsx", dmap.get("一次回水压力上限"));insMap.put("ychsylxx", dmap.get("一次回水压力下限"));
			 insMap.put("ychswdsx", dmap.get(XUtil.ychswdsx));insMap.put("ychswdxx", dmap.get(XUtil.ychswdxx));insMap.put("ecgsylsx", dmap.get(XUtil.ecgsylsx));
			 insMap.put("ecgsylxx", dmap.get(XUtil.ecgsylxx));insMap.put("ecgswdsx", dmap.get(XUtil.ecgswdsx));insMap.put("ecgswdxx", dmap.get(XUtil.ecgswdxx));
			 insMap.put("echswdsx", dmap.get(XUtil.echswdsx));insMap.put("echswdxx", dmap.get(XUtil.echswdxx));insMap.put("echsylsx", dmap.get(XUtil.echsylsx));
			 insMap.put("echsylxx", dmap.get(XUtil.echsylxx));insMap.put("ywsx", dmap.get(XUtil.ywsx));insMap.put("ywxx", dmap.get(XUtil.ywxx));
			 insMap.put("dyjbjcz", dmap.get(XUtil.dyjbjcz));insMap.put("echswdsdz", dmap.get(XUtil.echswdsdz));insMap.put("ecgsylsdz", dmap.get(XUtil.ecgsylsdz));
			 insMap.put("echsylsdz", dmap.get(XUtil.echsylsdz));insMap.put("echsbsylsx", dmap.get(XUtil.echsbsylsx));insMap.put("echsbsylxx", dmap.get(XUtil.echsbsylxx));
			 insMap.put("kqtjfsj", dmap.get(XUtil.kqtjfsj));insMap.put("gbtjfsj", dmap.get(XUtil.gbtjfsj));insMap.put("qdgpbsbpl", dmap.get(XUtil.qdgpbsbpl));
			 insMap.put("dybjjxz", dmap.get(XUtil.dybjjxz));insMap.put("qgpbsbsj", dmap.get(XUtil.qgpbsbsj));insMap.put("xyylsx", dmap.get(XUtil.xyylsx));
			 insMap.put("xyylxx", dmap.get(XUtil.xyylxx));insMap.put("echstjyl", dmap.get(XUtil.echstjyl));insMap.put("ywxxx", dmap.get(XUtil.ywxxx));
			 insMap.put("tzgpbsbpl", dmap.get(XUtil.tzgpbsbpl));insMap.put("tzgpbsbsj", dmap.get(XUtil.tzgpbsbsj));insMap.put("tjfzdkd", dmap.get(XUtil.tjfzdkd));
			 
			 insMap.put("bcwd1", dmap.get(XUtil.bcwd1));insMap.put("bcwd2", dmap.get(XUtil.bcwd2));insMap.put("bcwd3", dmap.get(XUtil.bcwd3));
			 insMap.put("bcwd4", dmap.get(XUtil.bcwd4));insMap.put("ecgswd1", dmap.get(XUtil.ecgswd1));insMap.put("ecgswd2", dmap.get(XUtil.ecgswd2));
			 insMap.put("ecgswd3", dmap.get(XUtil.ecgswd3));insMap.put("ecgswd4", dmap.get(XUtil.ecgswd4));insMap.put("ecgswd5", dmap.get(XUtil.ecgswd5));
			 insMap.put("ecgswd6", dmap.get(XUtil.ecgswd6));insMap.put("ecgswd7", dmap.get(XUtil.ecgswd7));insMap.put("ecgswd8", dmap.get(XUtil.ecgswd8));
			 insMap.put("sjdf1", dmap.get(XUtil.sjdf1));insMap.put("sjdf2", dmap.get(XUtil.sjdf2));insMap.put("sjdf3", dmap.get(XUtil.sjdf3));
			 insMap.put("sjdf4", dmap.get(XUtil.sjdf4));insMap.put("sjds1", dmap.get(XUtil.sjds1));insMap.put("sjds2", dmap.get(XUtil.sjds2));
			 insMap.put("sjds3", dmap.get(XUtil.sjds3));insMap.put("sjds4", dmap.get(XUtil.sjds4));insMap.put("swwd1", dmap.get(XUtil.swwd1));
			 insMap.put("swwd2", dmap.get(XUtil.swwd2));insMap.put("swwd3", dmap.get(XUtil.swwd3));insMap.put("swwd4", dmap.get(XUtil.swwd4));
			 insMap.put("swwd5", dmap.get(XUtil.swwd5));insMap.put("swwd6", dmap.get(XUtil.swwd6));insMap.put("swwd7", dmap.get(XUtil.swwd7));
			 insMap.put("swwd8", dmap.get(XUtil.swwd8));
			
			 opcService.updateXsj(insMap);
		}
	 @Scheduled(cron="0/30 * *  * * ? ")
	 public void sbxs()
				throws NotConnectedException, DuplicateGroupException, AddFailedException, UnsupportedEncodingException {
			JSONObject json = new JSONObject();
			String d[]= new String[]{"吉利.一委站.读状态.补水泵1远程就地","吉利.一委站.读状态.补水泵2远程就地","吉利.一委站.读状态.冷凝泵1远程就地",
					"吉利.一委站.读状态.调节阀远程就地","吉利.一委站.读状态.泄压阀远程就地","吉利.一委站.读状态.泄压阀状态","吉利.一委站.读状态.循环泵1远程就地"
					,"吉利.一委站.读状态.循环泵2远程就地","吉利.一委站.读状态.自来水阀远程就地","吉利.一委站.读状态.自来水阀状态","吉利.一委站.写状态.补水泵1强制",
					"吉利.一委站.写状态.补水泵2强制","吉利.一委站.写状态.补水泵控制方式","吉利.一委站.写状态.二次供水温度设定方式","吉利.一委站.写状态.冷凝泵1强制",
					"吉利.一委站.写状态.冷凝泵2强制","吉利.一委站.写状态.冷凝阀强制","吉利.一委站.写状态.调节阀定时开关使能","吉利.一委站.写状态.调节阀强制",
					"吉利.一委站.写状态.泄压阀强制","吉利.一委站.写状态.循环泵1强制","吉利.一委站.写状态.循环泵2强制","吉利.一委站.写状态.自来水阀强制"};
			
			ConnectionInformation ci = OPCConfiguration.getCLSIDConnectionInfomation();
			final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

			Map<String, Object> dmap = new HashMap<String, Object>();
			Map<String, Item> map = null;
			Group group = null;
			try {
				server.connect();
				group = server.addGroup("groupyx");
				group.setActive(true);
				map = group.addItems(d);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JIException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AlreadyConnectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (Entry<String, Item> temp : map.entrySet()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					String id = temp.getValue().getId();
					Object value = temp.getValue().read(true).getValue().getObject();
					

					String string = id.replace("吉利.教育局站.", "").replace("吉利.一委站.", "").replace("吉利.二委站.", "").replace("写数据.", "").replace("读数据.", "").replace("写状态.", "")
							.replace("读状态.", "");

					if (string.contains("#")) {
						string = string.replace("#", "");
					}
					if (string.contains("时间")) {
						value = temp.getValue().read(true).getValue().getObjectAsUnsigned().getValue();
					}
					boolean b = isNumeric(String.valueOf(value));
				
					if (b == false) {
						value = temp.getValue().read(true).getValue().getObject();
					}

					dmap.put(string, value);
				} catch (JIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			server.dispose();

			json.put("map", dmap);
			Map<String, Object> insMap=new HashMap<>();
			  insMap.put("hrz", "一委站"); 
			 
			  insMap.put("dzt_bsb1ycjd", dmap.get("补水泵1远程就地")); insMap.put("dzt_bsb2ycjd", dmap.get("补水泵2远程就地")); insMap.put("dzt_lnb1ycjd", dmap.get("冷凝泵1远程就地")); 
			  insMap.put("dzt_tjfycjd", dmap.get("调节阀远程就地")); insMap.put("dzt_xyfycjd", dmap.get("泄压阀远程就地")); insMap.put("dzt_xhb1ycjd", dmap.get("循环泵1远程就地")); 
			  insMap.put("dzt_xhb2ycjd", dmap.get("循环泵2远程就地")); insMap.put("dzt_zlsfycjd", dmap.get("自来水阀远程就地")); insMap.put("dzt_xyfzt", dmap.get("泄压阀状态"));
			  insMap.put("dzt_zlsfzt", dmap.get("自来水阀状态"));insMap.put("xzt_bsb1qz", dmap.get("补水泵1强制"));insMap.put("xzt_bsb2qz", dmap.get("补水泵2强制"));
			  insMap.put("xzt_bsbkzfs", dmap.get("补水泵控制方式"));insMap.put("xzt_xztecgswdsdfs", dmap.get("二次供水温度设定方式"));insMap.put("xzt_lnb1qz", dmap.get("冷凝泵1强制"));
			  insMap.put("xzt_lnb2qz", dmap.get("冷凝泵2强制"));insMap.put("xzt_lnfqz", dmap.get("冷凝阀强制"));insMap.put("xzt_tjfdskgsn", dmap.get("调节阀定时开关使能"));
			  insMap.put("xzt_tjfqz", dmap.get("调节阀强制"));insMap.put("xzt_xyfqz", dmap.get("泄压阀强制"));insMap.put("xzt_xhb1qz", dmap.get("循环泵1强制"));
			  insMap.put("xzt_xhb2qz", dmap.get("循环泵2强制"));insMap.put("xzt_zlsfqz", dmap.get("自来水阀强制"));
		     opcService.updateXsj_zt(insMap);
		}
}
