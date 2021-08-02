package com.demo.postgresql.controller;

import com.demo.postgresql.common.BaseResponse;
import com.demo.postgresql.constant.Constant;
import com.demo.postgresql.entity.User;
import com.demo.postgresql.entity.Schema;
import com.demo.postgresql.utils.DbUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/test/data")
public class TestController {
    @ApiOperation(value = "全局搜索")
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public BaseResponse getData(@RequestBody Map<String, List> paramsMap) throws SQLException, ClassNotFoundException {

        BaseResponse baseResponse = new BaseResponse(0, null, null);
        Class.forName(Constant.DRIVER);
        Connection connection = DriverManager.getConnection(Constant.URL, Constant.USER, Constant.PASSWORD);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(60); //sql执行60秒超时
        List<User> jsonList = paramsMap.get("userList");
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList = mapper.convertValue(jsonList, new TypeReference<List<User>>() {
        });
        List<Schema> jsonSchemaPossibleList = paramsMap.get("schemaPossibleList");
        List<Schema> schemaPossibleList = mapper.convertValue(jsonSchemaPossibleList, new TypeReference<List<Schema>>() {
        });

        List<String> schemaList = new ArrayList<>();
        //判断是否传入schema参数
        if (schemaPossibleList != null && schemaPossibleList.size() > 0) {
            schemaList = schemaPossibleList.stream().map(Schema::getSchemaName).collect(Collectors.toList());
        } else { //若schema参数为空，则全局搜索
            ResultSet schemaResult = statement.executeQuery(Constant.FIND_ALL_SCHEMA);
            while (schemaResult.next()) {
                String schemaName = schemaResult.getString("nspname"); //得到所有的schema
                schemaList.add(schemaName);
            }
        }
        List<String> result = new ArrayList<>();

        try {
            for (String schema : schemaList) { //过滤自带schema
                if (schema.equals("pg_toast_temp_1") || schema.equals("pg_temp_1") || schema.equals("pg_toast")) {
                    continue;
                }
                List<String> tableList = new ArrayList<>();
                ResultSet rs = statement.executeQuery(Constant.SCHEMA_SQL.replace("#", "'" + schema + "'")); //得到某个schema下所有的表名
                String schemaName = null;
                while (rs.next()) {
                    schemaName = rs.getString("schemaname");
                    String tableName = rs.getString("tablename");
                    tableList.add(tableName);
                }

                for (User user : userList) {
                    String findFirstName;
                    String findSecondName;
                    String findThirdName;
                    List<String> findList = new ArrayList<>();
                    if (user.getFirstName() != null && user.getFirstName().length() != 0 && !user.getFirstName().equals(" ")) {
                        findFirstName = user.getFirstName();
                        findList.add(findFirstName);
                    }
                    if (user.getSecondName() != null && user.getSecondName().length() != 0 && !user.getSecondName().equals(" ")) {
                        findSecondName = user.getSecondName();
                        findList.add(findSecondName);
                    }
                    if (user.getThirdName() != null && user.getThirdName().length() > 0 && !user.getThirdName().equals(" ")) {
                        findThirdName = user.getThirdName();
                        findList.add(findThirdName);
                    }

                    for (String table : tableList) {
                        //得到某个表所有的列名
                        String sql = Constant.COLUMN_SQL.replace("#", "'" + schemaName + "'").replace("&", "'" + table + "'");    // \"
                        ResultSet dataResult = statement.executeQuery(sql);
                        List<Object> columnList = DbUtils.convertToList(dataResult);
                        for (Object column : columnList) { //一张表有多个列
                            for (String testData : findList) {
                                String executeSql = Constant.FIND_SQL.replace("$", "\"" + schemaName + "\"" + "." + "\"" + table + "\"")
                                        .replace("@", String.valueOf(column).replace("{column_name=", "").replace("}", "")).replace("#", "'%" + testData + "%'");

                                ResultSet data;
                                try {
                                    data = statement.executeQuery(executeSql);
                                } catch (Exception e) {
                                    connection.rollback();
                                    try {
                                        data = statement.executeQuery(executeSql);//重试一次
                                    } catch (Exception e1) {
                                        connection.rollback();
                                        continue;
                                    }
                                }
                                if (DbUtils.convertToList(data).size() > 0) {
                                    result.add("数据: " + testData + " 匹配的模式是：" + schemaName + " ," + " 表名是：" + table + " ," + " 列名是：" + String.valueOf(column).replace("{column_name=", "").replace("}", ""));
                                }
                            }
                        }
                    }
                }
            }

        } catch (
                Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            statement.close();
            connection.close();
        }

        if (result.size() > 0) {
            baseResponse.setMsg("success");
            baseResponse.setData(result);
        }
        return baseResponse;
    }
}

/*
入参示例：
{
	"userList": [{
		"firstName": "郑晟旻",
		"secondName": "pg_toast_2604_index",
		"thirdName": "Extended dynamic SQL"
	}],
	"schemaPossibleList": [{
		"schemaName": "pg_catalog"
	}, {
		"schemaName": "public"
	}]
}

*/