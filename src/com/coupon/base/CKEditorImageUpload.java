package com.coupon.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
/*
 * 富文本编辑框上传图片提交controller
 */
public class CKEditorImageUpload {
	
	private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";  
	  
	protected Logger logger = Logger.getLogger(CKEditorImageUpload.class);  
	  
	/* 
	* 上传图片文件夹 
	*/  
	private static final String UPLOAD_PATH = "/planFileUpload/img/";  
	  
	/* 
	* 上传图片 
	*/  
	@RequestMapping(value = "uploadImg")  
	public void uplodaImg(@RequestParam("upload") MultipartFile file,HttpServletRequest request,HttpServletResponse response)  
	{  	  
	try 
			{ 
			String proPath = request.getSession().getServletContext().getRealPath("/");  
			String proName = request.getContextPath();  
			String path=proName + UPLOAD_PATH;  
			PrintWriter out = response.getWriter();  
			String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");  
			String fileName = file.getOriginalFilename();  
			String uploadContentType = file.getContentType();  
			String expandedName = "";  
			if (uploadContentType.equals("image/pjpeg")|| uploadContentType.equals("image/jpeg")) {  
			// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
			expandedName = ".jpg";  
			} 
			else if (uploadContentType.equals("image/png")|| uploadContentType.equals("image/x-png")) {  
			// IE6上传的png图片的headimageContentType是"image/x-png"  
			expandedName = ".png";  
			} else if (uploadContentType.equals("image/gif")) {  
			expandedName = ".gif";  
			} else if (uploadContentType.equals("image/bmp")) {  
			expandedName = ".bmp";  
			} else {  
			out.println("<script type=\"text/javascript\">");  
			out.println("window.parent.CKEDITOR.tools.callFunction("  
			+ CKEditorFuncNum + ",'',"  
			+ "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");  
			out.println("</script>");  
			return;  
			}  
			if (file.getSize() > 1024 * 1024 * 2) {  
			out.println("<script type=\"text/javascript\">");  
			out.println("window.parent.CKEDITOR.tools.callFunction("  
			+ CKEditorFuncNum + ",''," + "'文件大小不得大于2M');");  
			out.println("</script>");  
			return;  
			}  
			DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);  
			fileName = df.format(new Date()) + expandedName; 
			file.transferTo(new File(proPath + UPLOAD_PATH + fileName)); 
			// 返回"图像"选项卡并显示图片 request.getContextPath()为web项目名  
			out.println("<script type=\"text/javascript\">");  
			out.println("window.parent.CKEDITOR.tools.callFunction("  
			+ CKEditorFuncNum + ",'" + "../planFileUpload/img/" + fileName  
			+ "','')");  
			out.println("</script>"); 
			return;  
			} catch (IllegalStateException e) {  
	e.printStackTrace();  
	} catch (IOException e) {  
	e.printStackTrace();  
	} catch (Exception e) {  
	e.printStackTrace();  
	}  
	}  
	}  
