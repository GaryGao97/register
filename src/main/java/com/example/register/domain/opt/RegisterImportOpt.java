package com.example.register.domain.opt;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 导入文件
 * @author: Gary Gao(修远)
 * @date: 2021/7/21
 */
@Data
public class RegisterImportOpt {
    private MultipartFile file;
}
