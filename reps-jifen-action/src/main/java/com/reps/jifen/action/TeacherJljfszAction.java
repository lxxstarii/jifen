package com.reps.jifen.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.LoginToken;
import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.TeacherPjfzxssz;
import com.reps.jifen.entity.TeacherPjkfpjf;
import com.reps.jifen.entity.enums.JfComeFrom;
import com.reps.jifen.service.ITeacherPjfzxsszService;
import com.reps.jifen.service.ITeacherPjkfpjfService;
import com.reps.school.entity.School;
import com.reps.school.entity.TeacherClasses;
import com.reps.school.entity.TeacherSchool;
import com.reps.school.service.ITeacherClassesService;
import com.reps.school.service.ITeacherService;
import com.reps.system.entity.Organize;
import com.reps.system.entity.User;
import com.reps.system.service.IUserService;

/**
 * 教职工奖励积分设置
 * @author Lanxumit
 *
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/jljfsz")
public class TeacherJljfszAction extends BaseAction {

	private final Log logger = LogFactory.getLog(TeacherJljfszAction.class);
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ITeacherPjfzxsszService sxService;
	
	@Autowired
	ITeacherPjkfpjfService kfpService;
	
	@Autowired
	ITeacherService teacherService;
	
	@Autowired
	ITeacherClassesService teacherClassesService;
	
	/**
	 * 教师系数列表
	 * @param query
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/xsszlist")
	public ModelAndView jlxsszList(TeacherPjfzxssz query, Pagination pager) {
		ModelAndView mav = getModelAndView("/jifen/jzgjljf/xsszlist");
		ListResult<TeacherPjfzxssz> result = sxService.query(query, pager.getStartRow(), pager.getPageSize());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	/**
	 * 奖励系数设置
	 * @return
	 */
	@RequestMapping(value = "/jlxssz")
	public ModelAndView jlxssz() {
		ModelAndView mav = getModelAndView("/jifen/jzgjljf/jlsxsz");
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/savejlsx", method = RequestMethod.POST)
	@ResponseBody
	public Object saveJlsx(TeacherPjfzxssz info) throws RepsException {
		LoginToken token = getCurrentToken();
		List<TeacherPjfzxssz> tList = sxService.findAll();
		TeacherPjfzxssz data = null;
		int oldBrzRadio = 0, oldRjksRadio = 0;
		//默认不更新
		boolean isUpdateBrz = false, isUpdateRkjs = false;
		if (tList == null || tList.isEmpty()) {
			data = new TeacherPjfzxssz();
			if ("bzr".equals(info.getOption())) {
				isUpdateBrz = true;
			} else {
				isUpdateRkjs = true;
			}
		} else {
			data = tList.get(0);
			if (data.getBzr() != null) {
				oldBrzRadio = data.getBzr();
				if ("bzr".equals(info.getOption()) && info.getRatio() != data.getBzr()) {
					isUpdateBrz = true;
				}
			}
			if (data.getRkjs() != null) {
				oldRjksRadio = data.getRkjs();
				if ("rkjs".equals(info.getOption()) && info.getRatio() != data.getRkjs()) {
					isUpdateRkjs = true;
				}
			}
		}
		TeacherSchool query = new TeacherSchool();
		if ("bzr".equals(info.getOption())) {
			data.setBzr(info.getRatio());
			query.setWorkRole("23");
		} else {
			data.setRkjs(info.getRatio());
			query.setWorkRole("10");
		}
		//修改教师评价分值表,新增数据
		if (isUpdateBrz || isUpdateRkjs) {
			query.setSchool(new School());
			query.getSchool().setOrganize(new Organize());
			query.getSchool().getOrganize().setParentXpath(token.getParentIdsXpath() + "/" + token.getOrganizeId());
			//query.getSchool().getOrganize().setParentXpath("-1/43312620160501org000000000000000");
			List<TeacherSchool> tsList = teacherService.query(query, null);
			if (tsList != null && !tsList.isEmpty()) {
				for (TeacherSchool ts : tsList) {
					//先判断是否存在教师可分配积分
					TeacherPjkfpjf tpf = kfpService.findByTeacherId(ts.getTeacherId());
					List<TeacherClasses> tcList = teacherClassesService.getByTeacherSchoolId(ts.getId());
					int totalStudents = 0;
					if (tcList != null && !tcList.isEmpty()) {
						for (TeacherClasses tc : tcList) {
							if (tc.getClasses() != null) {
								totalStudents += tc.getClasses().getStudentCount() == null ? 0 : tc.getClasses().getStudentCount();
							}
						}
					}
					if (tpf == null) {
						tpf = new TeacherPjkfpjf();
						tpf.setTeacherId(ts.getTeacherId());
						tpf.setOrganizeId(ts.getOrganizeId());
						tpf.setPointsLeft(info.getRatio() * totalStudents);
						tpf.setTotalPointsAuthorized(info.getRatio() * totalStudents);
						tpf.setAuthorizedFrom(JfComeFrom.JFJLSX.getCode());
						kfpService.save(tpf);
					} else {
						//跳过已被单独分配积分的老师
						if (tpf.getAuthorizedFrom() == JfComeFrom.JFFP.getCode()) {
							continue;
						}
						//计算是否要重新初始化系数
						int oldTotalJf = 0;
						if ("bzr".equals(info.getOption())) {
							oldTotalJf = oldBrzRadio * totalStudents;
						} else {
							oldTotalJf = oldRjksRadio * totalStudents;
						}
						int newTotalJf = info.getRatio() * totalStudents;
						if (newTotalJf > oldTotalJf) {
							tpf.setTotalPointsAuthorized(tpf.getTotalPointsAuthorized() + (newTotalJf - oldTotalJf));
							tpf.setPointsLeft(tpf.getPointsLeft() + (newTotalJf - oldTotalJf));
						} else {
							if (tpf.getPointsLeft() == 0 && (oldTotalJf - newTotalJf) > 0) {
								throw new RepsException("初始化设置失败,"+ ts.getTeacher().getPerson().getName() + "教师可分配积分已用完.");
							}
							tpf.setTotalPointsAuthorized(tpf.getTotalPointsAuthorized() - (oldTotalJf - newTotalJf));
							tpf.setPointsLeft(tpf.getTotalPointsAuthorized() - (oldTotalJf - newTotalJf));
						}
						kfpService.update(tpf);
					}
				}
			}
		}
		sxService.saveOrUpdate(data);
		return ajax(AjaxStatus.OK, "保存成功");
	}
	
	/**
	 * 教师奖励积分列表
	 * @param query
	 * @param pager
	 * @return
	 * @throws RepsException
	 */
	@RequestMapping(value = "/jfszlist")
	public ModelAndView jfszList(TeacherPjkfpjf query, Pagination pager) throws RepsException {
		ModelAndView mav = getModelAndView("/jifen/jzgjljf/jfszlist");
		ListResult<TeacherPjkfpjf> result = kfpService.query(query, pager.getStartRow(), pager.getPageSize());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	/**
	 * 奖励积分设置
	 * @return
	 */
	@RequestMapping(value = "/jljfsz")
	public ModelAndView jljfsz() {
		ModelAndView mav = getModelAndView("/jifen/jzgjljf/jljfsz");
		
		return mav;
	}
	
	@RequestMapping(value = "/teachers")
	public ModelAndView teachers(Pagination pager, User user, String dialogId, String showName, String hideName, String hideNameValue, String callBack, boolean filterSelected) {
		ModelAndView mav = getModelAndView("/jifen/jzgjljf/teachers");
		String[] selectedUserIds = null;
	    if (StringUtils.isNotBlank(hideNameValue)) {
	      selectedUserIds = hideNameValue.split(",");
	    }
	    LoginToken token = getCurrentToken();
	    Organize organize = new Organize();
	    organize.setParentXpath(token.getParentIdsXpath() + "/" + token.getOrganizeId());
	    //organize.setParentXpath("-1/43312620160501org000000000000000");
	    user.setOrganize(organize);
	    ListResult<User> listResult = this.userService.query(pager.getStartRow(), pager.getPageSize(), user, null, filterSelected, selectedUserIds);

	    pager.setTotalRecord(listResult.getCount().longValue());

	    mav.addObject("list", listResult.getList());

	    mav.addObject("pager", pager);

	    mav.addObject("user", user);
	    mav.addObject("dialogId", dialogId);
	    mav.addObject("showName", showName);
	    mav.addObject("hideName", hideName);
	    mav.addObject("hideNameValue", hideNameValue);
	    mav.addObject("callBack", callBack);
	    mav.addObject("actionBasePath", "/reps");
		return mav;
	}
	
	@RequestMapping(value = "/savejljf")
	@ResponseBody
	public Object saveJljf(TeacherPjkfpjf info) throws RepsException {
		try {
			if (StringUtils.isBlank(info.getTeacherId())) {
				throw new RepsException("教师Id不能为空");
			}
			if (info.getJljf() == null) {
				throw new RepsException("分配积分不能为空");
			}
			TeacherPjkfpjf kpf = kfpService.findByTeacherId(info.getTeacherId());
			if (kpf == null) {
				throw new RepsException("该教师尚未初始化可分配积分, 请先设置教师奖励系数");
			}
			kpf.setAuthorizedFrom(JfComeFrom.JFFP.getCode());
			kpf.setTotalPointsAuthorized(kpf.getTotalPointsAuthorized() + info.getJljf());
			kpf.setPointsLeft(kpf.getPointsLeft() + info.getJljf());
			kfpService.update(kpf);
		} catch (RepsException e) {
			logger.error("保存设置教师奖励积分失败" + e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
		return ajax(AjaxStatus.OK, "保存成功");
	}
}
