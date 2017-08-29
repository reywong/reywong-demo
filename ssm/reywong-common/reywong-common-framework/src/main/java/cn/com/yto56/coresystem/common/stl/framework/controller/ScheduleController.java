package cn.com.yto56.coresystem.common.stl.framework.controller;

import cn.com.yto56.coresystem.common.stl.framework.base.BeanHolder;
import cn.com.yto56.coresystem.common.stl.framework.base.HostInfo;
import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import cn.com.yto56.coresystem.common.stl.framework.quartz.IScheduleService;
import cn.com.yto56.coresystem.common.stl.framework.quartz.ScheduleJob;
import cn.com.yto56.coresystem.common.stl.framework.quartz.impl.ScheduleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度管理
 * @author lihui
 * 2016-12-20
 */
@Controller
public class ScheduleController {

	private IScheduleService scheduleService;
	public IScheduleService getScheduleService() {
		if(this.scheduleService == null){
			this.scheduleService = BeanHolder.getBean(
                    "scheduleService", ScheduleServiceImpl.class);
		}
		return scheduleService;
	}
	
	@RequestMapping("/addJob")
	@ResponseBody
	public Map<String,Object> addJob(String jobClassName,String suffix){
		return getScheduleService().addJob(jobClassName, suffix);
	}
	
	@RequestMapping("/resumeJob")
	@ResponseBody
	public Map<String, Object> resumeJob(String jobClassName,String jobName){
		boolean resume = getScheduleService().resumeJob(jobClassName + "_" + jobName, jobClassName + ".group");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", resume);
		return result;
	}
	
	@RequestMapping("/pauseJob")
	@ResponseBody
	public Map<String, Object> pauseJob(String jobClassName,String jobName){
		boolean pause = getScheduleService().pausseJob(jobClassName + "_" + jobName, jobClassName + ".group");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", pause);
		return result;
	}
	
	@RequestMapping("/deleteJob")
	@ResponseBody
	public Map<String, Object> deleteJob(String jobClassName,String jobName){
		boolean delete = getScheduleService().deleteJob(jobClassName + "_" + jobName, jobClassName + ".group");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", delete);
		return result;
	}
	
	@RequestMapping("/updateParams")
	@ResponseBody
	public Map<String,Object> updateParams(String jobClassName,String jobName
			,String param1,String param2,String abs,HttpServletRequest request){
		boolean update = getScheduleService().updateJobParam(param1, param2,
				jobClassName + "_" + jobName, jobClassName + ".group");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success", update);
		return result;
	}
	
