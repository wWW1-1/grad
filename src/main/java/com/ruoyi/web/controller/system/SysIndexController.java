package com.ruoyi.web.controller.system;

import com.ruoyi.chain.order.service.IChainOrderService;
import com.ruoyi.chain.purchase.service.IChainPurchaseService;
import com.ruoyi.chain.sale.service.IChainSaleService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.CookieUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private IChainOrderService iChainOrderService;

    @Autowired
    private IChainPurchaseService iChainPurchaseService;

    @Autowired
    private IChainSaleService iChainSaleService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("sideTheme", configService.selectConfigByKey("sys.index.sideTheme"));
        mmap.put("skinName", configService.selectConfigByKey("sys.index.skinName"));
        Boolean footer = Convert.toBool(configService.selectConfigByKey("sys.index.footer"), true);
        Boolean tagsView = Convert.toBool(configService.selectConfigByKey("sys.index.tagsView"), true);
        mmap.put("footer", footer);
        mmap.put("tagsView", tagsView);
        mmap.put("mainClass", contentMainClass(footer, tagsView));
        mmap.put("copyrightYear", RuoYiConfig.getCopyrightYear());
        mmap.put("demoEnabled", RuoYiConfig.isDemoEnabled());
        mmap.put("isDefaultModifyPwd", initPasswordIsModify(user.getPwdUpdateDate()));
        mmap.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));
        mmap.put("isMobile", ServletUtils.checkAgentIsMobile(ServletUtils.getRequest().getHeader("User-Agent")));

        // 菜单导航显示风格
        String menuStyle = configService.selectConfigByKey("sys.index.menuStyle");
        // 移动端，默认使左侧导航菜单，否则取默认配置
        String indexStyle = ServletUtils.checkAgentIsMobile(ServletUtils.getRequest().getHeader("User-Agent")) ? "index" : menuStyle;

        // 优先Cookie配置导航菜单
        Cookie[] cookies = ServletUtils.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.isNotEmpty(cookie.getName()) && "nav-style".equalsIgnoreCase(cookie.getName())) {
                indexStyle = cookie.getValue();
                break;
            }
        }
        String webIndex = "topnav".equalsIgnoreCase(indexStyle) ? "index-topnav" : "index";
        return webIndex;
    }

    // 锁定屏幕
    @GetMapping("/lockscreen")
    public String lockscreen(ModelMap mmap) {
        mmap.put("user", getSysUser());
        ServletUtils.getSession().setAttribute(ShiroConstants.LOCK_SCREEN, true);
        return "lock";
    }

    // 解锁屏幕
    @PostMapping("/unlockscreen")
    @ResponseBody
    public AjaxResult unlockscreen(String password) {
        SysUser user = getSysUser();
        if (StringUtils.isNull(user)) {
            return AjaxResult.error("服务器超时，请重新登录");
        }
        if (passwordService.matches(user, password)) {
            ServletUtils.getSession().removeAttribute(ShiroConstants.LOCK_SCREEN);
            return AjaxResult.success();
        }
        return AjaxResult.error("密码不正确，请重新输入。");
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin() {
        return "skin";
    }

    // 切换菜单
    @GetMapping("/system/menuStyle/{style}")
    public void menuStyle(@PathVariable String style, HttpServletResponse response) {
        CookieUtils.setCookie(response, "nav-style", style);
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", RuoYiConfig.getVersion());
        initTransport(mmap);
        initOrder(mmap);
        initPurchase(mmap);
        initSale(mmap);
        return "main";
    }

    private void initTransport(ModelMap mmap) {
        List<Map> mapList = iChainPurchaseService.selectPurchasetTansportStatus();
        Long deliverTotal = 0l;
        Long undeliveredTotal = 0l;
        for (Map map : mapList) {
            String type = String.valueOf(map.get("type"));
            if ("4".equals(type)) {
                deliverTotal += Long.parseLong(String.valueOf(map.get("total")));
            } else {
                undeliveredTotal += Long.parseLong(String.valueOf(map.get("total")));
            }
        }
        mmap.addAttribute("deliverTotal", deliverTotal);
        mmap.addAttribute("undeliveredTotal", undeliveredTotal);
    }

    private void initSale(ModelMap mmap) {
        List<Map> mapList = iChainSaleService.selectSaleStatistics();
        long saleTotal = 0;
        Float saleAmount = 0f;
        long unSaleTotal = 0;
        Float unSaleAmount = 0f;
        for (Map map : mapList) {
            String type = String.valueOf(map.get("type"));
            if ("3".equals(type)) {
                saleTotal += Long.parseLong(String.valueOf(map.get("total")));
                saleAmount += Float.parseFloat(String.valueOf(map.get("amount")));
            }else{
                unSaleTotal += Long.parseLong(String.valueOf(map.get("total")));
                unSaleAmount += Float.parseFloat(String.valueOf(map.get("amount")));
            }

        }
        mmap.addAttribute("saleTotal", saleTotal);
        mmap.addAttribute("saleAmount", saleAmount);
        mmap.addAttribute("unSaleTotal", unSaleTotal);
        mmap.addAttribute("unSaleAmount", unSaleAmount);
    }

    private void initPurchase(ModelMap mmap) {
        List<Map> mapList = iChainPurchaseService.selectPurchaseStatistics();
        long purchaseTotal = 0;
        Float purchaseAmount = 0f;
        long unPurchaseTotal = 0;
        Float unPurchaseAmount = 0f;
        for (Map map : mapList) {
            String type = String.valueOf(map.get("type"));
            if ("3".equals(type)) {
                purchaseTotal += Long.parseLong(String.valueOf(map.get("total")));
                purchaseAmount += Float.parseFloat(String.valueOf(map.get("amount")));
            } else {
                unPurchaseTotal += Long.parseLong(String.valueOf(map.get("total")));
                unPurchaseAmount += Float.parseFloat(String.valueOf(map.get("amount")));
            }

        }
        mmap.addAttribute("purchaseTotal", purchaseTotal);
        mmap.addAttribute("purchaseAmount", purchaseAmount);
        mmap.addAttribute("unPurchaseTotal", unPurchaseTotal);
        mmap.addAttribute("unPurchaseAmount", unPurchaseAmount);
    }


    private void initOrder(ModelMap mmap) {
        long saleOrder = 0;
        long purchaseOrder = 0;
        float saleAmount = 0f;
        float purchaseAmount = 0f;
        List<Map> orderList = iChainOrderService.selectOrderStatistics();
        for (Map map : orderList) {
            String type = String.valueOf(map.get("type"));
            if ("1".equals(type)) {
                //销售金额
                saleAmount = Float.parseFloat(String.valueOf(map.get("amount")));
                saleOrder = Long.parseLong(String.valueOf(map.get("total")));
            } else if ("2".equals(type)) {
                //采购金额
                purchaseAmount = Float.parseFloat(String.valueOf(map.get("amount")));
                purchaseOrder = Long.parseLong(String.valueOf(map.get("total")));
            }
        }

        mmap.addAttribute("saleOrder", saleOrder);
        mmap.addAttribute("purchaseOrder", purchaseOrder);
        mmap.addAttribute("saleAmount", saleAmount);
        mmap.addAttribute("purchaseAmount", purchaseAmount);
    }


    // content-main class
    public String contentMainClass(Boolean footer, Boolean tagsView) {
        if (!footer && !tagsView) {
            return "tagsview-footer-hide";
        } else if (!footer) {
            return "footer-hide";
        } else if (!tagsView) {
            return "tagsview-hide";
        }
        return StringUtils.EMPTY;
    }

    // 检查初始密码是否提醒修改
    public boolean initPasswordIsModify(Date pwdUpdateDate) {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    // 检查密码是否过期
    public boolean passwordIsExpiration(Date pwdUpdateDate) {
        Integer passwordValidateDays = Convert.toInt(configService.selectConfigByKey("sys.account.passwordValidateDays"));
        if (passwordValidateDays != null && passwordValidateDays > 0) {
            if (StringUtils.isNull(pwdUpdateDate)) {
                // 如果从未修改过初始密码，直接提醒过期
                return true;
            }
            Date nowDate = DateUtils.getNowDate();
            return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
        }
        return false;
    }
}
