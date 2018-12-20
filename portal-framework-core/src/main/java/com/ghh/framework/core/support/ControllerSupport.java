package com.ghh.framework.core.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;

/*****************************************************************
 *
 * 控制器帮助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:33:57
 * @since v1.0.1
 ****************************************************************/
public abstract class ControllerSupport<T> {

	/**
	 * 保存操作带日志
	 * @param dto
	 * @param args
	 * @return
	 * @throws GhhException
	 */
	public AjaxJson saveWithOpLog(T dto, String userlog) throws GhhException{return null;}

	/**
	 * 保存操作不带日志
	 * @param dto
	 * @param userlog
	 * @return
	 * @throws GhhException
	 */
	public AjaxJson save(T dto) throws GhhException{return null;}
	
	/**
	 * excel导出公共方法
	 * 
	 * @param req
	 * @param resp
	 * @throws H3cException
	 * @throws IOException 
	 
	protected void expExcel(ExcelDTO dto,List<LinkedHashMap<String,String>> linkHpLst,
			HttpServletRequest req, HttpServletResponse resp)
			throws H3cException, IOException {
		// 1.生成EXCEL
		String excelName = "";
		String path = req.getSession().getServletContext().getRealPath("/");
		if ("2".equals(dto.getOutSuffix())) {
			excelName = dto.getFileName()+".xlsx";
			ExcelUtil.writeXSSFWorkbook(new XSSFWorkbook(), path, excelName, linkHpLst);
		}else{
			excelName = dto.getFileName()+".xls";
			ExcelUtil.writeHSSFWorkbook(new HSSFWorkbook(), path, excelName, linkHpLst);
		}
				
		// 2.下载EXCEL
		ExcelUtil.downLoadExcel(excelName, req, resp);
		
	}
	*/

	/**
	 * 在Controller类中添加该注解方法即可(注意：添加到某个controller，只针对该controller起作用)
	 * 
	 * @param ex
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	protected void exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PrintWriter writer = response.getWriter();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("msg", ex.getMessage());
			String jsonobject = "";
			try {
				jsonobject = mapper.writeValueAsString(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
			writer.write(jsonobject);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
	}
}
