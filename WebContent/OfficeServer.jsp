<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="DBstep.iMsgServer2000.*"%>
<%!
  public class iWebOffice {
    private String mOption;
    private String mRecordID;
    private String mTemplate;
    private String mDateTime;
    private String mFileName;
    private String mFileType;
    private String mDirectory;
    private String mFilePath;
    private String mUserName;
    private String mCommand;
    private String mContent;
    private String mLabelName;
    private String mImageName;
    private String mHtmlName;
    private DBstep.iMsgServer2000 MsgObj;
    
    public void ExecuteRun(HttpServletRequest request, HttpServletResponse response) {
      mOption = "";
      mRecordID = "";
      mTemplate = "";
      mFileName = "";
      mFileType = "";
      mCommand = "";
      mContent = "";
      mLabelName = "";
      mImageName = "";
      mDirectory = "";
      mHtmlName = "";
      mFilePath = request.getSession().getServletContext().getRealPath("");     //取得服务器路径
      MsgObj = new DBstep.iMsgServer2000();
		System.out.println(mFilePath);
      System.out.println("-------------------");
      System.out.println("ReadPackage");
      
      try {
        if (request.getMethod().equalsIgnoreCase("POST")) {
          MsgObj.Load(request);                                                 //读取并解析客户端发来的数据包
          mUserName = MsgObj.GetMsgByName("USERNAME");
          
          if (MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) {
            mOption = MsgObj.GetMsgByName("OPTION");
            System.out.println("OPTION:" + mOption);
            
            if (mOption.equalsIgnoreCase("LOADFILE")) {                         //请求调用文档  
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称 
              MsgObj.MsgTextClear();
              /* mFilePath = mFilePath + "\\Document\\" + mFileName;  */              //文件存在服务器的完整路径
              mFilePath = mFilePath+"/"+mFileName; 
              System.out.println("++++++++++++++++++"+mFilePath);
              if (MsgObj.MsgFileLoad(mFilePath)) {                              //调入文件文档
                MsgObj.SetMsgByName("STATUS", "打开成功!");                     //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("打开失败!");                                   //设置错误信息
              }
            }

            else if (mOption.equalsIgnoreCase("SAVEFILE")) {                    //请求保存文档
              mRecordID = MsgObj.GetMsgByName("RECORDID");                      //取得文档编号
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称
              //mDirName=MsgObj.GetMsgByName("DirName");		                    //取得目录名称 取得客户端传递的目录名称
              
              String isEmpty = MsgObj.GetMsgByName("EMPTY");                      //是否是空内容文档的标识
              if(isEmpty.equalsIgnoreCase("TRUE")){
                //此时接收的文档中内容是空白的，请用日志记录保存时间、保存用户、记录编号等信息，用于将来发现文档内容丢失时排查用。
                System.out.println("注意：本次保存的是空白内容的文档。RECORDID："+mRecordID);
              }

              MsgObj.MsgTextClear();
             /*  mFilePath = mFilePath + "\\Document\\" + mFileName;  */              //文件存在服务器的完整路径
               mFilePath = mFileName; 
              System.out.println("++++++++++++++++++"+mFilePath);
              if (MsgObj.MsgFileSave(mFilePath)) {                              //保存文档内容
                MsgObj.SetMsgByName("STATUS", "保存成功!");                     //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("保存失败!");                                   //设置错误信息
              }
              MsgObj.MsgFileClear();
            }

            else if (mOption.equalsIgnoreCase("LOADTEMPLATE")) {                //请求调用模板文档
              mRecordID = MsgObj.GetMsgByName("RECORDID");                      //取得文档编号
              mTemplate = MsgObj.GetMsgByName("TEMPLATE");                      //取得模板编号
              mFileType = MsgObj.GetMsgByName("FILETYPE");                      //取得文档类型
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称
              mCommand = MsgObj.GetMsgByName("COMMAND");
              if (mCommand.equalsIgnoreCase("INSERTFILE")) {
                MsgObj.MsgTextClear();
                mFilePath = mFilePath + "\\Document\\" + mTemplate;             //文件存在服务器的完整路径
                if (MsgObj.MsgFileLoad(mFilePath)) {                            //调入模板文档
                  MsgObj.SetMsgByName("STATUS", "打开模板成功!");               //设置状态信息
                  MsgObj.MsgError("");                                          //清除错误信息
                }
                else {
                  MsgObj.MsgError("打开模板失败!");                             //设置错误信息
                }
              }
              else {
                MsgObj.MsgTextClear();
                mFilePath = mFilePath + "\\Document\\" + mTemplate + mFileType; //文件存在服务器的完整路径
                if (MsgObj.MsgFileLoad(mFilePath)) {                            //调入模板文档
                  MsgObj.SetMsgByName("STATUS", "打开模板成功!");               //设置状态信息
                  MsgObj.MsgError("");                                          //清除错误信息
                }
                else {
                  MsgObj.MsgError("打开模板失败!");                             //设置错误信息
                }
              }
            }

            else if (mOption.equalsIgnoreCase("SAVETEMPLATE")) {                //请求保存模板文档
              mTemplate = MsgObj.GetMsgByName("TEMPLATE");                      //取得文档编号
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称
              mFileType = MsgObj.GetMsgByName("FILETYPE");                      //取得文档类型
              MsgObj.MsgTextClear();
              mFilePath = mFilePath + "\\Document\\" + mFileName;               //文件存在服务器的完整路径
              if (MsgObj.MsgFileLoad(mFilePath)) {                              //调入文件文档
                MsgObj.SetMsgByName("STATUS", "保存模板成功!");                 //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("保存模板失败!");                               //设置错误信息
              }
              MsgObj.MsgFileClear();
            }

            else if (mOption.equalsIgnoreCase("INSERTFILE")) {                  //请求调用正文文档
              mRecordID = MsgObj.GetMsgByName("RECORDID");                      //取得文档编号
              mFileType = MsgObj.GetMsgByName("FILETYPE");                      //取得文档类型
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称
              MsgObj.MsgTextClear();
              mFilePath = mFilePath + "\\Document\\" + mFileName;               //文件存在服务器的完整路径
              if (MsgObj.MsgFileLoad(mFilePath)) {                              //调入文件文档
                MsgObj.SetMsgByName("POSITION", "Content");                     //设置插入的位置[书签对象名]
                MsgObj.SetMsgByName("STATUS", "插入文件成功!");                 //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("插入文件成功!");                               //设置错误信息
              }
            }

            else if (mOption.equalsIgnoreCase("UPDATEFILE")) {                  //请求保存 定搞文档
              mRecordID = MsgObj.GetMsgByName("RECORDID");                      //取得文档编号
              mFileName = MsgObj.GetMsgByName("FILENAME");                      //取得文档名称
              MsgObj.MsgTextClear();
              mFilePath = mFilePath + "\\Document\\" + mFileName;               //文件存在服务器的完整路径
              if (MsgObj.MsgFileSave(mFilePath)) {                              //保存文档内容
                MsgObj.SetMsgByName("STATUS", "保存成功!");                     //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("保存失败!");                                   //设置错误信息
              }
              MsgObj.MsgFileClear();
            }

            else if (mOption.equalsIgnoreCase("INSERTIMAGE")) {                 //请求调用正文文档
              mRecordID = MsgObj.GetMsgByName("RECORDID");                      //取得文档编号
              mLabelName = MsgObj.GetMsgByName("LABELNAME");                    //标签名
              mImageName = MsgObj.GetMsgByName("IMAGENAME");                    //图片名
              mFilePath = mFilePath + "\\Document\\" + mImageName;              //图片在服务器的完整路径
              mFileType = mImageName.substring(mImageName.length() - 4).toLowerCase(); //取得文件的类型
              MsgObj.MsgTextClear();
              if (MsgObj.MsgFileLoad(mFilePath)) {                              //调入图片
                MsgObj.SetMsgByName("IMAGETYPE", mFileType);                    //指定图片的类型
                MsgObj.SetMsgByName("POSITION", mLabelName);                    //设置插入的位置[书签对象名]
                MsgObj.SetMsgByName("STATUS", "插入图片成功!");                 //设置状态信息
                MsgObj.MsgError("");                                            //清除错误信息
              }
              else {
                MsgObj.MsgError("插入图片失败!");                               //设置错误信息
              }
            }

            else if (mOption.equalsIgnoreCase("SAVEASHTML")) {                  //保存为HTML发布
              mHtmlName = MsgObj.GetMsgByName("HTMLNAME");                      //取得文件名称
              mDirectory = MsgObj.GetMsgByName("DIRECTORY");                    //取得目录名称
              MsgObj.MsgTextClear();
              if (mDirectory.trim().equalsIgnoreCase("")) {
                mFilePath = mFilePath + "\\HTML";
              }
              else {
                mFilePath = mFilePath + "\\HTML\\" + mDirectory;
              }
              MsgObj.MakeDirectory(mFilePath);
              if (MsgObj.MsgFileSave(mFilePath + "\\" + mHtmlName)) {
                MsgObj.MsgError("");                                            //清除错误信息
                MsgObj.SetMsgByName("STATUS", "保存成功");                      //设置状态信息
              }
              else {
                MsgObj.MsgError("保存失败");                                    //设置错误信息
              }
              MsgObj.MsgFileClear();
            }
            else if (mOption.equalsIgnoreCase("SAVEIMAGE")) {                   //保存为图片HTML发布
              mHtmlName = MsgObj.GetMsgByName("HTMLNAME");                      //取得文件名称
              mDirectory = MsgObj.GetMsgByName("DIRECTORY");                    //取得目录名称
              MsgObj.MsgTextClear();
              if (mDirectory.trim().equalsIgnoreCase("")) {
                mFilePath = mFilePath + "\\HTMLIMAGE";
              }
              else {
                mFilePath = mFilePath + "\\HTMLIMAGE\\" + mDirectory;
              }
              MsgObj.MakeDirectory(mFilePath);
              if (MsgObj.MsgFileSave(mFilePath + "\\" + mHtmlName)) {
                MsgObj.MsgError("");                                            //清除错误信息
                MsgObj.SetMsgByName("STATUS", "保存成功");                      //设置状态信息
              }
              else {
                MsgObj.MsgError("保存失败");                                    //设置错误信息
              }
              MsgObj.MsgFileClear();
            }
          }
          else {
            MsgObj.MsgError("客户端发送数据包错误!");
            MsgObj.MsgTextClear();
            MsgObj.MsgFileClear();
          }
        }
        else {
          MsgObj.MsgError("请使用Post方法");
          MsgObj.MsgTextClear();
          MsgObj.MsgFileClear();
        }
        System.out.println("SendPackage");
        MsgObj.Send(response);
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }
%>
<%
  iWebOffice officeServer = new iWebOffice();
  officeServer.ExecuteRun(request, response);
  out.clear();                                                                    //用于解决JSP页面中“已经调用getOutputStream()”问题
  out=pageContext.pushBody();                                                     //用于解决JSP页面中“已经调用getOutputStream()”问题
%>
