package com.snnubus.controller;
import com.snnubus.entity.*;
import com.snnubus.service.SiteinfoService;
import com.snnubus.service.UserService;
import com.snnubus.service.DriverService;
import com.snnubus.service.WaitService;
import com.snnubus.service.CommentService;
import com.snnubus.service.CentenceService;
import com.snnubus.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
//控制器类，处理来自客户端的HTTP请求，并返回相应的响应
@RestController
public class UserController {
    private static final double EARTH_RADIUS = 6371.0; // 地球半径，单位为千米
    private int myVariable=100; // 全局变量
    @Autowired
    private UserService userService; //乘客类
    @Autowired
    private DriverService driverService;//司机类
    @Autowired
    private CommentService commentService;//评论类
    @Autowired
    private SiteinfoService siteinfoService;//站点信息类
    @Autowired
    private WaitService waitService;//候车人数类
    @Autowired
    private NewsService newsService; //新闻类
    @Autowired
    private CentenceService centenceService;//留言类

    private String openid = "";
    private String gschool = "";

    /**
     * 用户登录
     * @param data
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> data) // 当用户点击登录
    {
        System.out.println(data);
        String code = data.get("code").toString(); // 获取code参数的值
        Integer role = Integer.parseInt(data.get("role").toString()); // 获取role参数的值
        String school = data.get("school").toString(); // 获取code参数的值
        System.out.println(code);
        System.out.println(role);
        System.out.println(school);
        // 构建向微信服务器发送请求的URL
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WXContent.APPID + "&secret=" + WXContent.APPSECRET
                + "&js_code=" + code + "&grant_type=authorization_code";

        // 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置支持的媒体类型
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN
        ));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        // 发送HTTP GET请求，获取微信服务器的响应
        ResponseEntity<Map> response = restTemplate.getForEntity(url,Map.class);

        // 提取微信服务器响应中的openid值
        gschool=school;
        openid = (String) response.getBody().get("openid");
        System.out.println(openid);

        if (role == 1){ // 如果角色为1（用户）
            List<User> users = userService.getById(openid,gschool); // 查询用户信息
            if (users.size() == 0) // 如果用户尚未注册
            {
                // 用户尚未注册，插入用户信息
                userService.insertId(openid,openid,school);

                return new Result(openid, Code.LOGIN_NOT,"登录成功!已为您注册账号");
            }
            else {
                User user = users.get(0); // 假设查询结果只有一个用户
                String userId = user.getUserId(); // 获取用户ID
                // 用户已经注册过
                System.out.println(userId);
                return new Result(userId,Code.LOGIN_ALREADY,"登录成功!欢迎回来");
            }

        }
        else{ // 如果角色不是1（司机）
            List<Driver> drivers = driverService.getById(openid,gschool); // 查询司机信息
            if (drivers.size() == 0) // 如果司机尚未注册
            {
                // 司机尚未注册，插入司机信息
                driverService.insertId(openid,openid,school);
                return new Result(openid, Code.LOGIN_NOT,"登录成功!已为您注册账号");
            }
            else {
                Driver driver = drivers.get(0); // 假设查询结果只有一个司机
                String userId = driver.getDriverId(); // 获取司机ID
                // 用户已经注册过
                System.out.println(userId);

                return new Result(userId,Code.LOGIN_ALREADY,"登录成功!欢迎回来");
            }
        }
    }
    // 定义一个自定义类来封装用户信息
    class UserInfoResponse {
        private String userName;
        private String userSex;
        private String userTelephone;
        private String userAvatar;
        public UserInfoResponse(String userName, String userSex, String userTelephone,String userAvatar) {
            this.userName = userName;
            this.userSex = userSex;
            this.userTelephone = userTelephone;
            this.userAvatar = userAvatar;
        }


        // 添加对应的 getter 方法
        public String getUserName() {
            return userName;
        }

        public String getUserSex() {
            return userSex;
        }

        public String getUserTelephone() {
            return userTelephone;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

    }
    class UserInfoResponsedetail {
        private String userName;
        private String userSex;
        private String userTelephone;
        private String userAvatar;
        private Integer userAgree;
        private String userSchool;

        public UserInfoResponsedetail(String userName, String userSex, String userTelephone,String userAvatar,Integer userAgree,String userSchool) {
            this.userName = userName;
            this.userSex = userSex;
            this.userTelephone = userTelephone;
            this.userAvatar = userAvatar;
            this.userAgree = userAgree;
            this.userSchool = userSchool;
        }

        // 添加对应的 getter 方法
        public String getUserName() {
            return userName;
        }

        public String getUserSex() {
            return userSex;
        }

        public String getUserTelephone() {
            return userTelephone;
        }

        public String getUserAvatar() {
            return userAvatar;
        }
        public Integer  getUserAgree() {
            return userAgree;
        }
    }
    /**
     * 用户信息
     * @param data
     * @return
     */
    @PostMapping("/mine")
    public  UserInfoResponse mine(@RequestBody Map<String,Object> data) // 当用户点击登录
    {
        System.out.println(data);
        Integer role = Integer.parseInt(data.get("role").toString()); // 获取role参数的值

        System.out.println(role);

        System.out.println(openid);

        if (role == 1){ // 如果角色为1（用户）
            List<User> users = userService.getinfo(openid,gschool); // 查询用户信息
            User user = users.get(0); // 假设查询结果只有一个用户
            String userName = user.getUserName(); // 获取用户
            String userSex = user.getUserSex(); // 获取用户
            String userTelephone = user.getUserTelephone(); // 获取用户
            String userAvatar = user.getUserAvatar();
            System.out.println(userName);
            return new UserInfoResponse(userName, userSex, userTelephone ,userAvatar);

        }
        else{ // 如果角色不是1（司机）
            List<Driver> drivers = driverService.getinfo(openid,gschool); // 查询司机信息
            Driver driver = drivers.get(0); // 假设查询结果只有一个司机
            String driverName = driver.getDriverName(); // 获取用户
            String driverSex = driver.getDriverSex(); // 获取用户
            String driverTelephone = driver.getDriverTelephone(); // 获取用户
            String driverAvatar = driver.getDriverAvatar();
            System.out.println(driverName);
            return new UserInfoResponse(driverName, driverSex, driverTelephone , driverAvatar);

        }
    }
    /**
     * 用户信息
     * @param data
     * @return
     */
    @PostMapping("/info")
    public  String info(@RequestBody Map<String,Object> data) // 当用户点击登录
    {
        System.out.println(data);
        Integer role = Integer.parseInt(data.get("role").toString()); // 获取role参数的值
        String school = data.get("school").toString(); // 获取code参数的值
        String name = data.get("username").toString();
        String sex = data.get("usersex").toString();
        String tele = data.get("usertele").toString();
        System.out.println(role);
        System.out.println(openid);
        System.out.println(name);
        System.out.println(sex);
        System.out.println(tele);

        if (role == 1){ // 如果角色为1（用户）
            List<User> users = userService.getinfo(openid,gschool); // 查询用户信息
            User user = users.get(0); // 假设查询结果只有一个用户
            userService.updateinfo(name,sex, tele,openid,gschool);
           return "保存成功";

        }
        else{ // 如果角色不是1（司机）
            List<Driver> drivers = driverService.getinfo(openid,gschool); // 查询司机信息
            Driver driver = drivers.get(0); // 假设查询结果只有一个司机
            driverService.updateinfo(name,sex, tele,openid,gschool);
            return "保存成功";


        }
    }
    /**
     * 用户信息
     * @param data
     * @return
     */
    @PostMapping("/detail")
    public  UserInfoResponsedetail detail(@RequestBody Map<String,Object> data) // 当用户点击登录
    {
        System.out.println(data);

        String did = data.get("did").toString();

        List<Driver> drivers = driverService.getinfo(did,gschool); // 查询司机信息
        Driver driver = drivers.get(0); // 假设查询结果只有一个司机
        String driverName = driver.getDriverName(); // 获取用户
        String driverSex = driver.getDriverSex(); // 获取用户
        String driverTelephone = driver.getDriverTelephone(); // 获取用户
        String driverAvatar = driver.getDriverAvatar();
        String driverSchool = driver.getDriverSchool();
        Integer driverAgree = driver.getDriverAgree();
        System.out.println(driverName);
        return new UserInfoResponsedetail(driverName, driverSex, driverTelephone , driverAvatar,driverAgree,driverSchool);


    }
    @GetMapping("/driverinfo")
    public List<Driver> getAllinfo() {
        System.out.println("查询司机信息");
        // 查询用户表的所有信息
        List<Driver> drivers = driverService.getAllinfo();
        System.out.println(drivers);
        return drivers;
    }
    /**
     * 评论信息
     * @param data
     * @return
     */
    @PostMapping("/comment")
    public List<Comment> getAllcomment(@RequestBody Map<String,Object> data) {
        System.out.println("查询评论信息");
        String did = data.get("did").toString();
        // 查询用户表的所有信息
        List<Comment> comments = commentService.getById(did,gschool);
        System.out.println(comments);
        return comments;
    }
    /**
     * 增加评论
     * @param data 包含信息的Map对象
     * @return 返回成功停止的提示信息
     */
    @PostMapping(value = "/upcomment") // 处理HTTP POST请求的映射
    public String upcomment(@RequestBody Map<String,Object> data) {
        System.out.println(data); // 打印参数的值
        String did = data.get("did").toString();
        String comment = data.get("content").toString();
        String name = data.get("name").toString();
        String avatar = data.get("avatar").toString();
        String identity = data.get("Identity").toString();
        String time = data.get("time").toString();
        commentService.insertId(did,comment,name,avatar,identity,time,gschool);
        return "成功增加评论"; //
    }
    /**
     * 留言信息
     * @param data
     * @return
     */
    @PostMapping("/centence")
    public List<Centence> getAllcentence(@RequestBody Map<String,Object> data) {
        System.out.println("查询留言信息");
        String title = data.get("title").toString();
        // 查询用户表的所有信息
        List<Centence> centences = centenceService.getByTitle(title,gschool);
        System.out.println(centences);
        return centences;
    }
    /**
     * 增加留言
     * @param data 包含信息的Map对象
     * @return 返回成功停止的提示信息
     */
    @PostMapping(value = "/upcentence") // 处理HTTP POST请求的映射
    public String upcentence(@RequestBody Map<String,Object> data) {
        System.out.println(data); // 打印参数的值
        String title = data.get("title").toString();
        String comment = data.get("content").toString();
        String name = data.get("name").toString();
        String avatar = data.get("avatar").toString();
        String identity = data.get("Identity").toString();
        String time = data.get("time").toString();
        centenceService.insertTitle(title,comment,name,avatar,identity,time,gschool);
        return "成功增加留言"; //
    }
    /**
     * 增加点赞人数
     * @param data 包含信息的Map对象
     * @return 返回成功停止的提示信息
     */
    @PostMapping(value = "/add") // 处理HTTP POST请求的映射
    public String add(@RequestBody Map<String,Object> data) {
        System.out.println(data); // 打印参数的值
        String did = data.get("did").toString();
        Integer people = Integer.parseInt(data.get("people").toString());
        driverService.updateagree(people,did,gschool);
        return "成功更新点赞人数"; //
    }
    /**
     * 司机位置更新
     * @param data 包含位置信息的Map对象
     * @return 返回成功插入的提示信息
     */
    @PostMapping("/driver") // 处理HTTP POST请求的映射
    public String driver(@RequestBody Map<String,Object> data) { // 当司机点击共享位置
        System.out.println(data); // 打印参数的值

        // 从data中获取司机的纬度和经度
        String driverLatitude = data.get("latitude").toString();
        String driverLongitude = data.get("longitude").toString();

        // 调用driverService的方法，更新司机的纬度和经度
        driverService.updatelatitude(driverLatitude,openid,gschool);
        driverService.updatelongitude(driverLongitude,openid,gschool);

        return "成功插入"; // 返回成功插入的提示信息
    }
    /**
     * 司机停止
     * @param data 包含停止信息的Map对象
     * @return 返回成功停止的提示信息
     */
    @PostMapping(value = "/driverstop") // 处理HTTP POST请求的映射
    public String driverstop(@RequestBody Map<String,Object> data) { // 当司机点击停止位置共享
        System.out.println(data); // 打印参数的值


        String driverLatitude = "0";
        String driverLongitude = "0";

        // 调用driverService的方法，更新司机的纬度和经度
        driverService.updatelatitude(driverLatitude,openid,gschool);
        driverService.updatelongitude(driverLongitude,openid,gschool);

        return "成功停止"; // 返回成功停止的提示信息
    }
    /**
     * 客户到站点
     * @param data 包含位置信息的Map对象
     * @return 返回候车状态信息
     */
    @PostMapping(value = "/site") // 当用户点击等车
    public String site(@RequestBody Map<String,Object> data) { // 标识site方法处理HTTP请求，并从请求体获取参数
        System.out.println(data); // 打印参数的值
        String waitid = openid; // 假设waitid为用户的唯一标识
        System.out.println(waitid);
        // 获取所有站点信息和用户等车队列信息
        List<Siteinfo> sites = siteinfoService.getAll();
        List<Waitcar> waits = waitService.getAll(waitid);
        String pLatitude = data.get("latitude").toString();
        double pla = Double.parseDouble(pLatitude);
        String pLongitude = data.get("longitude").toString();
        double plo = Double.parseDouble(pLongitude);
        int mark = 0; // 是否到达站点的标志
        System.out.println(sites);
        for (int i = 0; i < sites.size(); i++) {
            String pName = sites.get(i).getSiteinfoName();
            double la = Double.parseDouble(sites.get(i).getSiteinfoLatitude());//经度
            double lo = Double.parseDouble(sites.get(i).getSiteinfoLongitude());//纬度
            double ct = Double.parseDouble(sites.get(i).getSiteinfoCount());//i站点的人数
            double distance = calculateDistance(pla, plo, la, lo); // 计算用户与站点之间的距离
            System.out.println("两个经纬度之间的距离：" + distance + "米");
            if (distance < 10.0) { // 如果距离小于10米，则说明用户在站点范围内
                myVariable = i; // 更新myVariable的值为当前站点的索引
                mark = 1; // 更新到达站点的标志为1
                System.out.println("距离小于10米");
                if (waits.size() == 0) { // 如果等车队列为空，则新增一条记录并更新站点计数器
                    ct = ct + 1;
                    String ct1 = String.valueOf(ct);
                    System.out.println(ct1);
                    siteinfoService.updatecount(ct1, pName); // 更新站点计数器
                    waitService.insertId(waitid); // 插入等车队列记录
                    waitService.updatewaitcar("1", waitid); // 更新等车队列中的等车状态
                } else if (Integer.parseInt(waits.get(0).getWaitState()) == 0) {
                    // 如果等车队列第一条记录的等车状态为0，则更新等车队列和站点计数器
                    ct = ct + 1;
                    String ct1 = String.valueOf(ct);
                    System.out.println(ct1);
                    siteinfoService.updatecount(ct1, pName); // 更新站点计数器
                    waitService.updatewaitcar("1", waitid); // 更新等车队列中的等车状态
                    System.out.println("已经进入等车队列了");
                }
            } else {// 如果距离大于10米，则说明用户不在站点范围内
                System.out.println("距离大于等于10米");
            }
        }

        if (mark == 0) { // 如果没有到达任何站点，则更新等车队列中的等车状态为0，并返回提示信息
            waitService.updatewaitcar("0", waitid); // 更新等车队列中的等车状态为0
            if (myVariable == 100) {//如果myVariable未发生过变化，说明用户哪个站点范围也不在
                return "您不在任何站点范围内";
            } else {//myVariable发生过变化，说明用户是先处于从某个站点范围内等车，后离开
                String pName = sites.get(myVariable).getSiteinfoName();
                double ct = Double.parseDouble(sites.get(myVariable).getSiteinfoCount()) - 1;//当用户离开后该站点人数减1
                String ct1 = String.valueOf(ct);
                siteinfoService.updatecount(ct1, pName); // 更新站点计数器
                myVariable = 100; // 重置myVariable的值
                return "您不在任何站点范围内";
            }
        } else {
            return "正在候车";
        }
    }
    //计算距离
    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c * 1000; // 转换为米
        return distance;
    }


    /**
     * 获取新闻
     * @return 返回新闻信息
     */
    @PostMapping("/news")
    public List<News> getBySchool(@RequestBody Map<String,Object> data) {
        System.out.println("查询新闻信息");
        System.out.println(data);
        String school = data.get("school").toString();
        // 查询用户表的所有信息
        List<News> news = newsService.getBySchool(school);
        System.out.println(news);
        return news;
    }



}
