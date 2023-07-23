package cakeit.server.file.controller;

import cakeit.server.entity.FileEntity;
import cakeit.server.file.dto.FileDto;
import cakeit.server.file.service.FileService;
import cakeit.server.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final FileService fileService;

    @GetMapping("/api/upload")
    public String goToUpload() {
        log.info("업로드 겟 >>>");
        return "upload";
    }

    @PostMapping("/api/upload")
    public String uploadFile(FileDto fileDto) throws IOException {
        log.info("업로드 포스트 >>>");

        String url = s3Service.uploadFile(fileDto.getFile());

        log.info("업로드 포스트 url>>>" + url);

        fileDto.setUrl(url);

        log.info("업로드 포스트 fileDto>>>" + fileDto);
        fileService.save(fileDto);

        return "redirect:/api/list";
    }

    @GetMapping("api/list")
    public String listPage(Model model) {
        List<FileEntity> fileList = fileService.getFiles();
        model.addAttribute("fileList", fileList);
        return "list";
    }
}