	public final String JOB_TABLE_HTML = "<!DOCTYPE html><html><head><title>Job Monitor</title><meta http-equiv='refresh' content='5'></meta><style>body {width: 100%;margin: 20px auto;font-family: 'trebuchet MS', 'Lucida sans', Arial;font-size: 12px;color: #444;}table {*border-collapse: collapse;border-spacing: 0;width: 100%;}.bordered {border: solid #ccc 1px;-moz-border-radius: 6px;-webkit-border-radius: 6px;border-radius: 6px;-webkit-box-shadow: 0 1px 1px #ccc; -moz-box-shadow: 0 1px 1px #ccc; box-shadow: 0 1px 1px #ccc; }.bordered tr:hover { background: #fbf8e9;-o-transition: all 0.1s ease-in-out;-webkit-transition: all 0.1s ease-in-out;-moz-transition: all 0.1s ease-in-out;-ms-transition: all 0.1s ease-in-out;transition: all 0.1s ease-in-out;}.bordered td, .bordered th {border-left: 1px solid #ccc;border-top: 1px solid #ccc;padding: 8px;text-align: center;}.bordered th {background-color: #dce9f9;background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9)); background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);background-image:         linear-gradient(top, #ebf3fc, #dce9f9);-webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;  -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; border-top: none;text-shadow: 0 1px 0 rgba(255,255,255,.5); }.bordered td:first-child, .bordered th:first-child {border-left: none;}.bordered th:first-child {-moz-border-radius: 6px 0 0 0;-webkit-border-radius: 6px 0 0 0;border-radius: 6px 0 0 0;}.bordered th:last-child {-moz-border-radius: 0 6px 0 0;-webkit-border-radius: 0 6px 0 0;border-radius: 0 6px 0 0;}.bordered th:only-child{-moz-border-radius: 6px 6px 0 0;-webkit-border-radius: 6px 6px 0 0;border-radius: 6px 6px 0 0;}.bordered tr:last-child td:first-child {-moz-border-radius: 0 0 0 6px;-webkit-border-radius: 0 0 0 6px;border-radius: 0 0 0 6px;}.bordered tr:last-child td:last-child {-moz-border-radius: 0 0 6px 0;-webkit-border-radius: 0 0 6px 0;border-radius: 0 0 6px 0;}.font-red{color:red;font-weight:bold;}.font-green{color:green;font-weight:bold;}.font-yellow{color:#FFD700;font-weight:bold;}#pagination-digg a { border:solid 1px #9aafe5; margin-right:6px; }#pagination-digg a:link,#pagination-digg a:visited { color:#0e509e; display:block; float:left; padding:3px 6px; text-decoration:none; }#pagination-digg a:hover { border:solid 1px #0e509e; }</style>"
			+ "</head><body>";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/monitor")
	public void getJobState(HttpServletResponse response){
		Map<String,Object> result = getScheduleService().getJobState();
		List<ScheduleJob> schedules = (List<ScheduleJob>) result.get("schedules");
		Object objGroupClass = result.get("groupClass");
		Map<String, String> groupClass = null;
		if(objGroupClass != null){
			groupClass = (Map<String, String>) objGroupClass;
		}
		
		if(groupClass != null && groupClass.size() > 0){
			StringBuffer sub = new StringBuffer();
			sub.append(JOB_TABLE_HTML).append("<div id='pagination-digg'>");
			sub.append("<form action='' id='monitor-form' method='post' enctype='multipart/form-data' target='ajaxifr'>");
			Map<String, String> hostInfo = HostInfo.getHostInfo();
			sub.append("<p>").append("freeMemory:").append(hostInfo.get("freeMemory"))
												.append("&#12288;&#12288;&#12288;totalMemory:").append(hostInfo.get("totalMemory"))
										   .append("&#12288;&#12288;&#12288;maxMemory:").append(hostInfo.get("maxMemory"))
										   .append("</p>");
			
			sub.append("<table class='bordered'>");
			sub.append("<tr><th>JobName</th><th>JobState</th><th>PreviousFireTime</th><th>NextFireTime</th><th>Params</th><th>LastExecuteTime</th><th>ETime</th><th>Total</th><th>Option</th>");
			if(schedules != null && schedules.size() > 0){
				for (ScheduleJob scheduleJob : schedules) {
					String[] sp = scheduleJob.getJobName().split("_");
					String resumeUrl = "resumeJob?jobClassName="+sp[0] + "&jobName="+sp[1];
					String pauseUrl = "pauseJob?jobClassName="+sp[0] + "&jobName="+sp[1];
					String deleteUrl = "deleteJob?jobClassName="+sp[0] + "&jobName="+sp[1];
					String updateUrl = "updateParams?jobClassName="+sp[0] + "&jobName="+sp[1];
					String addUrl = "addJob?jobClassName="+groupClass.get(sp[0]+".group");
					sub.append("<tr>");
					sub.append("<td>").append(scheduleJob.getJobName()).append("</td>");
					if("BLOCKED".equals(scheduleJob.getJobStatus())){
						sub.append("<td class='font-red'>");
					}else if("NORMAL".equals(scheduleJob.getJobStatus())){
						sub.append("<td class='font-green'>");
					}else if("PAUSED".equals(scheduleJob.getJobStatus())){
						sub.append("<td class='font-yellow'>");
					}else{
						sub.append("<td>");
					}
				   sub.append(scheduleJob.getJobStatus()).append("</td>")
				   .append("<td>").append(releaseNull(scheduleJob.getPreviousFireTime())).append("</td>")
				   .append("<td>").append(releaseNull(scheduleJob.getNextfireTime())).append("</td>")
				   .append("<td><input id='").append(sp[1]).append("param1' value='").append(scheduleJob.getParam1()).append("' style='width:55px;' autocomplete='off' />")
				   .append("<input id='").append(sp[1]).append("param2' value='").append(scheduleJob.getParam2()).append("' style='width:55px;' autocomplete='off' /></td>")
				   .append("<td>").append(StringUtils.isEmpty(scheduleJob.getLastExecuteTime()) ? "-" : scheduleJob.getLastExecuteTime()).append("</td>")
				   .append("<td>").append(scheduleJob.getLastTimeConsuming()).append("</td>")
				   .append("<td>").append(scheduleJob.getExecuteTotalCount()).append("</td>")
				   .append("<td>")
				   .append("<a href='javascript:void(0);' title='Update' onclick='document.getElementById(\"monitor-form\").action=\"").append(updateUrl).append("&param2"
				   		+ "=\"+encodeURIComponent(document.getElementById(\"").append(sp[1]).append("param2\").value)+\"&param1=\"+encodeURIComponent(document.getElementById(\"").append(sp[1]).append("param1\").value);").append("document.getElementById(\"monitor-form\").submit();'>U</a>")
				   .append("<a href='javascript:void(0);' title='Resume' onclick='document.getElementById(\"monitor-form\").action=\"").append(resumeUrl).append("\";document.getElementById(\"monitor-form\").submit();'>R</a>")
				   .append("<a href='javascript:void(0);' title='Pause' onclick='document.getElementById(\"monitor-form\").action=\"").append(pauseUrl) .append("\";document.getElementById(\"monitor-form\").submit();'>P</a>")
				   .append("<a href='javascript:void(0);' title='Delete' onclick='document.getElementById(\"monitor-form\").action=\"").append(deleteUrl) .append("\";document.getElementById(\"monitor-form\").submit();'>D</a>")
				   .append("<a href='javascript:void(0);' title='Add' onclick='document.getElementById(\"monitor-form\").action=\"").append(addUrl) .append("\";document.getElementById(\"monitor-form\").submit();'>A</a></td>");
					sub.append("</tr>");
				}
			}
			sub.append("</table></form></div>");
			sub.append(" <br/><center><iframe name='ajaxifr' scrolling='no' frameborder='0' width='399' height='30'></iframe></center>");
			sub.append("</body></html>");
			response.setContentType("text/html; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.write(sub.toString());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/monitorforjson")
	@ResponseBody
	public List<ScheduleJob> getJobStateForJson(HttpServletResponse response){
		Map<String,Object> result = getScheduleService().getJobState();
		List<ScheduleJob> schedules = (List<ScheduleJob>) result.get("schedules");
		Object objGroupClass = result.get("groupClass");
		Map<String, String> groupClass = null;
		if(objGroupClass != null){
			groupClass = (Map<String, String>) objGroupClass;
		}
		
		if(schedules != null && schedules.size() > 0){
			for (ScheduleJob scheduleJob : schedules) {
				String[] sp = scheduleJob.getJobName().split("_");
				scheduleJob.setJobClassName(groupClass.get(sp[0]+".group"));
			}
		}
		return schedules;
	}

	public String releaseNull(String text){
		if(StringUtils.isEmpty(text)){ return "-";}
		return text;
	}
}
