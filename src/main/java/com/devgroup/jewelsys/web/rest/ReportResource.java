package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.domain.User;
import com.devgroup.jewelsys.service.ReportService;
import com.devgroup.jewelsys.service.UserService;
import com.devgroup.jewelsys.service.dto.RptParamsDTO;
import com.devgroup.jewelsys.util.SharedUtils;
import com.devgroup.partner.domain.PGoldType;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportResource {

    private final ReportService reportService;

    @Autowired
    ServletContext context;

    private final UserService userService;

    public ReportResource(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @PostMapping("/user-list-rpt")
    public RptParamsDTO printUserList(@Valid @RequestBody RptParamsDTO rptParams) {
        User loginUser = userService.getUserWithAuthorities().get();
        String rptOutFolder = context.getRealPath("RPT_OUTPUT");
        String rptOutputPath = rptOutFolder + "\\" + loginUser.getLogin() + "\\";
        String rptFileName = SharedUtils.generateFileName("UserListRpt");
        rptParams.setRptFileName(rptFileName);
        rptParams.setRptOutputPath(rptOutputPath);
        rptParams.setRptJrxml("UserList.jrxml");
        rptParams.setRptJasper("UserList.jasper");
        String rptOutFilePath = this.reportService.printUserList(rptParams);
        if (rptOutFilePath != null && rptOutFilePath != "") return rptParams; else return null;
    }

    //@PathVariable String fileName
    @GetMapping("/viewPdf/{fileName}")
    public ResponseEntity<Resource> viewPdf(@PathVariable final String fileName) throws Exception {
        User loginUser = userService.getUserWithAuthorities().get();
        String rptOutFolder = context.getRealPath("RPT_OUTPUT");
        String rptOutputPath = rptOutFolder + "\\" + loginUser.getLogin() + "\\";
        File file = new File(rptOutputPath + fileName);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + fileName);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok().headers(header).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable final String fileName) throws Exception {
        User loginUser = userService.getUserWithAuthorities().get();
        int dot = fileName.lastIndexOf('.');
        String extension = (dot == -1) ? "" : fileName.substring(dot + 1);
        String rptOutFolder = context.getRealPath("RPT_OUTPUT");
        String rptOutputPath = rptOutFolder + "\\" + loginUser.getLogin() + "\\";
        File file = new File(rptOutputPath + fileName);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        return ResponseEntity
            .ok()
            .headers(header)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/" + extension))
            .body(resource);
    }

    @GetMapping("/test")
    public RptParamsDTO test() {
        RptParamsDTO data = new RptParamsDTO();
        String rptOutFolder = context.getRealPath("RPT_OUTPUT");
        data.setRptPS1("Report Output Folder:" + rptOutFolder);

        String rootPath = SharedUtils.getResourceBasePath();
        data.setRptPS2("Root Path by jewelsys:" + rootPath);

        String contextPath = context.getContextPath();
        data.setRptPS3("Context Path" + contextPath);

        return data;
    }

    @GetMapping("/testps")
    public List<PGoldType> testps() {
        List<PGoldType> gtList = null;
        gtList = this.reportService.getAllGoldTypeFrmPS();

        return gtList;
    }

    @PostMapping("/mortgage-list-rpt")
    public RptParamsDTO generateMortgage(@Valid @RequestBody RptParamsDTO rptParams) {
        User loginUser = userService.getUserWithAuthorities().get();
        String rptOutFolder = context.getRealPath("RPT_OUTPUT");
        String rptOutputPath = rptOutFolder + "\\" + loginUser.getLogin() + "\\";
        String rptFileName = SharedUtils.generateFileName("MortgageListRpt");
        rptParams.setRptFileName(rptFileName);
        rptParams.setRptOutputPath(rptOutputPath);
        rptParams.setRptJrxml("MortgageListRpt.jrxml");
        rptParams.setRptJasper("MortgageListRpt.jrxml");
        String rptOutFilePath = this.reportService.generateMortgage(rptParams);
        if (rptOutFilePath != null && rptOutFilePath != "") return rptParams; else return null;
    }
}
