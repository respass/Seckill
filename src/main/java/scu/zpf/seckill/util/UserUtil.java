package scu.zpf.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import scu.zpf.seckill.domain.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
	
	private static void createUser(int count) throws Exception{
		List<User> users = new ArrayList<>(count);
		//生成用户
		for(int i=0;i<count;i++) {
			User user = new User();
			user.setPhone(Long.toString(13000000000L + i));
			user.setNickname("user"+i);
			user.setRegisterDate(new Date());
			user.setSalt("1b2u3g");
			user.setPassword("847012dc2140099eed13cbf445445c63");
			users.add(user);
		}
		System.out.println("create user");
		//插入数据库
//		Connection conn = DBUtil.getConn();
//		String sql = "insert into user( nickname, register_date, salt, password, phone)values(?,?,?,?,?)";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		for(int i=0;i<users.size();i++) {
//			User user = users.get(i);
//			pstmt.setString(1, user.getNickname());
//			pstmt.setTimestamp(2, new Timestamp(user.getRegisterDate().getTime()));
//			pstmt.setString(3, user.getSalt());
//			pstmt.setString(4, user.getPassword());
//			pstmt.setString(5, user.getPhone());
//			pstmt.addBatch();
//		}
//		pstmt.executeBatch();
//		pstmt.close();
//		conn.close();
//		System.out.println("insert to db");

		//登录，生成token
		String urlString = "http://localhost:8080/do_login";
		File file = new File("/Users/zpf/Desktop/tokens.txt");
		if(file.exists()) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		file.createNewFile();
		raf.seek(0);
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			URL url = new URL(urlString);
			HttpURLConnection co = (HttpURLConnection)url.openConnection();
			co.setRequestMethod("POST");
			co.setDoOutput(true);
			OutputStream out = co.getOutputStream();
			String params = "phone="+user.getPhone()+"&password="+Md5Util.inputPassswordToFormPassword("123456");
			out.write(params.getBytes());
			out.flush();
			InputStream inputStream = co.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) >= 0) {
				bout.write(buff, 0 ,len);
			}
			inputStream.close();
			bout.close();
			String response = new String(bout.toByteArray());
			JSONObject jo = JSON.parseObject(response);
			String token = jo.getString("data");
			System.out.println("create token : " + user.getPhone());
			
			String row = user.getPhone()+","+ token;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
			System.out.println("write to file : " + user.getPhone());
		}
		raf.close();

		System.out.println("over");
	}
	
	public static void main(String[] args)throws Exception {
		createUser(5000);
	}
}
