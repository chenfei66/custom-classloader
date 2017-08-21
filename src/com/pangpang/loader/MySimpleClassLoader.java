package com.pangpang.loader;

import com.pangpang.util.CommonFindClass;

/**
 * CustomClassLoader
 * ��ʾ--�Զ����������
 * @author pangpang
 *
 */
public class MySimpleClassLoader extends ClassLoader{
	private String byteCode_Path;

	public MySimpleClassLoader(String byteCode_Path) {
		this.byteCode_Path = byteCode_Path;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		byte[] value = CommonFindClass.commonFindClass(byteCode_Path, name);
		System.out.println("���ڵ����Զ����������");

		//��byte����ת��Ϊһ�����Class����ʵ��
		return defineClass(null, value, 0, value.length);

	}
	//����
	public static void main(String[] args) throws ClassNotFoundException {

		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));

		MySimpleClassLoader loader = new MySimpleClassLoader("E:\\H2\\gitTest\\");
		System.out.println("��ǰ��ĸ��������"+loader.getParent().getClass().getName());
		System.out.println("����Ŀ������������"+loader.loadClass("LoggerAdvice").getClassLoader().getClass().getName());

	}
}
