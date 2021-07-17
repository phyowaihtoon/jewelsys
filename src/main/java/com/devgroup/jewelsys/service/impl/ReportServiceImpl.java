package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.repository.UserRepository;
import com.devgroup.jewelsys.service.ReportService;
import com.devgroup.jewelsys.service.dto.RptParamsDTO;
import com.devgroup.jewelsys.service.dto.UserDTO;
import com.devgroup.jewelsys.util.ReportPrint;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    public ReportServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String printUserList(RptParamsDTO rptPara) {
        List<Object> userList = userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toCollection(LinkedList::new));
        String rptFilePath = ReportPrint.print(userList, rptPara);
        return rptFilePath;
    }
}
