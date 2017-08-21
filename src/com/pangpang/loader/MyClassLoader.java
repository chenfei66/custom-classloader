package com.pangpang.loader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pangpang.security.Use3DES;
import com.pangpang.util.CommonFindClass;

/**
 * �Զ����������
 * �Լ��ܺ���ֽ�����н���
 * @author pangpang
 *
 */
public class MyClassLoader extends ClassLoader {

	/**
	 * ԭ �ֽ���·��
	 */
	private String byteCode_Path;
	
	/**
	 * ��Կ
	 */
	private byte[] key;
	
	public MyClassLoader(String byteCode_Path, byte[] key) {
		this.byteCode_Path = byteCode_Path;
		this.key = key;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		byte[] value = CommonFindClass.commonFindClass(byteCode_Path, name);

		//�����ܺ���ֽ���--����
		value = Use3DES.decrypt(key, value);

		//��byte����ת��Ϊһ�����Class����ʵ��
		Class<?> tmp = defineClass( null, value, 0, value.length);

		System.out.println(tmp.toString());
		
		return tmp;
	}
	
	public static void main(String[] args) {
		BufferedInputStream in = null;
		try {
			//��ԭ�ֽ����ļ�����src�ֽ�����
			in = new BufferedInputStream(new FileInputStream("E:\\H2\\gitTest\\SimpleTest.class"));
			byte[] src = new byte[in.available()];
			in.read(src);
			in.close();
			
			byte[] key = "01234567899876543210abcd".getBytes();//��Կ24λ
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("E:/H2/SimpleTest.class"));
			
			//���ֽ���  ���ܺ�д��"E:\\GitCode"
			out.write(Use3DES.encrypt(key, src));
			out.close();
			
			//�����Զ����������������Ŀ���ֽ���
			MyClassLoader loader = new MyClassLoader("E:/H2/", key);
			System.out.println(loader.loadClass("SimpleTest").getClassLoader().getClass().getName());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
