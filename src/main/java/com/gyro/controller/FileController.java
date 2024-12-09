package com.gyro.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileController {

    // 显示文件上传页面
    @GetMapping(value = "/fileupload")
    public String toFileUpload() {
        return "fileupload";  // 返回文件上传页面
    }

    // 处理文件上传
    @PostMapping(value = "/fileupload")
    public String fileUpload(@RequestParam(value = "file") List<MultipartFile> files, HttpServletRequest request) {
        String msg = "";
        if (!files.isEmpty()) {
            String basePath = request.getServletContext().getRealPath("/upload/");  // 上传文件保存路径
            File uploadFile = new File(basePath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null && !originalFilename.isEmpty()) {
                    try {
                        originalFilename = UUID.randomUUID() + "_" + URLEncoder.encode(originalFilename, "UTF-8");  // 使用UTF-8编码文件名
                        file.transferTo(new File(basePath + originalFilename));  // 保存文件
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg = "文件上传失败！";
                    }
                } else {
                    msg = "上传的文件为空！";
                }
            }
            msg = "文件上传成功！";
        } else {
            msg = "没有文件被上传！";
        }
        request.setAttribute("msg", msg);
        return "fileupload";  // 返回上传页面
    }

    // 处理文件上传超过大小的异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        request.setAttribute("msg", "文件超过了指定大小，上传失败！");
        return "fileupload";  // 返回上传页面，显示错误信息
    }

    // 显示文件下载页面
    @RequestMapping(value = "/filedownload")
    public String toFileDownload(HttpServletRequest request) {
        return "filedownload";  // 返回文件下载页面
    }

    // 获取文件列表
    @RequestMapping(value = "/fileList")
    @ResponseBody
    public String fileList(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码为UTF-8
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String baseDir = request.getServletContext().getRealPath("/upload");  // 获取文件目录
        File baseFile = new File(baseDir);
        List<String> fileList = new ArrayList<>();

        if (baseFile.exists()) {
            File[] files = baseFile.listFiles();
            for (File file : files) {
                // 将文件名加入到文件列表中
                fileList.add(file.getName());
            }
        }

        // 返回文件列表，确保是以UTF-8编码的JSON字符串
        //return new String(JSON.toJSONString(fileList).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        //FastJSON 会默认使用 UTF-8 编码
        return JSON.toJSONString(fileList);
    }

    // 下载文件
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> fileDownload(String filename, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/upload/");
        File file = new File(path + filename);

        filename = this.getFilename(request, filename);  // 处理文件名编码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);  // 设置响应头
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.length()); 

        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);  
    }

    // 处理浏览器的编码问题
    public String getFilename(HttpServletRequest request, String filename) throws UnsupportedEncodingException {
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        String userAgent = request.getHeader("User-Agent");
        for (String keyword : IEBrowserKeyWords) {
            if (userAgent.contains(keyword)) {
                return URLEncoder.encode(filename, "UTF-8");  
            }
        }
        
        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
