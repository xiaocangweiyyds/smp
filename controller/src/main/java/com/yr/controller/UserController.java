package com.yr.controller;

import com.yr.entity.User;
import com.yr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @RequestMapping(value = "index")
    public String index() {
        return "user/index";
    }

    //国际化
    @RequestMapping(value = "i18n")
    public String i18n(Locale locale, Map<String, String> map) {
        map.put("lang", locale.toString());
        return "forward:/user/list";
    }

    @Autowired
    private IUserService userService;

    //添加
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public String add(User user, Map<String, Object> map, @RequestParam(value = "picture") MultipartFile files) {
        try {
            String pathUrl = "F:\\test\\Picture\\" + System.currentTimeMillis() + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
            files.transferTo(new File(pathUrl));
            user.setHead(pathUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.add(user);
        return "redirect:/user/list";
    }

    //from标签需要
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String input(Map<String, Object> map) {
        map.put("user", new User());
        return "user/compile";
    }

    //删除
    @RequestMapping(value = "users/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/user/list";
    }

    //修改
    @RequestMapping(value = "users", method = RequestMethod.PUT)
    public String update(@ModelAttribute User user, Map<String, Object> map, @RequestParam(value = "picture") MultipartFile files) {
        try {
            String pathUrl = "F:\\test\\Picture\\" + System.currentTimeMillis() + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
            files.transferTo(new File(pathUrl));
            user.setHead(pathUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.update(user);
        return "redirect:/user/list";
    }

    //修改回显
    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public String getUpdate(@PathVariable("id") Integer id, Map<String, Object> map) {
        map.put("user", userService.getUpdate(id));
        return "user/compile";
    }

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("user", userService.getUpdate(id));
        }
    }

    //查询
    @RequestMapping(value = "list")
    public String select(Map<String, List<User>> map) {
        List<User> list = userService.select();
        map.put("lst", list);
        return "user/list";
    }

    // 图片预览
    @RequestMapping(value = "picturePreview/{id}")
    public void picturePreview(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
        if (id != null) {
            FileInputStream fis = new FileInputStream(userService.getUpdate(id).getHead());
            ServletOutputStream out = response.getOutputStream();
            byte[] bt = new byte[1024];
            int length = 0;
            while ((length = fis.read(bt)) != -1) {
                out.write(bt, 0, length);
            }
            out.close();
            fis.close();
        }
    }

    // 上传
    @RequestMapping(path = "/uploadfile", method = RequestMethod.POST)
    public String upoadFile(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream("F:\\test\\uploadfile\\" + System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
            byte[] b = new byte[1024 * 1024];
            int length = 0;
            while ((length = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, length);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        }
        return "redirect:/user/list";
    }


    // 下载
    @RequestMapping(value = "downloadfile/{id}")
    public void downloadFile(String path, @PathVariable("id") Integer id, HttpServletResponse resp) throws Exception {
        if (path != "" || path != null) {
            File file = new File(userService.getUpdate(id).getHead());
            resp.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            resp.setContentType("application/octet-stream; charset=UTF-8");

            InputStream is = new FileInputStream(file);
            OutputStream os = resp.getOutputStream();
            byte[] b = new byte[1024 * 1024 * 5];

            int length = 0;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
                os.flush();
            }
            os.close();
            is.close();
        } else {
            String name = path.substring(path.lastIndexOf("\\") + 1);
            resp.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(name, "UTF-8"));
            resp.setContentType("application/octet-stream; charset=UTF-8");

            File file = new File(path);
            InputStream is = new FileInputStream(file);
            OutputStream os = resp.getOutputStream();
            byte[] b = new byte[1024 * 1024 * 5];

            int length = 0;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
                os.flush();
            }
            os.close();
            is.close();
        }
    }


}
