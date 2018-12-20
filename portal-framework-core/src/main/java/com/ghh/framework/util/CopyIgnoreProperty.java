package com.ghh.framework.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 实现了所有属性名相同的属性值的拷贝而不用考虑它的类型
 *
 * @author ghh
 * @date 2018年12月19日下午10:40:14
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class CopyIgnoreProperty extends CopyEventBean{

    /**
     * @$comment 实现了所有属性名相同的属性值的拷贝而不用考虑它的类型
     * 前提是属性名相同，基于属性的字段名来拷值：
     * 对所有类型的注释:这里的所有类型指的是在CopyEventBean.isBasicClass方法中校验的类型,以后会加上其它需要的类型。
     * 功能：
     * 1、所有类型相同的属性的值的互拷。
     * 2、所有类型到String型的值的互拷。
     * 3、String型到其他所有类型的值的互拷。
     * 4、Date型到Long型的的属性的值的互拷。
     * 5、String中特殊的比如日期的字符格式（"2008-04-01"）到Long型的属性的值的拷贝，单向的，不能实现Long到String这种类型。
     * 6、String中特殊的比如日期的字符格式（"2008-04-01"）到Date型的属性的值的拷贝，单向的，不能实现Date到String这种类型。
     * 7、等等。
     * 缺憾：
     * 1、无法实现比如像Double到Long的转换。
     * 2、以及还无法实现的类型互拷，只能在使用出现时再加上取。
     * @param fromObj 被复制对象
     * @param toObj 复制对象
     * @throws GhhException
	 * @code
	 * public class Ab01 {
          private String eab001;
          private Long eab002;
          private Double eab003;
          ......
        }
        public class Test {
          private String eab001;
          private String eab002;
          private String eab003;
          ......
        }
	    Ab01 ab01=new Ab01();
        Test test=new Test();
		test.setEab001("1");
		test.setEab002(Long.valueOf("3333333333"));
		test.setEab003(Double.valueOf("22.0"));
        copyIgnoreProperty.Copy(test,ab01);
     */
	public static  void copy(Object fromObj, Object toObj) throws GhhException {
		BeanInfo fromBeanInfo = null;
		BeanInfo toBeanInfo = null;
		
		try {
			fromBeanInfo = Introspector.getBeanInfo(fromObj.getClass(),
					Object.class);
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),
					Object.class);

			PropertyDescriptor[] fromProperties = fromBeanInfo
					.getPropertyDescriptors();
			PropertyDescriptor[] toProperties = toBeanInfo
					.getPropertyDescriptors();
   
			for (int i = 0; i < fromProperties.length; i++){
				for (int j = 0; j < toProperties.length; j++){
					if (fromProperties[i].getName().equals(toProperties[j].getName())) {
						Object fromValue=fromProperties[i].getReadMethod().invoke(fromObj,new Object[] {});
						
						//允许付空值 2014-12-3
						if(fromValue==null||fromValue.toString().equalsIgnoreCase("")){
							toProperties[j].getWriteMethod().invoke(toObj,new Object[] { null });
							break;
						}
						if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
							if (isBasicClass(fromProperties[i].getPropertyType())&& isBasicClass(toProperties[j].getPropertyType())&& fromProperties[i].getPropertyType() == toProperties[j].getPropertyType()){
								toProperties[j].getWriteMethod().invoke(toObj,new Object[] { fromValue });
								break;
							}
							else if (isBasicClass(fromProperties[i].getPropertyType())&& isBasicClass(toProperties[j].getPropertyType())&&fromProperties[i].getPropertyType()!=toProperties[j].getPropertyType()) {
								if(toProperties[j].getPropertyType()== Long.class||toProperties[j].getPropertyType()== long.class)
							    {
							    	
								    if(fromValue.toString().indexOf("-")>0)
								    {
								    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
								    	break;
								    }
								    if(fromValue.getClass()==Date.class)
								    {
								    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
								    	break;
								    }
								    toProperties[j].getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
								    break;
							    }
							    if(toProperties[j].getPropertyType()== Integer.class||toProperties[j].getPropertyType()== int.class)
						     	{
							    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
							    	break;
						     	}
							    if(toProperties[j].getPropertyType()== Float.class||toProperties[j].getPropertyType()== float.class)
							    {
							    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
							    	break;
							    }
							    if(toProperties[j].getPropertyType()== Date.class)
							    {
							    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT) });
							    	break;
							    }
							    if(toProperties[j].getPropertyType()== Short.class||toProperties[j].getPropertyType()== short.class)
							    {
							    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
							    	break;
							    }
							    if(toProperties[j].getPropertyType()== Double.class||toProperties[j].getPropertyType()== double.class)
							    {
							    	toProperties[j].getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
							    	break;
							    }
							    if(toProperties[j].getPropertyType()== String.class)
							    {
								    if(fromValue.getClass()==Date.class)
								    {
								    	toProperties[j].getWriteMethod().invoke(toObj,DateUtil.dateToNormalString((Date)fromValue));
								    	break;
								    }
								    if(fromValue.getClass()==Timestamp.class)
								    {
								    	toProperties[j].getWriteMethod().invoke(toObj,DateUtil.dateToNormalString((Date)fromValue));
								    	break;
								    }
							    	toProperties[j].getWriteMethod().invoke(toObj,fromValue.toString());
							    	break;
							    }
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GhhException("属性复制失败(" + e.getMessage() + ")");
		}
	}
	
	/**
     * @$comment 实现了把存放在HashMap对象里数据拷贝到其它实体中
     * 功能：
     * 1、拷贝时无须考虑toObj中属性的类型。
     * 2、类型的转换。
     *    2.1、可以转换到Long,Integer,Float,Date,Short,Double,String,Boolean。
     *    2.2、String中特殊的比如日期的字符格式（"2008-04-01"）到Long型的属性的值的转换。
     *    2.3、String中特殊的比如日期的字符格式（"2008-04-01"）到Date型的属性的值的转换。
     *    2.4、等等。
	 * @param fromHashMap 被复制的HashMap
	 * @param toObj 复制的对象
	 * @throws GhhException
	 * @code
	 * 	CommonQueryBS query_ac58_money=new CommonQueryBS();
		query_ac58_money.setConnection(sess.connection());	
	    query_ac58_money.setQuerySQL("select sum(akc087) as akc087,sum(ekc001) as ekc001,sum(eac005) as eac005 from ac58  where aac001="+aac001+"");
		Vector<?> vector_medical=query_ac58_money.query();
		Iterator<?> iterator_medical = vector_medical.iterator();
		ArchiveQueryMedicalPara medicalPara=dto.new ArchiveQueryMedicalPara();
		while (iterator_medical.hasNext()){
			HashMap tmp= (HashMap)iterator_medical.next();
			CopyIgnoreProperty.copyHashMap(tmp, medicalPara);
		}       
     */

	public static  void copyHashMap(HashMap<?, ?> fromHashMap, Object toObj) throws GhhException{
		BeanInfo toBeanInfo = null;
		Integer hashMapSize=fromHashMap.keySet().size();
		try {
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),Object.class);
			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor toPropertie:toProperties)
			{
				Integer toPropertieSize=0;
				String Property= toPropertie.getName();
		    	Object fromValue=fromHashMap.get(Property);
				//允许付空值 2014-12-3
				if(fromValue==null||fromValue.toString().equalsIgnoreCase("")){
					toPropertie.getWriteMethod().invoke(toObj,new Object[] { null });
					continue;
				}
				if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
					    if(hashMapSize.toString().equals(toPropertieSize.toString())){
					    	return;
					    }
					    toPropertieSize++;
						if(toPropertie.getPropertyType()== Long.class||toPropertie.getPropertyType()== long.class)
					    {
					    	
						    if(fromValue.toString().indexOf("-")>0)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
						    	continue;
						    }
						    if(fromValue.getClass()==Date.class)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
						    	continue;
						    }
						    toPropertie.getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
						    continue;
					    }
					    if(toPropertie.getPropertyType()== Integer.class||toPropertie.getPropertyType()== int.class)
				     	{
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
					    	continue;
				     	}
					    if(toPropertie.getPropertyType()== Float.class||toPropertie.getPropertyType()== float.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Date.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Short.class||toPropertie.getPropertyType()== short.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Double.class||toPropertie.getPropertyType()== double.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== String.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { fromValue.toString() });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Boolean.class||toPropertie.getPropertyType()== boolean.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Boolean.parseBoolean(fromValue.toString()) });
					    	continue;
					    }
					}
			}
			   
		}catch (Exception e) {
		   throw new GhhException("属性复制失败(" + e.getMessage() + ")"); 
		 }
	}
	
	/**
	 * @$comment 实现了把存放在实体中的数据拷贝到HashMap对象里
	 * @param fromObj
	 * @param toHashMap
	 * @throws GhhException
	 */
	public  static  void copyObjToHashMap(Object fromObj,HashMap<String,Object> toHashMap) throws GhhException{
		BeanInfo fromBeanInfo = null;
		try {
			fromBeanInfo = Introspector.getBeanInfo(fromObj.getClass(),Object.class);
			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor fromPropertie:fromProperties)
			{
				String Property= fromPropertie.getName();
		    	Object fromValue=fromPropertie.getReadMethod().invoke(fromObj,new Object[] {});
		    	
				if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
					toHashMap.put(Property, fromValue);
				}
			}
			   
		}catch (Exception e) {
		   throw new GhhException("属性复制失败(" + e.getMessage() + ")"); 
		 }		
	}
	
	
	/**
     * @$comment 实现了把存放在字符串里数据拷贝到其它实体中
     * 功能：
     * 1、拷贝时无须考虑toObj中属性的类型。
     * 2、类型的转换。
     *    2.1、可以转换到Long,Integer,Float,Date,Short,Double,String,Boolean。
     *    2.2、String中特殊的比如日期的字符格式（"2008-04-01"）到Long型的属性的值的转换。
     *    2.3、String中特殊的比如日期的字符格式（"2008-04-01"）到Date型的属性的值的转换。
     *    2.4、等等。
	 * @param fromObj 被复制的字符串
	 * @param toObj 复制的对象
	 * @throws GhhException
	 * @code
	 * public class Ac43 {
          private String aab033;
          private String aae078;
          private String aae140;
          ......
        }
        String str="{aab033=2, aae078=0, aae140=10, aae023=-17, eaz035=275, aaz223=44464434, aae020=51, check=true} ";    
        copyIgnoreProperty.copyStringToObject(str,Ac43);
     */
	public  static  void copyStringToObject(String fromObj,Object toObj) throws GhhException{
		BeanInfo toBeanInfo = null;
		try {
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),Object.class);
			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
			String[] fromValue = null;
			fromObj=fromObj.substring(1, fromObj.length()-1);
			if(fromObj.indexOf(",")>-1){
				fromValue = fromObj.split(",");
			}else{
				throw new GhhException("没有可以拷贝的属性"); 
			}	
			HashMap<String,Object> hp=new HashMap<String,Object>();
			for (PropertyDescriptor toPropertie:toProperties)
			{		
				String Property= toPropertie.getName();
		    	if(fromObj.contains(Property)){
		    		for (int i=0;i<fromValue.length;i++){
		    			if(fromValue[i].indexOf(Property+"=")>-1){
		    				hp.put(Property, fromValue[i].split(Property+"=")[1]);
		    			}
		    		}
		    	}
			}
			CopyIgnoreProperty.copyHashMap(hp, toObj);	   
		}catch (Exception e) {
		   throw new GhhException("属性复制失败(" + e.getMessage() + ")"); 
		 }		
	}
	
		
    /**
     * @$comment 实现了把存放在数组里对象的数据拷贝到其它实体中，返回对象列表
     * 使用说明：数组里一定要放一个实体对象，不能直接赋值，比如
     * 1、错误的赋值方法『Object[] obj=new Object[10];obj[0]="风";』
     * 2、正确的赋值方法『Ac01 ac01=new Ac01();Object[] obj=new Object[10];ac01.setAac003("风");obj[0]=ac01;』
     * 功能：
     * 1、拷贝时无须考虑toObj中属性的类型。
     * 2、类型的转换。
     *    2.1、可以转换到Long,Integer,Float,Date,Short,Double,String,Boolean。
     *    2.2、String中特殊的比如日期的字符格式（"2008-04-01"）到Long型的属性的值的转换。
     *    2.3、String中特殊的比如日期的字符格式（"2008-04-01"）到Date型的属性的值的转换。
     *    2.4、等等。
     * @param <typeName> 自定义类型
     * @param fromObj 存放对象的数组
     * @param toObj 类
     * @return List<typeName> 自定义对象列表
     * @throws GhhException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @code
     * public List GetSysCodeType() {
			Iterator lists = session
					.createQuery(
							"select new Kc05(aaz265,eac071,aaz308,aaz002) from Kc05 k where k.aaz001="+aaz001+"")
					.iterate();
			List kc05lst=new java.util.ArrayList(); 
			while (lists.hasNext())
            {
				Object[] tmp = (Object[])lists.next();
			    List<Kc05> lst=CopyIgnoreProperty.copyObjectArray(tmp, Kc05.class);
			    for(int i=0;i<lst.size();i++){
			      kc05lst.add((Kc05)lst.get(i));
			    }		
			}
			return kc05lst;
	    }     
     */
	public static <typeName> List<typeName> copyObjectArray(Object[] fromObj, Class<typeName> toObj) throws GhhException{	
		
		LinkedList<typeName> list = new LinkedList<typeName>();
		try {
			for(int i=0;i<fromObj.length;i++){
				typeName obj =  toObj.newInstance();
				CopyIgnoreProperty.copy(fromObj[i], obj);
				list.add(obj);
			}	

		}catch(Exception e){
			throw new GhhException("属性复制失败(" + e.getMessage() + ")"); 
		}
		return list;		
	}
	
	
	/**
	 * 将json对象的信息拷贝正常对象上
	 * @param fromJson
	 * @param toObj
	 * @throws H3cException
	public static  void copyJsonObjToObj(JSONObject fromJson, Object toObj) throws H3cException{
		BeanInfo toBeanInfo = null;
		Integer hashMapSize=fromJson.keySet().size();
		try {
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),Object.class);
			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor toPropertie:toProperties)
			{
				Integer toPropertieSize=0;
				String Property= toPropertie.getName();
		    	Object fromValue=fromJson.get(Property);
				if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
					    if(hashMapSize.toString().equals(toPropertieSize.toString())){
					    	return;
					    }
					    toPropertieSize++;
						if(toPropertie.getPropertyType()== Long.class||toPropertie.getPropertyType()== long.class)
					    {
					    	
						    if(fromValue.toString().indexOf("-")>0)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
						    	continue;
						    }
						    if(fromValue.getClass()==Date.class)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
						    	continue;
						    }
						    toPropertie.getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
						    continue;
					    }
					    if(toPropertie.getPropertyType()== Integer.class||toPropertie.getPropertyType()== int.class)
				     	{
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
					    	continue;
				     	}
					    if(toPropertie.getPropertyType()== Float.class||toPropertie.getPropertyType()== float.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Date.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Short.class||toPropertie.getPropertyType()== short.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Double.class||toPropertie.getPropertyType()== double.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== String.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { fromValue.toString() });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Boolean.class||toPropertie.getPropertyType()== boolean.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Boolean.parseBoolean(fromValue.toString()) });
					    	continue;
					    }
					}
			}
			   
		}catch (Exception e) {
		   throw new H3cException("属性复制失败(" + e.getMessage() + ")"); 
		 }
	}*/

}
