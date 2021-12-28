package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.config.PartnerDBConfiguration;
import com.devgroup.jewelsys.repository.UserRepository;
import com.devgroup.jewelsys.service.ReportService;
import com.devgroup.jewelsys.service.dto.RptParamsDTO;
import com.devgroup.jewelsys.service.dto.UserDTO;
import com.devgroup.jewelsys.util.ReportPrint;
import com.devgroup.partner.domain.PGoldType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    @Autowired
    PartnerDBConfiguration dbconfig;

    public ReportServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Connection getConnecton() throws SQLException {
        Connection con = null;
        try {
            Class.forName(dbconfig.getDriverClassName());
            con = DriverManager.getConnection(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }

    /*
    @Override
    public String printUserList(RptParamsDTO rptPara) {
        List<Object> userList = userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toCollection(LinkedList::new));
        String rptFilePath = ReportPrint.print(userList, rptPara);
        return rptFilePath;
    }
    */

    @Override
    public String printUserList(RptParamsDTO rptPara) {
        List<Object> userList = new ArrayList<Object>();

        Connection con = null;
        try {
            con = getConnecton();
            if (con != null) {
                Statement stat = con.createStatement();
                ResultSet res = stat.executeQuery("select * from gold_type");
                while (res.next()) {
                    UserDTO data = new UserDTO();
                    data.setFirstName(res.getString("name"));
                    data.setLastName("");
                    data.setEmail(res.getString("del_flg"));
                    userList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        String rptFilePath = ReportPrint.print(userList, rptPara);
        return rptFilePath;
    }

    @Override
    public List<PGoldType> getAllGoldTypeFrmPS() {
        Connection con = null;
        List<PGoldType> gtList = new ArrayList<PGoldType>();

        try {
            con = getConnecton();
            if (con != null) {
                Statement stat = con.createStatement();
                ResultSet res = stat.executeQuery("select * from gold_type");
                while (res.next()) {
                    PGoldType data = new PGoldType();
                    data.setCode(res.getString("code"));
                    data.setName(res.getString("name"));
                    data.setDelFlg(res.getString("del_flg"));
                    gtList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return gtList;
    }
}
