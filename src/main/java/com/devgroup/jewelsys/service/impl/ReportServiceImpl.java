package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.config.PartnerDBConfiguration;
import com.devgroup.jewelsys.domain.enumeration.MortgageDamageType;
import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import com.devgroup.jewelsys.service.ReportService;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    PartnerDBConfiguration dbconfig;

    public ReportServiceImpl() {}

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

        Map<String, Object> parameters = new HashMap<String, Object>();
        String rptFilePath = ReportPrint.print(userList, rptPara, parameters);
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

    @Override
    public String generateMortgage(RptParamsDTO rptPara) {
        /*
    	List<MortgageEntryDTO> mortgageList=em.createStoredProcedureQuery("SP_MORTGAGE_LIST_RPT")
    	.unwrap(org.hibernate.query.NativeQuery.class)
    	.setResultTransformer(Transformers.aliasToBean(MortgageEntryDTO.class))
    	.getResultList();
    	*/

        StoredProcedureQuery query = em.createStoredProcedureQuery("SP_MORTGAGE_LIST_RPT");
        query.registerStoredProcedureParameter("frmDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("toDate", String.class, ParameterMode.IN);
        query.setParameter("frmDate", rptPara.getRptPS1());
        query.setParameter("toDate", rptPara.getRptPS2());

        List<MortgageEntryDTO> mortgageList = null;
        List<Object[]> resultList = query.getResultList();
        if (resultList != null) {
            mortgageList = new ArrayList<MortgageEntryDTO>();
            for (Object[] arr : resultList) {
                MortgageEntryDTO dto = new MortgageEntryDTO();
                dto.setStartDateStr(String.valueOf(arr[0]));
                dto.setName(String.valueOf(arr[1]));
                String groupCode = arr[2].toString();
                String groupDesc = getGroupDesc(groupCode);
                dto.setGroupDesc(groupDesc);
                dto.setItemName(String.valueOf(arr[3]));
                String dtCode = arr[4] != null ? arr[4].toString() : "";
                String damageTypeDesc = getDamageTypeDesc(dtCode);
                dto.setDamageTypeDesc(damageTypeDesc);
                dto.setPrincipalAmount(Double.valueOf(String.valueOf(arr[5])));
                dto.setAddress(String.valueOf(arr[6]));
                dto.setMortStatusDesc(String.valueOf(arr[7]));
                mortgageList.add(dto);
            }
        }

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("frmDate", rptPara.getRptPS1());
        parameters.put("toDate", rptPara.getRptPS2());
        String rptFilePath = ReportPrint.print(mortgageList, rptPara, parameters);
        return rptFilePath;
    }

    private String getGroupDesc(String groupCode) {
        String groupDesc = "";
        switch (groupCode) {
            case "G01":
                groupDesc = MortgageItemGroup.G01.getValue();
                break;
            case "G02":
                groupDesc = MortgageItemGroup.G02.getValue();
                break;
            case "G03":
                groupDesc = MortgageItemGroup.G03.getValue();
                break;
            case "G04":
                groupDesc = MortgageItemGroup.G04.getValue();
                break;
            case "G05":
                groupDesc = MortgageItemGroup.G05.getValue();
                break;
            case "G06":
                groupDesc = MortgageItemGroup.G06.getValue();
                break;
            case "G07":
                groupDesc = MortgageItemGroup.G07.getValue();
                break;
            case "G08":
                groupDesc = MortgageItemGroup.G08.getValue();
                break;
            case "G09":
                groupDesc = MortgageItemGroup.G09.getValue();
                break;
            default:
                break;
        }
        return groupDesc;
    }

    private String getDamageTypeDesc(String code) {
        String damageTypeDesc = "";
        switch (code) {
            case "DT01":
                damageTypeDesc = MortgageDamageType.DT01.getValue();
                break;
            case "DT02":
                damageTypeDesc = MortgageDamageType.DT02.getValue();
                break;
            case "DT03":
                damageTypeDesc = MortgageDamageType.DT03.getValue();
                break;
            case "DT04":
                damageTypeDesc = MortgageDamageType.DT04.getValue();
                break;
            case "DT05":
                damageTypeDesc = MortgageDamageType.DT05.getValue();
                break;
            default:
                break;
        }
        return damageTypeDesc;
    }
}
