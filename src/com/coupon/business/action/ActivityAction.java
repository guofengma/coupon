package com.coupon.business.action;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.business.entity.Activity;
import com.coupon.business.service.ActivityService;
import com.coupon.util.FolderUtil;

@Controller
public class ActivityAction extends BaseAction{

	@Autowired ActivityService activityService ;
	
	@RequestMapping(value = "/business/activity/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<Activity> activitys = activityService.findAll(pageNo, pageSize);
		model.addAttribute("activitys",activitys);
		return "business/activity/list";
	}
	
	@RequestMapping(value = "/business/activity/save")
	public String save(HttpServletRequest request, ModelMap model) throws Exception {
		String oldId = "";
		String name = "";
		String url="";
		super.addMenuParams(request, model);
		Activity activity = new Activity();
		request.setCharacterEncoding("utf-8");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			return "redirect:list";
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();//用来获取普通input
		Iterator iter1 = items.iterator();//用来上传文件
		boolean fileChanged = false ;
		while (iter.hasNext()) // 表单中有几个input标签，就循环几次
		{
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {	
				if(item.getFieldName().equals("oldId"))
					oldId = item.getString("utf-8");
				if(item.getFieldName().equals("name"))
					name = item.getString("utf-8");
				if(item.getFieldName().equals("url"))
					url = item.getString("utf-8");
				if(item.getFieldName().equals("fileChanged"))
					fileChanged = item.getString("utf-8").equals("true");
			} else {
				
			}
		}
		if(oldId.equals("null")){
			
		}else{
			activity = activityService.findById(oldId);
			if(fileChanged){
				String root = request.getServletContext().getRealPath("/");
				String oldFilePath = root + "img\\"+activity.getPicPath();
				File oldfile = new File(oldFilePath);
				if(oldfile.exists())
					oldfile.delete();
			}
		}
		while (iter1.hasNext()) // 表单中有几个input标签，就循环几次
		{
			FileItem item1 = (FileItem) iter1.next();
			if (item1.isFormField()) {
				
			} else {
				if(fileChanged){
					String fileName = item1.getName();
					// 这里发现ie获取的是路径加文件名，chrome获取的是文件名，这里我们只需要文件名，所以有路径的要先去路径
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
							fileName.length());
					String prefix = System.currentTimeMillis() + "";
					File file = new File(request.getServletContext().getRealPath(
							"/")
							+ "img\\" + FolderUtil.getFolder());
					if (!file.exists())
						file.mkdirs();
					File uploadedFile = new File(request.getServletContext()
							.getRealPath("/")
							+ "img\\"+FolderUtil.getFolder()
							+ "\\"
							+ prefix + fileName);
					item1.write(uploadedFile);
					activity.setPicPath(FolderUtil.getFolder()+ "\\"
							+ prefix + fileName);
				}
			}
		}
		activity.setName(name);
		activity.setUrl(url);
		if(oldId.equals("null")){
			activityService.save(activity);
		}else{
			activityService.update(activity);
		}
		return "redirect:list";
	}
	
	@RequestMapping(value = "/business/activity/delete")
	public String delete(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Activity activity =activityService.findById(id);
		activity.setDeleted(true);
		activityService.update(activity);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/business/activity/getActivityInfo")
	public void getProductInfo(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		Activity activity =activityService.findById(id);
		result.append("{\"name\":\""+activity.getName()+"\",\"url\":\""+activity.getUrl()+"\",\"picPath\":\""+activity.getPicPath().replace("\\", "\\\\")+"\"}");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/activity/app/toActivityPage")
	public String toActivityPage(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		Activity activity =activityService.findById(id);
		model.addAttribute("url",activity.getUrl());
		return "app/activity";
	}
}
